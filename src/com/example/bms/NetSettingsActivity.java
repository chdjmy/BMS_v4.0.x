package com.example.bms;

import java.util.ArrayList;
import java.util.List;

import com.example.bean.SettingBean;
import com.example.dao.SettingDao;
import com.example.http.AsyncHttpRequest;
import com.example.http.RequestParams;
import com.example.http.RequestResultCallback;
import com.example.http.ThreadPool;
import com.example.util.Common;
import com.example.util.MD5;
import com.example.util.Uri;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NetSettingsActivity extends BaseActivity {

	private Button buttonSaveNet;
	private Button buttonCancelNet;
	private EditText edit_net_ip,edit_net_port,edit_net_webapp,edit_net_timeout;
	
	private Handler handler;
	private ProgressDialog dialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_settings);
		edit_net_ip = (EditText)findViewById(R.id.edit_net_ip);
		edit_net_port = (EditText)findViewById(R.id.edit_net_port);
		edit_net_webapp = (EditText)findViewById(R.id.edit_net_webapp);
		edit_net_timeout = (EditText)findViewById(R.id.edit_net_timeout);
		
		
		SettingBean net = Common.getSetting(this);
		edit_net_ip.setText(net.getIp());
		edit_net_port.setText(net.getPort());
		edit_net_webapp.setText(net.getWeb_app());
		edit_net_timeout.setText(String.valueOf(net.getTimeout()));
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				AlertDialog.Builder builder = new AlertDialog.Builder(NetSettingsActivity.this);
				builder.setTitle("提示");
				switch(msg.what){
				case 1:
					builder.setMessage("连接成功，是否保存设置？");
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									SettingBean net = new SettingBean();
									net.setIp(edit_net_ip.getText().toString());
									net.setPort(edit_net_port.getText().toString());
									net.setWeb_app(edit_net_webapp.getText().toString());
									net.setTimeout(Integer.parseInt(edit_net_timeout.getText().toString()));
									SettingDao settingDao = new SettingDao(NetSettingsActivity.this);
									settingDao.updateSetting(net);
									displayToast("保存成功");
								}
							});
					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					builder.show();
					break;
				case 2:
					builder.setMessage("连接失败，请检查IP或端口是否正确？");
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									
								}
							});
					builder.show();
					break;
				}
				dialog.dismiss();
			}
		};
	
		buttonSaveNet = (Button)findViewById(R.id.buttonSaveNet);
		buttonSaveNet.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SettingBean net = new SettingBean();
				net.setIp(edit_net_ip.getText().toString());
				net.setPort(edit_net_port.getText().toString());
				net.setWeb_app(edit_net_webapp.getText().toString());
				net.setTimeout(Integer.parseInt(edit_net_timeout.getText().toString()));
				SettingDao settingDao = new SettingDao(NetSettingsActivity.this);
				settingDao.updateSetting(net);
				displayToast("设置成功");
				NetSettingsActivity.this.finish();
				
			}
			
		});
		//删除键改为连接测试
		buttonCancelNet = (Button)findViewById(R.id.buttonCancelNet);
		buttonCancelNet.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(CheckNetworkState()){
					dialog = ProgressDialog.show(NetSettingsActivity.this, null,"连接中,请稍候...", true, false);
					SettingBean settingBean = new SettingBean();
					settingBean.setIp(edit_net_ip.getText().toString());
					settingBean.setPort(edit_net_port.getText().toString());
					settingBean.setWeb_app(edit_net_webapp.getText().toString());
					settingBean.setTimeout(Integer.parseInt(edit_net_timeout.getText().toString()));
					AsyncHttpRequest currentTime = new AsyncHttpRequest(Uri.getUri(
							settingBean.getIp(), settingBean.getPort(),
							settingBean.getWeb_app(), Uri.Init.GET_CURRENTTIME), null,
							new RequestResultCallback() {
								@Override
								public void onSuccess(Object paramObject) {
									Message msg = handler.obtainMessage();
									msg.what = 1;
									handler.sendMessage(msg);
								}

								@Override
								public void onFail(String paramException) {
									Message msg = handler.obtainMessage();
									msg.what = 2;
									handler.sendMessage(msg);
								}
							});
					ThreadPool.getSingle().execute(currentTime);
				}
				
			}
			
		});
		
	}

}

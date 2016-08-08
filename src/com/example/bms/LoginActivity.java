package com.example.bms;



import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.example.bean.UsersBean;
import com.example.dao.UsersDao;
import com.example.service.DownLoad;
import com.example.service.DownLoadReceive;
import com.example.service.SystemDownLoad;
import com.example.util.MD5;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * ��¼ҳ��
 * @author JMY
 *
 */
public class LoginActivity extends BaseActivity {

	//�û���¼
	private Button loginButton;
	private EditText userNameEdit;
	private EditText passWordEdit;
	
	//�汾���
	String url ="http://60.165.164.36:8085/AppVersion/update.xml";
	String versionCode = "18";
	DownLoadReceive receiver;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==100){
				
				System.out.println("����handler!");
				final Bundle bundle = (Bundle)msg.obj;				
			
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
				builder.setTitle("�汾����");
				builder.setMessage("��⵽�����°汾"+bundle.getString("name")+"���Ƿ����ظ���?\n\n"+bundle.getString("content"));
				builder.setPositiveButton("����",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String savePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "download";
								File fileDir = new File(savePath);
								if(!fileDir.exists()){
									fileDir.mkdirs();
								}				
								File fileList[] = fileDir.listFiles();
								for (File file2 : fileList){
									if(file2.getName().equals(bundle.getString("name", "BMS.apk"))){
										file2.delete();
									}
								}
								SystemDownLoad systemDownLoad = new SystemDownLoad(LoginActivity.this, 
										bundle.getString("url", "http://60.165.164.36:8085/AppVersion/BMS.apk"), 
										bundle.getString("name", "BMS.apk"),
										"/download/");
								systemDownLoad.downLoad();
								displayToast("�Ѽ������ض��У����Ժ󡭡�");
							}
						});
				builder.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
    			builder.show();
			}else if(msg.what==101){
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
				builder.setTitle("�汾����");
				builder.setMessage("��ǰ�������°汾��������¡�");
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
				});
				builder.show();
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		userNameEdit = (EditText)findViewById(R.id.userNameEdit);
		passWordEdit = (EditText)findViewById(R.id.passWordEdit);
		
		loginButton = (Button)findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(CheckLogin()){
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
				}
			}
			
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.login_menu_version) {
//			Intent intent = new Intent();
//			intent.setClass(LoginActivity.this, NetSettingsActivity.class);
//			startActivity(intent);
			if(CheckNetworkState()){
				displayToast("����У����Ժ󡭡�");
				receiver = new DownLoadReceive();
				IntentFilter intent = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
				registerReceiver(receiver, intent);
				new DownLoad(this, versionCode, handler,url).execute();
			}
			
		}else if(id == R.id.login_menu_init){
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, DataInitActivity.class);
			startActivity(intent);
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	public boolean CheckLogin(){
		String username = userNameEdit.getText().toString();
		String password = passWordEdit.getText().toString();
		if(StringUtils.isEmpty(username)){
			Toast.makeText(this,"�������û���", Toast.LENGTH_LONG).show();
			return false;
		}
		if(StringUtils.isEmpty(password)){
			Toast.makeText(this,"����������", Toast.LENGTH_LONG).show();
			return false;
		}
		String tp = password;
		UsersDao userDao = new UsersDao(this);
		MD5 md5 = new MD5();
		password = md5.getMD5ofStr(password);
		
		List<UsersBean> users = userDao.find(null, "username = ? AND password = ?", new String[]{username,password}, null, null, null, null);
		System.out.println(users);
		
		if(null != users && users.size() > 0){
			UsersBean userBean = users.get(0);
			Log.i("bms", userBean.getId());
			Log.i("bms",userBean.getUsername());
			Log.i("bms", userBean.getPassword());
			Log.i("bms", userBean.getDepCode());
			
			userDao.setCurrentUser(userBean);
			Toast.makeText(LoginActivity.this,userBean.getName()+", ��¼�ɹ�", Toast.LENGTH_LONG).show();
			return true;
//			NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//			Notification notification = new Notification(R.drawable.logo, "�ҵ�֪ͨ��", System.currentTimeMillis());
////			PendingIntent pi = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
//			notification.setLatestEventInfo(context, "�ҵ�֪ͨ����չ������", "�ҵ�֪ͨ��չ����ϸ����",null); 
//			mNotificationManager.notify(1, notification);
			
		}else{
			Toast.makeText(LoginActivity.this,"�û������������", Toast.LENGTH_LONG).show();
			return false;
		}
	
	}
}

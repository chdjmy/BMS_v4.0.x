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
 * 登录页面
 * @author JMY
 *
 */
public class LoginActivity extends BaseActivity {

	//用户登录
	private Button loginButton;
	private EditText userNameEdit;
	private EditText passWordEdit;
	
	//版本检测
	String url ="http://60.165.164.36:8085/AppVersion/update.xml";
	String versionCode = "18";
	DownLoadReceive receiver;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==100){
				
				System.out.println("测试handler!");
				final Bundle bundle = (Bundle)msg.obj;				
			
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
				builder.setTitle("版本更新");
				builder.setMessage("检测到版最新版本"+bundle.getString("name")+"，是否下载更新?\n\n"+bundle.getString("content"));
				builder.setPositiveButton("下载",
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
								displayToast("已加入下载队列，请稍后……");
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
			}else if(msg.what==101){
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
				builder.setTitle("版本更新");
				builder.setMessage("当前已是最新版本，无需更新。");
				builder.setPositiveButton("确定",
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
				displayToast("检测中，请稍后……");
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
			Toast.makeText(this,"请输入用户名", Toast.LENGTH_LONG).show();
			return false;
		}
		if(StringUtils.isEmpty(password)){
			Toast.makeText(this,"请输入密码", Toast.LENGTH_LONG).show();
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
			Toast.makeText(LoginActivity.this,userBean.getName()+", 登录成功", Toast.LENGTH_LONG).show();
			return true;
//			NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//			Notification notification = new Notification(R.drawable.logo, "我的通知栏", System.currentTimeMillis());
////			PendingIntent pi = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
//			notification.setLatestEventInfo(context, "我的通知栏标展开标题", "我的通知栏展开详细内容",null); 
//			mNotificationManager.notify(1, notification);
			
		}else{
			Toast.makeText(LoginActivity.this,"用户名或密码错误", Toast.LENGTH_LONG).show();
			return false;
		}
	
	}
}

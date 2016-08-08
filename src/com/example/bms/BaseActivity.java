package com.example.bms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Activity基类
 * @author JMY
 *
 */
public class BaseActivity extends Activity {

	// 检查网络状态,false代表无网络
	public boolean CheckNetworkState() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile =null;
		if(manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!=null){
			mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
					.getState();
		}
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		// 如果3G、wifi、2G等网络状态是连接的，则退出，否则显示提示信息
		if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
			return true;
		}
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			return true;
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
			builder.setTitle("提示");
			builder.setMessage("当前无网络连接，请检查网络设置？");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							Intent intent = new Intent(Settings.ACTION_SETTINGS);//系统设置界面
							startActivity(intent);
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
			return false;
		}
	}

	// Toast
	public void displayToast(String s) {
		Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, -20);
		toast.show();
	}
	
	public String getDepCode(){
		SharedPreferences currentUser = getSharedPreferences("currentUser", 0);
		String depCode = currentUser.getString("depCode", "");
		return depCode;
	}

}

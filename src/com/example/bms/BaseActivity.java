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
 * Activity����
 * @author JMY
 *
 */
public class BaseActivity extends Activity {

	// �������״̬,false����������
	public boolean CheckNetworkState() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile =null;
		if(manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!=null){
			mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
					.getState();
		}
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		// ���3G��wifi��2G������״̬�����ӵģ����˳���������ʾ��ʾ��Ϣ
		if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
			return true;
		}
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			return true;
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
			builder.setTitle("��ʾ");
			builder.setMessage("��ǰ���������ӣ������������ã�");
			builder.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							Intent intent = new Intent(Settings.ACTION_SETTINGS);//ϵͳ���ý���
							startActivity(intent);
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

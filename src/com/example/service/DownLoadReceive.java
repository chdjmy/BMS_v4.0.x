package com.example.service;

import java.io.File;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
/**
�����̳���BroadcastReceiver����Ϊ��apkϵͳ������ɺ����ᷢ��һ��DownloadManager.ACTION_DOWNLOAD_COMPLETE�㲥������ע��һ���㲥����Ȼ���������㲥
��������ɺ���ܹ�ʵ�֣�������װ���棬��ʾ����а�װ���ڽ��յ��㲥��ʵ�����´�����ܹ�ʵ��
������ʾ��װ
<!-- lang:Java -->
	private void installApk(String path,Context context){
		File file = new File(path);
		if(!file.exists()){
			Log.i("DownLoadReceive", "�ļ�������");
			return ;
		}
		// ͨ��Intent��װapk�ļ����Զ��򿪰�װ����
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//��������BroadcastReceive������activity������������ʽ��������ΪFLAG_ACTIVITY_NEW_TASK
		context.startActivity(intent);			
	}
 */
public class DownLoadReceive extends BroadcastReceiver{

	DownloadManager downloadManager;
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
			Log.i("DownLoadReceive", "�յ�����");
			long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
			
			downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
			
			SharedPreferences spf =  context.getSharedPreferences("download", Activity.MODE_PRIVATE);
			long download_id = spf.getLong("download_id",0);
			
			Query query = new Query();
			query.setFilterById(id);
			Cursor cursor = downloadManager.query(query);
			
			String path =null;
			if(cursor.moveToFirst()){
				int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);  
                if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                    String uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));                   
                    //�����ȡ��uriString �������ص�apk uri·�������������apk��·��������Ҫ��uriת�����ļ���·��
                    Uri uri = Uri.parse(uriString);
                    path = uri.getPath();
                }
				cursor.close();
			}
			if(id == download_id && path !=null){				
				installApk(path,context);	
			}
		}
	}

	private void installApk(String path,Context context){
		File file = new File(path);
		if(!file.exists()){
			Log.i("DownLoadReceive", "�ļ�������");
			return ;
		}
		// ͨ��Intent��װapk�ļ����Զ��򿪰�װ����
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//��������BroadcastReceive������activity������������ʽ��������ΪFLAG_ACTIVITY_NEW_TASK
		context.startActivity(intent);			
	}
}

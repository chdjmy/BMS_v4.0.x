package com.example.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.bean.SettingBean;
import com.example.common.dao.TemplateDao;

public class SettingDao extends TemplateDao<SettingBean>{
	
	
	public boolean updateSetting(SettingBean settingBean){
		SQLiteDatabase db = null;
		try {
			
			System.out.println(settingBean.getIp());
			System.out.println(settingBean.getPort());
			System.out.println(settingBean.getWeb_app());
			System.out.println(settingBean.getTimeout());
			
			db = this.getWritableDatabase();
			
			db.beginTransaction();
			
			db.delete("SETTINGS", null, null);
			
			ContentValues values = new ContentValues();
			values.put("ip", settingBean.getIp());
			values.put("port", settingBean.getPort());
			values.put("web_app", settingBean.getWeb_app());
			values.put("timeout", settingBean.getTimeout());
			
			db.insert("SETTINGS", null, values);
			
			db.setTransactionSuccessful();
			
			db.endTransaction();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(null != db){
				db.close();
			}
		}
		
		
		return false;
	}
	
	public SettingDao(Context context) {
		super(new SqlLiteHelper(context));
	}

}

package com.example.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.UsersBean;
import com.example.common.dao.TemplateDao;
import com.example.util.CommonHttpRequest;
import com.example.util.Uri;
import com.example.bean.SettingBean;

public class UsersDao extends TemplateDao<UsersBean> {

	private Context context;
	public UsersDao( Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}
	/**
	 * 保存当前登录操作者信息
	 * */
	public void setCurrentUser(UsersBean bean){
		SharedPreferences currentUser = context.getSharedPreferences("currentUser", 0);
		SharedPreferences.Editor editor = currentUser.edit();
		editor.putString("id", bean.getId());
		editor.putString("username", bean.getUsername());
		editor.putString("depCode", bean.getDepCode());
		editor.apply();
		return;
	}
	/**
	 * 获取当前登录操作者信息
	 * */
	public UsersBean getCurrentUser(){
		SharedPreferences currentUser = context.getSharedPreferences("currentUser", 0);
		String id = currentUser.getString("id", "");
//		String name = currentUser.getString("username", "");
//		String depCode = currentUser.getString("depCode", "");
		UsersBean bean = this.get(id);
		return bean;
	}
	
	public void init(SettingBean settingBean,final Handler handler){
		CommonHttpRequest.getSingle().request(context, settingBean, handler, Uri.Init.MSG_USERS_INFO, null);
	}
	public boolean add(JSONArray array){
		if(null != array){

			SQLiteDatabase db = null;
			
			try {
				
				db = this.getWritableDatabase();
				
				db.beginTransaction();
				
				db.delete("users", null, null);
				
				
				for(int i = 0 ; i < array.size() ; i++ ){
					JSONObject obj = array.getJSONObject(i);
					Integer id = obj.getInteger("id");
					String username = obj.getString("username");
					String password = obj.getString("password");
					String name = obj.getString("name");
					Integer company_id = obj.getInteger("companyId");
					String department = obj.getString("department");
					String telephone = obj.getString("telephone");
					String qq = obj.getString("qq");
					String msn = obj.getString("msn");
					String dep_code = obj.getString("depCode");
					String postion_id = obj.getString("postionId");
					String user_code = obj.getString("userCode");
					String sex = obj.getString("sex");
					String idNumber = obj.getString("idNumber");
					String birthday = obj.getString("birthday");
					String address = obj.getString("address");
					String state = obj.getString("state");
					String bak = obj.getString("bak");
					
					ContentValues values = new ContentValues();
					values.put("id", id);
					values.put("username", username);
					values.put("password", password);
					values.put("name", name);
					values.put("company_id", company_id);
					values.put("department", department);
					values.put("telephone", telephone);
					values.put("qq", qq);
					values.put("msn", msn);
					values.put("dep_code", dep_code);
					values.put("postion_id", postion_id);
					values.put("user_code", user_code);
					values.put("sex", sex);
					values.put("idNumber", idNumber);
					values.put("birthday", birthday);
					values.put("address", address);
					values.put("state", state);
					values.put("bak", bak);
					
					db.insert("users", null, values);
				}
				db.setTransactionSuccessful();
				
				db.endTransaction();
				
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(null != db){
					db.close();
				}
			}
		}
		
		return false;
	}
}

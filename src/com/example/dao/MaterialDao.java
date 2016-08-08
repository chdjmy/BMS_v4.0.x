package com.example.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.CulvertUsualExamBean;
import com.example.bean.MaterialBean;
import com.example.bean.SettingBean;
import com.example.common.dao.TemplateDao;
import com.example.util.CommonHttpRequest;
import com.example.util.Uri;

/**
 * 材料信息表
 * @author JMY
 *
 */
public class MaterialDao extends TemplateDao<MaterialBean> {

	private Context context;
	public MaterialDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}
	//获得最大的id
	public int getMaxId(){
		int idMax = 0;
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("material",new String[]{"MAX(material_id)"}, null, null, null, null, null);
			while (cursor.moveToNext()) {
				idMax = cursor.getInt(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return idMax;
	}
	
	/*
	 * 新增材料数据上传
	 */
	public void upload(SettingBean settingBean, final Handler handler) {

		List<MaterialBean> list = this.find(null, "is_upload=?",
					new String[] { "0" }, null, null, null, null);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				MaterialBean bean = list.get(i);
				String json = JSONObject.toJSONString(bean);
				CommonHttpRequest.getSingle().request(context, settingBean,
						handler, Uri.Upload.MSG_MATERIAL_INFO, json);
			}
		} else {
			Message msg = handler.obtainMessage();
			msg.what = Uri.Upload.MSG_MATERIAL_INFO;
			Bundle data = new Bundle();
			data.putBoolean(Uri.KEY_FLAG, true);
			data.putBoolean("data_flag", true);// 记录有没有数据
			data.putString(Uri.KEY_MSG, "无新增材料");
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	
	/*
	 * 要上传的材料的数量
	 */
	public int uploadNum() {

		List<MaterialBean> list = this.find(null, "is_upload=?",
					new String[] { "0" }, null, null, null, null);
		if (null != list && list.size() > 0) {
			return list.size();
		} 
		return 0;
	}
	
	// 上传后把上传标志位改为1
	public synchronized void updateUploadbyId(int id) {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("is_upload", "1");
			db.update("material", values, "is_upload=? AND material_id=?",
					new String[] { "0", id + "" });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}
	
	public void init(SettingBean settingBean,final Handler handler){
		CommonHttpRequest.getSingle().request(context, settingBean, handler, Uri.Init.MSG_MATERIAL_INFO, null);
	}
	
	public boolean add(JSONArray array){
		if(null != array){

			SQLiteDatabase db = null;
			
			try {
				
				db = this.getWritableDatabase();
				
				db.beginTransaction();
				
				db.delete("material", null, null);
				
				for(int i = 0 ; i < array.size() ; i++ ){
					JSONObject obj = array.getJSONObject(i);
					Integer material_id = obj.getInteger("materialId");
					String material_name = obj.getString("materialName");
					String material_unit = obj.getString("materialUnit");
					String bak = obj.getString("bak");
					String is_upload = obj.getString("isUpload");
					
					ContentValues values = new ContentValues();
					values.put("material_id", material_id);
					values.put("material_name", material_name);
					values.put("material_unit", material_unit);
					values.put("bak", bak);
					values.put("is_upload", is_upload);
					
					db.insert("material", null, values);
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

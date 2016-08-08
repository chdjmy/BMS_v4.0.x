package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.BridgeBean;
import com.example.bean.MaintainMaterialBean;
import com.example.bean.MaintainTaskBean;
import com.example.bean.MaterialBean;
import com.example.bean.SettingBean;
import com.example.common.dao.TemplateDao;
import com.example.util.CommonHttpRequest;
import com.example.util.Uri;

public class MaintainMaterialDao extends TemplateDao<MaintainMaterialBean> {

	private Context context;

	public MaintainMaterialDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;

	}

	// 获得最大的id
	public int getMaxId() {
		int idMax = 0;
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query("maintain_material",
					new String[] { "MAX(maintain_material_id)" }, null, null,
					null, null, null);
			while (cursor.moveToNext()) {
				idMax = cursor.getInt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return idMax;
	}

	// 根据维修计划id来查询维修材料
	public List<MaterialBean> queryMaterialByMaintainId(String id) {
		List<MaterialBean> list = new ArrayList<MaterialBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db
					.query("maintain_material mm,material m",
							new String[] { "mm.material_id", "m.material_name",
									"m.material_unit",
									"mm.material_planned_quantity,mm.material_finish_quantity" },
							"mm.maintain_task_id=? AND mm.material_id=m.material_id",
							new String[] { id }, null, null, null);
			while (cursor.moveToNext()) {
				MaterialBean bean = new MaterialBean();
				bean.setMaterialId(cursor.getString(0));
				bean.setMaterialName(cursor.getString(1));
				bean.setMaterialUnit(cursor.getString(2));
				bean.setMaterialPlannedQuantity(cursor.getDouble(3));
				bean.setMaterialFinishQuantity(cursor.getDouble(4));
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}

	// 根据维修计划id来删除维修材料
	public void deleteMaterialByMaintainId(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("maintain_material", "maintain_task_id=?",
				new String[] { id });
		db.close();
	}

	// 根据维修计划id来查询维修材料计划量和使用量
	public List<MaintainMaterialBean> queryMaintainMaterialByTaskId(String id) {
		List<MaintainMaterialBean> list = new ArrayList<MaintainMaterialBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query("maintain_material", new String[] {
					"maintain_material_id", "maintain_task_id", "material_id",
					"material_planned_quantity", "material_finish_quantity" },
					"maintain_task_id=?", new String[] { id }, null, null,
					"maintain_material_id asc");
			while (cursor.moveToNext()) {
				MaintainMaterialBean bean = new MaintainMaterialBean();
				bean.setMaintainMaterialId(cursor.getInt(0));
				bean.setMaintainTaskId(cursor.getInt(1));
				bean.setMaterialId(cursor.getInt(2));
				bean.setMaterialPlannedQuantity(cursor.getDouble(3));
				bean.setMaterialFinishQuantity(cursor.getDouble(4));
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}

	// 上传维修验收的材料
	public void uploadCheckMaterial(SettingBean settingBean,
			final Handler handler, int clientId, int serverId) {
		List<MaintainMaterialBean> list = this
				.queryMaintainMaterialByTaskId(clientId + "");
		if (null != list && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				MaintainMaterialBean bean = list.get(i);
				bean.setMaintainTaskId(serverId);
				bean.setMaterialId(getMaterialServerId(bean.getMaterialId()));
				list.set(i, bean);
			}
			String json = JSONObject.toJSONString(list);
			CommonHttpRequest.getSingle().request(context, settingBean,
					handler, Uri.Upload.MSG_MAINTAINCHECK_MATERIAL, json);
		}
	}
	// 上传维修计划的材料
	public void uploadPlanMaterial(SettingBean settingBean,
			final Handler handler, int clientId, int serverId) {
		List<MaintainMaterialBean> list = this
				.queryMaintainMaterialByTaskId(clientId + "");
		if (null != list && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				MaintainMaterialBean bean = list.get(i);
				bean.setMaintainTaskId(serverId);
				bean.setMaterialId(getMaterialServerId(bean.getMaterialId()));
				list.set(i, bean);
			}
			String json = JSONObject.toJSONString(list);
			CommonHttpRequest.getSingle().request(context, settingBean,
					handler, Uri.Upload.MSG_MAINTAINPLAN_MATERIAL, json);
		}
	}
		
	//获得ID映射，如果没有映射，则表示客户端和服务端相同ID
	public int getMaterialServerId(int clientId){
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query("IDMAPS", new String[] {
					"server_id" },
					"client_id=? AND data_type=3", new String[] { clientId+"" }, null, null,
					"server_id desc","0,1");
			while (cursor.moveToNext()) {
				return cursor.getInt(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return clientId;
	}
	
	public void init(SettingBean settingBean,final Handler handler){
		CommonHttpRequest.getSingle().request(context, settingBean, handler, Uri.Init.MSG_MAINTAINMATERIAL_INFO, null);
	}
	
	public boolean add(JSONArray array){
		if(null != array){

			SQLiteDatabase db = null;
			
			try {
				
				db = this.getWritableDatabase();
				
				db.beginTransaction();
				
				db.delete("maintain_material", null, null);
				
				
				for(int i = 0 ; i < array.size() ; i++ ){
					JSONObject obj = array.getJSONObject(i);
					Integer maintainMaterialId = obj.getInteger("maintainMaterialId");
					Integer maintainTaskId = obj.getInteger("maintainTaskId");
					Integer materialId = obj.getInteger("materialId");
					Double materialPlannedQuantity = obj.getDouble("materialPlannedQuantity");
					Double materialFinishQuantity = obj.getDouble("materialFinishQuantity");
					
					ContentValues values = new ContentValues();
					values.put("maintain_material_id", maintainMaterialId);
					values.put("maintain_task_id", maintainTaskId);
					values.put("material_id", materialId);
					values.put("material_planned_quantity", materialPlannedQuantity);
					values.put("material_finish_quantity", materialFinishQuantity);
					
					db.insert("maintain_material", null, values);
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

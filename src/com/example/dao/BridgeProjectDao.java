package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.BridgeBean;
import com.example.bean.BridgeProjectBean;
import com.example.bean.SettingBean;
import com.example.common.dao.TemplateDao;
import com.example.util.CommonHttpRequest;
import com.example.util.Uri;

public class BridgeProjectDao extends TemplateDao<BridgeProjectBean>{
	private Context context;
	public BridgeProjectDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}
	
	//分页查找
	public List<BridgeProjectBean>  findByPage(int page,String depCode){
		List<BridgeProjectBean> list = new ArrayList<BridgeProjectBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if(depCode.equals("0000")){
				cursor = db.query("project p, bridge b",new String[]{"p.project_id,b.bridge_id","b.bridge_code","b.bridge_name","p.project_date","p.budget_cost"}, "project_id>? AND project_id<=? AND p.bridge_code=b.bridge_code", new String[]{String.valueOf(page),String.valueOf(page+30)}, null, null, null);
			}else{
				cursor = db.query("project p, bridge b",new String[]{"p.project_id,b.bridge_id","b.bridge_code","b.bridge_name","p.project_date","p.budget_cost"}, "p.bridge_code=b.bridge_code AND b.dep_code=?", new String[]{depCode}, null,null, "p.project_id asc",page+",30");
			}
			while (cursor.moveToNext()) {
				BridgeProjectBean bean = new BridgeProjectBean();
				bean.setProjectId(cursor.getInt(0));
				bean.setBridgeId(cursor.getString(1));
				bean.setBridgeCode(cursor.getString(2));
				bean.setBridgeName(cursor.getString(3));
				bean.setProjectDate(cursor.getString(4));
				bean.setBudgetCost(cursor.getDouble(5));
				list.add(bean);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}	
		
	//按照ID查找	
	public BridgeProjectBean queryById(int id){
		BridgeProjectBean bean =this.get(id);
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("bridge",new String[]{"bridge_name"}, "bridge_code=?", new String[]{bean.getBridgeCode()}, null, null, null);
			while (cursor.moveToNext()) {
				bean.setBridgeName(cursor.getString(0));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		
		return bean;
	}	
	
	//按名字查找
	public List<BridgeProjectBean> findByName(String name,String depCode){
		List<BridgeProjectBean> list = new ArrayList<BridgeProjectBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if(depCode.equals("0000")){
				cursor = db.query("project p, bridge b",new String[]{"p.project_id,b.bridge_id","b.bridge_code","b.bridge_name","p.project_date","p.budget_cost"}, "p.bridge_code=b.bridge_code AND b.bridge_name LIKE '%"+name+"%'", null, null, null, null);
			}else{
				cursor = db.query("project p, bridge b",new String[]{"p.project_id,b.bridge_id","b.bridge_code","b.bridge_name","p.project_date","p.budget_cost"}, "p.bridge_code=b.bridge_code AND b.bridge_name LIKE '%"+name+"%' AND b.dep_code=?", new String[]{depCode}, null, null, null);
			}
			while (cursor.moveToNext()) {
				BridgeProjectBean bean = new BridgeProjectBean();
				bean.setProjectId(cursor.getInt(0));
				bean.setBridgeId(cursor.getString(1));
				bean.setBridgeCode(cursor.getString(2));
				bean.setBridgeName(cursor.getString(3));
				bean.setProjectDate(cursor.getString(4));
				bean.setBudgetCost(cursor.getDouble(5));
				list.add(bean);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}
	
	//按条码查找
	public List<BridgeProjectBean> findByBar(String bar,String depCode){
		List<BridgeProjectBean> list = new ArrayList<BridgeProjectBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if(depCode.equals("0000")){
				cursor = db.query("project p, bridge b",new String[]{"p.project_id,b.bridge_id","b.bridge_code","b.bridge_name","p.project_date","p.budget_cost"}, "p.bridge_code=b.bridge_code AND b.bar_code = ?", new String[]{bar}, null, null, null);
			}else{
				cursor = db.query("project p, bridge b",new String[]{"p.project_id,b.bridge_id","b.bridge_code","b.bridge_name","p.project_date","p.budget_cost"}, "p.bridge_code=b.bridge_code AND b.bar_code = ?  AND b.dep_code=?", new String[]{bar,depCode}, null, null, null);
			}
			while (cursor.moveToNext()) {
				BridgeProjectBean bean = new BridgeProjectBean();
				bean.setProjectId(cursor.getInt(0));
				bean.setBridgeId(cursor.getString(1));
				bean.setBridgeCode(cursor.getString(2));
				bean.setBridgeName(cursor.getString(3));
				bean.setProjectDate(cursor.getString(4));
				bean.setBudgetCost(cursor.getDouble(5));
				list.add(bean);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}
	
	public void init(SettingBean settingBean,final Handler handler){
		CommonHttpRequest.getSingle().request(context, settingBean, handler, Uri.Init.MSG_PROJECT_INFO, null);
	}
	
	public boolean add(JSONArray array){
		if(null != array){

			SQLiteDatabase db = null;
			
			try {
				
				db = this.getWritableDatabase();
				
				db.beginTransaction();
				
				db.delete("project", null, null);
				
				for(int i = 0 ; i < array.size() ; i++ ){
					JSONObject obj = array.getJSONObject(i);
					
					Integer projectId = obj.getInteger("projectId");
					
					String bridgeCode = obj.getString("bridgeCode");
					String projectDate = obj.getString("projectDate");
					Double budgetCost = obj.getDouble("budgetCost");
					Double constructionCost = obj.getDouble("constructionCost");
					Double managementCost = obj.getDouble("managementCost");
					
					Double supervisorCost = obj.getDouble("supervisorCost");
					Double designCost = obj.getDouble("designCost");
					Double examineCost = obj.getDouble("examineCost");
					Double detectionCost = obj.getDouble("detectionCost");
					Double sumCost = obj.getDouble("sumCost");
					
					String buildUnit = obj.getString("buildUnit");
					String designUnit = obj.getString("designUnit");
					String constructionUnit = obj.getString("constructionUnit");
					String supervisionUnit = obj.getString("supervisionUnit");
					String checkType = obj.getString("checkType");
					
					String constructType = obj.getString("constructType");
					String constructReason = obj.getString("constructReason");
					String projectRange = obj.getString("projectRange");
					String financeSource = obj.getString("financeSource");
					String qualityEvaluation = obj.getString("qualityEvaluation");
					String replayName = obj.getString("replayName");
					
					String replayNum = obj.getString("replayNum");
					String replayDate = obj.getString("replayDate");
					String authorityName = obj.getString("authorityName");
					String authorityNum = obj.getString("authorityNum");
					String makeProjectPeople = obj.getString("makeProjectPeople");
					
					String makeProjectDate = obj.getString("makeProjectDate");
					String startDate = obj.getString("startDate");
					String finishDate = obj.getString("finishDate");
					String acceptanceDate = obj.getString("acceptanceDate");
					String completionDate = obj.getString("completionDate");
					
					String tenderDate = obj.getString("tenderDate");
					String projectReport = obj.getString("projectReport");
					String partProjectReport = obj.getString("partProjectReport");
					String ourselfAccept = obj.getString("ourselfAccept");
					String supervisionAccept = obj.getString("supervisionAccept");
					
					String qualityDetection = obj.getString("qualityDetection");
					String completionAccept = obj.getString("completionAccept");
					
					String isSubmit = obj.getString("isSubmit");
					String submitPeople = obj.getString("submitPeople");
					String submitDate = obj.getString("submitDate");
					
					
					ContentValues values = new ContentValues();
					values.put("project_id", projectId);
					
					values.put("bridge_code", bridgeCode);
					values.put("project_date", projectDate);
					values.put("budget_cost", budgetCost);
					values.put("construction_cost", constructionCost);
					values.put("management_cost", managementCost);
					
					values.put("supervisor_cost", supervisorCost);
					values.put("design_cost", designCost);
					values.put("examine_cost", examineCost);
					values.put("detection_cost", detectionCost);
					values.put("sum_cost", sumCost);
					
					values.put("build_unit", buildUnit);
					values.put("design_unit", designUnit);
					values.put("construction_unit", constructionUnit);
					values.put("supervision_unit", supervisionUnit);
					values.put("check_type", checkType);
					
					values.put("construct_type", constructType);
					values.put("construct_reason", constructReason);
					values.put("project_range", projectRange);
					values.put("finance_source", financeSource);
					values.put("quality_evaluation", qualityEvaluation);
					values.put("replay_name", replayName);
					
					values.put("replay_num", replayNum);
					values.put("replay_date", replayDate);
					values.put("authority_name", authorityName);
					values.put("authority_num", authorityNum);
					values.put("make_project_people", makeProjectPeople);
					
					values.put("make_project_date", makeProjectDate);
					values.put("start_date", startDate);
					values.put("finish_date", finishDate);
					values.put("acceptance_date", acceptanceDate);
					values.put("completion_date", completionDate);
					
					values.put("tender_date", tenderDate);
					values.put("project_report", projectReport);
					values.put("part_project_report", partProjectReport);
					values.put("ourself_accept", ourselfAccept);
					values.put("supervision_accept", supervisionAccept);
					
					values.put("quality_detection", qualityDetection);
					values.put("completion_accept", completionAccept);
					values.put("is_submit", isSubmit);
					values.put("submit_people", submitPeople);
					values.put("submit_date", submitDate);
					
					db.insert("project", null, values);
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

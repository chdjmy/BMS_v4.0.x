package com.example.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
import com.example.bean.CulvertUsualExamBean;
import com.example.bean.MaintainTaskBean;
import com.example.bean.SettingBean;
import com.example.common.dao.TemplateDao;
import com.example.http.AsyncHttpRequest;
import com.example.http.FileUploadRequest;
import com.example.http.OnUploadProcessListener;
import com.example.http.RequestParams;
import com.example.http.RequestResultCallback;
import com.example.http.ThreadPool;
import com.example.util.CommonHttpRequest;
import com.example.util.MD5;
import com.example.util.Uri;

public class MaintainTaskDao extends TemplateDao<MaintainTaskBean> {

	private Context context;

	public MaintainTaskDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}

	// 分页查找
	public List<MaintainTaskBean> findByPage(int page, String depCode, int type) {
		List<MaintainTaskBean> list = new ArrayList<MaintainTaskBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();

			Cursor cursor = null;
			if (depCode.equals("0000")) {
				switch (type) {
				case 1://维修计划
					cursor = db
							.query("maintain_task m,bridge b",
									new String[] { "maintain_task_id",
											"maintain_code", "bridge_name",
											"submit_date", "planner","isupload","isverify","isold","ischecked"},
									"maintain_task_id>? AND maintain_task_id<=?  AND m.bridge_id = b.bridge_id AND (m.isverify=? OR m.isverify is null)",
									new String[] { String.valueOf(page),
											String.valueOf(page + 30), "0" },
									null, null, null);
					break;
				case 2://维修审核
					break;
				case 3://维修任务
					cursor = db
							.query("maintain_task m,bridge b",
									new String[] { "maintain_task_id",
											"maintain_code", "bridge_name",
											"submit_date", "planner","isupload","isverify","isold","ischecked"},
									"maintain_task_id>? AND maintain_task_id<=?  AND m.bridge_id = b.bridge_id AND m.isverify=? AND (m.ischecked=? OR m.ischecked is null OR m.ischecked=?)",
									new String[] { String.valueOf(page),
											String.valueOf(page + 30), "2","0","" },
									null, null, null);
					break;
				case 4://维修验收
					cursor = db
							.query("maintain_task m,bridge b",
									new String[] { "maintain_task_id",
											"maintain_code", "bridge_name",
											"submit_date", "planner","isupload","isverify","isold","ischecked"},
									"maintain_task_id>? AND maintain_task_id<=?  AND m.bridge_id = b.bridge_id AND m.isverify=?",
									new String[] { String.valueOf(page),
											String.valueOf(page + 30), "2" },
									null, null, null);
					break;
				}

			} else {
				switch (type) {
				case 1://维修计划
					cursor = db.query("maintain_task m,bridge b", new String[] {
							"maintain_task_id", "maintain_code", "bridge_name",
							"submit_date", "planner","isupload","isverify","isold","ischecked" },
							"m.bridge_id = b.bridge_id AND b.dep_code=? AND (m.isverify=? OR m.isverify is null)",
							new String[] { depCode,"0" }, null, null,
							"m.maintain_task_id asc", page + ",30");
					break;
				case 2://维修审核
					break;
				case 3://维修任务
					cursor = db.query("maintain_task m,bridge b", new String[] {
							"maintain_task_id", "maintain_code", "bridge_name",
							"submit_date", "planner","isupload","isverify","isold","ischecked" },
							"m.bridge_id = b.bridge_id AND b.dep_code=? AND m.isverify=? AND (m.ischecked=? OR m.ischecked is null OR m.ischecked=?) ",
							new String[] { depCode,"2" ,"0",""}, null, null,
							"m.maintain_task_id asc", page + ",30");
					break;
				case 4://维修验收
					cursor = db.query("maintain_task m,bridge b", new String[] {
							"maintain_task_id", "maintain_code", "bridge_name",
							"submit_date", "planner","isupload","isverify","isold","ischecked" },
							"m.bridge_id = b.bridge_id AND b.dep_code=? AND m.isverify=?",
							new String[] { depCode,"2" }, null, null,
							"m.maintain_task_id asc", page + ",30");
					break;
				}
			}

			while (cursor.moveToNext()) {
				MaintainTaskBean bean = new MaintainTaskBean();
				bean.setMaintainTaskId(cursor.getInt(0));
				bean.setMaintainCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
				bean.setSubmitDate(cursor.getString(3));
				bean.setPlanner(cursor.getString(4));
				bean.setIsupload(StringUtils.isNotEmpty(cursor.getString(5))?cursor.getString(5):"1");//null为已上传
				bean.setIsverify(StringUtils.isNotEmpty(cursor.getString(6))?cursor.getString(6):"0");//null为未审核
				bean.setIsold(StringUtils.isNotEmpty(cursor.getString(7))?cursor.getString(7):"1");//null为正常
				bean.setIschecked(StringUtils.isNotEmpty(cursor.getString(8))?cursor.getString(8):"0");//null为未验收
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

	// 按名称查找
	public List<MaintainTaskBean> findByBirdgeName(String name, String depCode) {

		List<MaintainTaskBean> list = new ArrayList<MaintainTaskBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if (depCode.equals("0000")) {
				cursor = db.query("maintain_task m,bridge b", new String[] {
						"maintain_task_id", "maintain_code", "bridge_name",
						"submit_date", "planner" },
						"m.bridge_id = b.bridge_id AND b.bridge_name LIKE '%"
								+ name + "%'", null, null, null, null);
			} else {
				cursor = db.query("maintain_task m,bridge b", new String[] {
						"maintain_task_id", "maintain_code", "bridge_name",
						"submit_date", "planner" },
						"m.bridge_id = b.bridge_id AND b.bridge_name LIKE '%"
								+ name + "%' AND b.dep_code=?",
						new String[] { depCode }, null, null, null);
			}
			while (cursor.moveToNext()) {
				MaintainTaskBean bean = new MaintainTaskBean();
				bean.setMaintainTaskId(cursor.getInt(0));
				bean.setMaintainCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
				bean.setSubmitDate(cursor.getString(3));
				bean.setPlanner(cursor.getString(4));
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

	// 按条码查找
	public List<MaintainTaskBean> findByBridgeBar(String bar, String depCode) {

		List<MaintainTaskBean> list = new ArrayList<MaintainTaskBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if (depCode.equals("0000")) {
				cursor = db.query("maintain_task m,bridge b", new String[] {
						"maintain_task_id", "maintain_code", "bridge_name",
						"submit_date", "planner" },
						"m.bridge_id = b.bridge_id AND b.bar_code=?",
						new String[] { bar }, null, null, null);
			} else {
				cursor = db
						.query("maintain_task m,bridge b",
								new String[] { "maintain_task_id",
										"maintain_code", "bridge_name",
										"submit_date", "planner" },
								"m.bridge_id = b.bridge_id AND b.bar_code=? AND b.dep_code=?",
								new String[] { bar, depCode }, null, null, null);
			}
			while (cursor.moveToNext()) {
				MaintainTaskBean bean = new MaintainTaskBean();
				bean.setMaintainTaskId(cursor.getInt(0));
				bean.setMaintainCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
				bean.setSubmitDate(cursor.getString(3));
				bean.setPlanner(cursor.getString(4));
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

	// 获得最大的id
	public int getMaxId() {
		int idMax = 0;
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query("maintain_task",
					new String[] { "MAX(maintain_task_id)" }, null, null, null,
					null, null);
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

	// 获得上传数据
	public List<MaintainTaskBean> findPlanToUpload(String depCode) {
		List<MaintainTaskBean> list = new ArrayList<MaintainTaskBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();

			Cursor cursor = null;
			if (depCode.equals("0000")) {
				cursor = db.query("maintain_task m,bridge b", new String[] {
						"maintain_task_id", "maintain_code", "bridge_name",
						"submit_date", "planner" },
						"m.isupload=? AND m.bridge_id = b.bridge_id",
						new String[] { "0" }, null, null, null);
			} else {
				cursor = db
						.query("maintain_task m,bridge b",
								new String[] { "maintain_task_id",
										"maintain_code", "bridge_name",
										"submit_date", "planner" },
								"m.bridge_id = b.bridge_id AND m.isupload=? AND b.dep_code=?",
								new String[] { "0", depCode }, null, null, null);
			}

			while (cursor.moveToNext()) {
				MaintainTaskBean bean = new MaintainTaskBean();
				bean.setMaintainTaskId(cursor.getInt(0));
				bean.setMaintainCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
				bean.setSubmitDate(cursor.getString(3));
				bean.setPlanner(cursor.getString(4));
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

	public List<MaintainTaskBean> findCheckToUpload(String depCode) {
		List<MaintainTaskBean> list = new ArrayList<MaintainTaskBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();

			Cursor cursor = null;
			if (depCode.equals("0000")) {
				cursor = db.query("maintain_task m,bridge b", new String[] {
						"maintain_task_id", "maintain_code", "bridge_name",
						"submit_date", "planner" },
						"m.bridge_id = b.bridge_id AND m.ischecked=?",
						new String[] { "2" }, null, null, null);
			} else {
				cursor = db
						.query("maintain_task m,bridge b",
								new String[] { "maintain_task_id",
										"maintain_code", "bridge_name",
										"submit_date", "planner" },
								"m.bridge_id = b.bridge_id AND m.ischecked=? AND b.dep_code=?",
								new String[] { "2", depCode }, null, null, null);
			}

			while (cursor.moveToNext()) {
				MaintainTaskBean bean = new MaintainTaskBean();
				bean.setMaintainTaskId(cursor.getInt(0));
				bean.setMaintainCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
				bean.setSubmitDate(cursor.getString(3));
				bean.setPlanner(cursor.getString(4));
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
	//上传维修验收
	public void uploadCheck(SettingBean settingBean, final Handler handler,String depCode){
		List<MaintainTaskBean> list = null;
		if(depCode.equals("0000")){
			list = this.find(null, "ischecked=?",
					new String[] { "2" }, null, null, null, null);
		}else{
			list = this.find(null, "ischecked=? AND dep_code=?",
					new String[] { "2" ,depCode}, null, null, null, null);
		}
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				MaintainTaskBean bean = list.get(i);
				String json = JSONObject.toJSONString(bean);
				CommonHttpRequest.getSingle().request(context, settingBean,
						handler, Uri.Upload.MSG_MAINTAINCHECK_INFO, json);
			}
		} else {
			Message msg = handler.obtainMessage();
			msg.what = Uri.Upload.MSG_MAINTAINCHECK_INFO;
			Bundle data = new Bundle();
			data.putBoolean(Uri.KEY_FLAG, true);
			data.putBoolean("data_flag", true);// 记录有没有数据
			data.putString(Uri.KEY_MSG, "无新增维修验收记录");
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	//维修验收上传数据的数量
	public int uploadCheckNum(String depCode){
		List<MaintainTaskBean> list = null;
		if(depCode.equals("0000")){
			list = this.find(null, "ischecked=?",
					new String[] { "2" }, null, null, null, null);
		}else{
			list = this.find(null, "ischecked=? AND dep_code=?",
					new String[] { "2" ,depCode}, null, null, null, null);
		}
		if (null != list && list.size() > 0) {
			return list.size();
		}
		return 0;
	}
	
	//上传维修计划
	public void uploadPlan(SettingBean settingBean, final Handler handler,String depCode){
		List<MaintainTaskBean> list = null;
		if(depCode.equals("0000")){
			list = this.find(null, "isupload=?",
					new String[] { "0" }, null, null, null, null);
		}else{
			list = this.find(null, "isupload=? AND dep_code=?",
					new String[] { "0" ,depCode}, null, null, null, null);
		}
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				MaintainTaskBean bean = list.get(i);
				String json = JSONObject.toJSONString(bean);
				CommonHttpRequest.getSingle().request(context, settingBean,
						handler, Uri.Upload.MSG_MAINTAINPLAN_INFO, json);
			}
		} else {
			Message msg = handler.obtainMessage();
			msg.what = Uri.Upload.MSG_MAINTAINPLAN_INFO;
			Bundle data = new Bundle();
			data.putBoolean(Uri.KEY_FLAG, true);
			data.putBoolean("data_flag", true);// 记录有没有数据
			data.putString(Uri.KEY_MSG, "无新增维修计划记录");
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	//维修验收上传数据的数量
	public int uploadPlanNum(String depCode){
		List<MaintainTaskBean> list = null;
		if(depCode.equals("0000")){
			list = this.find(null, "isupload=?",
					new String[] { "0" }, null, null, null, null);
		}else{
			list = this.find(null, "isupload=? AND dep_code=?",
					new String[] { "0" ,depCode}, null, null, null, null);
		}
		if (null != list && list.size() > 0) {
			return list.size();
		}
		return 0;
	}
	
	// 上传后把验收标志位从2改为1
	public synchronized void updateUploadCheckbyId(int id) {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("ischecked", "1");
			db.update("maintain_task", values, "maintain_task_id=?",
					new String[] {id + "" });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}
	
	// 上传后把验收标志位从2改为1
	public synchronized void updateUploadPlanbyId(int id) {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("isupload", "1");
			db.update("maintain_task", values, "maintain_task_id=?",
					new String[] {id + "" });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}
	
	// 上传维修验收照片
	public synchronized void uploadPhotoByPicPath(
			final SettingBean settingBean, final Handler handler,
			final String picPath, final int serverId) {
		// 异步上传照片
		AsyncHttpRequest currentTime = new AsyncHttpRequest(Uri.getUri(
				settingBean.getIp(), settingBean.getPort(),
				settingBean.getWeb_app(), Uri.Init.GET_CURRENTTIME), null,
				new RequestResultCallback() {
					@Override
					public void onSuccess(Object paramObject) {
						// url
						String url = Uri.getUri(settingBean.getIp(),settingBean.getPort(),settingBean.getWeb_app(),Uri.Upload.MAINTAINCHECK_PHOTO);
						MD5 md5 = new MD5();
						Map<String, String> parameters = new HashMap<String, String>();
						parameters.put("time", String.valueOf(paramObject));
						parameters.put("v",md5.getMD5ofStr(Uri.KEY + " "+ String.valueOf(paramObject)));
						parameters.put("serverId", serverId + "");
						parameters.put("dataType", "3");
						FileUploadRequest request = new FileUploadRequest(url,
								picPath, parameters,
								new OnUploadProcessListener() {
									@Override
									public void onUploadDone(int responseCode,
											String message) {
										if (null != handler) {
//											Message msg = handler
//													.obtainMessage();
//											Bundle data = new Bundle();
//											data.putBoolean(Uri.KEY_FLAG, true);
//											data.putString(Uri.KEY_MSG, message);
//											msg.setData(data);
//											msg.what = Uri.Upload.MSG_CULVERTUSUALEXAM_PHOTO;
//
//											handler.sendMessage(msg);
										}
									}

									@Override
									public void onUploadProcess(int uploadSize) {
										// TODO Auto-generated method stub

									}

									@Override
									public void initUpload(int fileSize) {
										// TODO Auto-generated method stub

									}

								});

						ThreadPool.getSingle().execute(request);
					}

					@Override
					public void onFail(String paramException) {
						if (null != handler) {
//							Message msg = handler.obtainMessage();
//							Bundle data = new Bundle();
//							data.putBoolean(Uri.KEY_FLAG, false);
//							data.putString(Uri.KEY_MSG, paramException);
//							msg.setData(data);
//							msg.what = Uri.Upload.MSG_BRIDGEUSUALEXAM_PHOTO;
//							handler.sendMessage(msg);
						}
					}
				});
		ThreadPool.getSingle().execute(currentTime);
	}

	// 根据Id查询经常性检查的照片地址
	public String queryPhotoAddrById(int id) {
		String addr = null;
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query("maintain_task",
					new String[] { "photo_addr" }, "maintain_task_id=?",
					new String[] { id + "" }, null, null, null);
			while (cursor.moveToNext()) {
				addr = cursor.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return addr;
	}
	//上传经常性检查照片
	public void uploadPhoto(SettingBean settingBean, final Handler handler,int clientId,int serverId){
		String addr = queryPhotoAddrById(clientId);
		if(StringUtils.isNotEmpty(addr)){
			String[] photos = addr.split(";");
			for(int i=0;i<photos.length;i++){
				uploadPhotoByPicPath(settingBean,handler,photos[i], serverId);
			}
		}
	}
	


	// 获得页数
	public void initCount(SettingBean settingBean, final Handler handler) {
		CommonHttpRequest.getSingle().request(context, settingBean, handler,
				Uri.Init.MSG_MAINTAIN_INFO, null);
	}

	// 分页初始化
	public synchronized void init(final SettingBean settingBean,
			final Handler handler, final int pageNum) {
		System.out.println("---------init Maintain ------");
		System.out.println("pageNum = " + pageNum);
		AsyncHttpRequest currentTime = new AsyncHttpRequest(Uri.getUri(
				settingBean.getIp(), settingBean.getPort(),
				settingBean.getWeb_app(), Uri.Init.GET_CURRENTTIME), null,
				new RequestResultCallback() {
					@Override
					public void onSuccess(Object paramObject) {
						MD5 md5 = new MD5();
						List<RequestParams> params = new ArrayList<RequestParams>();
						// long currentTime = System.currentTimeMillis();
						params.add(new RequestParams("time", String
								.valueOf(paramObject)));
						params.add(new RequestParams("v", md5
								.getMD5ofStr(Uri.KEY + " "
										+ String.valueOf(paramObject))));
						params.add(new RequestParams("pageNum", String
								.valueOf(pageNum)));
						// url
						String url = Uri.getUri(settingBean.getIp(),
								settingBean.getPort(),
								settingBean.getWeb_app(),
								Uri.Init.MAINTAIN_INFO_PAGE);
						AsyncHttpRequest request = new AsyncHttpRequest(url,
								params, new RequestResultCallback() {
									@Override
									public void onSuccess(Object paramObject) {
										if (null != handler) {
											Message msg = handler
													.obtainMessage();
											Bundle data = new Bundle();
											data.putBoolean(Uri.KEY_FLAG, true);
											data.putString(Uri.KEY_MSG,
													paramObject.toString());
											msg.setData(data);
											msg.what = Uri.Init.MSG_MAINTAIN_INFO_PAGE;

											handler.sendMessage(msg);
										}
									}

									@Override
									public void onFail(String paramException) {
										if (null != handler) {
											Message msg = handler
													.obtainMessage();
											Bundle data = new Bundle();
											data.putBoolean(Uri.KEY_FLAG, false);
											data.putString(Uri.KEY_MSG,
													paramException);
											msg.setData(data);
											msg.what = Uri.Init.MSG_MAINTAIN_INFO_PAGE;
											handler.sendMessage(msg);
										}
										// Toast.makeText(context,paramException,
										// Toast.LENGTH_LONG).show();
									}

								});
						ThreadPool.getSingle().execute(request);
					}

					@Override
					public void onFail(String paramException) {
						if (null != handler) {
							Message msg = handler.obtainMessage();
							Bundle data = new Bundle();
							data.putBoolean(Uri.KEY_FLAG, false);
							data.putString(Uri.KEY_MSG, paramException);
							msg.setData(data);
							msg.what = Uri.Init.MSG_MAINTAIN_INFO_PAGE;
							handler.sendMessage(msg);
						}
					}
				});
		ThreadPool.getSingle().execute(currentTime);

	}
	
	public boolean add(JSONArray array){
		if(null != array){

			SQLiteDatabase db = null;
			
			try {
				
				db = this.getWritableDatabase();
				
				db.beginTransaction();
				
			//	db.delete("maintain_task", null, null);
				
				
				for(int i = 0 ; i < array.size() ; i++ ){
					JSONObject obj = array.getJSONObject(i);
					Integer maintainTaskId = obj.getInteger("maintainTaskId");
					String maintainCode = obj.getString("maintainCode");
					String taskType = obj.getString("taskType");
					Integer bridgeId = obj.getInteger("bridgeId");
					String stake = obj.getString("stake");
					String diseaseName = obj.getString("diseaseName");
					String diseaseLocation = obj.getString("diseaseLocation");
					Double diseaseQuantity = obj.getDouble("diseaseQuantity");
					String diseaseUnit = obj.getString("diseaseUnit");
					String maintainPlan = obj.getString("maintainPlan");
					String planner = obj.getString("planner");
					String submitDate = obj.getString("submitDate");
					String isupload = obj.getString("isupload");
					String bak = obj.getString("bak");
					String isverify = obj.getString("isverify");
					String verifier = obj.getString("verifier");
					String verifytime = obj.getString("bverifytimeak");
					
					String maintainDate = obj.getString("maintainDate");
					String isold = obj.getString("isold");
					String verifySuggestion = obj.getString("verifySuggestion");
					String departmentName = obj.getString("departmentName");
					String checkTime = obj.getString("checkTime");
					String workProjectQuality = obj.getString("workProjectQuality");
					String workAreaLayout = obj.getString("workAreaLayout");
					String headman = obj.getString("headman");
					String member = obj.getString("member");
					String isgrade = obj.getString("isgrade");
					String checkPic = obj.getString("checkPic");
					String maintainName = obj.getString("maintainName");
					String isfile = obj.getString("isfile");
					String fileDate = obj.getString("fileDate");
					String maintainProblem = obj.getString("maintainProblem");
					
					String treatmentMeasure = obj.getString("treatmentMeasure");
					String treatmentEffect = obj.getString("treatmentEffect");
					String treatmentDate = obj.getString("treatmentDate");
					String treatmentContent = obj.getString("treatmentContent");
					String qualityEvaluation = obj.getString("qualityEvaluation");
					String leader = obj.getString("leader");
					String minister = obj.getString("minister");
					String filePeople = obj.getString("filePeople");
					String photoAddr = obj.getString("photoAddr");
					String ischecked = obj.getString("ischecked");
					
					ContentValues values = new ContentValues();
					values.put("maintain_task_id", maintainTaskId);
					values.put("maintain_code", maintainCode);
					values.put("task_type", taskType);
					values.put("bridge_id", bridgeId);
					values.put("stake", stake);
					values.put("disease_name", diseaseName);
					values.put("disease_location", diseaseLocation);
					values.put("disease_quantity", diseaseQuantity);
					values.put("disease_unit", diseaseUnit);
					values.put("maintain_plan", maintainPlan);
					values.put("planner", planner);
					values.put("submit_date", submitDate);
					values.put("isupload", isupload);
					values.put("bak", bak);
					values.put("isverify", isverify);
					values.put("verifier", verifier);
					values.put("verifytime", verifytime);
					values.put("maintain_date", maintainDate);
					values.put("isold", isold);
					values.put("verify_suggestion", verifySuggestion);
					values.put("department_name", departmentName);
					values.put("check_time", checkTime);
					values.put("work_project_quality", workProjectQuality);
					values.put("work_area_layout", workAreaLayout);
					values.put("headman", headman);
					values.put("member", member);
					values.put("isgrade", isgrade);
					values.put("check_pic", checkPic);
					values.put("maintain_name", maintainName);
					values.put("is_file", isfile);
					values.put("file_date", fileDate);
					values.put("maintain_problem", maintainProblem);
					values.put("treatment_measure", treatmentMeasure);
					values.put("treatment_effect", treatmentEffect);
					values.put("treatment_date", treatmentDate);
					values.put("treatment_content", treatmentContent);
					values.put("quality_evaluation", qualityEvaluation);
					values.put("leader", leader);
					values.put("minister", minister);
					values.put("file_people", filePeople);
					values.put("photo_addr", photoAddr);
					values.put("ischecked", ischecked);
					
					db.insert("maintain_task", null, values);
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

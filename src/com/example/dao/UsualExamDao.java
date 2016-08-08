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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.SettingBean;
import com.example.bean.UsualExamBean;
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

/**
 * 经常性检查DAO
 * 
 * @author JMY
 * 
 */
public class UsualExamDao extends TemplateDao<UsualExamBean> {
	private Context context;

	public UsualExamDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}

	// 按页查询
	public List<UsualExamBean> findByPage(int page,String depCode) {
		List<UsualExamBean> list = new ArrayList<UsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if(depCode.equals("0000")){
				cursor = db.query("usual_exam u,bridge b", new String[] {
						"u.usual_exam_id", "b.bridge_code", "b.bridge_name", "u.principal",
						"u.noter", "u.check_date" },
						"usual_exam_id>? AND usual_exam_id<=? AND u.bridge_code=b.bridge_code", new String[] {
								String.valueOf(page), String.valueOf(page + 30) },
						null, null, null);
			}else{
				cursor = db.query("usual_exam u,bridge b", new String[] {
						"u.usual_exam_id", "b.bridge_code", "b.bridge_name", "u.principal",
						"u.noter", "u.check_date" },
						"u.bridge_code=b.bridge_code AND b.dep_code=?", new String[] {depCode},
						null,null, "u.usual_exam_id asc",page+",30");
			}
			while (cursor.moveToNext()) {
				UsualExamBean bean = new UsualExamBean();
				bean.setUsualExamId(cursor.getString(0));
				bean.setBridgeCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
				bean.setPrincipal(cursor.getString(3));
				bean.setNoter(cursor.getString(4));
				bean.setCheckDate(cursor.getString(5));
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
	
	//根据经常性检查ID获取桥梁的depCode
	public String findDepCodeByUsuslExamId(String usualExamId){
		String bridgeDepCode = "";
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query("usual_exam u,bridge b",
					new String[] { "b.dep_code" }, "u.usual_exam_id=? AND u.bridge_code=b.bridge_code", new String[]{usualExamId}, null,
					null, null);
			while (cursor.moveToNext()) {
				bridgeDepCode = cursor.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return bridgeDepCode;
	}
	
	//根据ID查询列表项
	public UsualExamBean queryById(String id){
		UsualExamBean bean =this.get(id);
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("bridge",new String[]{"bridge_name,line_number,line_name,center_stake,MM_name"}, "bridge_code=?", new String[]{bean.getBridgeCode()}, null, null, null);
			while (cursor.moveToNext()) {
				bean.setBridgeName(cursor.getString(0));
				bean.setLineNumber(cursor.getString(1));
				bean.setLineName(cursor.getString(2));
				bean.setCenterStake(cursor.getDouble(3));
				bean.setMmName(cursor.getString(4));
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
	
	//根据ID查询最新的一次经常性检查
	public UsualExamBean queryLastById(String id){
		UsualExamBean bean = null;
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursorPro = db.query("usual_exam u", new String[]{"usual_exam_id"}, "u.bridge_code IN (SELECT ui.bridge_code FROM usual_exam ui WHERE ui.usual_exam_id="+id+")", null, null, null, "check_date desc");
			cursorPro.moveToFirst();
			id = cursorPro.getString(0);
			bean =this.get(id);
			if (null != db) {
				db.close();
			}
			db = this.getWritableDatabase();
			Cursor cursor = db.query("bridge",new String[]{"bridge_name,line_number,line_name,center_stake,MM_name"}, "bridge_code=?", new String[]{bean.getBridgeCode()}, null, null, null);
			while (cursor.moveToNext()) {
				bean.setBridgeName(cursor.getString(0));
				bean.setLineNumber(cursor.getString(1));
				bean.setLineName(cursor.getString(2));
				bean.setCenterStake(cursor.getDouble(3));
				bean.setMmName(cursor.getString(4));
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

	// 按条码查找
	public List<UsualExamBean> findByBar(String str,String depCode) {
		List<UsualExamBean> list = new ArrayList<UsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if(depCode.equals("0000")){
				cursor = db.query("usual_exam u,bridge b", new String[] { 
						"u.usual_exam_id","u.bridge_code","b.bridge_name","u.check_date" },
						"u.bridge_code=b.bridge_code AND b.bar_code=?",
						new String[] {str}, null, null, null);
			}else{
				cursor = db.query("usual_exam u,bridge b", new String[] { 
						"u.usual_exam_id","u.bridge_code","b.bridge_name","u.check_date" },
						"u.bridge_code=b.bridge_code AND b.bar_code=? AND b.dep_code=?",
						new String[] {str,depCode}, null, null, null);
			}
			while (cursor.moveToNext()) {
				UsualExamBean bean = new UsualExamBean();
				bean.setUsualExamId(cursor.getString(0));
				bean.setBridgeCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
				bean.setCheckDate(cursor.getString(3));
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

	// 按桥名查找
	public List<UsualExamBean> findByName(String str,String depCode) {
		List<UsualExamBean> list = new ArrayList<UsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if(depCode.equals("0000")){
				cursor = db.query("usual_exam u,bridge b", new String[] {
						"u.usual_exam_id", "u.bridge_code", "b.bridge_name",  "u.check_date" },
						"u.bridge_code=b.bridge_code AND b.bridge_name LIKE '%" + str + "%'",null, null, null, null);
			}else{
				cursor = db.query("usual_exam u,bridge b", new String[] {
						"u.usual_exam_id", "u.bridge_code", "b.bridge_name",  "u.check_date" },
						"u.bridge_code=b.bridge_code AND b.bridge_name LIKE '%" + str + "%' AND b.dep_code=?",
						new String[]{depCode}, null, null, null);
			}
			while (cursor.moveToNext()) {
				UsualExamBean bean = new UsualExamBean();
				bean.setUsualExamId(cursor.getString(0));
				bean.setBridgeCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
				bean.setCheckDate(cursor.getString(3));
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

	// 需要上传的经常性检查
	public List<UsualExamBean> findToUpload(String depCode) {
		List<UsualExamBean> list = new ArrayList<UsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if(depCode.equals("0000")){
				cursor = db.query("usual_exam u,bridge b", new String[] {
						"u.usual_exam_id", "u.bridge_code", "b.bridge_name",  "u.check_date" ,"u.principal","u.noter"},
						"u.bridge_code=b.bridge_code AND u.is_upload=?",new String[] { "0" }, null, null, null);
			}else{
				cursor = db.query("usual_exam u,bridge b", new String[] {
						"u.usual_exam_id", "u.bridge_code", "b.bridge_name",  "u.check_date","u.principal","u.noter" },
						"u.bridge_code=b.bridge_code AND u.is_upload=? AND b.dep_code=?",
						new String[]{"0",depCode}, null, null, null);
			}
			while (cursor.moveToNext()) {
				UsualExamBean bean = new UsualExamBean();
				bean.setUsualExamId(cursor.getString(0));
				bean.setBridgeCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
				bean.setCheckDate(cursor.getString(3));
				bean.setPrincipal(cursor.getString(4));
				bean.setNoter(cursor.getString(5));
		
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

	// 获得最大的ID
	public int getMaxId() {
		int idMax = 0;
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query("usual_exam",
					new String[] { "MAX(usual_exam_id)" }, null, null, null,
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

//	// 获得客户端(还未上传的数据中)最大的ID
//	public int getClientMaxId() {
//		int idMax = 0;
//		SQLiteDatabase db = null;
//		try {
//			db = this.getWritableDatabase();
//			Cursor cursor = db.query("usual_exam",
//					new String[] { "MAX(usual_exam_id)" }, "is_upload=?",
//					new String[] { "1" }, null, null, null);
//			while (cursor.moveToNext()) {
//				idMax = cursor.getInt(0);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != db) {
//				db.close();
//			}
//		}
//		return idMax;
//	}

	// 上传后把上传标志位改为1
	public synchronized void updateUploadbyId(int id) {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("is_upload", "1");
			db.update("usual_exam", values, "is_upload=? AND usual_exam_id=?",
					new String[] { "0", id + "" });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	// 上传后删除标志位为0 的记录，以便重新获取
	public void deleteUpload() {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			db.delete("usual_exam", "is_upload=?", new String[] { "0" });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}


	// 获得页数
	public void initCount(SettingBean settingBean, final Handler handler) {
		CommonHttpRequest.getSingle().request(context, settingBean, handler,
				Uri.Init.MSG_USUALEXAM_INFO, null);
	}

	// 分页初始化
	public synchronized void init(final SettingBean settingBean,final Handler handler, final int pageNum) {
		System.out.println("---------init usualExam ------");
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
						params.add(new RequestParams("time", String.valueOf(paramObject)));
						params.add(new RequestParams("v", md5.getMD5ofStr(Uri.KEY + " "+ String.valueOf(paramObject))));
						params.add(new RequestParams("pageNum", String.valueOf(pageNum)));
						// url
						String url = Uri.getUri(settingBean.getIp(),settingBean.getPort(),settingBean.getWeb_app(),Uri.Init.USUALEXAM_INFO_PAGE);
						AsyncHttpRequest request = new AsyncHttpRequest(url,params, new RequestResultCallback() {
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
											msg.what = Uri.Init.MSG_USUALEXAM_INFO_PAGE;

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
											msg.what = Uri.Init.MSG_USUALEXAM_INFO_PAGE;
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
							msg.what = Uri.Init.MSG_USUALEXAM_INFO_PAGE;
							handler.sendMessage(msg);
						}
					}
				});
		ThreadPool.getSingle().execute(currentTime);

	}

//	// 分页初始化
//	public synchronized boolean add(String json) {
//		if (json != null) {
//			List<UsualExamBean> list = JSON.parseArray(json,
//					UsualExamBean.class);
//			for (int i = 0; i < list.size(); i++) {
//				UsualExamBean bean = list.get(i);
//				insert(bean);
//			}
//			return true;
//		}
//		return false;
//	}

	public synchronized boolean add(JSONArray array) {
		if (null != array) {

			SQLiteDatabase db = null;

			try {

				db = this.getWritableDatabase();

				db.beginTransaction();

				// db.delete("usual_exam", null, null);

				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					ContentValues values = new ContentValues();

					values.put("usual_exam_id", obj.getIntValue("usualExamId"));
					values.put("manager_unit", obj.getString("managerUnit"));
					values.put("bridge_code", obj.getString("bridgeCode"));
//					values.put("bridge_name", obj.getString("bridgeName"));
//					values.put("line_number", obj.getString("lineNumber"));
//					values.put("line_name", obj.getString("lineName"));
//					values.put("center_stake",obj.getDoubleValue("centerStake"));
					values.put("MM_name", obj.getString("mmName"));
					values.put("principal", obj.getString("principal"));
					values.put("noter", obj.getString("noter"));

					values.put("wall_type", obj.getString("wallType"));
					values.put("wall_region", obj.getString("wallRegion"));
					values.put("wall_advise", obj.getString("wallAdvise"));
					values.put("slope_type", obj.getString("slopeType"));
					values.put("slope_region", obj.getString("slopeRegion"));
					values.put("slope_advise", obj.getString("slopeAdvise"));
					values.put("deck_type", obj.getString("deckType"));
					values.put("deck_region", obj.getString("deckRegion"));
					values.put("deck_advise", obj.getString("deckAdvise"));
					values.put("expansion_type", obj.getString("expansionType"));

					values.put("expansion_region",obj.getString("expansionRegion"));
					values.put("expansion_advise",obj.getString("expansionAdvise"));
					values.put("sidewalk_type", obj.getString("sidewalkType"));
					values.put("sidewalk_region",obj.getString("sidewalkRegion"));
					values.put("sidewalk_advise",obj.getString("sidewalkAdvise"));
					values.put("guard_type", obj.getString("guardType"));
					values.put("guard_region", obj.getString("guardRegion"));
					values.put("guard_advise", obj.getString("guardAdvise"));
					values.put("waterproof_type",obj.getString("waterproofType"));
					values.put("waterproof_region",obj.getString("waterproofRegion"));
					values.put("waterproof_advise",obj.getString("waterproofAdvise"));
					values.put("lighting_type", obj.getString("lightingType"));

					values.put("lighting_region",obj.getString("lightingRegion"));
					values.put("lighting_advise",obj.getString("lightingAdvise"));
					values.put("clean_type", obj.getString("cleanType"));
					values.put("clean_region", obj.getString("cleanRegion"));
					values.put("clean_advise", obj.getString("cleanAdvise"));
					values.put("abutment_type", obj.getString("abutmentType"));
					values.put("abutment_region",obj.getString("abutmentRegion"));
					values.put("abutment_advise",obj.getString("abutmentAdvise"));
					values.put("pier_type", obj.getString("pierType"));
					values.put("pier_region", obj.getString("pierRegion"));
					values.put("pie_advise", obj.getString("pieAdvise"));

					values.put("foundation_type",obj.getString("foundationType"));
					values.put("foundation_region",obj.getString("foundationRegion"));
					values.put("foundation_advise",obj.getString("foundationAdvise"));
					values.put("supports_type", obj.getString("supportsType"));
					values.put("supports_region",obj.getString("supportsRegion"));
					values.put("supports_advise",obj.getString("supportsAdvise"));
					values.put("superstructure_type",obj.getString("superstructureType"));
					values.put("superstructure_region",obj.getString("superstructureRegion"));
					values.put("superstructure_advise",obj.getString("superstructureAdvise"));
					values.put("approach_type", obj.getString("approachType"));

					values.put("approach_region",obj.getString("approachRegion"));
					values.put("approach_advise",obj.getString("approachAdvise"));
					values.put("sign_type", obj.getString("signType"));
					values.put("sign_region", obj.getString("signRegion"));
					values.put("sign_advise", obj.getString("signAdvise"));
					values.put("regulating_type",obj.getString("regulatingType"));
					values.put("regulating_region",obj.getString("regulatingRegion"));
					values.put("regulating_advise",obj.getString("regulatingAdvise"));
					values.put("else_type", obj.getString("elseType"));
					values.put("else_region", obj.getString("elseRegion"));

					values.put("else_advise", obj.getString("elseAdvise"));
					values.put("check_date", obj.getString("checkDate"));
					values.put("photo_addr", obj.getString("photoAddr"));
					values.put("is_upload", obj.getString("isUpload"));

					db.insert("usual_exam", null, values);
				}
				db.setTransactionSuccessful();

				db.endTransaction();

				return true;

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != db) {
					db.close();
				}
			}
		}

		return false;
	}


	/*
	 * 经常性检查数据上传
	 */
	public void upload(SettingBean settingBean, final Handler handler,String depCode) {

		List<UsualExamBean> list = null;
		if(depCode.equals("0000")){
			list = this.find(null, "is_upload=?",
					new String[] { "0" }, null, null, null, null);
		}else{
			list = this.find(null, "is_upload=? AND dep_code=?",
					new String[] { "0" ,depCode}, null, null, null, null);
		}
				
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				UsualExamBean bean = list.get(i);
				String json = JSONObject.toJSONString(bean);
				CommonHttpRequest.getSingle().request(context, settingBean,
						handler, Uri.Upload.MSG_BRIDGEUSUALEXAM_INFO, json);
			}
		} else {
			Message msg = handler.obtainMessage();
			msg.what = Uri.Upload.MSG_BRIDGEUSUALEXAM_INFO;
			Bundle data = new Bundle();
			data.putBoolean(Uri.KEY_FLAG, true);
			data.putBoolean("data_flag", true);// 记录有没有数据
			data.putString(Uri.KEY_MSG, "无新增经常性检查记录");
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	//查询上传的数据的数量
	public int uploadNum(String depCode){
		List<UsualExamBean> list = null;
		if(depCode.equals("0000")){
			list = this.find(null, "is_upload=?",
					new String[] { "0" }, null, null, null, null);
		}else{
			list = this.find(null, "is_upload=? AND dep_code=?",
					new String[] { "0" ,depCode}, null, null, null, null);
		}
		if (null != list && list.size() > 0) {
			return list.size();
		}
		return 0;
	}


	// 上传经常性检查的照片
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
						String url = Uri.getUri(settingBean.getIp(),settingBean.getPort(),settingBean.getWeb_app(),Uri.Upload.BRIDGEUSUALEXAM_PHOTO);
						MD5 md5 = new MD5();
						Map<String, String> parameters = new HashMap<String, String>();
						parameters.put("time", String.valueOf(paramObject));
						parameters.put("v",md5.getMD5ofStr(Uri.KEY + " "+ String.valueOf(paramObject)));
						parameters.put("serverId", serverId + "");
						parameters.put("dataType", "1");
						FileUploadRequest request = new FileUploadRequest(url,
								picPath, parameters,
								new OnUploadProcessListener() {

									@Override
									public void onUploadDone(int responseCode,
											String message) {
										if (null != handler) {
											Message msg = handler
													.obtainMessage();
											Bundle data = new Bundle();
											data.putBoolean(Uri.KEY_FLAG, true);
											data.putString(Uri.KEY_MSG, message);
											msg.setData(data);
											msg.what = Uri.Upload.MSG_BRIDGEUSUALEXAM_PHOTO;

											handler.sendMessage(msg);
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
							Message msg = handler.obtainMessage();
							Bundle data = new Bundle();
							data.putBoolean(Uri.KEY_FLAG, false);
							data.putString(Uri.KEY_MSG, paramException);
							msg.setData(data);
							msg.what = Uri.Upload.MSG_BRIDGEUSUALEXAM_PHOTO;
							handler.sendMessage(msg);
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
			Cursor cursor = db.query("usual_exam",
					new String[] { "photo_addr" }, "usual_exam_id=?",
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

//	/*
//	 * 经常性检查数据下载
//	 */
//	public void download(SettingBean settingBean, final Handler handler,
//			int clientMaxId) {
//		// TODO Auto-generated method stub
//		CommonHttpRequest.getSingle().request(context, settingBean, handler,
//				Uri.Upload.MSG_BRIDGEUSUALEXAM_DOWNLOAD,
//				String.valueOf(clientMaxId));
//	}

}

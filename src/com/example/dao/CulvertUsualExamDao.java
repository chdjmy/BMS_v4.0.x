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
import com.example.bean.CulvertUsualExamBean;
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

public class CulvertUsualExamDao extends TemplateDao<CulvertUsualExamBean> {

	private Context context;

	public CulvertUsualExamDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}

	// 按页查询
	public List<CulvertUsualExamBean> findByPage(int page, String depCode) {
		List<CulvertUsualExamBean> list = new ArrayList<CulvertUsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if (depCode.equals("0000")) {
				cursor = db
						.query("cusual_exam u,culvert c",
								new String[] { "u.cusual_exam_id",
										"u.culvert_code", "c.culvert_name",
										"u.check_date" },
								"cusual_exam_id>? AND cusual_exam_id<=? AND u.culvert_code=c.culvert_code",
								new String[] { String.valueOf(page),
										String.valueOf(page + 30) }, null,
								null, null);
			} else {
				cursor = db.query("cusual_exam u,culvert c", new String[] {
						"u.cusual_exam_id", "u.culvert_code", "c.culvert_name",
						"u.check_date" },
						"u.culvert_code=c.culvert_code  AND c.dep_code=?",
						new String[] { depCode }, null, null,
						"u.cusual_exam_id asc", page + ",30");
			}
			while (cursor.moveToNext()) {
				CulvertUsualExamBean bean = new CulvertUsualExamBean();
				bean.setCusualExamId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
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

	//根据经常性检查ID获取桥梁的depCode
	public String findDepCodeByUsuslExamId(String usualExamId){
		String culvertDepCode = "";
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query("cusual_exam u,culvert c",
					new String[] { "c.dep_code" }, "u.cusual_exam_id=? AND u.culvert_code=c.culvert_code", new String[]{usualExamId}, null,
					null, null);
			while (cursor.moveToNext()) {
				culvertDepCode = cursor.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return culvertDepCode;
	}
	// 根据ID查询
	public CulvertUsualExamBean queryById(String id) {
		CulvertUsualExamBean bean = this.get(id);
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db
					.query("culvert",
							new String[] { "culvert_name,line_number,line_name,center_stake,MM_name,culvert_type" },
							"culvert_code=?",
							new String[] { bean.getCulvertCode() }, null, null,
							null);
			while (cursor.moveToNext()) {
				bean.setCulvertName(cursor.getString(0));
				bean.setLineNumber(cursor.getString(1));
				bean.setLineName(cursor.getString(2));
				bean.setCenterStake(cursor.getDouble(3));
				bean.setMmName(cursor.getString(4));
				bean.setCulvertType(cursor.getString(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}

		return bean;
	}

	// 按条码查找
	public List<CulvertUsualExamBean> findByBar(String str, String depCode) {
		List<CulvertUsualExamBean> list = new ArrayList<CulvertUsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if (depCode.equals("0000")) {
				cursor = db.query("cusual_exam u,culvert c", new String[] {
						"u.cusual_exam_id", "u.culvert_code", "c.culvert_name",
						"u.check_date" },
						"u.culvert_code=c.culvert_code AND c.bar_code = ?",
						new String[] { str }, null, null, null);
			} else {
				cursor = db
						.query("cusual_exam u,culvert c",
								new String[] { "u.cusual_exam_id",
										"u.culvert_code", "c.culvert_name",
										"u.check_date" },
								"u.culvert_code=c.culvert_code AND c.bar_code = ? AND dep_code=?",
								new String[] { str, depCode }, null, null, null);
			}
			while (cursor.moveToNext()) {
				CulvertUsualExamBean bean = new CulvertUsualExamBean();
				bean.setCusualExamId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
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

	// 按涵洞名查找
	public List<CulvertUsualExamBean> findByName(String str, String depCode) {
		List<CulvertUsualExamBean> list = new ArrayList<CulvertUsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if (depCode.equals("0000")) {
				cursor = db.query("cusual_exam u,culvert c", new String[] {
						"u.cusual_exam_id", "u.culvert_code", "c.culvert_name",
						"u.check_date" },
						"u.culvert_code=c.culvert_code AND c.culvert_name LIKE '%"
								+ str + "%'", null, null, null, null);
			} else {
				cursor = db.query("cusual_exam u,culvert c", new String[] {
						"u.cusual_exam_id", "u.culvert_code", "c.culvert_name",
						"u.check_date" },
						"u.culvert_code=c.culvert_code AND c.culvert_name LIKE '%"
								+ str + "%' AND c.dep_code=?",
						new String[] { depCode }, null, null, null);
			}
			while (cursor.moveToNext()) {
				CulvertUsualExamBean bean = new CulvertUsualExamBean();
				bean.setCusualExamId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
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

	// 获得最大的ID
	public int getMaxId() {
		int idMax = 0;
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query("cusual_exam",
					new String[] { "MAX(cusual_exam_id)" }, null, null,
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

	// 需要上传的涵洞经常性检查
//	public List<CulvertUsualExamBean> findToUpload(String depCode) {
//		List<CulvertUsualExamBean> list = new ArrayList<CulvertUsualExamBean>();
//		SQLiteDatabase db = null;
//		try {
//			db = this.getWritableDatabase();
//			Cursor cursor = null;
//			if (depCode.equals("0000")) {
//				cursor = db.query("cusual_exam u,culvert c", new String[] {
//						"u.cusual_exam_id", "u.culvert_code", "c.culvert_name",
//						"u.check_date", "u.manager_name", "u.noter" },
//						"u.culvert_code=c.culvert_code AND u.is_upload=?",
//						new String[] { "0" }, null, null, null);
//			} else {
//				cursor = db
//						.query("cusual_exam u,culvert c",
//								new String[] { "u.cusual_exam_id",
//										"u.culvert_code", "c.culvert_name",
//										"u.check_date", "u.manager_name",
//										"u.noter" },
//								"u.culvert_code=c.culvert_code AND u.is_upload=? AND c.dep_code=?",
//								new String[] { "0", depCode }, null, null, null);
//			}
//			while (cursor.moveToNext()) {
//				CulvertUsualExamBean bean = new CulvertUsualExamBean();
//				bean.setCulvertUsualExamId(cursor.getString(0));
//				bean.setCulvertCode(cursor.getString(1));
//				bean.setCulvertName(cursor.getString(2));
//				bean.setCheckDate(cursor.getString(3));
//				bean.setManagerName(cursor.getString(4));
//				bean.setNoter(cursor.getString(5));
//
//				list.add(bean);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != db) {
//				db.close();
//			}
//		}
//		return list;
//	}
	public List<CulvertUsualExamBean> findToUpload(String depCode) {
		List<CulvertUsualExamBean> list = new ArrayList<CulvertUsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if (depCode.equals("0000")) {
				cursor = db
						.query("cusual_exam u,culvert c",
								new String[] { "u.cusual_exam_id",
										"u.culvert_code", "c.culvert_name",
										"u.check_date","noter" },
								"u.is_upload=? AND u.culvert_code=c.culvert_code",
								new String[] {"0"}, null,
								null, null);
			} else {
				cursor = db.query("cusual_exam u,culvert c", new String[] {
						"u.cusual_exam_id", "u.culvert_code", "c.culvert_name",
						"u.check_date" ,"noter"},
						"u.culvert_code=c.culvert_code AND u.is_upload=? AND c.dep_code=?",
						new String[] {"0", depCode }, null, null,
						null);
			}
			while (cursor.moveToNext()) {
				CulvertUsualExamBean bean = new CulvertUsualExamBean();
				bean.setCusualExamId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
				bean.setCheckDate(cursor.getString(3));
				bean.setNoter(cursor.getString(4));
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

	// 获得页数
	public void initCount(SettingBean settingBean, final Handler handler) {
		CommonHttpRequest.getSingle().request(context, settingBean, handler,
				Uri.Init.MSG_CUSUALEXAM_INFO, null);
	}

	// 分页初始化
	public synchronized void init(final SettingBean settingBean,
			final Handler handler, final int pageNum) {
		System.out.println("---------init CusualExam ------");
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
								Uri.Init.CUSUALEXAM_INFO_PAGE);
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
											msg.what = Uri.Init.MSG_CUSUALEXAM_INFO_PAGE;

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
											msg.what = Uri.Init.MSG_CUSUALEXAM_INFO_PAGE;
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
							msg.what = Uri.Init.MSG_CUSUALEXAM_INFO_PAGE;
							handler.sendMessage(msg);
						}
					}
				});
		ThreadPool.getSingle().execute(currentTime);

	}

	public synchronized boolean add(JSONArray array) {
		if (null != array) {

			SQLiteDatabase db = null;

			try {

				db = this.getWritableDatabase();

				db.beginTransaction();

				// db.delete("cusual_exam", null, null);

				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					ContentValues values = new ContentValues();

					values.put("cusual_exam_id", obj.getIntValue("cusualExamId"));
					values.put("culvert_code", obj.getString("culvertCode"));
					values.put("maintain_name", obj.getString("maintainName"));
					values.put("manager_name", obj.getString("managerName"));
					values.put("noter", obj.getString("noter"));
					values.put("check_date", obj.getString("checkDate"));
					values.put("inlet_type_disease",obj.getString("inletTypeDisease"));
					values.put("inlet_region", obj.getString("inletRegion"));
					values.put("inlet_advise", obj.getString("inletAdvise"));
					values.put("outlet_type_disease", obj.getString("outletTypeDisease"));

					values.put("outlet_region", obj.getString("outletRegion"));
					values.put("outlet_advise", obj.getString("outletAdvise"));
					values.put("body_type", obj.getString("bodyType"));
					values.put("body_region", obj.getString("bodyRegion"));
					values.put("body_advise", obj.getString("bodyAdvise"));
					values.put("top_type", obj.getString("topType"));
					values.put("top_region", obj.getString("topRegion"));
					values.put("top_advise", obj.getString("topAdvise"));
					values.put("bottom_type", obj.getString("bottomType"));
					values.put("bottom_region", obj.getString("bottomRegion"));

					values.put("bottom_advise", obj.getString("bottomAdvise"));
					values.put("fill_type", obj.getString("fillType"));
					values.put("fill_region", obj.getString("fillRegion"));
					values.put("fill_advise", obj.getString("fillAdvise"));
					values.put("lighting_type",obj.getString("lightingType"));
					values.put("lighting_region", obj.getString("lightingRegion"));
					values.put("lighting_advise", obj.getString("lightingAdvise"));
					values.put("else_type", obj.getString("elseType"));
					values.put("else_region", obj.getString("elseRegion"));
					values.put("else_advise",obj.getString("elseAdvise"));
					values.put("remark", obj.getString("remark"));
					values.put("photo_addr", obj.getString("photoAddr"));
					values.put("is_upload", obj.getString("isUpload"));


					db.insert("cusual_exam", null, values);
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

		List<CulvertUsualExamBean> list = null;
		if(depCode.equals("0000")){
			list = this.find(null, "is_upload=?",
					new String[] { "0" }, null, null, null, null);
		}else{
			list = this.find(null, "is_upload=? AND dep_code=?",
					new String[] { "0" ,depCode}, null, null, null, null);
		}
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				CulvertUsualExamBean bean = list.get(i);
				String json = JSONObject.toJSONString(bean);
				CommonHttpRequest.getSingle().request(context, settingBean,
						handler, Uri.Upload.MSG_CULVERTUSUALEXAM_INFO, json);
			}
		} else {
			Message msg = handler.obtainMessage();
			msg.what = Uri.Upload.MSG_CULVERTUSUALEXAM_INFO;
			Bundle data = new Bundle();
			data.putBoolean(Uri.KEY_FLAG, true);
			data.putBoolean("data_flag", true);// 记录有没有数据
			data.putString(Uri.KEY_MSG, "无新增经常性检查记录");
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	
	public int uploadNum(String depCode){
		List<CulvertUsualExamBean> list = null;
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
	
	// 上传后把上传标志位改为1
	public synchronized void updateUploadbyId(int id) {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("is_upload", "1");
			db.update("cusual_exam", values, "is_upload=? AND cusual_exam_id=?",
					new String[] { "0", id + "" });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
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
						String url = Uri.getUri(settingBean.getIp(),settingBean.getPort(),settingBean.getWeb_app(),Uri.Upload.CULVERTUSUALEXAM_PHOTO);
						MD5 md5 = new MD5();
						Map<String, String> parameters = new HashMap<String, String>();
						parameters.put("time", String.valueOf(paramObject));
						parameters.put("v",md5.getMD5ofStr(Uri.KEY + " "+ String.valueOf(paramObject)));
						parameters.put("serverId", serverId + "");
						parameters.put("dataType", "2");
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
			Cursor cursor = db.query("cusual_exam",
					new String[] { "photo_addr" }, "cusual_exam_id=?",
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
	
}

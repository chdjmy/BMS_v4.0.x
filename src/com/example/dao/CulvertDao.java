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
import com.example.bean.CulvertBean;
import com.example.bean.SettingBean;
import com.example.common.dao.TemplateDao;
import com.example.http.AsyncHttpRequest;
import com.example.http.RequestParams;
import com.example.http.RequestResultCallback;
import com.example.http.ThreadPool;
import com.example.util.CommonHttpRequest;
import com.example.util.MD5;
import com.example.util.Uri;

public class CulvertDao extends TemplateDao<CulvertBean> {

	private Context context;

	public CulvertDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}
	
	//分页查找
	public List<CulvertBean> findByPage(int page,String depCode){
		List<CulvertBean> list = new ArrayList<CulvertBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if(depCode.equals("0000")){
				cursor = db.query("culvert",new String[]{"culvert_id","culvert_code","culvert_name","line_number","line_name","line_type","location_name"}, "culvert_id>? AND culvert_id<=?", new String[]{String.valueOf(page),String.valueOf(page+30)}, null, null, null);
			}else{
				cursor = db.query("culvert",new String[]{"culvert_id","culvert_code","culvert_name","line_number","line_name","line_type","location_name"}, "dep_code=?", new String[]{depCode}, null, null,"culvert_id asc",page+",30");
			}
			
			while (cursor.moveToNext()) {
				CulvertBean bean = new CulvertBean();
				bean.setCulvertId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
				bean.setLineNumber(cursor.getString(3));
				bean.setLineName(cursor.getString(4));
				bean.setLineType(cursor.getString(5));
				bean.setLocationName(cursor.getString(6));
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
	public List<CulvertBean> findByBar(String str,String depCode){
		List<CulvertBean> list = new ArrayList<CulvertBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if(depCode.equals("0000")){
				cursor = db.query("culvert",new String[]{"culvert_id","culvert_code","culvert_name","line_number","line_name","line_type","location_name","center_stake","MM_name","culvert_type"}, "bar_code=?", new String[]{str}, null, null, null);
			}else{
				cursor = db.query("culvert",new String[]{"culvert_id","culvert_code","culvert_name","line_number","line_name","line_type","location_name","center_stake","MM_name","culvert_type"}, "bar_code=? AND dep_code=?", new String[]{str,depCode}, null, null, null);
			}
			while (cursor.moveToNext()) {
				CulvertBean bean = new CulvertBean();
				bean.setCulvertId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
				bean.setLineNumber(cursor.getString(3));
				bean.setLineName(cursor.getString(4));
				bean.setLineType(cursor.getString(5));
				bean.setLocationName(cursor.getString(6));
				bean.setCenterStake(cursor.getDouble(7));
				bean.setMmName(cursor.getString(8));
				bean.setCulvertType(cursor.getString(9));
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
	
	//按桥名查找
	public List<CulvertBean> findByName(String str,String depCode){
		List<CulvertBean> list = new ArrayList<CulvertBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = null;
			if(depCode.equals("0000")){
				cursor = db.query("culvert",new String[]{"culvert_id","culvert_code","culvert_name","line_number","line_name","line_type","location_name","center_stake","MM_name","culvert_type"}, "culvert_name LIKE '%"+str+"%'",null, null, null, null);
			}else{
				cursor = db.query("culvert",new String[]{"culvert_id","culvert_code","culvert_name","line_number","line_name","line_type","location_name","center_stake","MM_name","culvert_type"}, "culvert_name LIKE '%"+str+"%' AND dep_code=?",new String[]{depCode}, null, null, null);
			}
			
			while (cursor.moveToNext()) {
				CulvertBean bean = new CulvertBean();
				bean.setCulvertId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
				bean.setLineNumber(cursor.getString(3));
				bean.setLineName(cursor.getString(4));
				bean.setLineType(cursor.getString(5));
				bean.setLocationName(cursor.getString(6));
				bean.setCenterStake(cursor.getDouble(7));
				bean.setMmName(cursor.getString(8));
				bean.setCulvertType(cursor.getString(9));
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
	// 获得页数
	public void initCount(SettingBean settingBean, final Handler handler) {
		CommonHttpRequest.getSingle().request(context, settingBean, handler,
				Uri.Init.MSG_CULVERT_INFO, null);
	}

	// 分页初始化
	public synchronized void init(final SettingBean settingBean,final Handler handler, final int pageNum) {
		System.out.println("---------init Culvert ------");
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
						String url = Uri.getUri(settingBean.getIp(),settingBean.getPort(),settingBean.getWeb_app(),Uri.Init.CULVERT_INFO_PAGE);
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
											msg.what = Uri.Init.MSG_CULVERT_INFO_PAGE;

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
											msg.what = Uri.Init.MSG_CULVERT_INFO_PAGE;
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
							msg.what = Uri.Init.MSG_CULVERT_INFO_PAGE;
							handler.sendMessage(msg);
						}
					}
				});
		ThreadPool.getSingle().execute(currentTime);

	}
	//异步增加数据
	public synchronized boolean add(JSONArray array) {
		if (null != array) {

			SQLiteDatabase db = null;

			try {

				db = this.getWritableDatabase();

				db.beginTransaction();

				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					ContentValues values = new ContentValues();

					values.put("culvert_id", obj.getIntValue("culvertId"));
					values.put("grade", obj.getString("grade"));
					values.put("culvert_code", obj.getString("culvertCode"));
					values.put("culvert_name", obj.getString("culvertName"));
					values.put("line_number", obj.getString("lineNumber"));
					values.put("line_name", obj.getString("lineName"));
					values.put("serial_number",obj.getDoubleValue("serialNumber"));
					values.put("location_name", obj.getString("locationName"));
					values.put("line_type", obj.getString("lineType"));
					values.put("MM_name", obj.getString("mmName"));

					values.put("center_stake", obj.getString("centerStake"));
					values.put("construct_stake", obj.getString("constructStake"));
					values.put("culvert_angle", obj.getString("culvertAngle"));
					values.put("culvert_length", obj.getString("culvertLength"));
					values.put("cover_length", obj.getString("coverLength"));
					values.put("culvert_height", obj.getString("culvertLength"));
					values.put("fill_depth", obj.getString("fillDepth"));
					values.put("construct_date", obj.getString("constructDate"));
					values.put("culvert_type", obj.getString("culvertType"));
					values.put("design_load", obj.getString("designLoad"));

					values.put("hole_span",obj.getString("holeSpan"));
					values.put("inlet_type",obj.getString("inletType"));
					values.put("outlet_type", obj.getString("outletType"));
					values.put("manage_code",obj.getString("manageCode"));
					values.put("basis_disease", obj.getString("basisDisease"));
					values.put("body_disease", obj.getString("bodyDisease"));
					values.put("bottom_disease", obj.getString("bottomDisease"));
					values.put("top_disease",obj.getString("topDisease"));
					values.put("inlet_disease",obj.getString("inletDisease"));
					
					values.put("outlet_disease",obj.getString("outletdisease"));
					values.put("pitching_disease", obj.getString("pitchingDisease"));
					values.put("wing_slope_disease",obj.getString("wingSlopeDisease"));
					values.put("jump_disease",obj.getString("jumpDisease"));
					values.put("drainage_disease", obj.getString("drainageDisease"));
					values.put("maintenance_condition", obj.getString("maintenanceCondition"));
					values.put("clean_condition", obj.getString("cleanAdvise"));
					values.put("check_date", obj.getString("checkDate"));
					values.put("next_check_date",obj.getString("nextCheckDate"));
					values.put("remark",obj.getString("remark"));
					
					values.put("adaptive_score", obj.getString("adaptiveScore"));
					values.put("composite_rating", obj.getString("compositeRating"));
					values.put("bar_code", obj.getString("barCode"));
					values.put("culvert_photo_addr",obj.getString("culvertPhotoAddr"));
					values.put("latitude",obj.getString("laitude"));
					values.put("longitude",obj.getString("longitude"));
					values.put("culvert_type_material", obj.getString("culvertTypeMaterial"));
					values.put("culvert_type_hole",obj.getString("culvertTypeHole"));
					values.put("culvert_type_entrance",obj.getString("culvertTypeEntrance"));
					values.put("inlet_type_material",obj.getString("inletTypeMaterial"));
					
					values.put("inlet_type_hole",obj.getString("inletTypeHole"));
					values.put("outlet_type_material",obj.getString("outletTypeMaterial"));
					values.put("outlet_type_hole", obj.getString("outletTypeHole"));
					values.put("fileUploadAddr",obj.getString("fileUploadAddr"));
					values.put("dep_code",obj.getString("depCode"));

					db.insert("culvert", null, values);
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


}

package com.example.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.bean.SettingBean;
import com.example.http.AsyncHttpRequest;
import com.example.http.RequestParams;
import com.example.http.RequestResultCallback;
import com.example.http.ThreadPool;

/**
 * http请求
 * */
public class CommonHttpRequest {

	/**
	 * @param context
	 *            上下文
	 * @param settingBean
	 *            设置bean
	 * @param handler服务器返回时调用的handler
	 * @param method 请求的服务器端方法
	 * @param json   上传的数据
	 * */
	public void request(final Context context, final SettingBean settingBean,
			final Handler handler, final int methodId, final String json) {
		//System.out.println("---------request------");
		//System.out.println("methodId:" + methodId);
		//System.out.println("json:" + json);
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
						if (StringUtils.isNotEmpty(json)) {
							params.add(new RequestParams("json", json));
						}
						// url
						String url = getUrl(settingBean, methodId);
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
											msg.what = methodId;

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
											msg.what = methodId;
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
							msg.what = methodId;
							handler.sendMessage(msg);
						}
					}
				});
		ThreadPool.getSingle().execute(currentTime);

	}

	private String getUrl(SettingBean settingBean,int methodId){
		String ret = "";
		switch (methodId) {
		    //数据下载
			case Uri.Init.MSG_USERS_INFO:
				ret = Uri.Init.USERS_INFO;
				break;
				
			case Uri.Init.MSG_BRIDGE_INFO:
				ret = Uri.Init.BRIDGE_INFO;
				break;
			case Uri.Init.MSG_BRIDGE_INFO_PAGE:
				ret = Uri.Init.BRIDGE_INFO_PAGE;
				break;
			case Uri.Init.MSG_USUALEXAM_INFO:
				ret = Uri.Init.USUALEXAM_INFO;
				break;
			case Uri.Init.MSG_USUALEXAM_INFO_PAGE:
				ret = Uri.Init.USUALEXAM_INFO_PAGE;
				break;
			
			case Uri.Init.MSG_CULVERT_INFO:
				ret = Uri.Init.CULVERT_INFO;
				break;
			case Uri.Init.MSG_CULVERT_INFO_PAGE:
				ret = Uri.Init.CULVERT_INFO_PAGE;
				break;
			case Uri.Init.MSG_CUSUALEXAM_INFO:
				ret = Uri.Init.CUSUALEXAM_INFO;
				break;
			case Uri.Init.MSG_CUSUALEXAM_INFO_PAGE:
				ret = Uri.Init.CUSUALEXAM_INFO_PAGE;
				break;
			
			case Uri.Init.MSG_MATERIAL_INFO:
				ret = Uri.Init.MATERIAL_INFO;
				break;
			case Uri.Init.MSG_MAINTAIN_INFO:
				ret = Uri.Init.MAINTAIN_INFO;
				break;
			case Uri.Init.MSG_MAINTAIN_INFO_PAGE:
				ret = Uri.Init.MAINTAIN_INFO_PAGE;
				break;
			case Uri.Init.MSG_MAINTAINMATERIAL_INFO:
				ret = Uri.Init.MAINTAINMATERIAL_INFO;
				break;
			case Uri.Init.MSG_PROJECT_INFO:
				ret = Uri.Init.PROJECT_INFO;
				break;	
				
				
			//数据上传	
			case Uri.Upload.MSG_BRIDGEUSUALEXAM_INFO:
				ret = Uri.Upload.BRIDGEUSUALEXAM_INFO;
				break;
			case Uri.Upload.MSG_BRIDGEUSUALEXAM_PHOTO:	
				ret = Uri.Upload.BRIDGEUSUALEXAM_PHOTO;
				break;
			case Uri.Upload.MSG_CULVERTUSUALEXAM_INFO:
				ret = Uri.Upload.CULVERTUSUALEXAM_INFO;
				break;
			case Uri.Upload.MSG_CULVERTUSUALEXAM_PHOTO:	
				ret = Uri.Upload.CULVERTUSUALEXAM_PHOTO;
				break;
			case Uri.Upload.MSG_MATERIAL_INFO:	
				ret = Uri.Upload.MATERIAL_INFO;
				break;
			case Uri.Upload.MSG_MAINTAINCHECK_INFO:
				ret = Uri.Upload.MAINTAINCHECK_INFO;
				break;
			case Uri.Upload.MSG_MAINTAINCHECK_PHOTO:	
				ret = Uri.Upload.MAINTAINCHECK_PHOTO;
				break;
			case Uri.Upload.MSG_MAINTAINCHECK_MATERIAL:	
				ret = Uri.Upload.MAINTAINCHECK_MATERIAL;
				break;
			case Uri.Upload.MSG_MAINTAINPLAN_INFO:	
				ret = Uri.Upload.MAINTAINPLAN_INFO;
				break;
			case Uri.Upload.MSG_MAINTAINPLAN_MATERIAL:	
				ret = Uri.Upload.MAINTAINPLAN_MATERIAL;
				break;
				
			default:
				ret = "";
				break;
		}
		
		ret = Uri.getUri(settingBean.getIp(), settingBean.getPort(), settingBean.getWeb_app(),ret);
		return ret;
	}

	//在静态实例为null的时候创建，避免重复创建
	public static CommonHttpRequest getSingle() {
		if (null == request) {
			request = new CommonHttpRequest();
		}
		return request;
	}

	//单例模型 构造函数私有，确保只有一个实例
	private CommonHttpRequest() {

	}
	//定义一个静态实例
	private static CommonHttpRequest request;
}

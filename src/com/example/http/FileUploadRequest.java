package com.example.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.provider.Settings.Secure;
import android.view.View.OnClickListener;

public class FileUploadRequest extends BaseRequest{
	
//	private DefaultHttpClient httpClient;
	private Map<String, String> parameters;
	private OnUploadProcessListener onUploadProcessListener;
	private String picPath;
	
	/**
	 * @param url  请求地址
	 * @param params 请求参数
	 * @param requestResultCallback 请求回调函数
	 * */
	public FileUploadRequest(String url,String picPath,Map<String, String> parameters,OnUploadProcessListener MyUploadProcessListener){
		this.url = url;
		this.parameters = parameters;
		this.onUploadProcessListener = MyUploadProcessListener;
		this.picPath = picPath;
//		if(null == httpClient){
//			httpClient = new DefaultHttpClient();
//		}
	}
	
	//线程函数
	@Override
	public void run() {
		try {
			String fileKey = "img";// 与服务器端的File名字相同
			UploadUtil uploadUtil = UploadUtil.getInstance();
			//uploadUtil.setOnUploadProcessListener(onUploadProcessListener); // 设置监听器监听上传状态
			uploadUtil.uploadFile(picPath, fileKey, url, parameters,onUploadProcessListener);
			
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		} 
		
		super.run();
	}
	
	
	
	
	/**
	 * 去上传文件
	 */
	protected static final int TO_UPLOAD_FILE = 1;
	/**
	 * 上传文件响应
	 */
	protected static final int UPLOAD_FILE_DONE = 2; //
	/**
	 * 选择文件
	 */
	public static final int TO_SELECT_PHOTO = 3;
	/**
	 * 上传初始化
	 */
	private static final int UPLOAD_INIT_PROCESS = 4;
	/**
	 * 上传中
	 */
	private static final int UPLOAD_IN_PROCESS = 5;
	
	
	

}

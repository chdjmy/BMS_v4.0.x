package com.example.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class AsyncHttpRequest extends BaseRequest{
	
	/**
	 * @param url  请求地址
	 * @param params 请求参数
	 * @param requestResultCallback 请求回调函数
	 * */
	public AsyncHttpRequest(String url,List<RequestParams> params,RequestResultCallback requestResultCallback){
		this.url = url;
		this.params = params;
		this.requestCallback = requestResultCallback;
		if(null == httpClient){
			httpClient = new DefaultHttpClient();
		}
	}

	@Override
	public void run() {
		try {
			this.request = new HttpPost(url);
			// 设置请求超时时间
			this.request.getParams().setParameter("http.connection.timeout", this.connectTimeout);
			// 设置连接超时时间
			this.request.getParams().setParameter("http.socket.timeout", this.readTimeout);
			// 设置请求参数
			if(null != params && params.size() > 0){
				List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
				for(RequestParams param:params){
					list.add(new BasicNameValuePair(param.getName(), param.getValue()));
				}
				((HttpPost)this.request).setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			}
			// 响应对象
			HttpResponse response = httpClient.execute(this.request);
			int statusCode = response.getStatusLine().getStatusCode();
			
			System.out.println(response.getStatusLine().getStatusCode());
			
			
			if(success == statusCode){
				ByteArrayOutputStream content = new ByteArrayOutputStream();
				response.getEntity().writeTo(content);
				String ret = new String(content.toString()).trim();
				content.close();
				requestCallback.onSuccess(ret);
			}else{
				
				requestCallback.onFail("{\"success\":false,\"msg\":\"("+statusCode+")响应异常\"}");
			}
		} catch(IllegalArgumentException e){
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"连接错误\"}");
		} catch(ConnectTimeoutException e){
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"连接超时\"}");
		} catch(SocketTimeoutException e){
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"读取超时\"}");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"编码错误\"}");
		} catch(HttpHostConnectException e) {
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"连接错误\"}");
		} catch(ClientProtocolException e){
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"客户端协议异常\"}");
		}catch(IOException e) {
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"数据读取异常\"}");
		}
		
		super.run();
	}
	
	
	private DefaultHttpClient httpClient;
	private List<RequestParams> params;
}

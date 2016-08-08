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
	 * @param url  �����ַ
	 * @param params �������
	 * @param requestResultCallback ����ص�����
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
			// ��������ʱʱ��
			this.request.getParams().setParameter("http.connection.timeout", this.connectTimeout);
			// �������ӳ�ʱʱ��
			this.request.getParams().setParameter("http.socket.timeout", this.readTimeout);
			// �����������
			if(null != params && params.size() > 0){
				List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
				for(RequestParams param:params){
					list.add(new BasicNameValuePair(param.getName(), param.getValue()));
				}
				((HttpPost)this.request).setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			}
			// ��Ӧ����
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
				
				requestCallback.onFail("{\"success\":false,\"msg\":\"("+statusCode+")��Ӧ�쳣\"}");
			}
		} catch(IllegalArgumentException e){
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"���Ӵ���\"}");
		} catch(ConnectTimeoutException e){
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"���ӳ�ʱ\"}");
		} catch(SocketTimeoutException e){
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"��ȡ��ʱ\"}");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"�������\"}");
		} catch(HttpHostConnectException e) {
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"���Ӵ���\"}");
		} catch(ClientProtocolException e){
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"�ͻ���Э���쳣\"}");
		}catch(IOException e) {
			e.printStackTrace();
			requestCallback.onFail("{\"success\":false,\"msg\":\"���ݶ�ȡ�쳣\"}");
		}
		
		super.run();
	}
	
	
	private DefaultHttpClient httpClient;
	private List<RequestParams> params;
}

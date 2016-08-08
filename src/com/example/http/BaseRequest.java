package com.example.http;

import org.apache.http.client.methods.HttpUriRequest;


public class BaseRequest implements Runnable{

	HttpUriRequest request = null;
	
	protected int success = 200;
	
	protected String url = null;
	
	protected int connectTimeout = 30000;
	
	protected int readTimeout = 30000;
	
	protected RequestResultCallback requestCallback = null;
	
	@Override
	public void run() {
		
	}

	protected void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	protected void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public HttpUriRequest getRequest() {
		return this.request;
	}
}

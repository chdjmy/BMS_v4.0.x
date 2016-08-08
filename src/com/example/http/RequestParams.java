package com.example.http;

public class RequestParams {
	
	public RequestParams(String name,String value){
		this.name = name;
		this.value = value;
	}
	// 参数名
	private String name;
	// 值
	private String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}

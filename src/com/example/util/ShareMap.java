package com.example.util;

import java.util.HashMap;
import java.util.Map;

public class ShareMap{
	private ShareMap(){}
	private static ShareMap instance;
	public static ShareMap getInstance(){
		if(instance==null){
			instance = new ShareMap();
		}
		return instance;
	}
	
	public final Map<String,Double> map = new HashMap<String,Double>();
	
	public void put(String key,Double value){
		if(map.get(key)==null){
			map.put(key, value);
		}
	}
	public Double get(String key){
		if(map.get(key)!=null){
			return map.get(key);
		}else{
			return -1.0;
		}
	}
	public void remove(String key){
		map.remove(key);
	}
	public void clear(){
		map.clear();
	}
	
	public Map<String,Double> getMap(){
		return map;
	}
	
	
}

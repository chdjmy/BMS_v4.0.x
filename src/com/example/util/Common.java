package com.example.util;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.bean.SettingBean;
import com.example.dao.SettingDao;

@SuppressLint("SimpleDateFormat")
public class Common {
	public static String getNowDate(String format){
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(new Date());
	}
	public static String getPk(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String getGzCode(){
		String date = getNowDate(YMDHMS);
		return "M_GZ"+date.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
	}
	
	public static SettingBean getSetting(Context context){
		// ªÒ»°Õ¯¬Á…Ë÷√
		SettingDao settingDao = new SettingDao(context);
		List<SettingBean> beans = settingDao.find();
		SettingBean settingBean = null;
		if(null != beans && beans.size() > 0){
			settingBean = beans.get(0);
		}else{
			settingBean = new SettingBean();
		}
		return settingBean;
	}
	
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String YMD = "yyyy-MM-dd";
	public static final String YMDHM = "yyyy-MM-dd HH:mm";
}

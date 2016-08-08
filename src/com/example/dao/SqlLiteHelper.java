package com.example.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.example.bean.BridgeBean;
import com.example.bean.DataUploadIdMapBean;
/*
import com.mv.bean.AddressBean;
import com.mw.bean.BranchBean;
import com.mw.bean.EquipBean;
import com.mw.bean.EquipfixlistBean;
import com.mw.bean.EquipmaintainplanBean;
import com.mw.bean.FaultsheetBean;
import com.mw.bean.FaulttypeBean;
import com.mw.bean.MaintainBean;
import com.mw.bean.MaintainequiplistBean;
import com.mw.bean.MaintainplanBean;
import com.mw.bean.SessionBean;*/
import com.example.bean.SettingBean;
import com.example.bean.UsersBean;
import com.example.bean.UsualExamBean;

import com.example.common.dao.DBHelper;

/**
 * sqlLite 帮助类
 * 
 * @author minghui.wang
 */
public class SqlLiteHelper extends DBHelper {
	// 数据库名称
	private final static String DATABASE_NAME = "bms_tablet_db.db";

	// 数据库版本
	private final static int DATABASE_VERSION = 2;
	
	// 需要生成数据库表的类的数组
	/*
	private final static Class<?>[] modelClasses = {UsersBean.class, BranchBean.class, AddressBean.class, 
		                                            EquipBean.class, MaintainplanBean.class, EquipmaintainplanBean.class, 
		                                            MaintainBean.class, MaintainequiplistBean.class, FaultsheetBean.class, 
		                                            FaulttypeBean.class, EquipfixlistBean.class ,SettingBean.class,
		                                            SessionBean.class};*/
	private final static Class<?>[] modelClasses = {SettingBean.class,DataUploadIdMapBean.class};

	public SqlLiteHelper(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION, modelClasses);
	}
   
	
	public SqlLiteHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion, Class<?>[] modelClasses) {
		super(context, databaseName, factory, databaseVersion, modelClasses);
	}

}

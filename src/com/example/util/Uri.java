package com.example.util;

import org.apache.commons.lang.StringUtils;

import android.content.Context;

/**
 * �ӿ���
 * @author minghui.wang
 * */
public class Uri {
	
	public Uri(){
		
	}
	
	public Uri(Context context){
		this.context = context;
	}
	
	private Context context;
	
	/**
	 * ��֤KEY ��  �ӿ�KEY һ��
	 * */
	public static final String KEY = "scan";
	/**
	 * �ӿ�Ӧ��
	 * */
	public static final String WEB_APP = "BMS";
	
	public static final String KEY_SUCC = "success";
	
	public static final String KEY_MSG = "msg";

	public static final String KEY_DATA = "data";
	
	public static final String KEY_FLAG = "flag";
	
	
	public static final String TAG = "mw.scan.log";
	/**
	 * @param ip      ip 
	 * @param port    �˿�
	 * @param method  ����
	 * */
	public static String getUri(String ip, String port, String web_app, String method){
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://").append(ip);
		if(StringUtils.isNotEmpty(port)){
			buffer.append(":").append(port);
		}
		buffer.append("/").append(web_app).append("/").append(method);
		System.out.println(buffer.toString());
		return buffer.toString();
	}
	public static String getUri(String method){
		return "";
	}
	
	/**
	 * ��ʼ���ӿ�
	 * */
	public class Init{
		/**
		 * ��ȡ��ǰ�����ϵͳʱ��
		 * */
		public static final String GET_CURRENTTIME = "/json/Mobile_getCurrentTime.action";
		/*
		 * �û���Ϣ
		 */
		public static final String USERS_INFO = "json/Mobile_initUsers.action";
		public static final int MSG_USERS_INFO = 1;
		/*
		 * ����������Ϣ
		 */
		public static final String BRIDGE_INFO = "json/Mobile_initBridge.action";
		public static final int MSG_BRIDGE_INFO = 2;
		public static final String BRIDGE_INFO_PAGE = "json/Mobile_initBridgePage.action";
		public static final int MSG_BRIDGE_INFO_PAGE = 3;
		/*
		 * ���������Լ��
		 */
		public static final String USUALEXAM_INFO = "json/Mobile_initUsualExam.action";
		public static final int MSG_USUALEXAM_INFO = 4;
		public static final String USUALEXAM_INFO_PAGE = "json/Mobile_initUsualExamPage.action";
		public static final int MSG_USUALEXAM_INFO_PAGE = 5;

		/*
		 * ����������Ϣ
		 */
		public static final String CULVERT_INFO = "/json/Mobile_initCulvert.action";
		public static final int MSG_CULVERT_INFO = 6;
		public static final String CULVERT_INFO_PAGE = "/json/Mobile_initCulvertPage.action";
		public static final int MSG_CULVERT_INFO_PAGE = 7;
		/*
		 *���������Լ�� 
		 */
		public static final String CUSUALEXAM_INFO = "/json/Mobile_initCusualExam.action";
		public static final int MSG_CUSUALEXAM_INFO = 8;
		public static final String CUSUALEXAM_INFO_PAGE = "/json/Mobile_initCusualExamPage.action";
		public static final int MSG_CUSUALEXAM_INFO_PAGE = 9;
		
		/*
		 * ���ϻ�����Ϣ
		 */
		public static final String MATERIAL_INFO = "json/Mobile_initMaterial.action";
		public static final int MSG_MATERIAL_INFO = 10;
		
		/*
		 *ά������
		 */
		public static final String MAINTAIN_INFO = "/json/Mobile_initMaintain.action";
		public static final int MSG_MAINTAIN_INFO = 11;
		public static final String MAINTAIN_INFO_PAGE = "/json/Mobile_initMaintainPage.action";
		public static final int MSG_MAINTAIN_INFO_PAGE = 12;
		
		/*
		 * ά�޲�����Ϣ
		 */
		public static final String MAINTAINMATERIAL_INFO = "json/Mobile_initMaintainMaterial.action";
		public static final int MSG_MAINTAINMATERIAL_INFO = 13;
		
		/*
		 * ����������Ϣ
		 */
		public static final String PROJECT_INFO = "json/Mobile_initProject.action";
		public static final int MSG_PROJECT_INFO = 27;
		
	
		/**
		 * ��ʼ��ʼ��
		 * */
		public static final int MSG_START_INIT = 200;
		/**
		 * ������ʼ��
		 * */
		public static final int MSG_END_INIT = 201;
	}
	
	/**
	 * �ϴ���Ϣ
	 * */
	public class Upload{
		/*
		 * ���������Լ�������ϴ�
		 */
		public static final String BRIDGEUSUALEXAM_INFO = "json/Mobile_upLoadBridgeUsualExam.action";
		public static final int MSG_BRIDGEUSUALEXAM_INFO = 14;
		 //���������Լ����Ƭ�ϴ�
		public static final String BRIDGEUSUALEXAM_PHOTO = "json/photoUpload.action";
		public static final int MSG_BRIDGEUSUALEXAM_PHOTO = 15;
		
		/*
		 * ���������Լ�������ϴ�
		 */
		public static final String CULVERTUSUALEXAM_INFO = "json/Mobile_upLoadCulvertUsualExam.action";
		public static final int MSG_CULVERTUSUALEXAM_INFO = 16;
		 //���������Լ����Ƭ�ϴ�
		public static final String CULVERTUSUALEXAM_PHOTO = "json/photoUpload.action";
		public static final int MSG_CULVERTUSUALEXAM_PHOTO = 17;
		
		/*
		 * ά�޲������������ϴ�
		 */
		public static final String MATERIAL_INFO = "json/Mobile_upLoadMaterial.action";
		public static final int MSG_MATERIAL_INFO = 18;

		/*
		 * ά��������Ϣ�ϴ�
		 */
		public static final String MAINTAINCHECK_INFO = "json/Mobile_upLoadMaintainCheck.action";
		public static final int MSG_MAINTAINCHECK_INFO = 19;
		 //ά��������Ƭ�ϴ�
		public static final String MAINTAINCHECK_PHOTO = "json/photoUpload.action";
		public static final int MSG_MAINTAINCHECK_PHOTO = 20;
		//ά�����ղ����ϴ�
		public static final String MAINTAINCHECK_MATERIAL = "json/Mobile_upLoadMaintainCheckMaterial.action";
		public static final int MSG_MAINTAINCHECK_MATERIAL = 21;
		
		/*
		 * ά�޼ƻ���Ϣ�ϴ�
		 */
		public static final String MAINTAINPLAN_INFO = "json/Mobile_upLoadMaintainPlan.action";
		public static final int MSG_MAINTAINPLAN_INFO = 22;
		//ά�����ղ����ϴ�
		public static final String MAINTAINPLAN_MATERIAL = "json/Mobile_upLoadMaintainPlanMaterial.action";
		public static final int MSG_MAINTAINPLAN_MATERIAL = 23;
		
		
		public static final int MSG_START_UPLOAD = 24;
		public static final int MSG_END_UPLOAD = 25;
		
		public static final int MSG_UPLOAD_NUM = 26;
	}
	
	
	
	/**
	 * �ϱ��ӿ���
	 * */
	public class Report{
		public static final String FAULTSHEET = "Mobile_reportFaultsheet.action";
	}
	
	
	
}

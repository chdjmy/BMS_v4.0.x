package com.example.bms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.CulvertUsualExamBean;
import com.example.bean.DataUploadIdMapBean;
import com.example.bean.DataUploadItem;
import com.example.bean.MaintainTaskBean;
import com.example.bean.SettingBean;
import com.example.bean.UsualExamBean;
import com.example.dao.CulvertUsualExamDao;
import com.example.dao.DataUploadIdMapDao;
import com.example.dao.MaintainMaterialDao;
import com.example.dao.MaintainTaskDao;
import com.example.dao.MaterialDao;
import com.example.dao.UsualExamDao;
import com.example.http.UploadUtil;
import com.example.util.Common;
import com.example.util.Net;
import com.example.util.Uri;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DataUploadActivity extends BaseActivity {

	private ListView listViewBridgeUsualExam;
	private MyListAdapter adapter;
	private Handler handler;
	private List<DataUploadItem> listDataUpload;
	
	//�������б�־λ 1-���������Լ�飬2-���������Լ�飬3-ά�޲��ϣ�4-ά�����գ�5-ά�޼ƻ�
	private int usual_num = 0;
	private int cusual_num = 0;
	private int material_num = 0;
	private int plan_num = 0;
	private int check_num = 0;
	
	//�ϴ��߳̿���Ƶ�ʺ�ѭ���ȴ�ʱ�����
	private final int FREQUENCY = 2;   
	private final int WAIT_TIME = 500;//��λ����
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_upload);
		//���ϴ�������
		listDataUpload = new ArrayList<DataUploadItem>();
		
		//���������Լ���ϴ�����1
		UsualExamDao usualExamDao = new UsualExamDao(this);
		List<UsualExamBean> listUsualExams = usualExamDao.findToUpload(getDepCode());
		for(int i=0;i<listUsualExams.size();i++){
			UsualExamBean bean = listUsualExams.get(i);
			DataUploadItem item = new DataUploadItem();
			item.setDataType(1);
			item.setDataId(bean.getUsualExamId());
			item.setBridgeCode(bean.getBridgeCode());
			item.setBridgeName(bean.getBridgeName());
			item.setCheckDate(bean.getCheckDate());
			item.setNoter(bean.getNoter());
			listDataUpload.add(item);
		}
		
		//���������Լ���ϴ�����2
		CulvertUsualExamDao cusualExamDao = new CulvertUsualExamDao(this);
		List<CulvertUsualExamBean> listCusualExam = cusualExamDao.findToUpload(getDepCode());
		for(int i=0;i<listCusualExam.size();i++){
			CulvertUsualExamBean bean = listCusualExam.get(i);
			DataUploadItem item = new DataUploadItem();
			item.setDataType(2);
			item.setDataId(bean.getCusualExamId());
			item.setBridgeCode(bean.getCulvertCode());
			item.setBridgeName(bean.getCulvertName());
			item.setCheckDate(bean.getCheckDate());
			item.setNoter(bean.getNoter());
			listDataUpload.add(item);
		}
		
		//ά�޼ƻ��ϴ�5
		MaintainTaskDao maintainDao = new MaintainTaskDao(this);
		List<MaintainTaskBean> listMaintain = maintainDao.findPlanToUpload(getDepCode());
		for(int i=0;i<listMaintain.size();i++){
			MaintainTaskBean bean = listMaintain.get(i);
			DataUploadItem item = new DataUploadItem();
			item.setDataType(5);
			item.setDataId(String.valueOf(bean.getMaintainTaskId()));
			item.setBridgeCode(bean.getMaintainCode());
			item.setBridgeName(bean.getBridgeName());
			item.setCheckDate(bean.getSubmitDate());
			item.setNoter(bean.getPlanner());
			listDataUpload.add(item);
		}
		
		//ά�������ϴ�4
		List<MaintainTaskBean> listMaintainCheck = maintainDao.findCheckToUpload(getDepCode());
		for(int i=0;i<listMaintainCheck.size();i++){
			MaintainTaskBean bean = listMaintainCheck.get(i);
			DataUploadItem item = new DataUploadItem();
			item.setDataType(4);
			item.setDataId(String.valueOf(bean.getMaintainTaskId()));
			item.setBridgeCode(bean.getMaintainCode());
			item.setBridgeName(bean.getBridgeName());
			item.setCheckDate(bean.getSubmitDate());
			item.setNoter(bean.getPlanner());
			listDataUpload.add(item);
		}
		
		//��ʾ�б�
		listViewBridgeUsualExam = (ListView)findViewById(R.id.listViewDataUploadBridgeUsualExam);
		adapter = new MyListAdapter(DataUploadActivity.this,   
                R.layout.listitem_dataupload_usual,   
                listDataUpload); 
		listViewBridgeUsualExam.setAdapter(adapter);
		
		handler = new UploadDataHandler(this);
		
	}
	
	//������ʾ�б�Ľ���
	private class MyListAdapter extends ArrayAdapter<DataUploadItem>{ 
	    private int resource; 
	    public MyListAdapter(Context context, int resourceId, List<DataUploadItem> objects) { 
	        super(context, resourceId, objects); 
	        // ��¼�����Ժ�ʹ�� 
	        resource = resourceId; 
	    }
	    public View getView(final int position, View convertView, ViewGroup parent) { 
	        LinearLayout listView; 
	        // ��ȡ���� 
	        DataUploadItem b = getItem(position); 
	        String bridgeCode = b.getBridgeCode()+"  "+b.getBridgeName();
	        String noter = "��¼�ˣ�"+b.getNoter();
	        String submitDate = "�ύʱ�䣺"+b.getCheckDate();
	        String dataType=null;
	        switch(b.getDataType()){
	        case 1:
	        	dataType  = "�������ͣ����������Լ��";
	        	break;
	        case 2:
	        	dataType  = "�������ͣ����������Լ��";
	        	break;
	        case 5:
	        	dataType  = "�������ͣ�ά�޼ƻ�";
	        	break;
	        case 4:
	        	dataType  = "�������ͣ�ά������";
	        	break;
	        }
	
	        if(convertView == null) { 
	            listView = new LinearLayout(getContext()); 
	            String inflater = Context.LAYOUT_INFLATER_SERVICE; 
	            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater); 
	            vi.inflate(resource, listView, true); 
	        } else { 
	            listView = (LinearLayout)convertView; 
	        }
	
	        // ����Զ������� 
	        TextView item_bridgeCode = (TextView) listView.findViewById(R.id.bridgeCode); 
	        TextView item_noter = (TextView) listView.findViewById(R.id.bridgeName); 
	        TextView item_dataType = (TextView) listView.findViewById(R.id.dataType);
	        TextView item_submitDate = (TextView) listView.findViewById(R.id.submitDate); 
	        Button btn_info = (Button) listView.findViewById(R.id.btn_info); 
	        Button btn_add = (Button) listView.findViewById(R.id.btn_add);
	        
	        item_bridgeCode.setText(bridgeCode);
	        item_noter.setText(noter);
	        item_dataType.setText(dataType);
	        item_submitDate.setText(submitDate);
	        
	        btn_info.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					switch(listDataUpload.get(position).getDataType()){
					case 1:
						intent.setClass(DataUploadActivity.this,UsualExamDetailActivity.class);
						intent.putExtra("usualExamId", listDataUpload.get(position).getDataId());
						break;
					case 2:
						intent.setClass(DataUploadActivity.this, CulvertUsualExamDetailActivity.class);
						intent.putExtra("usualExamId", listDataUpload.get(position).getDataId());
						intent.putExtra("flag", 0);
						break;
					case 5:
						intent.setClass(DataUploadActivity.this, MaintainPlanAddActivity.class);
						intent.putExtra("maintainId", listDataUpload.get(position).getDataId());
						intent.putExtra("interType", "query");
						break;
					case 4:
						intent.setClass(DataUploadActivity.this, MaintainCheckAcceptActivity.class);
						intent.putExtra("maintainId", listDataUpload.get(position).getDataId());
						intent.putExtra("interType", "query");
						break;
					default:
						break;
					}
					startActivity(intent);
				}
			});
	        btn_add.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					switch(listDataUpload.get(position).getDataType()){
					case 1:
						intent.setClass(DataUploadActivity.this,UsualExamAddActivity.class);
						intent.putExtra("usualExamId", listDataUpload.get(position).getDataId());
						intent.putExtra("interType", 1);
						break;
					case 2:
						intent.setClass(DataUploadActivity.this, CulvertUsualExamDetailActivity.class);
						intent.putExtra("usualExamId", listDataUpload.get(position).getDataId());
						intent.putExtra("flag", 2);
						break;
					case 5:
						intent.setClass(DataUploadActivity.this, MaintainPlanAddActivity.class);
						intent.putExtra("maintainId", listDataUpload.get(position).getDataId());
						intent.putExtra("interType", "change");
						break;
					case 4:
						intent.setClass(DataUploadActivity.this, MaintainCheckAcceptActivity.class);
						intent.putExtra("maintainId", listDataUpload.get(position).getDataId());
						intent.putExtra("interType", "change");
						break;
					default:
						break;	
					}
					startActivity(intent);
				}
			});
	
	        return listView; 
	    }
	}
	//�˵� �ϴ�����
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.data_upload, menu);//�޸�
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.data_menu_submit) {
			if(CheckNetworkState()){
				handler.post(new uploadThread());
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	//�ϴ����ݵ��߳�
	class uploadThread implements Runnable{
		@Override
		public void run() {
			if(Net.getSingle(DataUploadActivity.this).netState()){
				AlertDialog.Builder builder = new AlertDialog.Builder(DataUploadActivity.this);
				builder.setTitle("��ʾ");
				builder.setMessage("ȷ���ϴ�������?");
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Message msg = handler.obtainMessage();
						msg.what = Uri.Upload.MSG_START_UPLOAD;
						handler.sendMessage(msg);//���Ϳ�ʼ��Ϣ�ϴ��İ�ť
						uploadData();//�ϴ�����
					}
				});
				builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();					
					}
				});
				builder.show();
			}else{
				Toast.makeText(DataUploadActivity.this,"����������", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	private void uploadData(){
		// ��ȡ��������
		SettingBean settingBean = Common.getSetting(this);
		
		//�����Լ�������ϴ�
		UsualExamDao usualExamDao = new UsualExamDao(this);
		usual_num = usualExamDao.uploadNum(getDepCode());
		if(usual_num!=0){
			usualExamDao.upload(settingBean, handler,getDepCode());
		}
		
		//���������Լ���ϴ�
		CulvertUsualExamDao cusualExamDao =  new CulvertUsualExamDao(this);
		cusual_num = cusualExamDao.uploadNum(getDepCode());
		if(cusual_num!=0){
			cusualExamDao.upload(settingBean, handler,getDepCode());
		}
		
		//ά�޲���
		MaterialDao materialDao = new MaterialDao(this);
		material_num = materialDao.uploadNum();
		if(material_num!=0){
			materialDao.upload(settingBean, handler);
		}
		
		//ά������
		MaintainTaskDao maintainTaskDao = new MaintainTaskDao(this);
		check_num = maintainTaskDao.uploadCheckNum(getDepCode());
		if(check_num!=0){
			maintainTaskDao.uploadCheck(settingBean, handler,getDepCode());
		}
		//ά�޼ƻ���Ϣ�ϴ�
		plan_num = maintainTaskDao.uploadPlanNum(getDepCode());
		if(plan_num!=0){
			maintainTaskDao.uploadPlan(settingBean, handler,getDepCode());
		}
		
		if((usual_num|cusual_num|material_num|check_num|plan_num)==0){
			Message msg = handler.obtainMessage();
			msg.what = Uri.Upload.MSG_END_UPLOAD;
			handler.sendMessage(msg);
		}
		
	}
	
	
	//handler
	class UploadDataHandler extends Handler{
		
		private ProgressDialog dialog;
		private int msg_num = 0 ;
		private Context context;
		
		public UploadDataHandler(Context context){
			this.context = context;
		}
		
		@Override
		public void handleMessage(Message msg) {
			
			if(msg.what == Uri.Upload.MSG_UPLOAD_NUM){//Ҫ�ϴ����ݵ�����������ǰ̨��ʾ
				//webView.loadUrl("javascript:setNum("+msg.getData().getInt("maintainNum")+","+msg.getData().getInt("faultsheetWhNum")+","+msg.getData().getInt("faultsheetGzNum")+","+msg.getData().getInt("usersNum")+")");
				return;
			}
			
			// http ��������ʶ
			boolean bl = false;
			// ����ӿ����ݽ����ʶ
			boolean dataBl = false;
			JSONObject object = null;
			JSONArray array = null;
			if(msg.what != Uri.Upload.MSG_START_UPLOAD && 
			   msg.what != Uri.Upload.MSG_END_UPLOAD &&
			   msg.what != Uri.Init.MSG_END_INIT){
				bl = msg.getData().getBoolean(Uri.KEY_FLAG);//�����Ϣ��KEY_FLAG
				if(!bl){
					JSONObject error = JSONObject.parseObject(msg.getData().getString(Uri.KEY_MSG));
					Toast.makeText(context,  error.getString(Uri.KEY_MSG), Toast.LENGTH_LONG).show();
				}else{
					String data = msg.getData().getString(Uri.KEY_MSG);
					try {
						object = JSONObject.parseObject(data);
					} catch (Exception e) {
						object = null;
					}
					try {
						dataBl = object.getBoolean(Uri.KEY_SUCC);
					} catch (Exception e) {
						dataBl = false;
					}
					if(dataBl){
						array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
					}
				}
			}
		
			
			switch (msg.what) {
					
				//�����Լ��������ϴ��󷵻صĽ��*************************
				case Uri.Upload.MSG_BRIDGEUSUALEXAM_INFO:
					if(bl && dataBl){
						//webView.loadUrl("javascript:setDataSuccImage("+ true +",'user_info')");
					}else{
						//webView.loadUrl("javascript:setDataSuccImage("+ false +",'user_info')");
					}
					if(msg.getData().getBoolean("data_flag")){//�ж���û����Ҫ�ϴ������ݣ�û�д��ϴ�����Ϊtrue����Daoֱ�ӷ���
						Toast.makeText(context,  msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_SHORT).show();
					}else{
						if(null != array && array.size() > 0){
							int clientId = array.getJSONObject(0).getInteger("clientId");
							int serverId = array.getJSONObject(1).getInteger("serverId");
							//�ж��Ƿ���ҪIDӳ��
//							if(clientId!=serverId){
//								DataUploadIdMapDao idMapDao = new DataUploadIdMapDao(context);
//								DataUploadIdMapBean idMapBean  = new DataUploadIdMapBean();
//								idMapBean.setClientId(clientId);
//								idMapBean.setServerId(serverId);
//								idMapBean.setDataType(1);
//								idMapDao.insert(idMapBean);
//							}
							UsualExamDao dao = new UsualExamDao(context);
							dao.updateUploadbyId(clientId);//��isupLoad��־λ��Ϊ1
							//�ϴ���Ƭ��Ϣ
							SettingBean settingBean = Common.getSetting(DataUploadActivity.this);
							dao.uploadPhoto(settingBean, this, clientId, serverId);
						}	
					}
					setUploadNum(1);
					break;
				case Uri.Upload.MSG_BRIDGEUSUALEXAM_PHOTO:
					if(bl && dataBl){
						//webView.loadUrl("javascript:setDataSuccImage("+ true +",'user_info')");
					}else{
						//webView.loadUrl("javascript:setDataSuccImage("+ false +",'user_info')");
					}
					if(null != array && array.size() > 0){
						//int serverMaxId = array.getJSONObject(0).getInteger("serverMaxId");
						//UsualExamDao dao = new UsualExamDao(context);
						//dao.add(array);
						//String savePath = array.getJSONObject(0).getString("savaPath");
						//System.out.print(savePath);
						//Toast.makeText(context, array.getJSONObject(0).getString("savaPath"), Toast.LENGTH_SHORT).show();
						
					}		
					break;
				case Uri.Upload.MSG_CULVERTUSUALEXAM_INFO:
					if(bl && dataBl){
						//webView.loadUrl("javascript:setDataSuccImage("+ true +",'user_info')");
					}else{
						//webView.loadUrl("javascript:setDataSuccImage("+ false +",'user_info')");
					}
					if(msg.getData().getBoolean("data_flag")){//�ж���û����Ҫ�ϴ������ݣ�û�д��ϴ�����Ϊtrue����Daoֱ�ӷ���
						Toast.makeText(context,  msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_SHORT).show();
					}else{
						if(null != array && array.size() > 0){
							int clientId = array.getJSONObject(0).getInteger("clientId");
							int serverId = array.getJSONObject(1).getInteger("serverId");
							//�ж��Ƿ���ҪIDӳ��
//							if(clientId!=serverId){
//								DataUploadIdMapDao idMapDao = new DataUploadIdMapDao(context);
//								DataUploadIdMapBean idMapBean  = new DataUploadIdMapBean();
//								idMapBean.setClientId(clientId);
//								idMapBean.setServerId(serverId);
//								idMapBean.setDataType(2);//���������Լ��
//								idMapDao.insert(idMapBean);
//							}
							CulvertUsualExamDao dao = new CulvertUsualExamDao(context);
							dao.updateUploadbyId(clientId);//��isupLoad��־λ��Ϊ1
							//�ϴ���Ƭ��Ϣ
							SettingBean settingBean = Common.getSetting(DataUploadActivity.this);
							dao.uploadPhoto(settingBean, this, clientId, serverId);
						}	
					}
					setUploadNum(2);
					break;
				case Uri.Upload.MSG_MATERIAL_INFO:
					if(bl && dataBl){
						//webView.loadUrl("javascript:setDataSuccImage("+ true +",'user_info')");
					}else{
						//webView.loadUrl("javascript:setDataSuccImage("+ false +",'user_info')");
					}
					if(msg.getData().getBoolean("data_flag")){//�ж���û����Ҫ�ϴ������ݣ�û�д��ϴ�����Ϊtrue����Daoֱ�ӷ���
						Toast.makeText(context,  msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_SHORT).show();
					}else{
						if(null != array && array.size() > 0){
							int clientId = array.getJSONObject(0).getInteger("clientId");
							int serverId = array.getJSONObject(1).getInteger("serverId");
							//�ж��Ƿ���ҪIDӳ��
							if(clientId!=serverId){
								DataUploadIdMapDao idMapDao = new DataUploadIdMapDao(context);
								DataUploadIdMapBean idMapBean  = new DataUploadIdMapBean();
								idMapBean.setClientId(clientId);
								idMapBean.setServerId(serverId);
								idMapBean.setDataType(3);//ά�޲���
								idMapDao.insert(idMapBean);
							}
							MaterialDao dao = new MaterialDao(context);
							dao.updateUploadbyId(clientId);//��isupLoad��־λ��Ϊ1
						}
					}
					setUploadNum(3);
					break;	
					
				case Uri.Upload.MSG_MAINTAINCHECK_INFO:
					if(bl && dataBl){
						//webView.loadUrl("javascript:setDataSuccImage("+ true +",'user_info')");
					}else{
						//webView.loadUrl("javascript:setDataSuccImage("+ false +",'user_info')");
					}
					if(msg.getData().getBoolean("data_flag")){//�ж���û����Ҫ�ϴ������ݣ�û�д��ϴ�����Ϊtrue����Daoֱ�ӷ���
						Toast.makeText(context,  msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_SHORT).show();
					}else{
						if(null != array && array.size() > 0){
							int clientId = array.getJSONObject(0).getInteger("clientId");
							int serverId = array.getJSONObject(1).getInteger("serverId");
							//�ж��Ƿ���ҪIDӳ��
//							if(clientId!=serverId){
//								DataUploadIdMapDao idMapDao = new DataUploadIdMapDao(context);
//								DataUploadIdMapBean idMapBean  = new DataUploadIdMapBean();
//								idMapBean.setClientId(clientId);
//								idMapBean.setServerId(serverId);
//								idMapBean.setDataType(2);//���������Լ��
//								idMapDao.insert(idMapBean);
//							}
							MaintainTaskDao dao = new MaintainTaskDao(context);
							dao.updateUploadCheckbyId(clientId);//��ischecked��־λ��Ϊ1
							//�ϴ���Ƭ��Ϣ
							SettingBean settingBean = Common.getSetting(DataUploadActivity.this);
							dao.uploadPhoto(settingBean, this, clientId, serverId);
							//����ά�����յĲ���
							MaintainMaterialDao daoMaterial = new MaintainMaterialDao(context);
							daoMaterial.uploadCheckMaterial(settingBean, this, clientId, serverId);
						}	
					}
					setUploadNum(4);
					break;
				case Uri.Upload.MSG_MAINTAINPLAN_INFO:
					if(bl && dataBl){
						//webView.loadUrl("javascript:setDataSuccImage("+ true +",'user_info')");
					}else{
						//webView.loadUrl("javascript:setDataSuccImage("+ false +",'user_info')");
					}
					if(msg.getData().getBoolean("data_flag")){//�ж���û����Ҫ�ϴ������ݣ�û�д��ϴ�����Ϊtrue����Daoֱ�ӷ���
						Toast.makeText(context,  msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_SHORT).show();
					}else{
						if(null != array && array.size() > 0){
							int clientId = array.getJSONObject(0).getInteger("clientId");
							int serverId = array.getJSONObject(1).getInteger("serverId");
							//�ж��Ƿ���ҪIDӳ��
//							if(clientId!=serverId){
//								DataUploadIdMapDao idMapDao = new DataUploadIdMapDao(context);
//								DataUploadIdMapBean idMapBean  = new DataUploadIdMapBean();
//								idMapBean.setClientId(clientId);
//								idMapBean.setServerId(serverId);
//								idMapBean.setDataType(2);//���������Լ��
//								idMapDao.insert(idMapBean);
//							}
							MaintainTaskDao dao = new MaintainTaskDao(context);
							dao.updateUploadPlanbyId(clientId);//��isupLoad��־λ��Ϊ1
							
							//����ά�����յĲ���
							SettingBean settingBean = Common.getSetting(DataUploadActivity.this);
							MaintainMaterialDao daoMaterial = new MaintainMaterialDao(context);
							daoMaterial.uploadPlanMaterial(settingBean, this, clientId, serverId);
						}	
					}
					setUploadNum(5);
					break;
				case Uri.Upload.MSG_START_UPLOAD://�ϴ���ʼʱ����Ϣ
					dialog = ProgressDialog.show(context, null,"�ϴ���,���Ժ�...", true, false);
					msg_num = 0;
					break;
				case Uri.Upload.MSG_END_UPLOAD://�ϴ������󣬹رս�����
					
					Toast.makeText(context,"�ϴ��������", Toast.LENGTH_LONG).show();
					if(dialog.isShowing()){
						dialog.dismiss();
					}
					listDataUpload.clear();
					adapter.notifyDataSetChanged();
					break;
		
				default:
					break;
				
			}
			
			super.handleMessage(msg);
		}
		
		
		private synchronized void setUploadNum(int key){
			switch(key){
			case 1:
				usual_num--;
				break;
			case 2:
				cusual_num--;
				break;
			case 3:
				material_num--;
				break;
			case 4:
				check_num--;
				break;
			case 5:
				plan_num--;
				break;
			}
			if((usual_num|cusual_num|material_num|check_num|plan_num)==0){
				Message msg = this.obtainMessage();
				msg.what = Uri.Upload.MSG_END_UPLOAD;
				this.sendMessage(msg);
			}
		}
		
	}
	
	
}




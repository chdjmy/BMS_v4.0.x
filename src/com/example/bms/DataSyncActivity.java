package com.example.bms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.SettingBean;
import com.example.dao.BridgeDao;
import com.example.dao.BridgeProjectDao;
import com.example.dao.CulvertDao;
import com.example.dao.CulvertUsualExamDao;
import com.example.dao.DataUploadIdMapDao;
import com.example.dao.MaintainMaterialDao;
import com.example.dao.MaintainTaskDao;
import com.example.dao.MaterialDao;
import com.example.dao.UsersDao;
import com.example.dao.UsualExamDao;
import com.example.util.Common;
import com.example.util.Net;
import com.example.util.Uri;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class DataSyncActivity extends BaseActivity {

	private Button init_btn_init, init_btn_back;
	private Handler handler;
	private HandlerThread handlerThread;
	private ImageView i1, i2, i3, i4, i5, i6, i7, i8, i9, i10,i11;
	private ProgressDialog dialog;
	
	//�ϴ��߳̿���Ƶ�ʺ�ѭ���ȴ�ʱ�����,�ȴ�WAIT_TIMEʱ�������WAIT_FREQUENCY���߳�
	private final int WAIT_TIME = 500;//��λ����
	private final int WAIT_FREQUENCY = 2;   
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_sync);

		handlerThread = new HandlerThread("handler_thread");
		handlerThread.start();
		handler = new InitHandler(this, handlerThread.getLooper());

		init_btn_init = (Button) findViewById(R.id.init_btn_init);
		init_btn_init.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(CheckNetworkState()){
					displayToast("��ʼͬ��");
					handler.post(new InitThread(DataSyncActivity.this));
				}
			}

		});
		init_btn_back = (Button) findViewById(R.id.init_btn_back);
		init_btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				handlerThread.quit();
				DataSyncActivity.this.finish();
			}

		});

		i1 = (ImageView) findViewById(R.id.imageView1);
		i2 = (ImageView) findViewById(R.id.imageView2);
		i3 = (ImageView) findViewById(R.id.imageView3);
		i4 = (ImageView) findViewById(R.id.imageView4);
		i5 = (ImageView) findViewById(R.id.imageView5);
		i6 = (ImageView) findViewById(R.id.imageView6);
		i7 = (ImageView) findViewById(R.id.imageView7);
		i8 = (ImageView) findViewById(R.id.imageView8);
		i9 = (ImageView) findViewById(R.id.imageView9);
		i10 = (ImageView) findViewById(R.id.imageView10);
		i11 = (ImageView) findViewById(R.id.imageView11);
	}

	// �Զ����߳��࣬ʵ��Runnable�ӿ�
	class InitThread implements Runnable {
		private Context context;

		InitThread(Context context) {
			this.context = context;
		}

		@Override
		public void run() {
			if (Net.getSingle(context).netState()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("��ʾ");
				builder.setMessage("�Ḳ��ԭ������,ȷ��ͬ��������?");
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// ��ʼͬ������ʾprocessBarDialog
								Message msg = handler.obtainMessage();
								msg.what = Uri.Init.MSG_START_INIT;
								handler.sendMessage(msg);
								// ��һ����ͬ���û���Ϣ
								SettingBean settingBean = Common
										.getSetting(context);
								new UsersDao(context).init(settingBean,handler);
							}
						});
				builder.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
								dialog.dismiss();
							}
						});
				builder.show();
			} else {
				Toast.makeText(context, "����������", Toast.LENGTH_LONG).show();
			}
		}
	}

	// �Զ���handler�࣬��дhandleMessage����
	class InitHandler extends Handler {

		private int msg_num = 0;
		
		private int maxPageBridge = 0;
		private int maxPageUsualExam = 0;
		private int maxPageculvert = 0;
		private int maxPageCusualExam = 0;
		private int maxPageMaintain = 0;
		
		private Context context;

		InitHandler(Context context, Looper looper) {
			super(looper);
			this.context = context;
		}

		@Override
		public void handleMessage(Message msg) {
			boolean bl = false;
			JSONObject object = null;
			if (msg.what != Uri.Init.MSG_START_INIT
					&& msg.what != Uri.Init.MSG_END_INIT) {
				bl = msg.getData().getBoolean(Uri.KEY_FLAG);
				if (!bl) {
					JSONObject error = JSONObject.parseObject(msg.getData().getString(Uri.KEY_MSG));
					Toast.makeText(context, error.getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
				}
				String data = msg.getData().getString(Uri.KEY_MSG);
				object = JSONObject.parseObject(data);
			}

			switch (msg.what) {
			case Uri.Init.MSG_USERS_INFO:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						UsersDao dao = new UsersDao(context);
						bl = dao.add(array);
						DataSyncActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								i1.setImageResource(R.drawable.finish);
							}
						});
					} else {
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
					}
				}
				setMsgNum();
				break;

			// ������Ϣ��ʼ��-���ҳ��
			case Uri.Init.MSG_BRIDGE_INFO:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						if (null != array && array.size() > 0) {
							SettingBean settingBean = Common.getSetting(context);
							maxPageBridge = array.getJSONObject(0).getInteger("total");
							BridgeDao bridgeDao = new BridgeDao(context);
							bridgeDao.delete();// ɾ����������
							for (int i = 1; i <= maxPageBridge; i++) {
								if(i%WAIT_FREQUENCY==0){
								try {
									Thread.sleep(WAIT_TIME);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								}
								bridgeDao.init(settingBean, this, i);
							}
						} else {
							setMsgNum();
						}
					} else {
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
						setMsgNum();
					}
				} else {
					setMsgNum();
				}
				break;
			// ������Ϣ��ʼ��-��ҳ��ʼ������
			case Uri.Init.MSG_BRIDGE_INFO_PAGE:

				if (object.getBoolean(Uri.KEY_SUCC)) {
					JSONArray array = JSONArray.parseArray(object
							.getString(Uri.KEY_DATA));
					BridgeDao bridgeDao = new BridgeDao(context);
					bl = bridgeDao.add(array);

				} else {
					Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
					bl = object.getBoolean(Uri.KEY_SUCC);
				}
				maxPageBridge--;
				System.out.println("pageCount==maxPageBridge```" + maxPageBridge);
				// ���������з�ҳʱ
				if (0 == maxPageBridge) {
					DataSyncActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							i2.setImageResource(R.drawable.finish);
						}
					});
					
					setMsgNum();
				}
				break;
			// ���������Լ��-���ҳ��
			case Uri.Init.MSG_USUALEXAM_INFO:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						if (null != array && array.size() > 0) {
							SettingBean settingBean = Common.getSetting(context);
							maxPageUsualExam = array.getJSONObject(0).getInteger("total");
							UsualExamDao usualExamDao = new UsualExamDao(
									context);
							usualExamDao.delete();
							for (int i = 1; i <= maxPageUsualExam; i++) {
								if(i%WAIT_FREQUENCY==0){
								try {
									Thread.sleep(WAIT_TIME);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								}
								usualExamDao.init(settingBean, this, i);
							}
						} else {
							setMsgNum();
						}
					} else {
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
						setMsgNum();
					}
				} else {
					setMsgNum();
				}
				break;
			// ���������ԙz��-��ҳ�������
			case Uri.Init.MSG_USUALEXAM_INFO_PAGE:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						UsualExamDao usualExamDao = new UsualExamDao(context);
						bl = usualExamDao.add(array);
					} else {
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
					}
				}
				maxPageUsualExam--;
				System.out.println("pageCount==maxPageUsualExam```" + maxPageUsualExam);
				if (0 == maxPageUsualExam) {
					// ����ǰ��ͼ��
					DataSyncActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							i3.setImageResource(R.drawable.finish);
						}
					});
					setMsgNum();
				}
				break;
			// ������Ϣ��ʼ��-���ҳ��
			case Uri.Init.MSG_CULVERT_INFO:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object
								.getString(Uri.KEY_DATA));
						if (null != array && array.size() > 0) {
							SettingBean settingBean = Common
									.getSetting(context);
							maxPageculvert = array.getJSONObject(0).getInteger("total");
							CulvertDao culvertDao = new CulvertDao(context);
							culvertDao.delete();// ɾ����������
							for (int i = 1; i <= maxPageculvert; i++) {
								if(i%WAIT_FREQUENCY==0){
								try {
									Thread.sleep(WAIT_TIME);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								}
								culvertDao.init(settingBean, this, i);
							}
						} else {
							setMsgNum();
						}
					} else {
						Toast.makeText(context,
								msg.getData().getString(Uri.KEY_MSG),
								Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
						setMsgNum();
					}
				} else {
					setMsgNum();
				}
				break;
			// ������Ϣ��ʼ��-��ҳ��ʼ������
			case Uri.Init.MSG_CULVERT_INFO_PAGE:
				if (object.getBoolean(Uri.KEY_SUCC)) {
					JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
					CulvertDao culvertDao = new CulvertDao(context);
					bl = culvertDao.add(array);

				} else {
					Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
					bl = object.getBoolean(Uri.KEY_SUCC);
				}
				maxPageculvert--;
				System.out.println("pageCount==maxPageculvert```" + maxPageculvert);
				// ���������з�ҳʱ
				if (0 == maxPageculvert) {
					DataSyncActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							i4.setImageResource(R.drawable.finish);
						}
					});
					setMsgNum();
				}
				break;
			// ���������Լ��-���ҳ��
			case Uri.Init.MSG_CUSUALEXAM_INFO:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						if (null != array && array.size() > 0) {
							SettingBean settingBean = Common.getSetting(context);
							maxPageCusualExam = array.getJSONObject(0).getInteger("total");
							CulvertUsualExamDao cusualExamDao = new CulvertUsualExamDao(context);
							cusualExamDao.delete();
							for (int i = 1; i <= maxPageCusualExam; i++) {
								if(i%WAIT_FREQUENCY==0){
								try {
									Thread.sleep(WAIT_TIME);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								}
								cusualExamDao.init(settingBean, this, i);
							}
						} else {
							setMsgNum();
						}
					} else {
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
						setMsgNum();
					}
				} else {
					setMsgNum();
				}
				break;
			// ���������ԙz��-��ҳ�������
			case Uri.Init.MSG_CUSUALEXAM_INFO_PAGE:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						CulvertUsualExamDao cusualExamDao = new CulvertUsualExamDao(context);
						bl = cusualExamDao.add(array);
					} else {
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
					}
				}
				maxPageCusualExam--;
				System.out.println("pageCount==maxPage```" + maxPageCusualExam);
				if (0 == maxPageCusualExam) {
					// ����ǰ��ͼ��
					DataSyncActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							i5.setImageResource(R.drawable.finish);
						}
					});
					setMsgNum();
				}
				break;	
			case Uri.Init.MSG_MATERIAL_INFO:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						MaterialDao dao = new MaterialDao(context);
						bl = dao.add(array);
						// ����ǰ��ͼ��
						DataSyncActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								i6.setImageResource(R.drawable.finish);
							}
						});
					} else {
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
					}
				}
				setMsgNum();
				break;
			// ά����Ϣ-���ҳ��
			case Uri.Init.MSG_MAINTAIN_INFO:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						if (null != array && array.size() > 0) {
							SettingBean settingBean = Common.getSetting(context);
							maxPageMaintain = array.getJSONObject(0).getInteger("total");
							MaintainTaskDao maintainTaskDao = new MaintainTaskDao(context);
							maintainTaskDao.delete();
							for (int i = 1; i <= maxPageMaintain; i++) {
								if(i%WAIT_FREQUENCY==0){
								try {
									Thread.sleep(WAIT_TIME);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								}
								maintainTaskDao.init(settingBean, this, i);
							}
						} else {
							setMsgNum();
						}
					} else {
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
						setMsgNum();
					}
				} else {
					setMsgNum();
				}
				break;
			// ά����Ϣ-��ҳ�������
			case Uri.Init.MSG_MAINTAIN_INFO_PAGE:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						MaintainTaskDao maintainTaskDao = new MaintainTaskDao(context);
						bl = maintainTaskDao.add(array);
					} else {
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
					}
				}
				maxPageMaintain--;
				System.out.println("pageCount==maxPage```" + maxPageMaintain);
				if (0 == maxPageMaintain) {
					// ����ǰ��ͼ��
					DataSyncActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							i7.setImageResource(R.drawable.finish);
							i8.setImageResource(R.drawable.finish);
							i9.setImageResource(R.drawable.finish);
						}
					});
					setMsgNum();
				}
				break;		
			case Uri.Init.MSG_MAINTAINMATERIAL_INFO:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						MaintainMaterialDao dao = new MaintainMaterialDao(context);
						bl = dao.add(array);
						// ����ǰ��ͼ��
						DataSyncActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								i10.setImageResource(R.drawable.finish);
							}
						});
					} else {
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
					}
				}
				setMsgNum();
				break;
			case Uri.Init.MSG_PROJECT_INFO:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						BridgeProjectDao dao = new BridgeProjectDao(context);
						bl = dao.add(array);
						// ����ǰ��ͼ��
						DataSyncActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								i11.setImageResource(R.drawable.finish);
							}
						});
					} else {
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG),Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
					}
				}
				setMsgNum();
				break;	
				
				

			// ���������ƣ���ʼ��ʼ��
			case Uri.Init.MSG_START_INIT:
				DataSyncActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialog = ProgressDialog.show(context, null,
								"������,���Ժ�...", true, false);
					}
				});

				msg_num = 0;
				break;
			// ���������ƣ�������ʼ��
			case Uri.Init.MSG_END_INIT:
				DataSyncActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (dialog.isShowing()) {
							i3.setImageResource(R.drawable.finish);
							i4.setImageResource(R.drawable.finish);
							dialog.dismiss();
						}
					}
				});
				break;
			}

			super.handleMessage(msg);
		}

		// init�����õĴ���
		private synchronized void setMsgNum() {
			msg_num = msg_num + 1;
			switch(msg_num){
			/**����*/
			case 1://����û���Ϣ���أ���ȡ����ҳ��
			{
				SettingBean settingBean = Common.getSetting(context);
				new BridgeDao(context).initCount(settingBean,handler);
				break;
			}
			case 2://��ɷ�ҳ����������Ϣ����ȡ���������Լ������ҳ��
			{	
				SettingBean settingBean = Common.getSetting(context);
				new UsualExamDao(context).initCount(settingBean, handler);
				break;
			}
				
			/**����*/	
			case 3://��ɷ�ҳ�������������Լ�飬��ȡ����ҳ��
			{
				SettingBean settingBean = Common.getSetting(context);
				new CulvertDao(context).initCount(settingBean,handler);
				break;
			}
			case 4://��ɷ�ҳ���غ�����Ϣ����ȡ���������Լ������ҳ��
			{	
				SettingBean settingBean = Common.getSetting(context);
				new CulvertUsualExamDao(context).initCount(settingBean, handler);
				break;
			}
			
			/**ά��*/	
			case 5://��ɷ�ҳ���غ��������Լ�飬��ʼ���ز�����Ϣ
			{
				SettingBean settingBean = Common.getSetting(context);
				new MaterialDao(context).init(settingBean,handler);
				break;
			}
			case 6://������ز�����Ϣ����ȡά������
			{
				SettingBean settingBean = Common.getSetting(context);
				new MaintainTaskDao(context).initCount(settingBean, handler);
				break;
			}
			case 7://���ά����Ϣ��ҳ���أ���ʼ����ά�޲���
			{	
				SettingBean settingBean = Common.getSetting(context);
				new MaintainMaterialDao(context).init(settingBean, handler);
				break;
			}
			case 8://���ά����Ϣ��ҳ���أ���ʼ����ά�޲���
			{	
				SettingBean settingBean = Common.getSetting(context);
				new BridgeProjectDao(context).init(settingBean, handler);
				break;
			}
			
			
			/**����*/	
			case 9://��ɻ�ȡ���������Լ��ҳ������ҳ���غ��������Լ��
			{
				new DataUploadIdMapDao(context).delete();
				Toast.makeText(context, "����ͬ����ɡ�", Toast.LENGTH_LONG).show();
				Message msg = this.obtainMessage();
				msg.what = Uri.Init.MSG_END_INIT;
				this.sendMessage(msg);
				break;
			}	
			default:
				break;
			}
		}
		
	}

}

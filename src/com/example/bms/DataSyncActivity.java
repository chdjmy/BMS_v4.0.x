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
	
	//上传线程开启频率和循环等待时间控制,等待WAIT_TIME时间后，启动WAIT_FREQUENCY个线程
	private final int WAIT_TIME = 500;//单位毫秒
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
					displayToast("开始同步");
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

	// 自定义线程类，实现Runnable接口
	class InitThread implements Runnable {
		private Context context;

		InitThread(Context context) {
			this.context = context;
		}

		@Override
		public void run() {
			if (Net.getSingle(context).netState()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("提示");
				builder.setMessage("会覆盖原有数据,确认同步数据吗?");
				builder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 开始同步，显示processBarDialog
								Message msg = handler.obtainMessage();
								msg.what = Uri.Init.MSG_START_INIT;
								handler.sendMessage(msg);
								// 第一步先同步用户信息
								SettingBean settingBean = Common
										.getSetting(context);
								new UsersDao(context).init(settingBean,handler);
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
								dialog.dismiss();
							}
						});
				builder.show();
			} else {
				Toast.makeText(context, "请连接网络", Toast.LENGTH_LONG).show();
			}
		}
	}

	// 自定义handler类，重写handleMessage方法
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

			// 桥梁信息初始化-获得页数
			case Uri.Init.MSG_BRIDGE_INFO:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						if (null != array && array.size() > 0) {
							SettingBean settingBean = Common.getSetting(context);
							maxPageBridge = array.getJSONObject(0).getInteger("total");
							BridgeDao bridgeDao = new BridgeDao(context);
							bridgeDao.delete();// 删除桥梁数据
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
			// 桥梁信息初始化-分页初始化数据
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
				// 下载完所有分页时
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
			// 桥梁经常性检查-获得页数
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
			// 桥梁經常性檢查-分页获得数据
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
					// 更新前端图标
					DataSyncActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							i3.setImageResource(R.drawable.finish);
						}
					});
					setMsgNum();
				}
				break;
			// 涵洞信息初始化-获得页数
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
							culvertDao.delete();// 删除桥梁数据
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
			// 涵洞信息初始化-分页初始化数据
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
				// 下载完所有分页时
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
			// 涵洞经常性检查-获得页数
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
			// 涵洞經常性檢查-分页获得数据
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
					// 更新前端图标
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
						// 更新前端图标
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
			// 维修信息-获得页数
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
			// 维修信息-分页获得数据
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
					// 更新前端图标
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
						// 更新前端图标
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
						// 更新前端图标
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
				
				

			// 进度条控制，开始初始化
			case Uri.Init.MSG_START_INIT:
				DataSyncActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialog = ProgressDialog.show(context, null,
								"加载中,请稍候...", true, false);
					}
				});

				msg_num = 0;
				break;
			// 进度条控制，结束初始化
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

		// init被调用的次数
		private synchronized void setMsgNum() {
			msg_num = msg_num + 1;
			switch(msg_num){
			/**桥梁*/
			case 1://完成用户信息下载，获取桥梁页数
			{
				SettingBean settingBean = Common.getSetting(context);
				new BridgeDao(context).initCount(settingBean,handler);
				break;
			}
			case 2://完成分页下载桥梁信息，获取桥梁经常性检查数据页数
			{	
				SettingBean settingBean = Common.getSetting(context);
				new UsualExamDao(context).initCount(settingBean, handler);
				break;
			}
				
			/**涵洞*/	
			case 3://完成分页下载桥梁经常性检查，获取涵洞页数
			{
				SettingBean settingBean = Common.getSetting(context);
				new CulvertDao(context).initCount(settingBean,handler);
				break;
			}
			case 4://完成分页下载涵洞信息，获取涵洞经常性检查数据页数
			{	
				SettingBean settingBean = Common.getSetting(context);
				new CulvertUsualExamDao(context).initCount(settingBean, handler);
				break;
			}
			
			/**维修*/	
			case 5://完成分页下载涵洞经常性检查，开始下载材料信息
			{
				SettingBean settingBean = Common.getSetting(context);
				new MaterialDao(context).init(settingBean,handler);
				break;
			}
			case 6://完成下载材料信息，获取维修数量
			{
				SettingBean settingBean = Common.getSetting(context);
				new MaintainTaskDao(context).initCount(settingBean, handler);
				break;
			}
			case 7://完成维修信息分页下载，开始下载维修材料
			{	
				SettingBean settingBean = Common.getSetting(context);
				new MaintainMaterialDao(context).init(settingBean, handler);
				break;
			}
			case 8://完成维修信息分页下载，开始下载维修材料
			{	
				SettingBean settingBean = Common.getSetting(context);
				new BridgeProjectDao(context).init(settingBean, handler);
				break;
			}
			
			
			/**结束*/	
			case 9://完成获取涵洞经常性检查页数，分页下载涵洞经常性检查
			{
				new DataUploadIdMapDao(context).delete();
				Toast.makeText(context, "数据同步完成。", Toast.LENGTH_LONG).show();
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

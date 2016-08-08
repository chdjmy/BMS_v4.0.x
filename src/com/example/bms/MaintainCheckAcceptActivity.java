package com.example.bms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.example.bean.BridgeBean;
import com.example.bean.MaintainMaterialBean;
import com.example.bean.MaintainTaskBean;
import com.example.bean.MaterialBean;
import com.example.dao.BridgeDao;
import com.example.dao.MaintainMaterialDao;
import com.example.dao.MaintainTaskDao;
import com.example.dao.MaterialDao;
import com.example.photo.activity.AlbumActivity;
import com.example.photo.activity.GalleryActivity;
import com.example.photo.util.Bimp;
import com.example.photo.util.FileUtils;
import com.example.photo.util.ImageItem;
import com.example.photo.util.PublicWay;
import com.example.photo.util.Res;
import com.mining.app.zxing.view.MipcaActivityCapture;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MaintainCheckAcceptActivity extends BaseActivity implements
		OnItemClickListener, View.OnTouchListener {

	// 条码扫描
	private final static int SCANNIN_GREQUEST_CODE = 10;
	// 材料管理
	private final static int MATERIAL_MANAGER_CODE = 2;
	//拍照返回码
	private static final int TAKE_PICTURE = 0x000001;

	// 表单控件
	private AutoCompleteTextView autoBridgeName;
	private List<BridgeBean> mList;// 桥梁搜索列表
	private EditText autoBridgeCode;
	private Button buttonScan;

//	private EditText taskType;
	private EditText stake;
	private EditText diseaseName;
	private EditText diseaseLocation;
	private EditText diseaseQuantity;
	private EditText diseaseUnit;
	private EditText maintainPlan;
	private EditText planner;
	private EditText submitDate;
	private EditText bak;
	//维修审核
	private EditText isverify;
	private EditText verifier;
	private EditText verifytime;
	private EditText verifySuggestion;
	private EditText maintainDate;
	private EditText isold;
	private EditText departmentName;
	private EditText checkTime;
	private EditText workProjectQuality;
	private EditText workAreaLayout;
	private EditText headman;
	private EditText member;
	private RadioGroup isgrade;
	private RadioButton grade_yes;
	private RadioButton grade_no;
	private EditText checkPic;
	
	
	

	// 材料相关控件
	private TableLayout tableLayoutPlanAdd;
	private List<MaterialBean> listMaterial;
	private Button buttonMaterialManager;

	// 提交后保存的信息
	private String bridgeId;
	private EditText[] etMaterialQuantity;
	private MaintainTaskBean bean;

	// 跳转参数
	private String interType;
	private String maintainId;
	
	// 拍照功能-
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap;
	// -拍照功能
	
	//下拉框
	private Spinner taskTypeSpinner;
	private int taskTypeSelected;
	private Spinner submitSpinner;
	private int submitSelected;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintain_check_accept);
		// 初始化控件
		autoBridgeName = (AutoCompleteTextView) findViewById(R.id.autoBridgeName);
		autoBridgeCode = (EditText) findViewById(R.id.autoBridgeCode);
//		taskType = (EditText) findViewById(R.id.taskType);
		stake = (EditText) findViewById(R.id.stake);
		diseaseName = (EditText) findViewById(R.id.diseaseName);
		diseaseLocation = (EditText) findViewById(R.id.diseaseLocation);
		diseaseQuantity = (EditText) findViewById(R.id.diseaseQuantity);
		diseaseUnit = (EditText) findViewById(R.id.diseaseUnit);
		maintainPlan = (EditText) findViewById(R.id.maintainPlan);
		planner = (EditText) findViewById(R.id.planner);
		submitDate = (EditText) findViewById(R.id.submitDate);
		bak = (EditText) findViewById(R.id.bak);
		tableLayoutPlanAdd = (TableLayout) findViewById(R.id.tableLayoutPlanAdd);
		buttonMaterialManager = (Button) findViewById(R.id.buttonMaterialManager);
		buttonScan = (Button) findViewById(R.id.buttonScan);
		
		isverify = (EditText) findViewById(R.id.isverify);
		verifier = (EditText) findViewById(R.id.verifier);
		verifytime = (EditText) findViewById(R.id.verifytime);
		verifySuggestion = (EditText) findViewById(R.id.verifySuggestion);
		maintainDate = (EditText) findViewById(R.id.maintainDate);
		isold = (EditText) findViewById(R.id.isold);
		departmentName = (EditText) findViewById(R.id.departmentName);
		checkTime = (EditText) findViewById(R.id.checkTime);
		workProjectQuality = (EditText) findViewById(R.id.workProjectQuality);
		workAreaLayout = (EditText) findViewById(R.id.workAreaLayout);
		headman = (EditText) findViewById(R.id.headman);
		member = (EditText) findViewById(R.id.member);
		checkPic = (EditText) findViewById(R.id.checkPic);
		
		grade_yes = (RadioButton)findViewById(R.id.grade_yes);
		grade_no = (RadioButton)findViewById(R.id.grade_no);
		isgrade = (RadioGroup)findViewById(R.id.isgrade);
	
		isgrade.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//获取变更后的选中项的ID
				int radioButtonId = group.getCheckedRadioButtonId();
				//根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton)MaintainCheckAcceptActivity.this.findViewById(radioButtonId);
				if(rb.getText().toString().equals("合格")){
					bean.setIsgrade("1");
				}else{
					bean.setIsgrade("0");
				}
				
			}
			
		});
		taskTypeSpinner = (Spinner) findViewById(R.id.taskTypeSpinner);
		submitSpinner = (Spinner) findViewById(R.id.submitSpinner);
		// 数据
		final List<String> data_list = new ArrayList<String>();
		data_list.add("日维修");
		data_list.add("周维修");
		data_list.add("月维修");
		data_list.add("季度维修");
		data_list.add("半年维修");
		data_list.add("年维修");
		data_list.add("专项检查");
		taskTypeSelected = 0;
		// 适配器
		ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, data_list);
		// 设置样式
		arr_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 加载适配器
		taskTypeSpinner.setAdapter(arr_adapter);
		taskTypeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//String cardNumber = data_list.get(arg2);
				taskTypeSelected = arg2;
				// 设置显示当前选择的项
				arg0.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
		
		// 数据
		final List<String> submit_list = new ArrayList<String>();
		submit_list.add("提交上一级审核");
		submit_list.add("本单位审核");
		submitSelected = 0;
		// 适配器
		ArrayAdapter<String> submit_adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, submit_list);
		// 设置样式
		submit_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 加载适配器
		submitSpinner.setAdapter(submit_adapter);
		submitSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//String cardNumber = submit_list.get(arg2);
				submitSelected = arg2;
				// 设置显示当前选择的项
				arg0.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});

			
		interType = getIntent().getStringExtra("interType");
		
		maintainId = getIntent().getStringExtra("maintainId");
		MaintainTaskDao maintainPlanDao = new MaintainTaskDao(this);
		bean = maintainPlanDao.get(maintainId);
		
		if(interType.equals("accept")&&bean.getIschecked()!=null&&bean.getIschecked().equals("1")){
			interType = "query";
		}
		if(interType.equals("accept")&&bean.getIschecked()!=null&&bean.getIschecked().equals("2")){
			interType = "change";
		}
		if (interType.equals("accept")) {
			int bridgeId = bean.getBridgeId();
			BridgeDao bridgeDao = new BridgeDao(this);
			BridgeBean bridgeBean = bridgeDao.get(bridgeId);
			autoBridgeName.setText(bridgeBean.getBridgeName() + " ");
			autoBridgeCode.setText(bridgeBean.getBridgeCode());
	//		taskType.setText(bean.getTaskType());
			if(StringUtils.isNotEmpty(bean.getTaskType())){
				taskTypeSelected = Integer.parseInt(bean.getTaskType());
			}else{
				taskTypeSelected = 0;
			}
			taskTypeSpinner.setSelection(taskTypeSelected);
			if(StringUtils.isNotEmpty(bean.getSubmitForm())){
				submitSelected = Integer.parseInt(bean.getSubmitForm());
			}else{
				submitSelected = 0;
			}
			submitSpinner.setSelection(submitSelected);
			stake.setText(bean.getStake());
			diseaseName.setText(bean.getDiseaseName());
			diseaseLocation.setText(bean.getDiseaseLocation());
			diseaseQuantity.setText(bean.getDiseaseQuantity().toString());
			diseaseUnit.setText(bean.getDiseaseUnit());
			maintainPlan.setText(bean.getMaintainPlan());
			planner.setText(bean.getPlanner());
			submitDate.setText(bean.getSubmitDate());
			bak.setText(bean.getBak());
			//维修任务
	//		isverify.setText(bean.getIsverify());
			if(StringUtils.isNotEmpty(bean.getIsverify())){
				int verify = Integer.parseInt(bean.getIsverify());
				if(verify==2){
					isverify.setText("审核已通过");
				}else if(verify==1){
					isverify.setText("审核未通过");
				}else{
					isverify.setText("未审核");
				}
				
			}
			verifier.setText(bean.getVerifier());
			verifytime.setText(bean.getVerifytime());
			verifySuggestion.setText(bean.getVerifySuggestion());
//			maintainDate.setText(bean.getMaintainDate());
			String old = bean.getIsold();
			if(old!=null){
				isold.setText(old.equals("0")?"过期":"正常");
			}else{
				isold.setText("正常");
			}
			//验收时间
			Date today = new Date();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			checkTime.setText(format.format(today));
			checkTime.setOnTouchListener(this);
			//维修日期
			maintainDate.setText(format.format(today));
			maintainDate.setOnTouchListener(this);
			
			displayMaterialList();
			buttonMaterialManager.setVisibility(View.INVISIBLE);
			buttonScan.setVisibility(View.INVISIBLE);
			
			autoBridgeName.setEnabled(false);
			autoBridgeCode.setEnabled(false);
			taskTypeSpinner.setEnabled(false);
			submitSpinner.setEnabled(false);
			stake.setEnabled(false);
			diseaseName.setEnabled(false);
			diseaseLocation.setEnabled(false);
			diseaseQuantity.setEnabled(false);
			diseaseUnit.setEnabled(false);
			maintainPlan.setEnabled(false);
			planner.setEnabled(false);
			submitDate.setEnabled(false);
			bak.setEnabled(false);
			isverify.setEnabled(false);
			verifier.setEnabled(false);
			verifytime.setEnabled(false);
			verifySuggestion.setEnabled(false);
//			maintainDate.setEnabled(false);
			isold.setEnabled(false);
			bean.setIsgrade("1");
			
		} else if(interType.equals("query")){
			int bridgeId = bean.getBridgeId();
			BridgeDao bridgeDao = new BridgeDao(this);
			BridgeBean bridgeBean = bridgeDao.get(bridgeId);
			autoBridgeName.setText(bridgeBean.getBridgeName() + " ");
			autoBridgeCode.setText(bridgeBean.getBridgeCode());
	//		taskType.setText(bean.getTaskType());
			if(StringUtils.isNotEmpty(bean.getTaskType())){
				taskTypeSelected = Integer.parseInt(bean.getTaskType());
			}else{
				taskTypeSelected = 0;
			}
			taskTypeSpinner.setSelection(taskTypeSelected);
			if(StringUtils.isNotEmpty(bean.getSubmitForm())){
				submitSelected = Integer.parseInt(bean.getSubmitForm());
			}else{
				submitSelected = 0;
			}
			submitSpinner.setSelection(submitSelected);
			stake.setText(bean.getStake());
			diseaseName.setText(bean.getDiseaseName());
			diseaseLocation.setText(bean.getDiseaseLocation());
			diseaseQuantity.setText(bean.getDiseaseQuantity().toString());
			diseaseUnit.setText(bean.getDiseaseUnit());
			maintainPlan.setText(bean.getMaintainPlan());
			planner.setText(bean.getPlanner());
			submitDate.setText(bean.getSubmitDate());
			bak.setText(bean.getBak());
			//维修任务
		//	isverify.setText(bean.getIsverify());
			if(StringUtils.isNotEmpty(bean.getIsverify())){
				int verify = Integer.parseInt(bean.getIsverify());
				if(verify==2){
					isverify.setText("审核已通过");
				}else if(verify==1){
					isverify.setText("审核未通过");
				}else{
					isverify.setText("未审核");
				}
				
			}
			verifier.setText(bean.getVerifier());
			verifytime.setText(bean.getVerifytime());
			verifySuggestion.setText(bean.getVerifySuggestion());
			maintainDate.setText(bean.getMaintainDate());
			String old = bean.getIsold();
			if(old!=null){
				isold.setText(old.equals("0")?"过期":"正常");
			}else{
				isold.setText("正常");
			}
			//维修验收
			departmentName.setText(bean.getDepartmentName());
			//验收时间
			checkTime.setText(bean.getCheckTime());
			workProjectQuality.setText(bean.getWorkProjectQuality());
			workAreaLayout.setText(bean.getWorkAreaLayout());
			headman.setText(bean.getHeadman());
			member.setText(bean.getMember());
			if(bean.getIsgrade()==null||bean.getIsgrade().equals("1")){
				grade_yes.setChecked(true);
			}else{
				grade_no.setChecked(true);
			}
			checkPic.setText(bean.getCheckPic());
			
			//显示图片
			String picAddr = bean.getPhotoAddr();
			Bimp.displayPhoto(picAddr);
			
			displayMaterialList();
			buttonMaterialManager.setVisibility(View.INVISIBLE);
			buttonScan.setVisibility(View.INVISIBLE);
			
		}else if(interType.equals("change")){//change
			
			int bridgeId = bean.getBridgeId();
			BridgeDao bridgeDao = new BridgeDao(this);
			BridgeBean bridgeBean = bridgeDao.get(bridgeId);
			autoBridgeName.setText(bridgeBean.getBridgeName() + " ");
			autoBridgeCode.setText(bridgeBean.getBridgeCode());
//			taskType.setText(bean.getTaskType());
			if(StringUtils.isNotEmpty(bean.getTaskType())){
				taskTypeSelected = Integer.parseInt(bean.getTaskType());
			}else{
				taskTypeSelected = 0;
			}
			taskTypeSpinner.setSelection(taskTypeSelected);
			if(StringUtils.isNotEmpty(bean.getSubmitForm())){
				submitSelected = Integer.parseInt(bean.getSubmitForm());
			}else{
				submitSelected = 0;
			}
			submitSpinner.setSelection(submitSelected);
			stake.setText(bean.getStake());
			diseaseName.setText(bean.getDiseaseName());
			diseaseLocation.setText(bean.getDiseaseLocation());
			diseaseQuantity.setText(bean.getDiseaseQuantity().toString());
			diseaseUnit.setText(bean.getDiseaseUnit());
			maintainPlan.setText(bean.getMaintainPlan());
			planner.setText(bean.getPlanner());
			submitDate.setText(bean.getSubmitDate());
			bak.setText(bean.getBak());
			//维修任务
	//		isverify.setText(bean.getIsverify());
			if(StringUtils.isNotEmpty(bean.getIsverify())){
				int verify = Integer.parseInt(bean.getIsverify());
				if(verify==2){
					isverify.setText("审核已通过");
				}else if(verify==1){
					isverify.setText("审核未通过");
				}else{
					isverify.setText("未审核");
				}
				
			}
			verifier.setText(bean.getVerifier());
			verifytime.setText(bean.getVerifytime());
			verifySuggestion.setText(bean.getVerifySuggestion());
			maintainDate.setText(bean.getMaintainDate());
			String old = bean.getIsold();
			if(old!=null){
				isold.setText(old.equals("0")?"过期":"正常");
			}else{
				isold.setText("正常");
			}
			//维修验收
			departmentName.setText(bean.getDepartmentName());
			//验收时间
			checkTime.setText(bean.getCheckTime());
			workProjectQuality.setText(bean.getWorkProjectQuality());
			workAreaLayout.setText(bean.getWorkAreaLayout());
			headman.setText(bean.getHeadman());
			member.setText(bean.getMember());
			if(bean.getIsgrade()==null||bean.getIsgrade().equals("1")){
				grade_yes.setChecked(true);
			}else{
				grade_no.setChecked(true);
			}
			
			checkPic.setText(bean.getCheckPic());
			
			//显示图片
			String picAddr = bean.getPhotoAddr();
			Bimp.displayPhoto(picAddr);
			
			displayMaterialList();
			buttonMaterialManager.setVisibility(View.INVISIBLE);
			buttonScan.setVisibility(View.INVISIBLE);
			
			autoBridgeName.setEnabled(false);
			autoBridgeCode.setEnabled(false);
//			taskType.setEnabled(false);
			taskTypeSpinner.setEnabled(false);
			submitSpinner.setEnabled(false);
			stake.setEnabled(false);
			diseaseName.setEnabled(false);
			diseaseLocation.setEnabled(false);
			diseaseQuantity.setEnabled(false);
			diseaseUnit.setEnabled(false);
			maintainPlan.setEnabled(false);
			planner.setEnabled(false);
			submitDate.setEnabled(false);
			bak.setEnabled(false);
			isverify.setEnabled(false);
			verifier.setEnabled(false);
			verifytime.setEnabled(false);
			verifySuggestion.setEnabled(false);
//			maintainDate.setEnabled(false);
			isold.setEnabled(false);
			
			// 材料管理列表
//			buttonMaterialManager.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(MaintainCheckAcceptActivity.this,
//							MaterialManagerActivity.class);
//					startActivityForResult(intent, MATERIAL_MANAGER_CODE);
//				}
//			});
//
//			// 初始化维修数量的材料列表
//			refreshMaterialList();
		}
		
		// photo-
		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(
				R.layout.activity_usual_exam_add, null);
		Init();
		// -photo

	}

	// 刷新材料列表
	public void refreshMaterialList() {

		MaterialDao materialDao = new MaterialDao(this);
		listMaterial = materialDao.find();
		etMaterialQuantity = new EditText[listMaterial.size()];

		tableLayoutPlanAdd.removeAllViews();
		for (int i = 0; i < listMaterial.size(); i++) {
			MaterialBean material = listMaterial.get(i);
			
			TableRow r = new TableRow(this);
			//材料名称
			TextView t = new TextView(this);
			t.setWidth(400);
			t.setText(material.getMaterialName());
			t.setGravity(Gravity.RIGHT);
			//计划用量
			EditText plan = new EditText(this);
			plan.setWidth(300);
			plan.setGravity(Gravity.CENTER);
			plan.setText(material.getMaterialPlannedQuantity()+ "");
			plan.setEnabled(false);
			//实际用量
			etMaterialQuantity[i] = new EditText(this);
			etMaterialQuantity[i].setWidth(300);
			etMaterialQuantity[i].setGravity(Gravity.CENTER);
			//单位
			TextView u = new TextView(this);
			u.setText(material.getMaterialUnit());
			
			r.addView(t);
			r.addView(plan);
			r.addView(etMaterialQuantity[i]);
			r.addView(u);
			tableLayoutPlanAdd.addView(r);

		}
	}

	// 显示维修材料
	public void displayMaterialList() {
		MaintainMaterialDao maintainMaterialDao = new MaintainMaterialDao(this);
		List<MaterialBean> list = maintainMaterialDao
				.queryMaterialByMaintainId(maintainId);
		etMaterialQuantity = new EditText[list.size()];
		EditText[] plan = new EditText[list.size()];
		for (int i = 0; i < list.size(); i++) {
			MaterialBean material = list.get(i);
			TableRow r = new TableRow(this);
			//材料名称
			TextView t = new TextView(this);
			t.setWidth(400);
			t.setText(material.getMaterialName());
			t.setGravity(Gravity.RIGHT);
			//计划用量
			plan[i] = new EditText(this);
			plan[i].setWidth(300);
			plan[i].setGravity(Gravity.CENTER);
			plan[i].setText(material.getMaterialPlannedQuantity()+ "");
			plan[i].setEnabled(false);
			//实际用量
			etMaterialQuantity[i] = new EditText(this);
			etMaterialQuantity[i].setWidth(300);
			etMaterialQuantity[i].setGravity(Gravity.CENTER);
			if(!interType.equals("accept")&&material.getMaterialFinishQuantity()!=null){
				etMaterialQuantity[i].setText(material.getMaterialFinishQuantity()+"");
			}
			//单位
			TextView u = new TextView(this);
			u.setText(material.getMaterialUnit());
			
			r.addView(t);
			r.addView(plan[i]);
			r.addView(etMaterialQuantity[i]);
			r.addView(u);
			tableLayoutPlanAdd.addView(r);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		BridgeBean bg = mList.get(position);
		autoBridgeName.setText(bg.getBridgeName());
		autoBridgeCode.setText(bg.getBridgeCode());
		bridgeId = bg.getBridgeId();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// 显示扫描到的内容
				String barCode = bundle.getString("result");
				BridgeDao bridgeDao = new BridgeDao(
						MaintainCheckAcceptActivity.this);
				List<BridgeBean> list = bridgeDao.findByBar(barCode,getDepCode());
				if (list.size() > 0) {
					BridgeBean bg = list.get(0);
					autoBridgeName.setText(bg.getBridgeName() + " ");
					autoBridgeCode.setText(bg.getBridgeCode());
					bridgeId = bg.getBridgeId();
				}
			}
			break;
		case MATERIAL_MANAGER_CODE:
			// 刷新维修数量的材料列表
			refreshMaterialList();
			break;
		
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
	
				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				String filePath = FileUtils.saveBitmap(bm, fileName);
				System.out.println(filePath);
				mediaScan(filePath);
	
				ImageItem takePhoto = new ImageItem();
				// takePhoto.setBitmap(bm);
				takePhoto.setImagePath(filePath);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}

	// 提交菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(interType.equals("accept")){
			getMenuInflater().inflate(R.menu.check, menu);// 验收
		}else if(interType.equals("change")){
			getMenuInflater().inflate(R.menu.usual_change, menu);
		}
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (interType.equals("accept")) {
			
			bean.setDepartmentName(departmentName.getText().toString());
			bean.setCheckTime(checkTime.getText().toString());
			bean.setWorkProjectQuality(workProjectQuality.getText().toString());
			bean.setWorkAreaLayout(workAreaLayout.getText().toString());
			bean.setHeadman(headman.getText().toString());
			bean.setMember(member.getText().toString());
			bean.setCheckPic(checkPic.getText().toString());
			bean.setIschecked("2");
			bean.setPhotoAddr(Bimp.savePhotoAddr());//保存照片
			
			MaintainTaskDao maintainTaskDao = new MaintainTaskDao(
					MaintainCheckAcceptActivity.this);
			maintainTaskDao.update(bean);

			MaintainMaterialDao maintainMaterialDao = new MaintainMaterialDao(MaintainCheckAcceptActivity.this);
			List<MaintainMaterialBean> list = maintainMaterialDao.queryMaintainMaterialByTaskId(maintainId);
			
			for (int i = 0; i < list.size(); i++) {
				if (StringUtils.isNotEmpty(etMaterialQuantity[i].getText().toString())) {
					MaintainMaterialBean m = list.get(i);
					m.setMaterialFinishQuantity(Double
							.parseDouble(etMaterialQuantity[i].getText()
									.toString()));
					maintainMaterialDao.update(m);
				}
			}
			displayToast("验收成功，已提交至上传列表");

		}else{
			bean.setDepartmentName(departmentName.getText().toString());
			bean.setCheckTime(checkTime.getText().toString());
			bean.setWorkProjectQuality(workProjectQuality.getText().toString());
			bean.setWorkAreaLayout(workAreaLayout.getText().toString());
			bean.setHeadman(headman.getText().toString());
			bean.setMember(member.getText().toString());
			bean.setCheckPic(checkPic.getText().toString());
			bean.setIschecked("2");
			bean.setPhotoAddr(Bimp.savePhotoAddr());//保存照片
			
			MaintainTaskDao maintainTaskDao = new MaintainTaskDao(
					MaintainCheckAcceptActivity.this);
			maintainTaskDao.update(bean);

			MaintainMaterialDao maintainMaterialDao = new MaintainMaterialDao(MaintainCheckAcceptActivity.this);
			List<MaintainMaterialBean> list = maintainMaterialDao.queryMaintainMaterialByTaskId(maintainId);
			
			for (int i = 0; i < list.size(); i++) {
				if (!etMaterialQuantity[i].getText().toString().equals("")) {
					MaintainMaterialBean m = list.get(i);
					m.setMaterialFinishQuantity(Double
							.parseDouble(etMaterialQuantity[i].getText()
									.toString()));
					maintainMaterialDao.update(m);
				}
			}
		}
		Bimp.clearPhoto();
		this.finish();
		return super.onOptionsItemSelected(item);
	}

	/**
	 * autoCompleteEditText的自定义适配器
	 */
	class MyAdapter extends BaseAdapter implements Filterable {
		private ArrayFilter mFilter;
		// private List<BridgeBean> mList;
		private Context context;
		private ArrayList<BridgeBean> mUnfilteredData;

		public MyAdapter(List<BridgeBean> mList, Context context) {
			// this.mList = mList;
			this.context = context;
		}

		@Override
		public int getCount() {

			return mList == null ? 0 : mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			if (convertView == null) {
				view = View.inflate(context, R.layout.listitem_auto_text, null);

				holder = new ViewHolder();
				holder.tv_name = (TextView) view
						.findViewById(R.id.bridgeNameHint);

				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}

			BridgeBean bg = mList.get(position);
			holder.tv_name.setText(bg.getBridgeName());

			return view;
		}

		class ViewHolder {
			public TextView tv_name;

		}

		@Override
		public Filter getFilter() {
			if (mFilter == null) {
				mFilter = new ArrayFilter();
			}
			return mFilter;
		}

		private class ArrayFilter extends Filter {

			@Override
			protected FilterResults performFiltering(CharSequence prefix) {
				FilterResults results = new FilterResults();

				if (mUnfilteredData == null) {
					mUnfilteredData = new ArrayList<BridgeBean>(mList);
				}

				if (prefix == null || prefix.length() == 0) {
					ArrayList<BridgeBean> list = mUnfilteredData;
					results.values = list;
					results.count = list.size();
				} else {
					String prefixString = prefix.toString().toLowerCase();

					ArrayList<BridgeBean> unfilteredValues = mUnfilteredData;

					BridgeDao bridgeDao = new BridgeDao(context);
					List<BridgeBean> newValues = bridgeDao
							.findByName(prefixString,getDepCode());

					results.values = newValues;
					results.count = newValues.size();
				}

				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				// noinspection unchecked
				mList = (List<BridgeBean>) results.values;
				if (results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}

		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			View view = View.inflate(this, R.layout.date_time_dialog, null);
			final DatePicker datePicker = (DatePicker) view
					.findViewById(R.id.date_picker);
			builder.setView(view);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH), null);

			if (v.getId() == R.id.checkTime) {
				final int inType = checkTime.getInputType();
				checkTime.setInputType(InputType.TYPE_NULL);
				checkTime.onTouchEvent(event);
				checkTime.setInputType(inType);
				checkTime.setSelection(checkTime.getText().length());

				builder.setTitle("请选择验收日期");
				builder.setPositiveButton("确  定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								StringBuffer sb = new StringBuffer();
								sb.append(String.format("%d-%02d-%02d",
										datePicker.getYear(),
										datePicker.getMonth() + 1,
										datePicker.getDayOfMonth()));
								checkTime.setText(sb);
								dialog.cancel();
							}
						});
			}else if(v.getId() == R.id.maintainDate){
				final int inType = maintainDate.getInputType();
				maintainDate.setInputType(InputType.TYPE_NULL);
				maintainDate.onTouchEvent(event);
				maintainDate.setInputType(inType);
				maintainDate.setSelection(maintainDate.getText().length());

				builder.setTitle("请选择维修日期");
				builder.setPositiveButton("确  定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								StringBuffer sb = new StringBuffer();
								sb.append(String.format("%d-%02d-%02d",
										datePicker.getYear(),
										datePicker.getMonth() + 1,
										datePicker.getDayOfMonth()));
								maintainDate.setText(sb);
								dialog.cancel();
							}
						});
			}
			Dialog dialog = builder.create();
			dialog.show();
		}

		return true;
	}
	
	// photo-
	public void Init() {

		pop = new PopupWindow(MaintainCheckAcceptActivity.this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MaintainCheckAcceptActivity.this,
						AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {// 增加按钮
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							MaintainCheckAcceptActivity.this,
							R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {// 预览图片
					Intent intent = new Intent(MaintainCheckAcceptActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == 9) {
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9||interType.equals("query")) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position)
						.getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}


	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			for (int i = 0; i < PublicWay.activityList.size(); i++) {
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}
			Bimp.tempSelectBitmap.clear();
			Bimp.max = 0;
			finish();
		}

		return true;
	}

	private void mediaScan(final String filePath) {

		MediaScannerConnectionClient mediaScannerClient = new MediaScannerConnectionClient() {

			private MediaScannerConnection msc = null;
			{
				msc = new MediaScannerConnection(MaintainCheckAcceptActivity.this,
						this);
				msc.connect();
			}

			public void onMediaScannerConnected() {
				// Optionally specify a MIME Type, or
				// have the Media Scanner imply one based
				// on the filename.
				String mimeType = null;
				msc.scanFile(filePath, mimeType);
			}

			public void onScanCompleted(String path, Uri uri) {
				msc.disconnect();
				// Log.d(TAG, "File Added at: " + uri.toString());
			}

		};
	}
	// -photo
}

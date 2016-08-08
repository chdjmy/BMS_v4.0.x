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
import com.example.dao.UsersDao;
import com.example.util.ShareMap;
import com.mining.app.zxing.view.MipcaActivityCapture;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MaintainPlanAddActivity extends BaseActivity implements
		OnItemClickListener, View.OnTouchListener {

	// 条码扫描
	private final static int SCANNIN_GREQUEST_CODE = 1;
	// 材料管理
	private final static int MATERIAL_MANAGER_CODE = 2;

	// 表单控件
	private AutoCompleteTextView autoBridgeName;
	private List<BridgeBean> mList;// 桥梁搜索列表
	private EditText autoBridgeCode;
	private Button buttonScan;
//	private EditText taskType;	
	private Spinner taskTypeSpinner;
	private int taskTypeSelected;

	private EditText stake;
	private EditText diseaseName;
	private EditText diseaseLocation;
	private EditText diseaseQuantity;
	private EditText diseaseUnit;
	private EditText maintainPlan;
	private EditText planner;
	private EditText submitDate;
	//5-21
	private Spinner submitSpinner;
	private int submitSelected;
	private EditText bak;
	

	// 材料相关控件
	private TableLayout tableLayoutPlanAdd;
	private List<MaterialBean> listMaterial;
	private Button buttonMaterialManager;

	// 提交后保存的信息
	private String bridgeId;
	private EditText[] etMaterialQuantity;

	// 跳转参数
	private String interType;
	private String maintainId;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintain_plan_add);
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
		if (interType.equals("query")) {
			maintainId = getIntent().getStringExtra("maintainId");
			MaintainTaskDao maintainPlanDao = new MaintainTaskDao(this);
			MaintainTaskBean bean = maintainPlanDao.get(maintainId);
			int bridgeId = bean.getBridgeId();
			BridgeDao bridgeDao = new BridgeDao(this);
			BridgeBean bridgeBean = bridgeDao.get(bridgeId);
			autoBridgeName.setText(bridgeBean.getBridgeName() + " ");
			autoBridgeCode.setText(bridgeBean.getBridgeCode());
			
			if(StringUtils.isNotEmpty(bean.getTaskType())){
				taskTypeSelected = Integer.parseInt(bean.getTaskType());
			}else{
				taskTypeSelected = 0;
			}
			taskTypeSpinner.setSelection(taskTypeSelected);
			
			stake.setText(bean.getStake());
			diseaseName.setText(bean.getDiseaseName());
			diseaseLocation.setText(bean.getDiseaseLocation());
			diseaseQuantity.setText(bean.getDiseaseQuantity().toString());
			diseaseUnit.setText(bean.getDiseaseUnit());
			maintainPlan.setText(bean.getMaintainPlan());
			planner.setText(bean.getPlanner());
			submitDate.setText(bean.getSubmitDate());
			
			if(StringUtils.isNotEmpty(bean.getSubmitForm())){
				submitSelected = Integer.parseInt(bean.getSubmitForm());
			}else{
				submitSelected = 0;
			}
			submitSpinner.setSelection(submitSelected);
			
			bak.setText(bean.getBak());
			displayMaterialList();
			buttonMaterialManager.setVisibility(View.INVISIBLE);
			buttonScan.setVisibility(View.INVISIBLE);

		} else if (interType.equals("add")) {
			// 桥梁名称的下拉搜索
			mList = new ArrayList<BridgeBean>();
			MyAdapter mAdapter = new MyAdapter(mList, this);
			autoBridgeName.setAdapter(mAdapter);
			autoBridgeName.setThreshold(1); // 设置输入一个字符 提示，默认为2
			autoBridgeName.setOnItemClickListener(this);
			Date today = new Date();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			submitDate.setText(format.format(today));
			submitDate.setOnTouchListener(this);// 设置OnTouch监听

			// 扫码搜索功能
			buttonScan.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(MaintainPlanAddActivity.this,
							MipcaActivityCapture.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
				}
			});
			// 材料管理列表
			buttonMaterialManager.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (etMaterialQuantity != null) {
						for (int i = 0; i < etMaterialQuantity.length; i++) {
							ShareMap.getInstance().remove(
									listMaterial.get(i).getMaterialId());
							ShareMap.getInstance().put(
									listMaterial.get(i).getMaterialId(),
									Double.parseDouble(etMaterialQuantity[i]
											.getText().toString()));
						}
					}
					Intent intent = new Intent(MaintainPlanAddActivity.this,
							MaterialManagerActivity.class);
					startActivityForResult(intent, MATERIAL_MANAGER_CODE);
				}
			});

			// 初始化维修数量的材料列表
			refreshMaterialList();
		} else if (interType.equals("change")) {
			maintainId = getIntent().getStringExtra("maintainId");
			MaintainTaskDao maintainPlanDao = new MaintainTaskDao(this);
			MaintainTaskBean bean = maintainPlanDao.get(maintainId);
			int bridgeId = bean.getBridgeId();
			BridgeDao bridgeDao = new BridgeDao(this);
			BridgeBean bridgeBean = bridgeDao.get(bridgeId);
			autoBridgeName.setText(bridgeBean.getBridgeName() + " ");
			autoBridgeCode.setText(bridgeBean.getBridgeCode());
			taskTypeSelected = Integer.parseInt(bean.getTaskType());
			taskTypeSpinner.setSelection(taskTypeSelected);
			stake.setText(bean.getStake());
			diseaseName.setText(bean.getDiseaseName());
			diseaseLocation.setText(bean.getDiseaseLocation());
			diseaseQuantity.setText(bean.getDiseaseQuantity().toString());
			diseaseUnit.setText(bean.getDiseaseUnit());
			maintainPlan.setText(bean.getMaintainPlan());
			planner.setText(bean.getPlanner());
			submitDate.setText(bean.getSubmitDate());
			submitSelected = Integer.parseInt(bean.getSubmitForm());
			submitSpinner.setSelection(submitSelected);
			bak.setText(bean.getBak());
			displayMaterialList();

			mList = new ArrayList<BridgeBean>();
			MyAdapter mAdapter = new MyAdapter(mList, this);
			autoBridgeName.setAdapter(mAdapter);
			autoBridgeName.setThreshold(1); // 设置输入一个字符 提示，默认为2
			autoBridgeName.setOnItemClickListener(this);
			Date today = new Date();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			submitDate.setText(format.format(today));
			submitDate.setOnTouchListener(this);// 设置OnTouch监听

			// 扫码搜索功能
			buttonScan.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(MaintainPlanAddActivity.this,
							MipcaActivityCapture.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
				}
			});
			// 材料管理列表
			buttonMaterialManager.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MaintainPlanAddActivity.this,
							MaterialManagerActivity.class);
					startActivityForResult(intent, MATERIAL_MANAGER_CODE);
				}
			});

		}

	}

	// 刷新材料列表
	public void refreshMaterialList() {

		MaterialDao materialDao = new MaterialDao(this);
		listMaterial = materialDao.find();
		for (int i = 0; i < listMaterial.size();) {
			if (ShareMap.getInstance().get(listMaterial.get(i).getMaterialId()) != -1.0) {
				i++;
			} else {
				listMaterial.remove(i);
			}
		}

		etMaterialQuantity = new EditText[listMaterial.size()];
		tableLayoutPlanAdd.removeAllViews();
		for (int i = 0; i < listMaterial.size(); i++) {
			MaterialBean material = listMaterial.get(i);

			TableRow r = new TableRow(this);
			TextView t = new TextView(this);
			t.setText(material.getMaterialName());
			t.setGravity(Gravity.RIGHT);
			etMaterialQuantity[i] = new EditText(this);
			TextView u = new TextView(this);
			u.setText(material.getMaterialUnit());

			t.setWidth(400);
			etMaterialQuantity[i].setWidth(600);
			Double quantity = ShareMap.getInstance().get(
					listMaterial.get(i).getMaterialId());
			if (quantity != 0.0) {
				etMaterialQuantity[i].setText(quantity + "");
			}

			r.addView(t);
			r.addView(etMaterialQuantity[i]);
			r.addView(u);
			tableLayoutPlanAdd.addView(r);
		}
	}

	// 显示维修材料
	public void displayMaterialList() {
		MaintainMaterialDao maintainMaterialDao = new MaintainMaterialDao(this);
		if(listMaterial!=null){
			listMaterial.clear();
		}
		listMaterial = maintainMaterialDao
				.queryMaterialByMaintainId(maintainId);

		etMaterialQuantity = new EditText[listMaterial.size()];
		for (int i = 0; i < listMaterial.size(); i++) {
			MaterialBean material = listMaterial.get(i);
			TableRow r = new TableRow(this);
			TextView t = new TextView(this);
			t.setText(material.getMaterialName());
			t.setGravity(Gravity.RIGHT);
			etMaterialQuantity[i] = new EditText(this);
			TextView u = new TextView(this);
			u.setText(material.getMaterialUnit());

			t.setWidth(400);
			etMaterialQuantity[i].setWidth(600);
			etMaterialQuantity[i].setText(material.getMaterialPlannedQuantity()
					+ "");
			etMaterialQuantity[i].setGravity(Gravity.CENTER);
			r.addView(t);
			r.addView(etMaterialQuantity[i]);
			r.addView(u);
			tableLayoutPlanAdd.addView(r);
			if (interType.equals("change")) {
				ShareMap.getInstance().put(material.getMaterialId(),
						material.getMaterialPlannedQuantity());
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		BridgeBean bg = mList.get(position);
		autoBridgeName.setText(bg.getBridgeName());
		autoBridgeCode.setText(bg.getBridgeCode());
		stake.setText(String.valueOf(bg.getCenterStake()));
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
						MaintainPlanAddActivity.this);
				List<BridgeBean> list = bridgeDao.findByBar(barCode,
						getDepCode());
				if (list.size() > 0) {
					BridgeBean bg = list.get(0);
					autoBridgeName.setText(bg.getBridgeName() + " ");
					autoBridgeCode.setText(bg.getBridgeCode());
					stake.setText(String.valueOf(bg.getCenterStake()));
					bridgeId = bg.getBridgeId();
				}
			}
			break;
		case MATERIAL_MANAGER_CODE:
			// 刷新维修数量的材料列表
			refreshMaterialList();
			break;
		}

	}

	// 提交菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (interType.equals("add")) {
			getMenuInflater().inflate(R.menu.usual_add, menu);
		} else if (interType.equals("change")) {// 修改
			getMenuInflater().inflate(R.menu.usual_change, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (interType.equals("add")) {
			MaintainTaskBean bean = new MaintainTaskBean();
			bean.setBridgeId(Integer.parseInt(bridgeId));
			bean.setTaskType(String.valueOf(taskTypeSelected));
			bean.setStake(stake.getText().toString());
			bean.setDiseaseName(diseaseName.getText().toString());
			bean.setDiseaseLocation(diseaseLocation.getText().toString());
			bean.setDiseaseQuantity(Double.parseDouble(diseaseQuantity
					.getText().toString()));
			bean.setDiseaseUnit(diseaseUnit.getText().toString());
			bean.setMaintainPlan(maintainPlan.getText().toString());
			bean.setPlanner(planner.getText().toString());
			bean.setSubmitDate(submitDate.getText().toString());
			bean.setBak(bak.getText().toString());

			DateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
			bean.setMaintainCode("WXJH" + ft.format(new Date()));
			
			
			if(submitSelected==0){//提交上一级审核
				bean.setSubmitForm("0");
				bean.setIsupload("0");
				bean.setIsverify("0");
				bean.setIschecked("0");
			}else{//本单位审核
				bean.setSubmitForm("1");
				bean.setIsupload("1");
				bean.setIsverify("2");
				bean.setIschecked("0");
				String name = new UsersDao(this).getCurrentUser().getName();
				bean.setVerifier(name);
				DateFormat ftVerify = new SimpleDateFormat("yyyy-MM-dd");
				bean.setVerifytime(ftVerify.format(new Date()));
				bean.setVerifySuggestion("通过");
			}

			MaintainTaskDao maintainTaskDao = new MaintainTaskDao(
					MaintainPlanAddActivity.this);
			bean.setMaintainTaskId(maintainTaskDao.getMaxId() + 1);
			maintainTaskDao.insert(bean);

			int maintainTaskid = maintainTaskDao.getMaxId();
			MaintainMaterialDao maintainMaterialDao = new MaintainMaterialDao(
					MaintainPlanAddActivity.this);
			for (int i = 0; i < etMaterialQuantity.length; i++) {
				if (!etMaterialQuantity[i].getText().toString().equals("")) {
					MaintainMaterialBean m = new MaintainMaterialBean();
					m.setMaintainTaskId(maintainTaskid);
					m.setMaterialId(Integer.parseInt(listMaterial.get(i)
							.getMaterialId()));
					m.setMaterialPlannedQuantity(Double
							.parseDouble(etMaterialQuantity[i].getText()
									.toString()));
					m.setMaintainMaterialId(maintainMaterialDao.getMaxId() + 1);
					maintainMaterialDao.insert(m);
				}
			}
			ShareMap.getInstance().clear();
			if(submitSelected==0){
				displayToast("上一级审核，已提交至上传列表。");
			}else{
				displayToast("本单位审核，已提交至维修任务。");
			}
			

			// Intent intent = new
			// Intent(MaintainPlanAddActivity.this,MaintainPlanActivity.class);
			// startActivity(intent);
			this.finish();

		} else if (interType.equals("change")) {
			MaintainTaskDao maintainPlanDao = new MaintainTaskDao(this);
			MaintainTaskBean bean = maintainPlanDao.get(maintainId);
			bean.setTaskType(String.valueOf(taskTypeSelected));
			bean.setStake(stake.getText().toString());
			bean.setDiseaseName(diseaseName.getText().toString());
			bean.setDiseaseLocation(diseaseLocation.getText().toString());
			bean.setDiseaseQuantity(Double.parseDouble(diseaseQuantity
					.getText().toString()));
			bean.setDiseaseUnit(diseaseUnit.getText().toString());
			bean.setMaintainPlan(maintainPlan.getText().toString());
			bean.setPlanner(planner.getText().toString());
			bean.setSubmitDate(submitDate.getText().toString());
			bean.setBak(bak.getText().toString());
			
			if(submitSelected==0){//提交上一级审核
				bean.setSubmitForm("0");
				bean.setIsupload("0");
				bean.setIsverify("0");
				bean.setIschecked("0");
			}else{//本单位审核
				bean.setSubmitForm("1");
				bean.setIsupload("1");
				bean.setIsverify("2");
				bean.setIschecked("0");
			}

			maintainPlanDao.update(bean);

			// 更新材料
			MaintainMaterialDao maintainMaterialDao = new MaintainMaterialDao(
					MaintainPlanAddActivity.this);
			maintainMaterialDao.deleteMaterialByMaintainId(maintainId);
			for (int i = 0; i < etMaterialQuantity.length; i++) {
				if (!etMaterialQuantity[i].getText().toString().equals("")) {
					MaintainMaterialBean m = new MaintainMaterialBean();
					m.setMaintainTaskId(Integer.parseInt(maintainId));
					m.setMaterialId(Integer.parseInt(listMaterial.get(i)
							.getMaterialId()));
					m.setMaterialPlannedQuantity(Double
							.parseDouble(etMaterialQuantity[i].getText()
									.toString()));

					m.setMaintainMaterialId(maintainMaterialDao.getMaxId() + 1);
					maintainMaterialDao.insert(m);
				}
			}
			ShareMap.getInstance().clear();
			displayToast("修改成功。");
			this.finish();

		}
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
			holder.tv_name.setText(bg.getBridgeName()+" "+bg.getBridgeCode());

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
					List<BridgeBean> newValues = bridgeDao.findByName(
							prefixString, getDepCode());

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

			if (v.getId() == R.id.submitDate) {
				final int inType = submitDate.getInputType();
				submitDate.setInputType(InputType.TYPE_NULL);
				submitDate.onTouchEvent(event);
				submitDate.setInputType(inType);
				submitDate.setSelection(submitDate.getText().length());

				builder.setTitle("请选择检查日期");
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
								submitDate.setText(sb);
								dialog.cancel();
							}
						});
			}
			Dialog dialog = builder.create();
			dialog.show();
		}

		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ShareMap.getInstance().clear();
			finish();
		}

		return true;
	}
}

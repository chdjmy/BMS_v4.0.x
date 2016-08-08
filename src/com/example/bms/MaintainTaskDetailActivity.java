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
import com.example.bms.MaintainPlanAddActivity.MyAdapter;
import com.example.bms.MaintainPlanAddActivity.MyAdapter.ViewHolder;
import com.example.dao.BridgeDao;
import com.example.dao.MaintainMaterialDao;
import com.example.dao.MaintainTaskDao;
import com.example.dao.MaterialDao;
import com.mining.app.zxing.view.MipcaActivityCapture;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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
import android.widget.AdapterView.OnItemClickListener;

public class MaintainTaskDetailActivity extends BaseActivity implements
		OnItemClickListener, View.OnTouchListener {

	// ����ɨ��
	private final static int SCANNIN_GREQUEST_CODE = 1;
	// ���Ϲ���
	private final static int MATERIAL_MANAGER_CODE = 2;

	// ���ؼ�
	private AutoCompleteTextView autoBridgeName;
	private List<BridgeBean> mList;// ���������б�
	private EditText autoBridgeCode;
	private Button buttonScan;

	//private EditText taskType;
	private EditText stake;
	private EditText diseaseName;
	private EditText diseaseLocation;
	private EditText diseaseQuantity;
	private EditText diseaseUnit;
	private EditText maintainPlan;
	private EditText planner;
	private EditText submitDate;
	private EditText bak;
	//ά�����
	private EditText isverify;
	private EditText verifier;
	private EditText verifytime;
	private EditText verifySuggestion;
	private EditText maintainDate;
	private EditText isold;
	
	//������
	private Spinner taskTypeSpinner;
	private int taskTypeSelected;
	private Spinner submitSpinner;
	private int submitSelected;
	
	
	

	// ������ؿؼ�
	private TableLayout tableLayoutPlanAdd;
	private List<MaterialBean> listMaterial;
	private Button buttonMaterialManager;

	// �ύ�󱣴����Ϣ
	private String bridgeId;
	private EditText[] etMaterialQuantity;

	// ��ת����
	private String interType;
	private String maintainId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintain_task_detail);
		this.setTitle("ά�޼ƻ�����");
		// ��ʼ���ؼ�
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
		
		
		taskTypeSpinner = (Spinner) findViewById(R.id.taskTypeSpinner);
		submitSpinner = (Spinner) findViewById(R.id.submitSpinner);
		// ����
		final List<String> data_list = new ArrayList<String>();
		data_list.add("��ά��");
		data_list.add("��ά��");
		data_list.add("��ά��");
		data_list.add("����ά��");
		data_list.add("����ά��");
		data_list.add("��ά��");
		data_list.add("ר����");
		taskTypeSelected = 0;
		// ������
		ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, data_list);
		// ������ʽ
		arr_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ����������
		taskTypeSpinner.setAdapter(arr_adapter);
		taskTypeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//String cardNumber = data_list.get(arg2);
				taskTypeSelected = arg2;
				// ������ʾ��ǰѡ�����
				arg0.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
		
		// ����
		final List<String> submit_list = new ArrayList<String>();
		submit_list.add("�ύ��һ�����");
		submit_list.add("����λ���");
		submitSelected = 0;
		// ������
		ArrayAdapter<String> submit_adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, submit_list);
		// ������ʽ
		submit_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ����������
		submitSpinner.setAdapter(submit_adapter);
		submitSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//String cardNumber = submit_list.get(arg2);
				submitSelected = arg2;
				// ������ʾ��ǰѡ�����
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
		//	taskType.setText(bean.getTaskType());
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
			//ά������
			
			if(StringUtils.isNotEmpty(bean.getIsverify())){
				int verify = Integer.parseInt(bean.getIsverify());
				if(verify==2){
					isverify.setText("�����ͨ��");
				}else if(verify==1){
					isverify.setText("���δͨ��");
				}else{
					isverify.setText("δ���");
				}
				
			}
			verifier.setText(bean.getVerifier());
			verifytime.setText(bean.getVerifytime());
			verifySuggestion.setText(bean.getVerifySuggestion());
			maintainDate.setText(bean.getMaintainDate());
			String old = bean.getIsold();
			if(old!=null){
				isold.setText(old.equals("1")?"����":"����");
			}
			
			displayMaterialList();
			buttonMaterialManager.setVisibility(View.INVISIBLE);
			buttonScan.setVisibility(View.INVISIBLE);
			
			autoBridgeName.setFocusable(false);
			autoBridgeCode.setFocusable(false);
			taskTypeSpinner.setFocusable(false);
			submitSpinner.setFocusable(false);
			stake.setFocusable(false);
			diseaseName.setFocusable(false);
			diseaseLocation.setFocusable(false);
			diseaseQuantity.setFocusable(false);
			diseaseUnit.setFocusable(false);
			maintainPlan.setFocusable(false);
			planner.setFocusable(false);
			submitDate.setFocusable(false);
			bak.setFocusable(false);
			isverify.setFocusable(false);
			verifier.setFocusable(false);
			verifytime.setFocusable(false);
			verifySuggestion.setFocusable(false);
			maintainDate.setFocusable(false);
			isold.setFocusable(false);

		} else if (interType.equals("add")) {
			// �������Ƶ���������
			mList = new ArrayList<BridgeBean>();
			MyAdapter mAdapter = new MyAdapter(mList, this);
			autoBridgeName.setAdapter(mAdapter);
			autoBridgeName.setThreshold(1); // ��������һ���ַ� ��ʾ��Ĭ��Ϊ2
			autoBridgeName.setOnItemClickListener(this);
			Date today = new Date();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			submitDate.setText(format.format(today));
			submitDate.setOnTouchListener(this);// ����OnTouch����

			// ɨ����������
			buttonScan.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(MaintainTaskDetailActivity.this,
							MipcaActivityCapture.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
				}
			});
			// ���Ϲ����б�
			buttonMaterialManager.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MaintainTaskDetailActivity.this,
							MaterialManagerActivity.class);
					startActivityForResult(intent, MATERIAL_MANAGER_CODE);
				}
			});

			// ��ʼ��ά�������Ĳ����б�
			refreshMaterialList();
		}

	}

	// ˢ�²����б�
	public void refreshMaterialList() {

		MaterialDao materialDao = new MaterialDao(this);
		listMaterial = materialDao.find();
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

			r.addView(t);
			r.addView(etMaterialQuantity[i]);
			r.addView(u);
			tableLayoutPlanAdd.addView(r);

		}
	}

	// ��ʾά�޲���
	public void displayMaterialList() {
		MaintainMaterialDao maintainMaterialDao = new MaintainMaterialDao(this);
		List<MaterialBean> list = maintainMaterialDao
				.queryMaterialByMaintainId(maintainId);

		etMaterialQuantity = new EditText[list.size()];
		for (int i = 0; i < list.size(); i++) {
			MaterialBean material = list.get(i);
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
			etMaterialQuantity[i].setFocusable(false);
			r.addView(t);
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
				// ��ʾɨ�赽������
				String barCode = bundle.getString("result");
				BridgeDao bridgeDao = new BridgeDao(
						MaintainTaskDetailActivity.this);
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
			// ˢ��ά�������Ĳ����б�
			refreshMaterialList();
			break;
		}

	}

	// �ύ�˵�
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	//	getMenuInflater().inflate(R.menu.check, menu);// �޸�
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.usual_menu_submit) {
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
			bean.setIsupload("0");

			MaintainTaskDao maintainTaskDao = new MaintainTaskDao(
					MaintainTaskDetailActivity.this);
			bean.setMaintainTaskId(maintainTaskDao.getMaxId() + 1);
			maintainTaskDao.insert(bean);

			int maintainTaskid = maintainTaskDao.getMaxId();
			MaintainMaterialDao maintainMaterialDao = new MaintainMaterialDao(
					MaintainTaskDetailActivity.this);
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

			// Intent intent = new
			// Intent(MaintainPlanAddActivity.this,MaintainPlanActivity.class);
			// startActivity(intent);
			this.finish();

		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * autoCompleteEditText���Զ���������
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

			if (v.getId() == R.id.submitDate) {
				final int inType = submitDate.getInputType();
				submitDate.setInputType(InputType.TYPE_NULL);
				submitDate.onTouchEvent(event);
				submitDate.setInputType(inType);
				submitDate.setSelection(submitDate.getText().length());

				builder.setTitle("��ѡ��������");
				builder.setPositiveButton("ȷ  ��",
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
}

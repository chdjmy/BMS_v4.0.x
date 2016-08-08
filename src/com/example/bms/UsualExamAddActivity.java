package com.example.bms;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.example.bean.BridgeBean;
import com.example.bean.UsualExamBean;
import com.example.bms.MaintainPlanAddActivity.MyAdapter;
import com.example.bms.MaintainPlanAddActivity.MyAdapter.ViewHolder;
import com.example.dao.BridgeDao;
import com.example.dao.UsualExamDao;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;










//photo-
import com.example.photo.util.Bimp;
import com.example.photo.util.FileUtils;
import com.example.photo.util.ImageItem;
import com.example.photo.util.PublicWay;
import com.example.photo.util.Res;
import com.example.photo.activity.AlbumActivity;
import com.example.photo.activity.GalleryActivity;
import com.mining.app.zxing.view.MipcaActivityCapture;

//-photo

public class UsualExamAddActivity extends BaseActivity implements
		View.OnTouchListener ,OnItemClickListener{


	private EditText text_manager_uint;

	private EditText text_line_number;
	private EditText text_line_name;
	private EditText text_center_stake;

	private EditText text_bridge_code;
	private EditText text_bridge_name;
	private EditText text_mm_name;

	private EditText text_principal;
	private EditText text_noter;
	private EditText text_check_date;

	private EditText text_wall_advice;
	private EditText text_wall_type;
	private EditText text_wall_region;

	private EditText text_slope_advice;
	private EditText text_slope_type;
	private EditText text_slope_region;

	private EditText text_abutment_advice;
	private EditText text_abutment_type;
	private EditText text_abutment_region;

	private EditText text_pier_advice;
	private EditText text_pier_type;
	private EditText text_pier_region;

	private EditText text_foundation_advice;
	private EditText text_foundation_type;
	private EditText text_foundation_region;

	private EditText text_supports_advice;
	private EditText text_supports_type;
	private EditText text_supports_region;

	private EditText text_superstructure_advice;
	private EditText text_superstructure_type;
	private EditText text_superstructure_region;

	private EditText text_approach_advice;
	private EditText text_approach_type;
	private EditText text_approach_region;

	private EditText text_expansion_advice;
	private EditText text_expansion_type;
	private EditText text_expansion_region;

	private EditText text_deck_advice;
	private EditText text_deck_type;
	private EditText text_deck_region;

	private EditText text_sidewalk_advice;
	private EditText text_sidewalk_type;
	private EditText text_sidewalk_region;

	private EditText text_guard_advice;
	private EditText text_guard_type;
	private EditText text_guard_region;

	private EditText text_sign_advice;
	private EditText text_sign_type;
	private EditText text_sign_region;

	private EditText text_waterproof_advice;
	private EditText text_waterproof_type;
	private EditText text_waterproof_region;

	private EditText text_lighting_advice;
	private EditText text_lighting_type;
	private EditText text_lighting_region;

	private EditText text_clean_advice;
	private EditText text_clean_type;
	private EditText text_clean_region;

	private EditText text_regulating_advice;
	private EditText text_regulating_type;
	private EditText text_regulating_region;

	private EditText text_else_advice;
	private EditText text_else_type;
	private EditText text_else_region;

	// 拍照功能-
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap;
	// -拍照功能
	
	//新建功能
	private final static int SCANNIN_GREQUEST_CODE = 2;	// 条码扫描
	private Button buttonScan;
	private AutoCompleteTextView autoBridgeName;
	private List<BridgeBean> mList;// 桥梁搜索列表
	private TextView labelBridgeName;
	//-新建功能

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent().getIntExtra("interType", 0) == -1) {//-1 新建，0-增加，1-修改
			setContentView(R.layout.activity_usual_exam_new);
			buttonScan= (Button)findViewById(R.id.buttonScan);
			autoBridgeName = (AutoCompleteTextView)findViewById(R.id.autoBridgeName);
			labelBridgeName = (TextView)findViewById(R.id.labelBridgeName);
			
			mList = new ArrayList<BridgeBean>();
			MyAdapter mAdapter = new MyAdapter(mList, this);
			autoBridgeName.setAdapter(mAdapter);
			autoBridgeName.setThreshold(1); // 设置输入一个字符 提示，默认为2
			autoBridgeName.setOnItemClickListener(this);
			
			// 扫码搜索功能
			buttonScan.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(UsualExamAddActivity.this,
							MipcaActivityCapture.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
				}
			});
		}else{
			setContentView(R.layout.activity_usual_exam_add);
		}
		setText();

		// photo-
		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(
				R.layout.activity_usual_exam_add, null);
		// setContentView(parentView);
		Init();
		// -photo
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

			if (v.getId() == R.id.date_check_date) {
				final int inType = text_check_date.getInputType();
				text_check_date.setInputType(InputType.TYPE_NULL);
				text_check_date.onTouchEvent(event);
				text_check_date.setInputType(inType);
				text_check_date
						.setSelection(text_check_date.getText().length());

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
								text_check_date.setText(sb);
								dialog.cancel();
							}
						});
			}
			Dialog dialog = builder.create();
			dialog.show();
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if (getIntent().getIntExtra("interType", 0) == 0) {
			getMenuInflater().inflate(R.menu.usual_add, menu);// 增加
		} else if(getIntent().getIntExtra("interType", 0) == -1){
			getMenuInflater().inflate(R.menu.usual_add, menu);// 增加
		}
		else{
			getMenuInflater().inflate(R.menu.usual_change, menu);// 修改
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.usual_menu_submit) {
			UsualExamBean bean = setBean();
			bean.setPhotoAddr(Bimp.savePhotoAddr());//保存图片路径
			
			UsualExamDao dao = new UsualExamDao(this);
			if (getIntent().getIntExtra("interType", 0) == 0) {//增加
				bean.setUsualExamId(String.valueOf(dao.getMaxId() + 1));
				dao.insert(bean);
				displayToast("添加成功,已添加到数据上传列表");
				Bimp.clearPhoto();
				this.finish();
			} else if (getIntent().getIntExtra("interType", 0) == -1){//新增
				bean.setUsualExamId(String.valueOf(dao.getMaxId() + 1));
				dao.insert(bean);
				displayToast("添加成功,已添加到数据上传列表");
				Bimp.clearPhoto();
				this.finish();
			}else {//修改
			
				bean.setUsualExamId(getIntent().getStringExtra("usualExamId"));
				dao.update(bean);
				displayToast("修改成功");
				Bimp.clearPhoto();
				this.finish();
			}

		}
		return super.onOptionsItemSelected(item);
	}

	private void setText() {
		//初始化各种控件
		text_manager_uint = (EditText) findViewById(R.id.text_manager_uint);

		text_line_number = (EditText) findViewById(R.id.text_line_number);
		text_line_name = (EditText) findViewById(R.id.text_line_name);
		text_center_stake = (EditText) findViewById(R.id.text_center_stake);

		text_bridge_code = (EditText) findViewById(R.id.text_bridge_code);
		text_bridge_name = (EditText) findViewById(R.id.text_bridge_name);
		text_mm_name = (EditText) findViewById(R.id.text_mm_name);

		text_principal = (EditText) findViewById(R.id.text_principal);
		text_noter = (EditText) findViewById(R.id.text_noter);
		text_check_date = (EditText) findViewById(R.id.date_check_date);

		text_wall_advice = (EditText) findViewById(R.id.text_wall_advice);
		text_wall_type = (EditText) findViewById(R.id.text_wall_type);
		text_wall_region = (EditText) findViewById(R.id.text_wall_region);

		text_slope_advice = (EditText) findViewById(R.id.text_slope_advice);
		text_slope_type = (EditText) findViewById(R.id.text_slope_type);
		text_slope_region = (EditText) findViewById(R.id.text_slope_region);

		text_abutment_advice = (EditText) findViewById(R.id.text_abutment_advice);
		text_abutment_type = (EditText) findViewById(R.id.text_abutment_type);
		text_abutment_region = (EditText) findViewById(R.id.text_abutment_region);

		text_pier_advice = (EditText) findViewById(R.id.text_pier_advice);
		text_pier_type = (EditText) findViewById(R.id.text_pier_type);
		text_pier_region = (EditText) findViewById(R.id.text_pier_region);

		text_foundation_advice = (EditText) findViewById(R.id.text_foundation_advice);
		text_foundation_type = (EditText) findViewById(R.id.text_foundation_type);
		text_foundation_region = (EditText) findViewById(R.id.text_foundation_region);

		text_supports_advice = (EditText) findViewById(R.id.text_supports_advice);
		text_supports_type = (EditText) findViewById(R.id.text_supports_type);
		text_supports_region = (EditText) findViewById(R.id.text_supports_region);

		text_superstructure_advice = (EditText) findViewById(R.id.text_superstructure_advice);
		text_superstructure_type = (EditText) findViewById(R.id.text_superstructure_type);
		text_superstructure_region = (EditText) findViewById(R.id.text_superstructure_region);

		text_approach_advice = (EditText) findViewById(R.id.text_approach_advice);
		text_approach_type = (EditText) findViewById(R.id.text_approach_type);
		text_approach_region = (EditText) findViewById(R.id.text_approach_region);

		text_expansion_advice = (EditText) findViewById(R.id.text_expansion_advice);
		text_expansion_type = (EditText) findViewById(R.id.text_expansion_type);
		text_expansion_region = (EditText) findViewById(R.id.text_expansion_region);

		text_deck_advice = (EditText) findViewById(R.id.text_deck_advice);
		text_deck_type = (EditText) findViewById(R.id.text_deck_type);
		text_deck_region = (EditText) findViewById(R.id.text_deck_region);

		text_sidewalk_advice = (EditText) findViewById(R.id.text_sidewalk_advice);
		text_sidewalk_type = (EditText) findViewById(R.id.text_sidewalk_type);
		text_sidewalk_region = (EditText) findViewById(R.id.text_sidewalk_region);

		text_guard_advice = (EditText) findViewById(R.id.text_guard_advice);
		text_guard_type = (EditText) findViewById(R.id.text_guard_type);
		text_guard_region = (EditText) findViewById(R.id.text_guard_region);

		text_sign_advice = (EditText) findViewById(R.id.text_sign_advice);
		text_sign_type = (EditText) findViewById(R.id.text_sign_type);
		text_sign_region = (EditText) findViewById(R.id.text_sign_region);

		text_waterproof_advice = (EditText) findViewById(R.id.text_waterproof_advice);
		text_waterproof_type = (EditText) findViewById(R.id.text_waterproof_type);
		text_waterproof_region = (EditText) findViewById(R.id.text_waterproof_region);

		text_lighting_advice = (EditText) findViewById(R.id.text_lighting_advice);
		text_lighting_type = (EditText) findViewById(R.id.text_lighting_type);
		text_lighting_region = (EditText) findViewById(R.id.text_lighting_region);

		text_clean_advice = (EditText) findViewById(R.id.text_clean_advice);
		text_clean_type = (EditText) findViewById(R.id.text_clean_type);
		text_clean_region = (EditText) findViewById(R.id.text_clean_region);

		text_regulating_advice = (EditText) findViewById(R.id.text_regulating_advice);
		text_regulating_type = (EditText) findViewById(R.id.text_regulating_type);
		text_regulating_region = (EditText) findViewById(R.id.text_regulating_region);

		text_else_advice = (EditText) findViewById(R.id.text_else_advice);
		text_else_type = (EditText) findViewById(R.id.text_else_type);
		text_else_region = (EditText) findViewById(R.id.text_else_region);

		if(getIntent().getIntExtra("interType", 0) == -1){//新建
			Date today = new Date();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			text_check_date.setText(format.format(today));
			text_check_date.setOnTouchListener(this);
			return;
		}
		//添加，修改
		String usualExamId = getIntent().getStringExtra("usualExamId");
		UsualExamDao dao = new UsualExamDao(this);
		UsualExamBean bean = null;
		if (getIntent().getIntExtra("interType", 0) == 0) {
			bean = dao.queryLastById(usualExamId);// 0-增加，显示上个月的数据
		} else {
			bean = dao.queryById(usualExamId);    // 1-修改
			String picAddr = bean.getPhotoAddr();
			Bimp.displayPhoto(picAddr);
		}
		
		// 设置内容
		text_manager_uint.setText(bean.getManagerUnit());
		text_line_number.setText(bean.getLineNumber());
		text_line_name.setText(bean.getLineName());
		text_center_stake.setText(String.valueOf(bean.getCenterStake()));
		text_bridge_code.setText(bean.getBridgeCode());
		text_bridge_name.setText(bean.getBridgeName());
		text_mm_name.setText(bean.getMmName());
		text_principal.setText(bean.getPrincipal());
		text_noter.setText(bean.getNoter());

		if (getIntent().getIntExtra("interType", 0) == 0) {
			// Date today = new Date();
			// DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// text_check_date.setText(format.format(today));
			text_check_date.setText(bean.getCheckDate());
		} else {
			text_check_date.setText(bean.getCheckDate());
		}
		text_check_date.setOnTouchListener(this);// 设置OnTouch监听

		text_wall_advice.setText(bean.getWallAdvise());
		text_wall_type.setText(bean.getWallType());
		text_wall_region.setText(bean.getWallRegion());
		text_slope_advice.setText(bean.getSlopeAdvise());
		text_slope_type.setText(bean.getSlopeType());
		text_slope_region.setText(bean.getSlopeRegion());
		text_abutment_advice.setText(bean.getAbutmentAdvise());
		text_abutment_type.setText(bean.getAbutmentType());
		text_abutment_region.setText(bean.getAbutmentRegion());
		text_pier_advice.setText(bean.getPierRegion());
		text_pier_type.setText(bean.getPierType());
		text_pier_region.setText(bean.getPierRegion());
		text_foundation_advice.setText(bean.getFoundationAdvise());
		text_foundation_type.setText(bean.getFoundationType());
		text_foundation_region.setText(bean.getFoundationRegion());
		text_supports_advice.setText(bean.getSupportsAdvise());
		text_supports_type.setText(bean.getSupportsType());
		text_supports_region.setText(bean.getSupportsRegion());
		text_superstructure_advice.setText(bean.getSuperstructureAdvise());
		text_superstructure_type.setText(bean.getSuperstructureType());
		text_superstructure_region.setText(bean.getSuperstructureRegion());
		text_approach_advice.setText(bean.getApproachAdvise());
		text_approach_type.setText(bean.getApproachType());
		text_approach_region.setText(bean.getApproachRegion());
		text_expansion_advice.setText(bean.getExpansionAdvise());
		text_expansion_type.setText(bean.getExpansionType());
		text_expansion_region.setText(bean.getExpansionRegion());
		text_deck_advice.setText(bean.getDeckAdvise());
		text_deck_type.setText(bean.getDeckType());
		text_deck_region.setText(bean.getDeckRegion());
		text_sidewalk_advice.setText(bean.getSidewalkAdvise());
		text_sidewalk_type.setText(bean.getSidewalkType());
		text_sidewalk_region.setText(bean.getSidewalkRegion());
		text_guard_advice.setText(bean.getGuardAdvise());
		text_guard_type.setText(bean.getGuardType());
		text_guard_region.setText(bean.getGuardRegion());
		text_sign_advice.setText(bean.getSignAdvise());
		text_sign_type.setText(bean.getSignType());
		text_sign_region.setText(bean.getSignRegion());
		text_waterproof_advice.setText(bean.getWaterproofAdvise());
		text_waterproof_type.setText(bean.getWaterproofType());
		text_waterproof_region.setText(bean.getWaterproofRegion());
		text_lighting_advice.setText(bean.getLightingAdvise());
		text_lighting_type.setText(bean.getLightingType());
		text_lighting_region.setText(bean.getLightingRegion());
		text_clean_advice.setText(bean.getCleanAdvise());
		text_clean_type.setText(bean.getCleanType());
		text_clean_region.setText(bean.getCleanRegion());
		text_regulating_advice.setText(bean.getRegulatingAdvise());
		text_regulating_type.setText(bean.getRegulatingType());
		text_regulating_region.setText(bean.getRegulatingRegion());
		text_else_advice.setText(bean.getElseAdvise());
		text_else_type.setText(bean.getElseType());
		text_else_region.setText(bean.getElseRegion());

	}

	private UsualExamBean setBean() {
		
		UsualExamBean bean = null;
		if(getIntent().getIntExtra("interType", 0) == -1){//新建
			bean = new UsualExamBean();
		}else{
			String usualExamId = getIntent().getStringExtra("usualExamId");
			UsualExamDao dao = new UsualExamDao(this);
			bean = dao.get(usualExamId);
		}
		// 获取内容
		bean.setManagerUnit(text_manager_uint.getText().toString());
		bean.setLineNumber(text_line_number.getText().toString());
		bean.setLineName(text_line_name.getText().toString());
		bean.setCenterStake(Double.parseDouble(text_center_stake.getText().toString()));
		bean.setBridgeCode(text_bridge_code.getText().toString());
		bean.setBridgeName(text_bridge_name.getText().toString());
		bean.setMmName(text_mm_name.getText().toString());
		bean.setPrincipal(text_principal.getText().toString());
		bean.setNoter(text_noter.getText().toString());
		bean.setCheckDate(text_check_date.getText().toString());
		bean.setWallAdvise(text_wall_advice.getText().toString());
		bean.setWallType(text_wall_type.getText().toString());
		bean.setWallRegion(text_wall_region.getText().toString());
		bean.setSlopeAdvise(text_slope_advice.getText().toString());
		bean.setSlopeType(text_slope_type.getText().toString());
		bean.setSlopeRegion(text_slope_region.getText().toString());
		bean.setAbutmentAdvise(text_abutment_advice.getText().toString());
		bean.setAbutmentType(text_abutment_type.getText().toString());
		bean.setAbutmentRegion(text_abutment_region.getText().toString());
		bean.setPierRegion(text_pier_advice.getText().toString());
		bean.setPierType(text_pier_type.getText().toString());
		bean.setPierRegion(text_pier_region.getText().toString());
		bean.setFoundationAdvise(text_foundation_advice.getText().toString());
		bean.setFoundationType(text_foundation_type.getText().toString());
		bean.setFoundationRegion(text_foundation_region.getText().toString());
		bean.setSupportsAdvise(text_supports_advice.getText().toString());
		bean.setSupportsType(text_supports_type.getText().toString());
		bean.setSupportsRegion(text_supports_region.getText().toString());
		bean.setSuperstructureAdvise(text_superstructure_advice.getText()
				.toString());
		bean.setSuperstructureType(text_superstructure_type.getText()
				.toString());
		bean.setSuperstructureRegion(text_superstructure_region.getText()
				.toString());
		bean.setApproachAdvise(text_approach_advice.getText().toString());
		bean.setApproachType(text_approach_type.getText().toString());
		bean.setApproachRegion(text_approach_region.getText().toString());
		bean.setExpansionAdvise(text_expansion_advice.getText().toString());
		bean.setExpansionType(text_expansion_type.getText().toString());
		bean.setExpansionRegion(text_expansion_region.getText().toString());
		bean.setDeckAdvise(text_deck_advice.getText().toString());
		bean.setDeckType(text_deck_type.getText().toString());
		bean.setDeckRegion(text_deck_region.getText().toString());
		bean.setSidewalkAdvise(text_sidewalk_advice.getText().toString());
		bean.setSidewalkType(text_sidewalk_type.getText().toString());
		bean.setSidewalkRegion(text_sidewalk_region.getText().toString());
		bean.setGuardAdvise(text_guard_advice.getText().toString());
		bean.setGuardType(text_guard_type.getText().toString());
		bean.setGuardRegion(text_guard_region.getText().toString());
		bean.setSignAdvise(text_sign_advice.getText().toString());
		bean.setSignType(text_sign_type.getText().toString());
		bean.setSignRegion(text_sign_region.getText().toString());
		bean.setWaterproofAdvise(text_waterproof_advice.getText().toString());
		bean.setWaterproofType(text_waterproof_type.getText().toString());
		bean.setWaterproofRegion(text_waterproof_region.getText().toString());
		bean.setLightingAdvise(text_lighting_advice.getText().toString());
		bean.setLightingType(text_lighting_type.getText().toString());
		bean.setLightingRegion(text_lighting_region.getText().toString());
		bean.setCleanAdvise(text_clean_advice.getText().toString());
		bean.setCleanType(text_clean_type.getText().toString());
		bean.setCleanRegion(text_clean_region.getText().toString());
		bean.setRegulatingAdvise(text_regulating_advice.getText().toString());
		bean.setRegulatingType(text_regulating_type.getText().toString());
		bean.setRegulatingRegion(text_regulating_region.getText().toString());
		bean.setElseAdvise(text_else_advice.getText().toString());
		bean.setElseType(text_else_type.getText().toString());
		bean.setElseRegion(text_else_region.getText().toString());
		bean.setIsUpload("0");
		
		String usualExamId = getIntent().getStringExtra("usualExamId");
		UsualExamDao dao = new UsualExamDao(this);
		bean.setDepCode(dao.findDepCodeByUsuslExamId(usualExamId));
		
		return bean;
	}

	// photo-
	public void Init() {

		pop = new PopupWindow(UsualExamAddActivity.this);

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
				Intent intent = new Intent(UsualExamAddActivity.this,
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
							UsualExamAddActivity.this,
							R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {// 预览图片
					Intent intent = new Intent(UsualExamAddActivity.this,
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
				if (position == 9) {
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

	private static final int TAKE_PICTURE = 0x000001;
	private Uri mOutputFileUri;
	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		mOutputFileUri = Uri.fromFile(new File(FileUtils.SDPATH+String.valueOf(System.currentTimeMillis()+".JPEG")));
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

				String fileName = String.valueOf(System.currentTimeMillis());
				String filePath = null;
				if(data!=null){
					if(data.hasExtra("data")){
						//从intent的Data里直接的获取的图片，是缩略图？
						Bitmap bm = (Bitmap) data.getExtras().get("data");
						filePath = FileUtils.saveBitmap(bm, fileName);
						
					}
				}
				else{
					filePath = mOutputFileUri.getPath();
				}
				mediaScan(filePath);
				ImageItem takePhoto = new ImageItem();
				// takePhoto.setBitmap(bm);
				takePhoto.setImagePath(filePath);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// 显示扫描到的内容
				String barCode = bundle.getString("result");
				BridgeDao bridgeDao = new BridgeDao(
						UsualExamAddActivity.this);
				List<BridgeBean> list = bridgeDao.findByBar(barCode,
						getDepCode());
				if (list.size() > 0) {
					BridgeBean bean = list.get(0);
					autoBridgeName.setText(barCode);
					text_manager_uint.setText("甘肃省酒泉公路管理局");
					text_line_number.setText(bean.getLineNumber());
					text_line_name.setText(bean.getLineName());
					text_center_stake.setText(String.valueOf(bean.getCenterStake()));
					text_bridge_code.setText(bean.getBridgeCode());
					text_bridge_name.setText(bean.getBridgeName());
					text_mm_name.setText(bean.getMmName());
					labelBridgeName.setText("桥梁条码:");
				}else{
					displayToast("该桥梁不在区域范围内");
				}
			}
			break;
		}
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
//			displayToast("onKeyDown方法调用");
		}

		return true;
	}

	private void mediaScan(final String filePath) {

		MediaScannerConnectionClient mediaScannerClient = new MediaScannerConnectionClient() {

			private MediaScannerConnection msc = null;
			{
				msc = new MediaScannerConnection(UsualExamAddActivity.this,
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		BridgeBean bean = mList.get(position);
		autoBridgeName.setText(bean.getBridgeName());
		text_manager_uint.setText("甘肃省酒泉公路管理局");
		text_line_number.setText(bean.getLineNumber());
		text_line_name.setText(bean.getLineName());
		text_center_stake.setText(String.valueOf(bean.getCenterStake()));
		text_bridge_code.setText(bean.getBridgeCode());
		text_bridge_name.setText(bean.getBridgeName());
		text_mm_name.setText(bean.getMmName());
		labelBridgeName.setText("桥梁名称：");
	}
	

}

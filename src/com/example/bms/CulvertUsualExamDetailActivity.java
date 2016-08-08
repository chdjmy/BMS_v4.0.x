package com.example.bms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.example.bean.CulvertBean;
import com.example.bean.CulvertUsualExamBean;
import com.example.dao.CulvertDao;
import com.example.dao.CulvertUsualExamDao;
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

public class CulvertUsualExamDetailActivity extends BaseActivity implements
View.OnTouchListener,OnItemClickListener{

	private EditText text_culvert_code;
	private EditText text_culvert_name;
	private EditText text_line_number;
	private EditText text_line_name;
	private EditText text_center_stake;

	private EditText text_MM_name;
	private EditText text_culvert_type;
	private EditText text_maintain_name;

	private EditText text_inlet_type_disease;
	private EditText text_inlet_region;
	private EditText text_inlet_advise;
	private EditText text_outlet_type_disease;
	private EditText text_outlet_region;
	private EditText text_outlet_advise;
	private EditText text_body_type;
	private EditText text_body_region;
	private EditText text_body_advise;
	private EditText text_top_type;
	private EditText text_top_region;
	private EditText text_top_advise;
	private EditText text_bottom_type;
	private EditText text_bottom_region;
	private EditText text_bottom_advise;
	private EditText text_fill_type;
	private EditText text_fill_region;
	private EditText text_fill_advise;
	private EditText text_lighting_type;
	private EditText text_lighting_region;
	private EditText text_lighting_advise;
	private EditText text_else_type;
	private EditText text_else_region;
	private EditText text_else_advise;
	private EditText text_remark;

	private EditText text_manager_name;
	private EditText text_noter;
	private EditText text_check_date;
	
	private int flag;
	private String usualExamId;
	
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
	private List<CulvertBean> mList;// 桥梁搜索列表
	private TextView labelBridgeName;
	//-新建功能

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		flag = getIntent().getIntExtra("flag", 0);//0表示详情，1表示增加，2表示修改,-1表示新建
		usualExamId = getIntent().getStringExtra("usualExamId");
		CulvertUsualExamDao dao = new CulvertUsualExamDao(this);
		
		switch(flag){
		case -1://新建
			setContentView(R.layout.activity_culvert_usual_exam_new);
			initEditText();
			Date today = new Date();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
			text_check_date.setText(format.format(today));
			text_check_date.setOnTouchListener(this);
			// photo-
			Res.init(this);
			bimap = BitmapFactory.decodeResource(getResources(),
					R.drawable.icon_addpic_unfocused);
			PublicWay.activityList.add(this);
			parentView = getLayoutInflater().inflate(
					R.layout.activity_culvert_usual_exam_new, null);
			Init();
			// -photo
			buttonScan= (Button)findViewById(R.id.buttonScan);
			autoBridgeName = (AutoCompleteTextView)findViewById(R.id.autoBridgeName);
			labelBridgeName = (TextView)findViewById(R.id.labelBridgeName);
			
			mList = new ArrayList<CulvertBean>();
			MyAdapter mAdapter = new MyAdapter(mList, this);
			autoBridgeName.setAdapter(mAdapter);
			autoBridgeName.setThreshold(1); // 设置输入一个字符 提示，默认为2
			autoBridgeName.setOnItemClickListener(this);
			
			// 扫码搜索功能
			buttonScan.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(CulvertUsualExamDetailActivity.this,
							MipcaActivityCapture.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
				}
			});
			
			break;
		case 0:
			setContentView(R.layout.activity_culvert_usual_exam_detail);
			initEditText();
			CulvertUsualExamBean bean = dao.queryById(usualExamId);
			setText(bean);
			
			break;
		case 1://增加
		case 2://修改
			setContentView(R.layout.activity_culvert_usual_exam_add);
			initEditText();
			CulvertUsualExamBean beanAdd = dao.queryById(usualExamId);
			setAddText(beanAdd);
			// photo-
			Res.init(this);
			bimap = BitmapFactory.decodeResource(getResources(),
					R.drawable.icon_addpic_unfocused);
			PublicWay.activityList.add(this);
			parentView = getLayoutInflater().inflate(
					R.layout.activity_culvert_usual_exam_add, null);
			Init();
			// -photo
			
			break;
		
		default:
			break;
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

			if (v.getId() == R.id.text_check_date) {
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
		switch(flag){
		case -1:
			getMenuInflater().inflate(R.menu.usual_add, menu);//新建
			break;
		case 0:
			break;
		case 1:
			getMenuInflater().inflate(R.menu.usual_add, menu);//增加
			break;
		case 2:
			getMenuInflater().inflate(R.menu.usual_change, menu);//修改
			break;
		default:
			break;
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
			CulvertUsualExamBean bean = setBean();
			bean.setPhotoAddr(Bimp.savePhotoAddr());//保存图片路径
			CulvertUsualExamDao dao = new CulvertUsualExamDao(this);
			switch(flag){
			case -1:
				bean.setCusualExamId(String.valueOf(dao.getMaxId()+1));
				dao.insert(bean);
				displayToast("添加成功，已提交到数据上传列表");
				Bimp.clearPhoto();
				this.finish();
				break;
			case 0:
				break;
			case 1:
				bean.setCusualExamId(String.valueOf(dao.getMaxId()+1));
				dao.insert(bean);
				displayToast("添加成功，已提交到数据上传列表");
				Bimp.clearPhoto();
				this.finish();
				break;
			case 2:
				bean.setCusualExamId(usualExamId);
				dao.update(bean);
				displayToast("修改成功");
				Bimp.clearPhoto();
				this.finish();
				break;
			default:
				break;
			}
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void setAddText(CulvertUsualExamBean bean){
		text_culvert_code.setText(bean.getCulvertCode());
		text_culvert_name.setText(bean.getCulvertName());
		text_line_number.setText(bean.getLineNumber());
		text_line_name.setText(bean.getLineName());
		text_center_stake.setText(String.valueOf(bean.getCenterStake()));

		text_MM_name.setText(bean.getMmName());
		text_culvert_type.setText(bean.getCulvertType());
		text_maintain_name.setText(bean.getMaintainName());

		text_inlet_type_disease.setText(bean.getInletTypeDisease());
		text_inlet_region.setText(bean.getInletRegion());
		text_inlet_advise.setText(bean.getInletAdvise());
		text_outlet_type_disease.setText(bean.getOutletTypeDisease());
		text_outlet_region.setText(bean.getOutletRegion());
		text_outlet_advise.setText(bean.getOutletAdvise());
		text_body_type.setText(bean.getBodyType());
		text_body_region.setText(bean.getBodyRegion());
		text_body_advise.setText(bean.getBodyAdvise());
		text_top_type.setText(bean.getTopType());
		text_top_region.setText(bean.getTopRegion());
		text_top_advise.setText(bean.getTopAdvise());
		text_bottom_type.setText(bean.getBottomType());
		text_bottom_region.setText(bean.getBottomRegion());
		text_bottom_advise.setText(bean.getBottomAdvise());
		text_fill_type.setText(bean.getFillType());
		text_fill_region.setText(bean.getFillRegion());
		text_fill_advise.setText(bean.getFillAdvise());
		text_lighting_type.setText(bean.getLightingType());
		text_lighting_region.setText(bean.getLightingAdvise());
		text_lighting_advise.setText(bean.getLightingType());
		text_else_type.setText(bean.getElseType());
		text_else_region.setText(bean.getElseAdvise());
		text_else_advise.setText(bean.getElseAdvise());
		text_remark.setText(bean.getRemark());

		text_manager_name.setText(bean.getMaintainName());
		text_noter.setText(bean.getNoter());
		Date today = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		text_check_date.setText(format.format(today));
		text_check_date.setOnTouchListener(this);
		
		String picAddr = bean.getPhotoAddr();
		Bimp.displayPhoto(picAddr);
	}
	
	public void setText(CulvertUsualExamBean bean){
		text_culvert_code.setText(bean.getCulvertCode());
		text_culvert_name.setText(bean.getCulvertName());
		text_line_number.setText(bean.getLineNumber());
		text_line_name.setText(bean.getLineName());
		text_center_stake.setText(String.valueOf(bean.getCenterStake()));

		text_MM_name.setText(bean.getMmName());
		text_culvert_type.setText(bean.getCulvertType());
		text_maintain_name.setText(bean.getMaintainName());

		text_inlet_type_disease.setText(bean.getInletTypeDisease());
		text_inlet_region.setText(bean.getInletRegion());
		text_inlet_advise.setText(bean.getInletAdvise());
		text_outlet_type_disease.setText(bean.getOutletTypeDisease());
		text_outlet_region.setText(bean.getOutletRegion());
		text_outlet_advise.setText(bean.getOutletAdvise());
		text_body_type.setText(bean.getBodyType());
		text_body_region.setText(bean.getBodyRegion());
		text_body_advise.setText(bean.getBodyAdvise());
		text_top_type.setText(bean.getTopType());
		text_top_region.setText(bean.getTopRegion());
		text_top_advise.setText(bean.getTopAdvise());
		text_bottom_type.setText(bean.getBottomType());
		text_bottom_region.setText(bean.getBottomRegion());
		text_bottom_advise.setText(bean.getBottomAdvise());
		text_fill_type.setText(bean.getFillType());
		text_fill_region.setText(bean.getFillRegion());
		text_fill_advise.setText(bean.getFillAdvise());
		text_lighting_type.setText(bean.getLightingType());
		text_lighting_region.setText(bean.getLightingAdvise());
		text_lighting_advise.setText(bean.getLightingType());
		text_else_type.setText(bean.getElseType());
		text_else_region.setText(bean.getElseAdvise());
		text_else_advise.setText(bean.getElseAdvise());
		text_remark.setText(bean.getRemark());

		text_manager_name.setText(bean.getMaintainName());
		text_noter.setText(bean.getNoter());
		text_check_date.setText(bean.getCheckDate());
		String picAddr = bean.getPhotoAddr();
		if(!StringUtils.isEmpty(picAddr)){
			// photo-
			Res.init(this);
//			bimap = BitmapFactory.decodeResource(getResources(),
//					R.drawable.icon_addpic_unfocused);
			PublicWay.activityList.add(this);
			parentView = getLayoutInflater().inflate(
					R.layout.activity_culvert_usual_exam_detail, null);
			Init();
			// -photo
			Bimp.displayPhoto(picAddr);
		}
		
	}
	
	public CulvertUsualExamBean setBean(){
		
		CulvertUsualExamBean bean = null;
		if(flag==-1){
			bean = new CulvertUsualExamBean();
		}else{
			CulvertUsualExamDao dao = new CulvertUsualExamDao(this);
			bean = dao.get(usualExamId);
		}
		
		bean.setCulvertCode(text_culvert_code.getText().toString());
		
		bean.setCulvertName(text_culvert_name.getText().toString());
		bean.setLineNumber(text_line_number.getText().toString());
		bean.setLineName(text_line_name.getText().toString());
		bean.setCenterStake(Double.parseDouble(text_center_stake.getText().toString()));

		bean.setMmName(text_MM_name.getText().toString());
		bean.setCulvertType(text_culvert_type.getText().toString());
		bean.setMaintainName(text_maintain_name.getText().toString());

		bean.setInletTypeDisease(text_inlet_type_disease.getText().toString());
		bean.setInletRegion(text_inlet_region.getText().toString());
		bean.setInletAdvise(text_inlet_advise.getText().toString());
		bean.setOutletTypeDisease(text_outlet_type_disease.getText().toString());
		bean.setOutletRegion(text_outlet_region.getText().toString());
		bean.setOutletAdvise(text_outlet_advise.getText().toString());
		bean.setBodyType(text_body_type.getText().toString());
		bean.setBodyRegion(text_body_region.getText().toString());
		bean.setBodyAdvise(text_body_advise.getText().toString());
		bean.setTopType(text_top_type.getText().toString());
		bean.setTopRegion(text_top_region.getText().toString());
		bean.setTopAdvise(text_top_advise.getText().toString());
		bean.setBottomType(text_bottom_type.getText().toString());
		bean.setBottomRegion(text_bottom_region.getText().toString());
		bean.setBottomAdvise(text_bottom_advise.getText().toString());
		bean.setFillType(text_fill_type.getText().toString());
		bean.setFillRegion(text_fill_region.getText().toString());
		bean.setFillAdvise(text_fill_advise.getText().toString());
		bean.setLightingType(text_lighting_type.getText().toString());
		bean.setLightingAdvise(text_lighting_region.getText().toString());
		bean.setLightingType(text_lighting_advise.getText().toString());
		bean.setElseType(text_else_type.getText().toString());
		bean.setElseAdvise(text_else_region.getText().toString());
		bean.setElseAdvise(text_else_advise.getText().toString());
		bean.setRemark(text_remark.getText().toString());

		bean.setMaintainName(text_manager_name.getText().toString());
		bean.setNoter(text_noter.getText().toString());
		bean.setCheckDate(text_check_date.getText().toString());
		bean.setIsUpload("0");
		
		CulvertUsualExamDao dao = new CulvertUsualExamDao(this);
		bean.setDepCode(dao.findDepCodeByUsuslExamId(usualExamId));
		
		return bean;
	}

	public void initEditText() {
		text_culvert_code = (EditText) findViewById(R.id.text_culvert_code);
		text_culvert_name = (EditText) findViewById(R.id.text_culvert_name);
		text_line_number = (EditText) findViewById(R.id.text_line_number);
		text_line_name = (EditText) findViewById(R.id.text_line_name);
		text_center_stake = (EditText) findViewById(R.id.text_center_stake);

		text_MM_name = (EditText) findViewById(R.id.text_MM_name);
		text_culvert_type = (EditText) findViewById(R.id.text_culvert_type);
		text_maintain_name = (EditText) findViewById(R.id.text_maintain_name);

		text_inlet_type_disease = (EditText) findViewById(R.id.text_inlet_type_disease);
		text_inlet_region = (EditText) findViewById(R.id.text_inlet_region);
		text_inlet_advise = (EditText) findViewById(R.id.text_inlet_advise);
		text_outlet_type_disease = (EditText) findViewById(R.id.text_outlet_type_disease);
		text_outlet_region = (EditText) findViewById(R.id.text_outlet_region);
		text_outlet_advise = (EditText) findViewById(R.id.text_outlet_advise);
		text_body_type = (EditText) findViewById(R.id.text_body_type);
		text_body_region = (EditText) findViewById(R.id.text_body_region);
		text_body_advise = (EditText) findViewById(R.id.text_body_advise);
		text_top_type = (EditText) findViewById(R.id.text_top_type);
		text_top_region = (EditText) findViewById(R.id.text_top_region);
		text_top_advise = (EditText) findViewById(R.id.text_top_advise);
		text_bottom_type = (EditText) findViewById(R.id.text_bottom_type);
		text_bottom_region = (EditText) findViewById(R.id.text_bottom_region);
		text_bottom_advise = (EditText) findViewById(R.id.text_bottom_advise);
		text_fill_type = (EditText) findViewById(R.id.text_fill_type);
		text_fill_region = (EditText) findViewById(R.id.text_fill_region);
		text_fill_advise = (EditText) findViewById(R.id.text_fill_advise);
		text_lighting_type = (EditText) findViewById(R.id.text_lighting_type);
		text_lighting_region = (EditText) findViewById(R.id.text_lighting_region);
		text_lighting_advise = (EditText) findViewById(R.id.text_lighting_advise);
		text_else_type = (EditText) findViewById(R.id.text_else_type);
		text_else_region = (EditText) findViewById(R.id.text_else_region);
		text_else_advise = (EditText) findViewById(R.id.text_else_advise);
		text_remark = (EditText) findViewById(R.id.text_remark);

		text_manager_name = (EditText) findViewById(R.id.text_manager_name);
		text_noter = (EditText) findViewById(R.id.text_noter);
		text_check_date = (EditText) findViewById(R.id.text_check_date);
	}
	// photo-
	public void Init() {

		pop = new PopupWindow(CulvertUsualExamDetailActivity.this);

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
				Intent intent = new Intent(CulvertUsualExamDetailActivity.this,
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
							CulvertUsualExamDetailActivity.this,
							R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {// 预览图片
					Intent intent = new Intent(CulvertUsualExamDetailActivity.this,
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
				if (position == 9||flag==0) {//详情不显示加号
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

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
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
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// 显示扫描到的内容
				String barCode = bundle.getString("result");
				CulvertDao culvertDao = new CulvertDao(CulvertUsualExamDetailActivity.this);
				List<CulvertBean> list = culvertDao.findByBar(barCode,getDepCode());
				if (list.size() > 0) {
					CulvertBean bean = list.get(0);
					autoBridgeName.setText(barCode);
					text_culvert_code.setText(bean.getCulvertCode());
					text_culvert_name.setText(bean.getCulvertName());
					text_line_number.setText(bean.getLineNumber());
					text_line_name.setText(bean.getLineName());
					text_center_stake.setText(String.valueOf(bean.getCenterStake()));
					text_MM_name.setText(bean.getMmName());
					text_culvert_type.setText(bean.getCulvertType());
					labelBridgeName.setText("涵洞条码:");
				}else{
					displayToast("该涵洞不在区域范围内");
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
		}

		return true;
	}

	private void mediaScan(final String filePath) {

		MediaScannerConnectionClient mediaScannerClient = new MediaScannerConnectionClient() {

			private MediaScannerConnection msc = null;
			{
				msc = new MediaScannerConnection(CulvertUsualExamDetailActivity.this,
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
		private ArrayList<CulvertBean> mUnfilteredData;

		public MyAdapter(List<CulvertBean> mList, Context context) {
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

			CulvertBean bg = mList.get(position);
			holder.tv_name.setText(bg.getCulvertName()+" "+bg.getCulvertCode());

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
					mUnfilteredData = new ArrayList<CulvertBean>(mList);
				}

				if (prefix == null || prefix.length() == 0) {
					ArrayList<CulvertBean> list = mUnfilteredData;
					results.values = list;
					results.count = list.size();
				} else {
					String prefixString = prefix.toString().toLowerCase();

					ArrayList<CulvertBean> unfilteredValues = mUnfilteredData;

					CulvertDao culvertDao = new CulvertDao(context);
					List<CulvertBean> newValues = culvertDao.findByName(
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
				mList = (List<CulvertBean>) results.values;
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

		CulvertBean bean = mList.get(position);
		autoBridgeName.setText(bean.getCulvertName());
		text_culvert_code.setText(bean.getCulvertCode());
		text_culvert_name.setText(bean.getCulvertName());
		text_line_number.setText(bean.getLineNumber());
		text_line_name.setText(bean.getLineName());
		text_center_stake.setText(String.valueOf(bean.getCenterStake()));
		text_MM_name.setText(bean.getMmName());
		text_culvert_type.setText(bean.getCulvertType());
		labelBridgeName.setText("涵洞名称：");
	}
}

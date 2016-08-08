package com.example.bms;

import org.apache.commons.lang.StringUtils;

import com.example.bean.UsualExamBean;
import com.example.bms.UsualExamAddActivity.GridAdapter;
import com.example.bms.UsualExamAddActivity.GridAdapter.ViewHolder;
import com.example.dao.UsualExamDao;
import com.example.photo.activity.AlbumActivity;
import com.example.photo.activity.GalleryActivity;
import com.example.photo.util.Bimp;
import com.example.photo.util.FileUtils;
import com.example.photo.util.ImageItem;
import com.example.photo.util.PublicWay;
import com.example.photo.util.Res;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

public class UsualExamDetailActivity extends BaseActivity {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usual_exam_detail);
		String usualExamId = getIntent().getStringExtra("usualExamId");
		UsualExamDao dao = new UsualExamDao(this);
		UsualExamBean bean = dao.queryById(usualExamId);
		
		text_manager_uint = (EditText)findViewById(R.id.text_manager_uint);
		
		text_line_number = (EditText)findViewById(R.id.text_line_number);
		text_line_name = (EditText)findViewById(R.id.text_line_name);
		text_center_stake = (EditText)findViewById(R.id.text_center_stake);
		
		text_bridge_code = (EditText)findViewById(R.id.text_bridge_code);
		text_bridge_name = (EditText)findViewById(R.id.text_bridge_name);
		text_mm_name = (EditText)findViewById(R.id.text_mm_name);
		
		text_principal = (EditText)findViewById(R.id.text_principal);
		text_noter = (EditText)findViewById(R.id.text_noter);
		text_check_date = (EditText)findViewById(R.id.text_check_date);
		
		text_wall_advice = (EditText)findViewById(R.id.text_wall_advice);
		text_wall_type = (EditText)findViewById(R.id.text_wall_type);
		text_wall_region = (EditText)findViewById(R.id.text_wall_region);
		
		text_slope_advice = (EditText)findViewById(R.id.text_slope_advice);
		text_slope_type = (EditText)findViewById(R.id.text_slope_type);
		text_slope_region = (EditText)findViewById(R.id.text_slope_region);
		
		text_abutment_advice = (EditText)findViewById(R.id.text_abutment_advice);
		text_abutment_type = (EditText)findViewById(R.id.text_abutment_type);
		text_abutment_region = (EditText)findViewById(R.id.text_abutment_region);

		text_pier_advice = (EditText)findViewById(R.id.text_pier_advice);
		text_pier_type = (EditText)findViewById(R.id.text_pier_type);
		text_pier_region = (EditText)findViewById(R.id.text_pier_region);

		text_foundation_advice = (EditText)findViewById(R.id.text_foundation_advice);
		text_foundation_type = (EditText)findViewById(R.id.text_foundation_type);
		text_foundation_region = (EditText)findViewById(R.id.text_foundation_region);

		text_supports_advice = (EditText)findViewById(R.id.text_supports_advice);
		text_supports_type = (EditText)findViewById(R.id.text_supports_type);
		text_supports_region = (EditText)findViewById(R.id.text_supports_region);

		text_superstructure_advice = (EditText)findViewById(R.id.text_superstructure_advice);
		text_superstructure_type = (EditText)findViewById(R.id.text_superstructure_type);
		text_superstructure_region = (EditText)findViewById(R.id.text_superstructure_region);

		text_approach_advice = (EditText)findViewById(R.id.text_approach_advice);
		text_approach_type = (EditText)findViewById(R.id.text_approach_type);
		text_approach_region = (EditText)findViewById(R.id.text_approach_region);

		text_expansion_advice = (EditText)findViewById(R.id.text_expansion_advice);
		text_expansion_type = (EditText)findViewById(R.id.text_expansion_type);
		text_expansion_region = (EditText)findViewById(R.id.text_expansion_region);

		text_deck_advice = (EditText)findViewById(R.id.text_deck_advice);
		text_deck_type = (EditText)findViewById(R.id.text_deck_type);
		text_deck_region = (EditText)findViewById(R.id.text_deck_region);

		text_sidewalk_advice = (EditText)findViewById(R.id.text_sidewalk_advice);
		text_sidewalk_type = (EditText)findViewById(R.id.text_sidewalk_type);
		text_sidewalk_region = (EditText)findViewById(R.id.text_sidewalk_region);

		text_guard_advice = (EditText)findViewById(R.id.text_guard_advice);
		text_guard_type = (EditText)findViewById(R.id.text_guard_type);
		text_guard_region = (EditText)findViewById(R.id.text_guard_region);

		text_sign_advice = (EditText)findViewById(R.id.text_sign_advice);
		text_sign_type = (EditText)findViewById(R.id.text_sign_type);
		text_sign_region = (EditText)findViewById(R.id.text_sign_region);

		text_waterproof_advice = (EditText)findViewById(R.id.text_waterproof_advice);
		text_waterproof_type = (EditText)findViewById(R.id.text_waterproof_type);
		text_waterproof_region = (EditText)findViewById(R.id.text_waterproof_region);

		text_lighting_advice = (EditText)findViewById(R.id.text_lighting_advice);
		text_lighting_type = (EditText)findViewById(R.id.text_lighting_type);
		text_lighting_region = (EditText)findViewById(R.id.text_lighting_region);

		text_clean_advice = (EditText)findViewById(R.id.text_clean_advice);
		text_clean_type = (EditText)findViewById(R.id.text_clean_type);
		text_clean_region = (EditText)findViewById(R.id.text_clean_region);

		text_regulating_advice = (EditText)findViewById(R.id.text_regulating_advice);
		text_regulating_type = (EditText)findViewById(R.id.text_regulating_type);
		text_regulating_region = (EditText)findViewById(R.id.text_regulating_region);

		text_else_advice = (EditText)findViewById(R.id.text_else_advice);
		text_else_type = (EditText)findViewById(R.id.text_else_type);
		text_else_region = (EditText)findViewById(R.id.text_else_region);
		
		//设置内容
		text_manager_uint.setText(bean.getManagerUnit());
		text_line_number.setText(bean.getLineNumber());
		text_line_name.setText(bean.getLineName());
		text_center_stake.setText(String.valueOf(bean.getCenterStake()));
		text_bridge_code.setText(bean.getBridgeCode());
		text_bridge_name.setText(bean.getBridgeName());
		text_mm_name.setText(bean.getMmName());
		text_principal.setText(bean.getPrincipal());
		text_noter.setText(bean.getNoter());
		text_check_date.setText(bean.getCheckDate());
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
		String picAddr = bean.getPhotoAddr();
		if(!StringUtils.isEmpty(picAddr)){
			// photo-
			Res.init(this);
//			bimap = BitmapFactory.decodeResource(getResources(),
//					R.drawable.icon_addpic_unfocused);
			PublicWay.activityList.add(this);
			parentView = getLayoutInflater().inflate(
					R.layout.activity_usual_exam_detail, null);
			Init();
			// -photo
			Bimp.displayPhoto(picAddr);
		}
		
	}
	
	// photo-
	public void Init() {

		pop = new PopupWindow(UsualExamDetailActivity.this);

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
				Intent intent = new Intent(UsualExamDetailActivity.this,
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
							UsualExamDetailActivity.this,
							R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {// 预览图片
					Intent intent = new Intent(UsualExamDetailActivity.this,
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
					holder.image.setVisibility(View.GONE);
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
				msc = new MediaScannerConnection(UsualExamDetailActivity.this,
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

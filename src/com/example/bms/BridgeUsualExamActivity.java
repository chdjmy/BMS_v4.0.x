package com.example.bms;

import java.util.List;

import com.example.bean.UsualExamBean;
import com.example.dao.UsualExamDao;
import com.mining.app.zxing.view.MipcaActivityCapture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class BridgeUsualExamActivity extends BaseActivity {
	// 条码扫描返回码
	private final static int SCANNIN_GREQUEST_CODE = 1;

	private ListView listViewBridgeUsualExam;
	private List<UsualExamBean> listUsualExams;
	private int page;
	private boolean isLoading;
	private UsualExamDao usualExamDao;
	private MyListAdapter adapter;

	private Button buttonScan;
	private Button buttonSearch;
	private EditText editSearch;
	private ImageView imageClear;
	private Button buttonNewUsual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bridge_usual_exam);

		listViewBridgeUsualExam = (ListView) findViewById(R.id.listViewBridgeUsualExam);
		usualExamDao = new UsualExamDao(this);
		page = 0;
		isLoading = false;
		listUsualExams = usualExamDao.findByPage(page,getDepCode());
		page = page + 30;
		adapter = new MyListAdapter(BridgeUsualExamActivity.this,
				R.layout.listitem_bridge_usualexam, listUsualExams);
		listViewBridgeUsualExam.setAdapter(adapter);
		listViewBridgeUsualExam.setOnScrollListener(new MyOnScrollListener());

		// 搜索功能
		editSearch = (EditText) findViewById(R.id.editSearch);
		buttonScan = (Button) findViewById(R.id.buttonScan);
		buttonScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(BridgeUsualExamActivity.this,
						MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}

		});
		buttonSearch = (Button) findViewById(R.id.buttonSearch);
		buttonSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = editSearch.getText().toString();
				if (str.equals("")) {
					displayToast("您输入的检索信息为空");
				} else {
					listUsualExams.clear();
					listUsualExams.addAll(0, usualExamDao.findByName(str,getDepCode()));
					adapter.notifyDataSetChanged();
					isLoading = true;
				}

			}

		});

		imageClear = (ImageView) findViewById(R.id.imageClear);
		imageClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editSearch.setText("");
				page = 0;
				isLoading = false;
				listUsualExams.clear();
				listUsualExams.addAll(usualExamDao.findByPage(page,getDepCode()));
				page = page + 30;
				adapter.notifyDataSetChanged();
			}

		});
		
		buttonNewUsual = (Button) findViewById(R.id.buttonNewUsual);
		buttonNewUsual.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(BridgeUsualExamActivity.this,
						UsualExamAddActivity.class);
				intent.putExtra("interType", -1);
				startActivity(intent);
			}

		});
	}

	// 条码扫描回调函数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// 显示扫描到的内容
				String barCode = bundle.getString("result");
				editSearch.setText(barCode);
				listUsualExams.clear();
				listUsualExams.addAll(0, usualExamDao.findByBar(barCode,getDepCode()));
				adapter.notifyDataSetChanged();
				isLoading = true;
			}
			break;
		}
	}

	private class MyOnScrollListener implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// 判断列表是否触底
			if (firstVisibleItem + visibleItemCount == totalItemCount
					&& totalItemCount != 0) {
				if (isLoading || page == -1) {
					return;
				}
				// 新建异步刷新并执行
				new AsyncTask<Void, String, List<UsualExamBean>>() {
					protected List<UsualExamBean> doInBackground(Void... params) {
						List<UsualExamBean> listNewBridges = null;
						try {
							listNewBridges = usualExamDao.findByPage(page,getDepCode());
							page = page + 30;

						} catch (Exception e) {
							publishProgress("已显示全部桥梁");
							e.printStackTrace();
						}
						return listNewBridges;
					}

					protected void onPreExecute() {
						// ll_loading.setVisibility(View.VISIBLE);
						isLoading = true;
						super.onPreExecute();
					}

					protected void onPostExecute(List<UsualExamBean> result) {
						if (result.size() == 0) {
							page = -1;
						} else {
							listUsualExams.addAll(result);
							adapter.notifyDataSetChanged();
						}

						// ll_loading.setVisibility(View.GONE);
						isLoading = false;
						super.onPostExecute(result);
					}

					protected void onProgressUpdate(String... values) {
						super.onProgressUpdate(values);
					}

				}.execute();
			}

		}

	}

	private class MyListAdapter extends ArrayAdapter<UsualExamBean> {
		private int resource;

		public MyListAdapter(Context context, int resourceId,
				List<UsualExamBean> objects) {
			super(context, resourceId, objects);
			// 记录下来稍后使用
			resource = resourceId;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LinearLayout listView;
			// 获取数据
			UsualExamBean b = getItem(position);
			String bridgeCode = b.getBridgeCode();
			String bridgeName = b.getBridgeName();
			String lineNumber = b.getCheckDate();
			// String lineName = b.getPrincipal();
			// String lineType = b.getNoter();
			// String locationName = b.getCheckDate();

			if (convertView == null) {
				listView = new LinearLayout(getContext());
				String inflater = Context.LAYOUT_INFLATER_SERVICE;
				LayoutInflater vi = (LayoutInflater) getContext()
						.getSystemService(inflater);
				vi.inflate(resource, listView, true);
			} else {
				listView = (LinearLayout) convertView;
			}

			// 填充自定义数据
			TextView item_bridgeCode = (TextView) listView
					.findViewById(R.id.bridgeCode);
			TextView item_bridgeName = (TextView) listView
					.findViewById(R.id.bridgeName);
			TextView item_lineNumber = (TextView) listView
					.findViewById(R.id.lineNumber);
			// TextView item_lineName = (TextView)
			// listView.findViewById(R.id.lineName);
			// TextView item_lineType = (TextView)
			// listView.findViewById(R.id.lineType);
			Button btn_info = (Button) listView.findViewById(R.id.btn_info);
			Button btn_add = (Button) listView.findViewById(R.id.btn_add);

			// TextView item_locationName = (TextView)
			// listView.findViewById(R.id.locationName);
			item_bridgeCode.setText(bridgeCode);
			item_bridgeName.setText(bridgeName);
			item_lineNumber.setText(lineNumber);
			// item_lineName.setText(lineName);
			// item_lineType.setText(lineType);
			// item_locationName.setText(locationName);
			btn_info.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(BridgeUsualExamActivity.this,
							UsualExamDetailActivity.class);
					intent.putExtra("usualExamId", listUsualExams.get(position)
							.getUsualExamId());
					startActivity(intent);
				}
			});
			btn_add.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(BridgeUsualExamActivity.this,
							UsualExamAddActivity.class);
					intent.putExtra("usualExamId", listUsualExams.get(position)
							.getUsualExamId());
					intent.putExtra("interType", 0);
					startActivity(intent);
				}
			});

			int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色
			listView.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同

			return listView;
		}
	}
}

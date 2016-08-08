package com.example.bms;

import java.util.List;

import com.example.bean.CulvertUsualExamBean;
import com.example.dao.CulvertUsualExamDao;
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

public class CulvertUsualExamActivity extends BaseActivity {
	//条码扫描返回码
	private final static int SCANNIN_GREQUEST_CODE = 1;

	private ListView listViewCulvertUsualExam;
	private List<CulvertUsualExamBean> listUsualExams;
	private int page;
	private boolean isLoading;
	private CulvertUsualExamDao usualExamDao;
	private MyListAdapter adapter;

	private Button buttonScan;
	private Button buttonSearch;
	private EditText editSearch;
	private ImageView imageClear;
	private Button buttonNewCusual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_culvert_usual_exam);

		listViewCulvertUsualExam = (ListView) findViewById(R.id.listViewCulvertUsualExam);
		usualExamDao = new CulvertUsualExamDao(this);
		page = 0;
		isLoading = false;
		listUsualExams = usualExamDao.findByPage(page,getDepCode());
		page = page + 30;
		adapter = new MyListAdapter(CulvertUsualExamActivity.this,
				R.layout.listitem_bridge_usualexam, listUsualExams);
		listViewCulvertUsualExam.setAdapter(adapter);
		listViewCulvertUsualExam.setOnScrollListener(new MyOnScrollListener());

		// 搜索功能
		editSearch = (EditText) findViewById(R.id.editSearch);
		buttonScan = (Button) findViewById(R.id.buttonScan);
		buttonScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CulvertUsualExamActivity.this, MipcaActivityCapture.class);
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
		buttonNewCusual = (Button) findViewById(R.id.buttonNewCusual);
		buttonNewCusual.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CulvertUsualExamActivity.this,
						CulvertUsualExamDetailActivity.class);
				intent.putExtra("usualExamId", "1");
				intent.putExtra("flag", -1);
				startActivity(intent);
			}
		});
	}

	//条码扫描回调函数
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				//显示扫描到的内容
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
			// 判断列表是否已经触底
			if (firstVisibleItem + visibleItemCount == totalItemCount
					&& totalItemCount != 0) {
				if (isLoading || page == -1) {
					return;
				}
				// 异步刷新
				new AsyncTask<Void, String, List<CulvertUsualExamBean>>() {
					protected List<CulvertUsualExamBean> doInBackground(
							Void... params) {
						List<CulvertUsualExamBean> listNewBridges = null;
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

					protected void onPostExecute(
							List<CulvertUsualExamBean> result) {
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

	private class MyListAdapter extends ArrayAdapter<CulvertUsualExamBean> {
		private int resource;

		public MyListAdapter(Context context, int resourceId,
				List<CulvertUsualExamBean> objects) {
			super(context, resourceId, objects);
			// 记录下来稍后使用
			resource = resourceId;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LinearLayout listView;
			// 获取数据
			CulvertUsualExamBean b = getItem(position);
			String bridgeCode = b.getCulvertCode();
			String bridgeName = b.getCulvertName();
			String lineNumber = b.getCheckDate().substring(0, 10);
			// String lineName = b.getMaintainName();
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
					intent.setClass(CulvertUsualExamActivity.this,
							CulvertUsualExamDetailActivity.class);
					intent.putExtra("usualExamId", listUsualExams.get(position)
							.getCusualExamId());
					intent.putExtra("flag", 0);
					startActivity(intent);
				}
			});
			btn_add.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(CulvertUsualExamActivity.this,
							CulvertUsualExamDetailActivity.class);
					intent.putExtra("usualExamId", listUsualExams.get(position)
							.getCusualExamId());
					intent.putExtra("flag", 1);
					startActivity(intent);
				}
			});

			int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色
			listView.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同

			return listView;
		}
	}
}

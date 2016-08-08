package com.example.bms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bean.BridgeBean;
import com.example.bean.MaintainTaskBean;
import com.example.bean.MemberBean;
import com.example.dao.BridgeDao;
import com.example.dao.MaintainTaskDao;
import com.example.dao.MemberDao;
import com.mining.app.zxing.view.MipcaActivityCapture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class MaintainPlanActivity extends BaseActivity {
	// 条码扫描返回码
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private final static int PLAN_REQUEST_CODE = 2;

	// 搜索框
	private Button buttonScan;
	private Button buttonSearch;
	private EditText editSearch;
	private ImageView imageClear;

	private Button buttonNewPlan;// 新建维修计划按钮
	private ListView listViewMaintainPlan;// 维修计划视图list
	private List<MaintainTaskBean> listMaintainPlans; // 维修计划数据list
	private MaintainTaskDao maintainPlanDao;// 维修计划dao
	private MyListAdapter adapter;
	private int page;
	private boolean isLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintain_plan);

		initSearchBar();

		buttonNewPlan = (Button) findViewById(R.id.buttonNewPlan);
		buttonNewPlan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MaintainPlanActivity.this,
						MaintainPlanAddActivity.class);
				intent.putExtra("interType", "add");
				startActivityForResult(intent,PLAN_REQUEST_CODE);
			}

		});

		// listView
		listViewMaintainPlan = (ListView) findViewById(R.id.listViewMaintainPlan);
		// list
		maintainPlanDao = new MaintainTaskDao(this);
		// adapter
		page = 0;
		isLoading = false;
		listMaintainPlans = maintainPlanDao.findByPage(page,getDepCode(),1);
		page = page + 30;
		adapter = new MyListAdapter(MaintainPlanActivity.this,
				R.layout.listitem_maintain_plan, listMaintainPlans);
		listViewMaintainPlan.setAdapter(adapter);
		listViewMaintainPlan.setOnScrollListener(new MyOnScrollListener());
		listViewMaintainPlan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MaintainPlanActivity.this,
						MaintainPlanAddActivity.class);
				intent.putExtra("interType", "query");
				MaintainTaskBean bean = listMaintainPlans.get(position);
				int maintainId = bean.getMaintainTaskId();
				intent.putExtra("maintainId", String.valueOf(maintainId));
				startActivity(intent);

			}
		});

	}

	private void initSearchBar() {

		// 搜索功能
		editSearch = (EditText) findViewById(R.id.editSearch);
		buttonScan = (Button) findViewById(R.id.buttonScan);
		buttonScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MaintainPlanActivity.this,
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
					listMaintainPlans.clear();
					listMaintainPlans.addAll(0,
							maintainPlanDao.findByBirdgeName(str,getDepCode()));
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
				listMaintainPlans.clear();
				listMaintainPlans.addAll(maintainPlanDao.findByPage(page,getDepCode(),1));
				page = page + 30;
				adapter.notifyDataSetChanged();
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
				listMaintainPlans.clear();
				listMaintainPlans.addAll(0, maintainPlanDao.findByBridgeBar(barCode,getDepCode()));
				adapter.notifyDataSetChanged();
				isLoading = true;
			}
			break;
		case PLAN_REQUEST_CODE:
			page = 0;
			isLoading = false;
			listMaintainPlans.clear();
			listMaintainPlans.addAll(maintainPlanDao.findByPage(page,getDepCode(),1));
			page = page + 30;
			adapter.notifyDataSetChanged();
			break;
		}
		
	}

	private class MyListAdapter extends ArrayAdapter<MaintainTaskBean> {
		private int resource;

		public MyListAdapter(Context context, int resourceId,
				List<MaintainTaskBean> objects) {
			super(context, resourceId, objects);
			// 记录下来稍后使用
			resource = resourceId;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout listView;
			// 获取数据
			MaintainTaskBean b = getItem(position);
			String maintainCode = b.getMaintainCode();
			String bridgeName = b.getBridgeName();
			String submitDate = b.getSubmitDate().substring(0, 10);
			String isUpload = b.getIsupload();

			if (convertView == null) {
				listView = new LinearLayout(getContext());
				String inflater = Context.LAYOUT_INFLATER_SERVICE;
				LayoutInflater vi = (LayoutInflater) getContext()
						.getSystemService(inflater);
				vi.inflate(resource, listView, true);
			} else {
				// 很奇怪基本没被调用过
				listView = (LinearLayout) convertView;
			}

			// 填充自定义数据
			TextView item_bridgeCode = (TextView) listView
					.findViewById(R.id.bridgeCode);
			TextView item_bridgeName = (TextView) listView
					.findViewById(R.id.bridgeName);
			TextView item_lineNumber = (TextView) listView
					.findViewById(R.id.lineNumber);
			TextView item_lineName = (TextView) listView
					.findViewById(R.id.lineName);

			item_bridgeCode.setText(maintainCode);
			item_bridgeName.setText(bridgeName);
			item_lineNumber.setText(submitDate);
			item_lineName.setText(isUpload.equals("0")?"待上传":"待审核");

			int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色
			listView.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同

			return listView;
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
			// 判断是否已触底
			if (firstVisibleItem + visibleItemCount == totalItemCount
					&& totalItemCount != 0) {
				if (isLoading || page == -1) {
					return;
				}
				// 异步刷新
				new AsyncTask<Void, String, List<MaintainTaskBean>>() {
					protected List<MaintainTaskBean> doInBackground(
							Void... params) {
						List<MaintainTaskBean> listNewMaintainPlans = null;
						try {
							listNewMaintainPlans = maintainPlanDao
									.findByPage(page,getDepCode(),1);
							page = page + 30;

						} catch (Exception e) {
							publishProgress("已显示全部桥梁");
							e.printStackTrace();
						}
						return listNewMaintainPlans;
					}

					protected void onPreExecute() {
						isLoading = true;
						super.onPreExecute();
					}

					protected void onPostExecute(List<MaintainTaskBean> result) {
						if (result.size() == 0) {
							page = -1;
						} else {
							listMaintainPlans.addAll(result);
							adapter.notifyDataSetChanged();
						}
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

}

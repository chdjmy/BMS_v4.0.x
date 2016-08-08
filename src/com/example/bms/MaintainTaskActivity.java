package com.example.bms;

import java.util.List;

import com.example.bean.MaintainTaskBean;
import com.example.dao.MaintainTaskDao;
import com.mining.app.zxing.view.MipcaActivityCapture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class MaintainTaskActivity extends BaseActivity {

	// ����ɨ�践����
	private final static int SCANNIN_GREQUEST_CODE = 1;

	// ������
	private Button buttonScan;
	private Button buttonSearch;
	private EditText editSearch;
	private ImageView imageClear;

	private ListView listViewMaintainPlan;// ά�޼ƻ���ͼlist
	private List<MaintainTaskBean> listMaintainPlans; // ά�޼ƻ�����list
	private MaintainTaskDao maintainPlanDao;// ά�޼ƻ�dao
	private MyListAdapter adapter;
	private int page;
	private boolean isLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintain_task);

		initSearchBar();

		// listView
		listViewMaintainPlan = (ListView) findViewById(R.id.listViewMaintainPlan);
		// list
		maintainPlanDao = new MaintainTaskDao(this);
		// adapter
		page = 0;
		isLoading = false;
		listMaintainPlans = maintainPlanDao.findByPage(page,getDepCode(),3);
		page = page + 30;
		adapter = new MyListAdapter(MaintainTaskActivity.this,
				R.layout.listitem_maintain_plan, listMaintainPlans);
		listViewMaintainPlan.setAdapter(adapter);
		listViewMaintainPlan.setOnScrollListener(new MyOnScrollListener());
		listViewMaintainPlan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MaintainTaskActivity.this,
						MaintainTaskDetailActivity.class);
				intent.putExtra("interType", "query");
				MaintainTaskBean bean = listMaintainPlans.get(position);
				int maintainId = bean.getMaintainTaskId();
				intent.putExtra("maintainId", String.valueOf(maintainId));
				startActivity(intent);

			}
		});

	}

	private void initSearchBar() {

		// ��������
		editSearch = (EditText) findViewById(R.id.editSearch);
		buttonScan = (Button) findViewById(R.id.buttonScan);
		buttonScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MaintainTaskActivity.this,
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
					displayToast("������ļ�����ϢΪ��");
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
				listMaintainPlans.addAll(maintainPlanDao.findByPage(page,getDepCode(),3));
				page = page + 30;
				adapter.notifyDataSetChanged();
			}

		});
	}

	// ����ɨ��ص�����
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// ��ʾɨ�赽������
				String barCode = bundle.getString("result");
				editSearch.setText(barCode);
				listMaintainPlans.clear();
				listMaintainPlans.addAll(0,
						maintainPlanDao.findByBridgeBar(barCode,getDepCode()));
				adapter.notifyDataSetChanged();
				isLoading = true;
			}
			break;
		}
	}

	private class MyListAdapter extends ArrayAdapter<MaintainTaskBean> {
		private int resource;

		public MyListAdapter(Context context, int resourceId,
				List<MaintainTaskBean> objects) {
			super(context, resourceId, objects);
			// ��¼�����Ժ�ʹ��
			resource = resourceId;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout listView;
			// ��ȡ����
			MaintainTaskBean b = getItem(position);
			String maintainCode = b.getMaintainCode();
			String bridgeName = b.getBridgeName();
			String submitDate = b.getSubmitDate().substring(0, 10);
			String isOld = b.getIsold();

			if (convertView == null) {
				listView = new LinearLayout(getContext());
				String inflater = Context.LAYOUT_INFLATER_SERVICE;
				LayoutInflater vi = (LayoutInflater) getContext()
						.getSystemService(inflater);
				vi.inflate(resource, listView, true);
			} else {
				// ����ֻ���û�����ù�
				listView = (LinearLayout) convertView;
			}

			// ����Զ�������
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
			item_lineName.setText("��ͨ��");

			int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB��ɫ
			listView.setBackgroundColor(colors[position % 2]);// ÿ��item֮����ɫ��ͬ

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
			// �ж��Ƿ��Ѵ���
			if (firstVisibleItem + visibleItemCount == totalItemCount
					&& totalItemCount != 0) {
				if (isLoading || page == -1) {
					return;
				}
				// �첽ˢ��
				new AsyncTask<Void, String, List<MaintainTaskBean>>() {
					protected List<MaintainTaskBean> doInBackground(
							Void... params) {
						List<MaintainTaskBean> listNewMaintainPlans = null;
						try {
							listNewMaintainPlans = maintainPlanDao
									.findByPage(page,getDepCode(),3);
							page = page + 30;

						} catch (Exception e) {
							publishProgress("����ʾȫ������");
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

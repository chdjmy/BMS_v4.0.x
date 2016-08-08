package com.example.bms;

import android.os.Bundle;

import java.util.List;

import com.example.bean.BridgeBean;
import com.example.bean.BridgeProjectBean;
import com.example.dao.BridgeDao;
import com.example.dao.BridgeProjectDao;
import com.mining.app.zxing.view.MipcaActivityCapture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 桥梁基本信息
 * @author JMY
 * 
 */
public class BridgeProjectActivity extends BaseActivity {
	//条码扫描返回码
	private final static int SCANNIN_GREQUEST_CODE = 1;

	private ListView listViewBridgeInfo;
	private List<BridgeProjectBean> listBridges;
	private int page;
	private boolean isLoading;
	private BridgeProjectDao bridgeDao;
	private MyListAdapter adapter;
	
	private Button buttonScan;
	private Button buttonSearch;
	private EditText editSearch;
	private ImageView imageClear;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bridge_project);
		listViewBridgeInfo = (ListView) findViewById(R.id.listViewBridgeInfo);
		bridgeDao = new BridgeProjectDao(this);
		page = 0;
		isLoading = false;
		listBridges = bridgeDao.findByPage(page,getDepCode());
		page = page + 30;
		adapter = new MyListAdapter(BridgeProjectActivity.this,   
                 R.layout.listitem_bridge_info,   
                 listBridges); 
		listViewBridgeInfo.setAdapter(adapter);
		listViewBridgeInfo.setOnItemClickListener(new MyItemClickListener());
		listViewBridgeInfo.setOnScrollListener(new MyOnScrollListener());
		
		//搜索功能
		editSearch = (EditText)findViewById(R.id.editSearch);
		buttonScan = (Button)findViewById(R.id.buttonScan);
		buttonScan.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(BridgeProjectActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
			
		});
		buttonSearch = (Button)findViewById(R.id.buttonSearch);
		buttonSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = editSearch.getText().toString();
				if(str.equals("")){
					displayToast("您输入的检索信息为空");
				
				}else{
					listBridges.clear();
					listBridges.addAll(0, bridgeDao.findByName(str,getDepCode()));
					adapter.notifyDataSetChanged();	
					isLoading = true;
				}
				
			}
			
		});
		
		imageClear = (ImageView)findViewById(R.id.imageClear);
		imageClear.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editSearch.setText("");
				page = 0;
				isLoading = false;
				listBridges.clear();
				listBridges.addAll(bridgeDao.findByPage(page,getDepCode()));
				page = page + 30;
				adapter.notifyDataSetChanged();	
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
				listBridges.clear();
				listBridges.addAll(0, bridgeDao.findByBar(barCode,getDepCode()));
				adapter.notifyDataSetChanged();	
				isLoading = true;
			}
			break;
		}
    }	


	private class MyItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			//displayToast(listBridges.get(position).getBridgeName());
			Intent intent = new Intent();
			
			intent.putExtra("projectId", listBridges.get(position).getProjectId());
			intent.setClass(BridgeProjectActivity.this,ProjectDetailActivity.class);
			startActivity(intent);
		}
		
	}
	
	private class MyOnScrollListener implements OnScrollListener{

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount!=0){
				
				if(isLoading||page==-1){
					return;
				}
				//异步刷新
				new AsyncTask<Void, String, List<BridgeProjectBean>>() {
					protected List<BridgeProjectBean> doInBackground(Void... params) {
						List<BridgeProjectBean> listNewBridges = null;
						try {
							listNewBridges = bridgeDao.findByPage(page,getDepCode());
							page = page + 30;
							
						} catch (Exception e) {
							publishProgress("已显示全部桥梁");
							e.printStackTrace();
						}
						return listNewBridges;
					}

					protected void onPreExecute() {
						//ll_loading.setVisibility(View.VISIBLE);
						isLoading = true;
						super.onPreExecute();
					}

					protected void onPostExecute(List<BridgeProjectBean> result) {
						if(result.size()==0){
							page = -1;
						}else{
							listBridges.addAll(result);
							adapter.notifyDataSetChanged();	
						}
						
						//ll_loading.setVisibility(View.GONE);
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
	
	private class MyListAdapter extends ArrayAdapter<BridgeProjectBean>{ 
	    private int resource; 
	    public MyListAdapter(Context context, int resourceId, List<BridgeProjectBean> objects) { 
	        super(context, resourceId, objects); 
	        // 记录下来稍后使用 
	        resource = resourceId; 
	    }
	    public View getView(int position, View convertView, ViewGroup parent) { 
	        LinearLayout listView; 
	        // 获取数据 
	        BridgeProjectBean b = getItem(position); 
	        String bridgeCode = b.getBridgeCode();
	        String bridgeName = b.getBridgeName();
	        String lineNumber = b.getProjectDate().substring(0,10);
	        String locationName = String.valueOf(b.getBudgetCost());
	
	        if(convertView == null) { 
	            listView = new LinearLayout(getContext()); 
	            String inflater = Context.LAYOUT_INFLATER_SERVICE; 
	            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater); 
	            vi.inflate(resource, listView, true); 
	            Log.d("Adapter", "convertView is null now"); 
	        } else { 
	            // 很奇怪基本没被调用过 
	            listView = (LinearLayout)convertView; 
	            Log.d("Adapter", "convertView is not null now"); 
	        }
	
	        // 填充自定义数据 
	        TextView item_bridgeCode = (TextView) listView.findViewById(R.id.bridgeCode); 
	        TextView item_bridgeName = (TextView) listView.findViewById(R.id.bridgeName); 
	        TextView item_lineNumber = (TextView) listView.findViewById(R.id.lineNumber);
	        TextView item_locationName = (TextView) listView.findViewById(R.id.locationName);
	        item_bridgeCode.setText(bridgeCode);
	        item_bridgeName.setText(bridgeName);
	        item_lineNumber.setText(lineNumber);
	        item_locationName.setText(locationName);
	   
	        int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色
	        listView.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同
	
	        return listView; 
	    }
	}
}

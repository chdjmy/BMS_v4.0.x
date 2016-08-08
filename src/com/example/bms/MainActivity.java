package com.example.bms;

import com.example.bean.SettingBean;
import com.example.dao.BridgeDao;
import com.example.util.Common;
import com.example.util.Uri;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
/**
 * 系统主页面
 * @author JMY
 *
 */
public class MainActivity extends BaseActivity implements OnClickListener{
	

	private TextView main_bridge_info;
	private TextView main_bridge_check;
	private TextView main_bridge_project;
	private TextView main_maintenance_check;
	private TextView main_culvert_info;
	private TextView main_culvert_check;
	
	private TextView main_maintenance_plan;
	private TextView main_maintenance_task;
	private TextView main_data_upload;
	private TextView main_data_synchronization;
	private TextView main_personal_info;
	private TextView main_network_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_bridge_info = (TextView)findViewById(R.id.main_bridge_info);
        main_bridge_check = (TextView)findViewById(R.id.main_bridge_check);
        main_bridge_project = (TextView)findViewById(R.id.main_bridge_project);
        main_maintenance_check = (TextView)findViewById(R.id.main_maintenance_check);
        main_culvert_info = (TextView)findViewById(R.id.main_culvert_info);
        main_culvert_check = (TextView)findViewById(R.id.main_culvert_check);
        
        main_maintenance_plan = (TextView)findViewById(R.id.main_maintenance_plan);
        main_maintenance_task = (TextView)findViewById(R.id.main_maintenance_task);
        main_data_upload = (TextView)findViewById(R.id.main_data_upload);
        main_data_synchronization = (TextView)findViewById(R.id.main_data_synchronization);
        main_personal_info = (TextView)findViewById(R.id.main_personal_info);
        main_network_settings = (TextView)findViewById(R.id.main_network_settings);
        
        main_bridge_info.setOnClickListener(this);
        main_bridge_check.setOnClickListener(this);
        main_bridge_project.setOnClickListener(this);
        main_maintenance_check.setOnClickListener(this);
        main_culvert_info.setOnClickListener(this);
        main_culvert_check.setOnClickListener(this);
        
        main_maintenance_plan.setOnClickListener(this);
        main_maintenance_task.setOnClickListener(this);
        main_data_upload.setOnClickListener(this);
        main_data_synchronization.setOnClickListener(this);
        main_personal_info.setOnClickListener(this);
        main_network_settings.setOnClickListener(this);
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.main_bridge_info:
			startActivity(new Intent(MainActivity.this,BridgeInfoActivity.class));
			break;
		case R.id.main_bridge_check:
			startActivity(new Intent(MainActivity.this,BridgeUsualExamActivity.class));
			break;
			
		case R.id.main_bridge_project:
			startActivity(new Intent(MainActivity.this,BridgeProjectActivity.class));
			break;
			
		case R.id.main_maintenance_check:
			startActivity(new Intent(MainActivity.this, MaintainCheckActivity.class));
			break;
			
		case R.id.main_culvert_info:
			startActivity(new Intent(MainActivity.this, CulvertInfoActivity.class));
			break;
			
		case R.id.main_culvert_check:
			startActivity(new Intent(MainActivity.this, CulvertUsualExamActivity.class));
			break;
			
		case R.id.main_maintenance_plan:
			startActivity(new Intent(MainActivity.this, MaintainPlanActivity.class));
			break;
			
		case R.id.main_maintenance_task:
			startActivity(new Intent(MainActivity.this, MaintainTaskActivity.class));
			break;
			
		case R.id.main_data_upload:
			startActivity(new Intent(MainActivity.this, DataUploadActivity.class));
			break;
			
		case R.id.main_data_synchronization:
			startActivity(new Intent(MainActivity.this, DataSyncActivity.class));
			break;
			
		case R.id.main_personal_info:
			startActivity(new Intent(MainActivity.this, PersonalInfoActivity.class));
			break;
			
		case R.id.main_network_settings:
			startActivity(new Intent(MainActivity.this, NetSettingsActivity.class));
			break;
		default:
			break;
		
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.main_menu_exit) {
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setTitle("提示");
			builder.setMessage("确认退出系统吗?");
			builder.setPositiveButton("退出",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							System.exit(0);
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
						}
					});
			builder.show();
			
//		}else if(id == R.id.login_menu_settings){
//			startActivity(new Intent(MainActivity.this, NetSettingsActivity.class));
		}
			
		return super.onOptionsItemSelected(item);
	}
}

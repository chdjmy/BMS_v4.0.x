package com.example.bms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bean.CulvertBean;
import com.example.bean.MemberBean;
import com.example.bean.UsersBean;
import com.example.dao.CulvertDao;
import com.example.dao.MemberDao;
import com.example.dao.UsersDao;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PersonalInfoActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info);
		
		String[] memberItems = new String[] { 
				"登录名：", "登录密码：", "中文全名：","部门：","职位：", "电话号码：","QQ号码：","msn：",
				"性别：",  "生日：","地址：", "状态：", "备注："};//"邮箱：", 
		
		UsersDao usersDao = new UsersDao(this);
		UsersBean bean = usersDao.getCurrentUser();
		bean.setPassword("******");
		
		int iLength = memberItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", memberItems[i]);
		}
		
		maps[0].put("value",bean.getUsername());//登录名
		maps[1].put("value",bean.getPassword());//登录密码
		maps[2].put("value",bean.getName());//中文全名
		maps[3].put("value",bean.getDepartment());//部门
		maps[4].put("value",bean.getPostionId());//职位
		maps[5].put("value",bean.getTelephone());//电话号码
		maps[6].put("value",bean.getQq());//QQ号码
		maps[7].put("value",bean.getMsn());//msn
		maps[8].put("value",bean.getSex());//性别
		maps[9].put("value",bean.getBirthday());//生日
		maps[10].put("value",bean.getAddress());//地址
		maps[11].put("value",bean.getState());//状态
		maps[12].put("value",bean.getBak());//备注
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
		
		ListView listView = ((ListView)findViewById(R.id.list_personal_info));
		SimpleAdapter listAdapter = new SimpleAdapter(this, list,
				R.layout.listitem_bridgeinfo_detail, new String[] { "name",
						"value" }, new int[] { R.id.name, R.id.value });
		listView.setAdapter(listAdapter);
	}
}

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
				"��¼����", "��¼���룺", "����ȫ����","���ţ�","ְλ��", "�绰���룺","QQ���룺","msn��",
				"�Ա�",  "���գ�","��ַ��", "״̬��", "��ע��"};//"���䣺", 
		
		UsersDao usersDao = new UsersDao(this);
		UsersBean bean = usersDao.getCurrentUser();
		bean.setPassword("******");
		
		int iLength = memberItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", memberItems[i]);
		}
		
		maps[0].put("value",bean.getUsername());//��¼��
		maps[1].put("value",bean.getPassword());//��¼����
		maps[2].put("value",bean.getName());//����ȫ��
		maps[3].put("value",bean.getDepartment());//����
		maps[4].put("value",bean.getPostionId());//ְλ
		maps[5].put("value",bean.getTelephone());//�绰����
		maps[6].put("value",bean.getQq());//QQ����
		maps[7].put("value",bean.getMsn());//msn
		maps[8].put("value",bean.getSex());//�Ա�
		maps[9].put("value",bean.getBirthday());//����
		maps[10].put("value",bean.getAddress());//��ַ
		maps[11].put("value",bean.getState());//״̬
		maps[12].put("value",bean.getBak());//��ע
		
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

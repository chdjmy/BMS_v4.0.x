package com.example.bms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.example.bean.MaintainMaterialBean;
import com.example.bean.MaintainTaskBean;
import com.example.bean.MaterialBean;
import com.example.dao.MaintainMaterialDao;
import com.example.dao.MaintainTaskDao;
import com.example.dao.MaterialDao;
import com.example.photo.util.Bimp;
import com.example.photo.util.PublicWay;
import com.example.util.ShareMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MaterialManagerActivity extends BaseActivity {

	private ListView listViewMaterial;
	private MyListAdapter adapter;
	private List<MaterialBean> listMaterial;
	// 增加材料
	private Button buttonMaterialAdd;
	private EditText editMaterialName;
	private EditText editMaterialUnit;
	private EditText editMaterialBak;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_material_manager);
		// 初始化材料列表
		listViewMaterial = (ListView) findViewById(R.id.listViewMaterial);
		MaterialDao materialDao = new MaterialDao(this);
		listMaterial = materialDao.find();
		adapter = new MyListAdapter(MaterialManagerActivity.this,
				R.layout.listitem_material_text, listMaterial);
		listViewMaterial.setAdapter(adapter);

		// 新增材料
		buttonMaterialAdd = (Button) findViewById(R.id.buttonMaterialAdd);
		buttonMaterialAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = LayoutInflater.from(MaterialManagerActivity.this);
		        View dialogAddMaterial = layoutInflater.inflate(R.layout.dialog_add_material, null);
		        editMaterialName = (EditText)dialogAddMaterial.findViewById(R.id.materialName);
		        editMaterialUnit = (EditText)dialogAddMaterial.findViewById(R.id.materialUnit);
		        editMaterialBak = (EditText)dialogAddMaterial.findViewById(R.id.materialBak);
		        
				new AlertDialog.Builder(MaterialManagerActivity.this)
				.setTitle("增加材料")
				.setIcon(android.R.drawable.ic_menu_add)
				.setView(dialogAddMaterial)
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								//保存数据
								String name = editMaterialName.getText().toString();
								String unit = editMaterialUnit.getText().toString();
								String bak = editMaterialBak.getText().toString();
								
								MaterialBean m = new MaterialBean();
								m.setMaterialName(name);
								m.setMaterialUnit(unit);
								m.setBak(bak);
								m.setIsUpload("0");
								
								
								MaterialDao materialDao = new MaterialDao(MaterialManagerActivity.this);
								m.setMaterialId(String.valueOf(materialDao.getMaxId()+1));
								materialDao.insert(m);
						
								//刷新列表
								listMaterial.add(m);
								adapter.notifyDataSetChanged();
								
								displayToast("添加成功");
								
								dialog.dismiss();
							}
						}).setNegativeButton("取消", null).show();
			}
		});
	}

	// 自定义adapter
	private class MyListAdapter extends ArrayAdapter<MaterialBean> {
		private int resource;

		public MyListAdapter(Context context, int resourceId,
				List<MaterialBean> objects) {
			super(context, resourceId, objects);
			// 记录下来稍后使用
			resource = resourceId;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			LinearLayout listView;
			// 获取数据
			MaterialBean m = getItem(position);
			String materialName = m.getMaterialName();
			String materialUint = m.getMaterialUnit();
			String materialBak = m.getBak();
			final String materialId = m.getMaterialId();

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
			TextView item_materialName = (TextView) listView
					.findViewById(R.id.materialName);
			TextView item_materialUint = (TextView) listView
					.findViewById(R.id.materialUint);
			TextView item_materialBak = (TextView) listView
					.findViewById(R.id.materialBak);
			item_materialName.setText(materialName);
			item_materialUint.setText(materialUint);
			item_materialBak.setText(materialBak);
			
			CheckBox item_materialSelect = (CheckBox)listView.findViewById(R.id.checkBoxMaterial);
			//初始化列表时判断该材料是否被选中
			Double quantity = ShareMap.getInstance().get(materialId);
			if(quantity!=-1.0){
				item_materialSelect.setChecked(true);
			}
			//对该checkBox添加选中监听
			item_materialSelect.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if(!buttonView.isPressed())return;
					//从未选中到选中
					if(isChecked){
						ShareMap.getInstance().put(materialId,0.0);
					}else{//从选中到未选中
						ShareMap.getInstance().remove(materialId);
					}
				}
				
			});

			int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色
			listView.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同

			return listView;
		}
	}
	
	//提交菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.material_select, menu);//修改
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_material_select) {
	
			this.finish();
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}

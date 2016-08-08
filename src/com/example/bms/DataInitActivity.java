package com.example.bms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.bean.SettingBean;
import com.example.common.dao.TableHelper;
import com.example.dao.SettingDao;
import com.example.dao.UsersDao;
import com.example.util.Common;



import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class DataInitActivity extends BaseActivity {

	private Button init_btn_init, init_btn_back;
	private ImageView i1,i2,i3,i4,i5, i6, i7, i8, i9, i10,i11;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_init);
		i1 = (ImageView)findViewById(R.id.imageView1);
		i2 = (ImageView)findViewById(R.id.imageView2);
		i3 = (ImageView)findViewById(R.id.imageView3);
		i4 = (ImageView)findViewById(R.id.imageView4);
		i5 = (ImageView)findViewById(R.id.imageView5);
		i6 = (ImageView) findViewById(R.id.imageView6);
		i7 = (ImageView) findViewById(R.id.imageView7);
		i8 = (ImageView) findViewById(R.id.imageView8);
		i9 = (ImageView) findViewById(R.id.imageView9);
		i10 = (ImageView) findViewById(R.id.imageView10);
		i11 = (ImageView) findViewById(R.id.imageView11);
//		copyDatabase();
		
		init_btn_init = (Button) findViewById(R.id.init_btn_init);
		init_btn_init.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//handler.post(new InitThread(InitActivity.this));
				copyDatabase();
				i1.setImageResource(R.drawable.finish);
				i2.setImageResource(R.drawable.finish);
				i3.setImageResource(R.drawable.finish);
				i4.setImageResource(R.drawable.finish);
				i5.setImageResource(R.drawable.finish);
				i6.setImageResource(R.drawable.finish);
				i7.setImageResource(R.drawable.finish);
				i8.setImageResource(R.drawable.finish);
				i9.setImageResource(R.drawable.finish);
				i10.setImageResource(R.drawable.finish);
				i11.setImageResource(R.drawable.finish);
				displayToast("初始化完成");
			}

		});
		init_btn_back = (Button) findViewById(R.id.init_btn_back);
		init_btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//DataInitActivity.this.finish();
				Intent intent = new Intent();
				intent.setClass(DataInitActivity.this, NetSettingsActivity.class);
				startActivity(intent);
			}

		});
		
		
	}
	//配置文件的拷贝
	private void copyDatabase(){
		SettingBean settingBean = Common.getSetting(this);
		openDatabase();
		SettingDao dao = new SettingDao(this);
		dao.insert(settingBean);
	}
	private final int BUFFER_SIZE = 400000;
	private void openDatabase() {
		try {
			String dbfile = "/data/data/com.example.bms/databases/bms_tablet_db.db";
			if ((new File(dbfile).exists())) { // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
				System.out.println("new file");
				InputStream is = this.getResources().openRawResource(
						R.raw.tablet_db);// 欲导入的数据库
				System.out.println("copy success");
				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			return;
		} catch (FileNotFoundException e) {
			Log.e("Database", "File not found");
			System.out.println("Exception File not found");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("Database", "IO exception");
			System.out.println("Exception IO exception");
			e.printStackTrace();
		}
		System.out.println("Database copy failed");
		return;
	}
}

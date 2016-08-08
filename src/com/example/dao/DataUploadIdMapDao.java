package com.example.dao;

import android.content.Context;
import com.example.bean.DataUploadIdMapBean;
import com.example.common.dao.TemplateDao;

public class DataUploadIdMapDao  extends TemplateDao<DataUploadIdMapBean> {

	private Context context;
	public DataUploadIdMapDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}

}

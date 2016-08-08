package com.example.dao;

import android.content.Context;
import com.example.bean.MemberBean;
import com.example.common.dao.TemplateDao;

public class MemberDao extends TemplateDao<MemberBean> {

	private Context context;

	public MemberDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}

}

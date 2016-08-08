package com.example.bms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bean.BridgeProjectBean;
import com.example.bean.CulvertBean;
import com.example.bean.MemberBean;
import com.example.dao.BridgeProjectDao;
import com.example.dao.CulvertDao;
import com.example.dao.MemberDao;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ProjectDetailActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_detail);
		
		
		
		String[] memberItems = new String[] { 
				"桥梁编码：", "桥梁名称：", "修建年月：","预算费用（万元）：","建安费：",
				"建管费：","监理费：", "设计费：", "审查费：", "竣工试验检测费：",
				"合计：","建设单位：", "设计单位：", "施工单位：","监理单位：",
				"验收类型：","修建类别：", "修建原因：", "工程范围：","经济来源：",
				"质量评定：","省局工程批复名称：", "省局批复文号：", "批复日期：","管理局文件名：", 
				"管理局文号：","工程下达人：", "下达日期：", "开工日期：","完工日期：",
				"交工日期：","竣工日期：", "招标公告日期：", "开工报告：","分项开工报汇：",
				"自检验收：","监理验收：", "质量检测：", "交竣工验收：","是否提交：",
				"工程提交人：","提交日期："
				}; 
		
		int projectId = getIntent().getIntExtra("projectId",0);
		BridgeProjectDao dao = new BridgeProjectDao(this);
		BridgeProjectBean info = dao.queryById(projectId);
		
		int iLength = memberItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", memberItems[i]);
		}
		
		maps[0].put("value",info.getBridgeCode());//桥梁编码
		maps[1].put("value",info.getBridgeName());//桥梁名称
		maps[2].put("value",info.getProjectDate());//修建年月
		maps[3].put("value",info.getBudgetCost());//
		maps[4].put("value",info.getConstructionCost());//
		maps[5].put("value",info.getManagementCost());//
		maps[6].put("value",info.getSupervisorCost());//
		maps[7].put("value",info.getDesignCost());//
		maps[8].put("value",info.getExamineCost());//
		maps[9].put("value",info.getDesignCost());//
		maps[10].put("value",info.getSumCost());//
		
		maps[11].put("value",info.getConstructType());//
		maps[12].put("value",info.getBuildUnit());//
		maps[13].put("value",info.getDesignUnit());//
		maps[14].put("value",info.getConstructionUnit());//
		maps[15].put("value",info.getSupervisionUnit());//
		maps[16].put("value",info.getCheckType());//
		maps[17].put("value",info.getConstructReason());//
		maps[18].put("value",info.getProjectRange());//
		maps[19].put("value",info.getFinanceSource());//
		maps[20].put("value",info.getQualityEvaluation());//
		
		maps[21].put("value",info.getReplayName());//
		maps[22].put("value",info.getReplayNum());//
		maps[23].put("value",info.getReplayDate());//
		maps[24].put("value",info.getAuthorityName());//
		maps[25].put("value",info.getAuthorityNum());//
		maps[26].put("value",info.getMakeProjectPeople());//
		maps[27].put("value",info.getMakeProjectDate());//
		maps[28].put("value",info.getStartDate());//
		maps[29].put("value",info.getFinishDate());//
		maps[30].put("value",info.getAcceptanceDate());//
		
		maps[31].put("value",info.getCompletionDate());//
		maps[32].put("value",info.getTenderDate());//
		maps[33].put("value",info.getProjectReport());//
		maps[34].put("value",info.getPartProjectReport());//
		maps[35].put("value",info.getOurselfAccept());//
		maps[36].put("value",info.getSupervisionAccept());//
		maps[37].put("value",info.getQualityDetection());//
		maps[38].put("value",info.getCompletionAccept());//
		maps[39].put("value",info.getIsSubmit());//
		maps[40].put("value",info.getSubmitPeople());//
		
		maps[41].put("value",info.getSubmitDate());//
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
		
		ListView listView = ((ListView)findViewById(R.id.list_project_detail));
		SimpleAdapter listAdapter = new SimpleAdapter(this, list,
				R.layout.listitem_bridgeinfo_detail, new String[] { "name",
						"value" }, new int[] { R.id.name, R.id.value });
		listView.setAdapter(listAdapter);
	}
}

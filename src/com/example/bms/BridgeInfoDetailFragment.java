package com.example.bms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bean.BridgeBean;
import com.example.bean.CulvertBean;
import com.example.bms.dummy.DummyContent;
import com.example.dao.BridgeDao;
import com.example.dao.CulvertDao;

/**
 * A fragment representing a single BridgeInfo detail screen. This fragment is
 * either contained in a {@link BridgeInfoListActivity} in two-pane mode (on
 * tablets) or a {@link BridgeInfoDetailActivity} on handsets.
 */
public class BridgeInfoDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;
	
	private String mBridgeId;
	private String mCulvertId;
	/**
	 * 数据链表
	 */
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public BridgeInfoDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
		if(getArguments().containsKey("bridgeId")){
			mBridgeId = getArguments().getString("bridgeId");
		}
		if(getArguments().containsKey("culvertId")){
			mCulvertId = getArguments().getString("culvertId");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_bridgeinfo_detail,
				container, false);
		
		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			int itemNum = Integer.parseInt(mItem.id);

			Map<String, Object>[] maps;
			switch (itemNum) {
			case 11:// 识别
				dataDistinguish();
				break;
			case 12:// 结构
				dataStructure();
				break;
			case 13:// 经济
				dataEconomy();
				break;
			case 14:// 档案
				dataRecord();
				break;
			case 15:// 责任人
				dataDuty();
				break;
			case 16:// 资料
				dataFile();
				break;
			case 17:// 其他
				dataElse();
				break;
				
			//以下是涵洞
			case 21://识别
				culvertDistinguish();
				break;
			case 22://病害
				culvertDisease();
				break;
			case 23://评价
				culvertEvaluate();
				break;
			default:
				break;
			}
			ListView listView = ((ListView) rootView
					.findViewById(R.id.bridgeinfo_detail));
			SimpleAdapter listAdapter = new SimpleAdapter(getActivity(), list,
					R.layout.listitem_bridgeinfo_detail, new String[] { "name",
							"value" }, new int[] { R.id.name, R.id.value });
			listView.setAdapter(listAdapter);
		}

		return rootView;
	}

	public void dataDistinguish() {
		String[] bridgeItems = new String[] { "桥梁代码：", "桥梁名称：", "桥梁条码：",
				"路线号：", "路线名称：", "路线类型：", "次要路线：", "顺序号：", "所在地：", "中心桩号：",
				"管养单位：", "跨越地物名称：", "跨越地物类型：", "施工桩号：", "公路技术等级：",
				"桥梁性质：", "桥梁分类：", "设计荷载等级：", "目前荷载等级：", "上部结构类型：", "桥墩类型：",
				"桥台类型：", "支座类型：", "桥墩基础类型：", "桥台基础类型：", "桥面铺装类型：", "伸缩缝类型：",
				"桥梁用途：", "桥梁状态：", "行政等级：", "所属乡镇："};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		maps[0].put("value",info.getBridgeCode());//桥梁代码
		maps[1].put("value",info.getBridgeName());//桥梁名称
		maps[2].put("value",info.getBarCode());//桥梁条码
		maps[3].put("value",info.getLineNumber());//路线号
		maps[4].put("value",info.getLineName());//路线名称
		maps[5].put("value",info.getLineType());//路线类型
		maps[6].put("value",info.getSecondaryLine());//次要路线
		maps[7].put("value",info.getSeqNumber());//顺序号
		maps[8].put("value",info.getLocationName());//所在地
		maps[9].put("value",info.getCenterStake());//中心桩号
		maps[10].put("value",info.getMmName());//管养单位　
		maps[11].put("value",info.getCrossName());//跨越地物名称
		maps[12].put("value",info.getCrossType());//跨越地物类型
		maps[13].put("value",info.getConstructionStake());//施工桩号
		maps[14].put("value",info.getRoadTechnicalGrade());//公路技术等级
		maps[15].put("value",info.getBridgeProperty());//桥梁性质
		maps[16].put("value",info.getBridgeType());//桥梁分类
		maps[17].put("value",info.getDesignLoad());//设计荷载等级
		maps[18].put("value",info.getNowLoad());//目前荷载等级
		maps[19].put("value",info.getSuperstructureForm());//上部结构类型
		maps[20].put("value",info.getBridgePierType());//桥墩类型　
		maps[21].put("value",info.getAbutmentType());//桥台类型
		maps[22].put("value",info.getSupportsType());//支座类型
		maps[23].put("value",info.getBridgePierBaseType());//桥墩基础类型
		maps[24].put("value",info.getAbutmentBaseType());//桥台基础类型
		maps[25].put("value",info.getBridgeDeckType());//桥面铺装类型
		maps[26].put("value",info.getExpansionJointsType());//伸缩缝类型
		maps[27].put("value",info.getBridgeUse());//桥梁用途
		maps[28].put("value",info.getBridgeState());//桥梁状态
		maps[29].put("value",info.getAdministrativeLevel());//行政等级
		maps[30].put("value",info.getBridgeTown());//所属乡镇
		
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}

	public void dataStructure() {
		String[] bridgeItems = new String[] { "桥梁代码：", "桥梁名称：", "跨径组合：",
				"最大跨径：", "桥梁全长：", "跨径总长：", "桥宽组合：", "桥面净宽：", "是否宽路窄桥：", "桥高：",
				"建桥年月：", "改建年月：", "通航等级：", "桥梁限高：", "桥下垂直净高：",
				"跨中截面高：", "桥面全宽：", "桥梁平曲线半径：", "桥头路面净宽：", "矢跨比：", "桥面纵坡：",
				"弯坡斜特征：", "立交特征：", "交叉形式："};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		
		maps[0].put("value",info.getBridgeCode());//桥梁代码
		maps[1].put("value",info.getBridgeName());//桥梁名称
		maps[2].put("value",info.getSpanCombination());//跨径组合
		maps[3].put("value",info.getMaxSpan());//最大跨径
		maps[4].put("value",info.getBridgeLength());//桥梁全长
		maps[5].put("value",info.getSpanLength());//跨径总长
		maps[6].put("value",info.getBridgeWidthComb());//桥宽组合
		maps[7].put("value",info.getClearWidth());//桥面净宽
		maps[8].put("value",info.getIsWroadNbridge());//是否宽路窄桥
		maps[9].put("value",info.getBridgeHight());//桥高
		maps[10].put("value",info.getBuildYear());//建桥年月　
		maps[11].put("value",info.getRebuildYear());//改建年月
		maps[12].put("value",info.getNavigationGrade());//通航等级
		maps[13].put("value",info.getBridgeHightLimit());//桥梁限高
		maps[14].put("value",info.getVerticalClearHeight());//桥下垂直净高
		maps[15].put("value",info.getMiddleSectionHigh());//跨中截面高
		maps[16].put("value",info.getBridgeWidth());//桥面全宽
		maps[17].put("value",info.getFlatCurveRadius());//桥梁平曲线半径
		maps[18].put("value",info.getBriFrontRoadWidth());//桥头路面净宽
		maps[19].put("value",info.getSpanRatio());//矢跨比
		maps[20].put("value",info.getDeckLongitudinal());//桥面纵坡
		maps[21].put("value",info.getCurvedRampFeature());//弯坡斜特征
		maps[22].put("value",info.getInterchangeFeature());//立交特征
		maps[23].put("value",info.getCrossForm());//交叉形式
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}

	public void dataEconomy() {
		String[] bridgeItems = new String[] { "桥梁代码：", "桥梁名称：", "总造价：",
				"施工日期：", "桥面中心标高：", "设计洪水频率：", "设计冲刷标高：", "主桥基底标高：", "历史最大洪水：", 
				"年日均交通量：","防护工程类型：", "墩台防撞设施类型：", "地基性质：", "抗震设防：", "桥上附设：",
				"分隔带：", "收费情况：", "设计时速：", "桥梁与环境协调度：", "环境条件："};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		maps[0].put("value",info.getBridgeCode());//桥梁代码
		maps[1].put("value",info.getBridgeName());//桥梁名称
		maps[2].put("value",info.getTotalCost());//总造价
		maps[3].put("value",info.getConstructionPeriod());//施工日期
		maps[4].put("value",info.getDeckCenterElevation());//桥面中心标高
		maps[5].put("value",info.getFloodFrequency());//设计洪水频率
		maps[6].put("value",info.getScourElevation());//设计冲刷标高
		maps[7].put("value",info.getMainBridgeBaseElevation());//主桥基底标高
		maps[8].put("value",info.getHistoricalMaximumFlood());//历史最大洪水
		maps[9].put("value",info.getAnnualAverageDailyTraffic());//年日均交通量
		maps[10].put("value",info.getProtectionProjectType());//　防护工程类型
		maps[11].put("value",info.getPierProtectionType());//墩台防撞设施类型
		maps[12].put("value",info.getGeologicalFoundation());//地基性质
		maps[13].put("value",info.getSeismic());//抗震设防
		maps[14].put("value",info.getBridgeAttached());//桥上附设
		maps[15].put("value",info.getMedian());//分隔带
		maps[16].put("value",info.getChargeStation());//收费情况
		maps[17].put("value",info.getDesignSpeed());//设计时速
		maps[18].put("value",info.getEnvironmentCoordinationDegree());//桥梁与环境协调度
		maps[19].put("value",info.getEnvironmentalConditions());//环境条件
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}

	public void dataRecord() {
		String[] bridgeItems = new String[] { "桥梁代码：", "桥梁名称：", "设计资料编号：",
				"竣工资料编号：", "养护资料编号：", "保管单位：", "交通管制措施：", "最近三年是否评定：",
				"最近改造完工日期：", "最近改造部位：","工程性质：", "产权单位名称：", "设计单位：", "设计者：",
				"施工单位：","施工负责人：", "所用标准图：", "监理单位：", "竣工验收意见："};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		maps[0].put("value",info.getBridgeCode());//桥梁代码
		maps[1].put("value",info.getBridgeName());//桥梁名称
		maps[2].put("value",info.getDesignDataNo());//设计资料编号
		maps[3].put("value",info.getCompletionDataNo());//竣工资料编号
		maps[4].put("value",info.getMaintainDataNo());//养护资料编号
		maps[5].put("value",info.getStorageUnits());//保管单位
		maps[6].put("value",info.getTrafficControlMeasures());//交通管制措施
		maps[7].put("value",info.getIsEvaluatedLast3year());//最近三年是否评定
		maps[8].put("value",info.getLastReformCompleteDate());//最近改造完工日期
		maps[9].put("value",info.getLatelyRemouldPart());//最近改造部位
		maps[10].put("value",info.getProjectProperty());//　工程性质
		maps[11].put("value",info.getOwnerUint());//产权单位名称
		maps[12].put("value",info.getDesignUnit());//设计单位
		maps[13].put("value",info.getDesigner());//设计者
		maps[14].put("value",info.getConstructionUnit());//施工单位
		maps[15].put("value",info.getConstructionManager());//施工负责人
		maps[16].put("value",info.getBlueprint());//所用标准图
		maps[17].put("value",info.getSupervisionUnit());//监理单位
		maps[18].put("value",info.getCompletedView());//竣工验收意见
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}

	public void dataDuty() {
		String[] bridgeItems = new String[] { "桥梁代码：", "桥梁名称：", "养护责任人姓名：",
				"养护责任人电话：", "桥梁工程师姓名：", "桥梁工程师电话：", "养护单位责任人：", "养护单位电话：",
				"路政监管责任人：", "路政监管电话：","市公路局包片姓名：", "市公路局包片电话：", "市公路局分管姓名：",
				"市公路局分管电话："};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		maps[0].put("value",info.getBridgeCode());//桥梁代码
		maps[1].put("value",info.getBridgeName());//桥梁名称
		maps[2].put("value",info.getMaintainName());//养护责任人姓名
		maps[3].put("value",info.getMaintainPhone());//
		maps[4].put("value",info.getProjectName());//桥梁工程师姓名
		maps[5].put("value",info.getProjectPhone());//
		maps[6].put("value",info.getUnitName());//养护单位责任人姓名
		maps[7].put("value",info.getUnitPhone());//
		maps[8].put("value",info.getRoadManagerName());//路政监管责任人姓名
		maps[9].put("value",info.getRoadManagerPhone());//
		maps[10].put("value",info.getAreaManagerName());//市公路局包片姓名
		maps[11].put("value",info.getAreaManagerPhone());//
		maps[12].put("value",info.getDutyManagerName());//市公路局分管姓名
		maps[13].put("value",info.getDutyManagerPhone());//
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}
	
	//资料
	public void dataFile() {
		String[] bridgeItems = new String[] { "桥梁代码：", "桥梁名称：", "设计图纸状况：",
				"设计图纸编号：", "设计文件状况：", "设计文件编号：",
				"施工文件状况：", "施工文件编号：",
				"竣工图纸状况：", "竣工文件编号：",
				"验收文件状况：", "验收文件编号：",
				"行政文件状况：", "行政文件编号：",
				"定期检查报告状态：", "定期检查报告编号：",
				"特殊检查报告状况：", "特殊检查报告状况：",
				"历次维修记录状况：", "历次维修记录编号：",
				"档案号：","存档案：","建档年/月："};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		maps[0].put("value",info.getBridgeCode());//桥梁代码
		maps[1].put("value",info.getBridgeName());//桥梁名称
		
		maps[2].put("value",info.getDesignDrawing());//设计图纸状况
		maps[3].put("value",info.getDesignDrawingNum());//
		maps[4].put("value",info.getDesignDocument());//桥梁工程师姓名
		maps[5].put("value",info.getDesignDocumentNum());//
		maps[6].put("value",info.getConstructionDocument());//养护单位责任人姓名
		maps[7].put("value",info.getConstructionDocumentNum());//
		maps[8].put("value",info.getCompletedDrawing());//路政监管责任人姓名
		maps[9].put("value",info.getCompletedDrawingNum());//
		maps[10].put("value",info.getAcceptanceDocument());//市公路局包片姓名
		maps[11].put("value",info.getAcceptanceDocumentNum());//
		maps[12].put("value",info.getAdministratorDocument());//市公路局分管姓名
		maps[13].put("value",info.getAdministratorDocumentNum());//
		maps[14].put("value",info.getInspectionReport());//市公路局分管姓名
		maps[15].put("value",info.getInspectionReportNum());//
		maps[16].put("value",info.getSpecialInspection());//市公路局分管姓名
		maps[17].put("value",info.getSpecialInspectionNum());//
		maps[18].put("value",info.getMaintainRecord());//市公路局分管姓名
		maps[19].put("value",info.getMaintainRecordNum());//
		maps[20].put("value",info.getFileId());//
		maps[21].put("value",info.getFileForm());//市公路局分管姓名
		maps[22].put("value",info.getFileDate());//
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}
	
	//其他
	public void dataElse() {
		String[] bridgeItems = new String[] { "桥梁代码：", "桥梁名称：",
				"资料上传地址：","建设单位：", 
				"开工日期：", "竣工日期：",
				"修建类别：", "修建原因：",
				"工程范围：", "经济来源：",
				"质量评定：", "工程费用（万元）：",
				"管理单位代码：",
				"上部结构材料编码：",
				"上部结构桥面板位：", "上部结构受力型式：",
				"桥墩类型材料编码：", "桥墩受力或截面形式：",
				"桥墩类型：",
				"桥台类型材料编码：",
				"桥台类型：","桥墩基础类型材料码：",
				"桥墩基础类型施工方式：","桥墩基础类型墩台基础：",
				"桥台基础类型材料码：",
				"桥台基础类型施工方式：","桥台基础类型墩台基础：",
				"抗震设防桥位震级：","抗震设防抗震措施：",
				"下穿通道名：","下穿通道桩号：",
				"引道总宽：","引道路面宽：",
				"引道线形：","地震动峰值加速度系数：",
				"桥台护坡：",
				"护墩体：","调治构造物：",
				"常水位：","设计水位："
		};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		maps[0].put("value",info.getBridgeCode());//桥梁代码
		maps[1].put("value",info.getBridgeName());//桥梁名称
		
		maps[2].put("value",info.getFileUploadAddr());//设计图纸状况
		maps[3].put("value",info.getBuildUnit());//
		maps[4].put("value",info.getStartDate());//桥梁工程师姓名
		maps[5].put("value",info.getCompletionDate());//
		maps[6].put("value",info.getConstructType());//养护单位责任人姓名
		maps[7].put("value",info.getConstructReason());//
		maps[8].put("value",info.getProjectRange());//路政监管责任人姓名
		maps[9].put("value",info.getFinanceSource());//
		maps[10].put("value",info.getQualityEvaluation());//市公路局包片姓名
		maps[11].put("value",info.getBudgetCost());//
		maps[12].put("value",info.getDepCode());//市公路局分管姓名
		
		maps[13].put("value",info.getSuperstructureMaterial());//
		maps[14].put("value",info.getSuperstructureBoard());//市公路局分管姓名
		maps[15].put("value",info.getSuperstructureStress());//
		maps[16].put("value",info.getBridgePierMaterial());//市公路局分管姓名
		maps[17].put("value",info.getBridgePierStress());//
		maps[18].put("value",info.getBridgePierForm());//市公路局分管姓名
		maps[19].put("value",info.getAbutmentMaterial());//
		maps[20].put("value",info.getAbutmentForm());//
		maps[21].put("value",info.getBridgePierBaseMaterial());//市公路局分管姓名
		maps[22].put("value",info.getBridgePierBaseConstruct());//
		maps[23].put("value",info.getBridgePierBaseForm());
		maps[24].put("value",info.getAbutmentBaseMaterial());//市公路局分管姓名
		maps[25].put("value",info.getAbutmentBaseConstruct());//
		maps[26].put("value",info.getAbutmentBaseForm());
		maps[27].put("value",info.getSeismicGrade());//
		maps[28].put("value",info.getSeismicMeasures());
		
		maps[29].put("value",info.getUnderCrossName());
		maps[30].put("value",info.getUnderCrossNumber());//市公路局分管姓名
		maps[31].put("value",info.getApproachWidth());//
		maps[32].put("value",info.getApproachFaceWidth());
		maps[33].put("value",info.getApproachForm());//
		maps[34].put("value",info.getEarthquakeFactor());
		
		maps[35].put("value",info.getSlopeProtection());//市公路局分管姓名
		maps[36].put("value",info.getBridgePier());//
		maps[37].put("value",info.getRegulatingStructure());
		maps[38].put("value",info.getNormalWaterLevel());//
		maps[39].put("value",info.getDesignWaterLevel());
		
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}	
	//涵洞的信息
	public void culvertDistinguish(){
		String[] culvertItems = new String[] { 
				"涵洞编号：", "涵洞名称：", "涵洞条码：","路线号：", "路线名称：",
				"管养单位：", "路线类型：", "顺序号：", "所在地：", "中心桩号：",
				"施工桩号：", "涵洞交角：", "涵洞全长：", "盖板总长：", "涵洞净高：",
				"涵顶填土厚：", "修建年月：", "涵洞类型：", "涵洞设计荷载：", "孔数及净跨：",
				"进口类型：","出口类型：", "管理形式码："};
		
		CulvertDao dao = new CulvertDao(getActivity());
		CulvertBean info = dao.get(mCulvertId);
		
		int iLength = culvertItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", culvertItems[i]);
		}
		maps[0].put("value",info.getCulvertCode());//涵洞编号
		maps[1].put("value",info.getCulvertName());//涵洞名称
		maps[2].put("value",info.getBarCode());//涵洞条码
		maps[3].put("value",info.getLineNumber());//路线号
		maps[4].put("value",info.getLineName());//路线名称
		maps[5].put("value",info.getMmName());//管养单位　
		maps[6].put("value",info.getLineType());//路线类型
		maps[7].put("value",info.getSerialNumber());//顺序号
		maps[8].put("value",info.getLocationName());//所在地
		maps[9].put("value",info.getCenterStake());//中心桩号
		maps[10].put("value",info.getMmName());//施工桩号
		maps[11].put("value",info.getCulvertAngle());//涵洞交角
		maps[12].put("value",info.getCulvertLength());//涵洞全长
		maps[13].put("value",info.getCoverLength());//盖板总长
		maps[14].put("value",info.getCulvertHeight());//涵洞净高
		maps[15].put("value",info.getFillDepth());//涵顶填土厚
		maps[16].put("value",info.getConstructDate());//修建年月
		maps[17].put("value",info.getCulvertType());//涵洞类型
		maps[18].put("value",info.getDesignLoad());//涵洞设计荷载
		maps[19].put("value",info.getHoleSpan());//孔数及净跨
		maps[20].put("value",info.getInletType());//进口类型　
		maps[21].put("value",info.getOutletType());//出口类型
		maps[22].put("value",info.getManageCode());//管理形式码
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}
	public void culvertDisease(){
		String[] culvertItems = new String[] { 
				"涵洞编号：", "涵洞名称：", "涵洞基础病害：","涵身病害：","涵底病害：", "涵顶病害：",
				"进水口病害：", "出水口病害：", "洞口铺砌病害：", "涵洞翼墙锥坡病害：", "涵顶跳车病害：",
				"涵洞排水病害：", "养护状况：", "清洁状况：", "检查日期：", "下次检查日期：",
				"备注："};
		
		CulvertDao dao = new CulvertDao(getActivity());
		CulvertBean info = dao.get(mCulvertId);
		
		int iLength = culvertItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", culvertItems[i]);
		}
		
		maps[0].put("value",info.getCulvertCode());//涵洞编号
		maps[1].put("value",info.getCulvertName());//涵洞名称
		maps[2].put("value",info.getBasisDisease());//涵洞基础病害
		maps[3].put("value",info.getBodyDisease());//涵身病害
		maps[4].put("value",info.getBottomDisease());//涵底病害
		maps[5].put("value",info.getTopDisease());//涵顶病害
		maps[6].put("value",info.getInletDisease());//进水口病害
		maps[7].put("value",info.getOutletDisease());//出水口病害
		maps[8].put("value",info.getPitchingDisease());//洞口铺砌病害
		maps[9].put("value",info.getWingSlopeDisease());//涵洞翼墙锥坡病害
		maps[10].put("value",info.getJumpDisease());//涵顶跳车病害
		maps[11].put("value",info.getDrainageDisease());//涵洞排水病害
		maps[12].put("value",info.getMaintenanceCondition());//养护状况
		maps[13].put("value",info.getCleanCondition());//清洁状况
		maps[14].put("value",info.getCheckDate());//检查日期
		maps[15].put("value",info.getNextCheckDate());//下次检查日期
		maps[16].put("value",info.getRemark());//备注
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}
	public void culvertEvaluate(){
		String[] culvertItems = new String[] { 
				"涵洞编号：", "涵洞名称：", "适应性综合评分：","综合评级："};
		
		CulvertDao dao = new CulvertDao(getActivity());
		CulvertBean info = dao.get(mCulvertId);
		
		int iLength = culvertItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", culvertItems[i]);
		}
		
		maps[0].put("value",info.getCulvertCode());//涵洞编号
		maps[1].put("value",info.getCulvertName());//涵洞名称
		maps[2].put("value",info.getAdaptiveScore());//适应性综合评分
		maps[3].put("value",info.getCompositeRating());//综合评级
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}
}

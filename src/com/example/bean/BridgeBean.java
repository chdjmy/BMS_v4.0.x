package com.example.bean;
import java.sql.Timestamp;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;


/**
 * 桥梁基本信息
 * @author JMY
 *
 */
@Table(name="bridge")
public class BridgeBean {
	@Id
	@Column(name="bridge_id", lenght = 0, type = "BIGINT" )
	private String bridgeId;
	
	@Column(name="bar_code", lenght = 50, type = "VARCHAR" )
	private String barCode;
	
	@Column(name="tech_station", lenght = 1, type = "CHAR" )
	private Double techStation;
	
	@Column(name="bridge_code", lenght = 15, type = "CHAR" )
	private String bridgeCode;
	
	@Column(name="bridge_name", lenght = 255, type = "NVARCHAR" )
	private String bridgeName;
	
	@Column(name="line_number", lenght = 255, type = "NVARCHAR" )
	private String lineNumber;
	
	@Column(name="line_name", lenght = 255, type = "NVARCHAR" )
	private String lineName;
	
	@Column(name="line_type", lenght = 255, type = "NVARCHAR" )
	private String lineType;
	
	@Column(name="secondary_line", lenght = 255, type = "NVARCHAR" )
	private String secondaryLine;
	
	@Column(name="seq_number", lenght = 255, type = "NVARCHAR" )
	private String seqNumber;
	
	@Column(name="location_name", lenght = 255, type = "NVARCHAR" )
	private String locationName;
	
	@Column(name="bridge_type", lenght = 255, type = "NVARCHAR" )
	private String bridgeType;
	
	@Column(name="center_stake", lenght = 0, type = "FLOAT" )
	private Double centerStake;
	
	@Column(name="MM_name", lenght = 255, type = "NVARCHAR" )
	private String mmName;
	
	@Column(name="cross_name", lenght = 255, type = "NVARCHAR" )
	private String crossName;
	
	@Column(name="cross_type", lenght = 255, type = "NVARCHAR" )
	private String crossType;
	
	@Column(name="construction_stake", lenght = 0, type = "FLOAT" )
	private Double constructionStake;
	
	@Column(name="road_technical_grade", lenght = 255, type = "NVARCHAR" )
	private String roadTechnicalGrade;
	
	@Column(name="bridge_property", lenght = 255, type = "NVARCHAR" )
	private String bridgeProperty;
	
	@Column(name="span_classify", lenght = 255, type = "NVARCHAR" )
	private String spanClassify;
	
	@Column(name="design_load", lenght = 255, type = "NVARCHAR" )
	private String designLoad;
	
	@Column(name="now_load", lenght = 255, type = "NVARCHAR" )
	private String nowLoad;
	
	@Column(name="superstructure_form", lenght = 255, type = "NVARCHAR" )
	private String superstructureForm;
	
	@Column(name="bridge_pier_type", lenght = 255, type = "NVARCHAR" )
	private String bridgePierType;
	
	@Column(name="abutment_type", lenght = 255, type = "NVARCHAR" )
	private String abutmentType;
	
	@Column(name="supports_type", lenght = 255, type = "NVARCHAR" )
	private String supportsType;
	
	@Column(name="bridge_pier_base_type", lenght = 255, type = "NVARCHAR" )
	private String bridgePierBaseType;
	
	@Column(name="abutment_base_type", lenght = 255, type = "NVARCHAR" )
	private String abutmentBaseType;
	
	@Column(name="bridge_deck_type", lenght = 255, type = "NVARCHAR" )
	private String bridgeDeckType;
	
	@Column(name="expansion_joints_type", lenght = 255, type = "NVARCHAR" )
	private String expansionJointsType;
	
	@Column(name="bridge_use", lenght = 255, type = "NVARCHAR" )
	private String bridgeUse;
	
	@Column(name="bridge_state", lenght = 255, type = "NVARCHAR" )
	private String bridgeState;
	
	@Column(name="administrative_level", lenght = 255, type = "NVARCHAR" )
	private String administrativeLevel;
	
	@Column(name="bridge_town", lenght = 255, type = "NVARCHAR" )
	private String bridgeTown;
	
	@Column(name="manage_code", lenght = 255, type = "NVARCHAR" )
	private String manageCode;
	
	@Column(name="bridge_length", lenght = 0, type = "FLOAT" )
	private Double bridgeLength;
	
	@Column(name="span_combination", lenght = 255, type = "NVARCHAR" )
	private String spanCombination;
	
	@Column(name="max_span", lenght = 0, type = "FLOAT" )
	private Double maxSpan;
	
	@Column(name="span_length", lenght = 0, type = "FLOAT" )
	private Double spanLength;
	
	@Column(name="bridge_width_comb", lenght = 255, type = "NVARCHAR" )
	private String bridgeWidthComb;
	
	@Column(name="isWroadNbridge", lenght = 255, type = "NVARCHAR" )
	private String isWroadNbridge;
	
	@Column(name="clear_width", lenght = 0, type = "FLOAT" )
	private Double clearWidth;
	
	@Column(name="bridge_hight", lenght = 0, type = "FLOAT" )
	private Double bridgeHight;
	
	@Column(name="build_year", lenght = 0, type = "DATE" )
	private String buildYear;
	
	@Column(name="rebuild_year", lenght = 0, type = "DATE" )
	private String rebuildYear;
	
	@Column(name="navigation_grade", lenght = 255, type = "NVARCHAR" )
	private String navigationGrade;
	
	@Column(name="bridge_hight_limit", lenght = 0, type = "FLOAT" )
	private Double bridgeHightLimit;
	
	@Column(name="vertical_clear_height", lenght = 0, type = "FLOAT")
	private Double verticalClearHeight;
	
	@Column(name="middle_section_high", lenght = 255, type = "NVARCHAR" )
	private String middleSectionHigh;
	
	@Column(name="bridge_width", lenght = 0, type = "FLOAT")
	private Double bridgeWidth;
	
	@Column(name="flat_curve_radius", lenght = 0, type = "FLOAT")
	private Double flatCurveRadius;
	
	@Column(name="bri_front_road_width", lenght = 0, type = "FLOAT")
	private Double briFrontRoadWidth;
	
	@Column(name="span_ratio", lenght = 255, type = "NVARCHAR")
	private String spanRatio;
	
	@Column(name="deck_longitudinal", lenght = 255, type = "NVARCHAR" )
	private String deckLongitudinal;
	
	@Column(name="curved_ramp_feature", lenght = 255, type = "NVARCHAR" )
	private String curvedRampFeature;
	
	@Column(name="interchange_feature", lenght = 255, type = "NVARCHAR" )
	private String interchangeFeature;
	
	@Column(name="cross_form", lenght = 255, type = "NVARCHAR" )
	private String crossForm;
	
	@Column(name="total_cost", lenght = 0, type = "FLOAT")
	private Double totalCost;
	
	@Column(name="construction_period", lenght = 0, type = "FLOAT")
	private Double constructionPeriod;
	
	@Column(name="deck_center_elevation", lenght = 0, type = "FLOAT")
	private Double deckCenterElevation;
	
	@Column(name="flood_frequency", lenght = 255, type = "NVARCHAR")
	private String floodFrequency;
	
	@Column(name="scour_elevation", lenght = 0, type = "FLOAT")
	private Double scourElevation;
	
	@Column(name="main_bridge_base_elevation", lenght = 0, type = "FLOAT")
	private Double mainBridgeBaseElevation;
	
	@Column(name="historical_maximum_flood", lenght = 0, type = "FLOAT")
	private Double historicalMaximumFlood;
	
	@Column(name="annual_average_daily_traffic", lenght = 255, type = "NVARCHAR")
	private String annualAverageDailyTraffic;
	
	@Column(name="protection_project_type", lenght = 255, type = "NVARCHAR")
	private String protectionProjectType;
	
	@Column(name="pier_protection_type", lenght = 255, type = "NVARCHAR")
	private String pierProtectionType;
	
	@Column(name="geological_foundation", lenght = 255, type = "NVARCHAR")
	private String geologicalFoundation;
	
	@Column(name="seismic", lenght = 255, type = "NVARCHAR")
	private String seismic;
	
	@Column(name="bridge_attached", lenght = 255, type = "NVARCHAR")
	private String bridgeAttached;
	
	@Column(name="median", lenght = 255, type = "NVARCHAR")
	private String median;
	
	@Column(name="charge_station", lenght = 255, type = "NVARCHAR")
	private String chargeStation;
	
	@Column(name="design_speed", lenght = 0, type = "FLOAT")
	private Double designSpeed;
	
	@Column(name="environment_coordination_degree", lenght = 0, type = "FLOAT")
	private Double environmentCoordinationDegree;
	
	@Column(name="environmental_conditions", lenght = 255, type = "NVARCHAR")
	private String environmentalConditions;
	
	@Column(name="design_data_No", lenght = 50, type = "VARCHAR")
	private String designDataNo;
	
	@Column(name="completion_data_No", lenght = 50, type = "VARCHAR")
	private String completionDataNo;
	
	@Column(name="maintain_data_No", lenght = 50, type = "VARCHAR")
	private String maintainDataNo;
	
	@Column(name="storage_units", lenght = 255, type = "NVARCHAR")
	private String storageUnits;
	
	@Column(name="traffic_control_measures", lenght = 255, type = "NVARCHAR")
	private String trafficControlMeasures;
	
	@Column(name="is_evaluated_last3year", lenght = 255, type = "NVARCHAR")
	private String isEvaluatedLast3year;
	
	@Column(name="last_reform_complete_date", lenght = 0, type = "DATETIME")
	private Timestamp lastReformCompleteDate;
	
	@Column(name="lately_remould_part", lenght = 255, type = "NVARCHAR")
	private String latelyRemouldPart;
	
	@Column(name="project_property", lenght = 255, type = "NVARCHAR")
	private String projectProperty;
	
	@Column(name="owner_uint", lenght = 255, type = "NVARCHAR")
	private String ownerUint;
	
	@Column(name="design_unit", lenght = 255, type = "NVARCHAR")
	private String designUnit;
	
	@Column(name="designer", lenght = 255, type = "NVARCHAR")
	private String designer;
	
	@Column(name="construction_unit", lenght = 255, type = "NVARCHAR")
	private String constructionUnit;
	
	@Column(name="construction_manager", lenght = 255, type = "NVARCHAR")
	private String constructionManager;
	
	@Column(name="blueprint", lenght = 255, type = "NVARCHAR")
	private String blueprint;
	
	@Column(name="supervision_unit", lenght = 255, type = "NVARCHAR")
	private String supervisionUnit;
	
	@Column(name="completed_view", lenght = 255, type = "NVARCHAR")
	private String completedView;
	
	@Column(name="maintain_name", lenght = 255, type = "NVARCHAR")
	private String maintainName;
	
	@Column(name="maintain_phone", lenght = 12, type = "CHAR")
	private String maintainPhone;
	
	@Column(name="project_name", lenght = 255, type = "NVARCHAR")
	private String projectName;
	
	@Column(name="project_phone", lenght = 12, type = "CHAR")
	private String projectPhone;
	
	@Column(name="unit_name", lenght = 255, type = "NVARCHAR")
	private String unitName;
	
	@Column(name="unit_phone", lenght = 12, type = "CHAR")
	private String unitPhone;
	
	@Column(name="road_manager_name", lenght = 255, type = "NVARCHAR")
	private String roadManagerName;
	
	@Column(name="road_manager_phone", lenght = 12, type = "CHAR")
	private String roadManagerPhone;
	
	@Column(name="area_manager_name", lenght = 255, type = "NVARCHAR")
	private String areaManagerName;
	
	@Column(name="area_manager_phone", lenght = 12, type = "CHAR")
	private String areaManagerPhone;
	
	@Column(name="duty_manager_name", lenght = 255, type = "NVARCHAR")
	private String dutyManagerName;
	
	@Column(name="duty_manager_phone", lenght = 12, type = "CHAR")
	private String dutyManagerPhone;
	
	@Column(name="front_photo_addr", lenght = 255, type = "NVARCHAR")
	private String frontPhotoAddr;
	
	@Column(name="side_photo_addr", lenght = 255, type = "NVARCHAR")
	private String sidePhotoAddr;
	
	@Column(name="latitude", lenght = 0, type = "FLOAT")
	private Double latitude;
	
	@Column(name="longitude", lenght = 0, type = "FLOAT")
	private Double longitude;
	
	
	//新增
	@Column(name="fileUploadAddr", lenght = 50, type = "VARCHAR")
	private String fileUploadAddr;
	@Column(name="build_unit", lenght = 50, type = "VARCHAR")
	private String buildUnit;
	@Column(name="start_date", lenght = 0, type = "DATE")
	private String startDate;
	@Column(name="completion_date", lenght = 0, type = "DATE")
	private String completionDate;
	@Column(name="construct_type", lenght = 50, type = "VARCHAR")
	private String constructType;
	@Column(name="construct_reason", lenght = 50, type = "VARCHAR")
	private String constructReason;
	@Column(name="project_range", lenght = 50, type = "VARCHAR")
	private String projectRange;
	@Column(name="finance_source", lenght = 50, type = "VARCHAR")
	private String financeSource;
	@Column(name="quality_evaluation", lenght = 50, type = "VARCHAR")
	private String qualityEvaluation;
	@Column(name="budget_cost", lenght = 0, type = "FLOAT")
	private Double budgetCost;
	@Column(name="dep_code", lenght = 255, type = "NVARCHAR")
	private String depCode;
	@Column(name="superstructure_material", lenght = 50, type = "VARCHAR")
	private String superstructureMaterial;
	@Column(name="superstructure_board", lenght = 50, type = "VARCHAR")
	private String superstructureBoard;
	@Column(name="superstructure_stress", lenght = 50, type = "VARCHAR")
	private String superstructureStress;
	
	@Column(name="bridge_pier_material", lenght = 50, type = "VARCHAR")
	private String bridgePierMaterial;
	@Column(name="bridge_pier_stress", lenght = 50, type = "VARCHAR")
	private String bridgePierStress;
	@Column(name="bridge_pier_form", lenght = 50, type = "VARCHAR")
	private String bridgePierForm;
	
	@Column(name="abutment_material", lenght = 50, type = "VARCHAR")
	private String abutmentMaterial;
	@Column(name="abutment_form", lenght = 50, type = "VARCHAR")
	private String abutmentForm;
	
	@Column(name="bridge_pier_base_material", lenght = 255, type = "VARCHAR")
	private String bridgePierBaseMaterial;
	@Column(name="bridge_pier_base_construct", lenght = 255, type = "VARCHAR")
	private String bridgePierBaseConstruct;
	@Column(name="bridge_pier_base_form", lenght = 255, type = "VARCHAR")
	private String bridgePierBaseForm;
	
	@Column(name="abutment_base_material", lenght = 50, type = "VARCHAR")
	private String abutmentBaseMaterial;
	@Column(name="abutment_base_construct", lenght = 50, type = "VARCHAR")
	private String abutmentBaseConstruct;
	@Column(name="abutment_base_form", lenght = 50, type = "VARCHAR")
	private String abutmentBaseForm;
	
	@Column(name="seismic_grade", lenght = 50, type = "VARCHAR")
	private String seismicGrade;
	@Column(name="seismic_measures", lenght = 50, type = "VARCHAR")
	private String seismicMeasures;
	
	
	@Column(name="under_cross_name", lenght = 255, type = "NVARCHAR")
    private String underCrossName;
	@Column(name="under_cross_number", lenght = 255, type = "NVARCHAR")
	private String underCrossNumber;
	
	@Column(name="approach_width", lenght = 255, type = "NVARCHAR")
	private String approachWidth;
	@Column(name="approach_face_width", lenght = 255, type = "NVARCHAR")
	private String approachFaceWidth;
	@Column(name="approach_form", lenght = 255, type = "NVARCHAR")
	private String approachForm;
	@Column(name="earthquake_factor", lenght = 255, type = "NVARCHAR")
	private String earthquakeFactor;
	@Column(name="slope_protection", lenght = 255, type = "NVARCHAR")
	private String slopeProtection;
	@Column(name="bridge_pier", lenght = 255, type = "NVARCHAR")
	private String bridgePier;
	@Column(name="regulating_structure", lenght = 255, type = "NVARCHAR")
	private String regulatingStructure;
	
	@Column(name="normal_water_level", lenght = 0, type = "FLOAT")
	private Double normalWaterLevel;
	@Column(name="design_water_level", lenght = 0, type = "FLOAT")
	private Double designWaterLevel;
	@Column(name="design_drawing", lenght = 255, type = "NVARCHAR")
	private String designDrawing;
	@Column(name="design_drawing_num", lenght = 255, type = "NVARCHAR")
	private String designDrawingNum;
	@Column(name="design_document", lenght = 255, type = "NVARCHAR")
	private String designDocument;
	@Column(name="design_document_num", lenght = 255, type = "NVARCHAR")
	private String designDocumentNum;
	@Column(name="construction_document", lenght = 255, type = "NVARCHAR")
	private String constructionDocument;
	@Column(name="construction_document_num", lenght = 255, type = "NVARCHAR")
	private String constructionDocumentNum;
	@Column(name="completed_drawing", lenght = 255, type = "NVARCHAR")
	private String completedDrawing;
	
	@Column(name="completed_drawing_num", lenght = 255, type = "NVARCHAR")
	private String completedDrawingNum;
	@Column(name="acceptance_document", lenght = 255, type = "NVARCHAR")
	private String acceptanceDocument;
	@Column(name="acceptance_document_num", lenght = 255, type = "NVARCHAR")
	private String acceptanceDocumentNum;
	@Column(name="administrator_document", lenght = 255, type = "NVARCHAR")
	private String administratorDocument;
	@Column(name="administrator_document_num", lenght = 255, type = "NVARCHAR")
	private String administratorDocumentNum;
	@Column(name="inspection_report", lenght = 255, type = "NVARCHAR")
	private String inspectionReport;
	
	@Column(name="inspection_report_num", lenght = 255, type = "NVARCHAR")
	private String inspectionReportNum;
	@Column(name="special_inspection", lenght = 255, type = "NVARCHAR")
	private String specialInspection;
	@Column(name="special_inspection_num", lenght = 255, type = "NVARCHAR")
	private String specialInspectionNum;
	@Column(name="maintain_record", lenght = 255, type = "NVARCHAR")
	private String maintainRecord;
	@Column(name="maintain_record_num", lenght = 255, type = "NVARCHAR")
	private String maintainRecordNum;
	@Column(name="file_id", lenght = 255, type = "NVARCHAR")
	private String fileId;
	@Column(name="file_form", lenght = 255, type = "NVARCHAR")
	private String fileForm;
	@Column(name="file_date", lenght = 0, type = "DATE")
	private String fileDate;
	
	@Column(name="curved", lenght = 255, type = "NVARCHAR")
	private String curved;

	//数据库里有model没有的
	@Column(name="curved_ ramp_degree", lenght = 255, type = "NVARCHAR")
	private String curvedRampDegree;
	
	


	public BridgeBean() {
	}



	public String getBridgeId() {
		return bridgeId;
	}



	public void setBridgeId(String bridgeId) {
		this.bridgeId = bridgeId;
	}



	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Double getTechStation() {
		return this.techStation;
	}

	public void setTechStation(Double techStation) {
		this.techStation = techStation;
	}

	public String getBridgeCode() {
		return this.bridgeCode;
	}

	public void setBridgeCode(String bridgeCode) {
		this.bridgeCode = bridgeCode;
	}

	public String getBridgeName() {
		return this.bridgeName;
	}

	public void setBridgeName(String bridgeName) {
		this.bridgeName = bridgeName;
	}

	public String getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getLineName() {
		return this.lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getLineType() {
		return this.lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getSecondaryLine() {
		return this.secondaryLine;
	}

	public void setSecondaryLine(String secondaryLine) {
		this.secondaryLine = secondaryLine;
	}

	public String getSeqNumber() {
		return this.seqNumber;
	}

	public void setSeqNumber(String seqNumber) {
		this.seqNumber = seqNumber;
	}

	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getBridgeType() {
		return this.bridgeType;
	}

	public void setBridgeType(String bridgeType) {
		this.bridgeType = bridgeType;
	}

	public Double getCenterStake() {
		return this.centerStake;
	}

	public void setCenterStake(Double centerStake) {
		this.centerStake = centerStake;
	}

	public String getMmName() {
		return this.mmName;
	}

	public void setMmName(String mmName) {
		this.mmName = mmName;
	}

	public String getCrossName() {
		return this.crossName;
	}

	public void setCrossName(String crossName) {
		this.crossName = crossName;
	}

	public String getCrossType() {
		return this.crossType;
	}

	public void setCrossType(String crossType) {
		this.crossType = crossType;
	}

	public Double getConstructionStake() {
		return this.constructionStake;
	}

	public void setConstructionStake(Double constructionStake) {
		this.constructionStake = constructionStake;
	}

	public String getRoadTechnicalGrade() {
		return this.roadTechnicalGrade;
	}

	public void setRoadTechnicalGrade(String roadTechnicalGrade) {
		this.roadTechnicalGrade = roadTechnicalGrade;
	}

	public String getBridgeProperty() {
		return this.bridgeProperty;
	}

	public void setBridgeProperty(String bridgeProperty) {
		this.bridgeProperty = bridgeProperty;
	}

	public String getSpanClassify() {
		return this.spanClassify;
	}

	public void setSpanClassify(String spanClassify) {
		this.spanClassify = spanClassify;
	}

	public String getDesignLoad() {
		return this.designLoad;
	}

	public void setDesignLoad(String designLoad) {
		this.designLoad = designLoad;
	}

	public String getNowLoad() {
		return this.nowLoad;
	}

	public void setNowLoad(String nowLoad) {
		this.nowLoad = nowLoad;
	}

	public String getSuperstructureForm() {
		return this.superstructureForm;
	}

	public void setSuperstructureForm(String superstructureForm) {
		this.superstructureForm = superstructureForm;
	}

	public String getBridgePierType() {
		return this.bridgePierType;
	}

	public void setBridgePierType(String bridgePierType) {
		this.bridgePierType = bridgePierType;
	}

	public String getAbutmentType() {
		return this.abutmentType;
	}

	public void setAbutmentType(String abutmentType) {
		this.abutmentType = abutmentType;
	}

	public String getSupportsType() {
		return this.supportsType;
	}

	public void setSupportsType(String supportsType) {
		this.supportsType = supportsType;
	}

	public String getBridgePierBaseType() {
		return this.bridgePierBaseType;
	}

	public void setBridgePierBaseType(String bridgePierBaseType) {
		this.bridgePierBaseType = bridgePierBaseType;
	}

	public String getAbutmentBaseType() {
		return this.abutmentBaseType;
	}

	public void setAbutmentBaseType(String abutmentBaseType) {
		this.abutmentBaseType = abutmentBaseType;
	}

	public String getBridgeDeckType() {
		return this.bridgeDeckType;
	}

	public void setBridgeDeckType(String bridgeDeckType) {
		this.bridgeDeckType = bridgeDeckType;
	}

	public String getExpansionJointsType() {
		return this.expansionJointsType;
	}

	public void setExpansionJointsType(String expansionJointsType) {
		this.expansionJointsType = expansionJointsType;
	}

	public String getBridgeUse() {
		return this.bridgeUse;
	}

	public void setBridgeUse(String bridgeUse) {
		this.bridgeUse = bridgeUse;
	}

	public String getBridgeState() {
		return this.bridgeState;
	}

	public void setBridgeState(String bridgeState) {
		this.bridgeState = bridgeState;
	}

	public String getAdministrativeLevel() {
		return this.administrativeLevel;
	}

	public void setAdministrativeLevel(String administrativeLevel) {
		this.administrativeLevel = administrativeLevel;
	}

	public String getBridgeTown() {
		return this.bridgeTown;
	}

	public void setBridgeTown(String bridgeTown) {
		this.bridgeTown = bridgeTown;
	}

	public String getManageCode() {
		return this.manageCode;
	}

	public void setManageCode(String manageCode) {
		this.manageCode = manageCode;
	}

	public Double getBridgeLength() {
		return this.bridgeLength;
	}

	public void setBridgeLength(Double bridgeLength) {
		this.bridgeLength = bridgeLength;
	}

	public String getSpanCombination() {
		return this.spanCombination;
	}

	public void setSpanCombination(String spanCombination) {
		this.spanCombination = spanCombination;
	}

	public Double getMaxSpan() {
		return this.maxSpan;
	}

	public void setMaxSpan(Double maxSpan) {
		this.maxSpan = maxSpan;
	}

	public Double getSpanLength() {
		return this.spanLength;
	}

	public void setSpanLength(Double spanLength) {
		this.spanLength = spanLength;
	}

	public String getBridgeWidthComb() {
		return this.bridgeWidthComb;
	}

	public void setBridgeWidthComb(String bridgeWidthComb) {
		this.bridgeWidthComb = bridgeWidthComb;
	}

	public String getIsWroadNbridge() {
		return this.isWroadNbridge;
	}

	public void setIsWroadNbridge(String isWroadNbridge) {
		this.isWroadNbridge = isWroadNbridge;
	}

	public Double getClearWidth() {
		return this.clearWidth;
	}

	public void setClearWidth(Double clearWidth) {
		this.clearWidth = clearWidth;
	}

	public Double getBridgeHight() {
		return this.bridgeHight;
	}

	public void setBridgeHight(Double bridgeHight) {
		this.bridgeHight = bridgeHight;
	}

	public String getBuildYear() {
		return this.buildYear;
	}

	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}

	public String getRebuildYear() {
		return this.rebuildYear;
	}

	public void setRebuildYear(String rebuildYear) {
		this.rebuildYear = rebuildYear;
	}

	public String getNavigationGrade() {
		return this.navigationGrade;
	}

	public void setNavigationGrade(String navigationGrade) {
		this.navigationGrade = navigationGrade;
	}

	public Double getBridgeHightLimit() {
		return this.bridgeHightLimit;
	}

	public void setBridgeHightLimit(Double bridgeHightLimit) {
		this.bridgeHightLimit = bridgeHightLimit;
	}

	public Double getVerticalClearHeight() {
		return this.verticalClearHeight;
	}

	public void setVerticalClearHeight(Double verticalClearHeight) {
		this.verticalClearHeight = verticalClearHeight;
	}

	public String getMiddleSectionHigh() {
		return this.middleSectionHigh;
	}

	public void setMiddleSectionHigh(String middleSectionHigh) {
		this.middleSectionHigh = middleSectionHigh;
	}

	public Double getBridgeWidth() {
		return this.bridgeWidth;
	}

	public void setBridgeWidth(Double bridgeWidth) {
		this.bridgeWidth = bridgeWidth;
	}

	public Double getFlatCurveRadius() {
		return this.flatCurveRadius;
	}

	public void setFlatCurveRadius(Double flatCurveRadius) {
		this.flatCurveRadius = flatCurveRadius;
	}

	public Double getBriFrontRoadWidth() {
		return this.briFrontRoadWidth;
	}

	public void setBriFrontRoadWidth(Double briFrontRoadWidth) {
		this.briFrontRoadWidth = briFrontRoadWidth;
	}

	public String getSpanRatio() {
		return this.spanRatio;
	}

	public void setSpanRatio(String spanRatio) {
		this.spanRatio = spanRatio;
	}

	public String getDeckLongitudinal() {
		return this.deckLongitudinal;
	}

	public void setDeckLongitudinal(String deckLongitudinal) {
		this.deckLongitudinal = deckLongitudinal;
	}

	public String getCurvedRampFeature() {
		return this.curvedRampFeature;
	}

	public void setCurvedRampFeature(String curvedRampFeature) {
		this.curvedRampFeature = curvedRampFeature;
	}

	public String getInterchangeFeature() {
		return this.interchangeFeature;
	}

	public void setInterchangeFeature(String interchangeFeature) {
		this.interchangeFeature = interchangeFeature;
	}

	public String getCrossForm() {
		return this.crossForm;
	}

	public void setCrossForm(String crossForm) {
		this.crossForm = crossForm;
	}

	public Double getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Double getConstructionPeriod() {
		return this.constructionPeriod;
	}

	public void setConstructionPeriod(Double constructionPeriod) {
		this.constructionPeriod = constructionPeriod;
	}

	public Double getDeckCenterElevation() {
		return this.deckCenterElevation;
	}

	public void setDeckCenterElevation(Double deckCenterElevation) {
		this.deckCenterElevation = deckCenterElevation;
	}

	public String getFloodFrequency() {
		return this.floodFrequency;
	}

	public void setFloodFrequency(String floodFrequency) {
		this.floodFrequency = floodFrequency;
	}

	public Double getScourElevation() {
		return this.scourElevation;
	}

	public void setScourElevation(Double scourElevation) {
		this.scourElevation = scourElevation;
	}

	public Double getMainBridgeBaseElevation() {
		return this.mainBridgeBaseElevation;
	}

	public void setMainBridgeBaseElevation(Double mainBridgeBaseElevation) {
		this.mainBridgeBaseElevation = mainBridgeBaseElevation;
	}

	public Double getHistoricalMaximumFlood() {
		return this.historicalMaximumFlood;
	}

	public void setHistoricalMaximumFlood(Double historicalMaximumFlood) {
		this.historicalMaximumFlood = historicalMaximumFlood;
	}

	public String getAnnualAverageDailyTraffic() {
		return this.annualAverageDailyTraffic;
	}

	public void setAnnualAverageDailyTraffic(String annualAverageDailyTraffic) {
		this.annualAverageDailyTraffic = annualAverageDailyTraffic;
	}

	public String getProtectionProjectType() {
		return this.protectionProjectType;
	}

	public void setProtectionProjectType(String protectionProjectType) {
		this.protectionProjectType = protectionProjectType;
	}

	public String getPierProtectionType() {
		return this.pierProtectionType;
	}

	public void setPierProtectionType(String pierProtectionType) {
		this.pierProtectionType = pierProtectionType;
	}

	public String getGeologicalFoundation() {
		return this.geologicalFoundation;
	}

	public void setGeologicalFoundation(String geologicalFoundation) {
		this.geologicalFoundation = geologicalFoundation;
	}

	public String getSeismic() {
		return this.seismic;
	}

	public void setSeismic(String seismic) {
		this.seismic = seismic;
	}

	public String getBridgeAttached() {
		return this.bridgeAttached;
	}

	public void setBridgeAttached(String bridgeAttached) {
		this.bridgeAttached = bridgeAttached;
	}

	public String getMedian() {
		return this.median;
	}

	public void setMedian(String median) {
		this.median = median;
	}

	public String getChargeStation() {
		return this.chargeStation;
	}

	public void setChargeStation(String chargeStation) {
		this.chargeStation = chargeStation;
	}

	public Double getDesignSpeed() {
		return this.designSpeed;
	}

	public void setDesignSpeed(Double designSpeed) {
		this.designSpeed = designSpeed;
	}

	public Double getEnvironmentCoordinationDegree() {
		return this.environmentCoordinationDegree;
	}

	public void setEnvironmentCoordinationDegree(
			Double environmentCoordinationDegree) {
		this.environmentCoordinationDegree = environmentCoordinationDegree;
	}

	public String getEnvironmentalConditions() {
		return this.environmentalConditions;
	}

	public void setEnvironmentalConditions(String environmentalConditions) {
		this.environmentalConditions = environmentalConditions;
	}

	public String getDesignDataNo() {
		return this.designDataNo;
	}

	public void setDesignDataNo(String designDataNo) {
		this.designDataNo = designDataNo;
	}

	public String getCompletionDataNo() {
		return this.completionDataNo;
	}

	public void setCompletionDataNo(String completionDataNo) {
		this.completionDataNo = completionDataNo;
	}

	public String getMaintainDataNo() {
		return this.maintainDataNo;
	}

	public void setMaintainDataNo(String maintainDataNo) {
		this.maintainDataNo = maintainDataNo;
	}

	public String getStorageUnits() {
		return this.storageUnits;
	}

	public void setStorageUnits(String storageUnits) {
		this.storageUnits = storageUnits;
	}

	public String getTrafficControlMeasures() {
		return this.trafficControlMeasures;
	}

	public void setTrafficControlMeasures(String trafficControlMeasures) {
		this.trafficControlMeasures = trafficControlMeasures;
	}

	public String getIsEvaluatedLast3year() {
		return this.isEvaluatedLast3year;
	}

	public void setIsEvaluatedLast3year(String isEvaluatedLast3year) {
		this.isEvaluatedLast3year = isEvaluatedLast3year;
	}

	public Timestamp getLastReformCompleteDate() {
		return this.lastReformCompleteDate;
	}

	public void setLastReformCompleteDate(Timestamp lastReformCompleteDate) {
		this.lastReformCompleteDate = lastReformCompleteDate;
	}

	public String getLatelyRemouldPart() {
		return this.latelyRemouldPart;
	}

	public void setLatelyRemouldPart(String latelyRemouldPart) {
		this.latelyRemouldPart = latelyRemouldPart;
	}

	public String getProjectProperty() {
		return this.projectProperty;
	}

	public void setProjectProperty(String projectProperty) {
		this.projectProperty = projectProperty;
	}

	public String getOwnerUint() {
		return this.ownerUint;
	}

	public void setOwnerUint(String ownerUint) {
		this.ownerUint = ownerUint;
	}

	public String getDesignUnit() {
		return this.designUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}

	public String getDesigner() {
		return this.designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public String getConstructionUnit() {
		return this.constructionUnit;
	}

	public void setConstructionUnit(String constructionUnit) {
		this.constructionUnit = constructionUnit;
	}

	public String getConstructionManager() {
		return this.constructionManager;
	}

	public void setConstructionManager(String constructionManager) {
		this.constructionManager = constructionManager;
	}

	public String getBlueprint() {
		return this.blueprint;
	}

	public void setBlueprint(String blueprint) {
		this.blueprint = blueprint;
	}

	public String getSupervisionUnit() {
		return this.supervisionUnit;
	}

	public void setSupervisionUnit(String supervisionUnit) {
		this.supervisionUnit = supervisionUnit;
	}

	public String getCompletedView() {
		return this.completedView;
	}

	public void setCompletedView(String completedView) {
		this.completedView = completedView;
	}

	public String getMaintainName() {
		return this.maintainName;
	}

	public void setMaintainName(String maintainName) {
		this.maintainName = maintainName;
	}

	public String getMaintainPhone() {
		return this.maintainPhone;
	}

	public void setMaintainPhone(String maintainPhone) {
		this.maintainPhone = maintainPhone;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectPhone() {
		return this.projectPhone;
	}

	public void setProjectPhone(String projectPhone) {
		this.projectPhone = projectPhone;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitPhone() {
		return this.unitPhone;
	}

	public void setUnitPhone(String unitPhone) {
		this.unitPhone = unitPhone;
	}

	public String getRoadManagerName() {
		return this.roadManagerName;
	}

	public void setRoadManagerName(String roadManagerName) {
		this.roadManagerName = roadManagerName;
	}

	public String getRoadManagerPhone() {
		return this.roadManagerPhone;
	}

	public void setRoadManagerPhone(String roadManagerPhone) {
		this.roadManagerPhone = roadManagerPhone;
	}

	public String getAreaManagerName() {
		return this.areaManagerName;
	}

	public void setAreaManagerName(String areaManagerName) {
		this.areaManagerName = areaManagerName;
	}

	public String getAreaManagerPhone() {
		return this.areaManagerPhone;
	}

	public void setAreaManagerPhone(String areaManagerPhone) {
		this.areaManagerPhone = areaManagerPhone;
	}

	public String getDutyManagerName() {
		return this.dutyManagerName;
	}

	public void setDutyManagerName(String dutyManagerName) {
		this.dutyManagerName = dutyManagerName;
	}

	public String getDutyManagerPhone() {
		return this.dutyManagerPhone;
	}

	public void setDutyManagerPhone(String dutyManagerPhone) {
		this.dutyManagerPhone = dutyManagerPhone;
	}

	public String getFrontPhotoAddr() {
		return this.frontPhotoAddr;
	}

	public void setFrontPhotoAddr(String frontPhotoAddr) {
		this.frontPhotoAddr = frontPhotoAddr;
	}

	public String getSidePhotoAddr() {
		return this.sidePhotoAddr;
	}

	public void setSidePhotoAddr(String sidePhotoAddr) {
		this.sidePhotoAddr = sidePhotoAddr;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}



	public String getFileUploadAddr() {
		return fileUploadAddr;
	}



	public void setFileUploadAddr(String fileUploadAddr) {
		this.fileUploadAddr = fileUploadAddr;
	}



	public String getBuildUnit() {
		return buildUnit;
	}



	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}



	public String getStartDate() {
		return startDate;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public String getCompletionDate() {
		return completionDate;
	}



	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}



	public String getConstructType() {
		return constructType;
	}



	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}



	public String getConstructReason() {
		return constructReason;
	}



	public void setConstructReason(String constructReason) {
		this.constructReason = constructReason;
	}



	public String getProjectRange() {
		return projectRange;
	}



	public void setProjectRange(String projectRange) {
		this.projectRange = projectRange;
	}



	public String getFinanceSource() {
		return financeSource;
	}



	public void setFinanceSource(String financeSource) {
		this.financeSource = financeSource;
	}



	public String getQualityEvaluation() {
		return qualityEvaluation;
	}



	public void setQualityEvaluation(String qualityEvaluation) {
		this.qualityEvaluation = qualityEvaluation;
	}



	public Double getBudgetCost() {
		return budgetCost;
	}



	public void setBudgetCost(Double budgetCost) {
		this.budgetCost = budgetCost;
	}



	public String getDepCode() {
		return depCode;
	}



	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}



	public String getSuperstructureMaterial() {
		return superstructureMaterial;
	}



	public void setSuperstructureMaterial(String superstructureMaterial) {
		this.superstructureMaterial = superstructureMaterial;
	}



	public String getSuperstructureBoard() {
		return superstructureBoard;
	}



	public void setSuperstructureBoard(String superstructureBoard) {
		this.superstructureBoard = superstructureBoard;
	}



	public String getSuperstructureStress() {
		return superstructureStress;
	}



	public void setSuperstructureStress(String superstructureStress) {
		this.superstructureStress = superstructureStress;
	}



	public String getBridgePierMaterial() {
		return bridgePierMaterial;
	}



	public void setBridgePierMaterial(String bridgePierMaterial) {
		this.bridgePierMaterial = bridgePierMaterial;
	}



	public String getBridgePierStress() {
		return bridgePierStress;
	}



	public void setBridgePierStress(String bridgePierStress) {
		this.bridgePierStress = bridgePierStress;
	}



	public String getBridgePierForm() {
		return bridgePierForm;
	}



	public void setBridgePierForm(String bridgePierForm) {
		this.bridgePierForm = bridgePierForm;
	}



	public String getAbutmentMaterial() {
		return abutmentMaterial;
	}



	public void setAbutmentMaterial(String abutmentMaterial) {
		this.abutmentMaterial = abutmentMaterial;
	}



	public String getAbutmentForm() {
		return abutmentForm;
	}



	public void setAbutmentForm(String abutmentForm) {
		this.abutmentForm = abutmentForm;
	}



	public String getBridgePierBaseMaterial() {
		return bridgePierBaseMaterial;
	}



	public void setBridgePierBaseMaterial(String bridgePierBaseMaterial) {
		this.bridgePierBaseMaterial = bridgePierBaseMaterial;
	}



	public String getBridgePierBaseConstruct() {
		return bridgePierBaseConstruct;
	}



	public void setBridgePierBaseConstruct(String bridgePierBaseConstruct) {
		this.bridgePierBaseConstruct = bridgePierBaseConstruct;
	}



	public String getBridgePierBaseForm() {
		return bridgePierBaseForm;
	}



	public void setBridgePierBaseForm(String bridgePierBaseForm) {
		this.bridgePierBaseForm = bridgePierBaseForm;
	}



	public String getAbutmentBaseMaterial() {
		return abutmentBaseMaterial;
	}



	public void setAbutmentBaseMaterial(String abutmentBaseMaterial) {
		this.abutmentBaseMaterial = abutmentBaseMaterial;
	}



	public String getAbutmentBaseConstruct() {
		return abutmentBaseConstruct;
	}



	public void setAbutmentBaseConstruct(String abutmentBaseConstruct) {
		this.abutmentBaseConstruct = abutmentBaseConstruct;
	}



	public String getAbutmentBaseForm() {
		return abutmentBaseForm;
	}



	public void setAbutmentBaseForm(String abutmentBaseForm) {
		this.abutmentBaseForm = abutmentBaseForm;
	}



	public String getSeismicGrade() {
		return seismicGrade;
	}



	public void setSeismicGrade(String seismicGrade) {
		this.seismicGrade = seismicGrade;
	}



	public String getSeismicMeasures() {
		return seismicMeasures;
	}



	public void setSeismicMeasures(String seismicMeasures) {
		this.seismicMeasures = seismicMeasures;
	}



	public String getUnderCrossName() {
		return underCrossName;
	}



	public void setUnderCrossName(String underCrossName) {
		this.underCrossName = underCrossName;
	}



	public String getUnderCrossNumber() {
		return underCrossNumber;
	}



	public void setUnderCrossNumber(String underCrossNumber) {
		this.underCrossNumber = underCrossNumber;
	}



	public String getApproachWidth() {
		return approachWidth;
	}



	public void setApproachWidth(String approachWidth) {
		this.approachWidth = approachWidth;
	}



	public String getApproachFaceWidth() {
		return approachFaceWidth;
	}



	public void setApproachFaceWidth(String approachFaceWidth) {
		this.approachFaceWidth = approachFaceWidth;
	}



	public String getApproachForm() {
		return approachForm;
	}



	public void setApproachForm(String approachForm) {
		this.approachForm = approachForm;
	}



	public String getEarthquakeFactor() {
		return earthquakeFactor;
	}



	public void setEarthquakeFactor(String earthquakeFactor) {
		this.earthquakeFactor = earthquakeFactor;
	}



	public String getSlopeProtection() {
		return slopeProtection;
	}



	public void setSlopeProtection(String slopeProtection) {
		this.slopeProtection = slopeProtection;
	}



	public String getBridgePier() {
		return bridgePier;
	}



	public void setBridgePier(String bridgePier) {
		this.bridgePier = bridgePier;
	}



	public String getRegulatingStructure() {
		return regulatingStructure;
	}



	public void setRegulatingStructure(String regulatingStructure) {
		this.regulatingStructure = regulatingStructure;
	}



	public Double getNormalWaterLevel() {
		return normalWaterLevel;
	}



	public void setNormalWaterLevel(Double normalWaterLevel) {
		this.normalWaterLevel = normalWaterLevel;
	}



	public Double getDesignWaterLevel() {
		return designWaterLevel;
	}



	public void setDesignWaterLevel(Double designWaterLevel) {
		this.designWaterLevel = designWaterLevel;
	}



	public String getDesignDrawing() {
		return designDrawing;
	}



	public void setDesignDrawing(String designDrawing) {
		this.designDrawing = designDrawing;
	}



	public String getDesignDrawingNum() {
		return designDrawingNum;
	}



	public void setDesignDrawingNum(String designDrawingNum) {
		this.designDrawingNum = designDrawingNum;
	}



	public String getDesignDocument() {
		return designDocument;
	}



	public void setDesignDocument(String designDocument) {
		this.designDocument = designDocument;
	}



	public String getDesignDocumentNum() {
		return designDocumentNum;
	}



	public void setDesignDocumentNum(String designDocumentNum) {
		this.designDocumentNum = designDocumentNum;
	}



	public String getConstructionDocument() {
		return constructionDocument;
	}



	public void setConstructionDocument(String constructionDocument) {
		this.constructionDocument = constructionDocument;
	}



	public String getConstructionDocumentNum() {
		return constructionDocumentNum;
	}



	public void setConstructionDocumentNum(String constructionDocumentNum) {
		this.constructionDocumentNum = constructionDocumentNum;
	}



	public String getCompletedDrawing() {
		return completedDrawing;
	}



	public void setCompletedDrawing(String completedDrawing) {
		this.completedDrawing = completedDrawing;
	}



	public String getCompletedDrawingNum() {
		return completedDrawingNum;
	}



	public void setCompletedDrawingNum(String completedDrawingNum) {
		this.completedDrawingNum = completedDrawingNum;
	}



	public String getAcceptanceDocument() {
		return acceptanceDocument;
	}



	public void setAcceptanceDocument(String acceptanceDocument) {
		this.acceptanceDocument = acceptanceDocument;
	}



	public String getAcceptanceDocumentNum() {
		return acceptanceDocumentNum;
	}



	public void setAcceptanceDocumentNum(String acceptanceDocumentNum) {
		this.acceptanceDocumentNum = acceptanceDocumentNum;
	}



	public String getAdministratorDocument() {
		return administratorDocument;
	}



	public void setAdministratorDocument(String administratorDocument) {
		this.administratorDocument = administratorDocument;
	}



	public String getAdministratorDocumentNum() {
		return administratorDocumentNum;
	}



	public void setAdministratorDocumentNum(String administratorDocumentNum) {
		this.administratorDocumentNum = administratorDocumentNum;
	}



	public String getInspectionReport() {
		return inspectionReport;
	}



	public void setInspectionReport(String inspectionReport) {
		this.inspectionReport = inspectionReport;
	}



	public String getInspectionReportNum() {
		return inspectionReportNum;
	}



	public void setInspectionReportNum(String inspectionReportNum) {
		this.inspectionReportNum = inspectionReportNum;
	}



	public String getSpecialInspection() {
		return specialInspection;
	}



	public void setSpecialInspection(String specialInspection) {
		this.specialInspection = specialInspection;
	}



	public String getSpecialInspectionNum() {
		return specialInspectionNum;
	}



	public void setSpecialInspectionNum(String specialInspectionNum) {
		this.specialInspectionNum = specialInspectionNum;
	}



	public String getMaintainRecord() {
		return maintainRecord;
	}



	public void setMaintainRecord(String maintainRecord) {
		this.maintainRecord = maintainRecord;
	}



	public String getMaintainRecordNum() {
		return maintainRecordNum;
	}



	public void setMaintainRecordNum(String maintainRecordNum) {
		this.maintainRecordNum = maintainRecordNum;
	}



	public String getFileId() {
		return fileId;
	}



	public void setFileId(String fileId) {
		this.fileId = fileId;
	}



	public String getFileForm() {
		return fileForm;
	}



	public void setFileForm(String fileForm) {
		this.fileForm = fileForm;
	}



	public String getFileDate() {
		return fileDate;
	}



	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}



	public String getCurved() {
		return curved;
	}



	public void setCurved(String curved) {
		this.curved = curved;
	}



	public String getCurvedRampDegree() {
		return curvedRampDegree;
	}



	public void setCurvedRampDegree(String curvedRampDegree) {
		this.curvedRampDegree = curvedRampDegree;
	}


	

}
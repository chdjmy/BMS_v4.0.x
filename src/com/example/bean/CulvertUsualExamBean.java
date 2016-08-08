package com.example.bean;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;

@Table(name="cusual_exam")
public class CulvertUsualExamBean {
	@Id
	@Column(name="cusual_exam_id", lenght = 0, type = "BIGINT" )
	private String cusualExamId;
	@Column(name="culvert_code", lenght = 15, type = "CHAR" )
	private String culvertCode;
//	@Column(name="culvert_name", lenght = 255, type = "NVARCHAR" )
	private String culvertName;
//	@Column(name="line_number", lenght = 255, type = "NVARCHAR" )
	private String lineNumber;
//	@Column(name="line_name", lenght = 255, type = "NVARCHAR" )
	private String lineName;
//	@Column(name="serial_number", lenght = 255, type = "NVARCHAR" )
	private String serialNumber;
//	@Column(name="location_name", lenght = 255, type = "NVARCHAR" )
	private String locationName;
//	@Column(name="line_type", lenght = 255, type = "NVARCHAR" )
	private String lineType;
//	@Column(name="MM_name", lenght = 255, type = "NVARCHAR" )
	private String mmName;
//	@Column(name="center_stake", lenght = 0, type = "FLOAT" )
	private Double centerStake;
//	@Column(name="construction_stake", lenght = 0, type = "FLOAT" )
	private Double constructionStake;
//	@Column(name="culvert_angle", lenght = 0, type = "FLOAT" )
	private Double culvertAngle;
//	@Column(name="culvert_length", lenght = 0, type = "FLOAT" )
	private Double culvertLength;
//	@Column(name="cover_length", lenght = 0, type = "FLOAT" )
	private Double coverLength;
//	@Column(name="culvert_height", lenght = 0, type = "FLOAT" )
	private Double culvertHeight;
//	@Column(name="fill_depth", lenght = 0, type = "FLOAT" )
	private Double fillDepth;
//	@Column(name="construct_date", lenght = 0, type = "DATE"  )
	private String constructDate;
//	@Column(name="culvert_type", lenght = 255, type = "NVARCHAR")
	private String culvertType;
//	@Column(name="design_load", lenght = 255, type = "NVARCHAR" )
	private String designLoad;
//	@Column(name="hole_span", lenght = 255, type = "NVARCHAR" )
	private String holeSpan;
//	@Column(name="inlet_type", lenght = 255, type = "NVARCHAR" )
	private String inletType;
//	@Column(name="outlet_type", lenght = 255, type = "NVARCHAR" )
	private String outletType;
//	@Column(name="manage_code", lenght = 255, type = "NVARCHAR" )
	private String manageCode;
	@Column(name="maintain_name", lenght = 255, type = "NVARCHAR" )
	private String maintainName;
	@Column(name="manager_name", lenght = 255, type = "NVARCHAR" )
	private String managerName;
	@Column(name="noter", lenght = 255, type = "NVARCHAR" )
	private String noter;
	@Column(name="check_date", lenght = 255, type = "NVARCHAR" )
	private String checkDate;
	@Column(name="inlet_type_disease", lenght = 255, type = "NVARCHAR" )
	private String inletTypeDisease;
	@Column(name="inlet_region", lenght = 255, type = "NVARCHAR" )
	private String inletRegion;
	@Column(name="inlet_advise", lenght = 255, type = "NVARCHAR" )
	private String inletAdvise;
	@Column(name="outlet_type_disease", lenght = 255, type = "NVARCHAR" )
	private String outletTypeDisease;
	@Column(name="outlet_region", lenght = 255, type = "NVARCHAR" )
	private String outletRegion;
	@Column(name="outlet_advise", lenght = 255, type = "NVARCHAR" )
	private String outletAdvise;
	@Column(name="body_type", lenght = 255, type = "NVARCHAR" )
	private String bodyType;
	@Column(name="body_region", lenght = 255, type = "NVARCHAR" )
	private String bodyRegion;
	@Column(name="body_advise", lenght = 255, type = "NVARCHAR" )
	private String bodyAdvise;
	@Column(name="top_type", lenght = 255, type = "NVARCHAR" )
	private String topType;
	@Column(name="top_region", lenght = 255, type = "NVARCHAR" )
	private String topRegion;
	@Column(name="top_advise", lenght = 255, type = "NVARCHAR" )
	private String topAdvise;
	@Column(name="bottom_type", lenght = 255, type = "NVARCHAR" )
	private String bottomType;
	@Column(name="bottom_region", lenght = 255, type = "NVARCHAR" )
	private String bottomRegion;
	@Column(name="bottom_advise", lenght = 255, type = "NVARCHAR" )
	private String bottomAdvise;
	@Column(name="fill_type", lenght = 255, type = "NVARCHAR" )
	private String fillType;
	@Column(name="fill_region", lenght = 255, type = "NVARCHAR" )
	private String fillRegion;
	@Column(name="fill_advise", lenght = 255, type = "NVARCHAR" )
	private String fillAdvise;
	@Column(name="lighting_type", lenght = 255, type = "NVARCHAR" )
	private String lightingType;
	@Column(name="lighting_region", lenght = 255, type = "NVARCHAR" )
	private String lightingRegion;
	@Column(name="lighting_advise", lenght = 255, type = "NVARCHAR" )
	private String lightingAdvise;
	@Column(name="else_type", lenght = 255, type = "NVARCHAR" )
	private String elseType;
	@Column(name="else_region", lenght = 255, type = "NVARCHAR" )
	private String elseRegion;
	@Column(name="else_advise", lenght = 255, type = "NVARCHAR" )
	private String elseAdvise;
	@Column(name="remark", lenght = 255, type = "NVARCHAR" )
	private String remark;
	@Column(name="photo_addr", lenght = 255, type = "NVARCHAR" )
	private String photoAddr;
	@Column(name="is_upload", lenght = 1, type = "CHAR" )
	private String isUpload;
	@Column(name="dep_code", lenght = 255, type = "NVARCHAR" )
	private String depCode;

	// Constructors

	/** default constructor */
	public CulvertUsualExamBean() {
	}
	
	public String getCusualExamId() {
		return this.cusualExamId;
	}

	public void setCusualExamId(String cusualExamId) {
		this.cusualExamId = cusualExamId;
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

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLineType() {
		return this.lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getMmName() {
		return this.mmName;
	}

	public void setMmName(String mmName) {
		this.mmName = mmName;
	}

	public Double getCenterStake() {
		return this.centerStake;
	}

	public void setCenterStake(Double centerStake) {
		this.centerStake = centerStake;
	}

	public Double getConstructionStake() {
		return this.constructionStake;
	}

	public void setConstructionStake(Double constructionStake) {
		this.constructionStake = constructionStake;
	}

	public Double getCulvertAngle() {
		return this.culvertAngle;
	}

	public void setCulvertAngle(Double culvertAngle) {
		this.culvertAngle = culvertAngle;
	}

	public Double getCulvertLength() {
		return this.culvertLength;
	}

	public void setCulvertLength(Double culvertLength) {
		this.culvertLength = culvertLength;
	}

	public Double getCoverLength() {
		return this.coverLength;
	}

	public void setCoverLength(Double coverLength) {
		this.coverLength = coverLength;
	}

	public Double getCulvertHeight() {
		return this.culvertHeight;
	}

	public void setCulvertHeight(Double culvertHeight) {
		this.culvertHeight = culvertHeight;
	}

	public Double getFillDepth() {
		return this.fillDepth;
	}

	public void setFillDepth(Double fillDepth) {
		this.fillDepth = fillDepth;
	}

	public String getConstructDate() {
		return this.constructDate;
	}

	public void setConstructDate(String constructDate) {
		this.constructDate = constructDate;
	}

	public String getCulvertType() {
		return this.culvertType;
	}

	public void setCulvertType(String culvertType) {
		this.culvertType = culvertType;
	}

	public String getDesignLoad() {
		return this.designLoad;
	}

	public void setDesignLoad(String designLoad) {
		this.designLoad = designLoad;
	}

	public String getHoleSpan() {
		return this.holeSpan;
	}

	public void setHoleSpan(String holeSpan) {
		this.holeSpan = holeSpan;
	}

	public String getInletType() {
		return this.inletType;
	}

	public void setInletType(String inletType) {
		this.inletType = inletType;
	}

	public String getOutletType() {
		return this.outletType;
	}

	public void setOutletType(String outletType) {
		this.outletType = outletType;
	}

	public String getManageCode() {
		return this.manageCode;
	}

	public void setManageCode(String manageCode) {
		this.manageCode = manageCode;
	}

	public String getMaintainName() {
		return this.maintainName;
	}

	public void setMaintainName(String maintainName) {
		this.maintainName = maintainName;
	}

	public String getManagerName() {
		return this.managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getNoter() {
		return this.noter;
	}

	public void setNoter(String noter) {
		this.noter = noter;
	}

	public String getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}


	public String getInletRegion() {
		return this.inletRegion;
	}

	public void setInletRegion(String inletRegion) {
		this.inletRegion = inletRegion;
	}

	public String getInletAdvise() {
		return this.inletAdvise;
	}

	public void setInletAdvise(String inletAdvise) {
		this.inletAdvise = inletAdvise;
	}


	public String getInletTypeDisease() {
		return inletTypeDisease;
	}

	public void setInletTypeDisease(String inletTypeDisease) {
		this.inletTypeDisease = inletTypeDisease;
	}

	public String getOutletTypeDisease() {
		return outletTypeDisease;
	}

	public void setOutletTypeDisease(String outletTypeDisease) {
		this.outletTypeDisease = outletTypeDisease;
	}

	public String getOutletRegion() {
		return this.outletRegion;
	}

	public void setOutletRegion(String outletRegion) {
		this.outletRegion = outletRegion;
	}

	public String getOutletAdvise() {
		return this.outletAdvise;
	}

	public void setOutletAdvise(String outletAdvise) {
		this.outletAdvise = outletAdvise;
	}

	public String getBodyType() {
		return this.bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public String getBodyRegion() {
		return this.bodyRegion;
	}

	public void setBodyRegion(String bodyRegion) {
		this.bodyRegion = bodyRegion;
	}

	public String getBodyAdvise() {
		return this.bodyAdvise;
	}

	public void setBodyAdvise(String bodyAdvise) {
		this.bodyAdvise = bodyAdvise;
	}

	public String getTopType() {
		return this.topType;
	}

	public void setTopType(String topType) {
		this.topType = topType;
	}

	public String getTopRegion() {
		return this.topRegion;
	}

	public void setTopRegion(String topRegion) {
		this.topRegion = topRegion;
	}

	public String getTopAdvise() {
		return this.topAdvise;
	}

	public void setTopAdvise(String topAdvise) {
		this.topAdvise = topAdvise;
	}

	public String getBottomType() {
		return this.bottomType;
	}

	public void setBottomType(String bottomType) {
		this.bottomType = bottomType;
	}

	public String getBottomRegion() {
		return this.bottomRegion;
	}

	public void setBottomRegion(String bottomRegion) {
		this.bottomRegion = bottomRegion;
	}

	public String getBottomAdvise() {
		return this.bottomAdvise;
	}

	public void setBottomAdvise(String bottomAdvise) {
		this.bottomAdvise = bottomAdvise;
	}

	public String getFillType() {
		return this.fillType;
	}

	public void setFillType(String fillType) {
		this.fillType = fillType;
	}

	public String getFillRegion() {
		return this.fillRegion;
	}

	public void setFillRegion(String fillRegion) {
		this.fillRegion = fillRegion;
	}

	public String getFillAdvise() {
		return this.fillAdvise;
	}

	public void setFillAdvise(String fillAdvise) {
		this.fillAdvise = fillAdvise;
	}

	public String getLightingType() {
		return this.lightingType;
	}

	public void setLightingType(String lightingType) {
		this.lightingType = lightingType;
	}

	public String getLightingRegion() {
		return this.lightingRegion;
	}

	public void setLightingRegion(String lightingRegion) {
		this.lightingRegion = lightingRegion;
	}

	public String getLightingAdvise() {
		return this.lightingAdvise;
	}

	public void setLightingAdvise(String lightingAdvise) {
		this.lightingAdvise = lightingAdvise;
	}

	public String getElseType() {
		return this.elseType;
	}

	public void setElseType(String elseType) {
		this.elseType = elseType;
	}

	public String getElseRegion() {
		return this.elseRegion;
	}

	public void setElseRegion(String elseRegion) {
		this.elseRegion = elseRegion;
	}

	public String getElseAdvise() {
		return this.elseAdvise;
	}

	public void setElseAdvise(String elseAdvise) {
		this.elseAdvise = elseAdvise;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPhotoAddr() {
		return this.photoAddr;
	}

	public void setPhotoAddr(String photoAddr) {
		this.photoAddr = photoAddr;
	}

	public String getIsUpload() {
		return this.isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}
	
	public String getCulvertCode() {
		return culvertCode;
	}

	public void setCulvertCode(String culvertCode) {
		this.culvertCode = culvertCode;
	}

	public String getCulvertName() {
		return culvertName;
	}

	public void setCulvertName(String culvertName) {
		this.culvertName = culvertName;
	}
	
	public String getDepCode() {
		
		return depCode;
	}



	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}
	
}

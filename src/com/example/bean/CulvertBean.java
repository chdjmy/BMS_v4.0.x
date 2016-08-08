package com.example.bean;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;

@Table(name="culvert")
public class CulvertBean {

	@Id
	@Column(name="culvert_id", lenght = 0, type = "BIGINT" )
	private String culvertId;
	@Column(name="grade", lenght = 1, type = "CHAR" )
	private String grade;
	@Column(name="culvert_code", lenght = 15, type = "CHAR" )
	private String culvertCode;
	@Column(name="culvert_name", lenght = 255, type = "NVARCHAR" )
	private String culvertName;
	@Column(name="line_number", lenght = 255, type = "NVARCHAR" )
	private String lineNumber;
	@Column(name="line_name", lenght = 255, type = "NVARCHAR" )
	private String lineName;
	@Column(name="serial_number", lenght = 255, type = "NVARCHAR" )
	private String serialNumber;
	@Column(name="location_name", lenght = 255, type = "NVARCHAR" )
	private String locationName;
	@Column(name="line_type", lenght = 255, type = "NVARCHAR" )
	private String lineType;
	@Column(name="MM_name", lenght = 255, type = "NVARCHAR" )
	private String mmName;
	
	@Column(name="center_stake", lenght = 0, type = "FLOAT" )
	private Double centerStake;
	@Column(name="construct_stake", lenght = 0, type = "FLOAT" )
	private Double constructStake;
	@Column(name="culvert_angle", lenght = 0, type = "FLOAT" )
	private Double culvertAngle;
	@Column(name="culvert_length", lenght = 0, type = "FLOAT" )
	private Double culvertLength;
	@Column(name="cover_length", lenght = 0, type = "FLOAT" )
	private Double coverLength;
	@Column(name="culvert_height", lenght = 0, type = "FLOAT" )
	private Double culvertHeight;
	@Column(name="fill_depth", lenght = 0, type = "FLOAT" )
	private Double fillDepth;
	@Column(name="construct_date", lenght = 255, type = "NVARCHAR" )
	private String constructDate;
	@Column(name="culvert_type", lenght = 255, type = "NVARCHAR" )
	private String culvertType;
	@Column(name="design_load", lenght = 255, type = "NVARCHAR" )
	private String designLoad;
	@Column(name="hole_span", lenght = 255, type = "NVARCHAR" )
	private String holeSpan;
	@Column(name="inlet_type", lenght = 255, type = "NVARCHAR" )
	private String inletType;
	@Column(name="outlet_type", lenght = 255, type = "NVARCHAR" )
	private String outletType;
	@Column(name="manage_code", lenght = 255, type = "NVARCHAR" )
	private String manageCode;
	@Column(name="basis_disease", lenght = 255, type = "NVARCHAR" )
	private String basisDisease;
	@Column(name="body_disease", lenght = 255, type = "NVARCHAR" )
	private String bodyDisease;
	@Column(name="bottom_disease", lenght = 255, type = "NVARCHAR" )
	private String bottomDisease;
	@Column(name="top_disease", lenght = 255, type = "NVARCHAR" )
	private String topDisease;
	@Column(name="inlet_disease", lenght = 255, type = "NVARCHAR" )
	private String inletDisease;
	@Column(name="outlet_disease", lenght = 255, type = "NVARCHAR" )
	private String outletDisease;
	@Column(name="pitching_disease", lenght = 255, type = "NVARCHAR" )
	private String pitchingDisease;
	@Column(name="wing_slope_disease", lenght = 255, type = "NVARCHAR" )
	private String wingSlopeDisease;
	@Column(name="jump_disease", lenght = 255, type = "NVARCHAR" )
	private String jumpDisease;
	@Column(name="drainage_disease", lenght = 255, type = "NVARCHAR" )
	private String drainageDisease;
	@Column(name="maintenance_condition", lenght = 255, type = "NVARCHAR" )
	private String maintenanceCondition;
	@Column(name="clean_condition", lenght = 255, type = "NVARCHAR" )
	private String cleanCondition;
	@Column(name="check_date", lenght = 0, type = "DATE" )
	private String checkDate;
	@Column(name="next_check_date", lenght = 0, type = "DATE" )
	private String nextCheckDate;
	@Column(name="remark", lenght = 255, type = "NVARCHAR" )
	private String remark;
	@Column(name="adaptive_score", lenght = 0, type = "FLOAT" )
	private Double adaptiveScore;
	@Column(name="composite_rating", lenght = 255, type = "NVARCHAR" )
	private String compositeRating;
	@Column(name="bar_code", lenght = 255, type = "NVARCHAR" )
	private String barCode;
	@Column(name="culvert_photo_addr", lenght = 255, type = "NVARCHAR" )
	private String culvertPhotoAddr;
	@Column(name="latitude", lenght = 0, type = "FLOAT" )
	private Double latitude;
	@Column(name="longitude", lenght = 0, type = "FLOAT" )
	private Double longitude;
	@Column(name="dep_code", lenght = 255, type = "VARCHAR" )
	private String depCode;
	// Constructors
	

	/** default constructor */
	public CulvertBean() {
	}

	public String getCulvertId() {
		return this.culvertId;
	}

	public void setCulvertId(String culvertId) {
		this.culvertId = culvertId;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCulvertCode() {
		return this.culvertCode;
	}

	public void setCulvertCode(String culvertCode) {
		this.culvertCode = culvertCode;
	}

	public String getCulvertName() {
		return this.culvertName;
	}

	public void setCulvertName(String culvertName) {
		this.culvertName = culvertName;
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

	public Double getConstructStake() {
		return this.constructStake;
	}

	public void setConstructStake(Double constructStake) {
		this.constructStake = constructStake;
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

	public String getBasisDisease() {
		return this.basisDisease;
	}

	public void setBasisDisease(String basisDisease) {
		this.basisDisease = basisDisease;
	}

	public String getBodyDisease() {
		return this.bodyDisease;
	}

	public void setBodyDisease(String bodyDisease) {
		this.bodyDisease = bodyDisease;
	}

	public String getBottomDisease() {
		return this.bottomDisease;
	}

	public void setBottomDisease(String bottomDisease) {
		this.bottomDisease = bottomDisease;
	}

	public String getTopDisease() {
		return this.topDisease;
	}

	public void setTopDisease(String topDisease) {
		this.topDisease = topDisease;
	}

	public String getInletDisease() {
		return this.inletDisease;
	}

	public void setInletDisease(String inletDisease) {
		this.inletDisease = inletDisease;
	}

	public String getOutletDisease() {
		return this.outletDisease;
	}

	public void setOutletDisease(String outletDisease) {
		this.outletDisease = outletDisease;
	}

	public String getPitchingDisease() {
		return this.pitchingDisease;
	}

	public void setPitchingDisease(String pitchingDisease) {
		this.pitchingDisease = pitchingDisease;
	}

	public String getWingSlopeDisease() {
		return this.wingSlopeDisease;
	}

	public void setWingSlopeDisease(String wingSlopeDisease) {
		this.wingSlopeDisease = wingSlopeDisease;
	}

	public String getJumpDisease() {
		return this.jumpDisease;
	}

	public void setJumpDisease(String jumpDisease) {
		this.jumpDisease = jumpDisease;
	}

	public String getDrainageDisease() {
		return this.drainageDisease;
	}

	public void setDrainageDisease(String drainageDisease) {
		this.drainageDisease = drainageDisease;
	}

	public String getMaintenanceCondition() {
		return this.maintenanceCondition;
	}

	public void setMaintenanceCondition(String maintenanceCondition) {
		this.maintenanceCondition = maintenanceCondition;
	}

	public String getCleanCondition() {
		return this.cleanCondition;
	}

	public void setCleanCondition(String cleanCondition) {
		this.cleanCondition = cleanCondition;
	}

	public String getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getNextCheckDate() {
		return this.nextCheckDate;
	}

	public void setNextCheckDate(String nextCheckDate) {
		this.nextCheckDate = nextCheckDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getAdaptiveScore() {
		return this.adaptiveScore;
	}

	public void setAdaptiveScore(Double adaptiveScore) {
		this.adaptiveScore = adaptiveScore;
	}

	public String getCompositeRating() {
		return this.compositeRating;
	}

	public void setCompositeRating(String compositeRating) {
		this.compositeRating = compositeRating;
	}

	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getCulvertPhotoAddr() {
		return this.culvertPhotoAddr;
	}

	public void setCulvertPhotoAddr(String culvertPhotoAddr) {
		this.culvertPhotoAddr = culvertPhotoAddr;
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
}

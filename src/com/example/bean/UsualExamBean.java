package com.example.bean;

import java.io.Serializable;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;

/**
 * UsualExam entity. @author MyEclipse Persistence Tools
 */
@Table(name="usual_exam")
public class UsualExamBean implements Serializable{

	// Fields
	@Id
	@Column(name="usual_exam_id", lenght = 0, type = "BIGINT" )
	private String usualExamId;
	@Column(name="manager_unit", lenght = 255, type = "NVARCHAR" )
	private String managerUnit;
	@Column(name="bridge_code", lenght = 255, type = "NVARCHAR" )
	private String bridgeCode;
//	@Column(name="bridge_name", lenght = 255, type = "NVARCHAR" )
	private String bridgeName;
//	@Column(name="line_number", lenght = 255, type = "NVARCHAR" )
	private String lineNumber;
//	@Column(name="line_name", lenght = 255, type = "NVARCHAR" )
	private String lineName;
//	@Column(name="center_stake", lenght = 255, type = "NVARCHAR" )
	private Double centerStake;
//	@Column(name="MM_name", lenght = 0, type = "FLOAT" )
	private String mmName;
	@Column(name="principal", lenght = 255, type = "NVARCHAR" )
	private String principal;
	@Column(name="noter", lenght = 255, type = "NVARCHAR" )
	private String noter;
	@Column(name="wall_type", lenght = 255, type = "NVARCHAR" )
	private String wallType;
	@Column(name="wall_region", lenght = 255, type = "NVARCHAR" )
	private String wallRegion;
	@Column(name="wall_advise", lenght = 255, type = "NVARCHAR" )
	private String wallAdvise;
	@Column(name="slope_type", lenght = 255, type = "NVARCHAR" )
	private String slopeType;
	@Column(name="slope_region", lenght = 255, type = "NVARCHAR" )
	private String slopeRegion;
	@Column(name="slope_advise", lenght = 255, type = "NVARCHAR" )
	private String slopeAdvise;
	@Column(name="deck_type", lenght = 255, type = "NVARCHAR" )
	private String deckType;
	@Column(name="deck_region", lenght = 255, type = "NVARCHAR" )
	private String deckRegion;
	@Column(name="deck_advise", lenght = 255, type = "NVARCHAR" )
	private String deckAdvise;
	@Column(name="expansion_type", lenght = 255, type = "NVARCHAR" )
	private String expansionType;
	@Column(name="expansion_region", lenght = 255, type = "NVARCHAR" )
	private String expansionRegion;
	@Column(name="expansion_advise", lenght = 255, type = "NVARCHAR" )
	private String expansionAdvise;
	@Column(name="sidewalk_type", lenght = 255, type = "NVARCHAR" )
	private String sidewalkType;
	@Column(name="sidewalk_region", lenght = 255, type = "NVARCHAR" )
	private String sidewalkRegion;
	@Column(name="sidewalk_advise", lenght = 255, type = "NVARCHAR" )
	private String sidewalkAdvise;
	@Column(name="guard_type", lenght = 255, type = "NVARCHAR" )
	private String guardType;
	@Column(name="guard_region", lenght = 255, type = "NVARCHAR" )
	private String guardRegion;
	@Column(name="guard_advise", lenght = 255, type = "NVARCHAR" )
	private String guardAdvise;
	@Column(name="waterproof_type", lenght = 255, type = "NVARCHAR" )
	private String waterproofType;
	@Column(name="waterproof_region", lenght = 255, type = "NVARCHAR" )
	private String waterproofRegion;
	@Column(name="waterproof_advise", lenght = 255, type = "NVARCHAR" )
	private String waterproofAdvise;
	@Column(name="lighting_type", lenght = 255, type = "NVARCHAR" )
	private String lightingType;
	@Column(name="lighting_region", lenght = 255, type = "NVARCHAR" )
	private String lightingRegion;
	@Column(name="lighting_advise", lenght = 255, type = "NVARCHAR" )
	private String lightingAdvise;
	@Column(name="clean_type", lenght = 255, type = "NVARCHAR" )
	private String cleanType;
	@Column(name="clean_region", lenght = 255, type = "NVARCHAR" )
	private String cleanRegion;
	@Column(name="clean_advise", lenght = 255, type = "NVARCHAR" )
	private String cleanAdvise;
	@Column(name="abutment_type", lenght = 255, type = "NVARCHAR" )
	private String abutmentType;
	@Column(name="abutment_region", lenght = 255, type = "NVARCHAR" )
	private String abutmentRegion;
	@Column(name="abutment_advise", lenght = 255, type = "NVARCHAR" )
	private String abutmentAdvise;
	@Column(name="pier_type", lenght = 255, type = "NVARCHAR" )
	private String pierType;
	@Column(name="pier_region", lenght = 255, type = "NVARCHAR" )
	private String pierRegion;
	@Column(name="pie_advise", lenght = 255, type = "NVARCHAR" )
	private String pieAdvise;
	@Column(name="foundation_type", lenght = 255, type = "NVARCHAR" )
	private String foundationType;
	@Column(name="foundation_region", lenght = 255, type = "NVARCHAR" )
	private String foundationRegion;
	@Column(name="foundation_advise", lenght = 255, type = "NVARCHAR" )
	private String foundationAdvise;
	@Column(name="supports_type", lenght = 255, type = "NVARCHAR" )
	private String supportsType;
	@Column(name="supports_region", lenght = 255, type = "NVARCHAR" )
	private String supportsRegion;
	@Column(name="supports_advise", lenght = 255, type = "NVARCHAR" )
	private String supportsAdvise;
	@Column(name="superstructure_type", lenght = 255, type = "NVARCHAR" )
	private String superstructureType;
	@Column(name="superstructure_region", lenght = 255, type = "NVARCHAR" )
	private String superstructureRegion;
	@Column(name="superstructure_advise", lenght = 255, type = "NVARCHAR" )
	private String superstructureAdvise;
	@Column(name="approach_type", lenght = 255, type = "NVARCHAR" )
	private String approachType;
	@Column(name="approach_region", lenght = 255, type = "NVARCHAR" )
	private String approachRegion;
	@Column(name="approach_advise", lenght = 255, type = "NVARCHAR" )
	private String approachAdvise;
	@Column(name="sign_type", lenght = 255, type = "NVARCHAR" )
	private String signType;
	@Column(name="sign_region", lenght = 255, type = "NVARCHAR" )
	private String signRegion;
	@Column(name="sign_advise", lenght = 255, type = "NVARCHAR" )
	private String signAdvise;
	@Column(name="regulating_type", lenght = 255, type = "NVARCHAR" )
	private String regulatingType;
	@Column(name="regulating_region", lenght = 255, type = "NVARCHAR" )
	private String regulatingRegion;
	@Column(name="regulating_advise", lenght = 255, type = "NVARCHAR" )
	private String regulatingAdvise;
	@Column(name="else_type", lenght = 255, type = "NVARCHAR" )
	private String elseType;
	@Column(name="else_region", lenght = 255, type = "NVARCHAR" )
	private String elseRegion;
	@Column(name="else_advise", lenght = 255, type = "NVARCHAR" )
	private String elseAdvise;
	@Column(name="check_date", lenght = 0, type = "DATE" )
	private String checkDate;
	@Column(name="photo_addr", lenght = 255, type = "NVARCHAR" )
	private String photoAddr;
	@Column(name="is_upload", lenght = 1, type = "CHAR" )
	private String isUpload;
	@Column(name="dep_code", lenght = 255, type = "NVARCHAR" )
	private String depCode;
	
	

	// Constructors

	public String getDepCode() {
		
		return depCode;
	}



	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}



	/** default constructor */
	public UsualExamBean() {
	}



	// Property accessors

	public String getUsualExamId() {
		return this.usualExamId;
	}

	public void setUsualExamId(String usualExamId) {
		this.usualExamId = usualExamId;
	}

	public String getManagerUnit() {
		return this.managerUnit;
	}

	public void setManagerUnit(String managerUnit) {
		this.managerUnit = managerUnit;
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

	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getNoter() {
		return this.noter;
	}

	public void setNoter(String noter) {
		this.noter = noter;
	}

	public String getWallType() {
		return this.wallType;
	}

	public void setWallType(String wallType) {
		this.wallType = wallType;
	}

	public String getWallRegion() {
		return this.wallRegion;
	}

	public void setWallRegion(String wallRegion) {
		this.wallRegion = wallRegion;
	}

	public String getWallAdvise() {
		return this.wallAdvise;
	}

	public void setWallAdvise(String wallAdvise) {
		this.wallAdvise = wallAdvise;
	}

	public String getSlopeType() {
		return this.slopeType;
	}

	public void setSlopeType(String slopeType) {
		this.slopeType = slopeType;
	}

	public String getSlopeRegion() {
		return this.slopeRegion;
	}

	public void setSlopeRegion(String slopeRegion) {
		this.slopeRegion = slopeRegion;
	}

	public String getSlopeAdvise() {
		return this.slopeAdvise;
	}

	public void setSlopeAdvise(String slopeAdvise) {
		this.slopeAdvise = slopeAdvise;
	}

	public String getDeckType() {
		return this.deckType;
	}

	public void setDeckType(String deckType) {
		this.deckType = deckType;
	}

	public String getDeckRegion() {
		return this.deckRegion;
	}

	public void setDeckRegion(String deckRegion) {
		this.deckRegion = deckRegion;
	}

	public String getDeckAdvise() {
		return this.deckAdvise;
	}

	public void setDeckAdvise(String deckAdvise) {
		this.deckAdvise = deckAdvise;
	}

	public String getExpansionType() {
		return this.expansionType;
	}

	public void setExpansionType(String expansionType) {
		this.expansionType = expansionType;
	}

	public String getExpansionRegion() {
		return this.expansionRegion;
	}

	public void setExpansionRegion(String expansionRegion) {
		this.expansionRegion = expansionRegion;
	}

	public String getExpansionAdvise() {
		return this.expansionAdvise;
	}

	public void setExpansionAdvise(String expansionAdvise) {
		this.expansionAdvise = expansionAdvise;
	}

	public String getSidewalkType() {
		return this.sidewalkType;
	}

	public void setSidewalkType(String sidewalkType) {
		this.sidewalkType = sidewalkType;
	}

	public String getSidewalkRegion() {
		return this.sidewalkRegion;
	}

	public void setSidewalkRegion(String sidewalkRegion) {
		this.sidewalkRegion = sidewalkRegion;
	}

	public String getSidewalkAdvise() {
		return this.sidewalkAdvise;
	}

	public void setSidewalkAdvise(String sidewalkAdvise) {
		this.sidewalkAdvise = sidewalkAdvise;
	}

	public String getGuardType() {
		return this.guardType;
	}

	public void setGuardType(String guardType) {
		this.guardType = guardType;
	}

	public String getGuardRegion() {
		return this.guardRegion;
	}

	public void setGuardRegion(String guardRegion) {
		this.guardRegion = guardRegion;
	}

	public String getGuardAdvise() {
		return this.guardAdvise;
	}

	public void setGuardAdvise(String guardAdvise) {
		this.guardAdvise = guardAdvise;
	}

	public String getWaterproofType() {
		return this.waterproofType;
	}

	public void setWaterproofType(String waterproofType) {
		this.waterproofType = waterproofType;
	}

	public String getWaterproofRegion() {
		return this.waterproofRegion;
	}

	public void setWaterproofRegion(String waterproofRegion) {
		this.waterproofRegion = waterproofRegion;
	}

	public String getWaterproofAdvise() {
		return this.waterproofAdvise;
	}

	public void setWaterproofAdvise(String waterproofAdvise) {
		this.waterproofAdvise = waterproofAdvise;
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

	public String getCleanType() {
		return this.cleanType;
	}

	public void setCleanType(String cleanType) {
		this.cleanType = cleanType;
	}

	public String getCleanRegion() {
		return this.cleanRegion;
	}

	public void setCleanRegion(String cleanRegion) {
		this.cleanRegion = cleanRegion;
	}

	public String getCleanAdvise() {
		return this.cleanAdvise;
	}

	public void setCleanAdvise(String cleanAdvise) {
		this.cleanAdvise = cleanAdvise;
	}

	public String getAbutmentType() {
		return this.abutmentType;
	}

	public void setAbutmentType(String abutmentType) {
		this.abutmentType = abutmentType;
	}

	public String getAbutmentRegion() {
		return this.abutmentRegion;
	}

	public void setAbutmentRegion(String abutmentRegion) {
		this.abutmentRegion = abutmentRegion;
	}

	public String getAbutmentAdvise() {
		return this.abutmentAdvise;
	}

	public void setAbutmentAdvise(String abutmentAdvise) {
		this.abutmentAdvise = abutmentAdvise;
	}

	public String getPierType() {
		return this.pierType;
	}

	public void setPierType(String pierType) {
		this.pierType = pierType;
	}

	public String getPierRegion() {
		return this.pierRegion;
	}

	public void setPierRegion(String pierRegion) {
		this.pierRegion = pierRegion;
	}

	public String getPieAdvise() {
		return this.pieAdvise;
	}

	public void setPieAdvise(String pieAdvise) {
		this.pieAdvise = pieAdvise;
	}

	public String getFoundationType() {
		return this.foundationType;
	}

	public void setFoundationType(String foundationType) {
		this.foundationType = foundationType;
	}

	public String getFoundationRegion() {
		return this.foundationRegion;
	}

	public void setFoundationRegion(String foundationRegion) {
		this.foundationRegion = foundationRegion;
	}

	public String getFoundationAdvise() {
		return this.foundationAdvise;
	}

	public void setFoundationAdvise(String foundationAdvise) {
		this.foundationAdvise = foundationAdvise;
	}

	public String getSupportsType() {
		return this.supportsType;
	}

	public void setSupportsType(String supportsType) {
		this.supportsType = supportsType;
	}

	public String getSupportsRegion() {
		return this.supportsRegion;
	}

	public void setSupportsRegion(String supportsRegion) {
		this.supportsRegion = supportsRegion;
	}

	public String getSupportsAdvise() {
		return this.supportsAdvise;
	}

	public void setSupportsAdvise(String supportsAdvise) {
		this.supportsAdvise = supportsAdvise;
	}

	public String getSuperstructureType() {
		return this.superstructureType;
	}

	public void setSuperstructureType(String superstructureType) {
		this.superstructureType = superstructureType;
	}

	public String getSuperstructureRegion() {
		return this.superstructureRegion;
	}

	public void setSuperstructureRegion(String superstructureRegion) {
		this.superstructureRegion = superstructureRegion;
	}

	public String getSuperstructureAdvise() {
		return this.superstructureAdvise;
	}

	public void setSuperstructureAdvise(String superstructureAdvise) {
		this.superstructureAdvise = superstructureAdvise;
	}

	public String getApproachType() {
		return this.approachType;
	}

	public void setApproachType(String approachType) {
		this.approachType = approachType;
	}

	public String getApproachRegion() {
		return this.approachRegion;
	}

	public void setApproachRegion(String approachRegion) {
		this.approachRegion = approachRegion;
	}

	public String getApproachAdvise() {
		return this.approachAdvise;
	}

	public void setApproachAdvise(String approachAdvise) {
		this.approachAdvise = approachAdvise;
	}

	public String getSignType() {
		return this.signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignRegion() {
		return this.signRegion;
	}

	public void setSignRegion(String signRegion) {
		this.signRegion = signRegion;
	}

	public String getSignAdvise() {
		return this.signAdvise;
	}

	public void setSignAdvise(String signAdvise) {
		this.signAdvise = signAdvise;
	}

	public String getRegulatingType() {
		return this.regulatingType;
	}

	public void setRegulatingType(String regulatingType) {
		this.regulatingType = regulatingType;
	}

	public String getRegulatingRegion() {
		return this.regulatingRegion;
	}

	public void setRegulatingRegion(String regulatingRegion) {
		this.regulatingRegion = regulatingRegion;
	}

	public String getRegulatingAdvise() {
		return this.regulatingAdvise;
	}

	public void setRegulatingAdvise(String regulatingAdvise) {
		this.regulatingAdvise = regulatingAdvise;
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

	public String getCheckDate() {
		return this.checkDate.substring(0, 10);
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
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

}
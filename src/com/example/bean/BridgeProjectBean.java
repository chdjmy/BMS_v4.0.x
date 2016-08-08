package com.example.bean;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;

@Table(name = "project")
public class BridgeProjectBean {
	@Id
	@Column(name="project_id", lenght = 0, type = "BIGINT" )
	private Integer projectId;
	@Column(name="bridge_code", lenght = 15, type = "CHAR" )
	private String bridgeCode;
	@Column(name="project_date", lenght = 0, type = "DATE" )
	private String projectDate;
	@Column(name="budget_cost", lenght = 0, type = "FLOAT" )
	private Double budgetCost;
	@Column(name="construction_cost", lenght = 0, type = "FLOAT" )
	private Double constructionCost;
	@Column(name="management_cost", lenght = 0, type = "FLOAT" )
	private Double managementCost;
	@Column(name="supervisor_cost", lenght = 0, type = "FLOAT" )
	private Double supervisorCost;
	@Column(name="design_cost", lenght = 0, type = "FLOAT" )
	private Double designCost;
	@Column(name="examine_cost", lenght = 0, type = "FLOAT" )
	private Double examineCost;
	@Column(name="detection_cost", lenght = 0, type = "FLOAT" )
	private Double detectionCost;
	@Column(name="sum_cost", lenght = 0, type = "FLOAT" )
	private Double sumCost;
	@Column(name="build_unit", lenght = 50, type = "NVARCHAR" )
	private String buildUnit;
	@Column(name="design_unit", lenght = 50, type = "NVARCHAR" )
	private String designUnit;
	@Column(name="construction_unit", lenght = 50, type = "NVARCHAR" )
	private String constructionUnit;
	@Column(name="supervision_unit", lenght = 50, type = "NVARCHAR" )
	private String supervisionUnit;
	@Column(name="check_type", lenght = 50, type = "NVARCHAR" )
	private String checkType;
	@Column(name="construct_type", lenght = 50, type = "NVARCHAR" )
	private String constructType;
	@Column(name="construct_reason", lenght = 50, type = "NVARCHAR" )
	private String constructReason;
	@Column(name="project_range", lenght = 50, type = "NVARCHAR" )
	private String projectRange;
	@Column(name="finance_source", lenght = 50, type = "NVARCHAR" )
	private String financeSource;
	@Column(name="quality_evaluation", lenght = 50, type = "NVARCHAR" )
	private String qualityEvaluation;
	@Column(name="replay_name", lenght = 50, type = "NVARCHAR" )
	private String replayName;
	@Column(name="replay_num", lenght = 50, type = "NVARCHAR" )
	private String replayNum;
	@Column(name="replay_date", lenght = 0, type = "DATE" )
	private String replayDate;
	@Column(name="authority_name", lenght = 50, type = "NVARCHAR" )
	private String authorityName;
	@Column(name="authority_num", lenght = 50, type = "NVARCHAR" )
	private String authorityNum;
	@Column(name="make_project_people", lenght = 50, type = "NVARCHAR" )
	private String makeProjectPeople;
	@Column(name="make_project_date", lenght = 0, type = "DATE" )
	private String makeProjectDate;
	@Column(name="start_date", lenght = 0, type = "DATE" )
	private String startDate;
	@Column(name="finish_date", lenght = 0, type = "DATE" )
	private String finishDate;
	@Column(name="acceptance_date", lenght = 0, type = "DATE" )
	private String acceptanceDate;
	@Column(name="completion_date", lenght = 0, type = "DATE" )
	private String completionDate;
	@Column(name="tender_date", lenght = 0, type = "DATE" )
	private String tenderDate;
	@Column(name="project_report", lenght = 1, type = "CHAR" )
	private String projectReport;
	@Column(name="part_project_report", lenght = 1, type = "CHAR" )
	private String partProjectReport;
	@Column(name="ourself_accept", lenght = 1, type = "CHAR" )
	private String ourselfAccept;
	@Column(name="supervision_accept", lenght = 1, type = "CHAR" )
	private String supervisionAccept;
	@Column(name="quality_detection", lenght = 1, type = "CHAR" )
	private String qualityDetection;
	@Column(name="completion_accept", lenght = 1, type = "CHAR" )
	private String completionAccept;
	@Column(name="is_submit", lenght = 1, type = "CHAR" )
	private String isSubmit;
	@Column(name="submit_people", lenght = 50, type = "NVARCHAR" )
	private String submitPeople;
	@Column(name="submit_date", lenght = 0, type = "DATE" )
	private String submitDate;
	
	//ÇÅÁºÃû³ÆºÍID
	private String bridgeId;
	private String bridgeName;
	
	
	
	public String getBridgeId() {
		return bridgeId;
	}
	public void setBridgeId(String bridgeId) {
		this.bridgeId = bridgeId;
	}
	public String getBridgeName() {
		return bridgeName;
	}
	public void setBridgeName(String bridgeName) {
		this.bridgeName = bridgeName;
	}
	
	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getBridgeCode() {
		return bridgeCode;
	}
	public void setBridgeCode(String bridgeCode) {
		this.bridgeCode = bridgeCode;
	}
	public String getProjectDate() {
		return projectDate;
	}
	public void setProjectDate(String projectDate) {
		this.projectDate = projectDate;
	}
	public Double getBudgetCost() {
		return budgetCost;
	}
	public void setBudgetCost(Double budgetCost) {
		this.budgetCost = budgetCost;
	}
	public Double getConstructionCost() {
		return constructionCost;
	}
	public void setConstructionCost(Double constructionCost) {
		this.constructionCost = constructionCost;
	}
	public Double getManagementCost() {
		return managementCost;
	}
	public void setManagementCost(Double managementCost) {
		this.managementCost = managementCost;
	}
	public Double getSupervisorCost() {
		return supervisorCost;
	}
	public void setSupervisorCost(Double supervisorCost) {
		this.supervisorCost = supervisorCost;
	}
	public Double getDesignCost() {
		return designCost;
	}
	public void setDesignCost(Double designCost) {
		this.designCost = designCost;
	}
	public Double getExamineCost() {
		return examineCost;
	}
	public void setExamineCost(Double examineCost) {
		this.examineCost = examineCost;
	}
	public Double getDetectionCost() {
		return detectionCost;
	}
	public void setDetectionCost(Double detectionCost) {
		this.detectionCost = detectionCost;
	}
	public Double getSumCost() {
		return sumCost;
	}
	public void setSumCost(Double sumCost) {
		this.sumCost = sumCost;
	}
	public String getBuildUnit() {
		return buildUnit;
	}
	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}
	public String getDesignUnit() {
		return designUnit;
	}
	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}
	public String getConstructionUnit() {
		return constructionUnit;
	}
	public void setConstructionUnit(String constructionUnit) {
		this.constructionUnit = constructionUnit;
	}
	public String getSupervisionUnit() {
		return supervisionUnit;
	}
	public void setSupervisionUnit(String supervisionUnit) {
		this.supervisionUnit = supervisionUnit;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
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
	public String getReplayName() {
		return replayName;
	}
	public void setReplayName(String replayName) {
		this.replayName = replayName;
	}
	public String getReplayNum() {
		return replayNum;
	}
	public void setReplayNum(String replayNum) {
		this.replayNum = replayNum;
	}
	public String getReplayDate() {
		return replayDate;
	}
	public void setReplayDate(String replayDate) {
		this.replayDate = replayDate;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public String getAuthorityNum() {
		return authorityNum;
	}
	public void setAuthorityNum(String authorityNum) {
		this.authorityNum = authorityNum;
	}
	public String getMakeProjectPeople() {
		return makeProjectPeople;
	}
	public void setMakeProjectPeople(String makeProjectPeople) {
		this.makeProjectPeople = makeProjectPeople;
	}
	public String getMakeProjectDate() {
		return makeProjectDate;
	}
	public void setMakeProjectDate(String makeProjectDate) {
		this.makeProjectDate = makeProjectDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public String getAcceptanceDate() {
		return acceptanceDate;
	}
	public void setAcceptanceDate(String acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}
	public String getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}
	public String getTenderDate() {
		return tenderDate;
	}
	public void setTenderDate(String tenderDate) {
		this.tenderDate = tenderDate;
	}
	public String getProjectReport() {
		return projectReport;
	}
	public void setProjectReport(String projectReport) {
		this.projectReport = projectReport;
	}
	public String getPartProjectReport() {
		return partProjectReport;
	}
	public void setPartProjectReport(String partProjectReport) {
		this.partProjectReport = partProjectReport;
	}
	public String getOurselfAccept() {
		return ourselfAccept;
	}
	public void setOurselfAccept(String ourselfAccept) {
		this.ourselfAccept = ourselfAccept;
	}
	public String getSupervisionAccept() {
		return supervisionAccept;
	}
	public void setSupervisionAccept(String supervisionAccept) {
		this.supervisionAccept = supervisionAccept;
	}
	public String getQualityDetection() {
		return qualityDetection;
	}
	public void setQualityDetection(String qualityDetection) {
		this.qualityDetection = qualityDetection;
	}
	public String getCompletionAccept() {
		return completionAccept;
	}
	public void setCompletionAccept(String completionAccept) {
		this.completionAccept = completionAccept;
	}
	public String getIsSubmit() {
		return isSubmit;
	}
	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}
	public String getSubmitPeople() {
		return submitPeople;
	}
	public void setSubmitPeople(String submitPeople) {
		this.submitPeople = submitPeople;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	
	
}

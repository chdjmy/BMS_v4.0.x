package com.example.bean;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;

@Table(name="maintain_task")
public class MaintainTaskBean {
	@Id
	@Column(name="maintain_task_id", lenght = 0, type = "BIGINT" )
	private Integer maintainTaskId;
	@Column(name="maintain_code", lenght = 50, type = "VARCHAR" )
	private String maintainCode;
	@Column(name="task_type", lenght = 1, type = "CHAR" )
	private String taskType;
	@Column(name="bridge_id", lenght = 0, type = "INT" )
	private Integer bridgeId;
	private String bridgeName;//用来保存桥梁名称，不对应数据库的字段
	
	@Column(name="stake", lenght = 50, type = "CHAR" )
	private String stake;
	@Column(name="disease_name", lenght = 100, type = "VARCHAR" )
	private String diseaseName;
	@Column(name="disease_location", lenght = 50, type = "VARCHAR" )
	private String diseaseLocation;
	@Column(name="disease_quantity", lenght = 0, type = "FLOAT" )
	private Double diseaseQuantity;
	@Column(name="disease_unit", lenght = 10, type = "VARCHAR" )
	private String diseaseUnit;
	@Column(name="maintain_plan", lenght = 256, type = "VARCHAR" )
	private String maintainPlan;
	@Column(name="planner", lenght = 50, type = "VARCHAR" )
	private String planner;	
	@Column(name="submit_date", lenght = 0, type = "DATE" )
	private String submitDate;
	@Column(name="isupload", lenght = 1, type = "CHAR" )
	private String isupload;
	@Column(name="bak", lenght = 1024, type = "VARCHAR" )
	private String bak;
	@Column(name="isverify", lenght = 1, type = "CHAR" )
	private String isverify;
	@Column(name="verifier", lenght = 50, type = "VARCHAR" )
	private String verifier;
	@Column(name="verifytime", lenght = 0, type = "DATE" )
	private String verifytime;
	@Column(name="maintain_date", lenght = 0, type = "DATE" )
	private String maintainDate;
	@Column(name="isold", lenght = 1, type = "CHAR" )
	private String isold;
	@Column(name="verify_suggestion", lenght = 1024, type = "VARCHAR" )
	private String verifySuggestion;
	
	@Column(name="department_name", lenght = 256, type = "VARCHAR" )
	private String departmentName;
	@Column(name="check_time", lenght = 0, type = "DATE" )
	private String checkTime;
	@Column(name="work_project_quality", lenght = 1024, type = "VARCHAR" )
	private String workProjectQuality;
	@Column(name="work_area_layout", lenght = 1024, type = "VARCHAR" )
	private String workAreaLayout;
	@Column(name="headman", lenght = 50, type = "VARCHAR" )
	private String headman;
	@Column(name="member", lenght = 256, type = "VARCHAR" )
	private String member;
	@Column(name="isgrade", lenght = 1, type = "CHAR" )
	private String isgrade;
	@Column(name="check_pic", lenght = 50, type = "VARCHAR" )
	private String checkPic;
	@Column(name="ischecked", lenght = 1, type = "CHAR" )
	private String ischecked;
	@Column(name="photo_addr", lenght = 255, type = "NVARCHAR" )
	private String photoAddr;
	
	@Column(name="submit_form", lenght = 1, type = "CHAR" )
	private String submitForm;

	
	public String getPhotoAddr() {
		return photoAddr;
	}
	public void setPhotoAddr(String photoAddr) {
		this.photoAddr = photoAddr;
	}
	public String getBridgeName() {
		return bridgeName;
	}
	public void setBridgeName(String bridgeName) {
		this.bridgeName = bridgeName;
	}
	
	public Integer getMaintainTaskId() {
		return maintainTaskId;
	}
	public void setMaintainTaskId(Integer maintainTaskId) {
		this.maintainTaskId = maintainTaskId;
	}
	public String getMaintainCode() {
		return maintainCode;
	}
	public void setMaintainCode(String maintainCode) {
		this.maintainCode = maintainCode;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public Integer getBridgeId() {
		return bridgeId;
	}
	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}
	public String getStake() {
		return stake;
	}
	public void setStake(String stake) {
		this.stake = stake;
	}
	public String getDiseaseName() {
		return diseaseName;
	}
	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	public String getDiseaseLocation() {
		return diseaseLocation;
	}
	public void setDiseaseLocation(String diseaseLocation) {
		this.diseaseLocation = diseaseLocation;
	}
	public Double getDiseaseQuantity() {
		return diseaseQuantity;
	}
	public void setDiseaseQuantity(Double diseaseQuantity) {
		this.diseaseQuantity = diseaseQuantity;
	}
	public String getDiseaseUnit() {
		return diseaseUnit;
	}
	public void setDiseaseUnit(String diseaseUnit) {
		this.diseaseUnit = diseaseUnit;
	}
	public String getMaintainPlan() {
		return maintainPlan;
	}
	public void setMaintainPlan(String maintainPlan) {
		this.maintainPlan = maintainPlan;
	}
	public String getPlanner() {
		return planner;
	}
	public void setPlanner(String planner) {
		this.planner = planner;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getIsupload() {
		return isupload;
	}
	public void setIsupload(String isupload) {
		this.isupload = isupload;
	}
	public String getBak() {
		return bak;
	}
	public void setBak(String bak) {
		this.bak = bak;
	}
	public String getIsverify() {
		return isverify;
	}
	public void setIsverify(String isverify) {
		this.isverify = isverify;
	}
	public String getVerifier() {
		return verifier;
	}
	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
	public String getVerifytime() {
		return verifytime;
	}
	public void setVerifytime(String verifytime) {
		this.verifytime = verifytime;
	}
	public String getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(String maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getIsold() {
		return isold;
	}
	public void setIsold(String isold) {
		this.isold = isold;
	}
	public String getVerifySuggestion() {
		return verifySuggestion;
	}
	public void setVerifySuggestion(String verifySuggestion) {
		this.verifySuggestion = verifySuggestion;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getWorkProjectQuality() {
		return workProjectQuality;
	}
	public void setWorkProjectQuality(String workProjectQuality) {
		this.workProjectQuality = workProjectQuality;
	}
	public String getWorkAreaLayout() {
		return workAreaLayout;
	}
	public void setWorkAreaLayout(String workAreaLayout) {
		this.workAreaLayout = workAreaLayout;
	}
	public String getHeadman() {
		return headman;
	}
	public void setHeadman(String headman) {
		this.headman = headman;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public String getIsgrade() {
		return isgrade;
	}
	public void setIsgrade(String isgrade) {
		this.isgrade = isgrade;
	}
	public String getCheckPic() {
		return checkPic;
	}
	public void setCheckPic(String checkPic) {
		this.checkPic = checkPic;
	}
	public String getIschecked() {
		return ischecked;
	}
	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}
	public String getSubmitForm() {
		return submitForm;
	}
	public void setSubmitForm(String submitForm) {
		this.submitForm = submitForm;
	}
	
	
	
	
}

package com.example.bean;

public class DataUploadItem {
	private int dataType;//1-桥梁经常性检查  2-涵洞经常性检查  3-维修计划提交
	private String dataId;
	private String bridgeCode;
	private String bridgeName;
	private String checkDate;
	private String noter;
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getBridgeCode() {
		return bridgeCode;
	}
	public void setBridgeCode(String bridgeCode) {
		this.bridgeCode = bridgeCode;
	}
	public String getBridgeName() {
		return bridgeName;
	}
	public void setBridgeName(String bridgeName) {
		this.bridgeName = bridgeName;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getNoter() {
		return noter;
	}
	public void setNoter(String noter) {
		this.noter = noter;
	}
}

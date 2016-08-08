package com.example.bean;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;

/**
 * 材料信息表
 * @author JMY
 *
 */
@Table(name="material")
public class MaterialBean {
	@Id
	@Column(name="material_id", lenght = 0, type = "BIGINT" )
	public String materialId;
	
	@Column(name="material_name", lenght = 50, type = "VARCHAR" )
	public String materialName;
	
	@Column(name="material_unit", lenght = 10, type = "VARCHAR" )
	public String materialUnit;
	
	@Column(name="bak", lenght = 1024, type = "VARCHAR" )
	public String bak;
	
	@Column(name="is_upload", lenght = 1, type = "CHAR" )
	public String isUpload;
	
	
	//用来显示材料列表
	private Double materialPlannedQuantity;
	private Double materialFinishQuantity;
	
	public Double getMaterialPlannedQuantity() {
		return materialPlannedQuantity;
	}

	public void setMaterialPlannedQuantity(Double materialPlannedQuantity) {
		this.materialPlannedQuantity = materialPlannedQuantity;
	}

	public Double getMaterialFinishQuantity() {
		return materialFinishQuantity;
	}

	public void setMaterialFinishQuantity(Double materialFinishQuantity) {
		this.materialFinishQuantity = materialFinishQuantity;
	}
	

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	public String getBak() {
		return bak;
	}

	public void setBak(String bak) {
		this.bak = bak;
	}

	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}
	
	
	
	
	
}

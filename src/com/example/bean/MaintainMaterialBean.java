package com.example.bean;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;

@Table(name="maintain_material")
public class MaintainMaterialBean {
	
	@Id
	@Column(name="maintain_material_id", lenght = 0, type = "INT" )
	private Integer maintainMaterialId;
	@Column(name="maintain_task_id", lenght = 0, type = "INT" )
	private Integer maintainTaskId;
	@Column(name="material_id", lenght = 0, type = "INT" )
	private Integer materialId;
	@Column(name="material_planned_quantity", lenght = 0, type = "FLOAT" )
	private Double materialPlannedQuantity;
	@Column(name="material_finish_quantity", lenght = 0, type = "FLOAT" )
	private Double materialFinishQuantity;
	
	public Integer getMaintainMaterialId() {
		return maintainMaterialId;
	}
	public void setMaintainMaterialId(Integer maintainMaterialId) {
		this.maintainMaterialId = maintainMaterialId;
	}
	public Integer getMaintainTaskId() {
		return maintainTaskId;
	}
	public void setMaintainTaskId(Integer maintainTaskId) {
		this.maintainTaskId = maintainTaskId;
	}
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
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
	
	

}

package com.example.bean;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;
@Table(name="IDMAPS")
public class DataUploadIdMapBean {
//	@Id
//	@Column(name="map_id", lenght = 0, type = "INTEGER" )
//	public int mapId;
	@Column(name="data_type", lenght = 0, type = "int" )
	public int dataType;
	@Column(name="client_id", lenght = 0, type = "int" )
	public int clientId;
	@Column(name="server_id", lenght = 0, type = "int" )
	public int serverId;
	
//	public int  getMapId() {
//		return mapId;
//	}
//	public void setMapId(int mapId) {
//		this.mapId = mapId;
//	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	
	
}

package com.example.bean;

import java.sql.Timestamp;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;

@Table(name="member")
public class MemberBean {

	// Fields
	@Id
	@Column(name="member_id", lenght = 0, type = "BIGINT" )
	private Integer memberId;
	@Column(name="username", lenght = 20, type = "VARCHAR" )
	private String username;
	@Column(name="password", lenght = 35, type = "VARCHAR" )
	private String password;
	@Column(name="name", lenght = 20, type = "VARCHAR" )
	private String name;
	@Column(name="dep_id", lenght = 0, type = "INT" )
	private Integer depId;
	@Column(name="postion_id", lenght = 0, type = "INT" )
	private Integer positionId;
	@Column(name="user_code", lenght = 20, type = "VARCHAR" )
	private String userCode;
	@Column(name="name", lenght = 1, type = "CHAR" )
	private String sex;
	@Column(name="id_number", lenght = 32, type = "VARCHAR" )
	private String idNumber;
	@Column(name="birthday", lenght = 0, type = "DATETIME" )
	private String birthday;
	@Column(name="qq", lenght = 11, type = "VARCHAR" )
	private String qq;
	@Column(name="phone", lenght = 12, type = "VARCHAR" )
	private String phone;
	@Column(name="address", lenght = 256, type = "VARCHAR" )
	private String address;
	@Column(name="state", lenght = 1, type = "CHAR" )
	private String state;
	@Column(name="bak", lenght = 1024, type = "VARCHAR" )
	private String bak;
	
	
	
	// Constructors

	/** default constructor */
	public MemberBean() {
	}

	
	

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDepId() {
		return this.depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

//	public String getBirthday() {
//		return this.birthday;
//	}
//
//	public void setBirthday(String birthday) {
//		this.birthday = birthday;
//	}
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBak() {
		return this.bak;
	}

	public void setBak(String bak) {
		this.bak = bak;
	}

	public Integer getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	

	

	

}

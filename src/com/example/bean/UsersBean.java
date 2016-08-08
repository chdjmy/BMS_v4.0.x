package com.example.bean;
import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户信息
 * @author JMY
 * */
@Table(name="USERS")
public class UsersBean{

	// Fields
	@Id
	@Column(name="id", lenght = 0, type = "INT" )
	private String id;
	@Column(name="username", lenght = 20, type = "VARCHAR" )
	private String username;
	@Column(name="password", lenght = 35, type = "VARCHAR" )
	private String password;
	@Column(name="name", lenght = 20, type = "VARCHAR" )
	private String name;
	@Column(name="company_id", lenght = 0, type = "INT" )
	private Short companyId;
	@Column(name="department", lenght = 50, type = "VARCHAR" )
	private String department;
	@Column(name="telephone", lenght = 12, type = "VARCHAR" )
	private String telephone;
	@Column(name="qq", lenght = 11, type = "VARCHAR" )
	private String qq;
	@Column(name="msn", lenght = 20, type = "VARCHAR" )
	private String msn;
	@Column(name="dep_code", lenght = 20, type = "VARCHAR" )
	private String depCode;
	@Column(name="postion_id", lenght = 20, type = "VARCHAR" )
	private String postionId;
	@Column(name="user_code", lenght = 20, type = "VARCHAR" )
	private String userCode;
	@Column(name="sex", lenght = 1, type = "CHAR" )
	private String sex;
	@Column(name="idNumber", lenght = 32, type = "VARCHAR" )
	private String idNumber;
	@Column(name="birthday", lenght = 0, type = "DATE" )
	private String birthday;
	@Column(name="address", lenght = 256, type = "VARCHAR" )
	private String address;
	@Column(name="state", lenght = 1, type = "CHAR" )
	private String state;
	@Column(name="bak", lenght = 1024, type = "VARCHAR" )
	private String bak;
	
	private Set usergroups = new HashSet(0);

	// Constructors

	/** default constructor */
	public UsersBean() {
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Short getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Short companyId) {
		this.companyId = companyId;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return this.msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getDepCode() {
		return this.depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getPostionId() {
		return this.postionId;
	}

	public void setPostionId(String postionId) {
		this.postionId = postionId;
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

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	public Set getUsergroups() {
		return this.usergroups;
	}

	public void setUsergroups(Set usergroups) {
		this.usergroups = usergroups;
	}

}

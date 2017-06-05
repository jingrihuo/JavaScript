package com.classcircle.model;

//create table manager
//(
//   manageraccount       varchar(32) not null,
//   schoolid             int,
//   managerpassword      varchar(200),
//   managertype          varchar(20),
//   managername          varchar(32),
//   primary key (manageraccount)
//);
public class ManagerBean {
	private String managerAccount;
	private int schoolId;
	private String managerPassword;
	private String managerType;
	private String managerName;

	public String getManagerAccount() {
		return managerAccount;
	}

	public void setManagerAccount(String managerAccount) {
		this.managerAccount = managerAccount;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getManagerPassword() {
		return managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}

	public String getManagerType() {
		return managerType;
	}

	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
}

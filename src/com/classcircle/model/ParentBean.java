package com.classcircle.model;

//create table parent
//(
//   parentid             int not null,
//   useraccount          varchar(32),
//   parentname           varchar(32),
//   primary key (parentid)
//);
public class ParentBean {
	private int parentId;
	private String userAccount;
	private String parentName;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}

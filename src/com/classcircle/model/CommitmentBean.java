package com.classcircle.model;

//create table commitment
//(
//   comid                int not null,
//   dynamicid            int,
//   useraccount          varchar(32),
//   comtext              varchar(220),
//   comuserid            varchar(32),
//   primary key (comid)
//);
public class CommitmentBean {
	private int comId;
	private int dynamicId;
	private String userAccount;
	private String comText;
	private String comUserId;
	private String userName;
	private String comUserName;

	public int getComId() {
		return comId;
	}

	public void setComId(int comId) {
		this.comId = comId;
	}

	public int getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(int dynamicId) {
		this.dynamicId = dynamicId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getComText() {
		return comText;
	}

	public void setComText(String comText) {
		this.comText = comText;
	}

	public String getComUserId() {
		return comUserId;
	}

	public void setComUserId(String comUserId) {
		this.comUserId = comUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComUserName() {
		return comUserName;
	}

	public void setComUserName(String comUserName) {
		this.comUserName = comUserName;
	}
}

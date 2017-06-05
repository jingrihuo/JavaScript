package com.classcircle.model;

//create table dynamic
//(
//   dynamicid            int not null,
//   useraccount          varchar(32),
//   classid              varchar(16),
//   dynamictext          varchar(220),
//   dynamicsrc           varchar(1200),
//   dynamicdate          date,
//   dynamicup            varchar(800),
//   primary key (dynamicid)
//);
public class DynamicBean {
	private int dynamicId;
	private String userAccount;
	private String classId;
	private String dynamicText;
	private String dynamicSrc;
	private String dynamicDate;
	private String dynamicUp;

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

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getDynamicText() {
		return dynamicText;
	}

	public void setDynamicText(String dynamicText) {
		this.dynamicText = dynamicText;
	}

	public String getDynamicSrc() {
		return dynamicSrc;
	}

	public void setDynamicSrc(String dynamicSrc) {
		this.dynamicSrc = dynamicSrc;
	}

	public String getDynamicDate() {
		return dynamicDate;
	}

	public void setDynamicDate(String dynamicDate) {
		this.dynamicDate = dynamicDate;
	}

	public String getDynamicUp() {
		return dynamicUp;
	}

	public void setDynamicUp(String dynamicUp) {
		this.dynamicUp = dynamicUp;
	}
}

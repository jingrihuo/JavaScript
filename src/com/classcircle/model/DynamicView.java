package com.classcircle.model;

import java.util.List;

//class.classid classid,
//dynamic.dynamicid,
//dynamic.useraccount,
//dynamic.dynamictext,
//dynamic.dynamicsrc,
//dynamic.dynamicdate,
//dynamic.dynamicup
public class DynamicView {
	private String classId;
	private int dynamicId;
	private String userAccount;
	private String dynamicText;
	private List<String> dynamicSrc;
	private String dynamicDate;
	private List<Up> dynamicUp;
	private List<CommitmentBean> commitment;
	private String userName;
	private String userHeadSrc;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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

	public String getDynamicText() {
		return dynamicText;
	}

	public void setDynamicText(String dynamicText) {
		this.dynamicText = dynamicText;
	}

	public List<String> getDynamicSrc() {
		return dynamicSrc;
	}

	public void setDynamicSrc(List<String> dynamicSrc) {
		this.dynamicSrc = dynamicSrc;
	}

	public String getDynamicDate() {
		return dynamicDate;
	}

	public void setDynamicDate(String dynamicDate) {
		this.dynamicDate = dynamicDate;
	}

	public List<Up> getDynamicUp() {
		return dynamicUp;
	}

	public void setDynamicUp(List<Up> dynamicUp) {
		this.dynamicUp = dynamicUp;
	}

	public List<CommitmentBean> getCommitment() {
		return commitment;
	}

	public void setCommitment(List<CommitmentBean> commitment) {
		this.commitment = commitment;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserHeadSrc() {
		return userHeadSrc;
	}

	public void setUserHeadSrc(String userHeadSrc) {
		this.userHeadSrc = userHeadSrc;
	}
}

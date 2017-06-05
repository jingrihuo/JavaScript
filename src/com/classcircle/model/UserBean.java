package com.classcircle.model;

//create table user
//(
//   useraccount          varchar(32) not null,
//   userpassword         varchar(200),
//   userheadsrc          varchar(200),
//   usercoversrc         varchar(200),
//   usersex              varchar(2),
//   primary key (useraccount)
//);
public class UserBean {
	private String userAccount;
	private String userPassword;
	private String userHeadSrc;
	private String userCoverSrc;
	private String userSex;

	private String userName;
	private String classId;
	private int schoolId;
	private String className;
	private String schoolName;
	private String userType;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserHeadSrc() {
		return userHeadSrc;
	}

	public void setUserHeadSrc(String userHeadSrc) {
		this.userHeadSrc = userHeadSrc;
	}

	public String getUserCoverSrc() {
		return userCoverSrc;
	}

	public void setUserCoverSrc(String userCoverSrc) {
		this.userCoverSrc = userCoverSrc;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}

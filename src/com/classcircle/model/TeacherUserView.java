package com.classcircle.model;

//school.schoolname,
//class.classid classid,
//class.schoolid,
//class.classname,
//teacher.teachername,
//teacher.project,
//user.useraccount useraccount,
//user.userpassword,
//user.userheadsrc,
//user.usercoversrc,
//user.usersex
public class TeacherUserView {
	private String schoolName;
	private String classId;
	private int schoolId;
	private String className;
	private String teacherName;
	private String teacherType;
	private String project;
	private String userAccount;
	private String userPassword;
	private String userHeadSrc;
	private String userCoverSrc;
	private String userSex;

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

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherType() {
		return teacherType;
	}

	public void setTeacherType(String teacherType) {
		this.teacherType = teacherType;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

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

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
}

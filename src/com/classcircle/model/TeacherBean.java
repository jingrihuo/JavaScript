package com.classcircle.model;

//create table teacher
//(
//   teacherid            varchar(16) not null,
//   useraccount          varchar(32),
//   teachername          varchar(32),
//   project              varchar(32),
//   primary key (teacherid)
//);
public class TeacherBean {
	private String teacherId;
	private String userAccount;
	private String teacherName;
	private String project;

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
}

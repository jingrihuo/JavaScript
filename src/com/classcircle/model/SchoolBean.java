package com.classcircle.model;

//create table school
//(
//   schoolid             int not null,
//   schoolname           varchar(64),
//   schooldetail         varchar(200),
//   primary key (schoolid)
//);
public class SchoolBean {
	private int schoolId;
	private String schoolName;
	private String schoolDetail;

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolDetail() {
		return schoolDetail;
	}

	public void setSchoolDetail(String schoolDetail) {
		this.schoolDetail = schoolDetail;
	}
}

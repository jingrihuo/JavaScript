package com.classcircle.model;

//create table class
//(
//   classid              varchar(16) not null,
//   schoolid             int,
//   classname            varchar(64),
//   classdetail          varchar(200),
//   primary key (classid)
//);
public class ClassBean {
	private String classId;
	private int schoolId;
	private String className;
	private String classDetail;

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

	public String getClassDetail() {
		return classDetail;
	}

	public void setClassDetail(String classDetail) {
		this.classDetail = classDetail;
	}

	@Override
	public String toString() {
		return "ClassBean [classId=" + classId + ", schoolId=" + schoolId + ", className=" + className
				+ ", classDetail=" + classDetail + "]";
	}
	
}

package com.classcircle.model;

//create table TeacherOfClass
//(
//   classid              varchar(16) not null,
//   teacherid            varchar(16) not null,
//	 teachertype          varchar(32),
//   primary key (classid, teacherid)
//);
public class TeacherOfClassBean {
	private String classId;
	private String teacherId;
	private String teacherType;
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherType() {
		return teacherType;
	}

	public void setTeacherType(String teacherType) {
		this.teacherType = teacherType;
	}
}

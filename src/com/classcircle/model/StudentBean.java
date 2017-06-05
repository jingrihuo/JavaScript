package com.classcircle.model;

//create table student
//(
//   studentid            varchar(16) not null,
//   classid              varchar(16),
//   studentname          varchar(32),
//   studentsex           varchar(2),
//   primary key (studentid)
//);
public class StudentBean {
	private String studentId;
	private String classId;
	private String studentName;
	private String studentSex;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentSex() {
		return studentSex;
	}

	public void setStudentSex(String studentSex) {
		this.studentSex = studentSex;
	}
}

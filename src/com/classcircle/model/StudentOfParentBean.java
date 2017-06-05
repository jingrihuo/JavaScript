package com.classcircle.model;

//create table StudentOfParent
//(
//   studentid            varchar(16) not null,
//   parentid             int not null,
//   primary key (studentid, parentid)
//);
public class StudentOfParentBean {
	private String studentId;
	private int parentId;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
}

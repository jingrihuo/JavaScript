package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.StudentBean;
import com.classcircle.tools.DBHelper;

//create table student
//(
// studentid            varchar(16) not null,
// classid              varchar(16),
// studentname          varchar(32),
// studentsex           varchar(2),
// primary key (studentid)
//);
public class StudentDAO {
	public int createStudent(StudentBean student) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into student(studentid, classid, studentname, studentsex) values(?, ?, ?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, student.getStudentId());
			pst.setString(2, student.getClassId());
			pst.setString(3, student.getStudentName());
			pst.setString(4, student.getStudentSex());
			result = pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return result;
	}

	public int deleteStudent(StudentBean student) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from student where studentid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, student.getStudentId());
			result = pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return result;
	}

	public int updateStudent(StudentBean student) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "update student set classid=?,studentname=? where studentid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, student.getClassId());
			pst.setString(2, student.getStudentName());
			pst.setString(3, student.getStudentId());
			result = pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return result;
	}

	public StudentBean getStudent(String studentId) {
		StudentBean student = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select studentid,classid,studentname,studentsex from student where studentid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, studentId);
			rs = pst.executeQuery();
			if (rs.next()) {
				student = new StudentBean();
				student.setStudentId(rs.getString(1));
				student.setClassId(rs.getString(2));
				student.setStudentName(rs.getString(3));
				student.setStudentSex(rs.getString(4));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return student;
	}

	public List<StudentBean> getStudentByClassId(String classId) {
		List<StudentBean> result = new ArrayList<StudentBean>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select studentid from student where classid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, classId);
			rs = pst.executeQuery();
			while (rs.next()) {
				StudentBean temp = new StudentBean();
				temp.setStudentId(rs.getString(1));
				result.add(temp);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return result;
	}
}

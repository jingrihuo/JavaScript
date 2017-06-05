package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.classcircle.model.TeacherBean;
import com.classcircle.tools.DBHelper;

//create table teacher
//(
// teacherid            varchar(16) not null,
// useraccount          varchar(32),
// teachername          varchar(32),
// project              varchar(32),
// primary key (teacherid)
//);
public class TeacherDAO {
	public int createTeacher(TeacherBean teacher) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into teacher(teacherid, useraccount, teachername, project) values(?, ?, ?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, teacher.getTeacherId());
			pst.setString(2, teacher.getUserAccount());
			pst.setString(3, teacher.getTeacherName());
			pst.setString(4, teacher.getProject());
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

	public int deleteTeacher(TeacherBean teacher) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from teacher where teacherid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, teacher.getTeacherId());
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

	public int updateTeacher(TeacherBean teacher) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "update teacher set useraccount=?,teachername=?,project=? where teacherid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, teacher.getUserAccount());
			pst.setString(2, teacher.getTeacherName());
			pst.setString(3, teacher.getProject());
			pst.setString(4, teacher.getTeacherId());
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

	public TeacherBean getTeacher(String teacherId) {
		TeacherBean teacher = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select teacherid,useraccount,teachername,project from teacher where teacherid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, teacherId);
			rs = pst.executeQuery();
			if (rs.next()) {
				teacher = new TeacherBean();
				teacher.setTeacherId(rs.getString(1));
				teacher.setUserAccount(rs.getString(2));
				teacher.setTeacherName(rs.getString(3));
				teacher.setProject(rs.getString(4));
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
		return teacher;
	}
}

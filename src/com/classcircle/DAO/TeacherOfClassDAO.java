package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.TeacherOfClassBean;
import com.classcircle.tools.DBHelper;

//create table TeacherOfClass
//(
// classid              varchar(16) not null,
// teacherid            varchar(16) not null,
// teachertype          varchar(32),
// primary key (classid, teacherid)
//);
public class TeacherOfClassDAO {
	public int createTeacherOfClass(TeacherOfClassBean tocb) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into teacherofclass(classid, teacherid, teachertype) values(?, ?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, tocb.getClassId());
			pst.setString(2, tocb.getTeacherId());
			pst.setString(3, tocb.getTeacherType());
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

	public int deleteTeacherOfClass(TeacherOfClassBean tocb) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from teacherofclass where classid=? and teacherid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, tocb.getClassId());
			pst.setString(2, tocb.getTeacherId());
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

	public boolean checkTeacher(String teacherId, String classId) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from teacherofclass where classid<>? and teacherid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, classId);
			pst.setString(2, teacherId);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = true;
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

	public TeacherOfClassBean getTeacherOfClass(String teacherId, String classId) {
		TeacherOfClassBean tocb = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select classid,teacherid,teachertype from teacherofclass where classid=? and teacherid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, classId);
			pst.setString(2, teacherId);
			rs = pst.executeQuery();
			if (rs.next()) {
				tocb = new TeacherOfClassBean();
				tocb.setClassId(rs.getString(1));
				tocb.setTeacherId(rs.getString(2));
				tocb.setTeacherType(rs.getString(3));
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
		return tocb;
	}

	public List<String> getTeacherIdByClassId(String classId) {
		List<String> result = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select teacherid from teacherofclass where classid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, classId);
			rs = pst.executeQuery();
			while (rs.next()) {
				result.add(rs.getString(1));
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

	public boolean checkRelationByTeacherId(String teacherId) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from teacherofclass where teacherid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, teacherId);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = true;
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

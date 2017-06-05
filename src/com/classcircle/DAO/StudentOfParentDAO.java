package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.StudentOfParentBean;
import com.classcircle.tools.DBHelper;

//create table StudentOfParent
//(
// studentid            varchar(16) not null,
// parentid             int not null,
// primary key (studentid, parentid)
//);
public class StudentOfParentDAO {
	public int createStudentOfParent(StudentOfParentBean sopb) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into studentofparent(parentid, studentid) values(?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, sopb.getParentId());
			pst.setString(2, sopb.getStudentId());
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

	public int deleteStudentOfParent(StudentOfParentBean sopb) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from studentofparent where studentid=? and parentid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, sopb.getStudentId());
			pst.setInt(2, sopb.getParentId());
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

	public StudentOfParentBean getStudentOfParent(String studentId, int parentId) {
		StudentOfParentBean sopb = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select studentid,parentid from studentofparent where studentid=? and parentid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, studentId);
			pst.setInt(2, parentId);
			rs = pst.executeQuery();
			if (rs.next()) {
				sopb = new StudentOfParentBean();
				sopb.setStudentId(rs.getString(1));
				sopb.setParentId(rs.getInt(2));
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
		return sopb;
	}
	
	public List<Integer> getParentIdByStudentId(String StudentId){
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select parentid from studentofparent where studentid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, StudentId);
			rs = pst.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt(1));
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
	
	public boolean checkRelationByStudentId(String StudentId) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select studentid,parentid from studentofparent where studentid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, StudentId);
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

	public boolean checkRelationByParentId(int parentId) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select studentid,parentid from studentofparent where parentid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, parentId);
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

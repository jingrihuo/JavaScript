package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.SchoolBean;
import com.classcircle.tools.DBHelper;

//create table school
//(
// schoolid             int not null,
// schoolname           varchar(64),
// schooldetail         varchar(200),
// primary key (schoolid)
//);
public class SchoolDAO {
	public int createSchool(SchoolBean school) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into school(schoolname, schooldetail) values(?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, school.getSchoolName());
			pst.setString(2, school.getSchoolDetail());
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

	public int deleteSchool(SchoolBean school) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from school where schoolid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, school.getSchoolId());
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

	public int updateSchool(SchoolBean school) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "update school set schoolname=?,schooldetail=? where schoolid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, school.getSchoolName());
			pst.setString(2, school.getSchoolDetail());
			pst.setInt(3, school.getSchoolId());
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

	public SchoolBean getSchool(int schoolId) {
		SchoolBean school = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select schoolid,schoolname,schooldetail from school where schoolid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, schoolId);
			rs = pst.executeQuery();
			if (rs.next()) {
				school = new SchoolBean();
				school.setSchoolId(rs.getInt(1));
				school.setSchoolName(rs.getString(2));
				school.setSchoolDetail(rs.getString(3));
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
		return school;
	}

	public List<SchoolBean> searchSchool(String schoolId, String schoolName, String schoolDetail) {
		List<SchoolBean> result = new ArrayList<SchoolBean>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select schoolid,schoolname,schooldetail from school where schoolid like ? and schoolname like ? and schooldetail like ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + schoolId + "%");
			pst.setString(2, "%" + schoolName + "%");
			pst.setString(3, "%" + schoolDetail + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				SchoolBean school = new SchoolBean();
				school.setSchoolId(rs.getInt(1));
				school.setSchoolName(rs.getString(2));
				school.setSchoolDetail(rs.getString(3));
				result.add(school);
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

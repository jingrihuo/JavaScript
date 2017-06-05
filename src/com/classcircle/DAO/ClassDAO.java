package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.ClassBean;
import com.classcircle.tools.DBHelper;

//create table class
//(
// classid              varchar(16) not null,
// schoolid             int,
// classname            varchar(64),
// classdetail          varchar(200),
// primary key (classid)
//);
public class ClassDAO {
	public int createClass(ClassBean classBean) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into class(classid, schoolid, classname, classdetail) values(?, ?, ?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, classBean.getClassId());
			pst.setInt(2, classBean.getSchoolId());
			pst.setString(3, classBean.getClassName());
			pst.setString(4, classBean.getClassDetail());
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

	public int deleteClass(ClassBean classBean) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from class where classid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, classBean.getClassId());
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

	public int deleteClass(int schoolId) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from class where schoolid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, schoolId);
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

	public int updateClass(ClassBean classBean) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "update class set classname=?,classdetail=? where classid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, classBean.getClassName());
			pst.setString(2, classBean.getClassDetail());
			pst.setString(3, classBean.getClassId());
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
	
	public List<String> getClassIdBySchoolId(int schoolId){
		List<String> result = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select classid from class where schoolid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, schoolId);
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

	public ClassBean getClass(String classId) {
		ClassBean classBean = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select classid,schoolid,classname,classdetail from class where classid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, classId);
			rs = pst.executeQuery();
			if (rs.next()) {
				classBean = new ClassBean();
				classBean.setClassId(rs.getString(1));
				classBean.setSchoolId(rs.getInt(2));
				classBean.setClassName(rs.getString(3));
				classBean.setClassDetail(rs.getString(4));
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
		return classBean;
	}

	public List<ClassBean> searchClass(String classId, String className, int schoolId) {
		List<ClassBean> result = new ArrayList<ClassBean>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select classid,schoolid,classname,classdetail from class where schoolid=? and classid like ? and classname like ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, schoolId);
			pst.setString(2, "%" + classId + "%");
			pst.setString(3, "%" + className + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				ClassBean classBean = new ClassBean();
				classBean.setClassId(rs.getString(1));
				classBean.setSchoolId(rs.getInt(2));
				classBean.setClassName(rs.getString(3));
				classBean.setClassDetail(rs.getString(4));
				result.add(classBean);
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
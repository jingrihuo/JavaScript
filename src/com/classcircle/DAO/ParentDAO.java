package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.classcircle.model.ParentBean;
import com.classcircle.tools.DBHelper;

//create table parent
//(
// parentid             int not null,
// useraccount          varchar(32),
// parentname           varchar(32),
// primary key (parentid)
//);
public class ParentDAO {
	public int createParent(ParentBean parent) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into parent(useraccount, parentname) values(?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, parent.getUserAccount());
			pst.setString(2, parent.getParentName());
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

	public int deleteParent(ParentBean parent) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from parent where parentid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, parent.getParentId());
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

	public int updateParent(ParentBean parent) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "update parent set useraccount=?,parentname=? where parentid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, parent.getUserAccount());
			pst.setString(2, parent.getParentName());
			pst.setInt(3, parent.getParentId());
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

	public ParentBean getParent(String userAccount) {
		ParentBean parent = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select parentid,useraccount,parentname from parent where useraccount=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userAccount);
			rs = pst.executeQuery();
			if (rs.next()) {
				parent = new ParentBean();
				parent.setParentId(rs.getInt(1));
				parent.setUserAccount(rs.getString(2));
				parent.setParentName(rs.getString(3));
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
		return parent;
	}
	
	public ParentBean getParentByParentId(int parentId) {
		ParentBean parent = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select parentid,useraccount,parentname from parent where parentid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, parentId);
			rs = pst.executeQuery();
			if (rs.next()) {
				parent = new ParentBean();
				parent.setParentId(rs.getInt(1));
				parent.setUserAccount(rs.getString(2));
				parent.setParentName(rs.getString(3));
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
		return parent;
	}
}

package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.DynamicBean;
import com.classcircle.tools.DBHelper;

//create table dynamic
//(
// dynamicid            int not null,
// useraccount          varchar(32),
// classid              varchar(16),
// dynamictext          varchar(220),
// dynamicsrc           varchar(1200),
// dynamicdate          date,
// dynamicup            varchar(800),
// primary key (dynamicid)
//);
public class DynamicDAO {
	public int createDynamic(DynamicBean dynamic) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into dynamic(useraccount, classid, dynamictext, dynamicsrc, dynamicdate, dynamicup) values(?, ?, ?, ?, ?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, dynamic.getUserAccount());
			pst.setString(2, dynamic.getClassId());
			pst.setString(3, dynamic.getDynamicText());
			pst.setString(4, dynamic.getDynamicSrc());
			pst.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			pst.setString(6, dynamic.getDynamicUp());
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

	public int deleteDynamic(DynamicBean dynamic) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from dynamic where dynamicid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, dynamic.getDynamicId());
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
	
	public int updateDynamic(DynamicBean dynamic) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "update dynamic set dynamicup=? where dynamicid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, dynamic.getDynamicUp());
			pst.setInt(2, dynamic.getDynamicId());
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
	
	public List<String> getDynamicIdByClassId(String classId){
		List<String> result = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select dynamicid from dynamic where classid=?";
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

	public List<String> getDynamicIdByUserAccount(String userAccount){
		List<String> result = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select dynamicid from dynamic where useraccount=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userAccount);
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
	
	public DynamicBean getDynamic(int dynamicId) {
		DynamicBean dynamic = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select dynamicid,useraccount,classid,dynamictext,dynamicsrc,dynamicdate,dynamicup from dynamic where dynamicid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, dynamicId);
			rs = pst.executeQuery();
			if (rs.next()) {
				dynamic = new DynamicBean();
				dynamic.setDynamicId(rs.getInt(1));
				dynamic.setUserAccount(rs.getString(2));
				dynamic.setClassId(rs.getString(3));
				dynamic.setDynamicText(rs.getString(4));
				dynamic.setDynamicSrc(rs.getString(5));
				Timestamp time = rs.getTimestamp(6);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				dynamic.setDynamicDate(sdf.parse(time.toString()).toString());
				dynamic.setDynamicUp(rs.getString(7));
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
		return dynamic;
	}
}

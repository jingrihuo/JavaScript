package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.CommitmentBean;
import com.classcircle.tools.DBHelper;

//create table commitment
//(
// comid                int not null,
// dynamicid            int,
// useraccount          varchar(32),
// comtext              varchar(220),
// comuserid            varchar(32),
// primary key (comid)
//);
public class CommitmentDAO {
	public int createCommitment(CommitmentBean commitment) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into commitment(dynamicid, useraccount, comtext, comuserid) values(?, ?, ?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, commitment.getDynamicId());
			pst.setString(2, commitment.getUserAccount());
			pst.setString(3, commitment.getComText());
			pst.setString(4, commitment.getComUserId());
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

	public int deleteCommitment(CommitmentBean commitment) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from commitment where comid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, commitment.getComId());
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

	public int deleteCommitmentByDynamicId(int DynamicId) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from commitment where dynamicid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, DynamicId);
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

	public CommitmentBean getCommitment(int comId) {
		CommitmentBean commitment = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select comid,dynamicid,useraccount,comtext,comuserid from commitment where comid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, comId);
			rs = pst.executeQuery();
			if (rs.next()) {
				commitment = new CommitmentBean();
				commitment.setComId(rs.getInt(1));
				commitment.setDynamicId(rs.getInt(2));
				commitment.setUserAccount(rs.getString(3));
				commitment.setComText(rs.getString(4));
				commitment.setComUserId(rs.getString(5));
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
		return commitment;
	}

	public List<CommitmentBean> getCommitmentList(int dynamicId) {
		List<CommitmentBean> result = new ArrayList<CommitmentBean>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select comid,useraccount,comtext,comuserid from commitment where dynamicid=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, dynamicId);
			rs = pst.executeQuery();
			while (rs.next()) {
				CommitmentBean com = new CommitmentBean();
				com.setComId(rs.getInt(1));
				com.setUserAccount(rs.getString(2));
				com.setComText(rs.getString(3));
				com.setComUserId(rs.getString(4));
				result.add(com);
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
	
	public CommitmentBean getCommitment(int dynamicId, String userAccount){
		CommitmentBean commitment = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select comid,dynamicid,useraccount,comtext,comuserid from commitment where comid>=ALL(select comid from commitment where dynamicid=? and useraccount=?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, dynamicId);
			pst.setString(2, userAccount);
			rs = pst.executeQuery();
			if (rs.next()) {
				commitment = new CommitmentBean();
				commitment.setComId(rs.getInt(1));
				commitment.setDynamicId(rs.getInt(2));
				commitment.setUserAccount(rs.getString(3));
				commitment.setComText(rs.getString(4));
				commitment.setComUserId(rs.getString(5));
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
		return commitment;
	}
}

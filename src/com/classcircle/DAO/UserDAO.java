package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.classcircle.model.UserBean;
import com.classcircle.tools.DBHelper;

//create table user
//(
// useraccount          varchar(32) not null,
// userpassword         varchar(200),
// userheadsrc          varchar(200),
// usercoversrc         varchar(200),
// usersex              varchar(2),
// primary key (useraccount)
//);
public class UserDAO {
	public int createUser(UserBean user) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into user(useraccount, userpassword, userheadsrc, usercoversrc, usersex) values(?, ?, ?, ?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserAccount());
			pst.setString(2, user.getUserPassword());
			pst.setString(3, user.getUserHeadSrc());
			pst.setString(4, user.getUserCoverSrc());
			pst.setString(5, user.getUserSex());
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

	public int deleteUser(UserBean user) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from user where useraccount=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserAccount());
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

	public int updateUser(UserBean user) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "update user set userpassword=?,userheadsrc=?,usercoversrc=? where useraccount=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserPassword());
			pst.setString(2, user.getUserHeadSrc());
			pst.setString(3, user.getUserCoverSrc());
			pst.setString(4, user.getUserAccount());
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

	public UserBean getUser(String userAccount) {
		UserBean user = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select useraccount,userpassword,userheadsrc,usercoversrc,usersex from user where useraccount=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userAccount);
			rs = pst.executeQuery();
			if (rs.next()) {
				user = new UserBean();
				user.setUserAccount(rs.getString(1));
				user.setUserPassword(rs.getString(2));
				user.setUserHeadSrc(rs.getString(3));
				user.setUserCoverSrc(rs.getString(4));
				user.setUserSex(rs.getString(5));
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
		return user;
	}

	public boolean checkUser(String userAccount, String userPassword) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from user where useraccount=? and userpassword=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userAccount);
			pst.setString(2, userPassword);
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

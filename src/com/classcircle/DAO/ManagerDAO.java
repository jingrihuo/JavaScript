package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.ManagerBean;
import com.classcircle.tools.DBHelper;

//create table manager
//(
//   manageraccount       varchar(32) not null,
//   schoolid             int,
//   managerpassword      varchar(200),
//   managertype          varchar(20),
//   managername          varchar(32),
//   primary key (manageraccount)
//);
public class ManagerDAO {
	public int createManager(ManagerBean manager) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into manager(manageraccount, schoolid, managerpassword, managertype, managername) values(?, ?, ?, ?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, manager.getManagerAccount());
			pst.setInt(2, manager.getSchoolId());
			pst.setString(3, manager.getManagerPassword());
			pst.setString(4, manager.getManagerType());
			pst.setString(5, manager.getManagerName());
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

	public int deleteManager(int schoolId) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from manager where schoolid=?";
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

	public int deleteManager(ManagerBean manager) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "delete from manager where manageraccount=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, manager.getManagerAccount());
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

	public int updateManager(ManagerBean manager) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "update manager set managerpassword=?,managername=? where manageraccount=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, manager.getManagerPassword());
			pst.setString(2, manager.getManagerName());
			pst.setString(3, manager.getManagerAccount());
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

	public ManagerBean getManager(String managerAccount) {
		ManagerBean manager = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select manageraccount,schoolid,managerpassword,managertype,managername from manager where manageraccount=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, managerAccount);
			rs = pst.executeQuery();
			if (rs.next()) {
				manager = new ManagerBean();
				manager.setManagerAccount(rs.getString(1));
				manager.setSchoolId(rs.getInt(2));
				manager.setManagerPassword(rs.getString(3));
				manager.setManagerType(rs.getString(4));
				manager.setManagerName(rs.getString(5));
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
		return manager;
	}

	public boolean checkManager(ManagerBean manager) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from manager where manageraccount=? and managerpassword=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, manager.getManagerAccount());
			pst.setString(2, manager.getManagerPassword());
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

	public List<ManagerBean> searchManager(String schoolId, String managerAccount, String managerName) {
		List<ManagerBean> result = new ArrayList<ManagerBean>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select manageraccount,schoolid,managerpassword,managertype,managername from manager where schoolid like ? and manageraccount like ? and managername like ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + schoolId + "%");
			pst.setString(2, "%" + managerAccount + "%");
			pst.setString(3, "%" + managerName + "%");
			System.out.println(pst.toString());
			rs = pst.executeQuery();
			while (rs.next()) {
				ManagerBean manager = new ManagerBean();
				manager.setManagerAccount(rs.getString(1));
				manager.setSchoolId(rs.getInt(2));
				manager.setManagerPassword(rs.getString(3));
				manager.setManagerType(rs.getString(4));
				manager.setManagerName(rs.getString(5));
				result.add(manager);
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

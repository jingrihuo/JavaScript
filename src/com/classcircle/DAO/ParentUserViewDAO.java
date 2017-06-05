package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.ParentUserView;
import com.classcircle.tools.DBHelper;

//school.schoolname,
//class.classid classid,
//class.schoolid schoolid,
//class.classname,
//student.studentname,
//parent.parentname,
//user.useraccount useraccount,
//user.userpassword,
//user.userheadsrc,
//user.usercoversrc,
//user.usersex
public class ParentUserViewDAO {
	public List<ParentUserView> getParentUser(String userAccount){
		List<ParentUserView> result = new ArrayList<ParentUserView>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select schoolname,classid,schoolid,classname,studentname,parentname,useraccount,userpassword,userheadsrc,usercoversrc,usersex from parentuserview where useraccount=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userAccount);
			rs = pst.executeQuery();
			while(rs.next()){
				ParentUserView parent = new ParentUserView();
				parent.setSchoolName(rs.getString(1));
				parent.setClassId(rs.getString(2));
				parent.setSchoolId(rs.getInt(3));
				parent.setClassName(rs.getString(4));
				parent.setStudentName(rs.getString(5));
				parent.setParentName(rs.getString(6));
				parent.setUserAccount(rs.getString(7));
				parent.setUserPassword(rs.getString(8));
				parent.setUserHeadSrc(rs.getString(9));
				parent.setUserCoverSrc(rs.getString(10));
				parent.setUserSex(rs.getString(11));
				result.add(parent);
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
	
	public List<ParentUserView> getParentUser(String userAccount, String classId){
		List<ParentUserView> result = new ArrayList<ParentUserView>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select schoolname,classid,schoolid,classname,studentname,parentname,useraccount,userpassword,userheadsrc,usercoversrc,usersex from parentuserview where useraccount=? and classid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userAccount);
			pst.setString(2, classId);
			System.out.println(pst.toString());
			rs = pst.executeQuery();
			while(rs.next()){
				ParentUserView parent = new ParentUserView();
				parent.setSchoolName(rs.getString(1));
				parent.setClassId(rs.getString(2));
				parent.setSchoolId(rs.getInt(3));
				parent.setClassName(rs.getString(4));
				parent.setStudentName(rs.getString(5));
				parent.setParentName(rs.getString(6));
				parent.setUserAccount(rs.getString(7));
				parent.setUserPassword(rs.getString(8));
				parent.setUserHeadSrc(rs.getString(9));
				parent.setUserCoverSrc(rs.getString(10));
				parent.setUserSex(rs.getString(11));
				result.add(parent);
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

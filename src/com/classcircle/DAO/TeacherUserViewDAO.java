package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.TeacherUserView;
import com.classcircle.tools.DBHelper;

//school.schoolname,
//class.classid classid,
//class.schoolid,
//class.classname,
//teacher.teachername,
//teacher.project,
//user.useraccount useraccount,
//user.userpassword,
//user.userheadsrc,
//user.usercoversrc,
//user.usersex
public class TeacherUserViewDAO {
	public List<TeacherUserView> getTeacherUserView(String userAccount){
		List<TeacherUserView> result = new ArrayList<TeacherUserView>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select schoolname,classid,schoolid,classname,teachername,project,useraccount,userpassword,userheadsrc,usercoversrc,usersex from teacheruserview where useraccount=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userAccount);
			rs = pst.executeQuery();
			while(rs.next()){
				TeacherUserView teacher = new TeacherUserView();
				teacher.setSchoolName(rs.getString(1));
				teacher.setClassId(rs.getString(2));
				teacher.setSchoolId(rs.getInt(3));
				teacher.setClassName(rs.getString(4));
				teacher.setTeacherName(rs.getString(5));
				teacher.setProject(rs.getString(6));
				teacher.setUserAccount(rs.getString(7));
				teacher.setUserPassword(rs.getString(8));
				teacher.setUserHeadSrc(rs.getString(9));
				teacher.setUserCoverSrc(rs.getString(10));
				teacher.setUserSex(rs.getString(11));
				result.add(teacher);
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
	
	public List<TeacherUserView> getTeacherUserView(String userAccount, String classId){
		List<TeacherUserView> result = new ArrayList<TeacherUserView>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select schoolname,classid,schoolid,classname,teachername,project,useraccount,userpassword,userheadsrc,usercoversrc,usersex from teacheruserview where useraccount=? and classid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userAccount);
			pst.setString(2, classId);
			System.out.println(pst.toString());
			rs = pst.executeQuery();
			while(rs.next()){
				TeacherUserView teacher = new TeacherUserView();
				teacher.setSchoolName(rs.getString(1));
				teacher.setClassId(rs.getString(2));
				teacher.setSchoolId(rs.getInt(3));
				teacher.setClassName(rs.getString(4));
				teacher.setTeacherName(rs.getString(5));
				teacher.setProject(rs.getString(6));
				teacher.setUserAccount(rs.getString(7));
				teacher.setUserPassword(rs.getString(8));
				teacher.setUserHeadSrc(rs.getString(9));
				teacher.setUserCoverSrc(rs.getString(10));
				teacher.setUserSex(rs.getString(11));
				result.add(teacher);
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

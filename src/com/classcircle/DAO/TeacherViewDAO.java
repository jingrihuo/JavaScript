package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.TeacherView;
import com.classcircle.tools.DBHelper;

//class.classid classid,
//class.schoolid,
//class.classname,
//teacher.teacherid teacherid,
//teacher.useraccount useraccount,
//teacher.teachername,
//teacher.project,
//user.userpassword,
//user.usersex
public class TeacherViewDAO {
	public List<TeacherView> searchTeacher(String teacherId, String teacherName, String project, String classId,
			int schoolId, String className) {
		List<TeacherView> result = new ArrayList<TeacherView>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select classid,schoolid,classname,teacherid,useraccount,teachername,teachertype,project,userpassword,usersex from teacherview where schoolid=? and teacherid like ? and teachername like ? and project like ? and classId like ? and classname like ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, schoolId);
			pst.setString(2, "%" + teacherId + "%");
			pst.setString(3, "%" + teacherName + "%");
			pst.setString(4, "%" + project + "%");
			pst.setString(5, "%" + classId + "%");
			pst.setString(6, "%" + className + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				TeacherView temp = new TeacherView();
				temp.setClassId(rs.getString(1));
				temp.setSchoolId(rs.getInt(2));
				temp.setClassName(rs.getString(3));
				temp.setTeacherId(rs.getString(4));
				temp.setUserAccount(rs.getString(5));
				temp.setTeacherName(rs.getString(6));
				temp.setTeacherType(rs.getString(7));
				temp.setProject(rs.getString(8));
				temp.setUserPassword(rs.getString(9));
				temp.setUserSex(rs.getString(10));
				result.add(temp);
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

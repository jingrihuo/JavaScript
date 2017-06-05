package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.StudentAndParentView;
import com.classcircle.tools.DBHelper;

//class.classid classid,
//class.schoolid,
//class.classname,
//student.studentid studentid,
//student.studentname,
//student.studentsex,
//parent.parentname,
//user.useraccount useraccount,
//user.userpassword,
//user.usersex
public class StudentAndParentViewDAO {
	public List<StudentAndParentView> searchStudentAndParent(int schoolId, String studentId, String classId,
			String parentName, String className) {
		List<StudentAndParentView> result = new ArrayList<StudentAndParentView>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select classid,schoolid,classname,studentid,studentname,studentsex,parentname,useraccount,userpassword,usersex from studentandparentview where schoolid=? and studentid like ? and classid like ? and parentname like ? and classname like ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, schoolId);
			pst.setString(2, "%" + studentId + "%");
			pst.setString(3, "%" + classId + "%");
			pst.setString(4, "%" + parentName + "%");
			pst.setString(5, "%" + className + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				StudentAndParentView temp = new StudentAndParentView();
				temp.setClassId(rs.getString(1));
				temp.setSchoolId(rs.getInt(2));
				temp.setClassName(rs.getString(3));
				temp.setStudentId(rs.getString(4));
				temp.setStudentName(rs.getString(5));
				temp.setStudentSex(rs.getString(6));
				temp.setParentName(rs.getString(7));
				temp.setUserAccount(rs.getString(8));
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

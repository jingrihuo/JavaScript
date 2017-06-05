package com.classcircle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.DynamicView;
import com.classcircle.tools.DBHelper;
import com.classcircle.tools.IdHelper;

//class.classid classid,
//dynamic.dynamicid,
//dynamic.useraccount,
//dynamic.dynamictext,
//dynamic.dynamicsrc,
//dynamic.dynamicdate,
//dynamic.dynamicup
public class DynamicViewDAO {
	// 每次取出的条目
	private final static int count = 20;

	public List<DynamicView> loadDynamic(String classId, int dynamicId) {
		List<DynamicView> result = new ArrayList<DynamicView>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select classid,dynamicid,useraccount,dynamictext,dynamicsrc,dynamicdate,dynamicup,userheadsrc from dynamicview where 1=1";
			if (dynamicId != 0) {
				sql += "and dynamicid<?";
			}
			sql += " and classid=? order by dynamicid desc limit 0,?";
			pst = conn.prepareStatement(sql);
			if (dynamicId != 0) {
				pst.setInt(1, dynamicId);
				pst.setString(2, classId);
				pst.setInt(3, count);
			} else {
				pst.setString(1, classId);
				pst.setInt(2, count);
			}
			System.out.println(pst.toString());
			rs = pst.executeQuery();
			while (rs.next()) {
				DynamicView dynamic = new DynamicView();
				dynamic.setClassId(rs.getString(1));
				dynamic.setDynamicId(rs.getInt(2));
				dynamic.setUserAccount(rs.getString(3));
				dynamic.setDynamicText(rs.getString(4));
				dynamic.setDynamicSrc(IdHelper.dynamicSrcToSrc(rs.getString(5)));
				dynamic.setDynamicDate(rs.getString(6));
				dynamic.setDynamicUp(IdHelper.dynamicUpToAccount(rs.getString(7)));
				dynamic.setUserHeadSrc(rs.getString(8));
				result.add(dynamic);
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

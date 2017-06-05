package com.classcircle.tools;

import java.util.ArrayList;
import java.util.List;

import com.classcircle.DAO.ManagerDAO;
import com.classcircle.model.ManagerBean;
import com.classcircle.model.Up;

public class IdHelper {
	public static String toDBId(String Id, String schoolId) {
		return schoolId + "," + Id;
	}

	public static String toId(String Id) {
		String[] array = Id.split(",", 2);
		return array[1];
	}

	public static String getSchoolId(String managerAccount) {
		ManagerDAO md = new ManagerDAO();
		ManagerBean manager = md.getManager(managerAccount);
		if (manager == null) {
			return "";
		}
		return String.valueOf(manager.getSchoolId());
	}

	public static List<Up> dynamicUpToAccount(String dynamicUp) {
		if (dynamicUp != null) {
			List<Up> result = new ArrayList<Up>();
			String[] array = dynamicUp.split(",");
			for (int i = 0; i < array.length; i++) {
				Up up = new Up();
				up.setUserAccount(array[i]);
				result.add(up);
			}
			return result;
		}
		return new ArrayList<Up>();
	}

	public static String accountToDynamicUp(List<Up> list) {
		String result = "";
		for (Up up : list) {
			result = result + up.getUserAccount() + ",";
		}
		if (result.length() > 0)
			result = result.substring(0, result.length() - 1);
		else
			result = null;
		return result;
	}

	public static List<String> dynamicSrcToSrc(String dynamicSrc) {
		if (dynamicSrc != null && !dynamicSrc.equals("")) {
			List<String> result = new ArrayList<String>();
			String[] array = dynamicSrc.split(",");
			for (int i = 0; i < array.length; i++) {
				result.add(array[i]);
			}
			return result;
		}
		return new ArrayList<String>();
	}
}

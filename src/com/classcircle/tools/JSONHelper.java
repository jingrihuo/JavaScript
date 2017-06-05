package com.classcircle.tools;

import java.util.ArrayList;
import java.util.List;

import com.classcircle.model.ClassBean;
import com.classcircle.model.CommitmentBean;
import com.classcircle.model.DynamicView;
import com.classcircle.model.ManagerBean;
import com.classcircle.model.ParentUserView;
import com.classcircle.model.SchoolBean;
import com.classcircle.model.StudentAndParentView;
import com.classcircle.model.StudentOfParentBean;
import com.classcircle.model.TeacherUserView;
import com.classcircle.model.TeacherView;
import com.classcircle.model.Up;
import com.classcircle.model.UserBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONHelper {
	public static List<UserBean> JSONToUserList(String acceptJSON) {
		// System.out.println(acceptJSON);
		List<UserBean> result = new ArrayList<UserBean>();
		JSONObject jo = new JSONObject();
		jo = JSONObject.fromObject(acceptJSON);
		JSONArray jsonArray = jo.getJSONArray("user");
		for (int i = 0; i < jsonArray.size(); i++) {
			UserBean user = new UserBean();
			JSONObject object = jsonArray.getJSONObject(i);
			user = (UserBean) JSONObject.toBean(object, UserBean.class);
			result.add(user);
		}
		return result;
	}

	public static JSONObject userListToJSON(List<UserBean> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (UserBean user : list) {
			JSONObject object = new JSONObject();
			object = JSONObject.fromObject(user);
			jsonArray.add(object);
		}
		jsonObject.put("user", jsonArray);
		return jsonObject;
	}

	public static List<ClassBean> JSONToClassList(String acceptJSON) {
		// System.out.println(acceptJSON);
		List<ClassBean> result = new ArrayList<ClassBean>();
		JSONObject jo = new JSONObject();
		jo = JSONObject.fromObject(acceptJSON);
		JSONArray jsonArray = jo.getJSONArray("class");
		for (int i = 0; i < jsonArray.size(); i++) {
			ClassBean classBean = new ClassBean();
			JSONObject object = jsonArray.getJSONObject(i);
			classBean = (ClassBean) JSONObject.toBean(object, ClassBean.class);
			result.add(classBean);
		}
		return result;
	}

	public static JSONObject classListToJSON(List<ClassBean> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (ClassBean classBean : list) {
			JSONObject object = new JSONObject();
			object = JSONObject.fromObject(classBean);
			jsonArray.add(object);
		}
		jsonObject.put("class", jsonArray);
		return jsonObject;
	}

	public static List<TeacherView> JSONToTeacherList(String acceptJSON) {
		// System.out.println(acceptJSON);
		List<TeacherView> result = new ArrayList<TeacherView>();
		JSONObject jo = new JSONObject();
		jo = JSONObject.fromObject(acceptJSON);
		JSONArray jsonArray = jo.getJSONArray("teacher");
		for (int i = 0; i < jsonArray.size(); i++) {
			TeacherView teacher = new TeacherView();
			JSONObject object = jsonArray.getJSONObject(i);
			teacher = (TeacherView) JSONObject.toBean(object, TeacherView.class);
			result.add(teacher);
		}
		return result;
	}

	public static JSONObject teacherListToJSON(List<TeacherView> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (TeacherView teacher : list) {
			JSONObject object = new JSONObject();
			object = JSONObject.fromObject(teacher);
			jsonArray.add(object);
		}
		jsonObject.put("teacher", jsonArray);
		return jsonObject;
	}

	public static List<StudentOfParentBean> JSONToStudentOfParentList(String acceptJSON) {
		// System.out.println(acceptJSON);
		List<StudentOfParentBean> result = new ArrayList<StudentOfParentBean>();
		JSONObject jo = new JSONObject();
		jo = JSONObject.fromObject(acceptJSON);
		JSONArray jsonArray = jo.getJSONArray("teacher");
		for (int i = 0; i < jsonArray.size(); i++) {
			StudentOfParentBean studentOfParent = new StudentOfParentBean();
			JSONObject object = jsonArray.getJSONObject(i);
			studentOfParent = (StudentOfParentBean) JSONObject.toBean(object, StudentOfParentBean.class);
			result.add(studentOfParent);
		}
		return result;
	}

	public static JSONObject studentOfParentListToJSON(List<StudentOfParentBean> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (StudentOfParentBean studentOfParent : list) {
			JSONObject object = new JSONObject();
			object = JSONObject.fromObject(studentOfParent);
			jsonArray.add(object);
		}
		jsonObject.put("teacher", jsonArray);
		return jsonObject;
	}

	public static List<ManagerBean> JSONToManagerList(String acceptJSON) {
		// System.out.println(acceptJSON);
		List<ManagerBean> result = new ArrayList<ManagerBean>();
		JSONObject jo = new JSONObject();
		jo = JSONObject.fromObject(acceptJSON);
		JSONArray jsonArray = jo.getJSONArray("manager");
		for (int i = 0; i < jsonArray.size(); i++) {
			ManagerBean manager = new ManagerBean();
			JSONObject object = jsonArray.getJSONObject(i);
			manager = (ManagerBean) JSONObject.toBean(object, ManagerBean.class);
			result.add(manager);
		}
		return result;
	}

	public static JSONObject managerListToJSON(List<ManagerBean> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (ManagerBean manager : list) {
			JSONObject object = new JSONObject();
			object = JSONObject.fromObject(manager);
			jsonArray.add(object);
		}
		jsonObject.put("manager", jsonArray);
		return jsonObject;
	}

	public static JSONObject studentAndParentViewToJSON(List<StudentAndParentView> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (StudentAndParentView studentAndParent : list) {
			JSONObject object = new JSONObject();
			object = JSONObject.fromObject(studentAndParent);
			jsonArray.add(object);
		}
		jsonObject.put("studentofparent", jsonArray);
		return jsonObject;
	}

	public static JSONObject teacherViewToJSON(List<TeacherView> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (TeacherView teacher : list) {
			JSONObject object = new JSONObject();
			object = JSONObject.fromObject(teacher);
			jsonArray.add(object);
		}
		jsonObject.put("teacher", jsonArray);
		return jsonObject;
	}

	public static JSONObject schoolListToJSON(List<SchoolBean> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (SchoolBean school : list) {
			JSONObject object = new JSONObject();
			object = JSONObject.fromObject(school);
			jsonArray.add(object);
		}
		jsonObject.put("school", jsonArray);
		return jsonObject;
	}

	public static JSONObject teacherUserListToJSON(List<TeacherUserView> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (TeacherUserView teacher : list) {
			JSONObject object = new JSONObject();
			object = JSONObject.fromObject(teacher);
			jsonArray.add(object);
		}
		jsonObject.put("user", jsonArray);
		return jsonObject;
	}

	public static JSONObject parentUserListToJSON(List<ParentUserView> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (ParentUserView parent : list) {
			JSONObject object = new JSONObject();
			object = JSONObject.fromObject(parent);
			jsonArray.add(object);
		}
		jsonObject.put("user", jsonArray);
		return jsonObject;
	}

	public static JSONObject dynamicListToJSON(List<DynamicView> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		System.out.println(list.size());
		for (DynamicView dynamic : list) {
			JSONObject object = new JSONObject();
			object.put("dynamicId", dynamic.getDynamicId());
			object.put("userAccount", dynamic.getUserAccount());
			if (dynamic.getUserHeadSrc() == null)
				object.put("userHeadSrc", "");
			else
				object.put("userHeadSrc", dynamic.getUserHeadSrc());
			object.put("userName", dynamic.getUserName());
			object.put("dynamicText", dynamic.getDynamicText());
			JSONArray dynamicSrcArray = new JSONArray();
			for (String src : dynamic.getDynamicSrc()) {
				JSONObject temp = new JSONObject();
				temp.put("src", src);
				dynamicSrcArray.add(temp);
			}
			object.put("dynamicSrc", dynamicSrcArray);
			object.put("dynamicDate", dynamic.getDynamicDate());
			JSONArray dynamicUpArray = new JSONArray();
			for (Up up : dynamic.getDynamicUp()) {
				JSONObject temp = new JSONObject();
				temp.put("userName", up.getUserName());
				temp.put("userAccount", up.getUserAccount());
				dynamicUpArray.add(temp);

			}
			object.put("dynamicUp", dynamicUpArray);
			JSONArray commitmentArray = new JSONArray();
			for (CommitmentBean com : dynamic.getCommitment()) {
				JSONObject temp = new JSONObject();
				temp = JSONObject.fromObject(com);
				commitmentArray.add(temp);
			}
			object.put("commitment", commitmentArray);
			jsonArray.add(object);
		}
		jsonObject.put("dynamic", jsonArray);
		return jsonObject;
	}

	public static JSONObject errorJSON(String error) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("check", "classcircle-server");
		jsonObject.put("error", error);
		return jsonObject;
	}

	public static JSONObject noErrorJSON(JSONObject jsonObject) {
		jsonObject.put("error", "");
		jsonObject.put("check", "classcircle-server");
		return jsonObject;
	}

	public static boolean checkJSON(JSONObject jsonObject) {
		boolean result = false;
		String check = jsonObject.getString("check");
		if (check.equals("classcircle-android")) {
			result = true;
		}
		return result;
	}
}

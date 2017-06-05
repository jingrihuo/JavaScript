package com.classcircle.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.classcircle.DAO.ClassDAO;
import com.classcircle.DAO.CommitmentDAO;
import com.classcircle.DAO.DynamicDAO;
import com.classcircle.DAO.ManagerDAO;
import com.classcircle.DAO.ParentDAO;
import com.classcircle.DAO.SchoolDAO;
import com.classcircle.DAO.StudentAndParentViewDAO;
import com.classcircle.DAO.StudentDAO;
import com.classcircle.DAO.StudentOfParentDAO;
import com.classcircle.DAO.TeacherDAO;
import com.classcircle.DAO.TeacherOfClassDAO;
import com.classcircle.DAO.TeacherViewDAO;
import com.classcircle.DAO.UserDAO;
import com.classcircle.model.ClassBean;
import com.classcircle.model.DynamicBean;
import com.classcircle.model.ManagerBean;
import com.classcircle.model.ParentBean;
import com.classcircle.model.SchoolBean;
import com.classcircle.model.StudentAndParentView;
import com.classcircle.model.StudentBean;
import com.classcircle.model.StudentOfParentBean;
import com.classcircle.model.TeacherBean;
import com.classcircle.model.TeacherOfClassBean;
import com.classcircle.model.TeacherView;
import com.classcircle.model.UserBean;
import com.classcircle.tools.DBHelper;
import com.classcircle.tools.IdHelper;
import com.classcircle.tools.JSONHelper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WebServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		System.out.println(method);
		if (method.equals("login")) {
			checkLogin(request, response);
		}
		if (method.equals("insertclass")) {
			createClass(request, response);
		}
		if (method.equals("deleteclass")) {
			String acceptJSON = receiveJSON(request);
			deleteClass(request, response, acceptJSON);
		}
		if (method.equals("updateclass")) {
			updateClass(request, response);
		}
		if (method.equals("searchclass")) {
			searchClass(request, response);
		}
		if (method.equals("insertstudent")) {
			createStudent(request, response);
		}
		if (method.equals("deletestudent")) {
			String acceptJSON = receiveJSON(request);
			deleteStudent(request, response, acceptJSON);
		}
		if (method.equals("updatestudent")) {
			updateStudent(request, response);
		}
		if (method.equals("searchstudent")) {
			searchStudent(request, response);
		}
		if (method.equals("insertteacher")) {
			createTeacher(request, response);
		}
		if (method.equals("deleteteacher")) {
			String acceptJSON = receiveJSON(request);
			deleteTeacher(request, response, acceptJSON);
		}
		if (method.equals("updateteacher")) {
			updateTeacher(request, response);
		}
		if (method.equals("searchteacher")) {
			searchTeacher(request, response);
		}
		if (method.equals("insertschool")) {
			createSchool(request, response);
		}
		if (method.equals("deleteschool")) {
			String acceptJSON = receiveJSON(request);
			deleteSchool(request, response, acceptJSON);
		}
		if (method.equals("updateschool")) {
			updateSchool(request, response);
		}
		if (method.equals("searchschool")) {
			searchSchool(request, response);
		}
		if (method.equals("insertmanager")) {
			createManager(request, response);
		}
		if (method.equals("deletemanager")) {
			String acceptJSON = receiveJSON(request);
			deleteManager(request, response, acceptJSON);
		}
		if (method.equals("searchmanager")) {
			searchManager(request, response);
		}
		if (method.equals("backup")) {
			System.out.println("backup");
			backup(request, response);
		}
		if (method.equals("xlsupload")) {
			try {
				xlsUpload(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

	private String receiveJSON(HttpServletRequest request) {
		String acceptJSON = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			acceptJSON = sb.toString();
			System.out.println(acceptJSON);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return acceptJSON;
	}

	private void checkLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		ManagerDAO md = new ManagerDAO();
		ManagerBean manager = new ManagerBean();
		manager.setManagerAccount(userName);
		manager.setManagerPassword(userPassword);
		// 验证用户是否合法
		if (md.checkManager(manager)) {
			manager = md.getManager(userName);
			// 超级管理员
			if (manager.getManagerType() != null && !manager.getManagerType().equals("")) {
				if (manager.getManagerType().equals("super")) {
					request.setAttribute("manageraccount", manager.getManagerAccount());
					request.setAttribute("username", manager.getManagerName());
					RequestDispatcher view = request.getRequestDispatcher("superadmin.jsp");
					view.forward(request, response);
				}
			} else {
				// 管理员
				request.setAttribute("manageraccount", manager.getManagerAccount());
				request.setAttribute("username", manager.getManagerName());
				System.out.println("欢迎管理员登录");
				RequestDispatcher view = request.getRequestDispatcher("index.jsp");
				view.forward(request, response);
			}
		} else {
			// 不合法
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
	}

	private void createClass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(request.getParameter("manageraccount"));
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		} else {
			// 合法
			// 验证班级是否存在
			String classId = request.getParameter("classid");
			classId = IdHelper.toDBId(classId, schoolId);
			ClassDAO cd = new ClassDAO();
			ClassBean classBean = cd.getClass(classId);
			if (classBean != null) {
				request.setAttribute("error", "该班级编号已被占用");
			} else {
				// 创建班级
				String className = request.getParameter("classname");
				String classDetail = request.getParameter("classdetail");
				classBean = new ClassBean();
				classBean.setClassId(classId);
				classBean.setClassName(className);
				classBean.setClassDetail(classDetail);
				classBean.setSchoolId(Integer.parseInt(schoolId));
				System.out.println(classBean);
				cd.createClass(classBean);
				RequestDispatcher view = request.getRequestDispatcher("success.html");
				view.forward(request, response);
			}
		}
	}

	private void deleteClass(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws ServletException, IOException {
		if (acceptJSON == null || acceptJSON.equals("")) {
			request.setAttribute("error", "服务器繁忙，请稍后再试");
		} else {
			JSONObject jo = JSONObject.fromObject(acceptJSON);
			String schoolId = IdHelper.getSchoolId(jo.getString("manageraccount"));
			// 验证是否合法
			if (schoolId == null || schoolId.equals("")) {
				RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
				view.forward(request, response);
			} else {
				// 合法
				ClassDAO cd = new ClassDAO();
				JSONArray jsonArray = jo.getJSONArray("classid");
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					String classId = object.getString("classid");
					classId = IdHelper.toDBId(classId, schoolId);
					ClassBean classBean = cd.getClass(classId);
					System.out.println(i);
					if (classBean == null) {
						request.setAttribute("error", IdHelper.toId(classId) + "该班级不存在，删除失败");
					} else {
						// 删除班级中的评论和动态
						DynamicDAO dd = new DynamicDAO();
						for (String dynamicId : dd.getDynamicIdByClassId(classId)) {
							CommitmentDAO comd = new CommitmentDAO();
							comd.deleteCommitmentByDynamicId(Integer.valueOf(dynamicId));
							DynamicBean dynamic = new DynamicBean();
							dynamic.setDynamicId(Integer.valueOf(dynamicId));
							dd.deleteDynamic(dynamic);
						}
						// 删除班级中关联的家长-学生、教师
						StudentDAO sd = new StudentDAO();
						List<StudentBean> studentlist = sd.getStudentByClassId(classId);
						StudentOfParentDAO sopd = new StudentOfParentDAO();
						ParentDAO pd = new ParentDAO();
						for (StudentBean student : studentlist) {
							List<Integer> parentIdList = sopd.getParentIdByStudentId(student.getStudentId());
							for (Integer parentId : parentIdList) {
								ParentBean parent = pd.getParentByParentId(parentId);
								String userAccount = parent.getUserAccount();
								// 删除学生-家长关联
								StudentOfParentBean bean = sopd.getStudentOfParent(student.getStudentId(), parentId);
								sopd.deleteStudentOfParent(bean);
								// 判断该家长下是否还有学生，没有则把该学生表和家长表下的学生和家长删除
								if (!sopd.checkRelationByParentId(parent.getParentId())) {
									// 删除该家长
									pd.deleteParent(parent);
									UserDAO ud = new UserDAO();
									UserBean user = ud.getUser(userAccount);
									if (user != null) {
										ud.deleteUser(user);
									}
								}
								if (!sopd.checkRelationByStudentId(student.getStudentId())) {
									// 删除该学生
									sd.deleteStudent(student);
								}
							}
						}
						// 删除该班级下关联的教师
						TeacherOfClassDAO tocd = new TeacherOfClassDAO();
						for (String teacherId : tocd.getTeacherIdByClassId(classId)) {
							TeacherOfClassBean bean = new TeacherOfClassBean();
							bean.setClassId(classId);
							bean.setTeacherId(teacherId);
							tocd.deleteTeacherOfClass(bean);
							TeacherDAO td = new TeacherDAO();
							TeacherBean teacher = td.getTeacher(teacherId);
							if (!tocd.checkRelationByTeacherId(teacherId)) {
								UserDAO ud = new UserDAO();
								UserBean user = ud.getUser(teacher.getUserAccount());
								td.deleteTeacher(teacher);
								if (user != null) {
									ud.deleteUser(user);
								}
							}
						}
					}
					// 删除该班级
					cd.deleteClass(classBean);
				}
			}
		}
	}

	private void updateClass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(request.getParameter("manageraccount"));
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// 合法
		// 验证该班级是否存在
		String classId = request.getParameter("classid");
		classId = IdHelper.toDBId(classId, schoolId);
		ClassDAO cd = new ClassDAO();
		ClassBean classBean = cd.getClass(classId);
		if (classBean == null) {
			request.setAttribute("error", "该班级不存在");
		}
		// 修改班级信息
		String className = request.getParameter("classname");
		String classDetail = request.getParameter("classdetail");
		classBean = new ClassBean();
		classBean.setClassId(classId);
		classBean.setClassName(className);
		classBean.setClassDetail(classDetail);
		classBean.setSchoolId(Integer.parseInt(schoolId));
		cd.updateClass(classBean);
		RequestDispatcher view = request.getRequestDispatcher("success.html");
		view.forward(request, response);
	}

	private void searchClass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(request.getParameter("manageraccount"));
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		} else {
			// 合法查询班级
			String classId = request.getParameter("classid");
			if (classId == null) {
				classId = "";
			}
			classId = IdHelper.toDBId(classId, schoolId);
			String className = request.getParameter("classname");
			if (className == null) {
				className = "";
			}

			ClassDAO cd = new ClassDAO();
			List<ClassBean> list = cd.searchClass(classId, className, Integer.parseInt(schoolId));
			for (ClassBean cb : list) {
				cb.setClassId(IdHelper.toId(cb.getClassId()));
			}
			JSONObject jsonObject = JSONHelper.classListToJSON(list);
			jsonObject.put("count", list.size());
			response.getWriter().write(jsonObject.toString());
		}
	}

	private void createStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(request.getParameter("manageraccount"));
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// 合法
		// 验证该班级是否存在
		String classId = request.getParameter("classid");
		classId = IdHelper.toDBId(classId, schoolId);
		ClassDAO cd = new ClassDAO();
		ClassBean classBean = cd.getClass(classId);
		if (classBean == null) {
			request.setAttribute("error", "该班级不存在");
		}
		String studentName = request.getParameter("studentname");
		String studentSex = request.getParameter("studentsex");
		String userAccount = request.getParameter("useraccount");
		String parentName = request.getParameter("parentname");
		String userSex = request.getParameter("usersex");
		String studentId = request.getParameter("studentid");
		studentId = IdHelper.toDBId(studentId, schoolId);
		// 验证该对学生-家长是否已被添加
		ParentDAO pd = new ParentDAO();
		ParentBean parent = pd.getParent(userAccount);
		StudentDAO sd = new StudentDAO();
		StudentBean student = sd.getStudent(studentId);
		StudentOfParentDAO sopd = new StudentOfParentDAO();
		if (parent != null) {
			// 学生-家长对存在
			StudentOfParentBean bean = sopd.getStudentOfParent(studentId, parent.getParentId());
			if (bean != null) {
				request.setAttribute("error", "该学生-家长已存在（家长手机号不能相同）");
			}
		}
		// 如果学生不存在
		if (student == null) {
			student = new StudentBean();
			student.setStudentName(studentName);
			student.setStudentSex(studentSex);
			student.setStudentId(studentId);
			student.setClassId(classId);
			sd.createStudent(student);
		}
		// 如果家长不存在
		if (parent == null) {
			parent = new ParentBean();
			parent.setParentName(parentName);
			parent.setUserAccount(userAccount);
			UserDAO ud = new UserDAO();
			UserBean user = new UserBean();
			user.setUserAccount(userAccount);
			user.setUserSex(userSex);
			user.setUserPassword("123456");
			ud.createUser(user);
			pd.createParent(parent);
			parent = pd.getParent(userAccount);
		}
		// 关联
		StudentOfParentBean bean = new StudentOfParentBean();
		bean.setStudentId(studentId);
		bean.setParentId(parent.getParentId());
		sopd.createStudentOfParent(bean);
		RequestDispatcher view = request.getRequestDispatcher("success.html");
		view.forward(request, response);
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws ServletException, IOException {
		if (acceptJSON == null || acceptJSON.equals("")) {
			request.setAttribute("error", "服务器繁忙，请稍后再试");
		}
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		String schoolId = IdHelper.getSchoolId(jo.getString("manageraccount"));
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		StudentDAO sd = new StudentDAO();
		ParentDAO pd = new ParentDAO();
		UserDAO ud = new UserDAO();
		StudentOfParentDAO sopd = new StudentOfParentDAO();
		// 合法
		JSONArray jsonArray = jo.getJSONArray("student");
		for (int i = 0; i < jsonArray.size(); i++) {
			// 验证该学生是否存在
			JSONObject object = jsonArray.getJSONObject(i);
			String studentId = object.getString("studentid");
			String userAccount = object.getString("useraccount");
			studentId = IdHelper.toDBId(studentId, schoolId);
			StudentBean student = sd.getStudent(studentId);
			if (student == null) {
				request.setAttribute("error", "该学生不存在");
			}
			// 验证该家长是否存在
			ParentBean parent = pd.getParent(userAccount);
			if (parent == null) {
				request.setAttribute("error", "该家长不存在");
			}
			// 验证该学生-家长关联是否存在
			StudentOfParentBean bean = sopd.getStudentOfParent(studentId, parent.getParentId());
			if (bean != null) {
				request.setAttribute("error", "该学生与该家长不匹配");
			}
			// 删除学生-家长关联
			sopd.deleteStudentOfParent(bean);
			// 判断该家长下是否还有学生，没有则把该学生表和家长表下的学生和家长删除
			if (!sopd.checkRelationByParentId(parent.getParentId())) {
				// 删除该学生
				sd.deleteStudent(student);
			}
			if (!sopd.checkRelationByStudentId(student.getStudentId())) {
				// 删除该家长
				pd.deleteParent(parent);
				UserBean user = ud.getUser(userAccount);
				if (user != null) {
					DynamicDAO dd = new DynamicDAO();
					// 删除该用户发布的评论和动态
					for (String dynamicId : dd.getDynamicIdByUserAccount(user.getUserAccount())) {
						CommitmentDAO comd = new CommitmentDAO();
						comd.deleteCommitmentByDynamicId(Integer.valueOf(dynamicId));
						DynamicBean dynamic = new DynamicBean();
						dynamic.setDynamicId(Integer.valueOf(dynamicId));
						dd.deleteDynamic(dynamic);
					}
					ud.deleteUser(user);
				}
			}
		}
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(request.getParameter("manageraccount"));
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// 合法
		// 验证该班级是否存在
		String classId = request.getParameter("classid");
		classId = IdHelper.toDBId(classId, schoolId);
		ClassDAO cd = new ClassDAO();
		ClassBean classBean = cd.getClass(classId);
		if (classBean == null) {
			request.setAttribute("error", classId + "该班级不存在");
		}
		String studentName = request.getParameter("studentname");
		String studentSex = request.getParameter("studentsex");
		String userAccount = request.getParameter("useraccount");
		String parentName = request.getParameter("parentname");
		String userSex = request.getParameter("usersex");
		String studentId = request.getParameter("studentid");
		studentId = IdHelper.toDBId(studentId, schoolId);

		StudentDAO sd = new StudentDAO();
		ParentDAO pd = new ParentDAO();
		StudentBean student = sd.getStudent(studentId);
		ParentBean parent = pd.getParent(userAccount);
		// 如果学生不存在
		if (student == null) {
			request.setAttribute("error", studentId + "该学生不存在");
		}
		student = new StudentBean();
		student.setStudentName(studentName);
		student.setStudentSex(studentSex);
		student.setStudentId(studentId);
		student.setClassId(classId);
		sd.updateStudent(student);
		// 如果家长不存在
		if (parent == null) {
			request.setAttribute("error", userAccount + "该家长不存在");
		}
		parent = new ParentBean();
		parent.setParentName(parentName);
		UserDAO ud = new UserDAO();
		UserBean user = new UserBean();
		user.setUserAccount(userAccount);
		user.setUserSex(userSex);
		ud.updateUser(user);
		parent.setParentName(parentName);
		pd.updateParent(parent);
		RequestDispatcher view = request.getRequestDispatcher("success.html");
		view.forward(request, response);
	}

	private void searchStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("1111");
		String schoolId = IdHelper.getSchoolId(request.getParameter("manageraccount"));
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// 合法
		String classId = request.getParameter("classid");
		if (classId == null) {
			classId = "";
		}
		classId = IdHelper.toDBId(classId, schoolId);
		String className = request.getParameter("classname");
		if (className == null) {
			className = "";
		}
		String studentId = request.getParameter("studentid");
		if (studentId == null) {
			studentId = "";
		}
		String studentName = request.getParameter("studentname");
		if (studentName == null) {
			studentName = "";
		}
		String parentName = request.getParameter("parentname");
		if (parentName == null) {
			parentName = "";
		}

		StudentAndParentViewDAO sapvd = new StudentAndParentViewDAO();
		List<StudentAndParentView> list = sapvd.searchStudentAndParent(Integer.valueOf(schoolId), studentId, classId,
				parentName, className);
		for (StudentAndParentView temp : list) {
			temp.setClassId(IdHelper.toId(temp.getClassId()));
			temp.setStudentId(IdHelper.toId(temp.getStudentId()));
		}
		JSONObject jsonObject = JSONHelper.studentAndParentViewToJSON(list);
		jsonObject.put("error", "");
		jsonObject.put("count", list.size());
		System.out.println(jsonObject.toString());
		response.getWriter().write(jsonObject.toString());
	}

	private void createTeacher(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(request.getParameter("manageraccount"));
		System.out.println(schoolId);
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// 合法
		String teacherId = request.getParameter("teacherid");
		teacherId = IdHelper.toDBId(teacherId, schoolId);
		System.out.println(teacherId);
		String userAccount = request.getParameter("useraccount");
		System.out.println(userAccount);
		String teacherName = request.getParameter("teachername");
		System.out.println(teacherName);
		String teacherType = request.getParameter("teachertype");
		System.out.println(teacherType);
		String classId = request.getParameter("classid");
		System.out.println(classId);
		String userSex = request.getParameter("usersex");
		System.out.println(userSex);
		classId = IdHelper.toDBId(classId, schoolId);
		String project = request.getParameter("project");
		System.out.println(project);

		ClassDAO cd = new ClassDAO();
		TeacherDAO td = new TeacherDAO();
		UserDAO ud = new UserDAO();
		TeacherOfClassDAO tocd = new TeacherOfClassDAO();
		// 验证班级是否存在
		ClassBean classBean = cd.getClass(classId);
		if (classBean == null) {
			request.setAttribute("error", IdHelper.toId(classId) + "该班级不存在");
		}
		// 验证老师是否存在
		TeacherBean teacher = td.getTeacher(teacherId);
		if (teacher != null) {
			// 若老师存在，验证老师班级联系是否存在
			TeacherOfClassBean bean = tocd.getTeacherOfClass(teacherId, classId);
			if (bean != null) {
				request.setAttribute("error", "该教师" + IdHelper.toId(teacherId) + "已存在于该班级" + IdHelper.toId(classId));
			}
		} else {
			// 老师不存在
			UserBean user = new UserBean();
			user.setUserAccount(userAccount);
			user.setUserPassword("123456");
			user.setUserSex(userSex);
			ud.createUser(user);
			teacher = new TeacherBean();
			teacher.setTeacherId(teacherId);
			teacher.setTeacherName(teacherName);
			teacher.setUserAccount(userAccount);
			teacher.setProject(project);
			System.out.println("create teacher");
			td.createTeacher(teacher);
			System.out.println("create teacher succeed");
		}
		TeacherOfClassBean bean = new TeacherOfClassBean();
		bean.setClassId(classId);
		bean.setTeacherId(teacherId);
		bean.setTeacherType(teacherType);
		tocd.createTeacherOfClass(bean);
		RequestDispatcher view = request.getRequestDispatcher("success.html");
		view.forward(request, response);
	}

	private void deleteTeacher(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws ServletException, IOException {
		if (acceptJSON == null || acceptJSON.equals("")) {
			request.setAttribute("error", "服务器繁忙，请稍后再试");
		}
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		String schoolId = IdHelper.getSchoolId(jo.getString("manageraccount"));
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		TeacherDAO td = new TeacherDAO();
		ClassDAO cd = new ClassDAO();
		UserDAO ud = new UserDAO();
		TeacherOfClassDAO tocd = new TeacherOfClassDAO();
		// 合法
		JSONArray jsonArray = jo.getJSONArray("teacher");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String teacherId = object.getString("teacherid");
			teacherId = IdHelper.toDBId(teacherId, schoolId);
			String classId = object.getString("classid");
			classId = IdHelper.toDBId(classId, schoolId);
			TeacherBean teacher = td.getTeacher(teacherId);
			System.out.println(teacherId);
			System.out.println(classId);
			TeacherOfClassBean bean = tocd.getTeacherOfClass(teacherId, classId);
			if (teacher == null) {
				request.setAttribute("error", IdHelper.toId(teacherId) + "该教师不存在");
			}
			ClassBean classBean = cd.getClass(classId);
			if (classBean == null) {
				request.setAttribute("error", IdHelper.toId(classId) + "该班级不存在");
			}
			if (bean == null) {
				request.setAttribute("error", "不合法的数据");
			}
			tocd.deleteTeacherOfClass(bean);
			if (!tocd.checkRelationByTeacherId(teacherId)) {
				UserBean user = ud.getUser(teacher.getUserAccount());
				td.deleteTeacher(teacher);
				if (user != null) {
					DynamicDAO dd = new DynamicDAO();
					// 删除该用户发布的评论和动态
					for (String dynamicId : dd.getDynamicIdByUserAccount(user.getUserAccount())) {
						CommitmentDAO comd = new CommitmentDAO();
						comd.deleteCommitmentByDynamicId(Integer.valueOf(dynamicId));
						DynamicBean dynamic = new DynamicBean();
						dynamic.setDynamicId(Integer.valueOf(dynamicId));
						dd.deleteDynamic(dynamic);
					}
					ud.deleteUser(user);
				}
			}
		}
	}

	private void updateTeacher(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(request.getParameter("manageraccount"));
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		String teacherId = request.getParameter("teacherid");
		teacherId = IdHelper.toDBId(teacherId, schoolId);
		String teacherName = request.getParameter("teachername");
		String teacherType = request.getParameter("teachertype");
		String classId = request.getParameter("classid");
		classId = IdHelper.toDBId(classId, schoolId);
		String project = request.getParameter("project");
		String oldClassId = request.getParameter("oldclassid");
		oldClassId = IdHelper.toDBId(oldClassId, schoolId);
		// 合法
		// 验证新班级是否存在
		ClassDAO cd = new ClassDAO();
		ClassBean classBean = cd.getClass(classId);
		if (classBean == null) {
			request.setAttribute("error", "该班级不存在");
		}
		// 验证原班级是否存在
		classBean = cd.getClass(oldClassId);
		if (classBean == null) {
			request.setAttribute("error", "不合法的数据");
		}
		// 验证该教师是否存在
		TeacherDAO td = new TeacherDAO();
		TeacherBean teacher = td.getTeacher(teacherId);
		if (teacher == null) {
			request.setAttribute("error", "该教师不存在");
		}
		// 更改教师信息
		teacher.setProject(project);
		teacher.setTeacherName(teacherName);
		td.updateTeacher(teacher);
		// 删除原教师-班级关联信息
		TeacherOfClassDAO tocd = new TeacherOfClassDAO();
		TeacherOfClassBean bean = new TeacherOfClassBean();
		bean.setTeacherId(teacherId);
		bean.setClassId(oldClassId);
		tocd.deleteTeacherOfClass(bean);
		// 创建新教师-班级关联信息
		bean = new TeacherOfClassBean();
		bean.setClassId(classId);
		bean.setTeacherId(teacherId);
		bean.setTeacherType(teacherType);
		tocd.createTeacherOfClass(bean);
		request.setAttribute("error", "");
		RequestDispatcher view = request.getRequestDispatcher("success.html");
		view.forward(request, response);
	}

	private void searchTeacher(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(request.getParameter("manageraccount"));
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// 合法
		String classId = request.getParameter("classid");
		if (classId == null) {
			classId = "";
		}
		classId = IdHelper.toDBId(classId, schoolId);
		String className = request.getParameter("classname");
		if (className == null) {
			className = "";
		}
		String teacherId = request.getParameter("teacherid");
		if (teacherId == null) {
			teacherId = "";
		}
		teacherId = IdHelper.toDBId(teacherId, schoolId);
		String teacherName = request.getParameter("teachername");
		if (teacherName == null) {
			teacherName = "";
		}
		String teacherType = request.getParameter("teachertype");
		if (teacherType == null) {
			teacherType = "";
		}
		String project = request.getParameter("project");
		if (project == null) {
			project = "";
		}
		TeacherViewDAO tvd = new TeacherViewDAO();
		List<TeacherView> list = tvd.searchTeacher(teacherId, teacherName, project, classId, Integer.parseInt(schoolId),
				className);
		for (TeacherView teacher : list) {
			teacher.setClassId(IdHelper.toId(teacher.getClassId()));
			teacher.setTeacherId(IdHelper.toId(teacher.getTeacherId()));
		}
		JSONObject jsonObject = JSONHelper.teacherViewToJSON(list);
		jsonObject.put("error", "");
		jsonObject.put("count", list.size());
		System.out.println(jsonObject.toString());
		response.getWriter().write(jsonObject.toString());
	}

	private void createSchool(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolName = request.getParameter("schoolname");
		String schoolDetail = request.getParameter("schooldetail");
		SchoolDAO sd = new SchoolDAO();
		SchoolBean school = new SchoolBean();
		school.setSchoolDetail(schoolDetail);
		school.setSchoolName(schoolName);
		sd.createSchool(school);
		RequestDispatcher view = request.getRequestDispatcher("success.html");
		view.forward(request, response);
	}

	private void deleteSchool(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws ServletException, IOException {
		if (acceptJSON == null || acceptJSON.equals("")) {
			request.setAttribute("error", "服务器繁忙，请稍后再试");
		}
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		JSONArray jsonArray = jo.getJSONArray("school");
		SchoolDAO sd = new SchoolDAO();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String schoolId = jsonObject.getString("schoolid");
			SchoolBean school = sd.getSchool(Integer.valueOf(schoolId));
			if (school == null) {
				request.setAttribute("error", "该学校不存在");
			} else {
				// 删除该学校关联的班级
				ClassDAO cd = new ClassDAO();
				for (String classId : cd.getClassIdBySchoolId(Integer.valueOf(schoolId))) {
					ClassBean classBean = cd.getClass(classId);
					// 删除班级中的评论和动态
					DynamicDAO dd = new DynamicDAO();
					for (String dynamicId : dd.getDynamicIdByClassId(classId)) {
						CommitmentDAO comd = new CommitmentDAO();
						comd.deleteCommitmentByDynamicId(Integer.valueOf(dynamicId));
						DynamicBean dynamic = new DynamicBean();
						dynamic.setDynamicId(Integer.valueOf(dynamicId));
						dd.deleteDynamic(dynamic);
					}
					// 删除班级中关联的家长-学生、教师
					StudentDAO stud = new StudentDAO();
					List<StudentBean> studentlist = stud.getStudentByClassId(classId);
					StudentOfParentDAO sopd = new StudentOfParentDAO();
					ParentDAO pd = new ParentDAO();
					for (StudentBean student : studentlist) {
						List<Integer> parentIdList = sopd.getParentIdByStudentId(student.getStudentId());
						for (Integer parentId : parentIdList) {
							ParentBean parent = pd.getParentByParentId(parentId);
							String userAccount = parent.getUserAccount();
							// 删除学生-家长关联
							StudentOfParentBean bean = sopd.getStudentOfParent(student.getStudentId(), parentId);
							sopd.deleteStudentOfParent(bean);
							// 判断该家长下是否还有学生，没有则把该学生表和家长表下的学生和家长删除
							if (!sopd.checkRelationByParentId(parent.getParentId())) {
								// 删除该学生
								pd.deleteParent(parent);
								UserDAO ud = new UserDAO();
								UserBean user = ud.getUser(userAccount);
								if (user != null) {
									ud.deleteUser(user);
								}
							}
							if (!sopd.checkRelationByStudentId(student.getStudentId())) {
								// 删除该家长
								stud.deleteStudent(student);
							}
						}
					}
					// 删除该班级下关联的教师
					TeacherOfClassDAO tocd = new TeacherOfClassDAO();
					for (String teacherId : tocd.getTeacherIdByClassId(classId)) {
						TeacherOfClassBean bean = new TeacherOfClassBean();
						bean.setClassId(classId);
						bean.setTeacherId(teacherId);
						tocd.deleteTeacherOfClass(bean);
						TeacherDAO td = new TeacherDAO();
						TeacherBean teacher = td.getTeacher(teacherId);
						if (!tocd.checkRelationByTeacherId(teacherId)) {
							UserDAO ud = new UserDAO();
							UserBean user = ud.getUser(teacher.getUserAccount());
							td.deleteTeacher(teacher);
							if (user != null) {
								ud.deleteUser(user);
							}
						}
					}
					// 删除该班级
					cd.deleteClass(classBean);
				}
				ManagerDAO md = new ManagerDAO();
				md.deleteManager(Integer.valueOf(schoolId));
				sd.deleteSchool(school);
			}
		}

	}

	private void updateSchool(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = request.getParameter("schoolid");
		System.out.println(schoolId);
		String schoolName = request.getParameter("schoolname");
		System.out.println(schoolName);
		String schoolDetail = request.getParameter("schooldetail");
		System.out.println(schoolDetail);
		SchoolDAO sd = new SchoolDAO();
		SchoolBean school = sd.getSchool(Integer.valueOf(schoolId));
		if (school == null) {
			request.setAttribute("error", "该学校不存在");
		} else {
			school.setSchoolDetail(schoolDetail);
			school.setSchoolName(schoolName);
			sd.updateSchool(school);
		}
		RequestDispatcher view = request.getRequestDispatcher("success.html");
		view.forward(request, response);
	}

	private void searchSchool(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = request.getParameter("schoolid");
		if (schoolId == null || schoolId.equals("")) {
			schoolId = "";
		}
		String schoolName = request.getParameter("schoolname");
		if (schoolId == null || schoolId.equals("")) {
			schoolName = "";
		}
		String schoolDetail = request.getParameter("schooldetail");
		if (schoolId == null || schoolId.equals("")) {
			schoolDetail = "";
		}
		SchoolDAO sd = new SchoolDAO();
		List<SchoolBean> list = sd.searchSchool(schoolId, schoolName, schoolDetail);
		JSONObject jsonObject = JSONHelper.schoolListToJSON(list);
		jsonObject.put("error", "");
		jsonObject.put("count", list.size());
		response.getWriter().write(jsonObject.toString());
	}

	private void createManager(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String managerAccount = request.getParameter("manageraccount");
		String schoolId = request.getParameter("schoolid");
		String managerName = request.getParameter("managername");
		ManagerDAO md = new ManagerDAO();
		ManagerBean manager = new ManagerBean();
		manager.setManagerAccount(managerAccount);
		manager.setManagerName(managerName);
		manager.setManagerPassword("123456");
		manager.setManagerType("");
		manager.setSchoolId(Integer.valueOf(schoolId));
		md.createManager(manager);
		RequestDispatcher view = request.getRequestDispatcher("success.html");
		view.forward(request, response);
	}

	private void deleteManager(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws ServletException, IOException {
		if (acceptJSON == null || acceptJSON.equals("")) {
			request.setAttribute("error", "服务器繁忙，请稍后再试");
		}
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		JSONArray jsonArray = jo.getJSONArray("manager");
		ManagerDAO md = new ManagerDAO();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String managerAccount = jsonObject.getString("manageraccount");
			ManagerBean manager = md.getManager(managerAccount);
			if (manager == null) {
				request.setAttribute("error", "该管理员不存在");
			} else {
				md.deleteManager(manager);
			}
		}
	}

	private void searchManager(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String managerAccount = request.getParameter("manageraccount");
		if (managerAccount == null || managerAccount.equals("")) {
			managerAccount = "";
		}
		String schoolId = request.getParameter("schoolid");
		if (schoolId == null || schoolId.equals("")) {
			schoolId = "";
		}
		String managerName = request.getParameter("managername");
		if (managerName == null || managerName.equals("")) {
			managerName = "";
		}
		ManagerDAO md = new ManagerDAO();
		List<ManagerBean> list = md.searchManager(schoolId, managerAccount, managerName);
		JSONObject jsonObject = JSONHelper.managerListToJSON(list);
		jsonObject.put("error", "");
		jsonObject.put("count", list.size());
		System.out.println(jsonObject.toString());
		response.getWriter().write(jsonObject.toString());
	}

	private void readClassExcel(HttpServletRequest request, HttpServletResponse response, String managerAccount)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(managerAccount);
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		} else {
			Sheet sheet;
			Workbook book;
			int i;
			Cell classIdCell, classNameCell, classDetailCell;
			try {
				book = Workbook.getWorkbook(new File("webapps/ClassCircle/xls/class.xls"));
				sheet = book.getSheet(0);
				i = 1;
				while (true) {
					classIdCell = sheet.getCell(0, i);
					if ("".equals(classIdCell.getContents())) {
						break;
					}
					String classId = IdHelper.toDBId(classIdCell.getContents(), schoolId);
					classNameCell = sheet.getCell(1, i);
					classDetailCell = sheet.getCell(2, i);
					ClassDAO cd = new ClassDAO();
					ClassBean classBean = new ClassBean();
					classBean.setClassId(classId);
					classBean.setClassName(classNameCell.getContents());
					classBean.setClassDetail(classDetailCell.getContents());
					classBean.setSchoolId(Integer.valueOf(schoolId));
					cd.createClass(classBean);
					i++;
				}
				RequestDispatcher view = request.getRequestDispatcher("success.html");
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void readStudentExcel(HttpServletRequest request, HttpServletResponse response, String managerAccount)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(managerAccount);
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		} else {
			Sheet sheet;
			Workbook book;
			int i;
			Cell studentIdCell, classIdCell, studentNameCell, userAccountCell, parentNameCell, studentSexCell,
					userSexCell;
			try {
				book = Workbook.getWorkbook(new File("webapps/ClassCircle/xls/student.xls"));
				sheet = book.getSheet(0);
				i = 1;
				while (true) {
					studentIdCell = sheet.getCell(0, i);
					if (studentIdCell.getContents().equals("")) {
						break;
					}
					classIdCell = sheet.getCell(1, i);
					studentNameCell = sheet.getCell(2, i);
					userAccountCell = sheet.getCell(3, i);
					parentNameCell = sheet.getCell(4, i);
					studentSexCell = sheet.getCell(5, i);
					userSexCell = sheet.getCell(6, i);
					String studentId = studentIdCell.getContents();
					studentId = IdHelper.toDBId(studentId, schoolId);
					String classId = classIdCell.getContents();
					classId = IdHelper.toDBId(classId, schoolId);
					String studentName = studentNameCell.getContents();
					String userAccount = userAccountCell.getContents();
					String parentName = parentNameCell.getContents();
					String studentSex = studentSexCell.getContents();
					String userSex = userSexCell.getContents();
					// 验证该对学生-家长是否已被添加
					ParentDAO pd = new ParentDAO();
					ParentBean parent = pd.getParent(userAccount);
					StudentDAO sd = new StudentDAO();
					StudentBean student = sd.getStudent(studentId);
					StudentOfParentDAO sopd = new StudentOfParentDAO();
					if (parent != null) {
						// 学生-家长对存在
						StudentOfParentBean bean = sopd.getStudentOfParent(studentId, parent.getParentId());
						if (bean != null) {
							request.setAttribute("error", "该学生-家长已存在（家长手机号不能相同）");
						}
					}
					// 如果学生不存在
					if (student == null) {
						student = new StudentBean();
						student.setStudentName(studentName);
						student.setStudentSex(studentSex);
						student.setStudentId(studentId);
						student.setClassId(classId);
						sd.createStudent(student);
					}
					// 如果家长不存在
					if (parent == null) {
						parent = new ParentBean();
						parent.setParentName(parentName);
						parent.setUserAccount(userAccount);
						UserDAO ud = new UserDAO();
						UserBean user = new UserBean();
						user.setUserAccount(userAccount);
						user.setUserSex(userSex);
						user.setUserPassword("123456");
						ud.createUser(user);
						pd.createParent(parent);
						parent = pd.getParent(userAccount);
					}
					// 关联
					StudentOfParentBean bean = new StudentOfParentBean();
					bean.setStudentId(studentId);
					bean.setParentId(parent.getParentId());
					sopd.createStudentOfParent(bean);
					i++;
				}
				RequestDispatcher view = request.getRequestDispatcher("success.html");
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void readTeacherExcel(HttpServletRequest request, HttpServletResponse response, String managerAccount)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(managerAccount);
		// 验证是否合法
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		} else {
			Sheet sheet;
			Workbook book;
			int i;
			Cell teacherIdCell, userAccountCell, teacherNameCell, teacherTypeCell, classIdCell, projectCell,
					userSexCell;
			try {
				book = Workbook.getWorkbook(new File("webapps/ClassCircle/xls/teacher.xls"));
				sheet = book.getSheet(0);
				i = 1;
				while (true) {
					teacherIdCell = sheet.getCell(0, i);
					if (teacherIdCell.getContents().equals("")) {
						break;
					}
					userAccountCell = sheet.getCell(1, i);
					teacherNameCell = sheet.getCell(2, i);
					teacherTypeCell = sheet.getCell(3, i);
					classIdCell = sheet.getCell(4, i);
					projectCell = sheet.getCell(5, i);
					userSexCell = sheet.getCell(6, i);
					String teacherId = teacherIdCell.getContents();
					teacherId = IdHelper.toDBId(teacherId, schoolId);
					String userAccount = userAccountCell.getContents();
					String teacherName = teacherNameCell.getContents();
					String teacherType = teacherTypeCell.getContents();
					String classId = classIdCell.getContents();
					String project = projectCell.getContents();
					String userSex = userSexCell.getContents();
					ClassDAO cd = new ClassDAO();
					TeacherDAO td = new TeacherDAO();
					UserDAO ud = new UserDAO();
					TeacherOfClassDAO tocd = new TeacherOfClassDAO();
					// 验证班级是否存在
					ClassBean classBean = cd.getClass(classId);
					if (classBean == null) {
						request.setAttribute("error", IdHelper.toId(classId) + "该班级不存在");
					}
					// 验证老师是否存在
					TeacherBean teacher = td.getTeacher(teacherId);
					if (teacher != null) {
						// 若老师存在，验证老师班级联系是否存在
						TeacherOfClassBean bean = tocd.getTeacherOfClass(teacherId, classId);
						if (bean != null) {
							request.setAttribute("error",
									"该教师" + IdHelper.toId(teacherId) + "已存在于该班级" + IdHelper.toId(classId));
						}
					} else {
						// 老师不存在
						UserBean user = new UserBean();
						user.setUserAccount(userAccount);
						user.setUserPassword("123456");
						user.setUserSex(userSex);
						ud.createUser(user);
						teacher = new TeacherBean();
						teacher.setTeacherId(teacherId);
						teacher.setTeacherName(teacherName);
						teacher.setUserAccount(userAccount);
						teacher.setProject(project);
						td.createTeacher(teacher);
					}
					TeacherOfClassBean bean = new TeacherOfClassBean();
					bean.setClassId(classId);
					bean.setTeacherId(teacherId);
					bean.setTeacherType(teacherType);
					tocd.createTeacherOfClass(bean);
					i++;
				}
				RequestDispatcher view = request.getRequestDispatcher("success.html");
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void readSchoolExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Sheet sheet;
		Workbook book;
		int i;
		Cell schoolNameCell, SchoolDetailCell;
		try {
			book = Workbook.getWorkbook(new File("webapps/ClassCircle/xls/school.xls"));
			sheet = book.getSheet(0);
			i = 1;
			while (true) {
				schoolNameCell = sheet.getCell(0, i);
				if (schoolNameCell.getContents().equals("")) {
					break;
				}
				SchoolDetailCell = sheet.getCell(1, i);
				String schoolName = schoolNameCell.getContents();
				String schoolDetail = SchoolDetailCell.getContents();
				SchoolDAO sd = new SchoolDAO();
				SchoolBean school = new SchoolBean();
				school.setSchoolDetail(schoolDetail);
				school.setSchoolName(schoolName);
				sd.createSchool(school);
				i++;
			}
			RequestDispatcher view = request.getRequestDispatcher("success.html");
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readManagerExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Sheet sheet;
		Workbook book;
		int i;
		Cell managerAccountCell, schoolIdCell, managerNameCell;
		try {
			book = Workbook.getWorkbook(new File("webapps/ClassCircle/xls/teacher.xls"));
			sheet = book.getSheet(0);
			i = 1;
			while (true) {
				managerAccountCell = sheet.getCell(0, i);
				if (managerAccountCell.getContents().equals("")) {
					break;
				}
				schoolIdCell = sheet.getCell(1, i);
				managerNameCell = sheet.getCell(2, i);
				String managerAccount = managerAccountCell.getContents();
				String schoolId = schoolIdCell.getContents();
				String managerName = managerNameCell.getContents();
				ManagerDAO md = new ManagerDAO();
				ManagerBean manager = new ManagerBean();
				manager.setManagerAccount(managerAccount);
				manager.setManagerName(managerName);
				manager.setManagerPassword("123456");
				manager.setManagerType("");
				manager.setSchoolId(Integer.valueOf(schoolId));
				md.createManager(manager);
				i++;
			}
			RequestDispatcher view = request.getRequestDispatcher("success.html");
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void backup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String managerAccount = request.getParameter("manageraccount");
		ManagerDAO md = new ManagerDAO();
		ManagerBean manager = md.getManager(managerAccount);
		System.out.println(manager.getManagerType());
		if (manager.getManagerType().equals("super")) {
			DBHelper.backup();
		}
		System.out.println("success");
	}

	private void xlsUpload(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, FileUploadException {
		String type = request.getParameter("type");
		System.out.println(type);
		String managerAccount = request.getParameter("manageraccount");
		// System.out.println("666");
		// ServletFileUpload upload = new ServletFileUpload();
		// upload.setHeaderEncoding("utf-8");
		// List<FileItem> items = upload.parseRequest(request);
		// Map<String, String> param = new HashMap<String, String>();
		// for (Object object : items) {
		// FileItem fileItem = (FileItem) object;
		// if (fileItem.isFormField()) {
		// param.put(fileItem.getFieldName(), fileItem.getString("utf-8"));//
		// 如果你页面编码是utf-8的
		// }
		// }
		// String type = param.get("type");
		// System.out.println(type);
		// String managerAccount = param.get("manageraccount");
		// String schoolId = "noschool";
		// if (type.equals("class") || type.equals("student") ||
		// type.equals("teacher")) {
		// schoolId =
		// IdHelper.getSchoolId(request.getParameter("manageraccount"));
		// }
		// String data = param.get("data");
		// // 时间
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		// Date date = new Date(System.currentTimeMillis());
		// String time = sdf.format(date);
		// String url = schoolId + type + time + ".xls";
		// File f = new File("webapps/ClassCircle/xls/"
		// + url);
		// f.createNewFile();
		// System.out.println("createNewFile success");
		// if (f.exists()) {
		// byte[] b = Base64.decode(data);
		// FileOutputStream fos = new FileOutputStream(f);
		// fos.write(b);
		// fos.close();
		// }
		if (type.equals("class")) {
			this.readClassExcel(request, response, managerAccount);
		}
		if (type.equals("student")) {
			this.readStudentExcel(request, response, managerAccount);
		}
		if (type.equals("manager")) {
			this.readManagerExcel(request, response);
		}
		if (type.equals("teacher")) {
			this.readTeacherExcel(request, response, managerAccount);
		}
		if (type.equals("school")) {
			this.readSchoolExcel(request, response);
		}
	}
}
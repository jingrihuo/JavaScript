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
		// ��֤�û��Ƿ�Ϸ�
		if (md.checkManager(manager)) {
			manager = md.getManager(userName);
			// ��������Ա
			if (manager.getManagerType() != null && !manager.getManagerType().equals("")) {
				if (manager.getManagerType().equals("super")) {
					request.setAttribute("manageraccount", manager.getManagerAccount());
					request.setAttribute("username", manager.getManagerName());
					RequestDispatcher view = request.getRequestDispatcher("superadmin.jsp");
					view.forward(request, response);
				}
			} else {
				// ����Ա
				request.setAttribute("manageraccount", manager.getManagerAccount());
				request.setAttribute("username", manager.getManagerName());
				System.out.println("��ӭ����Ա��¼");
				RequestDispatcher view = request.getRequestDispatcher("index.jsp");
				view.forward(request, response);
			}
		} else {
			// ���Ϸ�
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
	}

	private void createClass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(request.getParameter("manageraccount"));
		// ��֤�Ƿ�Ϸ�
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		} else {
			// �Ϸ�
			// ��֤�༶�Ƿ����
			String classId = request.getParameter("classid");
			classId = IdHelper.toDBId(classId, schoolId);
			ClassDAO cd = new ClassDAO();
			ClassBean classBean = cd.getClass(classId);
			if (classBean != null) {
				request.setAttribute("error", "�ð༶����ѱ�ռ��");
			} else {
				// �����༶
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
			request.setAttribute("error", "��������æ�����Ժ�����");
		} else {
			JSONObject jo = JSONObject.fromObject(acceptJSON);
			String schoolId = IdHelper.getSchoolId(jo.getString("manageraccount"));
			// ��֤�Ƿ�Ϸ�
			if (schoolId == null || schoolId.equals("")) {
				RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
				view.forward(request, response);
			} else {
				// �Ϸ�
				ClassDAO cd = new ClassDAO();
				JSONArray jsonArray = jo.getJSONArray("classid");
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					String classId = object.getString("classid");
					classId = IdHelper.toDBId(classId, schoolId);
					ClassBean classBean = cd.getClass(classId);
					System.out.println(i);
					if (classBean == null) {
						request.setAttribute("error", IdHelper.toId(classId) + "�ð༶�����ڣ�ɾ��ʧ��");
					} else {
						// ɾ���༶�е����ۺͶ�̬
						DynamicDAO dd = new DynamicDAO();
						for (String dynamicId : dd.getDynamicIdByClassId(classId)) {
							CommitmentDAO comd = new CommitmentDAO();
							comd.deleteCommitmentByDynamicId(Integer.valueOf(dynamicId));
							DynamicBean dynamic = new DynamicBean();
							dynamic.setDynamicId(Integer.valueOf(dynamicId));
							dd.deleteDynamic(dynamic);
						}
						// ɾ���༶�й����ļҳ�-ѧ������ʦ
						StudentDAO sd = new StudentDAO();
						List<StudentBean> studentlist = sd.getStudentByClassId(classId);
						StudentOfParentDAO sopd = new StudentOfParentDAO();
						ParentDAO pd = new ParentDAO();
						for (StudentBean student : studentlist) {
							List<Integer> parentIdList = sopd.getParentIdByStudentId(student.getStudentId());
							for (Integer parentId : parentIdList) {
								ParentBean parent = pd.getParentByParentId(parentId);
								String userAccount = parent.getUserAccount();
								// ɾ��ѧ��-�ҳ�����
								StudentOfParentBean bean = sopd.getStudentOfParent(student.getStudentId(), parentId);
								sopd.deleteStudentOfParent(bean);
								// �жϸüҳ����Ƿ���ѧ����û����Ѹ�ѧ����ͼҳ����µ�ѧ���ͼҳ�ɾ��
								if (!sopd.checkRelationByParentId(parent.getParentId())) {
									// ɾ���üҳ�
									pd.deleteParent(parent);
									UserDAO ud = new UserDAO();
									UserBean user = ud.getUser(userAccount);
									if (user != null) {
										ud.deleteUser(user);
									}
								}
								if (!sopd.checkRelationByStudentId(student.getStudentId())) {
									// ɾ����ѧ��
									sd.deleteStudent(student);
								}
							}
						}
						// ɾ���ð༶�¹����Ľ�ʦ
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
					// ɾ���ð༶
					cd.deleteClass(classBean);
				}
			}
		}
	}

	private void updateClass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schoolId = IdHelper.getSchoolId(request.getParameter("manageraccount"));
		// ��֤�Ƿ�Ϸ�
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// �Ϸ�
		// ��֤�ð༶�Ƿ����
		String classId = request.getParameter("classid");
		classId = IdHelper.toDBId(classId, schoolId);
		ClassDAO cd = new ClassDAO();
		ClassBean classBean = cd.getClass(classId);
		if (classBean == null) {
			request.setAttribute("error", "�ð༶������");
		}
		// �޸İ༶��Ϣ
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
		// ��֤�Ƿ�Ϸ�
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		} else {
			// �Ϸ���ѯ�༶
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
		// ��֤�Ƿ�Ϸ�
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// �Ϸ�
		// ��֤�ð༶�Ƿ����
		String classId = request.getParameter("classid");
		classId = IdHelper.toDBId(classId, schoolId);
		ClassDAO cd = new ClassDAO();
		ClassBean classBean = cd.getClass(classId);
		if (classBean == null) {
			request.setAttribute("error", "�ð༶������");
		}
		String studentName = request.getParameter("studentname");
		String studentSex = request.getParameter("studentsex");
		String userAccount = request.getParameter("useraccount");
		String parentName = request.getParameter("parentname");
		String userSex = request.getParameter("usersex");
		String studentId = request.getParameter("studentid");
		studentId = IdHelper.toDBId(studentId, schoolId);
		// ��֤�ö�ѧ��-�ҳ��Ƿ��ѱ����
		ParentDAO pd = new ParentDAO();
		ParentBean parent = pd.getParent(userAccount);
		StudentDAO sd = new StudentDAO();
		StudentBean student = sd.getStudent(studentId);
		StudentOfParentDAO sopd = new StudentOfParentDAO();
		if (parent != null) {
			// ѧ��-�ҳ��Դ���
			StudentOfParentBean bean = sopd.getStudentOfParent(studentId, parent.getParentId());
			if (bean != null) {
				request.setAttribute("error", "��ѧ��-�ҳ��Ѵ��ڣ��ҳ��ֻ��Ų�����ͬ��");
			}
		}
		// ���ѧ��������
		if (student == null) {
			student = new StudentBean();
			student.setStudentName(studentName);
			student.setStudentSex(studentSex);
			student.setStudentId(studentId);
			student.setClassId(classId);
			sd.createStudent(student);
		}
		// ����ҳ�������
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
		// ����
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
			request.setAttribute("error", "��������æ�����Ժ�����");
		}
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		String schoolId = IdHelper.getSchoolId(jo.getString("manageraccount"));
		// ��֤�Ƿ�Ϸ�
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		StudentDAO sd = new StudentDAO();
		ParentDAO pd = new ParentDAO();
		UserDAO ud = new UserDAO();
		StudentOfParentDAO sopd = new StudentOfParentDAO();
		// �Ϸ�
		JSONArray jsonArray = jo.getJSONArray("student");
		for (int i = 0; i < jsonArray.size(); i++) {
			// ��֤��ѧ���Ƿ����
			JSONObject object = jsonArray.getJSONObject(i);
			String studentId = object.getString("studentid");
			String userAccount = object.getString("useraccount");
			studentId = IdHelper.toDBId(studentId, schoolId);
			StudentBean student = sd.getStudent(studentId);
			if (student == null) {
				request.setAttribute("error", "��ѧ��������");
			}
			// ��֤�üҳ��Ƿ����
			ParentBean parent = pd.getParent(userAccount);
			if (parent == null) {
				request.setAttribute("error", "�üҳ�������");
			}
			// ��֤��ѧ��-�ҳ������Ƿ����
			StudentOfParentBean bean = sopd.getStudentOfParent(studentId, parent.getParentId());
			if (bean != null) {
				request.setAttribute("error", "��ѧ����üҳ���ƥ��");
			}
			// ɾ��ѧ��-�ҳ�����
			sopd.deleteStudentOfParent(bean);
			// �жϸüҳ����Ƿ���ѧ����û����Ѹ�ѧ����ͼҳ����µ�ѧ���ͼҳ�ɾ��
			if (!sopd.checkRelationByParentId(parent.getParentId())) {
				// ɾ����ѧ��
				sd.deleteStudent(student);
			}
			if (!sopd.checkRelationByStudentId(student.getStudentId())) {
				// ɾ���üҳ�
				pd.deleteParent(parent);
				UserBean user = ud.getUser(userAccount);
				if (user != null) {
					DynamicDAO dd = new DynamicDAO();
					// ɾ�����û����������ۺͶ�̬
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
		// ��֤�Ƿ�Ϸ�
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// �Ϸ�
		// ��֤�ð༶�Ƿ����
		String classId = request.getParameter("classid");
		classId = IdHelper.toDBId(classId, schoolId);
		ClassDAO cd = new ClassDAO();
		ClassBean classBean = cd.getClass(classId);
		if (classBean == null) {
			request.setAttribute("error", classId + "�ð༶������");
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
		// ���ѧ��������
		if (student == null) {
			request.setAttribute("error", studentId + "��ѧ��������");
		}
		student = new StudentBean();
		student.setStudentName(studentName);
		student.setStudentSex(studentSex);
		student.setStudentId(studentId);
		student.setClassId(classId);
		sd.updateStudent(student);
		// ����ҳ�������
		if (parent == null) {
			request.setAttribute("error", userAccount + "�üҳ�������");
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
		// ��֤�Ƿ�Ϸ�
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// �Ϸ�
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
		// ��֤�Ƿ�Ϸ�
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// �Ϸ�
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
		// ��֤�༶�Ƿ����
		ClassBean classBean = cd.getClass(classId);
		if (classBean == null) {
			request.setAttribute("error", IdHelper.toId(classId) + "�ð༶������");
		}
		// ��֤��ʦ�Ƿ����
		TeacherBean teacher = td.getTeacher(teacherId);
		if (teacher != null) {
			// ����ʦ���ڣ���֤��ʦ�༶��ϵ�Ƿ����
			TeacherOfClassBean bean = tocd.getTeacherOfClass(teacherId, classId);
			if (bean != null) {
				request.setAttribute("error", "�ý�ʦ" + IdHelper.toId(teacherId) + "�Ѵ����ڸð༶" + IdHelper.toId(classId));
			}
		} else {
			// ��ʦ������
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
			request.setAttribute("error", "��������æ�����Ժ�����");
		}
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		String schoolId = IdHelper.getSchoolId(jo.getString("manageraccount"));
		// ��֤�Ƿ�Ϸ�
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		TeacherDAO td = new TeacherDAO();
		ClassDAO cd = new ClassDAO();
		UserDAO ud = new UserDAO();
		TeacherOfClassDAO tocd = new TeacherOfClassDAO();
		// �Ϸ�
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
				request.setAttribute("error", IdHelper.toId(teacherId) + "�ý�ʦ������");
			}
			ClassBean classBean = cd.getClass(classId);
			if (classBean == null) {
				request.setAttribute("error", IdHelper.toId(classId) + "�ð༶������");
			}
			if (bean == null) {
				request.setAttribute("error", "���Ϸ�������");
			}
			tocd.deleteTeacherOfClass(bean);
			if (!tocd.checkRelationByTeacherId(teacherId)) {
				UserBean user = ud.getUser(teacher.getUserAccount());
				td.deleteTeacher(teacher);
				if (user != null) {
					DynamicDAO dd = new DynamicDAO();
					// ɾ�����û����������ۺͶ�̬
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
		// ��֤�Ƿ�Ϸ�
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
		// �Ϸ�
		// ��֤�°༶�Ƿ����
		ClassDAO cd = new ClassDAO();
		ClassBean classBean = cd.getClass(classId);
		if (classBean == null) {
			request.setAttribute("error", "�ð༶������");
		}
		// ��֤ԭ�༶�Ƿ����
		classBean = cd.getClass(oldClassId);
		if (classBean == null) {
			request.setAttribute("error", "���Ϸ�������");
		}
		// ��֤�ý�ʦ�Ƿ����
		TeacherDAO td = new TeacherDAO();
		TeacherBean teacher = td.getTeacher(teacherId);
		if (teacher == null) {
			request.setAttribute("error", "�ý�ʦ������");
		}
		// ���Ľ�ʦ��Ϣ
		teacher.setProject(project);
		teacher.setTeacherName(teacherName);
		td.updateTeacher(teacher);
		// ɾ��ԭ��ʦ-�༶������Ϣ
		TeacherOfClassDAO tocd = new TeacherOfClassDAO();
		TeacherOfClassBean bean = new TeacherOfClassBean();
		bean.setTeacherId(teacherId);
		bean.setClassId(oldClassId);
		tocd.deleteTeacherOfClass(bean);
		// �����½�ʦ-�༶������Ϣ
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
		// ��֤�Ƿ�Ϸ�
		if (schoolId == null || schoolId.equals("")) {
			RequestDispatcher view = request.getRequestDispatcher("404notfound.html");
			view.forward(request, response);
		}
		// �Ϸ�
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
			request.setAttribute("error", "��������æ�����Ժ�����");
		}
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		JSONArray jsonArray = jo.getJSONArray("school");
		SchoolDAO sd = new SchoolDAO();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String schoolId = jsonObject.getString("schoolid");
			SchoolBean school = sd.getSchool(Integer.valueOf(schoolId));
			if (school == null) {
				request.setAttribute("error", "��ѧУ������");
			} else {
				// ɾ����ѧУ�����İ༶
				ClassDAO cd = new ClassDAO();
				for (String classId : cd.getClassIdBySchoolId(Integer.valueOf(schoolId))) {
					ClassBean classBean = cd.getClass(classId);
					// ɾ���༶�е����ۺͶ�̬
					DynamicDAO dd = new DynamicDAO();
					for (String dynamicId : dd.getDynamicIdByClassId(classId)) {
						CommitmentDAO comd = new CommitmentDAO();
						comd.deleteCommitmentByDynamicId(Integer.valueOf(dynamicId));
						DynamicBean dynamic = new DynamicBean();
						dynamic.setDynamicId(Integer.valueOf(dynamicId));
						dd.deleteDynamic(dynamic);
					}
					// ɾ���༶�й����ļҳ�-ѧ������ʦ
					StudentDAO stud = new StudentDAO();
					List<StudentBean> studentlist = stud.getStudentByClassId(classId);
					StudentOfParentDAO sopd = new StudentOfParentDAO();
					ParentDAO pd = new ParentDAO();
					for (StudentBean student : studentlist) {
						List<Integer> parentIdList = sopd.getParentIdByStudentId(student.getStudentId());
						for (Integer parentId : parentIdList) {
							ParentBean parent = pd.getParentByParentId(parentId);
							String userAccount = parent.getUserAccount();
							// ɾ��ѧ��-�ҳ�����
							StudentOfParentBean bean = sopd.getStudentOfParent(student.getStudentId(), parentId);
							sopd.deleteStudentOfParent(bean);
							// �жϸüҳ����Ƿ���ѧ����û����Ѹ�ѧ����ͼҳ����µ�ѧ���ͼҳ�ɾ��
							if (!sopd.checkRelationByParentId(parent.getParentId())) {
								// ɾ����ѧ��
								pd.deleteParent(parent);
								UserDAO ud = new UserDAO();
								UserBean user = ud.getUser(userAccount);
								if (user != null) {
									ud.deleteUser(user);
								}
							}
							if (!sopd.checkRelationByStudentId(student.getStudentId())) {
								// ɾ���üҳ�
								stud.deleteStudent(student);
							}
						}
					}
					// ɾ���ð༶�¹����Ľ�ʦ
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
					// ɾ���ð༶
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
			request.setAttribute("error", "��ѧУ������");
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
			request.setAttribute("error", "��������æ�����Ժ�����");
		}
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		JSONArray jsonArray = jo.getJSONArray("manager");
		ManagerDAO md = new ManagerDAO();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String managerAccount = jsonObject.getString("manageraccount");
			ManagerBean manager = md.getManager(managerAccount);
			if (manager == null) {
				request.setAttribute("error", "�ù���Ա������");
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
		// ��֤�Ƿ�Ϸ�
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
		// ��֤�Ƿ�Ϸ�
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
					// ��֤�ö�ѧ��-�ҳ��Ƿ��ѱ����
					ParentDAO pd = new ParentDAO();
					ParentBean parent = pd.getParent(userAccount);
					StudentDAO sd = new StudentDAO();
					StudentBean student = sd.getStudent(studentId);
					StudentOfParentDAO sopd = new StudentOfParentDAO();
					if (parent != null) {
						// ѧ��-�ҳ��Դ���
						StudentOfParentBean bean = sopd.getStudentOfParent(studentId, parent.getParentId());
						if (bean != null) {
							request.setAttribute("error", "��ѧ��-�ҳ��Ѵ��ڣ��ҳ��ֻ��Ų�����ͬ��");
						}
					}
					// ���ѧ��������
					if (student == null) {
						student = new StudentBean();
						student.setStudentName(studentName);
						student.setStudentSex(studentSex);
						student.setStudentId(studentId);
						student.setClassId(classId);
						sd.createStudent(student);
					}
					// ����ҳ�������
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
					// ����
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
		// ��֤�Ƿ�Ϸ�
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
					// ��֤�༶�Ƿ����
					ClassBean classBean = cd.getClass(classId);
					if (classBean == null) {
						request.setAttribute("error", IdHelper.toId(classId) + "�ð༶������");
					}
					// ��֤��ʦ�Ƿ����
					TeacherBean teacher = td.getTeacher(teacherId);
					if (teacher != null) {
						// ����ʦ���ڣ���֤��ʦ�༶��ϵ�Ƿ����
						TeacherOfClassBean bean = tocd.getTeacherOfClass(teacherId, classId);
						if (bean != null) {
							request.setAttribute("error",
									"�ý�ʦ" + IdHelper.toId(teacherId) + "�Ѵ����ڸð༶" + IdHelper.toId(classId));
						}
					} else {
						// ��ʦ������
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
		// �����ҳ�������utf-8��
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
		// // ʱ��
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
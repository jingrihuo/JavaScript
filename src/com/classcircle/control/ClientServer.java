package com.classcircle.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classcircle.DAO.CommitmentDAO;
import com.classcircle.DAO.DynamicDAO;
import com.classcircle.DAO.DynamicViewDAO;
import com.classcircle.DAO.ParentUserViewDAO;
import com.classcircle.DAO.TeacherUserViewDAO;
import com.classcircle.DAO.UserDAO;
import com.classcircle.model.CommitmentBean;
import com.classcircle.model.DynamicBean;
import com.classcircle.model.DynamicView;
import com.classcircle.model.ParentUserView;
import com.classcircle.model.TeacherUserView;
import com.classcircle.model.Up;
import com.classcircle.model.UserBean;
import com.classcircle.tools.BaseException;
import com.classcircle.tools.IdHelper;
import com.classcircle.tools.JSONHelper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ClientServer
 */
@WebServlet("/ClientServer")
public class ClientServer extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acceptJSON = receiveJSON(request);
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		System.out.println("hello,world");
		try {
			if (method.equals("login")) {
				checkLogin(request, response, acceptJSON);
			}
			if (method.equals("userspace")) {
				userSpace(request, response, acceptJSON);
			}
			if (method.equals("updatepwd")) {
				updatePwd(request, response, acceptJSON);
			}
			if (method.equals("loaddynamic")) {
				loadDynamic(request, response, acceptJSON);
			}
			if (method.equals("insertdynamic")) {
				createDynamic(request, response, acceptJSON);
			}
			if (method.equals("deletedynamic")) {
				deleteDynamic(request, response, acceptJSON);
			}
			if (method.equals("insertcommitment")) {
				createCommitment(request, response, acceptJSON);
			}
			if (method.equals("deletecommitment")) {
				deleteCommitment(request, response, acceptJSON);
			}
			if (method.equals("dynamicup")) {
				dynamicUp(request, response, acceptJSON);
			}
			if (method.equals("cancelUp")) {
				cancelUp(request, response, acceptJSON);
			}
			if (method.equals("uploadfile")) {
				fileUpload(request, response, acceptJSON);
			}
			if (method.equals("uploadHead")) {
				headUpload(request, response, acceptJSON);
			}
			if (method.equals("uploadCover")) {
				coverUpload(request, response, acceptJSON);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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

	private String optimizeDynamicCode(String userAccount, String classId) throws BaseException {
		String result = "";
		UserDAO ud = new UserDAO();
		ParentUserViewDAO puvd = new ParentUserViewDAO();
		TeacherUserViewDAO tuvd = new TeacherUserViewDAO();
		System.out.println(userAccount);
		UserBean user = ud.getUser(userAccount);
		if (user == null) {
			throw new BaseException("����������");
		}
		List<ParentUserView> parentlist = puvd.getParentUser(userAccount, classId);
		List<TeacherUserView> teacherlist = tuvd.getTeacherUserView(userAccount, classId);
		if (teacherlist.size() <= 0 && parentlist.size() <= 0) {
			throw new BaseException("teacherlist and parentlist size <=0");
		}
		if (teacherlist.size() > 0) {
			for (TeacherUserView teacher : teacherlist) {
				result = teacher.getTeacherName() + "��ʦ";
			}
		} else if (parentlist.size() > 0) {
			for (ParentUserView parent : parentlist) {
				result = parent.getStudentName() + "�ļҳ�";
			}
		}
		return result;
	}

	private void checkLogin(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		System.out.println(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		// ��������
		JSONObject result = new JSONObject();
		UserDAO ud = new UserDAO();
		TeacherUserViewDAO tuvd = new TeacherUserViewDAO();
		ParentUserViewDAO puvd = new ParentUserViewDAO();
		JSONArray jsonArray = jo.getJSONArray("user");
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String userAccount = jsonObject.getString("userAccount");
		String userPwd = jsonObject.getString("userPwd");
		// ��֤�û��Ƿ�Ϸ�
		if (ud.checkUser(userAccount, userPwd)) {
			UserBean user = ud.getUser(userAccount);
			user.getUserAccount();
			List<TeacherUserView> teacherlist = tuvd.getTeacherUserView(userAccount);
			List<UserBean> userlist = new ArrayList<UserBean>();
			// �Ϸ������Ϊ��ʦ
			if (teacherlist.size() > 0) {
				for (TeacherUserView teacher : teacherlist) {
					UserBean temp = user;
					teacher.setClassId(IdHelper.toId(teacher.getClassId()));
					temp.setUserName(teacher.getTeacherName() + "��ʦ");
					temp.setClassId(teacher.getClassId());
					temp.setSchoolId(teacher.getSchoolId());
					temp.setClassName(teacher.getClassName());
					temp.setSchoolName(teacher.getSchoolName());
					if (teacher.getTeacherType().equals("������")) {
						temp.setUserType("master");
					} else {
						temp.setUserType("teacher");
					}
					userlist.add(temp);
				}
				result = JSONHelper.userListToJSON(userlist);
			}
			List<ParentUserView> parentlist = puvd.getParentUser(userAccount);
			// �Ϸ������Ϊ�ҳ�
			if (parentlist.size() > 0) {
				for (ParentUserView parent : parentlist) {
					UserBean temp = user;
					parent.setClassId(IdHelper.toId(parent.getClassId()));
					temp.setUserName(parent.getStudentName() + "�ļҳ�");
					temp.setClassId(parent.getClassId());
					temp.setSchoolId(parent.getSchoolId());
					temp.setClassName(parent.getClassName());
					temp.setSchoolName(parent.getSchoolName());
					temp.setUserType("parent");
					userlist.add(temp);
				}
				result = JSONHelper.userListToJSON(userlist);
			}
			result = JSONHelper.noErrorJSON(result);
		} else {
			// �û���ݲ��Ϸ�
			result = JSONHelper.errorJSON("�û������������");
		}
		System.out.println(result.toString());
		response.getWriter().write(result.toString());
	}

	private void userSpace(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		// ��������
		JSONObject result = new JSONObject();
		TeacherUserViewDAO tuvd = new TeacherUserViewDAO();
		ParentUserViewDAO puvd = new ParentUserViewDAO();
		UserDAO ud = new UserDAO();
		JSONArray jsonArray = jo.getJSONArray("user");
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String userAccount = jsonObject.getString("userAccount");
		String classId = jsonObject.getString("classId");
		String schoolId = jsonObject.getString("schoolId");
		classId = IdHelper.toDBId(classId, schoolId);
		// ҵ���߼�
		List<TeacherUserView> teacherlist = tuvd.getTeacherUserView(userAccount, classId);
		List<ParentUserView> parentlist = puvd.getParentUser(userAccount, classId);
		// ���ݳ���
		if (teacherlist.size() <= 0 && parentlist.size() <= 0) {
			response.getWriter().write(JSONHelper.errorJSON("����������").toString());
			throw new BaseException("teacherlist and parentlist size <=0");
		} else {
			// ��ʦ�û���Ϣ
			UserBean user = ud.getUser(userAccount);
			List<UserBean> userlist = new ArrayList<UserBean>();
			if (teacherlist.size() > 0) {
				for (TeacherUserView teacher : teacherlist) {
					UserBean temp = user;
					teacher.setClassId(IdHelper.toId(teacher.getClassId()));
					temp.setUserName(teacher.getTeacherName() + "��ʦ");
					temp.setClassId(teacher.getClassId());
					temp.setSchoolId(teacher.getSchoolId());
					temp.setClassName(teacher.getClassName());
					temp.setSchoolName(teacher.getSchoolName());
					if (teacher.getTeacherType().equals("������")) {
						temp.setUserType("master");
					} else {
						temp.setUserType("teacher");
					}
					userlist.add(temp);
				}
				result = JSONHelper.userListToJSON(userlist);
			}
			// �Ϸ������Ϊ�ҳ�
			if (parentlist.size() > 0) {
				for (ParentUserView parent : parentlist) {
					UserBean temp = user;
					parent.setClassId(IdHelper.toId(parent.getClassId()));
					temp.setUserName(parent.getStudentName() + "�ļҳ�");
					temp.setClassId(parent.getClassId());
					temp.setSchoolId(parent.getSchoolId());
					temp.setClassName(parent.getClassName());
					temp.setSchoolName(parent.getSchoolName());
					temp.setUserType("parent");
					userlist.add(temp);
				}
			}
			result = JSONHelper.noErrorJSON(result);
		}
		response.getWriter().write(result.toString());
	}

	private void updatePwd(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		// ��������
		System.out.println(acceptJSON);
		System.out.println("updatePwd");
		JSONObject result = new JSONObject();
		UserDAO ud = new UserDAO();
		JSONArray jsonArray = jo.getJSONArray("user");
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String userAccount = jsonObject.getString("userAccount");
		String userPwd = jsonObject.getString("userPwd");
		String userNewPwd = jsonObject.getString("userNewPwd");
		// ��֤ԭ����
		if (ud.checkUser(userAccount, userPwd)) {
			UserBean user = ud.getUser(userAccount);
			user.setUserPassword(userNewPwd);
			ud.updateUser(user);
			result = JSONHelper.noErrorJSON(result);
		} else {
			// ԭ�������
			result = JSONHelper.errorJSON("ԭ�������");
		}
		System.out.println(result.toString());
		response.getWriter().write(result.toString());
	}

	private void loadDynamic(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		// ��������
		JSONObject result = new JSONObject();
		String schoolId = jo.getString("schoolId");
		String classId = jo.getString("classId");
		classId = IdHelper.toDBId(classId, schoolId);
		String dynamicId = jo.getString("dynamicId");
		DynamicViewDAO dvd = new DynamicViewDAO();
		CommitmentDAO cd = new CommitmentDAO();
		try {
			// ���ض�̬
			List<DynamicView> list = dvd.loadDynamic(classId, Integer.valueOf(dynamicId));
			System.out.println(list.size());
			// ��Ӷ�̬����
			for (DynamicView dynamic : list) {
				dynamic.setCommitment(cd.getCommitmentList(dynamic.getDynamicId()));
				dynamic.setUserName(this.optimizeDynamicCode(dynamic.getUserAccount(), classId));
				// �����������
				for (CommitmentBean com : dynamic.getCommitment()) {
					com.setUserName(this.optimizeDynamicCode(com.getUserAccount(), classId));
					if (!com.getComUserId().equals("") && com.getComUserId() != null) {
						com.setComUserName(this.optimizeDynamicCode(com.getComUserId(), classId));
					}
				}
				// ��ӵ�������
				for (Up up : dynamic.getDynamicUp()) {
					up.setUserName(this.optimizeDynamicCode(up.getUserAccount(), classId));
				}
			}
			result = JSONHelper.dynamicListToJSON(list);
			JSONHelper.noErrorJSON(result);
			System.out.println(result.toString());
			response.getWriter().write(result.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.getWriter().write(JSONHelper.errorJSON("�������쳣").toString());
			throw new BaseException("���ݿ�����쳣(may be teacherlist and parentlist size <= 0)");
		}
	}

	private void createDynamic(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		// ��������
		JSONObject result = new JSONObject();
		JSONArray jsonArray = jo.getJSONArray("dynamic");
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String userAccount = jsonObject.getString("userAccount");
		String schoolId = jsonObject.getString("schoolId");
		String classId = jsonObject.getString("classId");
		classId = IdHelper.toDBId(classId, schoolId);
		String dynamicText = jsonObject.getString("dynamicText");
		String dynamicSrc = jsonObject.getString("dynamicSrc");
		// ������̬
		DynamicDAO dd = new DynamicDAO();
		DynamicBean dynamic = new DynamicBean();
		dynamic.setClassId(classId);
		dynamic.setDynamicSrc(dynamicSrc);
		dynamic.setDynamicText(dynamicText);
		dynamic.setUserAccount(userAccount);
		dd.createDynamic(dynamic);
		// ��������
		result = JSONHelper.noErrorJSON(result);
		response.getWriter().write(result.toString());
	}

	private void deleteDynamic(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, Exception {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		// ��������
		JSONObject result = new JSONObject();
		JSONArray jsonArray = jo.getJSONArray("dynamic");
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String dynamicId = jsonObject.getString("dynamicId");
		DynamicDAO dd = new DynamicDAO();
		CommitmentDAO cd = new CommitmentDAO();
		// ɾ���󶨸ö�̬������
		cd.deleteCommitmentByDynamicId(Integer.valueOf(dynamicId));
		// ɾ���ö�̬
		DynamicBean dynamic = dd.getDynamic(Integer.valueOf(dynamicId));
		if (dynamic != null) {
			dd.deleteDynamic(dynamic);
		}
		// ��������
		result = JSONHelper.noErrorJSON(result);
		response.getWriter().write(result.toString());
	}

	private void createCommitment(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		// ��������
		JSONObject result = new JSONObject();
		JSONArray jsonArray = jo.getJSONArray("commitment");
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String dynamicId = jsonObject.getString("dynamicId");
		String userAccount = jsonObject.getString("userAccount");
		String comText = jsonObject.getString("comText");
		String comUserId = jsonObject.getString("comUserId");
		// ��������
		CommitmentDAO cd = new CommitmentDAO();
		CommitmentBean com = new CommitmentBean();
		com.setComText(comText);
		com.setComUserId(comUserId);
		com.setDynamicId(Integer.valueOf(dynamicId));
		com.setUserAccount(userAccount);
		cd.createCommitment(com);
		// ��ȡ�����۵Ķ�̬���ط�����
		com = cd.getCommitment(Integer.valueOf(dynamicId), userAccount);
		result.put("comId", com.getComId());
		// ��������
		result = JSONHelper.noErrorJSON(result);
		response.getWriter().write(result.toString());
	}

	private void deleteCommitment(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		// ��������
		JSONObject result = new JSONObject();
		JSONArray jsonArray = jo.getJSONArray("commitment");
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String comId = jsonObject.getString("comId");
		// ɾ������
		CommitmentDAO cd = new CommitmentDAO();
		CommitmentBean com = cd.getCommitment(Integer.valueOf(comId));
		if (com == null) {
			result = JSONHelper.errorJSON("���ݳ���");
		} else {
			com.setComId(Integer.valueOf(comId));
			cd.deleteCommitment(com);
			result = JSONHelper.noErrorJSON(result);
		}
		// ��������
		response.getWriter().write(result.toString());
	}

	private void dynamicUp(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		// ��������
		JSONObject result = new JSONObject();
		JSONArray jsonArray = jo.getJSONArray("dynamicup");
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String dynamicId = jsonObject.getString("dynamicId");
		String userAccount = jsonObject.getString("userAccount");
		// ����
		DynamicDAO dd = new DynamicDAO();
		DynamicBean dynamic = dd.getDynamic(Integer.valueOf(dynamicId));
		if (dynamic == null) {
			result = JSONHelper.errorJSON("���ݳ���");
		} else {
			if (dynamic.getDynamicUp() == null) {
				dynamic.setDynamicUp("");
			}
			if (!dynamic.getDynamicUp().equals("")) {
				dynamic.setDynamicUp(IdHelper.toDBId(userAccount, dynamic.getDynamicUp()));
			} else {
				dynamic.setDynamicUp(userAccount);
			}
			dd.updateDynamic(dynamic);
			result = JSONHelper.noErrorJSON(result);
		}
		response.getWriter().write(result.toString());
	}

	private void cancelUp(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		// ��������
		JSONObject result = new JSONObject();
		String userAccount = jo.getString("userAccount");
		int dynamicId = jo.getInt("dynamicId");
		DynamicDAO dd = new DynamicDAO();
		DynamicBean dynamic = dd.getDynamic(dynamicId);
		int count = 0;
		int tag = 0;
		List<Up> upList = IdHelper.dynamicUpToAccount(dynamic.getDynamicUp());
		for (Up up : upList) {
			if (up.getUserAccount().equals(userAccount))
				tag = 1;
			if (tag == 0) {
				count++;
			}
		}
		if (tag == 1) {
			upList.remove(count);
		}
		System.out.println("6666666");
		System.out.println(IdHelper.accountToDynamicUp(upList));
		dynamic.setDynamicUp(IdHelper.accountToDynamicUp(upList));
		dd.updateDynamic(dynamic);
		result = JSONHelper.noErrorJSON(result);
		response.getWriter().write(result.toString());
	}

	private void fileUpload(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		JSONObject result = new JSONObject();
		JSONArray jsonArray = jo.getJSONArray("file");
		String userAccount = jo.getString("userAccount");
		String dynamicSrc = "";
		// ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(System.currentTimeMillis());
		String time = sdf.format(date);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String fileType = object.getString("fileType");
			String file = object.getString("file");
			String url = userAccount + time + (i + 1) + "." + fileType;
			dynamicSrc += url + ",";
			File f = new File("webapps/ClassCircle/file/" + url);
			f.createNewFile();
			System.out.println("createNewFile success");
			if (f.exists()) {
				byte[] b = Base64.decode(file);
				FileOutputStream fos = new FileOutputStream(f);
				fos.write(b);
				fos.close();
			}
		}
		dynamicSrc = dynamicSrc.substring(0, dynamicSrc.length() - 1);
		result.put("dynamicSrc", dynamicSrc);
		// ��������
		System.out.println("success");
		result = JSONHelper.noErrorJSON(result);
		System.out.println(result.toString());
		response.getWriter().write(result.toString());
	}

	private void headUpload(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		JSONObject result = new JSONObject();
		String userAccount = jo.getString("userAccount");
		String uploadHead = jo.getString("uploadHead");
		// ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(System.currentTimeMillis());
		String time = sdf.format(date);
		String url = userAccount + time + ".JPEG";
		File f = new File("webapps/ClassCircle/head/" + url);
		f.createNewFile();
		System.out.println("createNewFile success");
		if (f.exists()) {
			byte[] b = Base64.decode(uploadHead);
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(b);
			fos.close();
		}
		// �޸�����
		UserDAO ud = new UserDAO();
		UserBean user = ud.getUser(userAccount);
		user.setUserHeadSrc(url);
		ud.updateUser(user);
		// ��������
		System.out.println("success");
		result = JSONHelper.noErrorJSON(result);
		System.out.println(result.toString());
		response.getWriter().write(result.toString());
	}

	private void coverUpload(HttpServletRequest request, HttpServletResponse response, String acceptJSON)
			throws IOException, BaseException {
		JSONObject jo = JSONObject.fromObject(acceptJSON);
		// ��֤���ݰ��Ƿ�Ϸ�
		if (!JSONHelper.checkJSON(jo)) {
			response.getWriter().write(JSONHelper.errorJSON("���ݰ���֤����").toString());
			throw new BaseException("���ݰ���֤����");
		}
		// �Ϸ�
		JSONObject result = new JSONObject();
		String userAccount = jo.getString("userAccount");
		String uploadHead = jo.getString("uploadCover");
		// ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(System.currentTimeMillis());
		String time = sdf.format(date);
		String url = userAccount + time + ".JPEG";
		File f = new File("webapps/ClassCircle/cover/" + url);
		f.createNewFile();
		System.out.println("createNewFile success");
		if (f.exists()) {
			byte[] b = Base64.decode(uploadHead);
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(b);
			fos.close();
		}
		// �޸�����
		UserDAO ud = new UserDAO();
		UserBean user = ud.getUser(userAccount);
		user.setUserCoverSrc(url);
		ud.updateUser(user);
		// ��������
		System.out.println("success");
		result = JSONHelper.noErrorJSON(result);
		System.out.println(result.toString());
		response.getWriter().write(result.toString());
	}
}
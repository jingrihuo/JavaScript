<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<form action="webservlet?method=xlsupload&type=class" method="post" >  
           		   <input name="manageraccount" id="field4" class="required" style="display: none" value="<%=request.getParameter("manageraccount")%>"/>
                   <input type="submit" name="submit" value="提交" />  
                  
    </form>  
</body>
</html>
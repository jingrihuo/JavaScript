<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>传输中</title>
<script src="bower_components/jquery/jquery.min.js"></script>
</head>
<body>
	<script>
	
		top.location.href="webservlet?method=backup&manageraccount=<%=request.getParameter("manageraccount")%>";
			
	
	
	</script>
</body>
</html>
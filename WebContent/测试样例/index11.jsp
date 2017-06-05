<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  
            + request.getServerName() + ":" + request.getServerPort()  
            + path + "/";  
%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
<head>  
<base href="<%=basePath%>">  
  
<title>My JSP 'index.jsp' starting page</title>  
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0">  
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
<meta http-equiv="description" content="This is my page">  
<!-- 
    <link rel="stylesheet" type="text/css" href="styles.css"> 
    -->  
</head>  
  
<body>  
    <input type="button" onclick="validate()" value="请求并解析JSON">  
    <div id="div"></div>  
    <table border="1" style="width:300px;height:200px;" id="table">  
        <tr id="tr" border="1">  
            <td>1</td>  
            <td>1</td>  
            <td>1</td>  
        </tr>  
  
        <tr border="1">  
            <td>1</td>  
            <td>1</td>  
            <td>1</td>  
        </tr>  
    </table>  
    <script type="text/javascript">  
    
     function validate(){  
     var div=document.getElementById("div");  
     var xmlhttp;  
     var table=document.getElementById('table');  
  
        if (window.XMLHttpRequest) {  
            xmlhttp = new XMLHttpRequest();  
        } else {  
            xmlhttp = new ActiveObject("Microsoft.XMLHTTP");  
        }  
        xmlhttp.onreadystatechange = function() {  
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {  
                var x = xmlhttp.responseText  
                div.innerHTML = x;  
                var sd = eval("(" + x + ")");  
  
                var arr = new Array();  
                var i = 0;
                console.log(sd.as);  
                for ( var a in sd.person) {//获取有几个person  
                    var count = 0;//计算person属性值的个数  
                    i++;  
                    for ( var item in sd.person[a]) {  
                        count++;  
                        var p = sd.person[a][item];//得到属性值的内容  
                        table.rows[i - 1].cells[count - 1].innerHTML = p;//把内容填向table表格  
                    }  
                }  
            }  
        }  
        var url = "json";  
        xmlhttp.open("POST", url, false);  
        xmlhttp.send();  
  
    }  
</script>  
</body>  
</body>  
</html>  
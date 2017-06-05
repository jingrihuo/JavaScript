<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE >
<html>
<head>
<script src="scriptaculous/lib/prototype.js" type="text/javascript"></script>
<script src="scriptaculous/src/effects.js" type="text/javascript"></script>
<script type="text/javascript" src="validation.js"></script>

<link title="style1" rel="stylesheet" href="style4.css" type="text/css" />
<link title="style2" rel="alternate stylesheet" href="style2.css" type="text/css" />
<link title="style3" rel="alternate stylesheet" href="style3.css" type="text/css" />
<script>
	var manageraccount2=<%=request.getParameter("manageraccount")%>;
	if(manageraccount2==null){
		self.location='404.html'; 
		}
	</script>
<script>
function check(){
    var classid = document.getElementById('studentid').value;
    var check = true;
    if(classid == ''){
         document.getElementById('tip').innerHTML = "";
        document.getElementById('tip').innerHTML = "班级编号不能为空";
        check = false;
        return check;
    }
    if(classid.length>12){
        document.getElementById('tip').innerHTML = "";
       document.getElementById('tip').innerHTML = "班级编号长度不能超过12个字符";
       check = false;
       return check;
   }
    return check;
} 
</script>
</head>
<body>
    <div class="style_changer">
    	<div class="style_changer_text">Themes:</div>
        <input type="submit" value="1" onclick="setActiveStyleSheet('style1');" />
        <input type="submit" value="2" onclick="setActiveStyleSheet('style2');" />
        <input type="submit" value="3" onclick="setActiveStyleSheet('style3');" />
    </div>

	<div class="form_content">
    <form id="test" action="webservlet?method=updateclass" method="post" onsubmit="return check()">
    <fieldset>
        <legend>修改班级信息</legend>
        <div class="form-row">
            <div class="field-label"><label for="field1">班级编号</label>:</div>
            <div class="field-widget"><input name="classid" id="field1" class="required" value="<%=request.getParameter("classid")%>" disabled="true"/></div>
            <div class="field-widget"><input type="hidden" name="classid" id="field1" class="required" value="<%=request.getParameter("classid") %>" /></div>
        	<span id="tip"></span>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field2">班级名称</label>:</div>
            <div class="field-widget"><input name="classname" id="field2" class="required" value="<%=request.getParameter("classname")%>"/></div>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field3">班级描述</label>:</div>
            <div class="field-widget"><input name="classdetail" id="field3" class="required" value="<%=request.getParameter("classdetail")%>"/></div>
        </div>
        <input name="manageraccount" id="field4" class="required" style="display: none" value="<%=request.getParameter("manageraccount")%>"/>
    </fieldset>
    <input type="submit" class="submit" value="Submit" /> 
    </form>
    </div>
    
</body>
</html>
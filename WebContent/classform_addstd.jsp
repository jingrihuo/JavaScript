<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    var studentid = document.getElementById('studentid').value;
    var check = true;
    if(studentid == ''){
         document.getElementById('tip').innerHTML = "";
        document.getElementById('tip').innerHTML = "学号不能为空";
        check = false;
        return check;
    }
    if(studentid.length>28){
        document.getElementById('tip').innerHTML = "";
       document.getElementById('tip').innerHTML = "学号长度不能超过28个字符";
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
    <form id="test" action="webservlet?method=insertstudent" method="post" onsubmit="return check()">
    <fieldset>
        <legend>添加学生-家长</legend>
        <div class="form-row">
            <div class="field-label"><label for="field1">学号</label>:</div>
            <div class="field-widget"><input name="studentid" id="field1" class="required"/></div>
            <span id="tip"></span>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field2">班级编号</label>:</div>
            <div class="field-widget"><input name="classid" id="field2" class="required"/></div>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field3">学生名字</label>:</div>
            <div class="field-widget"><input name="studentname" id="field3" class="required" /></div>
        </div>
        <div class="form-row">
            <div class="field-label"><label for="field1">手机号</label>:</div>
            <div class="field-widget"><input name="useraccount" id="field4" class="required"/></div>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field2">家长名字</label>:</div>
            <div class="field-widget"><input name="parentname" id="field5" class="required"/></div>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field3">学生性别</label>:</div>
            <div class="field-widget"><input name="studentsex" id="field6" class="required" /></div>
        </div>
        <div class="form-row">
            <div class="field-label"><label for="field1">家长性别</label>:</div>
            <div class="field-widget"><input name="usersex" id="field7" class="required"/></div>
        </div>
        
        <input name="manageraccount" id="field8" class="required" style="display: none" value="<%=request.getParameter("manageraccount")%>"/>
    </fieldset>
    <input type="submit" class="submit" value="Submit" /> 
    </form>
    </div>
    

</body>
</html> 
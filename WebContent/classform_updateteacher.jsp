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
    var teacherid = document.getElementById('studentid').value;
    var check = true;
    if(teacherid == ''){
         document.getElementById('tip').innerHTML = "";
        document.getElementById('tip').innerHTML = "教师账号不能为空";
        check = false;
        return check;
    }
    if(teacherid.length>28){
        document.getElementById('tip').innerHTML = "";
       document.getElementById('tip').innerHTML = "教师账号长度不能超过28个字符";
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
    <form id="test" action="webservlet?method=updateteacher" method="post" onsubmit="return check()">
    <fieldset>
        <legend>修改教师</legend>
        <div class="form-row">
            <div class="field-label"><label for="field1">教师编号</label>:</div>
            <div class="field-widget"><input name="teacherid" id="field1" class="required" value="<%=request.getParameter("teacherid")%>" disabled="true"/></div>
            <div class="field-widget"><input type="hidden" name="teacherid" id="field1" class="required" value="<%=request.getParameter("teacherid")%>"/></div>
       		<span id="tip"></span>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field2">手机号</label>:</div>
            <div class="field-widget"><input name="useraccount" id="field2" class="required" value="<%=request.getParameter("useraccount")%>" disabled="true"/></div>
            <div class="field-widget"><input type="hidden" name="useraccount" class="required" value="<%=request.getParameter("useraccount")%>"/></div>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field3">教师名称</label>:</div>
            <div class="field-widget"><input name="teachername" id="field3" class="required" value="<%=request.getParameter("teachername")%>"/></div>
        </div>
        <div class="form-row">
            <div class="field-label"><label for="field1">教师类型</label>:</div>
            <div class="field-widget"><input name="teachertype" id="field4" class="required" value="<%=request.getParameter("teachertype")%>"/></div>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field2">任课班级</label>:</div>
            <div class="field-widget"><input name="classid" id="field5" class="required" value="<%=request.getParameter("classid")%>"/></div>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field2">原任课班级</label>:</div>
            <div class="field-widget"><input name="classid" id="fieldim" class="required" value="<%=request.getParameter("classid")%>" disabled="true"/></div>
            <input type="hidden" name="oldclassid" id="bb">
  			<script>
  			document.getElementById("bb").value=document.getElementById("fieldim").value;
  			</script>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field3">教学课程</label>:</div>
            <div class="field-widget"><input name="project" id="field6" class="required" value="<%=request.getParameter("project")%>"/></div>
        </div>
        <div class="form-row">
            <div class="field-label"><label for="field1">教师性别</label>:</div>
            <div class="field-widget"><input name="usersex" id="field7" class="required" value="<%=request.getParameter("usersex")%>"/></div>
        </div>

        <input name="manageraccount" id="field8" class="required" style="display: none" value="<%=request.getParameter("manageraccount")%>"/>
    </fieldset>
    <input type="submit" class="submit" value="Submit" /> 
    </form>
    </div>
    

</body>
</html> 
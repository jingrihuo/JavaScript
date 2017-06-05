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
    var classid = document.getElementById('studentid').value;
    var check = true;
    if(classid == ''){
         document.getElementById('tip').innerHTML = "";
        document.getElementById('tip').innerHTML = "学校名称不能为空";
        check = false;
        return check;
    }
    if(classid.length>60){
        document.getElementById('tip').innerHTML = "";
       document.getElementById('tip').innerHTML = "学校名称长度不能超过60个字符";
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
    <form id="test" action="webservlet?method=insertschool" method="post" onsubmit="return check()">
    <fieldset>
        <legend>添加学校信息</legend>
        
        <div class="form-row">
            <div class="field-label"><label for="field2">学校名称</label>:</div>
            <div class="field-widget"><input name="schoolname" id="field2" class="required"/></div>
            <span id="tip"></span>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field3">学校描述</label>:</div>
            <div class="field-widget"><input name="schooldetail" id="field3" class="required" /></div>
        </div>
       
    </fieldset>
    <input type="submit" class="submit" value="Submit" /> 
    </form>
    </div>
    

</body>
</html> 
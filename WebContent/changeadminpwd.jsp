<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE >
<html>
<head>
<script src="scriptaculous/lib/prototype.js" type="text/javascript"></script>
<script src="scriptaculous/src/effects.js" type="text/javascript"></script>
<script type="text/javascript" src="validation.js"></script>
<script src="bower_components/jquery/jquery.min.js"></script>
<script type="text/javascript">
//<![CDATA[
<!--
// Alternative Style: Working With Alternate Style Sheets
// http://www.alistapart.com/articles/alternate/
//-->
//]]>
</script>
<link title="style1" rel="stylesheet" href="style4.css" type="text/css" />
<link title="style2" rel="alternate stylesheet" href="style2.css" type="text/css" />
<link title="style3" rel="alternate stylesheet" href="style3.css" type="text/css" />
<script>
function check(){
    var pwd1 = document.getElementById('field3').value;
    var pwd2 = document.getElementById('field4').value;
    var check = true;
    if(pwd1 == ''){
         document.getElementById('tip1').innerHTML = "";
        document.getElementById('tip1').innerHTML = "重置密码不能为空";
        check = false;
        return check;
    }
    if(pwd2 == ''){
        document.getElementById('tip2').innerHTML = "";
       document.getElementById('tip2').innerHTML = "重置密码不能为空";
       check = false;
       return check;
   }
    if(pwd1!=pwd2){
       alert("前后两次输入密码不一致");
       check = false;
       return check;
   }
    return check;
}  
function checkpwd(){
	var check = check();
		if(check){
		$.ajax({
       	 	type:"post",
        	url:"webservlet?method=updateclass",
        	data:"&manageraccount="+document.getElementById("field1").value+"&managerpassword="+document.getElementById("field2").value+"&newmanagerpassword="+document.getElementById("field3").value,
        	error: function () {
            	alert("现密码错误！");
        	}
    	})
	}
}
function checkall(){
	check();
	checkpwd();
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
   
    <fieldset>
        <legend>修改密码</legend>
        <div class="form-row">
            <div class="field-label"><label for="field1">管理员账号</label>:</div>
            <div class="field-widget"><input name="manageraccount" id="field1" class="required" value="<%=request.getParameter("manageraccount")%>" disabled="true"/></div>
             <div class="field-widget"><input type="hidden" name="manageraccount" id="field1" class="required" value="<%=request.getParameter("manageraccount")%>" /></div>
        	
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field2">请输入现密码</label>:</div>
            <div class="field-widget"><input name="managerpassword" id="field2" class="required" /></div>
        </div>
        
        <div class="form-row">
            <div class="field-label"><label for="field3">请输入重置密码</label>:</div>
            <input type="password" name="newmanagerpassword" id="field3" class="required"  />
            <span id="tip1"></span>
        </div>
        <div class="form-row">
            <div class="field-label"><label for="field3">请再次输入重置密码</label>:</div>
            <input type="password" name="managerpassword" id="field4" class="required"  />
            <span id="tip2"></span>
        </div>
        
    </fieldset>
        <input type="submit" class="submit" value="Submit" onclick="checkall()"/> 
    
    </div>
    <p id="mask" style="display:none;">
    	本页面将于5秒钟后自动关闭！
    </p>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>教师管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description"
	content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
<meta name="author" content="Muhammad Usman">

<!-- The styles -->
<link id="bs-css" href="css/bootstrap-cerulean.min.css" rel="stylesheet">

<link href="css/charisma-app.css" rel="stylesheet">
<link href='bower_components/fullcalendar/dist/fullcalendar.css'
	rel='stylesheet'>
<link href='bower_components/fullcalendar/dist/fullcalendar.print.css'
	rel='stylesheet' media='print'>
<link href='bower_components/chosen/chosen.min.css' rel='stylesheet'>
<link href='bower_components/colorbox/example3/colorbox.css'
	rel='stylesheet'>
<link href='bower_components/responsive-tables/responsive-tables.css'
	rel='stylesheet'>
<link
	href='bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css'
	rel='stylesheet'>
<link href='css/jquery.noty.css' rel='stylesheet'>
<link href='css/noty_theme_default.css' rel='stylesheet'>
<link href='css/elfinder.min.css' rel='stylesheet'>
<link href='css/elfinder.theme.css' rel='stylesheet'>
<link href='css/jquery.iphone.toggle.css' rel='stylesheet'>
<link href='css/uploadify.css' rel='stylesheet'>
<link href='css/animate.min.css' rel='stylesheet'>
<link href="css/absolute.css" rel="stylesheet">


<!-- jQuery -->
<script src="bower_components/jquery/jquery.min.js"></script>

<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon" href="img/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="style.css">
<script src="js/jquery.min.js"></script>
<script src="js/teacher.js"></script>
<script src="js/jquery.page1.js"></script>
<style>
	.re {
		width: 90%;
	}
</style>

</head>


<body>

	<script type="text/javascript">
		function timedCount(id) {
        document.getElementById(id).innerHTML="已在线"+b+":"+c;
        c=c+1;
        if (c==60) 
            {c = c % 60;b += 1};
        setTimeout(function(){timedCount('demo');}, 1000);
    }

		window.onload = function validate() {
			var count;
			var div = document.getElementById("div");
			var xmlhttp;
			var table = document.getElementById('table');

			if (window.XMLHttpRequest) {
				xmlhttp = new XMLHttpRequest();
			} else {
				xmlhttp = new ActiveObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var x = xmlhttp.responseText

					var sd = eval("(" + x + ")");

					var arr = new Array();
					var i = 0;
					count = sd.count;
					$(document).Table('.table_body', 're', count, 10, sd,
							[ '序号', '选中情况', '班级编号', '班级名称', '教师编号','教师手机号','教师姓名' , '教师性别','教师类型','任课课程'],
							[ [ , , ], [ , , ], [ , , ] ]);
					$(document).PaginBar('page_navigation', 'table_body', 20);

					}
				}
			var url = "webservlet?method=searchteacher&manageraccount=<%=request.getParameter("manageraccount")%>";
			xmlhttp.open("POST", url, false);
			xmlhttp.send();

		}
		/*function sendAjax() {
			//构造表单数据
			var formData = new FormData();
			formData.append('studentid', $("#f1").value);
			formData.append('studentname', $("#f2").value);
			formData.append('classid', $("#f3").value);
			formData.append('parentname', $("#f4").value);
			formData.append('manageraccount', $("#hidden1").value);
			//创建xhr对象 
			var xhr = new XMLHttpRequest();
			//设置xhr请求的超时时间
			xhr.timeout = 3000;
			//设置响应返回的数据格式
			xhr.responseType = "json";
			//创建一个 post 请求，采用异步
			xhr.open('POST', 'webservlet?method=searchteacher', true);
			//注册相关事件回调处理函数
			xhr.onreadystatechange = function() {
				if (this.status == 200 || this.status == 304) {
					//删除原有元素再次添加元素
					var box = document.getElementById("table_by");
					var box2 = document.getElementById("page_navigation");
					$(box).children(selector).remove();
					$(box2).children(selector).remove();

					var x = xmlhttp.responseText

					var sd = eval("(" + x + ")");

					var arr = new Array();
					var i = 0;
					$(document).Table('.table_body', 're', count, 10, sd,
							[ '序号', '选中情况', '班级编号', '班级名称', '教师编号','教师手机号','教师姓名' , '教师性别','教师类型','任课课程'],
							[ [ , , ], [ , , ], [ , , ] ]);
					$(document).PaginBar('page_navigation', 'table_body', 20);

				}
			};

			//发送数据
			xhr.send(formData);
		}*/
		function sendAjax() {

			var count;
			var div = document.getElementById("div");
			var xmlhttp2;
			var table = document.getElementById('table');

			
			xmlhttp2 = new XMLHttpRequest();
			
			xmlhttp2.onreadystatechange = function() {
				if (xmlhttp2.readyState == 4 && xmlhttp2.status == 200) {
					var box = document.getElementById("table_by");
					var box2 = document.getElementById("page_navigation");
					$(box).children().remove();
					$(box2).children().remove();
					
					var x = xmlhttp2.responseText

					var sd = eval("(" + x + ")");

					var arr = new Array();
					var i = 0;
					count = sd.count;
					$(document).Table('.table_body', 're', count, 10, sd,
							[ '序号', '选中情况', '班级编号', '班级名称', '教师编号','教师手机号','教师姓名' , '教师性别','教师类型','任课课程'],
							[ [ , , ], [ , , ], [ , , ] ]);
					$(document).PaginBar('page_navigation', 'table_body', 20);
				}
			}
			var url = "webservlet?method=searchteacher&classid="+document.getElementById("f1").value+"&classname="+document.getElementById("f2").value+"&teacherid="+document.getElementById("f3").value+"&teachername="+document.getElementById("f4").value+"&teachertype="+document.getElementById("f5").value+"&project="+document.getElementById("f6").value+"&manageraccount="+document.getElementById("hidden1").value;
			xmlhttp2.open("POST", url, false);
			xmlhttp2.send();
		}
		//批量全选
		function manycheck() {
			$(".in1").prop("checked","checked");
		}
		//删除选中的td
		function deleteteacher() {
			/* str = $('input[type="checkbox"]:checked').map(function() {

				return $(this).parent().next().text(); // 根据checkbox定位到后面的td，然后使用text()函数获取内容

			}).get().join(", "); */
			//获取studentid
			str = $(":checkbox:checked").map(function() {

                return $(this).next('td').text();  // 根据checkbox定位到后面的td，然后使用text()函数获取内容

            }).get().join(",");   // 获取内容数组并拼接为字符串
            var str2 = str.split(',');
            //获取家长编号
            str3 = $(":checkbox:checked").map(function() {
				
                return $(this).next('td').next('td').next('td').text();  // 根据checkbox定位到后面的td，然后使用text()函数获取内容

            }).get().join(",");   // 获取内容数组并拼接为字符串
            var str4 = str3.split(',');


			// 获取内容数组并拼接为字符串
			/* console.log(str);
			str = $(":checkbox:checked").map(function() {

                return $(this).parent().siblings('td').text();  // 根据checkbox定位到后面的td，然后使用text()函数获取内容

            }).get().join(", ");   // 获取内容数组并拼接为字符串
 */

			var json ="{" +  "\"manageraccount\"" +":"
					+"\"<%=request.getParameter("manageraccount")%>\"" +","+ "\"teacher\"" + ":"
					+ "["

			for (var i = 0; i < str2.length; i++) {
				json += "{" + "\"teacherid\"" + ":";
				json += "\"" + str4[i] + "\"" + ",";
				json += "\"classid\"" + ":";
				json += "\"" + str2[i] + "\"";
				json += "}";
				if(i!=str2.length-1)
					json = json + ",";
			}
			json += "]" + "}";
			$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				dataType : null,
				url : "webservlet?method=deleteteacher",//传入后台的地址/方法
				data : json,//参数，这里是一个json语句
				success : function() {
					window.location.reload();
				},
				error : function(err) {

				}
			});
		}
		//批量全不选
		function removecheck() {
			$(".in1").removeAttr("checked");
		}
		function fn3(){
			window.open("classform_updateteacher.jsp?manageraccount=<%=request.getParameter("manageraccount")%>&teacherid="+$(":checkbox:checked").next('td').next('td').next('td').text()
					+"&useraccount="+$(":checkbox:checked").next('td').next('td').next('td').next('td').text()
					+"&teachername="+$(":checkbox:checked").next('td').next('td').next('td').next('td').next('td').text()
			+"&teachertype="+$(":checkbox:checked").next('td').next('td').next('td').next('td').next('td').next('td').next('td').text()
			+"&classid="+$(":checkbox:checked").next('td').text()
			+"&project="+$(":checkbox:checked").next('td').next('td').next('td').next('td').next('td').next('td').next('td').next('td').text()
			+"&usersex="+$(":checkbox:checked").next('td').next('td').next('td').next('td').next('td').next('td').text()
			+"&classname="+$(":checkbox:checked").next('td').text(),"_blank"); 
		}
	</script>
</head>

<body>
<script>
	var manageraccount2=<%=request.getParameter("manageraccount")%>;
	if(manageraccount2==null){
		self.location='404.html'; 
		}
	</script>
	<!-- topbar starts -->

	<div class="navbar navbar-default" role="navigation">

		<div class="navbar-inner">
			<button type="button" class="navbar-toggle pull-left animated flip">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.jsp"> <img
				alt="Charisma Logo" src="img/logo20.png" class="hidden-xs" /> <span>Charisma</span></a>

			<!-- user dropdown starts -->
			<div class="btn-group pull-right">
				<button class="btn btn-default dropdown-toggle"
					data-toggle="dropdown">
					<i class="glyphicon glyphicon-user"></i><span
						class="hidden-sm hidden-xs" style="display: none"> <%
 	String manageraccount = (String) request.getParameter("manageraccount");
 	out.print(manageraccount);
 %></span> <span class="hidden-sm hidden-xs2"> <%
 	String username = (String) request.getParameter("username");
 	out.print(username);
 %>
					</span> <span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a href="changeadminpwd.jsp?manageraccount=<%=request.getParameter("manageraccount")%>">修改密码</a></li>
					<li class="divider"></li>
					<li><a href="login.html">登出</a></li>
				</ul>
			</div>
			<!-- user dropdown ends -->

			<!-- theme selector starts -->
			<div class="btn-group pull-right theme-container animated tada">
				<button class="btn btn-default dropdown-toggle"
					data-toggle="dropdown">
					<i class="glyphicon glyphicon-tint"></i><span
						class="hidden-sm hidden-xs">改变样式/皮肤</span> <span class="caret"></span>
				</button>
				<ul class="dropdown-menu" id="themes">
					<li><a data-value="classic" href="#"><i class="whitespace"></i>
							Classic</a></li>
					<li><a data-value="cerulean" href="#"><i
							class="whitespace"></i> Cerulean</a></li>
					<li><a data-value="cyborg" href="#"><i class="whitespace"></i>
							Cyborg</a></li>
					<li><a data-value="simplex" href="#"><i class="whitespace"></i>
							Simplex</a></li>
					<li><a data-value="darkly" href="#"><i class="whitespace"></i>
							Darkly</a></li>
					<li><a data-value="lumen" href="#"><i class="whitespace"></i>
							Lumen</a></li>
					<li><a data-value="slate" href="#"><i class="whitespace"></i>
							Slate</a></li>
					<li><a data-value="spacelab" href="#"><i
							class="whitespace"></i> Spacelab</a></li>
					<li><a data-value="united" href="#"><i class="whitespace"></i>
							United</a></li>
				</ul>
			</div>
			<!-- theme selector ends -->


		</div>
	</div>
	<!-- topbar ends -->
	<!-- left menu starts -->
	<div class="col-sm-2 col-lg-2">
		<div class="sidebar-nav">
			<div class="nav-canvas">
				<div class="nav-sm nav nav-stacked"></div>
				<ul class="nav nav-pills nav-stacked main-menu">
                        <li class="nav-header">Main</li>
                        </li>
                       			<li><a class="ajax-link" href="index_classManager.jsp?manageraccount=<%=request.getParameter("manageraccount")%>&username=<%=request.getParameter("username")%>"><i
                                    class="glyphicon glyphicon-align-justify"></i><span>  班级管理</span></a></li>
                        </li>
                          <li><a class="ajax-link" href="index_teacherManager.jsp?manageraccount=<%=request.getParameter("manageraccount")%>&username=<%=request.getParameter("username")%>"><i
                                    class="glyphicon glyphicon-align-justify"></i><span>  教师管理</span></a></li>
                        <li><a class="ajax-link" href="index_std-parentsManager.jsp?manageraccount=<%=request.getParameter("manageraccount")%>&username=<%=request.getParameter("username")%>"><i class="glyphicon glyphicon-list-alt"></i><span> 学生-家长管理</span></a>
                        </li>
                     </ul> 
			</div>
		</div>
	</div>
<div id="content" class="col-lg-10 col-sm-10">
	<div class="box-inner" >
		<div class="row">
    		<div class="box-content" style="padding:30px">		
		 <!-- 查询 -->
			
				班级编号：<input type="text" name="classid" id="f1">
				班级名称：<input
					type="text" name="classname" id="f2"> 
				教师编号：<input type="text" name="teacherid" id="f3">
				教师姓名：<input type="text" name="teachername" id="f4">
				教师编号：<input type="text" name="teachertype" id="f5">
				教师姓名：<input type="text" name="project" id="f6">
					<input type="hidden"
					name="manageraccount" id="hidden1" value="">
				<script>
					$("#hidden1").value = $("span.hidden-sm hidden-xs").text();
				</script>
				<input type="submit" value="查询" onclick="sendAjax()">
				<a href="daoru_teacher.jsp">导入excel表</a>
			<!-- 增加 --> <a
			href="classform_addteacher.jsp?manageraccount=<%=request.getParameter("manageraccount")%>" target="_Blank"><input
				type="button" value="增加教师" ></a> <!-- 修改 --> 
				<input
				type="button" value="修改教师信息" onclick="fn3()" ><span>
					注意修改教师信息的时候只能勾选一个复选框！
				</span><br> 
				<input type="button"value="批量选中" onclick="manycheck()">
				<input type="button"value="批量取消" onclick="removecheck()">
				<input type="button"value="删除" onclick="deleteteacher()">
				</div>
			</div>
	<span id="div1">
		<div class="table_body" id="table_by"></div>
		<div id="page_navigation"></div>

		<div
			style="text-align: center; margin: 1px 0; font: normal 14px/20px 'MicroSoft YaHei';">
	</span>

		</div>
	</div>
	


	<!-- external javascript -->

	<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- library for cookie management -->
	<script src="js/jquery.cookie.js"></script>
	<!-- calender plugin -->
	<script src='bower_components/moment/min/moment.min.js'></script>
	<script src='bower_components/fullcalendar/dist/fullcalendar.min.js'></script>
	<!-- data table plugin -->
	<script src='js/jquery.dataTables.min.js'></script>

	<!-- select or dropdown enhancer -->
	<script src="bower_components/chosen/chosen.jquery.min.js"></script>
	<!-- plugin for gallery image view -->
	<script src="bower_components/colorbox/jquery.colorbox-min.js"></script>
	<!-- notification plugin -->
	<script src="js/jquery.noty.js"></script>
	<!-- library for making tables responsive -->
	<script src="bower_components/responsive-tables/responsive-tables.js"></script>
	<!-- tour plugin -->
	<script
		src="bower_components/bootstrap-tour/build/js/bootstrap-tour.min.js"></script>
	<!-- star rating plugin -->
	<script src="js/jquery.raty.min.js"></script>
	<!-- for iOS style toggle switch -->
	<script src="js/jquery.iphone.toggle.js"></script>
	<!-- autogrowing textarea plugin -->
	<script src="js/jquery.autogrow-textarea.js"></script>
	<!-- multiple file upload plugin -->
	<script src="js/jquery.uploadify-3.1.min.js"></script>
	<!-- history.js for cross-browser state change on ajax -->
	<script src="js/jquery.history.js"></script>
	<!-- application script for Charisma demo -->
	<script src="js/charisma.js"></script>


</body>
</html>
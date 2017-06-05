<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <!--
        ===
        This comment should NOT be removed.

        终南夜风雪 v1.0
    -->
    <meta charset="utf-8">
    <title>班级圈管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
    <meta name="author" content="Muhammad Usman">
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <!-- The styles -->
    <link id="bs-css" href="css/bootstrap-cerulean.min.css" rel="stylesheet">

    <link href="css/charisma-app.css" rel="stylesheet">
    <link href='bower_components/fullcalendar/dist/fullcalendar.css' rel='stylesheet'>
    <link href='bower_components/fullcalendar/dist/fullcalendar.print.css' rel='stylesheet' media='print'>
    <link href='bower_components/chosen/chosen.min.css' rel='stylesheet'>
    <link href='bower_components/colorbox/example3/colorbox.css' rel='stylesheet'>
    <link href='bower_components/responsive-tables/responsive-tables.css' rel='stylesheet'>
    <link href='bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css' rel='stylesheet'>
    <link href='css/jquery.noty.css' rel='stylesheet'>
    <link href='css/noty_theme_default.css' rel='stylesheet'>
    <link href='css/elfinder.min.css' rel='stylesheet'>
    <link href='css/elfinder.theme.css' rel='stylesheet'>
    <link href='css/jquery.iphone.toggle.css' rel='stylesheet'>
    <link href='css/uploadify.css' rel='stylesheet'>
    <link href='css/animate.min.css' rel='stylesheet'>
    <link href="css/absolute.css" rel="stylesheet">
    <link rel="stylesheet" href="style.css">

    <script src="js/jquery.min.js"></script>
    <script src="js/table.js"></script>
    <script src="js/jquery.page.js"></script>
    
    <!-- jQuery -->
    <script src="bower_components/jquery/jquery.min.js"></script>

    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- The fav icon -->
    <link rel="shortcut icon" href="img/favicon.ico">
    <script>
        function timedCount(id) {
        document.getElementById(id).innerHTML="已在线"+b+":"+c;
        c=c+1;
        if (c==60) 
            {c = c % 60;b += 1};
        setTimeout(function(){timedCount('demo');}, 1000);
    }
    window.onload=function(){
         c=0;
         b=0;
        timedCount('demo');
        var container = document.getElementById('container');
    var list = document.getElementById('list');
    var buttons = document.getElementById('buttons').getElementsByTagName('span');
    var prev = document.getElementById('prev');
    var next = document.getElementById('next');
    var index = 1;    //用于索引当前按钮
    var len = 5;      //图片的数量
    var animated = false;   //用于判断切换是否进行
    var interval = 3000;    //自动播放定时器秒数，这里是3秒
    var timer;             //定时器


    function animate (offset) {
        animated = true;     //切换进行中
        var time = 500;     //位移总时间
        var inteval = 10;   //位移间隔时间
        var speed = offset/(time/inteval);   //每次位移量
        var left = parseInt(list.style.left) + offset; //目标值

        var go = function (){
            //这两种情况表示还在切换中
         if ( (speed > 0 && parseInt(list.style.left) < left) || (speed < 0 && parseInt(list.style.left) > left)) {
                list.style.left = parseInt(list.style.left) + speed + 'px';
                setTimeout(go, inteval); //继续执行切换go()函数
            }
            else {
                list.style.left = left + 'px';
                if(left>-600){
                    list.style.left = -600 * len + 'px';
                }
                if(left<(-600 * len)) {
                    list.style.left = '-600';
                }
                animated = false; //切换完成
            }
        }
        go();
    }
    //用于为按钮添加样式
    function showButton() {
        //先找出原来有.on类的按钮，并移除其类
        for (var i = 0; i < buttons.length ; i++) {
            if( buttons[i].className == 'on'){
                buttons[i].className = '';
                break;
            }
        }
        //为当前按钮添加类
        buttons[index - 1].className = 'on';
    }
     //自动播放
    function play() {
        timer = setTimeout(function () {
            next.onclick();
            play();
        }, interval);
    }
     //清除定时器
    function stop() {
        clearTimeout(timer);
    }
    //右点击
    next.onclick = function () {
        if (animated) {    //如果切换还在进行，则直接结束，直到切换完成
            return;
        }
        if (index == 5) {
            index = 1; 
        }
        else {
            index += 1;
        }
        animate(-600);
        showButton();
    }
    //左点击
    prev.onclick = function () {
        if (animated) {       //如果切换还在进行，则直接结束，直到切换完成
            return;
        }
        if (index == 1) {
            index = 5;
        }
        else {
            index -= 1;
        }
        animate(600);
        showButton();
    }

    for (var i = 0; i < buttons.length; i++) {
        buttons[i].onclick = function () {
            if (animated) {         //如果切换还在进行，则直接结束，直到切换完成
                return;
            }
            if(this.className == 'on') {     //如果点击的按钮是当前的按钮，不切换，结束
                return;
            }
            //获取按钮的自定义属性index，用于得到索引值
            var myIndex = parseInt(this.getAttribute('index'));
            var offset = -600 * (myIndex - index);   //计算总的位移量

            animate(offset);
            index = myIndex;   //将新的索引值赋值index
            showButton();
        }
    }

    container.onmouseover = stop;//父容器的移入移出事件
    container.onmouseout = play;

    play();  //调用自动播放函数

    }
    </script>
    <style>
        
        a{
            text-decoration: none;
        }
        
        #container { 
            width: 600px;     /*这里600x400是图片的宽高*/
            height: 400px; 
            border: 3px solid #333; 
            overflow: hidden;   /*隐藏溢出的图片，因为图片左浮动，总宽度为4200*/
            position: relative;
            right:-500px;
            top: -150px;

        }
        #list { 
            width: 4200px;   /*这里设置7张图片总宽度*/
            height: 4px; 
            position: absolute;  /*基于父容器container进行定位*/
            z-index: 1;
        }
        #list img { 
            width: 600px;     /*这里600x400是图片的宽高*/
            height: 400px; 
            float: left;
        }
        #buttons { 
            position: absolute; 
            height: 10px; 
            width: 100px; 
            z-index: 2;   /*按钮在图片的上面*/
            bottom: 20px; 
            left: 250px;
        }
        #buttons span { 
            cursor: pointer; 
            float: left; 
            border: 1px solid #fff; 
            width: 10px; 
            height: 10px; 
            border-radius: 50%; 
            background: #333; 
            margin-right: 5px;
        }
        #buttons .on {  
            background: orangered;   /*选中的按钮样式*/
        }
        .arrow { 
            cursor: pointer; 
            display: none;    /*左右切换按钮默认先隐藏*/
            line-height: 39px; 
            text-align: center; 
            font-size: 36px; 
            font-weight: bold; 
            width: 40px; 
            height: 40px;  
            position: absolute; 
            z-index: 2; 
            top: 180px; 
            background-color: RGBA(0,0,0,.3); 
            color: #fff;
        }
        .arrow:hover { 
            background-color: RGBA(0,0,0,.7);
        }
        #container:hover .arrow { 
            display: block;   /*当鼠标放上去容器上面就显示左右切换按钮*/
        }
        #prev { 
            left: 20px;
        }
        #next { 
            right: 20px;
        }

    </style>
    <script>
    window.onload=function(){
		var manageraccount=<%=request.getAttribute("manageraccount")%>;
	if(manageraccount==null){
		self.location='404.html'; 
		}
	}
    	function fs(){
    		location.reload();
    	}
    </script>

</head>

<body>
    <!-- topbar starts -->
    <div class="navbar navbar-default" role="navigation">

        <div class="navbar-inner">
            <button type="button" class="navbar-toggle pull-left animated flip">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.html"> <img alt="Charisma Logo" src="img/logo20.png" class="hidden-xs"/>
                <span>Charisma</span></a>

            <!-- user dropdown starts -->
            <div class="btn-group pull-right">
                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="glyphicon glyphicon-user"></i><span class="hidden-sm hidden-xs"> <%=request.getAttribute("username")%></span>
                    <span class="caret"></span>
                </button>
                
                    <span id="demo"></span>
                </button>
                <ul class="dropdown-menu">
					<li><a href="changeadminpwd.jsp?manageraccount=<%=request.getAttribute("manageraccount")%>">修改密码</a></li>
					<li class="divider"></li>
					<li><a href="login.html">登出</a></li>
				</ul>
            </div>
            <!-- user dropdown ends -->

            <!-- theme selector starts -->
            <div class="btn-group pull-right theme-container animated tada">
                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="glyphicon glyphicon-tint"></i><span
                        class="hidden-sm hidden-xs">改变样式/皮肤</span>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" id="themes">
                    <li><a data-value="classic" href="#"><i class="whitespace"></i> Classic</a></li>
                    <li><a data-value="cerulean" href="#"><i class="whitespace"></i> Cerulean</a></li>
                    <li><a data-value="cyborg" href="#"><i class="whitespace"></i> Cyborg</a></li>
                    <li><a data-value="simplex" href="#"><i class="whitespace"></i> Simplex</a></li>
                    <li><a data-value="darkly" href="#"><i class="whitespace"></i> Darkly</a></li>
                    <li><a data-value="lumen" href="#"><i class="whitespace"></i> Lumen</a></li>
                    <li><a data-value="slate" href="#"><i class="whitespace"></i> Slate</a></li>
                    <li><a data-value="spacelab" href="#"><i class="whitespace"></i> Spacelab</a></li>
                    <li><a data-value="united" href="#"><i class="whitespace"></i> United</a></li>
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
                    <div class="nav-sm nav nav-stacked">

                    </div>
                    <ul class="nav nav-pills nav-stacked main-menu">
                        <li class="nav-header">Main</li>
                        <li><a class="ajax-link" onlick=fs() href="#"><i class="glyphicon glyphicon-home"></i><span>主页</span></a>
                        </li>
                        <li><a class="ajax-link" href="index_classManager.jsp?manageraccount=<%=request.getAttribute("manageraccount")%>&username=<%=request.getAttribute("username")%>"><i class="glyphicon glyphicon-eye-open"></i><span>班级管理</span></a>
                        </li>
                          <li><a class="ajax-link" href="index_teacherManager.jsp?manageraccount=<%=request.getAttribute("manageraccount")%>&username=<%=request.getAttribute("username")%>"><i
                                    class="glyphicon glyphicon-align-justify"></i><span> 教师管理</span></a></li>
                        <li><a class="ajax-link" href="index_std-parentsManager.jsp?manageraccount=<%=request.getAttribute("manageraccount")%>&username=<%=request.getAttribute("username")%>"><i class="glyphicon glyphicon-list-alt"></i><span> 学生-家长管理</span></a>
                        </li>
                     </ul> 
                </div>
            </div>
<div id="container">
    <div id="list" style="left: -600px;">
        <img src="image/5.jpg" alt="1"/>
        <img src="image/1.jpg" alt="1"/>
        <img src="image/2.jpg" alt="2"/>
        <img src="image/3.jpg" alt="3"/>
        <img src="image/4.jpg" alt="4"/>
        <img src="image/5.jpg" alt="5"/>
        <img src="image/1.jpg" alt="5"/>
    </div>
    <div id="buttons">
        <span index="1" class="on"></span>
        <span index="2"></span>
        <span index="3"></span>
        <span index="4"></span>
        <span index="5"></span>
    </div>
    <a href="javascript:;" id="prev" class="arrow">&lt;</a>
    <a href="javascript:;" id="next" class="arrow">&gt;</a>
</div>
        <footer class="footer">
             <p id="cent">Powered by: Charisma</a></p>
         </footer>
     </div>
        <!--/span-->
        <!-- left menu ends -->
     
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
<script src="bower_components/bootstrap-tour/build/js/bootstrap-tour.min.js"></script>
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

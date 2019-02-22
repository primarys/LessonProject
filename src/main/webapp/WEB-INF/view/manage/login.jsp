<%@ page language="java" import="com.project.utils.Const" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<link>
    <%@ include file="/common/framework.jsp" %>
    <title>账号登录 - ${title}</title>
	<script>
		if (window.top != window) {
			window.top.location.href = "${ctx }/login";
		}
	</script>
	<link rel="stylesheet" href="${rctx }/css/manage/login.css${nocache}">
    <style>
        .am-dropdown li a:hover {
            background:#dd514c !important;
            color: #ffffff !important;
        }
        .diy-radius{
            border-radius: 5px;
        }
        .diy-top-right-radius{
            border-top-right-radius: 5px;
        }
        .diy-bottom-radius{
            border-bottom-left-radius: 5px;
            border-bottom-right-radius: 5px;
        }
        .diy-top-left-radius{
            border-top-left-radius: 5px;
        }
    </style>
    <script src="${rctx}/utils/salert/salert.js"></script>
</head>
<body>
    <div style="width: 100%;height: 100%;text-align: center">
        <div class="bg bg-blur"></div>
        <div class="content-front">
            <header <%--class="am-text-left"--%>>
                <a href="${pctx}" target="_blank" class="logo am-margin-left">
                    <img src="${rctx}/image/common/pzc_logo.png">
                </a>
                <img src="${rctx}/image/common/website.png" id="website-logo">
            </header>
            <div class="am-cf" style="display: inline-block">
                <div class="am-fr">
                    <div data-am-widget="tabs"
                         class="am-tabs am-tabs-d2 diy-radius" style="background-color: rgba(255, 255, 255, 0.6);">
                        <ul class="am-tabs-nav am-cf diy-radius">
                            <li class="am-active diy-top-left-radius"><a href="[data-tab-panel-0]">账号登录</a></li>
                            <li class="diy-top-right-radius"><a href="[data-tab-panel-1]">手机登录/注册</a></li>
                        </ul>
                        <div class="am-tabs-bd diy-bottom-radius" >
                            <div data-tab-panel-0 class="am-tab-panel am-active" style="padding: 0">
                                <%--<div class="am-dropdown"  style="width: 100%" data-am-dropdown>
                                    <button id="judge" class="am-btn am-btn-primary am-dropdown-toggle" style="width: 100%;font-size: 1.5rem" data-am-dropdown-toggle>教师/学生登录 <span class="am-icon-caret-down"></span></button>
                                    <ul class="am-dropdown-content">
                                        <li id="teacher"  style="text-align: center"><a id="teacherLink" href="#" >教师登录</a></li>
                                        <li class="am-divider"></li>
                                        <li id="student" style="text-align: center"><a id="studentLink" href="#" >学生登录</a></li>
                                    </ul>
                                </div>--%>
                                <form action="${ctx}/login" method="post" >
                                    <%--<h1 class="am-text-black am-text-center">账号登录</h1>--%>
                                    <div class="my-form-content">
                                        <i class="am-icon-user my-form-icon"></i>
                                        <label id="my-form-name" class="my-name-form-label">手机号/登录名</label>
                                        <input id="my-form-input-name" class="my-form-field" autocomplete="off" type="text" name="name" value="${name}" onclick="addNameClass()" required>
                                    </div>
                                    <div class="my-form-content" style="margin-top: 35px">
                                        <i class="am-icon-key my-form-icon"></i>
                                        <label id="my-form-password" class="my-password-form-label">密码</label>
                                        <input id="my-form-input-password" class="my-form-field" autocomplete="off" type="password" name="password" value="${password}" onclick="addPasswordClass()" required>
                                    </div>
                                    <div>
                                        <input type="hidden" id="my-form-input-judge" class="my-form-field" name="loginerJudge" value="teacher" required>
                                    </div>
                                    <c:if test = "${sessionScope.get(\"loginerr\") != null && sessionScope.get(\"loginerr\") >= 3}" >
                                        <div class="am-form-group">
                                            <input class="am-form-field am-inline" type="text" name="verify" placeholder="验证码" autocomplete="off" required style="width: 225px">
                                            <img src="${ctx}/verifyImg?rand=" title="换一张" class="am-fr" style="border:1px solid #ccc; cursor: pointer"
                                                 onclick="this.src='${ctx}/verifyImg?rand='+Math.random();"/>
                                        </div>
                                    </c:if>
                                    <div class="am-form-group">
                                        <button id= "submit" class="am-btn am-btn-primary am-btn-block" type="submit">教师登录</button>
                                    </div>
                                    <%--<a href="${ctx}/register">教师注册</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a href="forget">忘记密码</a>--%>
                                </form>
                            </div>
                            <div data-tab-panel-1 id="data-tab-panel-1" class="am-tab-panel" style="padding:0px;">
                                <form class="am-form" id="messageSend" onsubmit="event.preventDefault()">
                                    <div class="am-form-group am-form-icon" style="margin-top: 5%">
                                        <i class="am-icon-user"></i>
                                        <input class="am-form-field" id="phoneNum"  type="text" name="phoneNum" placeholder="手机号" required>
                                    </div>
                                    <div class="am-form-group am-form-icon" style="margin-top: 5%">
                                        <i class="am-icon-key"></i>
                                        <input id="verify" class="am-form-field am-inline" type="text" name="verify" placeholder="图形验证码" autocomplete="off" required style="width: 206px"  >
                                        <img src="${ctx}/verifyImg?rand=" title="换一张" class="am-fr" style="border:1px solid #ccc; cursor: pointer; width: 114px; height: 36px"
                                             onclick="this.src='${ctx}/verifyImg?rand='+Math.random();" />
                                    </div>
                                    <div class="am-form-group am-form-icon">
                                        <div class="am-input-group">
                                            <input type="text" class="am-form-field" id="verificationCode" name="verificationCode" placeholder="手机验证码">
                                            <span class="am-input-group-btn">
                                                <button id="getMessage" class="am-btn am-btn-default" type="button" @click="getMessage">获取验证码</button>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <button class="am-btn am-btn-primary am-btn-block" type="submit" @click="dxTeacherLogin">登录</button>
                                    </div>
                                    <h2></h2>
                                </form>

                                <%--<div class="am-margin-vertical" style="text-align: center">--%>
                                    <%--<a href="${ctx}/weixinLoginOrRegister" target="_self">--%>
                                        <%--请用微信扫码注册--%>
                                        <%--<div id="invite-qrcode"></div>--%>
                                    <%--</a>--%>
                                <%--</div>--%>

                                    <%--<form action="${ctx}/teacherRegister" class="am-form" method="post" >--%>
                                        <%--<div class="am-form-group am-form-icon">--%>
                                            <%--<i class="am-icon-user"></i>--%>
                                            <%--<input class="am-form-field" type="text" name="phoneNum" placeholder="手机号" value="${phoneNum}" required>--%>
                                        <%--</div>--%>
                                        <%--<div class="am-form-group am-form-icon">--%>
                                            <%--<i class="am-icon-key"></i>--%>
                                            <%--<input class="am-form-field" type="password" name="password" placeholder="密码" value="${password}" required>--%>
                                        <%--</div>--%>
                                        <%--<div class="am-form-group">--%>
                                            <%--<button class="am-btn am-btn-primary am-btn-block" type="submit">注 册</button>--%>
                                        <%--</div>--%>
                                        <%--<h2></h2>--%>
                                    <%--</form>--%>
                            </div>
                        </div>
                    </div>
                    <c:if test="${not empty err}">
                        <div class="am-alert" style="margin: 10px" data-am-alert>
                            <button type="button" class="am-close">&times;</button>
                            <p><i class="am-icon-info-circle"></i> ${err}</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty sessionScope.get(Const.USER_ADMIN)
                      || not empty sessionScope.get(Const.USER_TEACHER)
                      || not empty sessionScope.get(Const.USER_STUDENT)}">
                        <div class="am-alert am-alert-success" data-am-alert>
                            <button type="button" class="am-close">&times;</button>
                            <p><a href="${ctx}/manage/dashboard" class="am-icon-cog"> 您已登录账号，进入平台管理</a></p>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <footer class="am-text-center am-text-sm am-padding-sm am-topbar-fixed-bottom" style="background-color: #474443;">
        <%@ include file="/common/copyright.jsp" %>
    </footer>
</body>
<script>
    var app = new Vue({
        el:"#messageSend",
        data:{

        },
        methods:{
            getMessage: function () {
                $.ajax({
                    type:"POST",
                    url:CONST.$ctx+"/sendVerification",
                    data:{
                        phoneNum:$("#phoneNum").val(),
                        verify:$("#verify").val()
                    },
                    success : function (res) {
                        salert(res);
                        if(res == "发送手机动态码成功！"){
                            showTime();
                        }

                    }
                });
            },
            dxTeacherLogin: function () {
                $.ajax({
                    type:"POST",
                    url:CONST.$ctx+"/dxTeacherLogin",
                    data:{
                        phoneNum:$("#phoneNum").val(),
                        verify:$("#verify").val(),
                        verificationCode:$("#verificationCode").val()
                    },
                    success : function (ret) {
                        if(ret == "短信验证码错误！"){
                            salert(ret);
                            $("#data-tab-panel-1").addClass("am-active");
                        }else{
                            salert(ret);
                            location.href = CONST.$ctx + "/TeacherLoginOK";
                        }
                    }
                });
            }
        },
    });

    var t = 60;//设定倒数秒数
    //显示倒数秒数
    function showTime(){
        t -= 1;
        document.getElementById('getMessage').innerHTML= "重新获取(<span id='codeSeconds'></span>)s";
        document.getElementById('codeSeconds').innerHTML = t;
        if(t==0){
            $("#getMessage").removeAttr("disabled");
            $("#getMessage").html("重新获取");//
            $("#getMessage").css("cursor","pointer");
            $("#getMessage").attr("onclick","getMessage()");
            t = 60;
            return ;
        }else{
            //刚进入倒数时，即倒数59秒时
            $("#getMessage").attr("disabled","disabled");
        }
        //每秒执行一次,showTime()
        setTimeout("showTime()",1000);
    }
</script>
<script>
    var text = "http://www.pzcnet.com/LessonProject/weixinLoginOrRegister";
    var temp = new $.AMUI.qrcode({
        text: text,
        width: 185,
        height: 185,
        correctLevel: 3,
    });
    $(function() {
        if(${!errType}){
            $("#my-form-input-name").addClass("normal-input");
            $("#my-form-input-password").addClass("normal-input");
        }
        if(${errType==1}){
            $("#my-form-input-name").addClass("err-input");
        }
        if(${errType==2}){
            $("#my-form-input-password").addClass("err-input");
        }
        if($("#my-form-input-name").val()!="") {
            $("#my-form-name").addClass("my-form-name-label-input");
        }
        if($("#my-form-input-password").val()!=""||${errType==2}) {
            $("#my-form-password").addClass("my-form-password-label-input");
        }
    });
    setTimeout(function () {
        $("#invite-qrcode").html(temp);
    }, 10);
    //点击别的位置移除账号密码输入样式
    $(document).mousedown(function(e){
        if($(e.target).parent("#info").length==0){
            if($("#my-form-input-name").val()=="") {
                $("#my-form-name").removeClass("my-form-name-label-input");
            }
            if($("#my-form-input-password").val()==""&&${errType!=2}) {
                $("#my-form-password").removeClass("my-form-password-label-input");
            }
        }
    })
    //tab指向输入框时呈现输入样式
    $("#my-form-input-name").focus(function(e){
        if($("#my-form-input-name").val()=="") {
            $("#my-form-name").addClass("my-form-name-label-input");
        }
        if($("#my-form-input-password").val()=="") {
            $("#my-form-password").removeClass("my-form-password-label-input");
        }
    });
    $("#my-form-input-password").focus(function(e){
        if($("#my-form-input-password").val()=="") {
            $("#my-form-password").addClass("my-form-password-label-input");
        }
        if($("#my-form-input-name").val()=="") {
            $("#my-form-name").removeClass("my-form-name-label-input");
        }
    });

    function addNameClass() {
        if(($("#my-form-name")[0].className!="my-form-name-label-input")) {
            $("#my-form-name").addClass("my-form-name-label-input");
            if($("#my-form-input-password").val()==""){
                $("#my-form-password").removeClass("my-form-password-label-input");
            }
        }
    };
    function addPasswordClass() {
        if($("#my-form-password")[0].className=="my-password-form-label") {
            $("#my-form-password").addClass("my-form-password-label-input");
            if($("#my-form-input-name").val()=="") {
                $("#my-form-name").removeClass("my-form-name-label-input");
            }
        }
    };

    $(function() {
        var loginerJudge;//判断标志位
        var $dropdown = $('.am-dropdown'),
             data = $dropdown.data('amui.dropdown');
        $('#teacher').on('click', function () {
             data.active ? $dropdown.dropdown('close') : $dropdown.dropdown('open');
             loginerJudge = "teacher";
            $("#my-form-input-judge").val("teacher");
             $("#submit").html("教师登录");
             return false;
        });
        $('#student').on('click', function () {
            data.active ? $dropdown.dropdown('close') : $dropdown.dropdown('open');
            loginerJudge = "student";
            $("#my-form-input-judge").val("student");
            $("#submit").html("学生登录");
            return false;
        });
    });

</script>
</html>
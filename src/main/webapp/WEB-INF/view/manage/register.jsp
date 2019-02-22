<%@ page language="java" import="com.project.utils.Const" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="/common/framework.jsp" %>
    <title>教师注册 - ${title}</title>
	<link rel="stylesheet" href="${rctx }/css/manage/login.css${nocache}">
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
            <div class="am-cf" style="display: inline-block" id="app">
                <div class="am-fr">
                    <c:if test="${not empty err}">
                        <div class="am-alert" data-am-alert>
                            <button type="button" class="am-close">&times;</button>
                            <p><i class="am-icon-info-circle"></i> ${err}</p>
                        </div>
                    </c:if>
                    <form action="${ctx}/teacherRegister" method="post">
                        <h1 class="am-text-black am-text-center">教师注册</h1>
                        <div class="am-form-group am-form-icon">
                            <i class="am-icon-user"></i>
                            <input class="am-form-field" id = "username" type="text" name="teacherName" placeholder="姓名"  required>
                        </div>
                        <%--<div class="am-form-group am-form-icon">--%>
                            <%--<i class="am-icon-user"></i>--%>
                            <%--<input class="am-form-field" type="text" name="userName" placeholder="登录名(必须以T开头)"  required>--%>
                        <%--</div>--%>
                        <div class="am-form-group am-form-icon">
                            <i class="am-icon-phone"></i>
                            <input class="am-form-field" id = "tel" onblur="hasExistCusPhone()" type="text" name="phoneNum" placeholder="手机号码" required>
                        </div>
                        <div class="am-form-group am-form-icon">
                            <i class="am-icon-mail-reply-all"></i>
                            <input class="am-form-field" id = "mail" type="email" name="email" placeholder="邮箱"required>
                        </div>
                        <div class="am-form-group am-form-icon">
                            <i class="am-icon-key"></i>
                            <input class="am-form-field" id = "psw" onblur="registerPswLength()" type="password" name="password" placeholder="密码"  required>
                        </div>
                        <div class="am-form-group am-form-icon">
                            <i class="am-icon-key"></i>
                            <input class="am-form-field" id = "repsw" onblur="samePsw()" type="password" name="passwordConfirm" placeholder="再次确认密码"  required>
                        </div>
                        <div class="am-form-group">
                            <div class="am-input-group">
                                <input id="yzm" class="am-form-field am-inline" name="dynamicCode"
                                       type="text" placeholder="验证码" autocomplete="off" required>
                                <span class="am-input-group-label am-padding-0">
                                    <img src="${ctx}/verifyImg?rand=" title="换一张" class="am-fr" style="cursor: pointer"
                                         onclick="this.src='${ctx}/verifyImg?rand='+Math.random();"/>
                                </span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <button class="am-btn am-btn-primary am-btn-block" type="submit">马上注册</button>
                        </div>
                        <%--<div><small class="am-text-muted">* 若没有保存手机号码，请联系管理员重置密码</small></div>--%>
                        <a href="login">立即登录</a>
                    </form>


                </div>
            </div>
        </div>
    </div>
    <footer class="am-text-center am-text-sm am-padding-sm am-topbar-fixed-bottom" style="background-color: #474443;">
        <%@ include file="/common/copyright.jsp" %>
    </footer>
</body>
<script type="text/javascript">
    /* 教师注册信息 */
    var username;//姓名
    var tel;//手机号码
    var mail; //邮箱
    var psw;//密码
    var repsw;//重复密码
    var identify;//短信验证码
    var yzm;//图片验证码
    //var isCusAgree;//同意用户协议

    function getCusInfo1() {
        username = $("#username").val();
        tel = $("#tel").val();
        mail = $("#mail").val();
        identify = $("#identify").val();
        yzm = $("#yzm").val();
        //isCusAgree = document.getElementsByName("isCusAgree")[0].checked;
    }
    function getCusInfo2() {
        tel = $("#tel").val();
        psw = $("#psw").val();
        repsw = $("#repsw").val();
    }

    //是否存在已经注册的手机
    function hasExistCusPhone() {
        var isExist = 0;
        $.ajax({
            type : "POST",
            url : "${ctx}/hasPhoneRegister",
            async: false,
            //dataType : "json",
            data : {
                phoneNum : document.getElementById("tel").value
                },
            success : function(result) {
                if (result == "1") {
                    isExist = 1;
                    $('#tel').popover('setContent', '手机号已注册').popover('open');
                    //$("#tel").placeholder("手机号已注册");
                } else if (result == "2") {
                    isExist = 2;
                    $('#tel').popover('setContent', '手机号格式有误').popover('open');
                    //$("#tel").placeholder("手机号格式有误");
                } else
                {
//                    $('#tel').popover('setContent', '可以使用该手机号码！').popover('open');
                }
            }
        });
        return isExist;
    }

    //密码长度验证
    function registerPswLength() {
        var isFalse = 0;
        $.ajax({
            type : "POST",
            url : "${ctx}/checkPswLength",
            //dataType : "json",
            asyns : false,
            data : {
                psw : document.getElementById("psw").value
            },
            success : function(result) {
                if (result == "1") {
                    isFalse = 1;//密码格式错误
                $('#psw').popover('setContent', '密码错误，请确认长度为6到18位。').popover('open');
            } else{}
            }
        });
        return isFalse;
    }

    //两次密码是否一样
    function samePsw(){
        var isSame = 1;
        $.ajax({
            type : "POST",
            url : "${ctx}/hasSamePsw",
            async: false,
            dataType : "json",
            data : {
                psw : document.getElementById("psw").value,
                repsw : document.getElementById("repsw").value
            },
            success : function(result) {
                if (result == "0") {
                    isSame= 0;
                    $('#repsw').popover('setContent', '两次输入密码不一样。').popover('open');
                } else
                { }
            }
        });
        return isSame;
    }
</script>

<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
</html>
<%@ page language="java" import="com.project.utils.Const" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="/common/framework.jsp" %>
    <title>重置密码 - ${title}</title>
	<link rel="stylesheet" href="${rctx }/css/manage/login.css${nocache}">
</head>
<body class="am-container">
    <header class="am-text-left">
        <a href="${pctx}" target="_blank" id="pzc-logo">
            <img src="${rctx}/image/common/logo.png">
        </a>
        <img src="${rctx}/image/common/website.png" id="website-logo">
    </header>
    <div class="am-cf am-text-left" id="app">
        <div class="am-fr">
            <h1 class="am-text-white am-text-center">重置密码</h1>
            <form action="reset" class="am-form" method="post" id="app">
                <div class="am-form-group am-form-icon">
                    <i class="am-icon-key"></i>
                    <input class="am-form-field" type="password" name="password"
                           v-model="password" placeholder="新密码" required>
                </div>
                <div class="am-form-group am-form-icon am-margin-0">
                    <i class="am-icon-key"></i>
                    <input class="am-form-field" type="password" name="phone"
                           v-model="again" placeholder="再次输入" required>
                </div>
                <div class="am-form-group">
                    <small :class="{'am-text-primary':true,'am-hide':password==again}">两次密码不一致</small>
                </div>
                <div class="am-form-group">
                    <button class="am-btn am-btn-primary am-btn-block" type="submit"
                            :disabled="password=='' || password!=again">修改并登录</button>
                </div>
                <a href="login">立即登录</a>
            </form>
        </div>
    </div>
    <footer class="am-text-center am-text-sm am-padding-sm am-topbar-fixed-bottom">
        <%@ include file="/common/copyright.jsp" %>
    </footer>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script>
    new Vue({
        el: "#app",
        data: {
            password: "",
            again: ""
        }
    })
</script>
</html>
<%@ page language="java" import="com.project.utils.Const" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="/common/framework.jsp" %>
    <title>忘记密码 - ${title}</title>
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
                    <form action="forget" method="post">
                        <h1 class="am-text-black am-text-center">修改密码</h1>
                        <div class="am-form-group am-form-icon">
                            <i class="am-icon-user"></i>
                            <input class="am-form-field" type="text" name="name" placeholder="登录名" value="${name}" required>
                        </div>
                        <div class="am-form-group am-form-icon">
                            <i class="am-icon-phone"></i>
                            <input class="am-form-field" type="tel" name="phone" placeholder="手机号码" value="${phone}" required>
                        </div>
                        <div class="am-form-group">
                            <div class="am-input-group">
                                <input id="verify" class="am-form-field am-inline" name="verify"
                                       type="text" placeholder="验证码" autocomplete="off" required>
                                <span class="am-input-group-label am-padding-0">
                                    <img src="${ctx}/verifyImg" title="换一张" class="am-fr" style="cursor: pointer"
                                         onclick="this.src='${ctx}/verifyImg?rand=Math.random()'"/>
                                </span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <button class="am-btn am-btn-primary am-btn-block" type="submit">验证手机</button>
                        </div>
                        <div><small class="am-text-muted">* 若没有保存手机号码，请联系管理员重置密码</small></div>
                        <a href="login">立即登录</a>
                    </form>
                    <c:if test="${not empty err}">
                        <div class="am-alert" data-am-alert>
                            <button type="button" class="am-close">&times;</button>
                            <p><i class="am-icon-info-circle"></i> ${err}</p>
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
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
</html>
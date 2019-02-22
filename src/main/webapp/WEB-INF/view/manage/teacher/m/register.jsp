<%@ page language="java" import="com.project.utils.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>账号信息填写</title>
    <%@ include file="/common/framework.jsp" %>
    <link rel="stylesheet" href="${rctx }/css/student/m/fillInfo.css${nocache}">
</head>
<body>
<header class="am-text-left">
        <a href="${pctx}" target="_blank" class="logo am-margin-left">
        <img src="${rctx}/image/common/pzc_logo.png"></a>
        <img src="${rctx}/image/common/website.png" id="website-logo">
</header>
<div class="am-cf am-text-left">
    <div>
        <form action="${ctx}/registerConfirm" class="am-form" method="post" id="addaccountform">
            <div class="am-form-group am-form-icon">
                <i class="am-icon-user"></i>
                <input autocomplete="off" class="am-form-field" type="text" name="teacherName" placeholder="请输入姓名" value="${teacherName}" required>
            </div>
            <div class="am-form-group am-form-icon">
                <i class="am-icon-genderless"></i>
                <input type="radio" name="sex" value="1" checked="checked">男</input>
                <input type="radio" name="sex" value="2">女</input>
               <%--<input autocomplete="off" class="am-form-field" type="text" name="sex" placeholder="请输入性别" value="${sex}" required>--%>
            </div>
            <div class="am-form-group am-form-icon">
                <i class="am-icon-phone"></i>
                <input autocomplete="off" class="am-form-field" type="text" name="phoneNum" placeholder="请输入手机" value="${phoneNum}" required>
            </div>
            <div class="am-form-group am-form-icon">
                <i class="am-icon-mail-forward"></i>
                <input autocomplete="off" class="am-form-field" type="text" name="email" placeholder="请输入邮箱" value="${email}" required>
            </div>
            <input type="hidden" name="weixinOpenId" value="${weixinOpenId}">
            <div class="am-form-group">
                <button class="am-btn am-btn-primary am-btn-block" type="submit">确  定</button>
            </div>
        </form>
        <%--<c:if test="${not empty error}">
            <div class="am-alert am-margin-top-sm" data-am-alert>
                <button type="button" class="am-close">&times;</button>
                <p><i class="am-icon-info-circle"></i> ${error}</p>
            </div>
        </c:if>--%>
        <%--<c:if test="${not empty sessionScope.get(Const.USER_ADMIN)
              || not empty sessionScope.get(Const.USER_TEACHER)}">
            <div class="am-alert am-alert-success" data-am-alert>
                <button type="button" class="am-close">&times;</button>
                <p><a href="${ctx}/manage/dashboard" class="am-icon-cog"> 您已登录账号，进入平台管理</a></p>
            </div>
        </c:if>--%>
    </div>
</div>

<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">${error}</div>
        <div class="am-modal-footer">
            <span class="am-modal-btn">确定</span>
        </div>
    </div>
</div>
<footer class="am-text-center am-text-sm am-padding-sm am-topbar-fixed-bottom">
    <%@ include file="/common/copyright.jsp" %>
</footer>
<script>
    $(function () {
        if(${not empty error}) {
            $('#my-alert').modal('open');
        }
    })
</script>
</body>
</html>

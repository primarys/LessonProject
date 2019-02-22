<%@ page language="java" import="com.project.utils.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>学号姓名录入</title>
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
        <form action="${ctx}/fillInfo" class="am-form" method="post" id="addaccountform">
            <div class="am-form-group am-form-icon">
                <i class="am-icon-key"></i>
                <input autocomplete="off" class="am-form-field" type="text" name="stuNumber" placeholder="请输入学号" value="${stuNumber}" required>
            </div>
            <div class="am-form-group am-form-icon">
                <i class="am-icon-users"></i>
                <input autocomplete="off" class="am-form-field" type="text" name="stuName" placeholder="请输入姓名" value="${stuName}" required>
            </div>
            <input type="hidden" name="weixinOpenId" value="${weixinOpenId}">
            <input type="hidden" name="lessonId" value="${lessonId}">
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

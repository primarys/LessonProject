<%@ page language="java" pageEncoding="utf-8" import="com.project.utils.Const" %>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link href="${rctx}/css/manage/frame_styles.css${nocache}" type="text/css" rel="stylesheet">
</head>
<body>
<div class="main" id="app">
    <ol class="am-breadcrumb am-margin-bottom-sm">
        <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
        <li class="am-active">修改资料</li>
    </ol>
    <form id="form-updateAdmin" class="am-form" @submit="updateAdmin">
        <div style="width: 300px; margin: 0 auto">
            <c:if test="${empty admin.phone || empty admin.name}">
                <div class="am-alert am-alert-warning" data-am-alert>
                    <button type="button" class="am-close">&times;</button>
                    <p>请完善您的个人信息</p>
                </div>
            </c:if>
            <div class="am-form-group">
                <label>名称 *</label>
                <input type="text" name="name" placeholder="输入您的名称" value="${admin.name}" required>
            </div>
            <div class="am-form-group">
                <label>联系方式 *</label>
                <input type="text" name="phone" placeholder="输入您的联系方式" value="${admin.phone}" required>
            </div>
            <div class="am-form-group">
                <label>电子邮箱</label>
                <input type="text" name="email" placeholder="输入您的电子邮箱" value="${admin.email}">
            </div>
            <div class="am-form-group">
                <button type="submit" class="am-btn am-btn-primary am-btn-block am-icon-save"> 保存</button>
            </div>
            <hr>
            <div class="am-form-group">
                <a class="am-btn am-btn-outline-warning am-btn-block am-icon-key" href="${ctx}/manage/admin/modifyPassword" > 修改密码</a>
            </div>
        </div>
    </form>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/admin/manage.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

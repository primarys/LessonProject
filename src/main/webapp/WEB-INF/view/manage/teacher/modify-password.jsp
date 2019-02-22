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
        <li><a href="${ctx}/manage/teacher/edit">修改资料</a></li>
        <li class="am-active">修改密码</li>
    </ol>
    <form id="form-updateTeacher" class="am-form" @submit="updatePassword">
        <div style="width: 300px; margin: 0 auto">
            <div class="am-form-group">
                <label>原密码</label>&nbsp;&nbsp;&nbsp;<label> * 默认密码为123456</label>
                <input type="password" name="password" placeholder="输入您的原密码" required>
            </div>
            <div class="am-form-group">
                <label>新密码</label>
                <input type="password" name="newPassword" placeholder="输入您的新密码" v-model="newPassword" required>
            </div>
            <div class="am-form-group">
                <label>确认密码</label>
                <input type="password" name="again"  placeholder="再次确认密码" v-model="again" required>
            </div>
            <div class="am-form-group">
                <button type="submit" class="am-btn am-btn-primary am-btn-block am-icon-save"> 保存</button>
            </div>
        </div>
    </form>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/teacher/manage.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

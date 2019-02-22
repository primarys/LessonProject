<%@ page language="java" pageEncoding="utf-8" import="com.project.utils.Const" %>
<html>
<head>
    <%@include file="/common/framework.jsp" %>
    <title>${title}</title>
    <link href="${rctx}/css/manage/frame_styles.css${nocache}" type="text/css" rel="stylesheet">
</head>
<body>
<div class="main" id="c-app">
    <ol class="am-breadcrumb am-margin-bottom-sm">
        <li><a href="javascript:history.back()" class="am-icon-arrow-circle-left" >上一级</a></li>
        <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
        <%--<li><a href="${ctx}/manage/teacher/lessonList">课程管理</a></li>--%>
        <li><a href="${ctx}/manage/teacher/viewLesson?id=${lesson.id}">${lesson.lessonName}</a></li>
        <li class="am-active">添加学生</li>
    </ol>
    <form id="ajax-form" class="am-form" method="post" @submit ="saveStudent">
        <input type="hidden" name="vid" value="${lesson.id}">
        <%--<input type="hidden" name="cid" value="${student.id}">--%>
        <div  style="width: 300px; margin: 0 auto">
            <div class="am-form-group">
                <label>学号</label>
                <input type="text" name="studentNumber" placeholder="请输入学号" required>
            </div>
            <div class="am-form-group">
                <label>名称</label>
                <input type="text" name="studentName" placeholder="请输入姓名" required>
            </div>
            <%--<div class="am-form-group">
                <h3>性别</h3>
                <label class="am-radio-inline">
                    <input type="radio" name="sex" value="1" data-am-ucheck
                           checked
                    > 男
                </label>
                <label class="am-radio-inline">
                    <input type="radio" name="sex" value="2" data-am-ucheck
                    > 女
                </label>
            </div>--%>
            <div class="am-form-group">
                <button type="submit" class="am-btn am-btn-primary am-btn-block am-icon-save"> 保存</button>
            </div>
        </div>
    </form>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/teacher/student.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

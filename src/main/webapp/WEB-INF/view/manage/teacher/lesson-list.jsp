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
        <li class="am-active">课程管理</li>
    </ol>
    <div class="am-margin-vertical">
        <a class="am-btn am-btn-sm am-btn-primary am-icon-plus am-text-white am-radius" href="${ctx}/manage/teacher/editLesson"> 新增课程</a>
    </div>
    <table class="am-table am-table-hover am-table-centered">
        <thead>
        <tr class="am-active">
            <th>名称</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${lessonList}" var="lesson">
            <tr>
                <td class="am-text-middle">${lesson.lessonName}</td>
                <td class="am-text-middle"><fmt:formatDate value="${lesson.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td class="am-text-middle"><fmt:formatDate value="${lesson.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>
                    <a class="am-btn am-btn-sm am-btn-success am-text-white am-icon-eye am-radius"
                       href="${ctx}/manage/teacher/viewLesson?id=${lesson.id}"> 查看</a>
                    <a class="am-btn am-btn-sm am-btn-success am-text-white am-icon-flag am-radius"
                       href="${ctx}/manage/teacher/classSign?id=${lesson.id}"> 学生签到</a>
                    <a class="am-btn am-btn-sm am-btn-warning am-text-white am-icon-pencil am-radius"
                       href="${ctx}/manage/teacher/editLesson?id=${lesson.id}"> 编辑</a>
                    <button class="am-btn am-btn-sm am-btn-outline-default am-icon-trash-o am-radius" @click="deleteLesson(${lesson.id})"> 删除</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/teacher/lesson.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

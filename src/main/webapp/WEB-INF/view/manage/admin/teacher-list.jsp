<%@ page language="java" pageEncoding="utf-8" import="com.project.utils.Const" %>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link href="${rctx}/css/manage/frame_styles.css${nocache}" type="text/css" rel="stylesheet">
    <%--<%@include file="/resource/utils/DataTable/import.jsp"%>--%>
</head>
<body>
<div class="main" id="app">
    <ol class="am-breadcrumb am-margin-bottom-sm">
        <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
        <li class="am-active">成员管理</li>
    </ol>
    <div class="am-margin-vertical">
        <button class="am-btn am-btn-sm am-btn-primary am-icon-user-plus" @click="createTeacher"> 一键创建教师</button>
    </div>
    <table class="am-table am-table-hover am-table-centered">
        <thead>
        <tr class="am-active">
            <th>名称</th>
            <th>联系电话</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${teacherList}" var="teacher">
            <tr>
                <td class="am-text-middle">${teacher.teacherName}</td>
                <td class="am-text-middle">${teacher.phoneNum}</td>
                <td class="am-text-middle">${teacher.email}</td>
                <td>
                    <a class="am-btn am-btn-sm am-btn-success am-text-white am-icon-eye" href="viewTeacher?mid=${teacher.id}"> 查看</a>
                    <a class="am-btn am-btn-sm am-btn-warning am-text-white am-icon-pencil" href="editTeacher?mid=${teacher.id}"> 编辑</a>
                    <button class="am-btn am-btn-sm am-btn-outline-default am-bg-white am-icon-trash-o" @click="deleteTeacher(${teacher.id})"> 删除</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${teacherList.size() == 0}">
        <p class="am-margin-xl am-text-center">
            暂无教师账号
        </p>
    </c:if>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/admin/manage.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

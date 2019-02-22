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
        <li><a href="${ctx}/manage/admin/teacherList" class="am-icon-arrow-circle-left">上一级</a></li>
        <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
        <li><a href="${ctx}/manage/admin/teacherList">成员管理</a></li>
        <li class="am-active">查看详情</li>
    </ol>
    <div class="am-cf">
        <img src="${teacher.headPortrait.url}" class="ifr-img-head am-thumbnail am-fl ifr-js-shrink">
        <div class="ifr-div-info-box">
            <h2 class="am-margin-0">${teacher.userName}</h2>
            <div class="am-margin-bottom">
                <a class="am-btn am-btn-xs am-btn-warning am-text-white am-icon-pencil" href="${ctx}/manage/admin/editTeacher?mid=${teacher.id}">编辑</a>
                <%--<c:if test="${teacher.locked}">
                    <button class="am-btn am-btn-xs am-btn-outline-danger am-icon-unlock" @click="lockTeacher(${teacher.id}, false)">解锁</button>
                </c:if>
                <c:if test="${not teacher.locked}">
                    <button class="am-btn am-btn-xs am-btn-danger am-icon-lock" @click="lockTeacher(${teacher.id}, true)"> 锁定</button>
                </c:if>--%>
                <button class="am-btn am-btn-xs am-btn-outline-default am-icon-trash-o" @click="deleteTeacher(${teacher.id})">删除</button>
            </div>
            <div class="ifr-js-shrink">
                <p>账号名称：${teacher.teacherName}</p>
                <p>联系方式：${teacher.phoneNum}</p>
                <p>电子邮箱：${teacher.email}</p>
            </div>
        </div>
        <div class="am-margin-vertical ifr-div-shrink">
            <button class="am-fr am-btn am-btn-xs am-btn-default am-icon-chevron-circle-up" @click="shrink"> 收起</button>
            <button class="am-fr am-btn am-btn-xs am-btn-default am-icon-chevron-circle-down am-hide" @click="shrink"> 展开</button>
        </div>
    </div>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/admin/manage.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

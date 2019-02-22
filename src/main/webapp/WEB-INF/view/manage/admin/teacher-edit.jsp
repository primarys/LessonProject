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
        <li><a href="${ctx}/manage/admin/teacherList" class="am-icon-arrow-circle-left">上一级</a></li>
        <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
        <li><a href="${ctx}/manage/admin/teacherList">成员管理</a></li>
        <li class="am-active">编辑</li>
    </ol>
    <form id="form-updateTeacher" class="am-form">
        <div class="am-cf" style="width: 600px; margin: 0 auto">
            <div class="am-form-group am-text-center am-fl">
                <ul class="am-gallery am-gallery-overlay am-text-center">
                    <li>
                        <div class="am-gallery-item ifr-img-head" style="margin: 0 auto">
                            <img :src="headUrl" class="ifr-img-head"/>
                            <a class="am-gallery-title am-text-white" @click="resetHead">恢复默认头像</a>
                            <input type="hidden" name="head" v-model="head">
                            <input type="hidden" name="mid" value="${teacher.id}">
                            <input type="hidden" id="headUrl" value="${teacher.headPortrait.url}" v-model="headUrl">
                            <input type="hidden" id="defaultUrl" value="${Const.IMG_DEFAULT_TEACHER_HEAD}">
                        </div>
                    </li>
                </ul>
                <strong>${teacher.userName}</strong>
            </div>
            <div class="am-fl" style="width: 300px; margin: 1rem">
                <div class="am-form-group">
                    <label>名称</label>
                    <input type="text" name="name" placeholder="输入老师名称" value="${teacher.teacherName}">
                </div>
                <div class="am-form-group">
                    <label>联系方式</label>
                    <input type="text" name="phone" placeholder="输入老师联系方式" value="${teacher.phoneNum}">
                </div>
                <div class="am-form-group">
                    <label>电子邮箱</label>
                    <input type="text" name="email" placeholder="输入老师电子邮箱" value="${teacher.email}">
                </div>
                <div class="am-form-group">
                    <button type="submit" class="am-btn am-btn-primary am-btn-block am-icon-save" @click="updateTeacher"> 保存</button>
                </div>
                <hr>
                <div class="am-form-group">
                    <button type="button" class="am-btn am-btn-outline-warning am-icon-key" @click="resetTeacherPassword(${teacher.id})"> 重设密码</button>
                    <button type="button" class="am-btn am-btn-outline-default am-icon-trash-o" @click="deleteTeacher(${teacher.id})"> 删除账号</button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/admin/manage.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

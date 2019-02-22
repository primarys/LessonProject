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
    <form id="form-updateTeacher" class="am-form" enctype="multipart/form-data" method="post" @submit="updateTeacher">
        <div class="am-cf" style="width: 600px; margin: 0 auto">
            <c:if test="${empty teacher.phoneNum || empty teacher.teacherName}">
                <div class="am-alert am-alert-warning" data-am-alert>
                    <button type="button" class="am-close">&times;</button>
                    <p>请完善您的个人信息</p>
                </div>
            </c:if>
            <div class="am-form-group am-text-center am-fl">
                <ul class="am-gallery am-gallery-overlay am-text-center">
                    <li>
                        <div class="am-gallery-item ifr-img-head" style="margin: 0 auto">
                            <img :src="headUrl" class="ifr-img-head"/>
                            <div class="am-gallery-title am-text-white">
                                <div class="am-u-sm-6 am-padding-0" @click="resetHead" style="cursor: default">恢复默认</div>
                                <div class="am-u-sm-6 am-padding-0">更换头像</div>
                                <input id="file" class="ifr-input-head" type="file" name="newHead" @change="preview"
                                       accept="image/jpeg,image/jpg,image/png">
                            </div>
                            <p class="am-text-muted am-text-xs">* 建议尺寸为200&times;200像素<br>支持jpg或png格式的图片</p>
                            <input type="hidden" name="head" v-model="head">
                            <input type="hidden" name="id" value="${teacher.id}">
                            <input type="hidden" id="headUrl" value="${teacher.headPortrait.url}">
                            <input type="hidden" id="defaultUrl" value="${Const.IMG_DEFAULT_TEACHER_HEAD}">
                        </div>
                    </li>
                </ul>
            </div>
            <div class="am-fl" style="width: 300px; margin: 1rem">
                <div class="am-form-group">
                    <label>名称 *</label>
                    <input type="text" name="name" placeholder="输入教师名称" value="${teacher.teacherName}" required>
                </div>
                <div class="am-form-group">
                    <label>联系方式 *</label>
                    <input type="text" name="phone" placeholder="输入教师联系方式" value="${teacher.phoneNum}" required>
                </div>
                <div class="am-form-group">
                    <label>电子邮箱</label>
                    <input type="text" name="email" placeholder="输入教师电子邮箱" value="${teacher.email}">
                </div>
                <div class="am-form-group">
                    <button type="submit" class="am-btn am-btn-primary am-btn-block am-icon-save"> 保存</button>
                </div>
                <hr>
                <div class="am-form-group">
                    <a class="am-btn am-btn-outline-warning am-btn-block am-icon-key am-text-warning" href="modifyPassword"> 修改密码</a>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/teacher/manage.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

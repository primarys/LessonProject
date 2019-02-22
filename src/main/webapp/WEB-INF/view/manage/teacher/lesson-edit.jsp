<%@page language="java" pageEncoding="utf-8"%>
<%@page import="com.project.utils.Const"%>
<%@page import="com.project.utils.DateUtils"%>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link href="${rctx}/css/manage/frame_styles.css${nocache}" type="text/css" rel="stylesheet">
    <%@include file="/resource/utils/InputNumber/import.jsp"%>
    <%@include file="/resource/utils/DatetimePicker/import-css.jsp"%>
</head>
<body>
<div class="main" id="app">
    <ol class="am-breadcrumb am-margin-bottom-sm">
        <li><a href="javascript:history.back()" class="am-icon-arrow-circle-left">上一级</a></li>
        <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
        <%--<li><a href="${ctx}/manage/teacher/lessonList">课程管理</a></li>--%>
        <li class="am-active">编辑</li>
    </ol>
    <form id="ajax-form" class="am-form" @submit="saveLesson" method="post" enctype="multipart/form-data">
        <div style="width: 545px; margin: 0 auto">
            <div data-am-widget="titlebar" class="am-titlebar am-titlebar-default am-margin-top-0" >
                <h2 class="am-titlebar-title ">
                    课程信息
                </h2>
            </div>
            <div class="am-cf">
                <div class="am-fl" style="width: 300px; margin: 1rem">
                    <div class="am-form-group">
                        <label>名称</label>
                        <input type="text" name="name" placeholder="输入课程活动名称" value="${lesson.lessonName}" required>
                    </div>
                    <div>
                        <input type="hidden" name="lessonId" value="${lesson.id}" placeholder="-1" required>
                    </div>
                    <%--设置开始、结束默认时间--%>
                    <c:if test="${empty lesson}">
                        <c:set value="<%=new Date()%>" var="beginTime"></c:set>
                        <c:set value="<%=DateUtils.tomorrow()%>" var="endTime"></c:set>
                    </c:if>
                    <c:if test="${not empty lesson}">
                        <c:set value="${lesson.beginTime}" var="beginTime"></c:set>
                        <c:set value="${lesson.endTime}" var="endTime"></c:set>
                    </c:if>
                    <div class="am-form-group">
                        <label>开始时间</label>
                        <div class="am-input-group input-group" id='beginTimePicker'>
                            <input class="am-form-field" name="beginTime" required readonly
                                   value="<fmt:formatDate value="${beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                            <span class="am-input-group-label datepickerbutton am-bg-white">
                                <i class="icon-th am-icon-calendar"></i>
                            </span>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label>结束时间</label>
                        <div class="am-input-group input-group" id="endTimePicker">
                            <input class="am-form-field" name="endTime" required readonly
                                   value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                            <span class="am-input-group-label datepickerbutton am-bg-white">
                                <i class="icon-th am-icon-calendar"></i>
                              </span>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label>详细介绍</label>
                        <textarea name="content" placeholder="输入课程活动详细介绍" required style="height: 140px">${lesson.introduction}</textarea>
                    </div>
                    <button type="submit" class="am-btn am-btn-primary am-btn-lg am-btn-block am-icon-save"> 保存</button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
<%@include file="/resource/utils/DatetimePicker/import-js.jsp"%>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/teacher/lesson.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

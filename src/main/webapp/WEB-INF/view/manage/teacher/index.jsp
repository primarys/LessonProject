<%@ page language="java" pageEncoding="utf-8" import="com.project.utils.Const" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link href="${rctx}/css/manage/frame_styles.css${nocache}" type="text/css" rel="stylesheet">
    <%@include file="/resource/utils/DatetimePicker/import-css.jsp"%>
</head>
<body>
<%
    Date date = new Date();
    int hour = date.getHours();
    String noon = "早上";
    if (hour >= 0 && hour <= 5) {
        noon = "凌晨";
    } else if (hour >= 8 && hour <= 10) {
        noon = "上午";
    } else if (hour > 10 && hour <= 12) {
        noon = "中午";
    } else if (hour > 12 && hour <= 16) {
        noon = "下午";
    } else if (hour > 16 && hour <= 18) {
        noon = "傍晚";
    } else if (hour > 18 && hour <= 24) {
        noon = "晚上";
    }
    request.setAttribute("noon", noon);
%>
<div class="main">
    <div class="am-cf am-padding-0">
        <div class="am-u-lg-9 am-u-sm-8 am-padding-top-sm" style="width: 100%">
            <div class="am-margin-bottom-sm">
                <img class="am-circle am-thumbnail am-inline am-margin-0" src="${teacher.headPortrait.url}" style="width: 80px; height: 80px">
                <strong class="am-margin-left-sm">${noon}好，${teacher.teacherName}老师</strong>
            </div>
            <%--<small>
                <i class="am-icon-laptop"></i> 上次登录IP：${teacher.lastLoginIP}，
                <i class="am-icon-clock-o"></i> 上次登录时间：
                <fmt:formatDate value="${teacher.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </small>--%>
            <%--<div class="am-margin-vertical">
                <div class="am-panel am-panel-default">
                    <div class="am-panel-hd">常用功能</div>
                    <div class="am-panel-bd am-cf am-padding-horizontal-0">
                        &lt;%&ndash;<div class="am-u-sm-4 am-margin-bottom">
                            <a class="am-icon-flag" href="${ctx}/manage/teacher/classSign"> 课堂签到</a>
                        </div>&ndash;%&gt;
                        <div class="am-u-sm-6 am-margin-bottom">
                            <a class="am-icon-flag" href="${ctx}/manage/teacher/lessonList"> 课程管理</a>
                        </div>
                        <div class="am-u-sm-6 am-margin-bottom">
                            <a class="am-icon-pencil" href="${ctx}/manage/teacher/edit"> 修改信息</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>--%>
            <div class="main" id="app">
                <div class="am-margin-vertical">
                    <a class="am-btn am-btn-sm am-btn-primary am-icon-plus am-text-white am-radius" href="${ctx}/manage/teacher/editLesson"> 新增课程</a>
                </div>
                <%--<form action="${ctx}/manage/teacher/classBegin" method="post">--%>
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
                        <c:forEach items="${lessonList}" var="lesson" varStatus="i">
                            <tr>
                                <td class="am-text-middle">
                                    <a href="${ctx}/manage/teacher/editLesson?id=${lesson.id}">${lesson.lessonName}</a>
                                </td>
                                <td class="am-text-middle"><fmt:formatDate value="${lesson.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td class="am-text-middle"><fmt:formatDate value="${lesson.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>
                                        <a class="am-btn am-btn-sm am-btn-success am-text-white am-icon-eye am-radius"
                                           href="${ctx}/manage/teacher/viewLesson?id=${lesson.id}"> 查看</a>
                                        <c:if test="${lesson.timeStamp!=0}">
                                            <a class="am-btn am-btn-sm am-btn-success am-text-white am-icon-flag am-radius"
                                               href="${ctx}/manage/teacher/classSign?id=${lesson.id}"> 学生签到</a>
                                            <a class="am-btn am-btn-sm am-btn-warning am-text-white am-icon-calendar-check-o am-radius"
                                               href="${ctx}/manage/teacher/rollCall?id=${lesson.id}&type=${Const.ROLLCALL_RANDOM}"> 随机点名</a>
                                            <a class="am-btn am-btn-sm am-btn-warning am-text-white am-icon-hand-paper-o am-radius"
                                               href="${ctx}/manage/teacher/quiz?id=${lesson.id}"> 课堂提问</a>
                                        </c:if>
                                        <c:if test="${lesson.timeStamp==0}">
                                                <%--<input type="hidden" name="lessonId" value="${lesson.id}">--%>
                                                <button class="am-btn am-btn-sm am-btn-primary am-text-white am-icon-flag am-radius"
                                                  @click="beginClass(${lesson.id},'${lesson.lessonName}',${i.index})" > 开始上课</button>
                                        </c:if>
                                        <button class="am-btn am-btn-sm am-btn-outline-default am-icon-trash-o am-radius" @click="deleteLesson(${lesson.id})"> 删除</button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                <%--</form>--%>
            </div>
        </div>
        <c:set var="classBeginFlag" scope="session" value="false"/>
        <c:forEach items="${lessonList}" var="lesson">
            <c:if test="${lesson.timeStamp!=0}">
                <c:set var="classBeginFlag" scope="session" value="true"/>
                <c:set var="classBeginName" scope="session" value="${lesson.lessonName}"/>
            </c:if>
        </c:forEach>
        <c:forEach items="${lessonList}" var="lesson" varStatus="j">
            <div class="am-modal am-modal-confirm" tabindex="-1" id="classBegin-confirm${j.index}">
                <div class="am-modal-dialog">
                    <c:if test="${!classBeginFlag}">
                        <div class="am-modal-bd">
                            是否确定${lesson.lessonName}开始上课？
                        </div>
                    </c:if>
                    <c:if test="${classBeginFlag}">
                        <div class="am-modal-bd">
                            ${classBeginName}正在上课，确定更换成${lesson.lessonName}开始上课？
                        </div>
                    </c:if>
                    <div class="am-modal-footer">
                        <span class="am-modal-btn" data-am-modal-confirm>确定</span>
                        <span class="am-modal-btn" data-am-modal-cancel>取消</span>
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="am-modal am-modal-confirm" tabindex="-1" id="deleteLesson-step1">
            <div class="am-modal-dialog">
                <div class="am-modal-hd">确认删除该课程？</div>
                <div class="am-modal-bd">
                    删除后课程以及学生列表将被删除，请谨慎操作。
                </div>
                <div class="am-modal-footer">
                    <span class="am-modal-btn" data-am-modal-confirm>确定</span>
                    <span class="am-modal-btn" data-am-modal-cancel>取消</span>
                </div>
            </div>
        </div>
        <%--<div style="float: right" class="am-u-lg-3 am-u-sm-4 am-padding-top-sm">--%>
        <%--&lt;%&ndash;<aside class="am-u-lg-3 am-u-sm-4 am-padding-top-sm am-cf">&ndash;%&gt;--%>
            <%--&lt;%&ndash;侧边栏，显示时间、天气等信息吧&ndash;%&gt;--%>
            <%--<div id="datetimepicker" class="am-fr"></div>--%>
        <%--&lt;%&ndash;</aside>&ndash;%&gt;--%>
        <%--</div>--%>
    <footer class="am-text-center am-text-sm am-padding-sm am-topbar-fixed-bottom">
        <%@include file="/common/copyright.jsp"%>
    </footer>
</div>
</div>
</body>
<%@include file="/resource/utils/DatetimePicker/import-js.jsp"%>
<script>
    $(function() {
        $('#datetimepicker').datetimepicker({
            inline: true,
            viewMode: 'days',
            format: 'YYYY-MM-DD'
        });
        $("#datetimepicker .picker-switch")[0].removeAttribute("title");
    });
</script>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/teacher/lesson.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

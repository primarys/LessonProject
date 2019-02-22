<%@ page language="java" pageEncoding="utf-8" import="com.project.utils.Const" %>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link href="${rctx}/css/manage/frame_styles.css${nocache}" type="text/css" rel="stylesheet">
    <style>
        .jj a{
            color: white;
            font-size: 30px;
        }
        .am-modal-dialog {
            border-radius: 5px!important;
        }
    </style>
    <%--<%@include file="/resource/utils/DataTable/import.jsp"%>--%>
</head>
<body>
<div class="main" id="c-app">
    <ol class="am-breadcrumb am-margin-bottom-sm">
        <li><a href="${ctx}/manage/teacher/viewLesson?id=${lesson.id}" class="am-icon-arrow-circle-left">上一级</a></li>
        <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
        <%--<li><a href="${ctx}/manage/teacher/lessonList">课程管理</a></li>--%>
        <li><a href="${ctx}/manage/teacher/viewLesson?id=${lesson.id}">${lesson.lessonName}</a></li>
        <li class="am-active">查看学生</li>
    </ol>
    <div class="am-cf">
        <div class="ifr-div-info-box ifr-div-info-box-strip">
            <div>
                <a class="am-btn am-btn-xs am-btn-warning am-text-white am-icon-pencil" href="${ctx}/manage/teacher/editStudent?vid=${lesson.id}&cid=${student.id}"> 编辑</a>
                <%--<button class="am-btn am-btn-xs am-btn-outline-default am-icon-trash-o" @click="deleteStudent(${student.id})"> 删除</button>--%>
            </div>
            <h2 style="margin-top: 10px;margin-bottom: 0">${student.name}</h2>
            <div>
                <p style="margin:0 0 5px">学号：${student.studentNum}</p>
                <%--<p>性别：
                    <c:if test="${student.sex == 1}">男</c:if>
                    <c:if test="${student.sex == 2}">女</c:if>
                </p>--%>
            </div>
        </div>
    </div>
    <div class="am-g am-padding-0">
        <div class="am-u-sm-6">
            <table class="am-table am-table-hover am-table-striped am-table-centered">
                <caption><h1>考勤情况</h1></caption>
                <thead>
                <tr class="am-active">
                    <th>日期</th>
                    <th>出席情况</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${rollCallList}" var="rollCall" varStatus="i">
                    <tr>
                        <td class="am-text-middle"><fmt:formatDate value="${rollCall.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="am-text-middle">
                            <span class="awardorstate-onlyshow presense-bg am-text-white" v-if="${rollCall.state == 1}">出席</span>
                            <span class="awardorstate-onlyshow leave-bg am-text-white" v-if="${rollCall.state == 2}">请假</span>
                            <span class="awardorstate-onlyshow late-bg am-text-white" v-if="${rollCall.state == 3}">迟到</span>
                            <span class="awardorstate-onlyshow absense-bg am-text-white" v-if="${rollCall.state == 4}">缺席</span>
                            <span class="awardorstate-onlyshow presense-bg am-text-white" v-if="${rollCall.state == 5}">未点名</span>
                            <%--<div id="changeRollStateDropdown${i.index}"  class="am-dropdown am-dropdown-up" style="padding: auto" data-am-dropdown>
                            <span id="rollIndex${i.index}" class="am-dropdown-toggle" data-am-dropdown-toggle style="cursor:pointer;">
                                <span class="am-badge am-badge-success" v-if="${rollCall.state == 1}">出席&lt;%&ndash;<span class="am-icon-caret-down"></span>&ndash;%&gt;</span>
                                <span class="am-badge am-badge-warning" v-if="${rollCall.state == 2}">请假</span>
                                <span class="am-badge am-bg-info am-text-white" v-if="${rollCall.state == 3}">迟到</span>
                                <span class="am-badge am-badge-danger" v-if="${rollCall.state == 4}">缺席</span>
                                <span class="am-badge am-badge-success" v-if="${rollCall.state == 5}">未点名</span>
                            </span>
                                <ul class="am-dropdown-content">
                                    <li class="am-dropdown-header">修改状态</li>
                                    <li class="am-divider"></li>
                                    <li><a @click="changeRollState(1,${rollCall.id},${i.index})">出席</a></li>
                                    <li><a @click="changeRollState(2,${rollCall.id},${i.index})">请假</a></li>
                                    <li><a @click="changeRollState(3,${rollCall.id},${i.index})">迟到</a></li>
                                    <li><a @click="changeRollState(4,${rollCall.id},${i.index})">缺席</a></li>
                                </ul>
                            </div>--%>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
        <div class="am-u-sm-6">
            <table class="am-table am-table-hover am-table-striped am-table-centered">
                <caption><h1>课堂提问记录</h1></caption>
                <thead>
                <tr class="am-active">
                    <th>日期</th>
                    <th>问答得分</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${quizList}" var="quiz" varStatus="j">
                    <tr>
                        <c:set var="quizAward" scope="page" value="${quiz.award}"/>
                        <c:if test="${quizAward > 0}" >
                        <td class="am-text-middle"><fmt:formatDate value="${quiz.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td <%--id="quizIndex${j.index}"--%> class="am-text-middle">
                            +${quiz.award}
                            <%--<div id="changeQuizAwardDropdown${j.index}" class="am-dropdown am-dropdown-up" data-am-dropdown>
                                <span class="am-dropdown-toggle" data-am-dropdown-toggle style="cursor:pointer;">+${quiz.award}</span>
                                <ul class="am-dropdown-content">
                                    <li class="am-dropdown-header">修改加分</li>
                                    <li class="am-divider"></li>
                                    <input id="AwardInput${j.index}" type="number" class="am-form-field" value="${quiz.award}">
                                    <li><a @click="changeQuizAward(${quiz.id},${j.index})">确定</a></li>
                                </ul>
                            </div>--%>
                        </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/teacher/student.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

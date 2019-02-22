<%@ page language="java" import="com.project.utils.*" pageEncoding="utf-8"%>
<html>
<head>
    <title>课堂扫码签到</title>
    <%@include file="/common/framework.jsp" %>
    <link rel="stylesheet" href="${rctx }/css/student/m/stuSelf.css${nocache}">
</head>
<body>
    <form action="${ctx}/confirm" method="post">
        <input type="hidden" name="studentId" value="${student.id}">
        <input type="hidden" name="lessonId" value="${lessonId}">
        <%--<button class="am-btn am-btn-success" type="submit">签到成功</button>--%>
        <div class="checkin-wrapper">
            <button class="checkin-btn" type="submit">签到成功</button>
        </div>
    </form>
    <div class="bd spacing">
        <div class="base-block">
            <h1><i class="am-icon-hand-pointer-o"></i>${student.name}</h1>
        </div>
    </div>
    <footer class="am-text-center am-text-sm am-padding-sm am-topbar-fixed-bottom">
        <%@ include file="/common/copyright.jsp" %>
    </footer>
</body>
</html>

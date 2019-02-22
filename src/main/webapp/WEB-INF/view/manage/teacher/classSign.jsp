<%--
  Created by IntelliJ IDEA.
  User: fym
  Date: 2018/3/10
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" import="com.project.utils.Const" %>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link href="${rctx}/css/manage/frame_styles.css${nocache}" type="text/css" rel="stylesheet">
    <style>
        #invite-qrcode {
            padding-top: 20px;
            border-bottom: 1px solid #ddd;
        }
    </style>
</head>
<body>
<div class="main" id="app">
    <ol class="am-breadcrumb am-margin-bottom-sm">
        <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
        <li class="am-active">课堂签到</li>
    </ol>
    <div class="am-margin-vertical" style="text-align: center">
        <a href="${ctx}/toRegister?lessonId=${lessonId}" target="_blank">
            <div id="invite-qrcode"></div>
        </a>
    </div>
    <br>
    <div class="my-form-content am-form-group" style="text-align: center">
        <div>启用二维码扫码点名后，未扫码的同学将自动记为缺席。</div>
    </div>
</div>
</body>
<script>
    var text = "http://www.pzcnet.com/LessonProject/toRegister?lessonId="+${lessonId};
    var temp = new $.AMUI.qrcode({
        text: text,
        width: 280,
        height: 280
    });

    setTimeout(function () {
        $("#invite-qrcode").html(temp);
    }, 20)

</script>
<script src="${rctx}/js/manage/teacher/lesson.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

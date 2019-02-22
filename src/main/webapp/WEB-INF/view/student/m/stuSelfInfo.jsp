<%@ page language="java" import="com.project.utils.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>个人中心</title>
    <%@ include file="/common/framework.jsp" %>
    <link rel="stylesheet" type="text/css" href="${rctx }/css/mobile/navbar.css">
    <link rel="stylesheet" type="text/css" href="${rctx }/css/student/index.css">
    <style>
        .am-modal-dialog{
            border-radius:1em !important;
        }
    </style>
</head>
<body>
<div class="user-center-container">
    <div class="profile-info-block">
        <!-- 信息栏 -->
        <a href="${ctx }/customer/m/toMyInfo">
            <div class="info-box">
                    <span class="profile-avatar fa fa-user" >
                        <%--<c:if test="${not empty student.headPortrait.url}">--%>
                            <%--<img src="${student.headPortrait.url}"/>--%>
                        <%--</c:if>--%>
                        <%--<c:if test="${empty student.headPortrait.url}">--%>
                            <%--<img src="${rctx }/image/common/pzc_logo_simple.png"/>--%>
                        <%--</c:if>--%>
                    </span>
                <div class="info-name">
                    <span>昵称</span>
                    <p>欢迎来到知识社区</p>
                </div>
            </div>
        </a>
        <div class="info-data">
    </div>
    <div class="profile-base-block">
        <a class="mock-title" href="${ctx }/customer/manage/m/toOrderAll">
            <span class="my-order fa fa-myatten fa-fw"></span>
            我的出勤
            <span class="arrow-right fa fa-chevron-right"></span>
        </a>
        <a class="mock-title" href="${ctx }/customer/manage/m/listSupplierOrder">
            <span class="my-information fa fa-shopping-cart fa-fw"></span>
            我的点名
            <span class="arrow-right fa fa-chevron-right"></span>
        </a>
        <a class="mock-title" href="${ctx }/customer/manage/supGroupOrder/orderList">
            <span class="am-text-danger fa fa-users fa-fw"></span>
            我的问答
            <span class="arrow-right fa fa-chevron-right"></span>
        </a>
    </div>
</div>
</body>
</html>

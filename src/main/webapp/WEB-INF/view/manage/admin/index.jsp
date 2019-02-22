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
        <div class="am-u-lg-9 am-u-sm-8 am-padding-top-sm">
            <div>
                <h3>${noon}好，${admin.name}</h3>
                <small>
                    <i class="am-icon-clock-o"></i> 上次登录时间：
                    <fmt:formatDate value="${admin.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>，
                    <i class="am-icon-laptop"></i> 上次登录IP：${admin.lastLoginIP}
                </small>
            </div>
            <div class="am-margin-vertical">
                <div class="am-panel am-panel-default">
                    <div class="am-panel-hd">常用功能</div>
                    <div class="am-panel-bd am-cf am-padding-horizontal-0">
                        <div class="am-u-sm-6 am-margin-bottom">
                            <a class="am-icon-users" href="${ctx}/manage/admin/teacherList"> 查看所有教师</a>
                        </div>
                        <div class="am-u-sm-6 am-margin-bottom">
                            <a class="am-icon-pencil" href="${ctx}/manage/admin/edit"> 修改信息</a>
                        </div>
                        <div class="am-u-sm-6 am-margin-bottom">
                            <a class="am-icon-list" href=""> 更多</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <aside class="am-u-lg-3 am-u-sm-4 am-padding-top-sm am-cf">
            <%--侧边栏，显示时间、天气等信息吧--%>
                <div id="datetimepicker" class="am-fr"></div>
        </aside>
    </div>
    <footer class="am-text-center am-text-sm am-padding-sm am-topbar-fixed-bottom">
        <%@include file="/common/copyright.jsp"%>
    </footer>
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
</html>

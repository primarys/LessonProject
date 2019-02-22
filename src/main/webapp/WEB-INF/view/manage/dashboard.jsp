<%@ page language="java" import="com.project.utils.Const" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link rel="stylesheet" href="${rctx}/css/manage/dashboard.css${nocache}">
</head>
<body data-type="index" style="overflow-scrolling: none">
<header class="am-container-fluid am-padding-horizontal am-topbar-fixed-top">
    <img src="${rctx}/image/common/website.png" id="logo">
    <div class="am-fr" style="margin-top: 25px">
        <a id="ifr-js-fullpage" class="am-text-white am-icon-arrows-alt am-margin-right"> 全屏</a>
        <a id="ifr-js-fullpage-exit" class="am-text-white am-icon-arrows-alt am-margin-right am-hide"> 退出全屏</a>
        <a href="${ctx}/logout" class="am-text-white am-icon-sign-out"> 注销</a>
    </div>
</header>
<div class="am-container-fluid am-padding-right-0">
    <div id="lessonAside" class="tpl-left-nav tpl-left-nav-hover">
        <div class="tpl-left-nav-title">
            平台管理
        </div>
        <div class="tpl-left-nav-list">
            <ul class="tpl-left-nav-menu">
                <li class="tpl-left-nav-item">
                    <a href="${ctx}/manage/dashboard" class="nav-link active">
                        <i class="am-icon-home am-icon-fw"></i>
                        <span>首页</span>
                    </a>
                </li>
                <c:if test="${role == Const.USER_ADMIN}">
                    <li class="tpl-left-nav-item">
                        <a href="${ctx}/manage/admin/teacherList" class="nav-link" target="mainframe">
                            <i class="am-icon-users am-icon-fw"></i>
                            <span>教师管理</span>
                        </a>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="${ctx}/manage/admin/edit" class="nav-link ifr-js-a-info" target="mainframe"
                            <c:if test="${empty admin.phone || admin.name}">title="请完善您的个人信息"</c:if>>
                            <i class="am-icon-pencil am-icon-fw"></i>
                            <span>修改资料</span>
                            <c:if test="${empty admin.phone || admin.name}">
                                <b class="am-icon-circle am-text-primary am-text-xs"></b>
                            </c:if>
                        </a>
                    </li>
                </c:if>
                <c:if test="${role == Const.USER_TEACHER}">
                    <%--<li class="tpl-left-nav-item">
                        <a href="${ctx}/manage/teacher/lessonList" class="nav-link" target="mainframe">
                            <i class="am-icon-flag am-icon-fw"></i>
                            <span>课程管理</span>
                        </a>
                    </li>--%>
                    <li class="tpl-left-nav-item">
                        <a href="${ctx}/manage/teacher/edit" class="nav-link ifr-js-a-info" target="mainframe"
                            <c:if test="${empty teacher.phoneNum || teacher.teacherName}">title="请完善您的个人信息"</c:if>>
                            <i class="am-icon-pencil am-icon-fw"></i>
                            <span>修改资料</span>
                            <c:if test="${empty teacher.phoneNum || teacher.teacherName}">
                                <b class="am-icon-circle am-text-primary am-text-xs"></b>
                            </c:if>
                        </a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
    <iframe src="${ctx}/manage/${role}/index"  scrolling="auto" id="mainframe" name="mainframe"
            allowfullscreen mozallowfullscreen webkitallowfullscree
            frameborder="0" onLoad="resizeHeight()"></iframe>
</div>
<script>
    // iframe自适应高度
    function resizeHeight() {
        $("iframe").height($(window).height()-79);
        $(window).resize(function() {
            $("iframe").height($(window).height()-79);
        });
    }
    resizeHeight();

    // 导航栏点击效果
    $('.tpl-left-nav-link-list').on('click', function() {
        $(this).siblings('.tpl-left-nav-sub-menu').slideToggle(80)
            .end()
            .find('.tpl-left-nav-more-ico').toggleClass('tpl-left-nav-more-ico-rotate');
    });
    $(".nav-link").on('click', function() {
        $(".nav-link").removeClass("active");
        $(this).addClass("active");
    });

    // 全屏切换
    $('#ifr-js-fullpage').on('click', function () {
        if ($.AMUI.fullscreen.enabled) {
            $.AMUI.fullscreen.request();
        }
        $(this).hide();
        $('#ifr-js-fullpage-exit').show();
    });
    $('#ifr-js-fullpage-exit').on('click', function () {
        if ($.AMUI.fullscreen.enabled) {
            $.AMUI.fullscreen.exit();
        }
        $(this).hide();
        $('#ifr-js-fullpage').show();
    });

    // 监听信息是否填写完整
//    var t = setInterval(function () {
//        $.post("checkInfoComplete", function (ret) {
//            if (ret) {
//                window.clearInterval(t);
//                $(".ifr-js-a-info").removeAttr("title").find("b").remove();
//            }
//        }, "json");
//    }, 5000);


</script>
</body>
</html>
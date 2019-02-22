<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>微信登录</title>
    <%@include file="/common/framework.jsp" %>
    <%--<script src="${rctx}/utils/salert/salert.js"></script>--%>
    <style>
        .am-modal-btn {
            color: #fff; border: 0
        }
    </style>
</head>
<body>
<header data-am-widget="header" class="am-header am-header-default am-header-fixed">
    <%--<div class="am-header-left am-header-nav">
        <a href="javascript:history.back()">
            <i class="am-header-icon am-icon-chevron-left"></i>
        </a>
    </div>--%>
    <h1 class="am-header-title">
        微信登录
    </h1>
    <%--<div class="am-header-right am-header-nav">
        <a data-am-modal="{target: '#registerActions'}">
            <i class="am-header-icon am-icon-bars"></i>
        </a>
    </div>--%>
</header>
<div data-am-widget="tabs" class="am-tabs am-tabs-d2 am-margin-0">
    <ul class="am-tabs-nav am-cf" id="tabs">
        <li :class="{'am-active': index == 0}" @click="showTab(0)"><a>微信注册登录</a></li>
    </ul>
    <div>
        <%--微信注册--%>
        <div data-tab-panel-0 class="am-padding-sm am-active">
            <div class="am-margin">
                <%--微信按钮--%>
                <div class="am-form" id="register-form-wechat">
                    <div class="am-form-group">
                        <button id="wechat-login" class="am-btn am-btn-success am-btn-block am-round"
                                :disabled="!readProtocol" @click="teacherWechatRegister">
                            <i class="fa fa-wechat"></i> 微信登录
                        </button>
                    </div>
                    <div class="am-form-group">
                        <label class="am-checkbox am-inline-block am-margin-0">
                            <input type="checkbox" data-am-ucheck
                                   :checked="readProtocol" v-model="readProtocol">
                            我已阅读并同意
                        </label>
                        <%--<a href="${ctx }/about/protocol.jsp" target="_blank" class="am-text-sm">《我知盘中餐网用户协议》</a>--%>
                        <div class="am-margin-top am-text-muted am-text-sm"style="text-align: center">
                            系统仅使用您的微信id自动分配账号
                            <br>（不会获取任何用户信息和隐私）
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--<div class="am-modal am-modal-alert" tabindex="-1" id="toLogin">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">
            提示
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">
            该账号已注册，可前往登录
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn am-btn-primary" onclick="location.href='${ctx}/customer/toLogin'">确定</span>
        </div>
    </div>
</div>--%>
<script src="${rctx}/js-edit/teacher/register.js${nocache}"></script>
</body>
</html>


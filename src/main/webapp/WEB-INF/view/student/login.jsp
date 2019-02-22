<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>课堂点名</title>
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
    <h1 class="am-header-title">
        课堂点名
    </h1>
</header>
<div class="am-form" id="register-form-wechat" style="padding-top: 60%;padding-left: 20%;padding-right: 20%;">
    <div class="am-form-group">
        <button id="wechat-login" class="am-btn am-btn-success am-btn-block am-round" @click="wechatRegister">
            <i class="am-sucess am-icon-wechat"></i> 微信签到
        </button>
    </div>
</div>
<footer class="am-text-center am-text-sm am-padding-sm am-topbar-fixed-bottom">
    <%@ include file="/common/copyright.jsp" %>
</footer>
<%--<div data-am-widget="tabs" class="am-tabs am-tabs-d2 am-margin-0">
    <ul class="am-tabs-nav am-cf" id="tabs">
        <li :class="{'am-active': index == 0}" @click="showTab(0)"><a>微信注册登录</a></li>
    </ul>
    <div>
        &lt;%&ndash;微信注册&ndash;%&gt;
        <div data-tab-panel-0 class="am-padding-sm am-active">
            <div class="am-margin">
                &lt;%&ndash;微信按钮&ndash;%&gt;
                <div class="am-form" id="register-form-wechat">
                    <div class="am-form-group">
                        <button id="wechat-login" class="am-btn am-btn-success am-btn-block am-round"
                                :disabled="!readProtocol" @click="wechatRegister">
                            <i class="fa fa-wechat"></i> 微信签到
                        </button>
                    </div>
                    <div class="am-form-group">
                        <label class="am-checkbox am-inline-block am-margin-0">
                            <input type="checkbox" data-am-ucheck
                                   :checked="readProtocol" v-model="readProtocol">
                            我已阅读并同意
                        </label>
                        &lt;%&ndash;<a href="${ctx }/about/protocol.jsp" target="_blank" class="am-text-sm">《我知盘中餐网用户协议》</a>&ndash;%&gt;
                        <div class="am-margin-top am-text-muted am-text-sm"style="text-align: center">
                            系统仅使用您的微信id自动分配账号
                            <br>（不会获取任何用户信息和隐私）
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>--%>
<script src="${rctx}/js-edit/student/register.js${nocache}"></script>
</body>
</html>


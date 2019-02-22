<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="${rctx}/css/manage/login.css${nocache}">
</head>
<body class="am-container">
<header class="am-text-left">
    <a href="${pctx}" target="_blank" id="pzc-logo">
        <img src="${rctx}/image/common/logo.png">
    </a>
    <img src="${rctx}/image/common/website.png" id="website-logo">
</header>
<div>
    <div class="am-text-center am-padding am-bg-white"
         style="width: 240px; height: 310px; margin: 0 auto; border-radius: 4px">
        <div id="qrcode"></div>
        <p>微信扫描上图的二维码<br>进入微信投票</p>
    </div>
</div>
<footer class="am-text-center am-text-sm am-padding-sm am-topbar-fixed-bottom">
    <%@include file="/common/copyright.jsp"%>
</footer>
</body>
<script>
    var QRCode = $.AMUI.qrcode;
    $("#qrcode").html(new QRCode({
        text: '${pctx}${empty jump ? ctx : jump}',
        width: 200,
        height: 200
    }));
</script>
</html>

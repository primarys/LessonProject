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
    <div class="am-margin-vertical" style="text-align: center">
        <a href="${ctx}/lunxun?lesson_id=${lesson_id}" target="_blank">
            <div id="invite-qrcode"></div>
        </a>
    </div>
</div>
</body>
<script>
    var text = "http://www.pzcnet.com/LessonProject/testlunxun";
    var temp = new $.AMUI.qrcode({
        text: text,
        width: 280,
        height: 280
    });

    setTimeout(function () {
        $("#invite-qrcode").html(temp);
    }, 20)

</script>
<script>
    $(document).ready(function(){
        var uuid = document.getElementsByName('uuid')[0].value;
        // 想法是通过ajax轮询，请求服务器上的登录状态信息
        $.ajax({
            url: '${ctx}/testlunxun',
            type: 'POST',
            async: true,
            data: {
                'uuid': uuid
            },

            timeout: 5000,
            dataType: 'json',

            success: function(result){
                alert("Success!");
                console.log(result);
            },
            error: function(){
                console.log('额，AJAX请求出错了！');
            }


        });


    });
</script>
</html>

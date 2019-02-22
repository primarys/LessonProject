<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/common_value.jsp"%>
    <title>${title}</title>
    <style>
        html, body, iframe {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }
    </style>
</head>
<body>
<iframe src="${pctx}/error/500.jsp" frameborder="0"></iframe>
</body>
</html>
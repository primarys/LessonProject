<%@ page language="java" pageEncoding="utf-8"%>
<%@page  import="com.project.utils.Const"%>
<%@page  import="java.net.URLEncoder"%>
<%-- 跳转到盘中餐的链接，可能会对盘中餐创建新用户 --%>
<c:if test="${not empty sessionScope.get(Const.USER_STUDENT)}">
    <c:set var="_student" value="${sessionScope.get(Const.USER_STUDENT)}"/>
    <c:set var="jctx" value="${pctx}/lessonJump?openId=${_student.wechatId}&nickname=${_student.wechatNickName}&head=${URLEncoder.encode(_student.wechatHeadUrl)}&target=${URLEncoder.encode(pctx)}"/>
</c:if>
<c:if test="${empty sessionScope.get(Const.USER_STUDENT)}">
    <c:set var="jctx" value="${pctx}/lessonJump?target=${URLEncoder.encode(pctx)}"/>
</c:if>
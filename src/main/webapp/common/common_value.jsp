<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%-- 系统根路径 --%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%-- 系统资源根路径 --%>
<c:set var="rctx" value="${ctx }/resource"/>
<%-- 系统页面根路径 --%>
<c:set var="vctx" value="${ctx }/WEB-INF/view"/>
<%-- 测试用jsp使用的根路径 --%>
<c:set var="tctx" value="/WEB-INF/view"/>
<%--娱学科技官网--%>
<c:set var="pctx" value="http://www.pzcnet.com/LessonProject"/>
<%--网站名称--%>
<c:set var="title" value="课程学生管理系统"/>
<%--网站版本号，用来避免css, js缓存--%>
<%
    request.setAttribute("nocache", "?v=" + Long.toString(new Date().getTime()));
%>
<%--页面html公共部分--%>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="WebRoot" content="${ctx }" />
<meta http-equiv="ResourcePath" content="${rctx }" /> 
<%-- 固定页面缩放为100%，并禁止页面缩放 --%>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkithttp-equiv='WebRoot' " />
<%--禁止页面缓存--%>
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache,must-revalidate">
<meta http-equiv="Cache" content="no-cache">
<%--<%@ include file="common_ie.jsp"%>--%>
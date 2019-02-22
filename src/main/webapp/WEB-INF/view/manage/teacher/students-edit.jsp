<%@ page language="java" pageEncoding="utf-8" import="com.project.utils.Const" %>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link href="${rctx}/css/manage/frame_styles.css${nocache}" type="text/css" rel="stylesheet">
</head>
<body>
<div class="main" id="c-app">
    <ol class="am-breadcrumb am-margin-bottom-sm">
        <li><a href="javascript:history.back()" class="am-icon-arrow-circle-left">上一级</a></li>
        <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
        <%--<li><a href="${ctx}/manage/teacher/lessonList">课程管理</a></li>--%>
        <li><a href="${ctx}/manage/teacher/viewLesson?id=${lesson.id}">${lesson.lessonName}</a></li>
        <li class="am-active">批量增加学生</li>
    </ol>
    <form id="ajax-form" class="am-form am-g am-padding-0" method="post" @submit="saveStudents">
        <input type="hidden" name="vid" value="${lesson.id}">
        <div class="am-u-sm-6 am-text-center">
            <textarea v-model="stuListText" @input="stuListInputStatus = ''"
                    placeholder="请按照【学号 姓名】输入数据&#13;&#10;不同属性使用空格或缩进符隔开，不同学生使用换行隔开，例如：&#13;&#10;23020171151234 小明&#13;&#10;23020171151235 小红" style="height: 300px"></textarea>
            <button type="button" class="am-btn am-btn-outline-primary am-margin" @click="translate">
                转换 <i class="am-icon-angle-double-right"></i>
            </button>
            <div class="am-text-muted am-text-sm">{{stuListInputStatus}}</div>
        </div>
        <div class="am-u-sm-6 am-text-center">
            <template>
                <div style="min-height: 300px">
                    <table class="am-table am-table-hover am-table-striped">
                        <thead>
                        <tr class="am-active">
                            <th>学号</th>
                            <th>姓名</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(stu, i) in stuList" v-if="stuList.length > 0">
                            <td>
                                <input type="text" name="num" :value="stu.num">
                            </td>
                            <td>
                                <input type="text" name="name" :value="stu.name">
                            </td>
                            <%--<td>
                                <label class="am-radio-inline">
                                    <input type="radio" :name="'sex' + i" value="1" data-am-ucheck
                                           :checked="stu.sex == 1"> 男
                                </label>
                                <label class="am-radio-inline">
                                    <input type="radio" :name="'sex' + i" value="2" data-am-ucheck
                                           :checked="stu.sex == 2"> 女
                                </label>
                            </td>--%>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <button type="submit" class="am-btn am-btn-primary" :disabled="!stuListCorrect">
                    保存 <i class="am-icon-save"></i>
                </button>
            </template>
        </div>
    </form>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/teacher/student.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
</html>

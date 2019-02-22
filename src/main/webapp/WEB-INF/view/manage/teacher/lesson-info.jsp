<%@ page language="java" pageEncoding="utf-8" import="com.project.utils.Const" %>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link href="${rctx}/css/manage/frame_styles.css${nocache}" type="text/css" rel="stylesheet">
    <%@include file="/resource/utils/DataTable/import-fix.jsp"%>
</head>
<body>
<div class="am-modal am-modal-confirm" tabindex="-1" id="deleteStudent-step1">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">确认删除该学生？</div>
        <div class="am-modal-bd">
            删除后该学生以及点名记录将被删除，请谨慎操作
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-confirm>确定</span>
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
        </div>
    </div>
</div>
<div class="am-modal am-modal-alert" tabindex="-1" id="needClassBegin-alert">
    <div class="am-modal-dialog">
        <div class="am-modal-bd ">
            请先返回首页点击开始上课才能进行相应操作
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn">确定</span>
        </div>
    </div>
</div>
<div class="main">
    <div id="app">
        <template>
            <ol class="am-breadcrumb am-margin-bottom-sm am-margin-top-0">
                <li><a href="${ctx}/manage/teacher/lessonList" class="am-icon-arrow-circle-left">上一级</a></li>
                <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
                <%--<li><a href="${ctx}/manage/teacher/lessonList">课程管理</a></li>--%>
                <li class="am-active">查看详情</li>
            </ol>
            <div class="am-cf">
                <%--<img src="${lesson.cover.url}"--%>
                     <%--:class="{'ifr-img-head am-thumbnail am-fl ifr-js-shrink':true, 'ifr-img-head-sm':shrinked}">--%>
                <div class="ifr-div-info-box">
                    <h2 class="am-margin-bottom-0">
                        ${lesson.lessonName}
                    </h2>
                    <div class="am-margin-bottom-sm">
                        <a class="am-btn am-btn-xs am-btn-warning am-text-white am-icon-pencil" href="${ctx}/manage/teacher/editLesson?id=${lesson.id}"> 编辑</a>
                        <c:if test="${lesson.timeStamp!=0}">
                            <div class="am-dropdown" data-am-dropdown>
                                <button class="am-btn am-btn-primary am-btn-xs am-dropdown-toggle" data-am-dropdown-toggle>
                                    <i class="am-icon-bell"></i>
                                    点名 <span class="am-icon-caret-down"></span></button>
                                <ul class="am-dropdown-content">
                                    <li><a href="rollCall?id=${lesson.id}&type=${Const.ROLLCALL_RANDOM}">随机点名</a></li>
                                    <li><a href="quiz?id=${lesson.id}">课堂提问</a></li>
                                </ul>
                            </div>
                        </c:if>
                        <c:if test="${lesson.timeStamp==0}">
                            <div class="am-dropdown">
                                <button class="am-btn am-btn-primary am-btn-xs" data-am-modal="{target: '#needClassBegin-alert'}">
                                    <i class="am-icon-bell"></i>
                                    点名 <span class="am-icon-caret-down"></span></button>
                            </div>
                        </c:if>
                        <%--<button class="am-btn am-btn-xs am-btn-outline-default am-icon-trash-o" @click="deleteLesson(${lesson.id})"> 删除</button>--%>
                    </div>
                    <div :class="{'ifr-js-shrink':true, 'am-hide':shrinked}">
                        <%--<p>创建时间：<fmt:formatDate value="${lesson.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>--%>
                        <p>持续时间：
                            <fmt:formatDate value="${lesson.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/> ~
                            <fmt:formatDate value="${lesson.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </p>
                        <p>详情：${lesson.introduction}</p>
                    </div>
                </div>
                <div class="am-margin-vertical ifr-div-shrink">
                    <button :class="{'am-fr am-btn am-btn-xs am-btn-default am-icon-chevron-circle-up':true, 'am-hide':shrinked}"
                            @click="shrink"> 收起</button>
                    <button :class="{'am-fr am-btn am-btn-xs am-btn-default am-icon-chevron-circle-down':true, 'am-hide':!shrinked}"
                            @click="shrink"> 展开</button>
                </div>
            </div>
        </template>
    </div>
    <div id="c-app">
        <template>
            <div class="am-padding-top am-padding-bottom" style="float: left">
                <a class="am-btn am-btn-primary am-btn-sm am-icon-user-plus am-text-white am-radius"
                   href="${ctx}/manage/teacher/editStudent?vid=${lesson.id}"> 增加学生</a>
                <a class="am-btn am-btn-primary am-btn-sm am-icon-users am-text-white am-radius"
                   href="${ctx}/manage/teacher/editStudents?vid=${lesson.id}"> 批量增加学生</a>
            </div>
            <div id="searchBox" class="am-padding-top am-padding-bottom" style="float: right">
                <form action="${ctx}/manage/teacher/viewLesson" method="post">
                    <input type="hidden" name="id" value="${lesson.id}">
                    <input style="height: 32px" name="studentNumOrName" autocomplete="off" placeholder="请输入学号或姓名" type="text">
                    <button class="am-btn am-btn-primary am-btn-sm am-icon-search am-text-white am-radius" name="button" type="submit">
                        搜索学生
                    </button>
                </form>
            </div>
            <table id="my-dataTable" class="am-table am-table-hover am-table-bordered am-table-centered am-text-nowrap table-margin-bottom">
            <thead>
            <tr class="am-active">
                <c:if test="${lesson.timeStamp!=0}">
                    <th rowspan="2" class="am-text-middle">学号</th>
                    <th rowspan="2" class="am-text-middle">姓名</th>
                    <%--<th rowspan="2" class="am-text-middle">性别</th>--%>
                    <th colspan="4">考勤情况</th>
                    <th rowspan="2" class="am-text-middle">本学期提问加分</th>
                    <th rowspan="2" class="am-text-middle">本节课出勤情况</th>
                    <th rowspan="2" class="am-text-middle">本节课提问加分情况</th>
                    <th rowspan="2" class="am-text-middle">操作</th>
                </c:if>
                <c:if test="${lesson.timeStamp==0}">
                    <th rowspan="2" class="am-text-middle">学号</th>
                    <th rowspan="2" class="am-text-middle">姓名</th>
                    <%--<th rowspan="2" class="am-text-middle">性别</th>--%>
                    <th colspan="4">考勤情况</th>
                    <th rowspan="2" class="am-text-middle">本学期提问加分</th>
                    <th rowspan="2" class="am-text-middle">操作</th>
                </c:if>
            </tr>
            <tr class="am-active">
                    <th>出席</th>
                    <th>请假</th>
                    <th>迟到</th>
                    <th>缺席</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${stuLessonList}" var="stuLesson" varStatus="i">
                <tr>
                    <c:if test="${lesson.timeStamp!=0}">
                        <td class="am-text-middle">${stuLesson.student.studentNum}</td>
                        <td class="am-text-middle">${stuLesson.student.name}</td>
                        <%--<td class="am-text-middle">
                            <c:if test="${stuLesson.student.sex == 1}">男</c:if>
                            <c:if test="${stuLesson.student.sex == 2}">女</c:if>
                        </td>--%>
                        <td id="${i.index}rollCallIndex1">${stuLesson.presense}</td>
                        <td id="${i.index}rollCallIndex2">${stuLesson.leaves}</td>
                        <td id="${i.index}rollCallIndex3">${stuLesson.late}</td>
                        <td id="${i.index}rollCallIndex4">${stuLesson.absense}</td>
                        <td id="quizAwardIndex${i.index}">${stuLesson.award}</td>
                        <td>
                            <div class="am-btn-group" style="height: 36px;">
                                <div id="rollIndex${i.index}" style="float: left">
                                    <span  class="awardorstate-show presense-bg am-text-white" style="height: 100%" v-if="${rollCallList.get(i.index).state == 1}">出席</span>
                                    <span  class="awardorstate-show leave-bg am-text-white" style="height: 100%" v-if="${rollCallList.get(i.index).state == 2}">请假</span>
                                    <span  class="awardorstate-show late-bg am-text-white" style="height: 100%" v-if="${rollCallList.get(i.index).state == 3}">迟到</span>
                                    <span  class="awardorstate-show absense-bg am-text-white" style="height: 100%" v-if="${rollCallList.get(i.index).state == 4}">缺席</span>
                                    <span  class="awardorstate-show presense-bg am-text-white" style="height: 100%" v-if="${rollCallList.get(i.index).state == 5}">未点名</span>
                                </div>
                                <div id="changeRollStateDropdown${i.index}" class="am-dropdown" style="height: 100%" data-am-dropdown>
                                    <div id="rollIndexDropdown${i.index}" class="am-dropdown-toggle"  data-am-dropdown-toggle>
                                        <button class="am-btn am-btn-success" style="height: 100%;" v-if="${rollCallList.get(i.index).state == 1}">
                                            <span class="am-icon-caret-down"></span>
                                        </button>
                                        <button class="am-btn am-btn-warning leave-bg" style="height: 100%;" v-if="${rollCallList.get(i.index).state == 2}">
                                            <span class="am-icon-caret-down"></span>
                                        </button>
                                        <button class="am-btn late-bg late-caret-down" style="height: 100%;" v-if="${rollCallList.get(i.index).state == 3}">
                                            <span class="am-icon-caret-down"></span>
                                        </button>
                                        <button class="am-btn am-btn-danger" style="height: 100%;" v-if="${rollCallList.get(i.index).state == 4}">
                                            <span class="am-icon-caret-down"></span>
                                        </button>
                                        <button class="am-btn am-btn-success" style="height: 100%;" v-if="${rollCallList.get(i.index).state == 5}">
                                            <span class="am-icon-caret-down"></span>
                                        </button>
                                    </div>
                                    <ul class="am-dropdown-content">
                                        <li class="am-dropdown-header">修改状态</li>
                                        <li class="am-divider"></li>
                                        <li><a @click="changeRollState(1,${rollCallList.get(i.index).id},${i.index})">出席</a></li>
                                        <li><a @click="changeRollState(2,${rollCallList.get(i.index).id},${i.index})">请假</a></li>
                                        <li><a @click="changeRollState(3,${rollCallList.get(i.index).id},${i.index})">迟到</a></li>
                                        <li><a @click="changeRollState(4,${rollCallList.get(i.index).id},${i.index})">缺席</a></li>
                                    </ul>
                                </div>
                            </div>
                            <%--<div id="changeRollStateDropdown${i.index}"  class="am-dropdown am-dropdown-up" style="padding: auto" data-am-dropdown>
                            <span id="rollIndex${i.index}" class="am-dropdown-toggle" data-am-dropdown-toggle style="cursor:pointer;">
                                <span  class="am-badge am-badge-success" v-if="${rollCallList.get(i.index).state == 1}">出席</span>
                                <span  class="am-badge am-badge-warning" v-if="${rollCallList.get(i.index).state == 2}">请假</span>
                                <span  class="am-badge am-bg-info am-text-white" v-if="${rollCallList.get(i.index).state == 3}">迟到</span>
                                <span  class="am-badge am-badge-danger" v-if="${rollCallList.get(i.index).state == 4}">缺席&lt;%&ndash;<span class="am-icon-caret-down"></span>&ndash;%&gt;</span>
                                <span  class="am-badge am-badge-success" v-if="${rollCallList.get(i.index).state == 5}">未点名</span>
                            </span>
                                <ul  class="am-dropdown-content">
                                    <li class="am-dropdown-header">修改状态</li>
                                    <li class="am-divider"></li>
                                    <li><a @click="changeRollState(1,${rollCallList.get(i.index).id},${i.index})">出席</a></li>
                                    <li><a @click="changeRollState(2,${rollCallList.get(i.index).id},${i.index})">请假</a></li>
                                    <li><a @click="changeRollState(3,${rollCallList.get(i.index).id},${i.index})">迟到</a></li>
                                    <li><a @click="changeRollState(4,${rollCallList.get(i.index).id},${i.index})">缺席</a></li>
                                </ul>
                            </div>--%>
                        </td>
                        <td  class="am-text-middle">
                            <div class="am-btn-group" style="height: 36px;">
                                <span id="quizIndex${i.index}" class="awardorstate-show award-bg am-text-white">+${quizList.get(i.index).award}</span>
                                <div id="changeQuizAwardDropdown${i.index}" class="am-dropdown" data-am-dropdown>
                                    <button class="am-btn award-caret-down am-text-white am-dropdown-toggle" data-am-dropdown-toggle> <span class="am-icon-caret-down"></span></button>
                                    <ul class="am-dropdown-content">
                                        <li class="am-dropdown-header">修改加分</li>
                                        <li class="am-divider"></li>
                                        <input id="AwardInput${i.index}" type="number" class="am-form-field" value="${quizList.get(i.index).award}">
                                        <li><a @click="changeQuizAward(${quizList.get(i.index).id},${i.index})">确定</a></li>
                                    </ul>
                                </div>
                            </div>
                        </td>
                        <td>
                            <a class="am-btn am-btn-sm am-btn-success am-text-white am-icon-eye am-radius" href="viewStudent?vid=${lesson.id}&cid=${stuLesson.student.id}"> 查看</a>
                                <%--<a class="am-btn am-btn-sm am-btn-warning am-text-white am-icon-pencil am-radius" href="editStudent?vid=${lesson.id}&cid=${stuLesson.student.id}"> 编辑</a>--%>
                            <button class="am-btn am-btn-sm am-btn-outline-default am-bg-white am-icon-trash-o am-radius" @click="deleteStudent(${lesson.id}, ${stuLesson.student.id})"> 删除</button>
                        </td>
                    </c:if>
                    <c:if test="${lesson.timeStamp==0}">
                        <td class="am-text-middle">${stuLesson.student.studentNum}</td>
                        <td class="am-text-middle">${stuLesson.student.name}</td>
                        <%--<td class="am-text-middle">
                            <c:if test="${stuLesson.student.sex == 1}">男</c:if>
                            <c:if test="${stuLesson.student.sex == 2}">女</c:if>
                        </td>--%>
                        <td>${stuLesson.presense}</td>
                        <td>${stuLesson.leaves}</td>
                        <td>${stuLesson.late}</td>
                        <td>${stuLesson.absense}</td>
                        <td>${stuLesson.award}</td>
                        <td>
                            <a class="am-btn am-btn-sm am-btn-success am-text-white am-icon-eye am-radius" href="viewStudent?vid=${lesson.id}&cid=${stuLesson.student.id}"> 查看</a>
                                <%--<a class="am-btn am-btn-sm am-btn-warning am-text-white am-icon-pencil am-radius" href="editStudent?vid=${lesson.id}&cid=${stuLesson.student.id}"> 编辑</a>--%>
                            <button class="am-btn am-btn-sm am-btn-outline-default am-bg-white am-icon-trash-o am-radius" @click="deleteStudent(${lesson.id}, ${stuLesson.student.id})"> 删除</button>
                        </td>
                    </c:if>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        </template>
    </div>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js/manage/teacher/lesson.js${nocache}"></script>
<script src="${rctx}/js/manage/teacher/student.js${nocache}"></script>
<script>
    $(function() {
        <c:if test="${not empty stuLessonList}">
            $("table").dataTable({
                "iDisplayLength": ${stuLessonList.size()},
                "bPaginate": false,
                "bLengthChange": false,
                "bFilter": false,
//                "aoColumnDefs": [ { "bSortable": false, "aTargets": [0, 6] }],
                "bInfo": false
            });
        </c:if>
        $("#my-dataTable").width("100%");
    });
</script>
<%@include file="../listenF5.jsp"%>
</html>

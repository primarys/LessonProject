<%@ page language="java" pageEncoding="utf-8" import="com.project.utils.Const" %>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link href="${rctx}/css/manage/frame_styles.css${nocache}" type="text/css" rel="stylesheet">
</head>
<body>
<div class="main">
    <ol class="am-breadcrumb am-margin-bottom-sm am-margin-top-0">
        <li><a href="${ctx}/manage/teacher/viewLesson?id=${lesson.id}" class="am-icon-arrow-circle-left">上一级</a></li>
        <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
        <%--<li><a href="${ctx}/manage/teacher/lessonList">课程管理</a></li>--%>
        <li><a href="${ctx}/manage/teacher/viewLesson?id=${lesson.id}">${lesson.lessonName}</a></li>
        <li class="am-active">
            <c:if test="${type == 1}">随机点名</c:if>
            <%--<c:if test="${type == Const.ROLLCALL_RANDOM}">随机点名</c:if>--%>
        </li>
    </ol>
    <div id="app">
        <template>
            <div class="am-margin-xl am-text-center" v-if="!started">
                <button class="am-btn am-btn-primary am-btn-xl am-round am-margin-xl"
                        @click="started = true">开始点名</button>
            </div>
            <div v-if="started && index < stuList.length" class="am-text-center am-margin-vertical-xl">
                <div>
                    <p class="am-text-xxxl">
                        <%--<i class="am-icon-mars am-text-info" v-if="stuList[index].sex == 1"></i>--%>
                        <%--<i class="am-icon-venus am-text-primary" v-else></i>--%>
                        {{stuList[index].name}}
                    </p>
                    <p class="am-text-xl">{{stuList[index].num}}</p>
                </div>
                <div class="am-margin-vertical-xl ifr-roll-call-btn-group">
                    <button class="am-btn am-btn-success am-btn-xl am-round"
                            <%--@click="rollCall(${Const.ROLLCALL_PRESENT})"--%>
                            @click="rollCall(1)">
                        <i class="am-icon-check am-text-xxxl"></i>
                        <div>出席</div>
                    </button>
                    <button class="am-btn am-btn-warning am-btn-xl am-round"
                            <%--@click="rollCall(${Const.ROLLCALL_LEAVE})"--%>
                            @click="rollCall(2)">
                        <i class="am-icon-plus-circle am-text-xxxl"></i>
                        <div>请假</div>
                    </button>
                    <button class="am-btn am-bg-info am-text-white am-btn-xl am-round"
                            <%--@click="rollCall(${Const.ROLLCALL_LATE})"--%>
                            @click="rollCall(3)">
                        <i class="am-icon-circle-o am-text-xxxl"></i>
                        <div>迟到</div>
                    </button>
                    <button class="am-btn am-btn-danger am-btn-xl am-round"
                            <%--@click="rollCall(${Const.ROLLCALL_ABSENT})"--%>
                            @click="rollCall(4)">
                        <i class="am-icon-times am-text-xxxl"></i>
                        <div>缺席</div>
                    </button>
                </div>
            </div>
            <div class="am-margin-xl am-text-center" v-if="index >=1">
                <button class="am-btn am-btn-success am-btn-xl am-round am-margin-xl" @click="endRollCall">点名结束</button>
            </div>
            <table class="am-table am-table-hover am-table-striped am-table-centered" v-if="stuListCalled.length > 0">
                <caption><h1>已点名单</h1></caption>
                <thead>
                <tr class="am-active">
                    <th>学号</th>
                    <th>姓名</th>
                    <%--<th>性别</th>--%>
                    <th>出席情况</th>
                    <th>修改出席</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(stu, i) in stuListCalled">
                    <td class="am-text-middle">{{stu.student.num}}</td>
                    <td class="am-text-middle">{{stu.student.name}}</td>
                    <%--<td class="am-text-middle">
                        <template v-if="stu.student.sex == 1">男</template>
                        <template v-else>女</template>
                    </td>--%>
                    <td class="am-text-middle">
                        <span class="awardorstate-onlyshow presense-bg am-text-white" v-if="stu.state == 1">出席</span>
                        <span class="awardorstate-onlyshow leave-bg am-text-white" v-if="stu.state == 2">请假</span>
                        <span class="awardorstate-onlyshow late-bg am-text-white" v-if="stu.state == 3">迟到</span>
                        <span class="awardorstate-onlyshow absense-bg am-text-white" v-if="stu.state == 4">缺席</span>
                        <span class="am-icon-spin am-icon-spinner" v-if="stu.saved == 0"></span>
                        <span class="am-icon-warning am-text-warning" v-if="stu.saved == -1"> 保存失败</span>
                    </td>
                    <td>
                        <button class="am-btn am-btn-success am-icon-check"
                                :disabled="stu.saved != 1 || stu.state == 1"
                                @click="resetRollCallState(stu.student.id, i, 1)"> 出席</button>
                        <button class="am-btn am-btn-warning am-icon-plus-circle"
                                :disabled="stu.saved != 1 || stu.state == 2"
                                @click="resetRollCallState(stu.student.id, i, 2)"> 请假</button>
                        <button class="am-btn am-bg-info am-text-white am-icon-circle-o"
                                :disabled="stu.saved != 1 || stu.state == 3"
                                @click="resetRollCallState(stu.student.id, i, 3)"> 迟到</button>
                        <button class="am-btn am-btn-danger am-icon-times"
                                :disabled="stu.saved != 1 || stu.state == 4"
                                @click="resetRollCallState(stu.student.id, i, 4)"> 缺席</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </template>
    </div>
</div>
</body>
<script src="${rctx}/utils/Modal/amazeui.dialog.js"></script>
<script src="${rctx}/js-edit/manage/teacher/roll-call.js${nocache}"></script>
<%@include file="../listenF5.jsp"%>
<script>
    new Vue({
        el: "#app",
        data: {
            started: false,     // 是否开始点名
            index: 0,           // 当前点名
            stuList: [          // 学生名单
                <c:forEach items="${studentList}" var="stu">
                {id: ${stu.id}, num: '${stu.studentNum}', name: '${stu.name}', sex: ${stu.sex}},
                </c:forEach>
            ],
            stuListCalled: []
        },
        methods: {
            // 开始点名
            rollCall: function (state) {
                this.started = true;
                this.stuListCalled.push({
                    student: this.stuList[this.index],  // 学生信息
                    id: -1,               // 点名记录的id
                    state: state,       // 点名结果
                    saved: 0            // 是否已经保存
                });
                this.setRollCallState(this.stuList[this.index].id, this.index, state);
                this.index++;
            },
            // 保存点名状态
            setRollCallState: function (id, index, state) {
                var $this = this;
                $.post('setRollCallState', {
                    id: id,
                    state: state,
                    lessonId:${lesson.id}
                }, function (ret) {
                    if (ret != false) {
                        $this.stuListCalled[index].saved = 1;
                        $this.stuListCalled[index].id = ret;
                    } else {
                        errorTip();
                        $this.stuListCalled[index].saved = -1;
                    }
                }, 'JSON');
            },
            // 修改出席状态
            resetRollCallState: function (id, index, state) {
                var $this = this;
                $.post('resetRollCallState', {
                    id: id,
                    state: state,
                    lessonId:${lesson.id}
                }, function (ret) {
                    if (ret) {
                        /*successTip();*/
                        $this.stuListCalled[index].saved = 1;
                        $this.stuListCalled[index].state = state;
                    } else {
                        errorTip();
                        $this.stuListCalled[index].saved = -1;
                    }
                }, 'JSON');
            },
            endRollCall:function () {
                window.location='${ctx}/manage/teacher/viewLesson?id=${lesson.id}';
            }
        }
    });
</script>
</html>

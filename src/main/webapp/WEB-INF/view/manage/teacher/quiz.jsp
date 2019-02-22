<%@ page language="java" pageEncoding="utf-8" import = "com.project.utils.Const" %>
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
        <li class="am-active">课堂提问</li>
    </ol>
    <div id="app">
        <template>
            <div class="am-text-center am-margin-vertical-xl">
                <div v-if="!started">
                    <p class="am-text-xxxl">
                        XXX
                    </p>
                    <p class="am-text-xl">XXXXX</p>
                </div>
                <div v-if="started">
                    <p class="am-text-xxxl">
                        <%--<i class="am-icon-mars am-text-info" v-if="stuList[index].sex == 1"></i>--%>
                        <%--<i class="am-icon-venus am-text-primary" v-else></i>--%>
                        {{stuList[index].name}}
                    </p>
                    <p class="am-text-xl">{{stuList[index].num}}</p>
                </div>
                <div class="am-margin-vertical-xl" v-show="started && !rolling">
                    <form id="form-award">
                        <input type="hidden" name="id" :value="stuList[index].id">
                        <input type="hidden" name="lessonId" :value="lessonId">
                        <input id="firstAward" type="hidden" name="score" v-model="score">
                        <a class="am-padding-lg am-text-xxxl am-text-warning">
                            <i :class="{'am-icon-star': score >= s, 'am-icon-star-o': score < s}"
                               v-for="s in 5" @click="score = s"></i>
                        </a>
                    </form>
                </div>
                <div class="am-margin-vertical-xl" v-show="!started || rolling">
                    <a class="am-padding-lg am-text-xxxl am-text-warning">
                        <i class="am-icon-star-o" v-for="s in 5"></i>
                    </a>
                </div>
                <div class="am-margin-vertical-xl ifr-roll-call-btn-group">
                    <button class="am-btn am-btn-success am-btn-xl am-round"
                            v-if="!rolling" @click="startRolling">
                        <i class="am-icon-play am-text-xxxl"></i>
                        <div>开始</div>
                    </button>
                    <button class="am-btn am-btn-danger am-btn-xl am-round"
                            v-if="rolling" @click="stopRolling">
                        <i class="am-icon-pause am-text-xxxl"></i>
                        <div>暂停</div>
                    </button>
                    <button class="am-btn am-btn-xl am-round ifr-bg-yellow"
                            :disabled="!started || rolling" @click="recordAward">
                        <i class="am-icon-star am-text-xxxl"></i>
                        <div>打分</div>
                    </button>
                    <button class="am-btn am-btn-warning am-btn-xl am-round" :disabled="!started || rolling"
                            @click="rollCall(2)"
                            <%--@click="setRollCallState(stuList[index].id,${lesson.id},2,0)"--%>>
                            <%--@click="setRollCallState(stuList[index].id, ${Const.ROLLCALL_LEAVE})"--%>
                        <i class="am-icon-plus-circle am-text-xxxl"></i>
                        <div>请假</div>
                    </button>
                    <button class="am-btn am-bg-info am-text-white am-btn-xl am-round" :disabled="!started || rolling"
                            @click="rollCall(3)"
                            <%--@click="setRollCallState(stuList[index].id,${lesson.id},3,0)"--%>>
                            <%--@click="setRollCallState(stuList[index].id, ${Const.ROLLCALL_LATE})"--%>
                        <i class="am-icon-circle-o am-text-xxxl"></i>
                        <div>迟到</div>
                    </button>
                    <button class="am-btn am-btn-danger am-btn-xl am-round" :disabled="!started || rolling"
                            @click="rollCall(4)"
                            <%--@click="setRollCallState(stuList[index].id,${lesson.id},4,0)"--%>>
                            <%--@click="setRollCallState(stuList[index].id, ${Const.ROLLCALL_ABSENT})"--%>
                        <i class="am-icon-times am-text-xxxl"></i>
                        <div>缺席</div>
                    </button>
                </div>
            </div>
            <table class="am-table am-table-hover am-table-striped am-table-centered table-margin-bottom" v-if="stuListAward.length > 0">
                <caption><h1>已点名单</h1></caption>
                <thead>
                <tr class="am-active">
                    <th>学号</th>
                    <th>姓名</th>
                    <%--<th>性别</th>--%>
                    <th>出席情况</th>
                    <th>加分情况</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(stu, i) in stuListAward">
                    <td class="am-text-middle">{{stu.student.num}}</td>
                    <td class="am-text-middle">{{stu.student.name}}</td>
                    <%--<td class="am-text-middle">
                        <template v-if="stu.student.sex == 1">男</template>
                        <template v-else>女</template>
                    </td>--%>
                    <td class="am-text-middle">
                        <div class="am-btn-group" style="height: 36px;">
                            <div id="rollIndex" style="float: left">
                                <span class="awardorstate-show presense-bg am-text-white" style="height: 100%" v-if="stu.state == 1">出席</span>
                                <span class="awardorstate-show leave-bg am-text-white" style="height: 100%" v-if="stu.state == 2">请假</span>
                                <span class="awardorstate-show late-bg am-text-white" style="height: 100%" v-if="stu.state == 3">迟到</span>
                                <span class="awardorstate-show absense-bg am-text-white" style="height: 100%" v-if="stu.state == 4">缺席</span>
                            </div>
                            <div class="am-dropdown" style="height: 100%" data-am-dropdown>
                                <div id="rollIndexDropdown" class="am-dropdown-toggle"  data-am-dropdown-toggle>
                                    <button class="am-btn am-btn-success" style="height: 100%;" v-if="stu.state == 1">
                                        <span class="am-icon-caret-down"></span>
                                    </button>
                                    <button class="am-btn am-btn-warning leave-bg" style="height: 100%;" v-if="stu.state == 2">
                                        <span class="am-icon-caret-down"></span>
                                    </button>
                                    <button class="am-btn late-bg late-caret-down" style="height: 100%;" v-if="stu.state == 3">
                                        <span class="am-icon-caret-down"></span>
                                    </button>
                                    <button class="am-btn am-btn-danger" style="height: 100%;" v-if="stu.state == 4">
                                        <span class="am-icon-caret-down"></span>
                                    </button>
                                </div>
                                <ul class="am-dropdown-content">
                                    <li class="am-dropdown-header">修改状态</li>
                                    <li class="am-divider"></li>
                                    <li><a @click="resetRollCallState(stu.student.id, i, 1)">出席</a></li>
                                    <li><a @click="resetRollCallState(stu.student.id, i, 2)">请假</a></li>
                                    <li><a @click="resetRollCallState(stu.student.id, i, 3)">迟到</a></li>
                                    <li><a @click="resetRollCallState(stu.student.id, i, 4)">缺席</a></li>
                                </ul>
                            </div>
                        </div>
                        <span class="am-icon-spin am-icon-spinner" v-if="stu.saved == 0"></span>
                        <span class="am-icon-warning am-text-warning" v-if="stu.saved == -1"> 保存失败</span>
                    </td>
                    <td>
                        <div class="am-btn-group" style="height: 36px;">
                            <span id="quizIndex" class="awardorstate-show award-bg am-text-white">+{{stu.award}}</span>
                            <%--<div class="am-hidden">{{a}}</div>--%>
                            <div class="am-dropdown" data-am-dropdown>
                                <button class="am-btn award-caret-down am-text-white am-dropdown-toggle" data-am-dropdown-toggle> <span class="am-icon-caret-down"></span></button>
                                <ul class="am-dropdown-content">
                                    <li class="am-dropdown-header">修改加分</li>
                                    <li class="am-divider"></li>
                                    <input :id="i" type="number" class="am-form-field" value="stu.award">
                                    <li><a @click="resetAward(stu.student.id,i)">确定</a></li>
                                </ul>
                            </div>
                        </div>
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
            started: false, // 是否开始点名
            index: 0,           // 当前点名
            showindex:0,        // 底部显示的数量
            rolling: false,     // 是否滚动名单
            timer: 0,           // 计时器
            score: 0,
            state:1,
            lessonId:${lesson.id},
            stuList: [          // 学生名单
                <c:forEach items="${studentList}" var="stu">
                {id: ${stu.id}, num: '${stu.studentNum}', name: '${stu.name}', sex: ${stu.sex}},
                </c:forEach>
            ],
            stuListAward: [],
            a: 0
        },
        methods: {
            // 开始滚动
            startRolling: function () {
                this.started = true;
                this.rolling = true;
                this.score = 0;
                var $this = this;
                this.timer = setInterval(function () {
                    $this.index++;
                    if ($this.index >= $this.stuList.length) {
                        $this.index = 0;
                    }
                }, 50);
            },
            // 停止滚动
            stopRolling: function () {
                var $this = this;
                $("#form-award").rating({
                    score: $this.score
                })
                window.clearInterval($this.timer);
                this.timer = setInterval(function () {
                    $this.index++;
                    if ($this.index >= $this.stuList.length) {
                        $this.index = 0;
                    }
                }, 100);
                setTimeout(function () {
                    window.clearInterval($this.timer);
                    $this.rolling = false;
                }, 500);
            },
            //出勤状态
            rollCall: function (state) {
                this.started = true;
                this.stuListAward.push({
                    student: this.stuList[this.index],  // 学生信息
                    id: -1,               // 点名记录的id
                    state: state,       // 点名结果
                    award: 0,           //加分情况
                    saved: 0            // 是否已经保存
                });
                this.setRollCallState(this.stuList[this.index].id, this.showindex, state, false);
                this.showindex++;
            },
            // 保存点名状态
            setRollCallState: function (id,index,state) {
                var $this = this;
                $.post('setRollCallState', {
                    id: id,
                    lessonId:this.lessonId,
                    state: state
                }, function (ret) {
                    if (ret) {
                        $("#"+index).val('0');
                        $(".am-dropdown").dropdown();
                        $this.started = false;
                        $this.stuListAward[index].saved = 1;
                        $this.stuListAward[index].id = ret;
                        //successTip();
                    } else {
                        errorTip();
                        $this.stuListAward[index].saved = -1;
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
                        $(".am-dropdown").dropdown('close');
                        $this.stuListAward[index].saved = 1;
                        $this.stuListAward[index].state = state;
                    } else {
                        errorTip();
                        $this.stuListAward[index].saved = -1;
                    }
                }, 'JSON');
            },
            // 记录得分
            recordAward: function (id) {
                event.preventDefault();
                var $this = this;
                var award=$("#firstAward").val();
                var showindex= this.showindex;
                $.post('recordAward', $("#form-award").serialize(), function (ret) {
                        $(".am-dropdown").dropdown();
                        $("#"+showindex).val(award);
                        $this.state=parseInt(ret[0]);
                        $this.id=parseInt(ret[1]);
                        $this.score=parseInt(ret[2]);
                        //successAwardTip();
                        $this.started = false;
                        $this.showindex++;
                }, 'JSON');
                this.stuListAward.push({
                    student: this.stuList[this.index],  // 学生信息
                    id: this.id,               // 点名记录的id
                    state: this.state,      // 点名结果
                    award: this.score,   //加分情况
                    save: 1
                });
            },
            // 修改加分
            resetAward: function (id,index) {
                event.preventDefault();
                var $this = this;
                var award =$("#"+index).val();
                var awardone=$("#firstAward").val();
                var type="^[0-9]*[1-9][0-9]*$";
                var r=new RegExp(type);
                var flag=r.test(award);
                if(!flag){
                    alert('请输入正整数')
                }
                else{
                    $.post('modifyAward', {
                        id: id,
                        lessonId: ${lesson.id},
                        award: award,
                        awardone: $this.stuListAward[index].award
                    }, function (ret) {
                        if (ret) {
                            $(".am-dropdown").dropdown('close');
//                            $("#"+index).val(parseInt(ret[2]));
                            $this.score = parseInt(ret[2]);
                            $this.stuListAward[index].award = $this.score;
//                            $this.a = Math.random();
                        } else {
                            errorTip();
                        }
                    }, 'JSON');
                }
            }
        },
        watch: {
            score: {
                handler: function (val) {
                    $("#form-award").rating({
                        score: val
                    })
                }
            }
        }
    });
</script>
<script src="${rctx}/utils/Rating/amazeui.rating.js"></script>
</html>

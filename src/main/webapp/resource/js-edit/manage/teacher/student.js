new Vue({
    el: "#c-app",
    data: {
        newPassword: "",
        again: "",
        shrinked: (getCookie("shrinked") == "true"),
        stuList: [], // 批量增加学生
        stuListText: '',
        stuListInputStatus: '',
        stuListCorrect: false
    },
    methods: {
        // 保存学生
        saveStudent: function () {
            event.preventDefault();
            var loading = AMUI.dialog.loading({
                title: '正在上传'
            });
            var formData = new FormData($("#ajax-form")[0]);
            $.ajax({
                type: "POST",
                data: formData,
                url: "saveStudent",
                contentType: false,
                processData: false,
                error: function() {
                    loading.modal('close');
                    errorTip();
                },
                success: function (ret) {
                    loading.modal('close');
                    if (ret != "false") {
                        AMUI.dialog.alert({
                            title: '操作成功',
                            content: "已成功保存学生",
                            onConfirm: function() {
                                location.href = "viewLesson?id=" + $("[name=vid]").val();
                            }
                        });
                    } else {
                        errorTip();
                    }
                }
            });
        },
        // 删除学生
        deleteStudent: function(vid, cid) {
            AMUI.dialog.confirm({
                title: '确认删除该学生？',
                content: '删除后该学生以及点名记录将被删除，请谨慎操作',
                onConfirm: function() {
                    $.post("deleteStudent", {vid: vid, cid: cid}, function(ret){
                        if (ret) {
                            AMUI.dialog.alert({
                                title: '删除成功',
                                content: "该学生以及点名记录也被删除",
                                onConfirm: function() {
                                    location.href = "viewLesson?id=" + vid;
                                }
                            });
                        } else {
                            errorTip();
                        }
                    }, "json");
                }
            });
        },
        // 收起投票详情页面上方的内容
        shrink: function() {
            if (getCookie("shrinked") == null || getCookie("shrinked") == "false") {
                addCookie("shrinked", "true");
            } else {
                addCookie("shrinked", "false");
            }
            $("div.ifr-js-shrink").slideToggle(200);
            $("img.ifr-js-shrink").toggleClass("ifr-img-head-sm");
            $(".ifr-div-shrink button").eq(0).toggle();
            $(".ifr-div-shrink button").eq(1).toggle();
        },
        // 批量增加学生，文本转换
        translate: function () {
            var students = this.stuListText.trim().replace(new RegExp('\t','gm'),' ').split('\n');
            this.stuList = [];
            for (var i = 0; i < students.length; i++) {
                var info = students[i].split(/ +/);
                if (info.length == 3 && (info[2] == '男' || info[2] == '女')) {
                    this.stuList.push({
                        num: info[0],
                        name: info[1],
                        sex: info[2] == '男' ? 1 : 2
                    })
                } else if (info.length != 1 || info[0] != '') {
                    this.stuList = [];
                    AMUI.dialog.alert({
                        title: '操作失败',
                        content: "格式错误"
                    });
                    return;
                }
            }
            if (this.stuList.length > 0) {
                this.stuListCorrect = true;
                this.stuListInputStatus = '输入' + students.length + '行，转换成功' + this.stuList.length + '行';
            }
        },
        // 批量保存学生
        saveStudents: function () {
            event.preventDefault();
            var loading = AMUI.dialog.loading({
                title: '正在上传'
            });
            var formData = new FormData($("#ajax-form")[0]);
            $.ajax({
                type: "POST",
                data: formData,
                url: "saveStudents",
                contentType: false,
                processData: false,
                error: function() {
                    loading.modal('close');
                    errorTip();
                },
                success: function (ret) {
                    loading.modal('close');
                    if (ret != "false") {
                        AMUI.dialog.alert({
                            title: '操作成功',
                            content: "已成功保存学生",
                            onConfirm: function() {
                                location.href = "viewLesson?id=" + $("[name=vid]").val();
                            }
                        });
                    } else {
                        errorTip();
                    }
                }
            });
        },
        //修改点名状态
        changeRollState: function (state,rollCallId,rollIndex) {
            $.post('modifyAttendance', {
                state: state,
                rollCallId:rollCallId
            }, function (ret) {
                $("#changeRollStateDropdown"+rollIndex).dropdown('close');
                if(state==1){
                    $("#rollIndex"+rollIndex).html('<span class="awardorstate-show presense-bg am-text-white" style="height: 100%">出席</span>');
                    $("#rollIndexDropdown"+rollIndex).html('<button class="am-btn am-btn-success" style="height: 100%;"><span class="am-icon-caret-down"></span></button>');
                }
                if(state==2){
                    $("#rollIndex"+rollIndex).html('<span class="awardorstate-show leave-bg am-text-white" style="height: 100%">请假</span>');
                    $("#rollIndexDropdown"+rollIndex).html('<button class="am-btn am-btn-warning leave-bg" style="height: 100%;"><span class="am-icon-caret-down"></span></button>');
                }
                if(state==3){
                    $("#rollIndex"+rollIndex).html('<span class="awardorstate-show late-bg am-text-white" style="height: 100%">迟到</span>');
                    $("#rollIndexDropdown"+rollIndex).html('<button class="am-btn late-bg late-caret-down" style="height: 100%;"><span class="am-icon-caret-down"></span></button>');
                }
                if(state==4){
                    $("#rollIndex"+rollIndex).html('<span  class="awardorstate-show absense-bg am-text-white" style="height: 100%">缺席</span>');
                    $("#rollIndexDropdown"+rollIndex).html('<button class="am-btn am-btn-danger" style="height: 100%;"><span class="am-icon-caret-down"></span></button>');
                }
                    $("#"+rollIndex+"rollCallIndex1").html(ret[0]);
                    $("#"+rollIndex+"rollCallIndex2").html(ret[1]);
                    $("#"+rollIndex+"rollCallIndex3").html(ret[2]);
                    $("#"+rollIndex+"rollCallIndex4").html(ret[3]);
                }, 'JSON');
            },

        //修改问答得分
        changeQuizAward: function (quizId,quizIndex) {
            var award =$("#AwardInput"+quizIndex).val();
            var type="^[0-9]*[1-9][0-9]*$";
            var r=new RegExp(type);
            var flag=r.test(award);
            if(!flag){
                alert('请输入正整数')
            }
            else{
                $.post('modifyQuiz',
                    {quizId: quizId,
                     award: award
                    }, function (ret) {
                        $("#changeQuizAwardDropdown" + quizIndex).dropdown('close');
                        $("#quizIndex" + quizIndex).html('+'+award);
                        $("#quizAwardIndex"+quizIndex).html(ret);
                    }, 'JSON');
            }
            },

}
});

function errorTip() {
    AMUI.dialog.alert({
        title: '操作失败',
        content: "请重试"
    });
}
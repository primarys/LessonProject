new Vue({
    el: "#app",
    data: {
        head: 0,
        headUrl: $("#headUrl").val(),
        newPassword: "",
        again: ""
    },
    methods: {
        /**
         * Teacher管理
         */
        // 创建teacher
        createTeacher: function() {
            AMUI.dialog.confirm({
                title: '确认一键创建教师？',
                content: '系统将自动分配教师账号和密码',
                onConfirm: function() {
                    $.post("createTeacher", function(ret){
                        if (ret.Model) {
                            AMUI.dialog.alert({
                                title: '创建成功',
                                content: "用户名：" + ret.Model.userName + "，初始密码：" + ret.Model.password +"。",
                                onConfirm: function() {
                                    location.reload();
                                }
                            });
                        } else {
                            errorTip();
                        }
                    }, "json");
                }
            });
        },
        // 删除teacher
        deleteTeacher: function(id) {
            AMUI.dialog.confirm({
                title: '确认删除该教师？',
                content: '删除后该教师开设的课程和课程的学生将不可见，请谨慎操作',
                onConfirm: function() {
                    $.post("deleteTeacher", {id: id}, function(ret){
                        if (ret) {
                            AMUI.dialog.alert({
                                title: '删除成功',
                                content: "该教师开设的课程和课程的学生不可见",
                                onConfirm: function() {
                                    location.href = "teacherList";
                                }
                            });
                        } else {
                            errorTip();
                        }
                    }, "json");
                }
            });
        },
        // 锁定teacher
        lockTeacher:  function(id, toLock) {
            AMUI.dialog.confirm({
                title: toLock ? "确认锁定该教师？" : "确认解锁该教师？",
                content: toLock ? '锁定后该教师将不可登录，开设的课程和课程的学生将不可见，请谨慎操作'
                    : "解锁后该教师恢复登录，开设的课程和课程的学生恢复可见，请谨慎操作",
                onConfirm: function() {
                    $.post("lockTeacher", {id: id}, function(ret){
                        if (ret) {
                            AMUI.dialog.alert({
                                title: '操作成功',
                                content: toLock ? "该教师将不可登录，开设的课程和课程的学生不可见"
                                    : "该教师恢复登录，开设的课程和课程的学生恢复可见",
                                onConfirm: function() {
                                    location.reload();
                                }
                            });
                        } else {
                            errorTip();
                        }
                    }, "json");
                }
            });
        },
        // 重置teacher密码
        resetTeacherPassword: function(id) {
            AMUI.dialog.confirm({
                title: '确认重置密码？',
                content: '重置后将自动分配随机密码，且该教师将下线',
                onConfirm: function() {
                    $.post("resetTeacherPassword", {id: id}, function(ret){
                        if (ret != "false") {
                            AMUI.dialog.alert({
                                title: '操作成功',
                                content: "密码已重置为" + ret,
                                onConfirm: function() {
                                    location.reload();
                                }
                            });
                        } else {
                            errorTip();
                        }
                    });
                }
            });
        },
        // 重置teacher头像
        resetHead: function() {
            this.head = 1;
            this.headUrl = $("#defaultUrl").val();
        },
        // 修改teacher信息
        updateTeacher: function() {
            event.preventDefault();
            $.post("updateTeacher", $("#form-updateTeacher").serialize(), function(ret){
                if (ret != "false") {
                    AMUI.dialog.alert({
                        title: '操作成功',
                        content: "已成功修改信息",
                        onConfirm: function() {
                            location.reload();
                        }
                    });
                } else {
                    errorTip();
                }
            });
        },
        // 收起详情页面上方的内容
        shrink: function() {
            $("div.ifr-js-shrink").slideToggle(200);
            $("img.ifr-js-shrink").toggleClass("ifr-img-head-sm");
            $(".ifr-div-shrink button").eq(0).toggle();
            $(".ifr-div-shrink button").eq(1).toggle();
        },
        /**
         * Admin管理
         */
        // 修改admin信息
        updateAdmin: function() {
            event.preventDefault();
            $.post("updateAdmin", $("#form-updateAdmin").serialize(), function(ret){
                if (ret != "false") {
                    AMUI.dialog.alert({
                        title: '操作成功',
                        content: "已成功修改信息",
                        onConfirm: function() {
                            location.reload();
                        }
                    });
                } else {
                    errorTip();
                }
            });
        },
        // 修改admin密码
        updatePassword: function() {
            event.preventDefault();
            if (this.newPassword==this.again) {
                $.post("updatePassword", $("#form-updateAdmin").serialize(), function (ret) {
                    if (ret != "false") {
                        AMUI.dialog.alert({
                            title: '操作成功',
                            content: "已成功修改密码",
                            onConfirm: function () {
                                location.reload();
                            }
                        });
                    } else {
                        errorTip();
                    }
                });
            } else{
                AMUI.dialog.alert({
                    title: '操作失败',
                    content: "两次密码不一致"
                });
            }
        }
    }
});

function errorTip() {
    AMUI.dialog.alert({
        title: '操作失败',
        content: "请重试"
    });
}
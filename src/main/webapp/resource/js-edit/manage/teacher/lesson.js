new Vue({
    el: "#app",
    data: {
        cover: 0,        // 0：封面不变，1：恢复默认，2：更换封面
        coverUrl: $("#coverUrl").val(),
        shrinked: (getCookie("shrinked") == "true")
    },
    methods: {
        // 删除课程活动
        deleteLesson: function (id) {
            $('#deleteLesson-step1').modal({
                relatedTarget: this,
                onConfirm: function(options) {
                    $.post('deleteLesson', {
                        id: id
                    }, function (ret) {
                        if(ret) {
                            AMUI.dialog.alert({
                                title: "删除成功", content: "课程以及学生列表也被删除", onConfirm: function () {
                                    location.href = "lessonList";
                                }
                            })
                        }else {
                            AMUI.dialog.alert({title: "错误", content: "删除失败"});
                        }
                    }, 'JSON');
                },
            });
        },
        // 重置封面
        resetCover: function() {
            this.cover = 1;
            this.coverUrl = $("#defaultUrl").val();
        },
        // 更换封面预览
        preview: function() {
            this.cover = 2;
            var reader = new FileReader();
            var $this = this;
            reader.readAsDataURL($("#file")[0].files[0]);
            reader.onload = function(e){
                $this.coverUrl = e.target.result;
            }
        },
        // 保存课程活动
        saveLesson: function () {
            event.preventDefault();
            var loading = AMUI.dialog.loading({
                title: '正在上传'
            });
            var formData = new FormData($("#ajax-form")[0]);
            $.ajax({
                type: "POST",
                data: formData,
                url: "saveLesson",
                contentType: false,
                processData: false,
                error: function() {
                    loading.modal('close');
                    errorTip();
                },
                success: function (retId) {
                    loading.modal('close');
                    if (retId != "false") {
                        AMUI.dialog.alert({
                            title: '操作成功',
                            content: "已成功保存课程信息",
                            onConfirm: function() {
                                location.href = "viewLesson?id=" + retId;
                            }
                        });
                    } else {
                        errorTip();
                    }
                }
            });
        },
        // 开始上课
        beginClass: function (lessonId,lessonName,lessonIndex) {
            $('#classBegin-confirm'+lessonIndex).modal({
                relatedTarget: this,
                onConfirm: function(options) {
                    var loading = AMUI.dialog.loading({
                        title: lessonName+'开始上课'
                    });
                    $.post('classBegin', {
                        lessonId: lessonId
                    }, function (ret) {
                        if(ret) {
                            location.reload();
                            loading.modal('close');
                        }else {
                            loading.modal('close');
                            AMUI.dialog.alert({title: "错误", content: "修改错误"});
                        }
                    }, 'JSON');
                },
            });
        },
        // 收起课程详情页面上方的内容
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
        }
    }
});

function errorTip() {
    AMUI.dialog.alert({
        title: '操作失败',
        content: "请重试"
    });
}
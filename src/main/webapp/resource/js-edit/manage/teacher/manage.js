new Vue({
    el: "#app",
    data: {
        head: 0,        // 0：头像不变，1：恢复默认，2：更换头像
        headUrl: $("#headUrl").val(),
        newPassword: "",
        again: ""
    },
    methods: {
        // 修改自己信息
        updateTeacher: function() {
            event.preventDefault();
            var loading = AMUI.dialog.loading({
                title: '正在上传'
            });
            var formData = new FormData($("#form-updateTeacher")[0]);
            $.ajax({
                type: "POST",
                dataType: "JSON",
                data: formData,
                url: "updateTeacher",
                contentType: false,
                processData: false,
                error: function() {
                    loading.modal('close');
                    errorTip();
                },
                success: function (ret) {
                    loading.modal('close');
                    if (ret) {
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
                }
            });
        },
        // 修改自己的密码
        updatePassword: function() {
            event.preventDefault();
            if (this.newPassword == this.again) {
                $.post("updatePassword", $("#form-updateTeacher").serialize(), function (ret) {
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
            } else {
                AMUI.dialog.alert({
                    title: '操作失败',
                    content: "两次密码不一致"
                });
            }
        },
        // 重置头像
        resetHead: function() {
            this.head = 1;
            this.headUrl = $("#defaultUrl").val();
        },
        // 更换头像预览
        preview: function() {
            this.head = 2;
            var reader = new FileReader();
            var $this = this;
            reader.readAsDataURL($("#file")[0].files[0]);
            reader.onload = function(e){
                $this.headUrl = e.target.result;
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
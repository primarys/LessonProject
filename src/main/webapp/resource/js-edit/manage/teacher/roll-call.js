function successTip() {
    AMUI.dialog.alert({
        title: '操作成功',
        content: "已成功修改出席状态"
    });
}

function successAwardTip() {
    AMUI.dialog.alert({
        title: '操作成功',
        content: "已记录分数"
    });
}

function errorTip() {
    AMUI.dialog.alert({
        title: '操作失败',
        content: "请重试"
    });
}
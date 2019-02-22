var app = new Vue({
    el: "#wechat-login",
    data: {
        readProtocol: true
    },
    methods: {
        teacherWechatRegister: function() {
            // var jump = CONST.$ctx + "/stuSelf";
            // var url = window.location.search;  //获取地址栏中的地址
            // url = url.toString();  //转换成字符串
            // var array = new Array();  //存放分割后的字符串
            // array = url.split("=");  //根据“=”符号将查询字符串分割开
            location.href = CONST.$ctx + "/weixinLoginOrRegister";
        }
    }
});

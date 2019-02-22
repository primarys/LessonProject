<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@include file="/common/framework.jsp" %>
    <%@include file="import.jsp"%>
</head>
<body>
<div id="app">
    <h1>默认主题颜色</h1>
    <h4>默认：初始值1，最大值10000，最小值1，步长1，中等大小</h4>
    <input-number></input-number>
    <hr>
    <h4>自定义，初始值5，最大值20，最小值1，步长5，大号</h4>
    <input-number :max="20" :min="1" :val="5" :step="5" size="large"></input-number>
    <hr>
    <h4>自定义，初始值5，最大值10，最小值1，步长2，小号</h4>
    <input-number :max="10" :min="1" :val="5" :step="2" size="small"></input-number>
    <hr>
    <h1>圆形主题</h1>
    <h4>圆形按钮：一般是居右放置，如果值为0则隐藏减号和数字（但会占位）</h4>
    <div class="am-text-right">
        <input-number theme="round" :min="0" :val="0"></input-number>
    </div>
    <hr>
    <h4>圆形按钮（小号）</h4>
    <div class="am-text-right">
        <input-number theme="round" size="small" :min="0" :val="0"></input-number>
    </div>
    <hr>
    <h4>圆形按钮（大号）</h4>
    <div class="am-text-right">
        <input-number theme="round" size="large" :min="0" :val="0"></input-number>
    </div>
    <hr>
    <h1>颜色（橙色、绿色）</h1>
    <h4>默认形状，橙色</h4>
    <input-number color="orange"></input-number>
    <hr>
    <h4>圆形主题，绿色</h4>
    <div class="am-text-right">
        <input-number color="green" theme="round" size="small" data-param="1,2,3"
                      @get-value="value" @get-data-param="getDataParam"></input-number>
        <br>父组件（父页面）获取子组件（input-number组件）的值: {{ childValue }}
        <br>父组件重新获取传入子组件的自定义参数和数值：{{param}}, {{num}}
    </div>
    <hr>
</div>
</body>
<script>
    new Vue({
        el: "#app",
        data: {
            childValue: 1,
            param: [],
            num: 0
        },
        methods: {
            value: function(val) {      // 方法名"value"不一定要这样起，但是要和子组件中写的 get-value="xxx" 中的 xxx 一致
                this.childValue = val;
            },
            getDataParam: function (param, num) {
                this.param = param;
                this.num = num;
            }
        }
    })
</script>
</html>
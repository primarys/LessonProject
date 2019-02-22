<%@ page language="java" import="com.project.utils.Const" pageEncoding="utf-8"%>
<html>
<head>
    <%@include file="/common/framework.jsp"%>
    <title>${title}</title>
    <link href="${rctx}/css/community/index.css" rel="stylesheet" type="text/css">
    <script src="${rctx }/js/community/index.js"></script>
</head>
<body>
<div class="main">
    <ol class="am-breadcrumb am-margin-bottom-sm">
        <li><a href="${ctx}/manage/dashboard" target="_parent">首页</a></li>
        <li class="am-active">问答社区</li>
    </ol>
    <div class="am-margin-vertical">
        <a class="am-btn am-btn-sm am-btn-primary am-text-white" id="doc-prompt-toggle"> 发布问题</a>
    </div>
    <ul class="am-nav am-nav-tabs">
        <li class="am-active" id="special0" onclick="change(this)"><a href="#">问题列表</a></li>
        <li id="special1" onclick="change(this)"><a href="#" >我的提问</a></li>
    </ul>
    <div id="companel1" class="companel">
        <ul class="am-list">
            <li><a href="#">
                <p>每个人都有一个死角， 自己走不出来，别人也闯不进去。</p></a>
                <span>6条评论</span></li>
            <li><a href="#">
                我把最深沉的秘密放在那里。</a></li>
            <li><a href="#">
                你不懂我，我不怪你。</a></li>
            <li><a href="#">
                每个人都有一道伤口， 或深或浅，盖上布，以为不存在。</a></li>
        </ul>
    </div>
    <div id="companel2" class="companel miss">
        <ul class="am-list">
            <li><a href="#">自己走不出来，别人也闯不进去。</a></li>
            <li><a href="#">我把最深沉的秘密放在那里。</a></li>
            <li><a href="#">你不懂我，我不怪你。</a></li>
            <li><a href="#">每个人都有一道伤口， 或深或浅，盖上布，以为不存在。</a></li>
        </ul>
    </div>
</div>


<%--发布问题的模态框--%>
<div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt">
    <div class="am-modal-dialog">
        <div class="am-modal-hd"><h1>问题发布</h1></div>
        <form >
            <div class="am-modal-bd">
                <input name="textfield" type="text" placeholder="请输入问题标题" required>
                <div class="am-form-group">
                    <div id="note" class="note am-text-middle">
                        <span>在这里输入你想发布的问题</span>
                    </div>
                    <%--点击文本框则预置文字消失--%>
                    <textarea required="required" rows="6" id="doc-ta-1"
                              onfocus="document.getElementById('note').style.display='none'"
                              onblur="if(value=='')document.getElementById('note').style.display='block'"></textarea>
                </div>
            </div>
            <div class="am-modal-footer">
                <button class="am-btn  question-btn"  onclick="closemodel()">取消</button>
                <button type="submit" class="am-btn  question-btn">提交</button>
            </div>
        </form>
    </div>
</div>
<script>
    // 全屏切换
    $('#ifr-js-fullpage').on('click', function () {
        if ($.AMUI.fullscreen.enabled) {
            $.AMUI.fullscreen.request();
        }
        $(this).hide();
        $('#ifr-js-fullpage-exit').show();
    });
    $('#ifr-js-fullpage-exit').on('click', function () {
        if ($.AMUI.fullscreen.enabled) {
            $.AMUI.fullscreen.exit();
        }
        $(this).hide();
        $('#ifr-js-fullpage').show();
    });

    //发布问题模态框
    $(function() {
        $('#doc-prompt-toggle').on('click', function() {
            $('#my-prompt').modal({
                relatedTarget: this
//                onConfirm: function() {
//                    var e=$("#doc-ta-1").val();
//                    if(e == null){
//                        alert('你输入的是：' + e);
//                    }
//                    else{
//                        alert('你输入的是：' + e);
//                    }
//                    location.reload();
//                }
//                onCancel: function(e) {
//                    alert('不想说!');
//                }
            });
        });
    });
    //关闭模态框
    function closemodel() {
        var $modal = $('#my-prompt');
        $modal.modal('close');
    }
</script>
</body>
</html>

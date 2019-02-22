<%@ page language="java" pageEncoding="utf-8"%>
<script src="${rctx}/utils/lazyload/jquery.lazyload.min.js"></script>
<script>
    $(function () {
        // 图片懒加载
        $(".lazy").each(function() {
            var src = $(this).attr("data-src");
            $(this).lazyload({
                placeholder: src
            });
        })
    });
</script>
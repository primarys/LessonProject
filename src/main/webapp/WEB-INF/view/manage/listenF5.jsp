<%@ page language="java" pageEncoding="UTF-8"%>
<script>
    $("body").bind("keydown",function(event){
        if (event.keyCode == 116) {
            event.preventDefault(); //阻止默认刷新
            location.reload();
        }
    })
</script>

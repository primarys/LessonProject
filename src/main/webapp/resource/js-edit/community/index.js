function change(e){
//	alert("do");
    var temp=$(e).attr("id");//取出id的值，根据id的值判断哪一部分要隐藏
//	alert(temp);
    if(temp=='special0'){
        $("#special0").attr("class","am-active");
        $("#special1").removeClass("am-active");
        $("#companel1").addClass("active");
        $("#companel2").removeClass("active");
        $("#companel2").addClass("miss");
    }else {
        $("#special1").attr("class","am-active");
        $("#special0").removeClass("am-active");
        $("#companel1").removeClass("active");
        $("#companel1").addClass("miss");
        $("#companel2").addClass("active");
    }
}
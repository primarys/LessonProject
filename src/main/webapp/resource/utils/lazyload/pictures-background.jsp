<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>多图延迟加载</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@include file="/common/common.jsp" %>
	<style type="text/css">
		div{
			width:800px;
			height:600px;
			margin:auto;
			text-align:center;
			background-image:url('grey.gif');
			background:no-repeat;
			background-size:contain;
		}

	</style>
</head>
<body>

<div class="lazy" data-original="http://ww2.sinaimg.cn/mw1024/6aa3df83gw1f61mwoqaw5j20x30jun7s.jpg"></div>
<div class="lazy" data-original="http://ww3.sinaimg.cn/mw1024/6aa3df83gw1f4jigdie94j20x30juwnk.jpg"></div>
<div class="lazy" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1f1juwevmx7j20rk0gj789.jpg"></div>
<div class="lazy" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1eyfez2ccl6j20rk0gjk0r.jpg"></div>
<div class="lazy" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1ewbbzpwmegj20tx0hyjwq.jpg"></div>
<div class="lazy" data-original="http://www.pp3.cn/uploads/201607/20160717005.jpg"></div>
<div class="lazy" data-original="http://pic.pp3.cn/uploads/pic1/0520/cwyuy1mqasb.jpg"></div>


	<script src="jquery.lazyload.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$(function() {
      	$("div.lazy").lazyload({
  			effect: "fadeIn", // 载入使用何种效果   		
		});
  	});
</script>

</body>
</html>
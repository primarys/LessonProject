<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>多图延迟加载</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@include file="/common/common.jsp" %>
	<style type="text/css">
		.contain{
			width:800px;
			height:600px;
			margin:50px auto;
			overflow:scroll;
			border:1px solid #ccc;
		}
		.contain img{
			height:90%;
			margin:20px;
		}
		.inner-box{
			width:6700px;
			height:100%			
		}
		.inner-box img{
			vertical-align: middle;
		}
	</style>
</head>
<body>

<div class="contain div1">
	<img class="lazy" data-original="http://ww2.sinaimg.cn/mw1024/6aa3df83gw1f61mwoqaw5j20x30jun7s.jpg">
	<img class="lazy" data-original="http://ww3.sinaimg.cn/mw1024/6aa3df83gw1f4jigdie94j20x30juwnk.jpg">
	<img class="lazy" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1f1juwevmx7j20rk0gj789.jpg">
	<img class="lazy" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1eyfez2ccl6j20rk0gjk0r.jpg">
	<img class="lazy" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1ewbbzpwmegj20tx0hyjwq.jpg">
	<img class="lazy" data-original="http://www.pp3.cn/uploads/201607/20160717005.jpg">
	<img class="lazy" data-original="http://pic.pp3.cn/uploads/pic1/0520/cwyuy1mqasb.jpg">
</div>

<div class="contain div2">
	<div class="inner-box">
		<img class="lazy2" data-original="http://ww2.sinaimg.cn/mw1024/6aa3df83gw1f61mwoqaw5j20x30jun7s.jpg">
		<img class="lazy2" data-original="http://ww3.sinaimg.cn/mw1024/6aa3df83gw1f4jigdie94j20x30juwnk.jpg">
		<img class="lazy2" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1f1juwevmx7j20rk0gj789.jpg">
		<img class="lazy2" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1eyfez2ccl6j20rk0gjk0r.jpg">
		<img class="lazy2" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1ewbbzpwmegj20tx0hyjwq.jpg">
		<img class="lazy2" data-original="http://www.pp3.cn/uploads/201607/20160717005.jpg">
		<img class="lazy2" data-original="http://pic.pp3.cn/uploads/pic1/0520/cwyuy1mqasb.jpg">
	</div>
</div>

	<script src="jquery.lazyload.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$(function() {
      	$("img.lazy").lazyload({
  			effect: "fadeIn", // 载入使用何种效果   	
  			container: $(".div1")	
		});
		$("img.lazy2").lazyload({
  			effect: "fadeIn", // 载入使用何种效果   	
  			container: $(".div2")	
		});
  	});
</script>

</body>
</html>
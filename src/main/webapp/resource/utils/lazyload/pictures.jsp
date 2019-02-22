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
		}
		img{
			/* height:100%; */
			width:100%
		}
	</style>
</head>
<body>

<div><img class="lazy" data-original="http://ww2.sinaimg.cn/mw1024/6aa3df83gw1f61mwoqaw5j20x30jun7s.jpg"></div>
<div><img class="lazy" data-original="http://ww3.sinaimg.cn/mw1024/6aa3df83gw1f4jigdie94j20x30juwnk.jpg"></div>
<div><img class="lazy" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1f1juwevmx7j20rk0gj789.jpg"></div>
<div><img class="lazy" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1eyfez2ccl6j20rk0gjk0r.jpg"></div>
<div><img class="lazy" data-original="http://ww4.sinaimg.cn/mw1024/6aa3df83gw1ewbbzpwmegj20tx0hyjwq.jpg"></div>
<div><img class="lazy" data-original="http://www.pp3.cn/uploads/201607/20160717005.jpg"></div>
<div><img class="lazy" data-original="http://pic.pp3.cn/uploads/pic1/0520/cwyuy1mqasb.jpg"></div>


	<script src="jquery.lazyload.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$(function() {
      	$("img.lazy").lazyload({
  			placeholder : "grey.gif", //用图片提前占位
    		// placeholder,值为某一图片路径.此图片用来占据将要加载的图片的位置,待图片加载时,占位图则会隐藏
  			effect: "fadeIn", // 载入使用何种效果
    		// effect(特效),值有show(直接显示),fadeIn(淡入),slideDown(下拉)等,常用fadeIn
  			//threshold: 200, // 提前开始加载
		    // threshold,值为数字,代表页面高度.如设置为200,表示滚动条在离目标位置还有200的高度时就开始加载图片,可以做到不让用户察觉
		  	//event: 'click',  // 事件触发时才加载
		    // event,值有click(点击),mouseover(鼠标划过),sporty(运动的),foobar(…).可以实现鼠标莫过或点击图片才开始加载,后两个值未测试…
		  	//container: $("#container"),  // 对某容器中的图片实现效果
		    // container,值为某容器.lazyload默认在拉动浏览器滚动条时生效,这个参数可以让你在拉动某DIV的滚动条时依次加载其中的图片
		  	//failurelimit : 10 // 图片排序混乱时
		    // failurelimit,值为数字.lazyload默认在找到第一张不在可见区域里的图片时则不再继续加载,但当HTML容器混乱的时候可能出现可见区域内图片并没加载出来的情况,failurelimit意在加载N张可见区域外的图片,以避免出现这个问题.
		});
  	});
</script>

</body>
</html>
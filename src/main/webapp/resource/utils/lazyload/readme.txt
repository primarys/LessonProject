1.lazyLoad插件简介及作用
	网站性能优化插件，提高用户体验。
	页面如果有很多图片的时候，只加载可视区域内的或即将进入可视区域的图片，而其它隐藏的图片则先不加载。
2.使用方法
（1）导入jquery库和lazyload插件（jquery库已包含在common.jsp中）
    <script src="${rctx }/utils/lazyload/jquery.lazyload.js" type="text/javascript" charset="utf-8"></script>
（2）为图片加入样式lazy（也可设置其他类名），图片真实路径引用方法用data-original
    <img class="lazy" data-original="1.jpg">
    **注意不要设置src属性，即使是src=""浏览器也会试图加载图片**
（3）在页面中初始化lazyload并设置图片显示方式
    <script type="text/javascript" charset="utf-8">
        $(function() {
            $("img.lazy").lazyload({
                effect: "fadeIn"
            });
        });
    </script>
（4）常用参数设置
    $("img.lazy").lazyload({
        placeholder : "grey.gif", //用默认图片提前占位
        // placeholder,值为某一图片路径.此图片用来占据将要加载的图片的位置,待图片加载时,占位图则会隐藏
        effect: "fadeIn", // 载入使用何种效果
        // effect(特效),值有show(直接显示),fadeIn(淡入),slideDown(下拉)等,常用fadeIn
        threshold: 200, // 提前开始加载
        // threshold,值为数字,代表页面高度.如设置为200,表示滚动条在离目标位置还有200的高度时就开始加载图片,可以做到不让用户察觉
        event: 'click',  // 点击事件触发时才加载
        // event,值有click(点击),mouseover(鼠标划过),sporty(运动的),foobar(…).可以实现鼠标莫过或点击图片才开始加载,后两个值未测试…
        container: $("#container"),  // 对某容器中的图片实现效果
        // container,值为某容器.lazyload默认在拉动浏览器滚动条时生效,这个参数可以让你在拉动某DIV的滚动条时依次加载其中的图片
        // 一个页面有多个容器需要延迟加载时，注意每个容器里的图片要和所在的容器对应，每个容器应设置id，用id做唯一辨识
        failurelimit : 10 // 图片排序混乱时
        // failurelimit,值为数字.lazyload默认在找到第一张不在可见区域里的图片时则不再继续加载,但当HTML容器混乱的时候可能出现可见区域内图片并没加载出来的情况,failurelimit意在加载N张可见区域外的图片,以避免出现这个问题.
    });

    **更多参数介绍可见http://www.cnblogs.com/szytwo/archive/2012/12/27/2836141.html**
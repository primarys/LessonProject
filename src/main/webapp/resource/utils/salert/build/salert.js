// 美化版弹出框
// $(document).ready(function(){
	window.salert = function(str, theme, setting){
		$('<style class="salert-style">.beauty-alert{position:fixed;z-index:1110;min-height:40px;min-width:200px;max-height:90%;background:rgba(100,100,100,0.5);border-radius:5px;top:50%;left:50%;padding:0 10px;text-align:center;font-size:18px;line-height:40px;color:#fff;font-family:"Microsoft Yahei",Arial;word-wrap:break-word;word-break:break-all;overflow:auto;-webkit-transform:translateX(-50%) translateY(-50%);-moz-transform:translateX(-50%) translateY(-50%);-ms-transform:translateX(-50%) translateY(-50%);-o-transform:translateX(-50%) translateY(-50%);transform:translateX(-50%) translateY(-50%);-webkit-transition:0.5s all ease;-moz-transition:0.5s all ease;-ms-transition:0.5s all ease;-o-transition:0.5s all ease;transition:0.5s all ease;}.beauty-alert.alert-red{background:rgba(200,0,0,0.5);}.beauty-alert.alert-orange{background:rgba(254,135,82,0.6);}.beauty-alert.alert-green{background:rgba(128,174,58,0.6);}.beauty-alert.alert-down{margin-top:30px;}.beauty-alert.alert-up{margin-top:-30px;}</style>').appendTo($("head"));
		var alert_box = $('<div class="beauty-alert ' + theme + '" style="opacity:0;z-index=99999;"></div>');
		var defaultSetting = {
	    	move:false
	    }
		if(typeof setting == "object"){
			if(typeof setting.move != "boolean"){
				setting.move = defaultSetting.move;
			}
		}else{
			setting = defaultSetting
		}
	    if(setting.move){
	    	alert_box.addClass("alert-down");
	   	}
	    alert_box.text(str);
	    $("body").append(alert_box);
	    setTimeout(function() {
	        alert_box.css("opacity", 1);
	        if(setting.move){
	        	alert_box.removeClass("alert-down");
	        }
	    }, 1);

	    setTimeout(function() {
	        alert_box.css("opacity", 0);
	        if(setting.move){
	        	alert_box.addClass("alert-up");
	        }
	    }, 2000);
	    setTimeout(function() {
	        alert_box.remove();
	        if(setting.move){
	        	alert_box.removeClass("alert-up");
	        }
	    }, 3000);
	};
// });

//salert("hello");//弹出半透明灰色提示框
//salert("hello","alert-red");//弹出半透明红色提示框
//salert("hello","alert-orange");//弹出半透明橙色提示框
//salert("hello","alert-green");//弹出半透明绿色提示框
//salert("hello","",{move:true});//弹出有移动效果的提示框
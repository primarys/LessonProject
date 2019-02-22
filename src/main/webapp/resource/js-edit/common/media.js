/**
 * Created by Dobility on 2016/8/23.
 */

//返回是否是PC
var MEDIA = new Object();
MEDIA.isPhone = function() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
                  "SymbianOS", "Windows Phone",
                  "iPod","iPad"];
    var flag = false;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = true;
            break;
        }
    }
    return flag;
};

// 返回是否为微信浏览器
MEDIA.isWeChat = function () {
    if(ua.indexOf("micromessenger") >= 0 && MEDIA.isPhone()) {
        return true;
    }else{
        return false;
    }
};

// 浏览器名称和版本号
// ios不支持 exec 方法
MEDIA.browser = "";
MEDIA.version = "0"; 
var ua = navigator.userAgent.toLowerCase();
var r = new Array(6);
	r[0] = /(msie\s|trident.*rv:)([\w.]+)/;   
	r[1] = /(firefox)\/([\w.]+)/;   
	r[2] = /(opera).+version\/([\w.]+)/;   
	r[3] = /(chrome)\/([\w.]+)/;   
	r[4] = /version\/([\w.]+).*(safari)/;
for (var i = 0; i < r.length; i++) {
	var match = r[i].exec(ua);
	if(match != null){
		MEDIA.browser = match[1];
		if (i == 0) MEDIA.browser = "IE";
		MEDIA.version = match[2] || "0";  
		break;
	}
}

var num = /^\d$/g;
if (!num.test(MEDIA.version.substring(0, 1))) {
	var temp = MEDIA.browser;
	MEDIA.browser = MEDIA.version;
	MEDIA.version = temp;
}

//返回是否 IE8 及以下版本的 IE
MEDIA.lteIE9 = function() {
	if (MEDIA.browser == 'IE' && parseInt(MEDIA.version) <= '8.0') {
		return true;
	}
	return false;
};

//返回是否 UC 浏览器
MEDIA.isUCMobile = function() {
	if (ua.indexOf("ucbrowser") >= 0 && MEDIA.isPhone()){
		return true;
	} else {
		return false;
	}
};

//保留旧版本，以防止无法使用isPhone()
function isPhone() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
                  "SymbianOS", "Windows Phone",
                  "iPod","iPad"];
    var flag = false;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = true;
            break;
        }
    }
    return flag;
};

//visible-pc、visible-mb、hidden-pc、hidden-mb类
$(function() {
    if (MEDIA.isPhone()) {
        $(".visible-pc").hide();
        $(".visible-mb").show();
        $(".hidden-mb").hide();
    } else {
        $(".visible-mb").hide();
        $(".visible-pc").show();
        $(".hidden-pc").hide();
    }
});
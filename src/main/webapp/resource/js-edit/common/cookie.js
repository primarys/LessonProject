function addCookie(name,value) {
    var cookieString = name + "=" + escape(value);
    var expireDay = 30;
    var date = new Date();
    date.setTime(date.getTime() + expireDay*24*60*60*1000); 
    cookieString = cookieString + "; path=/; expires=" + date.toGMTString();
	document.cookie = cookieString; 
}

function getCookie(cookie_name) {
    var results = document.cookie.match('(^|;) ?' + cookie_name + '=([^;]*)(;|$)'); 
    
    if (results) 
        return (unescape(results[2])); 
    else 
        return null; 
}

function delCookie(name) {
    // 所谓删除 cookie，就是让 cookie 过期
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if(cval != null) {
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }
}
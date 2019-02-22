<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.regex.Pattern" %>
<%!
    String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
            + "|windows (phone|ce)|blackberry"
            + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            + "|laystation portable)|nokia|fennec|htc[-_]"
            + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
            + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
    Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

    public boolean checkMobile(String userAgent) {
        if (null == userAgent) {
            userAgent = "";
        }
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            return true;
        } else {
            return false;
        }
    }

    public int getIEVersion(String userAgent) {
        int msie = userAgent.indexOf("msie");
        String version = "10";
        if (msie >= 0) {
            version = userAgent.substring(msie + 5, msie + 7);
            if (version.substring(1, 2).equals(".")) {
                version = version.substring(0, 1);
            }
        }
        return Integer.valueOf(version);
    }

    public boolean checkWeChat(String userAgent) {
        if (checkMobile(userAgent) && userAgent.indexOf("micromessenger") >= 0) {
            return true;
        }
        return false;
    }
%>
<%
    String userAgent = request.getHeader("USER-AGENT").toLowerCase();
    String path1 = request.getContextPath();
    String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path1 + "/";
    String $ctx = basePath1;
    Boolean isPhone = false;
    Boolean isWeChat = checkWeChat(userAgent);
    int IE_VERSION = getIEVersion(userAgent);
    if (IE_VERSION < 10) {
        response.sendRedirect($ctx + "error/noie.jsp");
    }
    if (null == userAgent) {
        userAgent = "";
    }
    if (checkMobile(userAgent)) {
        isPhone = true;
    }
    request.setAttribute("isPhone", isPhone);
    request.setAttribute("isWeChat", isWeChat);
%>
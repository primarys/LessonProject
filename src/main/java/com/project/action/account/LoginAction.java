package com.project.action.account;

import com.project.base.BaseAction;
import com.project.common.ViewLocation;
import com.project.model.*;
import com.project.service.*;
import com.project.utils.*;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Controller
@Namespace("/")
public class LoginAction extends BaseAction<Object>{

    @Autowired
    AdminService adminService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    LessonService lessonService;
    @Autowired
    StudentService studentService;
    @Autowired
    RollCallService rollCallService;
    @Autowired
    StuLessonService stuLessonService;

    // 图形验证码
    @Action(value = "verifyImg", results = {@Result(name = "verifyImg", location = ViewLocation.View_ROOT + "verifyImg.jsp")})
    public String verifyImg() {
        return "verifyImg";
    }

    // 管理员登录验证
    @Action(value = "login", results = {@Result(name = "LoginUI", location = ViewLocation.View_ROOT + "manage/login.jsp"),
            @Result(name = "IndexUI", type=TYPE_REDIRECT_ACTION, location = "manage/dashboard")})
    public String login() throws Exception{
        String username = getParameter("name");//username是登录名，账号或者手机
        String password = getParameter("password");
        String loginerJudge = getParameter("loginerJudge");
        request.setAttribute("name", username);
        request.setAttribute("password", password);
        request.setAttribute("loginerJudge",loginerJudge);
        if( session.getAttribute("loginerr") != null && (int)(session.getAttribute("loginerr")) >= 3 ) {
            String verify = getParameter("verify").toUpperCase();
            // 信息完整性验证
            if (username.equals("") && password.equals("") && verify.equals("")) {
                return "LoginUI";
            }
            if (username.equals("") || password.equals("") || verify.equals("")) {
                request.setAttribute("err", "请完整输入信息");
                return "LoginUI";
            }
            if (!verify.equals(session.getAttribute(Const.RAND_IMG))) {
                request.setAttribute("err", "验证码错误");
                return "LoginUI";
            }
        }
        else{
            if (username.equals("") && password.equals("")) {
                return "LoginUI";
            }
            if (username.equals("") || password.equals("")) {
                request.setAttribute("err", "请完整输入信息");
                return "LoginUI";
            }
        }
        // 密码加密
        password = DigestUtil.encryptPWD(password);
        // 登录判断
        return loginIn(username, password, loginerJudge);
    }

    // 管理员注销账号
    @Action(value = "logout", results = {@Result(name = "login", type=TYPE_REDIRECT_ACTION, location = "login")})
    public String logout() {
        logoutAll();
        return "login";
    }

    // 退出所有管理员账号
    public void logoutAll() {
        session.removeAttribute(Const.USER_ADMIN);
        session.removeAttribute(Const.USER_TEACHER);
    }

    //扫码微信签到
    @Action(value = "toRegister", results = {@Result(name = "register", location = ViewLocation.View_ROOT + "student/login.jsp")})
    public String toRegister(){
        Integer lessonId = getIntegerParameter("lessonId");
        request.setAttribute("lessonId",lessonId);
        return "register";
    }

    // 微信注册
    @Action(value = "weixinLogin")
    public void weixinLogin() throws Exception {
        //请求weixinLogin接口前的地址--存于session，用于weixinAuthorization接口最后回调
        /*String jump = getParameter("jump");
        String[] urlArray = new String[3];
        urlArray = jump.split("lessonId=");
        jump=urlArray[0];
        Integer lessonId =Integer.parseInt(urlArray[1]);
        session.setAttribute("jump", jump);*/
        // 如果没有cookie，或者即使有cookie但保存的openId找不到对应用户，就要重新获取了
        //请求授权，拿到code
        Integer lessonId = Integer.parseInt(getParameter("lessonId"));
        log.info("weixinLogin lessonId：" + lessonId);
        String redirect_uri = getWebRoot() + "weixinAuthorization";
        log.info("请求微信授权接口Url：" + redirect_uri);
        String urlString = WeixinConfig.getAuthorizeUrl(redirect_uri)+"&lessonId="+lessonId;
        log.info(redirect_uri);
        session.setAttribute("lessonId",lessonId);
        log.info("weixinLoginSession lessonId："+(Integer)(getSession().getAttribute("lessonId")));
//        request.setAttribute("lessonId", lessonId);
        response.sendRedirect(urlString);   // 调用微信登录api
    }

    // 微信登录回调
    @Action(value = "weixinAuthorization", results = {@Result(name = "stuSelf",location = ViewLocation.View_ROOT + "student/m/stuSelf.jsp"),@Result(name = "fillInfo", location = ViewLocation.View_ROOT + "student/m/fillInfo.jsp")})
    public String weixinAuthorization() throws IOException {

//        Integer lessonId = getIntegerParameter("lessonId");
        Integer lessonId = (Integer)(getSession().getAttribute("lessonId"));
        log.info("weixinAuthorization lessonId：" + lessonId);
        // 获得调用后的返回值code
        String code = getParameter("code");
        log.info("code:" + code);

        //向指定URL发送GET方法的请求
        //根据code，请求access_token
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = WeixinConfig.getOpenIdUrl(code);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送GET请求出现异常！" + e);
            session.setAttribute(Const.ERROR_TIP, "请求微信接口异常");
            log.info(DataUtils.getTrace(e));
        } finally {
            // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        log.info("响应结果：" + result);

        /**
         * 第一步：获取openid，判断是否新用户
         */
        Student student = new Student();
        boolean studentExist = true;
        JSONObject jsonObject = JSONObject.fromObject(result);
        String weixinOpenId = null;
        String weixinNickname = null;
        String weixinHeadImgUrl = null;
        if (jsonObject.containsKey("openid")) {
            weixinOpenId = jsonObject.getString("openid");
            log.info("openid:" + weixinOpenId);
            student = studentService.getByWechatId(weixinOpenId);
            if(student == null){
                studentExist = false;
            }
        }

        /**
         * 第二步：如果是新用户的话，拉取用户信息
         */
        if (!studentExist) {
            String inforesult = "";
            try {
                String InfoUrl = WeixinConfig.getUserInfoUrl(jsonObject.getString("access_token"), jsonObject.getString("openid"));
                URL infourl = new URL(InfoUrl);
                URLConnection infoconnection = infourl.openConnection();
                infoconnection.setRequestProperty("accept", "*/*");
                infoconnection.connect();
                BufferedReader infoin = null;
                infoin = new BufferedReader(new InputStreamReader(infoconnection.getInputStream(), "UTF-8"));
                String line;
                while ((line = infoin.readLine()) != null) {
                    inforesult += line;
                }
            } catch (Exception e) {
                log.info("发送GET请求异常!" + e);
                session.setAttribute(Const.ERROR_TIP, "请求微信接口异常");
                log.info(DataUtils.getTrace(e));
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            log.info("响应结果：" + inforesult);
            JSONObject infojsonobject = JSONObject.fromObject(inforesult);
            if (infojsonobject.containsKey("openid")) {
                weixinOpenId = infojsonobject.getString("openid");
                weixinNickname = infojsonobject.getString("nickname");
                weixinHeadImgUrl = infojsonobject.getString("headimgurl");
            } else if (infojsonobject.containsKey("errcode")) {
                if (session.getAttribute(Const.ERROR_TIP).equals("")) {
                    session.setAttribute(Const.ERROR_TIP, "请求用户信息出现异常！--errorCode："
                            + infojsonobject.getString("errcode") + " msg：" + infojsonobject.getString("errmsg"));
                }
                log.info(infojsonobject.getString("errmsg"));
            } else {
                if (session.getAttribute(Const.ERROR_TIP).equals("")) {
                    session.setAttribute(Const.ERROR_TIP, "请求用户信息未取得数据！");
                }
            }
        }

        /**
         * BY吴克强
         * 以下代码用来判断该openid是否已经绑定学生，若绑定则跳转签到成功，否则跳转到学号姓名填写界面
         */
        if(weixinOpenId == null){
            log.info("获取微信openId失败!");
        }
        else{
            if(!studentExist){
                request.setAttribute("weixinOpenId",weixinOpenId);
                request.setAttribute("weixinNickname",weixinNickname);
                request.setAttribute("weixinHeadImgUrl",weixinHeadImgUrl);
                request.setAttribute("lessonId",lessonId);
                return "fillInfo";
            }
            else{
                request.setAttribute("student",student);
                request.setAttribute("lessonId",lessonId);
                return "stuSelf";
//                response.sendRedirect((String)session.getAttribute("jump"));
            }
        }

        return null;
    }

    /**
     * BY吴克强
     * 信息填写处理，填写这个openid将要绑定的学生，若信息填错则跳转报错页面，否则跳转签到成功
     * @return
     */
    @Action(value = "fillInfo", results = {@Result(name = "stuSelf",location = ViewLocation.View_ROOT + "student/m/stuSelf.jsp"), @Result(name = "fillInfo",location = ViewLocation.View_ROOT + "student/m/fillInfo.jsp")})
    public String fillInfo(){
        Integer lessonId = getIntegerParameter("lessonId");
        String stuNumber = getParameter("stuNumber");
        String stuName = getParameter("stuName");
        String weixinOpenId = getParameter("weixinOpenId");
        String weixinNickname = getParameter("weixinNickname");
        String weixinHeadImgUrl = getParameter("weixinHeadImgUrl");
        //String weixinOpenIdTest = "aaaaa";
        Student student = studentService.getByNameAndNumber(stuName, stuNumber);
        if (student == null){
            log.info("根据姓名学号没有找到学生!");
            request.setAttribute("error","根据学号姓名找不到该学生!");
            return "fillInfo";
        }
        //student.setWeichat_openid(weixinOpenIdTest);
        student.setWeichat_openid(weixinOpenId);
        student.setNickname(weixinNickname);

        /**
         * 这里要设置头像，根据weixinHeadImgUrl下载然后存到资源库
         */

        studentService.update(student);
        request.setAttribute("student",student);
        request.setAttribute("lessonId", lessonId);
        return "stuSelf";
    }

    //学生扫码后跳转页面
   /* @Action(value = "stuSelf",results = {@Result(name = "stuSelf", location = ViewLocation.View_ROOT + "student/m/stuSelf.jsp")})
    public String stuSelf(){
        Integer lessonId = getIntegerParameter("lessonId");
        Integer studentId = getIntegerParameter("studentId");
       // Student student = studentService.getByNameAndNumber();
        request.setAttribute("lessonId", lessonId);
        request.setAttribute("studentId", studentId);
        return "stuSelf";
    }*/

    /**
     * BY吴克强
     * 点击签到确定后更新点名表
     * @return
     */
    @Action(value = "confirm", results = {@Result(name = "stuSelfInfo", location = ViewLocation.View_ROOT + "student/m/stuSelfInfo.jsp")})
    public String confirm(){
        Integer studentId = getIntegerParameter("studentId");
        Integer lessonId = getIntegerParameter("lessonId");
        Student student = studentService.getById(studentId);
        Lesson lesson = lessonService.getByVid(lessonId);
        RollCall rollCall = rollCallService.getLastRollCallByLessonAndStudent(lesson, student);
        short fromState = rollCall.getState();
        rollCall.changeState(fromState, Const.ROLLCALL_PRESENT);
        rollCallService.update(rollCall);
        StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
        stuLesson.changeState(fromState, Const.ROLLCALL_PRESENT);
        stuLessonService.update(stuLesson);
        request.setAttribute("student", student);
        return "stuSelfInfo";
    }

    private String loginIn(String username, String password, String loginerJudge){
        if (username.equals(Const.USER_ADMIN_NAME)) {
            Admin admin = adminService.checkLogin(password);
            if (admin != null) {
                // 注销其他管理员账号
                logoutAll();
                // 记录登录时间、ip
                admin.setLoginRecord();
                adminService.update(admin);
                // 放入session中
                admin.setPassword("");
                session.setAttribute(Const.USER_ADMIN, admin);
                return "IndexUI";
            } else {
                request.setAttribute("err", "密码错误");
                request.setAttribute("errType", "2");
                request.setAttribute("password", "");
                if (session.getAttribute("loginerr") == null) {
                    session.setAttribute("loginerr", 1);
                } else {
                    int loginerr = (int) (session.getAttribute("loginerr"));
                    session.setAttribute("loginerr", loginerr + 1);
                }
            }
            return "LoginUI";
        }

        // 判断Teacher
        if (username.substring(0, Const.USER_TEACHER_PRE.length()).equals(Const.USER_TEACHER_PRE)) {
            Teacher teacher = teacherService.checkLoginFromUsername(username, password);
            if (teacher != null) {
                // 注销其他管理员账号
                logoutAll();
                // 记录登录时间、ip
//                        teacher.setLoginRecord();
                teacherService.update(teacher);
                // 放入session中
                teacher.setPassword("");
                session.setAttribute(Const.USER_TEACHER, teacher);
                return "IndexUI";
            } else {
                request.setAttribute("err", "密码错误");
                request.setAttribute("errType", "2");
                request.setAttribute("password", "");
                if (session.getAttribute("loginerr") == null) {
                    session.setAttribute("loginerr", 1);
                } else {
                    int loginerr = (int) (session.getAttribute("loginerr"));
                    session.setAttribute("loginerr", loginerr + 1);
                }
                return "LoginUI";
            }
        }

        // 判断手机号码Teacher
        if(ValidateUtils.checkMobileNumber(username) && loginerJudge.equals("teacher")){
            Teacher teacher = teacherService.checkLoginFromPhoneNum(username, password);
            if (teacher != null) {
                logoutAll();
                teacherService.update(teacher);
                teacher.setPassword("");
                session.setAttribute(Const.USER_TEACHER, teacher);
                return "IndexUI";
            } else {
                request.setAttribute("err", "密码错误");
                request.setAttribute("errType", "2");
                request.setAttribute("password", "");
                if(session.getAttribute("loginerr") == null) {
                    session.setAttribute("loginerr",1);
                }else{
                    int loginerr = (int) (session.getAttribute("loginerr"));
                    session.setAttribute("loginerr",loginerr + 1);
                }
                return "LoginUI";
            }
        }

        // 判断手机号码student
        if(ValidateUtils.checkMobileNumber(username) && loginerJudge.equals("student")){
            Student student = studentService.checkLoginFromPhoneNum(username, password);
            if (student != null) {
                logoutAll();
                studentService.update(student);
                student.setPassword("");
                session.setAttribute(Const.USER_STUDENT, student);
                return "IndexUI";
            } else {
                request.setAttribute("err", "密码错误");
                request.setAttribute("errType", "2");
                request.setAttribute("password", "");
                if(session.getAttribute("loginerr") == null) {
                    session.setAttribute("loginerr",1);
                }else{
                    int loginerr = (int) (session.getAttribute("loginerr"));
                    session.setAttribute("loginerr",loginerr + 1);
                }
                return "LoginUI";
            }
        }
        request.setAttribute("err", "账号未注册");
        request.setAttribute("errType", "1");
        return "LoginUI";
    }

}

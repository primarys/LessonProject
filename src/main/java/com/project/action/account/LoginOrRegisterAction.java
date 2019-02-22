package com.project.action.account;

import com.project.base.BaseAction;
import com.project.common.ViewLocation;
import com.project.model.Student;
import com.project.model.Teacher;
import com.project.service.TeacherService;
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
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by Wu keqiang on 2018/4/24
 **/
@Controller
@Namespace("/")
public class LoginOrRegisterAction extends BaseAction<Object>{

    @Autowired
    TeacherService teacherService;

    /**
     * 注册页面跳转
     */
    @Action(value = "register", results = {@Result(name = "registerJSP", location = ViewLocation.View_ROOT + "manage/register.jsp")})
    public String register() {
        return "registerJSP";
    }

    /**
     * 老师注册
     * @return
     */
    @Action(value = "teacherRegister", results = {@Result(name = "teacherRegister", location = ViewLocation.View_ROOT + "manage/login.jsp"),
                                                  @Result(name = "teacherRegisterError", location = ViewLocation.View_ROOT + "manage/register.jsp")})
    public String teacherRegister() {

        String teacherName = getParameter("teacherName");
        //String userName = getParameter("userName");
        String phoneNum = getParameter("phoneNum");
        String email = getParameter("email");
        String password = getParameter("password");
        String passwordConfirm = getParameter("passwordConfirm");
        String dynamicCode = getParameter("dynamicCode");
        String verificationCode = session.getAttribute(Const.RAND_IMG).toString();

        Teacher teacherQuery =teacherService.getByPhoneNumber(phoneNum);
        if (teacherQuery != null){
            request.setAttribute("err", "该手机号码已被注册!");
            return "teacherRegisterError";
        }
        password = DigestUtil.encryptPWD(password);
        passwordConfirm = DigestUtil.encryptPWD(passwordConfirm);
        if (!password.equals(passwordConfirm)) {
            request.setAttribute("err", "两次密码不相同!");
            return "teacherRegisterError";
        }
        if (!verificationCode.equals(dynamicCode)&&!verificationCode.equals(dynamicCode.toUpperCase())){
            request.setAttribute("err", "验证码错误!");
            return "teacherRegisterError";
        }
        Teacher teacher = new Teacher(teacherName,phoneNum, password, email);
        teacherService.save(teacher);
        return "teacherRegister";
    }

    /**
     * 老师登录
     */
    @Action(value = "teacherLogin", results = {@Result(name = "LoginUI", location = ViewLocation.View_ROOT + "manage/dashboard.jsp"),
                                               @Result(name = "LoginErr", location = ViewLocation.View_ROOT + "manage/login.jsp")})
    public String teacherLogin() {
        short loginType = getIntegerParameter("loginType").shortValue();
        String phoneNum = getParameter("phoneNum");
        Teacher teacher = teacherService.getByPhoneNumber(phoneNum);
        if (teacher == null) {
            request.setAttribute("err", "手机号码错误或未注册!");
            return "";
        }
        if (loginType == Const.LOGIN_PASSWORD) {
            String password = getParameter("password");
            password = DigestUtil.encryptPWD(password);
            if (!teacher.getPassword().equals(password)) {
                request.setAttribute("err", "密码错误!");
                return "LoginErr";
            }
        }
        else if (loginType == Const.LOGIN_DYNAMICCODE) {
            String dynamicCode = getParameter("dynamicCode");
            String verificationCode = session.getAttribute("verificationCode").toString();
            if (!verificationCode.equals(dynamicCode)) {
                request.setAttribute("err", "验证码错误!");
                return "LoginErr";
            }
        }
        return "LoginUI";
    }


    /**
     * 发送短信动态码
     *
     * @return
     */
    @Action(value = "sendVerification")
    public void sendVerification() throws Exception {
        String verify = getParameter("verify");
        verify = verify.toUpperCase();
        if (verify.equals(session.getAttribute(Const.RAND_IMG))) {
            String ip = request.getRemoteAddr();
            String phone = getParameter("phoneNum");
            // 1.生成动态码
            String verificationCode = MessageSend.getVerificationCode();
            try {
                JSONObject result = JSONObject
                        .fromObject(MessageSend.sendDynamicVerification(
                                verificationCode, phone, ip));
                if ("OK".equals(result.get("msg"))) {
                    session.removeAttribute(Const.PHONE_VERIFICATION_CODE);
                    session.setAttribute(Const.PHONE_VERIFICATION_CODE, verificationCode);
                    writeStringToResponse("发送手机动态码成功！");
                }
            } catch (Exception e) {
                log.error("发送动态码失败！");
                writeStringToResponse("发送手机动态码失败！");
                e.printStackTrace();
            }
        }else{
            log.error("图形验证码错误！");
            writeStringToResponse("图形验证码错误！");
        }
    }

    /**
     * 老师短信注册
     *
     */
    @Action(value = "dxTeacherLogin", results = {@Result(name = "LoginUI", location = ViewLocation.View_ROOT + "manage/dashboard.jsp")})
    public void dxTeacherLogin() throws Exception {
        String phoneNum = getParameter("phoneNum");
        String verify = getParameter("verify").toUpperCase();
        String verificationCode = getParameter("verificationCode");
        Teacher teacher = teacherService.getByPhoneNumber(phoneNum);
        if (verify.equals(session.getAttribute(Const.RAND_IMG)) &&
                verificationCode.equals(getSession().getAttribute(Const.PHONE_VERIFICATION_CODE))) {
            if (teacher != null) {
                writeStringToResponse("教师已注册，跳转登录！");
                session.setAttribute(Const.USER_TEACHER, teacher);
                //return "LoginUI";
            } else {
                String password = DigestUtil.encryptPWD(Const.DUFAULT_PASSWORD);
                teacher = new Teacher(phoneNum, password);
                teacherService.save(teacher);
                writeStringToResponse("教师注册成功，跳转登录！");
                session.setAttribute(Const.USER_TEACHER, teacher);
                //return "LoginUI";
            }
        } else {
            log.error("短信验证码错误！");
            writeStringToResponse("短信验证码错误！");
            //return "";
        }
    }

    @Action(value = "TeacherLoginOK", results = {@Result(name = "LoginUI", type=TYPE_REDIRECT_ACTION, location = "manage/dashboard")})
    public String TeacherLoginOK() throws Exception {
        session.setAttribute(Const.USER_TEACHER, getSession().getAttribute(Const.USER_TEACHER));
        return "LoginUI";
    }


    //扫码微信签到
    @Action(value = "teacherLoginOrRegister", results = {@Result(name = "loginOrRegister", location = ViewLocation.View_ROOT + "manage/teacher/m/login.jsp")})
    public String toRegister(){
        return "loginOrRegister";
    }

    // 微信注册
    @Action(value = "weixinLoginOrRegister")
    public void weixinLoginOrRegister() throws Exception {
        //请求授权，拿到code
        String redirect_uri = getWebRoot() + "weixinAuthorization1";
        log.info("请求微信授权接口Url：" + redirect_uri);
        String urlString = WeixinConfig.getAuthorizeUrl(redirect_uri);
        log.info(redirect_uri);
        response.sendRedirect(urlString);   // 调用微信登录api
    }

    // 微信登录回调
    @Action(value = "weixinAuthorization1", results = {@Result(name = "IndexUI", type=TYPE_REDIRECT_ACTION, location = "manage/dashboard"),
                                                      @Result(name = "register", location = ViewLocation.View_ROOT + "teacher/m/register.jsp")})
    public String weixinAuthorization1() throws IOException {
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
        Teacher teacher = new Teacher();
        boolean teacherExist = true;
        JSONObject jsonObject = JSONObject.fromObject(result);
        String weixinOpenId = null;
        String weixinNickname = null;
        String weixinHeadImgUrl = null;
        if (jsonObject.containsKey("openid")) {
            weixinOpenId = jsonObject.getString("openid");
            log.info("openid:" + weixinOpenId);
            teacher = teacherService.getByWeixinOpenId(weixinOpenId);
            if (teacher == null) {
                teacherExist = false;
            }
        }

        /**
         * 第二步：如果是新用户的话，拉取用户信息
         */
        if (!teacherExist) {
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
        if (teacherExist) {
            teacher.teacherTrim();
            //如果这个微信openId绑定了老师，则转入登录界面
            session.setAttribute(Const.USER_TEACHER, teacher);
            return "IndexUI";
        }
        request.setAttribute("weixinOpenId", weixinOpenId);
        request.setAttribute("weixinNickname", weixinNickname);
        request.setAttribute("weixinHeadImgUrl", weixinHeadImgUrl);
        return "register";
    }

    @Action(value = "registerConfirm", results = {@Result(name = "IndexUI", type=TYPE_REDIRECT_ACTION, location = "manage/dashboard")})
    public String registerConfirm() {
        // 显示的名字
        String teacherName = getParameter("teacherName");
        //性别
        short sex = getIntegerParameter("sex").shortValue();
        //手机号码
        String phoneNum = getParameter("phoneNum");
        //邮箱
        String email = getParameter("email");
        //微信openId
        String weixinOpenId = getParameter("weixinOpenId");
        Teacher teacher = new Teacher(teacherName, sex, phoneNum, email, weixinOpenId);
        teacherService.save(teacher);
        session.setAttribute("teacher",teacher);
        request.setAttribute("msg", "注册成功");
        return "IndexUI";
    }

    @Action(value = "hasPhoneRegister")
    public String hasPhoneRegister() {
        String tel = getParameter("phoneNum");
        if (!ValidateUtils.checkMobileNumber(tel)) {
            //getSession().put(Const.ERROR_TIP, "手机号格式有误");
            writeStringToResponse("2");
            return "2";//手机号格式有误
        }
        Teacher teacher = teacherService.getByPhoneNumber(tel);
        if (teacher == null) {
            writeStringToResponse("0");
            return "0";//未注册
        }
        writeStringToResponse("1");//已注册
        return "1";
    }

    @Action(value = "checkPswLength")
    public String checkPswLength() {
        String psw = getParameter("psw");
        if(!ValidateUtils.checkPasswordLength(psw)) {
            writeStringToResponse("1");//密码长度出错;
            return "1";
        }
        writeStringToResponse("0");
        return "0";
    }

    @Action(value = "hasSamePsw")
    public String hasSamePsw() {
        String psw = getParameter("psw");
        String repsw = getParameter("repsw");
        String password = DigestUtil.encryptPWD(psw);
        String rePassword = DigestUtil.encryptPWD(repsw);
        if (!password.equals(rePassword)) {
            writeStringToResponse("0");
            return "0";
        }
        writeStringToResponse("1");
        return "1";
    }

}

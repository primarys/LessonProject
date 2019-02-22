package com.project.action.account;

import com.project.base.BaseAction;
import com.project.common.ViewLocation;
import com.project.model.Admin;
import com.project.model.Teacher;
import com.project.service.AdminService;
import com.project.service.TeacherService;
import com.project.utils.Const;
import com.project.utils.DigestUtil;
import com.project.utils.ValidateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Namespace("/")
public class ForgetAction extends BaseAction<Object> {

    @Autowired
    AdminService adminService;
    @Autowired
    TeacherService teacherService;

    // 忘记密码
    @Action(value = "forget", results = {
            @Result(name = "forget", location = ViewLocation.View_ROOT + "manage/forget.jsp"),
            @Result(name = "reset", type = TYPE_REDIRECT_ACTION, location = "reset")})
    public String forget() {
        String name = getParameter("name");
        String phone = getParameter("phone");
        String verify = getParameter("verify").toUpperCase();
        request.setAttribute("name", name);
        request.setAttribute("phone", phone);
        // 出错判断
        if (name.isEmpty() && phone.isEmpty() && verify.isEmpty()) {
            return "forget";
        }
        if (name.isEmpty() || phone.isEmpty() || verify.isEmpty()) {
            request.setAttribute("err", "请完整输入信息");
            return "forget";
        }
        if (!verify.equals(session.getAttribute(Const.RAND_IMG))) {
            request.setAttribute("err", "验证码错误");
            return "forget";
        }
        if (!ValidateUtils.checkMobileNumber(phone)) {
            request.setAttribute("err", "手机格式错误");
            return "forget";
        }
        // 账号类型判断
        session.setAttribute("forgetRole", null);
        session.setAttribute("forgetType", null);
        // Admin判断
        if (name.equals(Const.USER_ADMIN_NAME)) {
            Admin admin = adminService.getById(1);
            if (!admin.getPhone().equals(phone)) {
                request.setAttribute("err", "登录名与手机号码不匹配");
                return "forget";
            } else {
                session.setAttribute("forgetRole", admin);
                session.setAttribute("forgetType", Const.USER_ADMIN);
            }
        }
        // Teacher判断
        if (name.substring(0, Const.USER_TEACHER_PRE.length()).equals(Const.USER_TEACHER_PRE)) {
            Teacher teacher = teacherService.getByUsername(name);
            if (teacher == null) {
                request.setAttribute("err", "不存在此登录名");
                return "forget";
            } else if (!teacher.getPhoneNum().equals(phone)) {
                request.setAttribute("err", "登录名与手机号码不匹配");
                return "forget";
            } else {
                session.setAttribute("forgetRole", teacher);
                session.setAttribute("forgetType", Const.USER_TEACHER);
            }
        }
        // 通过了验证，可以跳转到第二页面
        return "reset";
    }

    // 重置密码页面
    @Action(value = "reset", results = {
            @Result(name = "reset", location = ViewLocation.View_ROOT + "manage/reset.jsp"),
            @Result(name = "forget", type = TYPE_REDIRECT_ACTION, location = "forget"),
            @Result(name = "login", type = TYPE_REDIRECT_ACTION, location = "manage/dashboard")})
    public String reset() {
        // 需要经过第一步判断才能进入
        if (session.getAttribute("forgetRole") == null) {
            return "forget";
        }
        String password = getParameter("password");
        if (password.isEmpty()) {
            return "reset";
        }
        password = DigestUtil.encryptPWD(password);
        // 修改密码吧
        String type = (String)session.getAttribute("forgetType");
        if (type.equals(Const.USER_ADMIN)) {
            // 修改密码
            Admin admin = (Admin)session.getAttribute("forgetRole");
            admin.setPassword(password);
            adminService.update(admin);
            session.setAttribute(Const.USER_ADMIN, admin);
        } else if(type.equals(Const.USER_TEACHER)) {
            // 修改密码
            Teacher teacher = (Teacher)session.getAttribute("forgetRole");
            teacher.setPassword(password);
            teacherService.update(teacher);
            session.setAttribute(Const.USER_TEACHER, teacher);
        }
        // 自动登录
        return "login";
    }
}

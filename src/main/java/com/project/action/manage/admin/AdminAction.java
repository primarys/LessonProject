package com.project.action.manage.admin;

import com.project.base.BaseAction;
import com.project.common.ViewLocation;
import com.project.model.*;
import com.project.service.*;
import com.project.utils.Const;
import com.project.utils.DigestUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ParentPackage("admins")
@Namespace("/manage/admin")
public class AdminAction extends BaseAction<Admin>{

    @Autowired
    AdminService adminService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    PicStoreService picStoreService;

    // 首页
    @Action(value = "index", results = {@Result(name = "index", location = ViewLocation.View_ROOT + "manage/admin/index.jsp")})
    public String index() {
        Admin admin = (Admin) session.getAttribute(Const.USER_ADMIN);
        admin = adminService.getById(admin.getId());
        request.setAttribute("admin", admin);
        return "index";
    }

    // 修改资料页面
    @Action(value = "edit", results = {@Result(name = "editUI", location = ViewLocation.View_ROOT + "manage/admin/admin-edit.jsp")})
    public String edit() {
        Admin admin = (Admin) session.getAttribute(Const.USER_ADMIN);
        admin = adminService.getById(admin.getId());    // 避免session中admin不是最新
        request.setAttribute("admin", admin);
        return "editUI";
    }

    // 修改资料
    @Action(value = "updateAdmin")
    public void updateAdmin() {
        try {
            String name = getParameter("name");
            String phone = getParameter("phone");
            String email = getParameter("email");
            Admin admin = (Admin) session.getAttribute(Const.USER_ADMIN);
            admin = adminService.getById(admin.getId());    // 避免session中admin不是最新
            admin.setName(name);
            admin.setPhone(phone);
            admin.setEmail(email);
            adminService.update(admin);
            writeStringToResponse(Const.JSON_SUCCESS);
        } catch (Exception e) {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

    // 修改密码页面
    @Action(value = "modifyPassword", results = {
            @Result(name = "modifyPasswordUI", location = ViewLocation.View_ROOT + "manage/admin/modify-password.jsp")})
    public String modifyPassword() {
        return "modifyPasswordUI";
    }

    // 修改密码
    @Action(value = "updatePassword")
    public void updatePassword() {
        String password = getParameter("password");
        String newPassword = getParameter("newPassword");
        Admin admin = adminService.checkLogin(DigestUtil.encryptPWD(password));
        if(admin==null) {
            writeStringToResponse(Const.JSON_FAIL);
        } else {
            admin.setPassword(DigestUtil.encryptPWD(newPassword));
            adminService.update(admin);
            writeStringToResponse(Const.JSON_SUCCESS);
            session.removeAttribute(Const.USER_ADMIN);
        }
    }

}

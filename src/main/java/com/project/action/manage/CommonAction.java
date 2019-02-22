package com.project.action.manage;

import com.project.base.BaseAction;
import com.project.base.BaseModel;
import com.project.common.ViewLocation;
import com.project.common.WebApplication;
import com.project.model.Admin;
import com.project.model.BaseUserModel;
import com.project.model.Teacher;
import com.project.service.AdminService;
import com.project.service.TeacherService;
import com.project.utils.Const;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ParentPackage("manages")
@Namespace("/manage")
public class CommonAction extends BaseAction<Object>{

    @Autowired
    AdminService adminService;
    @Autowired
    TeacherService teacherService;

    // 控制台首页
    @Action(value = "dashboard", results = {@Result(name = "dashboard", location = ViewLocation.View_ROOT + "manage/dashboard.jsp")})
    public String dashboard() {
        if (session.getAttribute(Const.USER_ADMIN) != null) {
            // 更新admin信息
            Admin admin = (Admin) session.getAttribute(Const.USER_ADMIN);
            admin = adminService.getById(admin.getId());
            session.setAttribute(Const.USER_ADMIN, admin);
            request.setAttribute("role", Const.USER_ADMIN);
        } else if (session.getAttribute(Const.USER_TEACHER) != null) {
            // 更新teacher信息
            Teacher teacher = (Teacher) session.getAttribute(Const.USER_TEACHER);
            teacher = teacherService.getById(teacher.getId());
            session.setAttribute(Const.USER_TEACHER, teacher);
            request.setAttribute("role", Const.USER_TEACHER);
        }
        return "dashboard";
    }

    // 检查信息填写是否完整，来控制小圆点的显示
    @Action(value = "checkInfoComplete")
    public void checkInfoComplete() {
        Admin admin = null;
        Teacher teacher = null;
        if (session.getAttribute(Const.USER_ADMIN) != null) {
            admin = (Admin) session.getAttribute(Const.USER_ADMIN);
            admin = adminService.getById(admin.getId());
        } else if (session.getAttribute(Const.USER_TEACHER) != null) {
            teacher = (Teacher) session.getAttribute(Const.USER_TEACHER);
            teacher = teacherService.getById(teacher.getId());
        }
        if ((admin != null && !admin.getPhone().isEmpty() && !admin.getName().isEmpty())||((teacher != null && !teacher.getPhoneNum().isEmpty() && !teacher.getTeacherName().isEmpty()))) {
            writeStringToResponse(Const.JSON_SUCCESS);
        } else {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

}

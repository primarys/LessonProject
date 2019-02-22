package com.project.action.manage.admin;

import com.project.base.BaseAction;
import com.project.common.ViewLocation;
import com.project.model.*;
import com.project.service.TeacherService;
import com.project.service.PicStoreService;
import com.project.utils.Const;
import com.project.utils.DigestUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@ParentPackage("admins")
@Namespace("/manage/admin")
public class AdminTeacherManage extends BaseAction<Admin>{

    @Autowired
    TeacherService teacherService;
    @Autowired
    PicStoreService picStoreService;

    // 查看所有master
    @Action(value = "teacherList", results = {@Result(name = "teacherList", location = ViewLocation.View_ROOT + "manage/admin/teacher-list.jsp")})
    public String teacherList() {
        List<Teacher> teacherList = teacherService.getAllTeacher();
        request.setAttribute("teacherList", teacherList);
        return "teacherList";
    }

    // 查看teacher详情
    @Action(value = "viewTeacher", results = {@Result(name = "viewTeacher", location = ViewLocation.View_ROOT + "manage/admin/teacher-info.jsp")})
    public String viewTeacher() {
        int id = getIntParameter("mid", 1);
        Teacher teacher = teacherService.getById(id);
        // 获取teacher的所有lesson和所有student
        request.setAttribute("teacher", teacher);
        return "viewTeacher";
    }

    // 编辑teacher详情
    @Action(value = "editTeacher", results = {@Result(name = "editTeacher", location = ViewLocation.View_ROOT + "manage/admin/teacher-edit.jsp")})
    public String editTeacher() {
        int id = getIntParameter("mid", -1);
        Teacher teacher = teacherService.getById(id);
        request.setAttribute("teacher", teacher);
        return "editTeacher";
    }

    // 修改teacher
    @Action(value = "updateTeacher")
    public void updateTeacher() {
        try {
            int id = getIntParameter("mid", -1);
            int resetHead = getIntParameter("head", 0);
            String name = getParameter("name");
            String phone = getParameter("phone");
            String email = getParameter("email");
            Teacher teacher = teacherService.getById(id);
            teacher.setTeacherName(name);
            teacher.setPhoneNum(phone);
            teacher.setEmail(email);
            PicStore pic = picStoreService.getDefaultTeacherHead();
            if (resetHead == 1) {
                //picStoreService.delete(teacher.getHeadPortrait().getId());
                teacher.setHeadPortrait(pic);
            }
            teacherService.update(teacher);
            writeStringToResponse(Const.JSON_SUCCESS);
        } catch (Exception e) {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

    // 重置teacher密码
    @Action(value = "resetTeacherPassword")
    public void resetTeacherPassword() {
        int id = getIntParameter("id", -1);
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            // 随机生成密码
            UUID uuid = UUID.randomUUID();
            String tmpPassword = uuid.toString().substring(0, 8);
            teacher.setPassword(DigestUtil.encryptPWD(tmpPassword));
            teacherService.update(teacher);
            session.removeAttribute(Const.USER_TEACHER);     // 被改密码的master下线
            writeStringToResponse(tmpPassword);
        } else {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

    // 增加teacher
    @Action(value = "createTeacher")
    public void createTeacher() {
        Teacher teacher = new Teacher();
        try {
            // 随机生成密码
            UUID uuid = UUID.randomUUID();
            String tmpPassword = uuid.toString().substring(0, 8);
            String str = DigestUtil.encryptPWD(tmpPassword);
            teacher.setPassword(str);
//            teacher.setCreateTime(new Date());
            teacherService.save(teacher);
            // 保存后再重新获得Id，用于编号username
            int id = teacher.getId();
            //  重新拿全部信息，避免外键head丢失
            teacher = teacherService.getById(id);
            teacher.setUserName(Const.USER_TEACHER_PRE + String.valueOf(Const.USER_CREATE_MAX + id).substring(1));
            teacherService.update(teacher);
            // 返回前端teacher时密码为原密码
            teacher.setUserName(Const.USER_TEACHER_PRE + String.valueOf(Const.USER_CREATE_MAX + id).substring(1));
            teacher.setPassword(tmpPassword);
            writeJsonToResponse(teacher, null);
        } catch (Exception e) {
            writeStringToResponse(Const.JSON_FAIL);
            teacherService.remove(teacher.getId());
        }
    }

    // 锁定teacher
    @Action(value = "lockTeacher")
    public void lockTeacher() {
        int id = getIntParameter("id", -1);
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
//            teacher.setLocked(!teacher.isLocked());
            teacherService.update(teacher);
            writeStringToResponse(Const.JSON_SUCCESS);
        } else {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

    // 删除teacher
    @Action(value = "deleteTeacher")
    public void deleteTeacher() {
        int id = getIntParameter("id", -1);
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            teacherService.delete(id);
            writeStringToResponse(Const.JSON_SUCCESS);
        } else {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

}

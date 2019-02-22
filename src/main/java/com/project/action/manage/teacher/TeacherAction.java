package com.project.action.manage.teacher;

import com.project.base.BaseAction;
import com.project.common.ViewLocation;
import com.project.model.Lesson;
import com.project.model.Teacher;
import com.project.model.PicStore;
import com.project.service.*;
import com.project.utils.*;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
@ParentPackage("teachers")
@Namespace("/manage/teacher")
public class TeacherAction extends BaseAction<Teacher>{

    @Autowired
    TeacherService teacherService;
    @Autowired
    PicStoreService picStoreService;
    @Autowired
    LessonService lessonService;

    // 首页
    @Action(value = "index", results = {@Result(name = "index", location = ViewLocation.View_ROOT + "manage/teacher/index.jsp")})
    public String index() {
        Teacher teacher = teacherService.getById(getLoginTeacher().getId());       // 避免session信息滞后，需要重新获取
        List<Lesson> lessonList = lessonService.getListByTeacher(getLoginTeacher());
        Date now = new Date();
        for (Lesson lesson : lessonList) {
            if (lesson.getTimeStamp() != 0 && (now.getTime() - lesson.getTimeStamp()) > Const.CLASS_LAST) {
                lesson.setTimeStamp(0);
                lessonService.update(lesson);
            }
        }
        request.setAttribute("teacher", teacher);
        request.setAttribute("lessonList", lessonList);
        return "index";
    }

    // 修改资料页面
    @Action(value = "edit", results = {@Result(name = "editUI", location = ViewLocation.View_ROOT + "manage/teacher/teacher-edit.jsp")})
    public String edit() {
        Teacher teacher = teacherService.getById(getLoginTeacher().getId());    // 避免session中admin不是最新
        request.setAttribute("teacher", teacher);
        return "editUI";
    }

    // 修改资料
    @Action(value = "updateTeacher")
    public void updateTeacher() {
        try {
            int resetHead = getIntParameter("head", 0); // 0：不改变头像，1：恢复默认，2：更换为其他头像
            String name = getParameter("name");
            String phone = getParameter("phone");
            String email = getParameter("email");
            Teacher teacher = teacherService.getById(getLoginTeacher().getId());
            teacher.setTeacherName(name);
            teacher.setPhoneNum(phone);
            teacher.setEmail(email);
            PicStore pic = picStoreService.getDefaultTeacherHead();
            if (resetHead == 1 && teacher.getHeadPortrait() != pic) {
                ImageUtils.deleteImage(picStoreService, teacher.getHeadPortrait());
                teacher.setHeadPortrait(pic);
            } else if (resetHead == 2) {
                String path = Const.USER_TEACHER + "\\" + getLoginTeacher().getId() + "\\info";
                PicStore head = ImageUtils.uploadImage(request, picStoreService, path,
                        "newHead", "管理员" + teacher.getUserName() + "的头像", teacher.getHeadPortrait());
                if (head != null) {
                    teacher.setHeadPortrait(head);
                }
            }
            teacherService.update(teacher);
            writeStringToResponse(Const.JSON_SUCCESS);
        } catch (Exception e) {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

    // 修改密码页面
    @Action(value = "modifyPassword", results = {
            @Result(name = "modifyPasswordUI", location = ViewLocation.View_ROOT + "manage/teacher/modify-password.jsp")})
    public String modifyPassword() {
        return "modifyPasswordUI";
    }

    // 修改密码
    @Action(value = "updatePassword")
    public void updatePassword() {
        String password = getParameter("password");
        String newPassword = getParameter("newPassword");
        Teacher teacher = teacherService.checkLoginFromUsername(getLoginTeacher().getUserName(), DigestUtil.encryptPWD(password));
        if(teacher == null) {
            writeStringToResponse(Const.JSON_FAIL);
        } else {
            teacher.setPassword(DigestUtil.encryptPWD(newPassword));
            teacherService.update(teacher);
            writeStringToResponse(Const.JSON_SUCCESS);
            session.removeAttribute(Const.USER_TEACHER);
        }
    }

    // 获得当前登录的master
    public Teacher getLoginTeacher() {
        return (Teacher) session.getAttribute(Const.USER_TEACHER);
    }

}

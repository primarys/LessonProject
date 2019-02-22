package com.project.action.manage.teacher;

import com.project.base.BaseAction;
import com.project.common.ViewLocation;
import com.project.model.*;
import com.project.service.LessonService;
import com.project.service.RollCallService;
import com.project.service.StuLessonService;
import com.project.service.StudentService;
import com.project.utils.Const;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@ParentPackage("teachers")
@Namespace("/manage/teacher")
public class RollCallAction extends BaseAction<RollCall> {

    @Autowired
    LessonService lessonService;
    @Autowired
    StudentService studentService;
    @Autowired
    RollCallService rollCallService;
    @Autowired
    StuLessonService stuLessonService;

    // 点名页面
    @Action(value = "rollCall", results = @Result(name = "rollCall", location = ViewLocation.View_ROOT + "manage/teacher/roll-call.jsp"))
    public String rollCall() {
        int lessonId = getIntParameter("id", -1);
        short type = (short) getIntParameter("type", Const.ROLLCALL_RANDOM);
        Lesson lesson = lessonService.getById(lessonId);
        List<Student> studentList = stuLessonService.getRandomListByLessonAndAbsense(lesson);
        request.setAttribute("lesson", lesson);
        request.setAttribute("studentList", studentList);
        request.setAttribute("type", type);
        return "rollCall";
    }

    // 保存点名结果
    @Action(value = "setRollCallState")
    public void setRollCallState() {
        int studentId = getIntParameter("id", -1);
        int lessonId = getIntegerParameter("lessonId");
        short toState = (short) getIntParameter("state", Const.ROLLCALL_PRESENT);
        Student student = studentService.getById(studentId);
        Lesson lesson = lessonService.getByVid(lessonId);
        StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
        if (student != null) {
            RollCall rollCall = rollCallService.getLastRollCallByLessonAndStudent(lesson, student);
            short fromState = rollCall.getState();
            rollCall.changeState(fromState, toState);
            rollCallService.update(rollCall);
            stuLesson.changeState(fromState, toState);
            stuLessonService.update(stuLesson);
            writeStringToResponse(rollCall.getId() + "");
        } else {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

    // 修改出席状态
    @Action(value = "resetRollCallState")
    public void resetRollCallState() {
        int studentId = getIntParameter("id", -1);  //用studentId获取最近的一个RollCall
        int lessonId = getIntParameter("lessonId", -1);
        short toState = (short) getIntParameter("state", Const.ROLLCALL_PRESENT);
        Student student = studentService.getById(studentId);
        Lesson lesson = lessonService.getByVid(lessonId);
        RollCall rollCall = rollCallService.getLastRollCallByLessonAndStudent(lesson, student);
        short fromState = rollCall.getState();
        StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
        if (rollCall != null) {
            // 减少原状态的次数，增加新状态的次数
            rollCall.changeState(fromState, toState);
            rollCallService.update(rollCall);
            stuLesson.changeState(fromState, toState);
            stuLessonService.update(stuLesson);
            writeStringToResponse(Const.JSON_SUCCESS);
        } else {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

    // 获得当前登录的master
    public Teacher getLoginTeacher() {
        return (Teacher) session.getAttribute(Const.USER_TEACHER);
    }
}

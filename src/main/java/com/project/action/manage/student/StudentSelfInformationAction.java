package com.project.action.manage.student;

import com.project.base.BaseAction;
import com.project.common.ViewLocation;
import com.project.model.Lesson;
import com.project.model.RollCall;
import com.project.model.StuLesson;
import com.project.model.Student;
import com.project.service.LessonService;
import com.project.service.RollCallService;
import com.project.service.StuLessonService;
import com.project.service.StudentService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by Wu keqiang on 2018/4/21
 **/
@Controller
@ParentPackage("students")
@Namespace("/manage/student")
public class StudentSelfInformationAction extends BaseAction<Object>{

    @Autowired
    StudentService studentService;
    @Autowired
    StuLessonService stuLessonService;
    @Autowired
    LessonService lessonService;
    @Autowired
    RollCallService rollCallService;

    @Action(value = "viewLesson", results = {@Result(name = "viewLesson", location = ViewLocation.View_ROOT + "student/m/viewLesson.jsp")})
    public String viewLesson(){
        Integer studentId = getIntegerParameter("studentId");
        Student student = studentService.getById(studentId);
        List<Lesson> lessonList = stuLessonService.getLessonListByStudent(student);
        request.setAttribute("lessonList", lessonList);
        return "viewLesson";
    }

    @Action(value = "viewLessonDetail")
    public void viewLessonDetail() {
        Integer studentId = getIntegerParameter("studentId");
        Integer lessonId = getIntegerParameter("lessonId");
        Student student = studentService.getById(studentId);
        Lesson lesson = lessonService.getByVid(lessonId);
        StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
        List<RollCall> rollCallList = rollCallService.getListByLessonAndStudent(lesson, student);
        request.setAttribute("stuLesson", stuLesson);
        request.setAttribute("rollCallList", rollCallList);
    }

}

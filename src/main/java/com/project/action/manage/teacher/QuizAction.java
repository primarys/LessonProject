package com.project.action.manage.teacher;

import com.project.base.BaseAction;
import com.project.common.ViewLocation;
import com.project.model.*;
import com.project.service.*;
import com.project.utils.Const;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@ParentPackage("teachers")
@Namespace("/manage/teacher")
public class QuizAction extends BaseAction<RollCall> {

    @Autowired
    LessonService lessonService;
    @Autowired
    StudentService studentService;
    @Autowired
    RollCallService rollCallService;
    @Autowired
    QuizService quizService;
    @Autowired
    StuLessonService stuLessonService;

    // 课堂提问页面
    @Action(value = "quiz", results = @Result(name = "quiz", location = ViewLocation.View_ROOT + "manage/teacher/quiz.jsp"))
    public String quiz() {
        int lessonId = getIntParameter("id", -1);
        Lesson lesson = lessonService.getByVid(lessonId);
        List<Student> studentList = stuLessonService.getRandomListByLessonAndAbsense(lesson);
        request.setAttribute("lesson", lesson);
        request.setAttribute("studentList", studentList);
        return "quiz";
    }

    // 记录课堂提问加分
    @Action(value = "recordAward")
    public void recordAward() {
        int studentId = getIntParameter("id", -1);
        int lessonId = getIntegerParameter("lessonId");
        int score = getIntParameter("score", 0);
        Lesson lesson = lessonService.getByVid(lessonId);
        Student student = studentService.getById(studentId);
        StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
        RollCall rollCall = rollCallService.getLastRollCallByLessonAndStudent(lesson, student);
        switch (rollCall.getState()){
            case 1:break;
            case 2: rollCall.setState(Const.ROLLCALL_LATE); rollCallService.update(rollCall); break;
            case 3:break;
            case 4: rollCall.setState(Const.ROLLCALL_PRESENT); rollCallService.update(rollCall); break;
            case 5: rollCall.setState(Const.ROLLCALL_PRESENT); rollCallService.update(rollCall); break;
        }
        if (student != null) {
            Quiz quiz = quizService.getLastQuizByStudentAndLesson(lesson, student);
            quiz.setAward(quiz.getAward() + score);
            quizService.update(quiz);
            stuLesson.setAward(stuLesson.getAward() + score);
            stuLessonService.update(stuLesson);
            String myreturnValue=String.valueOf(rollCall.getState());
            String rollCallId = String.valueOf(rollCall.getId());
            List retList = new ArrayList<String>();
            retList.add(myreturnValue);
            retList.add(rollCallId);
            retList.add(String.valueOf(score));
            writeJsonToResponse(retList);
        } else {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

    // 修改课堂提问加分
    @Action(value = "modifyAward")
    public void modifyAward() {
        int studentId = getIntParameter("id", -1);
        int lessonId = getIntegerParameter("lessonId");
        int score = getIntParameter("award", 0);
        int scoreDafen = getIntParameter("awardone", 0);
        log.info(score);
        log.info(scoreDafen);
        Lesson lesson = lessonService.getByVid(lessonId);
        Student student = studentService.getById(studentId);
        StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
        RollCall rollCall = rollCallService.getLastRollCallByLessonAndStudent(lesson, student);
        if (student != null) {
            Quiz quiz = quizService.getLastQuizByStudentAndLesson(lesson, student);
            int fromAward = quiz.getAward();
            quiz.setAward(fromAward - scoreDafen + score);
            quizService.update(quiz);
            stuLesson.setAward(stuLesson.getAward() - scoreDafen + score);
            stuLessonService.update(stuLesson);
            String myreturnValue=String.valueOf(rollCall.getState());
            String rollCallId = String.valueOf(rollCall.getId());
            String scoreString = String.valueOf(score);
            List retList = new ArrayList<String>();
            retList.add(myreturnValue);
            retList.add(rollCallId);
            retList.add(scoreString);
            writeJsonToResponse(retList);
        } else {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

    // 获得当前登录的master
    public Teacher getLoginTeacher() {
        return (Teacher) session.getAttribute(Const.USER_TEACHER);
    }
}

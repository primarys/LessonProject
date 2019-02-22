package com.project.action.manage.teacher;

import com.project.base.BaseAction;
import com.project.common.ViewLocation;
import com.project.model.*;
import com.project.service.*;
import com.project.utils.Const;
import com.project.utils.DateUtils;
import com.project.utils.ImageUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;

@Controller
@ParentPackage("teachers")
@Namespace("/manage/teacher")
public class TeacherLessonAction extends BaseAction<Teacher> {

    @Autowired
    LessonService lessonService;
    @Autowired
    StudentService studentService;
    @Autowired
    PicStoreService picStoreService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StuLessonService stuLessonService;
    @Autowired
    RollCallService rollCallService;
    @Autowired
    QuizService quizService;

    // 开始上课
    @Action(value = "classBegin", results = {@Result(name = "classBegin", location = ViewLocation.View_ROOT + "manage/teacher/index.jsp")})
    public String classBegin() {
        final Integer lessonId = getIntegerParameter("lessonId");
        Teacher teacher = teacherService.getById(getLoginTeacher().getId());       // 避免session信息滞后，需要重新获取
        List<Lesson> lessonList = lessonService.getListByTeacher(getLoginTeacher());
        Date now = new Date();
        for (Lesson lesson : lessonList) {
            if (lesson.getId() == lessonId){
                lesson.setTimeStamp(now.getTime());
            }
            else {
                lesson.setTimeStamp(0);
            }
            lessonService.update(lesson);
        }

        /**
         * 创建本节课未点名，创建本节课0分的quiz
         */
        Lesson lesson = lessonService.getByVid(lessonId);
        List<Student> studentList = stuLessonService.getStudentListByLesson(lesson);
        for (Student student : studentList) {
//            List<RollCall> rollCallList = rollCallService.getNotRollCallListByLessonAndStudent(lesson, student);
//            StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
//            /**
//             *  更新rollCall和stuLesson
//             */
//            for (RollCall rollCall : rollCallList) {
//                if (lesson.getNotType() == 0){
//                    rollCall.setState(Const.ROLLCALL_PRESENT);
//                    stuLesson.saveState(Const.ROLLCALL_PRESENT);
//                }
//                else if (lesson.getNotType() == 1){
//                    rollCall.setState(Const.ROLLCALL_ABSENT);
//                    stuLesson.saveState(Const.ROLLCALL_ABSENT);
//                }
//                rollCallService.update(rollCall);
//                stuLessonService.update(stuLesson);
//            }
            /**
             * 重新生成本节课的未点名和0分提问
             */
            RollCall rollCall = new RollCall(student, Const.ROLLCALL_NOT, lesson);
            rollCallService.save(rollCall);
            Quiz quiz = new Quiz(student, 0, lesson);
            quizService.save(quiz);
        }

        /**
         * 两小时之后更新以前未点名
         */
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                rollCallFinished(lessonId);
            }
        };
        timer.schedule(task, Const.CLASS_LAST);

        lesson.setNotType((short)0);
        lessonService.update(lesson);
        request.setAttribute("teacher", teacher);
        request.setAttribute("lessonList", lessonList);
        writeStringToResponse(Const.JSON_SUCCESS);
        return "classBegin";
    }

    /**
     * 更新以前未点名
     */
    public void rollCallFinished(int lessonId){
        Lesson lesson = lessonService.getByVid(lessonId);
        List<Student> studentList = stuLessonService.getStudentListByLesson(lesson);
        for (Student student : studentList) {
            List<RollCall> rollCallList = rollCallService.getNotRollCallListByLessonAndStudent(lesson, student);
            StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
            /**
             *  更新rollCall和stuLesson
             */
            for (RollCall rollCall : rollCallList) {
                if (lesson.getNotType() == 0){
                    rollCall.setState(Const.ROLLCALL_PRESENT);
                    stuLesson.saveState(Const.ROLLCALL_PRESENT);
                }
                else if (lesson.getNotType() == 1){
                    rollCall.setState(Const.ROLLCALL_ABSENT);
                    stuLesson.saveState(Const.ROLLCALL_ABSENT);
                }
                rollCallService.update(rollCall);
                stuLessonService.update(stuLesson);
            }
        }
    }

    // 课堂签到二维码
    @Action(value = "classSign", results = @Result(name = "classSign", location = ViewLocation.View_ROOT + "manage/teacher/classSign.jsp"))
    public String classSign() {
        Integer lessonId = getIntParameter("id",-1);
        Lesson lesson = lessonService.getByVid(lessonId);
        lesson.setNotType((short)1);
        lessonService.update(lesson);
        request.setAttribute("lessonId",lessonId.toString());
        return "classSign";
    }

    // 查看课程详情,加入模糊搜索
    @Action(value = "viewLesson", results = @Result(name = "viewLesson", location = ViewLocation.View_ROOT + "manage/teacher/lesson-info.jsp"))
    public String viewLesson() {
        int lessonId = getIntParameter("id", -1);
        String studentNumOrName = getParameter("studentNumOrName", null);
        Lesson lesson = lessonService.getByVid(lessonId);
        List<StuLesson> stuLessonList = new ArrayList<>();
        // 搜索的字符串是空，返回完整列表
        if (studentNumOrName == null) {
            stuLessonList = stuLessonService.getListByLesson(lesson);
        }
        else if (stuLessonService.getFuzzyStuLessonListByNumOrName(null, studentNumOrName, lesson).size() != 0) {
            stuLessonList = stuLessonService.getFuzzyStuLessonListByNumOrName(null, studentNumOrName, lesson);
        }
        else if (stuLessonService.getFuzzyStuLessonListByNumOrName(studentNumOrName, null, lesson).size() != 0) {
            stuLessonList = stuLessonService.getFuzzyStuLessonListByNumOrName(studentNumOrName, null, lesson);
        }

        List<RollCall> rollCallList = new ArrayList<>();
        List<Quiz> quizList = new ArrayList<>();
        for (StuLesson stuLesson : stuLessonList) {
            rollCallList.add(rollCallService.getLastRollCallByLessonAndStudent(stuLesson.getLesson(), stuLesson.getStudent()));
            //Quiz quiz = quizService.getLastQuizByStudentAndLesson(stuLesson.getLesson(), stuLesson.getStudent());
            quizList.add(quizService.getLastQuizByStudentAndLesson(stuLesson.getLesson(), stuLesson.getStudent()));
        }

        request.setAttribute("lesson", lesson);
        request.setAttribute("rollCallList", rollCallList);
        request.setAttribute("stuLessonList", stuLessonList);
        request.setAttribute("quizList", quizList);

        return "viewLesson";
    }

    // 新建课程页面
    @Action(value = "editLesson", results = @Result(name = "editLesson", location = ViewLocation.View_ROOT + "manage/teacher/lesson-edit.jsp"))
    public String editLesson() {
        int lessonId = getIntParameter("id", -1);
        if (lessonId != -1) {
            Lesson lesson = lessonService.getByVid(lessonId);
            request.setAttribute("lesson", lesson);
        }
        return "editLesson";
    }

    // 保存课程信息
    @Action(value = "saveLesson", results = @Result(name = "timeError", location = ViewLocation.View_ROOT + "manage/teacher/timeError.jsp"))
    public String saveLesson() {
        try {
            int lessonId = getIntParameter("lessonId", -1);                 // -1表示要新建，否则为更新
            int resetCover = getIntParameter("cover", 0);       // 0：不改变封面，1：恢复默认，2：更换为其他封面
            String lessonName = getParameter("name");
            String begin = getParameter("beginTime");
            String end = getParameter("endTime");
            String introduction = getParameter("content");
            Teacher teacher = getLoginTeacher();

            /**
             * 这需要向前台获取
             */
            String classinfo = getParameter("classinfo");
            /**
             *
             */

            Lesson lesson = lessonService.getByVid(lessonId);
            // 判断begin, end大小
            if (begin.compareTo(end) > 0) {
                return "timeError";
            }

            /**
             *
             */

            Date beginTime = DateUtils.parseDate(begin, DateUtils.DEFAULT_YMDHMS_FORMAT);
            Date endTime = DateUtils.parseDate(end, DateUtils.DEFAULT_YMDHMS_FORMAT);
            if (lesson != null) {
                lesson.setTeacher(teacher);
                lesson.setLessonName(lessonName);
                lesson.setBeginTime(beginTime);
                lesson.setEndTime(endTime);
                lesson.setIntroduction(introduction);
                lesson.setClassinfo(classinfo);
                PicStore pic = picStoreService.getDefaultLessonCover();
                if (resetCover == 1 && lesson.getCover() != pic) {
                    ImageUtils.deleteImage(picStoreService, lesson.getCover());
                    lesson.setCover(pic);
                } else if (resetCover == 2) {
                    String path = Const.USER_TEACHER + "\\" + getLoginTeacher().getId() + "\\lesson\\" + lesson.getId() + "\\info";
                    PicStore cover = ImageUtils.uploadImage(request, picStoreService, path,
                            "newCover", "课程" + lesson.getId() + "的封面", lesson.getCover());
                    if (cover != null) {
                        lesson.setCover(cover);
                    }
                }
                lessonService.update(lesson);
            } else {
                lesson = new Lesson(teacher, lessonName, introduction, classinfo, beginTime, endTime);
                String path = Const.USER_TEACHER + "\\" + getLoginTeacher().getId() + "\\lesson\\" + lesson.getId() + "\\info";
                PicStore cover = ImageUtils.uploadImage(request, picStoreService, path,
                        "newCover", "课程" + lesson.getId() + "的封面", lesson.getCover());
                if (cover != null) {
                    lesson.setCover(cover);
                }
                lessonService.save(lesson);
            }
            writeStringToResponse(lesson.getId() + "");
        } catch (Exception e) {
            writeStringToResponse(Const.JSON_FAIL);
        }
        return "saveLesson";
    }

    // 删除课程活动
    @Action(value = "deleteLesson")
    public void deleteLesson() {
        int lessonId = getIntParameter("id", -1);
        Lesson lesson = lessonService.getByVid(lessonId);
        if (lesson != null) {
            lesson.setDelFlag((short)0);
            lessonService.update(lesson);
            writeStringToResponse(Const.JSON_SUCCESS);
        } else {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

    // 获取登录的master
    public Teacher getLoginTeacher() {
        return (Teacher) session.getAttribute(Const.USER_TEACHER);
    }

    // 判断字符串是否全是汉字
    public boolean allChinese(String string) {
        String reg = "[\\u4e00-\\u9fa5]+";
        return string.matches(reg);
    }

    // 判断字符串是否全是数字
    public boolean allFigure(String string) {
        return string.matches("[0-9]+");
    }

}

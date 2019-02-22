package com.project.action.manage.teacher;

import au.com.bytecode.opencsv.CSVReader;
import com.project.base.BaseAction;
import com.project.common.ViewLocation;
import com.project.model.*;
import com.project.service.*;
import com.project.utils.Const;
import com.project.utils.Encode;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@ParentPackage("teachers")
@Namespace("/manage/teacher")
public class TeacherStudentAction extends BaseAction<Teacher> {

    @Autowired
    LessonService lessonService;
    @Autowired
    StudentService studentService;
    @Autowired
    PicStoreService picStoreService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    RollCallService rollCallService;
    @Autowired
    QuizService quizService;
    @Autowired
    StuLessonService stuLessonService;

    // 查看学生详情
    @Action(value = "viewStudent", results = @Result(name = "viewStudent", location = ViewLocation.View_ROOT + "manage/teacher/student-info.jsp"))
    public String viewStudent() {
        int lessonId = getIntParameter("vid", -1);
        int studentId = getIntParameter("cid", -1);
        Lesson lesson = lessonService.getByVid(lessonId);
        Student student = studentService.getById(studentId);
        if (lesson != null) {
            StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
            List<RollCall> rollCallList = rollCallService.getListByLessonAndStudent(lesson, student);
            List<Quiz> quizList = quizService.getListByLessonAndStudent(lesson, student);
            request.setAttribute("lesson", lesson);
            request.setAttribute("rollCallList", rollCallList);
            request.setAttribute("quizList", quizList);
            request.setAttribute("student", student);
        }
        return "viewStudent";
    }
    
    /**
     * 确认修改考勤状态
     * @return
     */
    @Action(value = "modifyAttendance")
    public  void modifyAttendance() {
        Integer rollCallId = getIntegerParameter("rollCallId");
        short toState = getIntegerParameter("state").shortValue();
        RollCall rollCall = rollCallService.getById(rollCallId);
        short fromState = rollCall.getState();
        rollCall.changeState(fromState, toState);
        rollCallService.update(rollCall);
        Student student = rollCall.getStudent();
        Lesson lesson = rollCall.getLesson();
        StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson,student);
        stuLesson.changeState(fromState, toState);
        stuLessonService.update(stuLesson);
        List<Integer> ret = new ArrayList<>();
        ret.add(stuLesson.getPresense());
        ret.add(stuLesson.getLeaves());
        ret.add(stuLesson.getLate());
        ret.add(stuLesson.getAbsense());
        writeJsonToResponse(ret);
        /*writeStringToResponse(Const.JSON_SUCCESS);*/
    }

    /**
     * 确认修改课堂提问状态
     * @return
     */
    @Action(value = "modifyQuiz")
    public void modifyQuiz() {
        Integer quizId = getIntegerParameter("quizId");
        Integer award = getIntegerParameter("award");
        Quiz quiz = quizService.getById(quizId);
        int fromAward = quiz.getAward();
        quiz.changeAward(award);
        quizService.update(quiz);
        Student student = quiz.getStudent();
        Lesson lesson = quiz.getLesson();
        StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
        int toAward = award;
        stuLesson.changeAward(fromAward,toAward);
        stuLessonService.update(stuLesson);
        writeStringToResponse(String.valueOf(stuLesson.getAward()));
    }

    // 编辑学生页面
    @Action(value = "editStudent", results = @Result(name = "editStudent", location = ViewLocation.View_ROOT + "manage/teacher/student-edit.jsp"))
    public String editStudent() {
        int lessonId = getIntParameter("vid", -1);
        int studentId = getIntParameter("cid", -1);
        Lesson lesson = lessonService.getByVid(lessonId);
        Student student = studentService.getById(studentId);
        request.setAttribute("lesson", lesson);
        request.setAttribute("student", student);
        return "editStudent";
    }

    // 批量增加学生页面
    @Action(value = "editStudents", results = @Result(name = "editStudents", location = ViewLocation.View_ROOT + "manage/teacher/students-edit.jsp"))
    public String editStudents() {
        int lessonId = getIntParameter("vid", -1);
        Lesson lesson = lessonService.getByVid(lessonId);
        request.setAttribute("lesson", lesson);
        return "editStudents";
    }

    // 增加学生
    @Action(value = "saveStudent")
    public void saveStudent() {
        int lessonId = getIntParameter("vid", -1);
        String studentNumber = getParameter("studentNumber");
        String studentName = getParameter("studentName");
        Lesson lesson = lessonService.getByVid(lessonId);
        Student student = studentService.getByNameAndNumber(studentName, studentNumber);
        if (student == null) {
            student = new Student(studentNumber, studentName);
            studentService.save(student);
        }
        if (stuLessonService.getByStudentAndLesson(student, lesson) != null) {
            writeStringToResponse("error");
            return;
        }
        StuLesson stuLesson = new StuLesson(student, lesson);
        stuLessonService.save(stuLesson);

        // 如果现在正在上课，增加相应的未点名rollcall和quiz
        if (lesson.getTimeStamp() != 0) {
            RollCall rollCall = new RollCall(student, Const.ROLLCALL_NOT, lesson);
            rollCallService.save(rollCall);
            Quiz quiz = new Quiz(student, 0, lesson);
            quizService.save(quiz);
        }
    }

    // 批量保存学生
    @Action(value = "saveStudents")
    public void saveStudents() {
        int lessonId = getIntParameter("vid", -1);
        Lesson lesson = lessonService.getByVid(lessonId);
        if (lesson != null) {
            try {
                String[] nums = request.getParameterValues("num");
                String[] names = request.getParameterValues("name");
                for (int i = 0 ; i < nums.length; i++) {
                    Student student = studentService.getByNameAndNumber(names[i], nums[i]);
                    if (student == null) {
                        student = new Student(nums[i], names[i]);
                        studentService.save(student);
                    }
                    if (stuLessonService.getByStudentAndLesson(student, lesson) != null) {
                        writeStringToResponse("error");
                        return;
                    }
                    StuLesson stuLesson = new StuLesson(student, lesson);
                    stuLessonService.save(stuLesson);

                    // 如果现在正在上课，增加相应的未点名rollCall和quiz
                    if (lesson.getTimeStamp() != 0) {
                        RollCall rollCall = new RollCall(student, Const.ROLLCALL_NOT, lesson);
                        rollCallService.save(rollCall);
                        Quiz quiz = new Quiz(student, 0, lesson);
                        quizService.save(quiz);
                    }
                }
                writeStringToResponse(Const.JSON_SUCCESS);
            } catch (Exception e) {
                writeStringToResponse(Const.JSON_FAIL);
            }
        } else {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }


/*
    // 批量保存学生
    @Action(value = "saveStudents")
    public void saveStudents () {
        Integer lessonId = getIntegerParameter("lessonId");
        Lesson lesson = lessonService.getByVid(lessonId);
        String filePath = getParameter("filePath");

        String charseName = "utf-8";
        DataInputStream in = null;
        try {
            in = new DataInputStream(new FileInputStream(new File(filePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new InputStreamReader(in,charseName),',', '\'', 1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String[]> list = null;
        try {
            list = csvReader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String[] ss : list){
            if (ss.length != 2) {
                log.info("上传的csv文件内部格式不正确!");
                writeStringToResponse("error");
                return;
            }
            String studentNumber = ss[0];
            String studentName = ss[1];
            if (Encode.isMessyCode(studentName)){
                charseName = "gbk";
                log.info("utf-8解码为乱码!");
                break;
            }

            //如果学生不存在，则添加学生
            Student student = studentService.getByNameAndNumber(studentName, studentNumber);
            if (student == null) {
                student = new Student(studentNumber, studentName);
                studentService.save(student);
            }
            StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
            if (stuLesson != null) {
                log.info("有学生已经加入此课程!");
                writeStringToResponse("error");
                return;
            }
            stuLesson = new StuLesson(student, lesson);
            stuLessonService.save(stuLesson);
        }

        // 如果utf-8乱码，则使用gbk解码
        if (charseName.equals("gbk")) {
            try {
                in = new DataInputStream(new FileInputStream(new File(filePath)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                csvReader = new CSVReader(new InputStreamReader(in,charseName),',', '\'', 1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                list = csvReader.readAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(String[] ss : list){
                String studentNumber = ss[0];
                String studentName = ss[1];

                //如果学生不存在，则添加学生
                Student student = studentService.getByNameAndNumber(studentName, studentNumber);
                if (student == null) {
                    student = new Student(studentNumber, studentName);
                    studentService.save(student);
                }
                StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
                if (stuLesson != null) {
                    log.info("有学生已经加入此课程!");
                    writeStringToResponse("error");
                    return;
                }
                stuLesson = new StuLesson(student, lesson);
                stuLessonService.save(stuLesson);
            }
        }
        try {
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

*/

    // 删除学生
    @Action(value = "deleteStudent")
    public void deleteStudent() {
        int lessonId = getIntParameter("vid", -1);
        int studentId = getIntParameter("cid", -1);
        Lesson lesson = lessonService.getByVid(lessonId);
        Student student = studentService.getById(studentId);
        StuLesson stuLesson = stuLessonService.getByLessonAndStudent(lesson, student);
        if (stuLesson != null) {
            stuLesson.setDelFlag((short)0);
            stuLessonService.update(stuLesson);
            writeStringToResponse(Const.JSON_SUCCESS);
        } else {
            writeStringToResponse(Const.JSON_FAIL);
        }
    }

    // 获取登录的master
    public Teacher getLoginTeacher() {
        return (Teacher) session.getAttribute(Const.USER_TEACHER);
    }

}

package com.project.service;

import com.project.base.BaseService;
import com.project.model.Lesson;
import com.project.model.StuLesson;
import com.project.model.Student;

import java.util.List;

public interface StuLessonService extends BaseService<StuLesson>{
    // 根据 lesson 获取student
    public List<StuLesson> getListByLesson(Lesson lesson);
    // 按照概率获取随机点名名单
    public List<Student> getRandomListByLessonAndAbsense(Lesson lesson);
    // 根据 lesson 和 id 来获取student
    public StuLesson getByLessonAndStudent(Lesson lesson, Student student);
    //根据lesson获取学生列表
    public List<Student> getStudentListByLesson(Lesson lesson);
    //根据student获取stulesson列表
    public List<StuLesson> getStuLessonListByStudent(Student student);
    //根据student获取lesson列表
    public List<Lesson> getLessonListByStudent(Student student);
    //模糊查找
    public List<StuLesson> getFuzzyStuLessonListByNumOrName(String studentNum, String studentName, Lesson lesson);
    // 根据student和lesson查找
    public StuLesson getByStudentAndLesson(Student student, Lesson lesson);
}

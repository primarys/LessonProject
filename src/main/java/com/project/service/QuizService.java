package com.project.service;

import com.project.base.BaseService;
import com.project.model.Lesson;
import com.project.model.Quiz;
import com.project.model.Student;

import java.util.List;

public interface QuizService extends BaseService<Quiz> {
    // 根据学生获取课堂提问情况列表
    public List<Quiz> getListByStudent(Student student);
    // 根据lesson获取课堂提问情况表
    public List<Quiz> getListByLesson(Lesson lesson);
    //根据lesson和student获取提问列表
    public List<Quiz> getListByLessonAndStudent(Lesson lesson, Student student);
    //根据学生和课程获取最近的一条Quzi
    public Quiz getLastQuizByStudentAndLesson(Lesson lesson, Student student);
}

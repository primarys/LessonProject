package com.project.service;

import com.project.base.BaseService;
import com.project.model.Lesson;
import com.project.model.Teacher;

import java.util.List;

public interface LessonService extends BaseService<Lesson> {
    // 根据teacher获得课程
    public List<Lesson> getListByTeacher(Teacher teacher);
    // lesson id获得课程
    public Lesson getByVid(int id);
    //根据课程名称获得课程
    public List<Lesson> getByLessonName(String lessonName);
}

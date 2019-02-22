package com.project.service;

import com.project.base.BaseService;
import com.project.model.Lesson;
import com.project.model.RollCall;
import com.project.model.Student;

import java.util.List;

public interface RollCallService extends BaseService<RollCall> {
    // 根据Id获取点名信息
    public RollCall getById(int id);
    // 根据学生获取点名情况列表
    public List<RollCall> getListByStudent(Student student);
    //根据出席情况（出席、缺席、补签/迟到、请假）获取点名情况列表
    public List<RollCall> getListByState(short state);
    //根据课程获取点名情况列表
    public List<RollCall> getListByLesson(Lesson lesson);
    //根据课程和学生获取点名情况列表
    public List<RollCall> getListByLessonAndStudent(Lesson lesson, Student student);
    //根据课程和学生获取最近的一条点名
    public RollCall getLastRollCallByLessonAndStudent(Lesson lesson, Student student);
    //根据student和lesson获取未点名RollCallList
    public List<RollCall> getNotRollCallListByLessonAndStudent(Lesson lesson, Student student);
}

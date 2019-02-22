package com.project.model;

import com.project.base.BaseModel;

import java.util.Date;

/**
 * 课程点名
 * @author Dobility
 */
public class RollCall  extends BaseModel {
    // 与学生的对应关系，
    private Student student;
    // 出席情况（出席、缺席、补签/迟到、请假、未点名）
    private short state;
    // 点名时间
    private Date createTime;
    //lessonId
    private Lesson lesson;

    public RollCall() { super(); }

    public RollCall(Student student, short state, Lesson lesson) {
        super();
        this.student = student;
        this.state = state;
        this.lesson = lesson;
        this.createTime = new Date();
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void changeState(short fromState, short toState) {
        this.state = toState;
        this.createTime = new Date();
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
        //this.setCreateTime(new Date());
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

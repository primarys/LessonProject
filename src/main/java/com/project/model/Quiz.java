package com.project.model;

import com.project.base.BaseModel;

import java.util.Date;

/**
 * 课程提问
 * @author Dobility
 */
public class Quiz extends BaseModel {
    // 与学生的对应关系，学生中已经包含Lesson，Lesson包含Teacher
    private Student student;
    // 回答加分（可以是负数）
    private int award;
    // 提问时间
    private Date createTime;
    //对应的lesson
    private Lesson lesson;

    public Quiz() { super(); }

    public Quiz(Student student, int award, Lesson lesson) {
        super();
        this.student = student;
        this.award = award;
        this.createTime = new Date();
        this.lesson = lesson;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void changeAward(int newAwad) {
        this.award = newAwad;
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

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

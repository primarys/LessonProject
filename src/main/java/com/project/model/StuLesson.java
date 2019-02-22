package com.project.model;

import com.project.base.BaseModel;
import com.project.utils.Const;

import java.io.Serializable;

/**
 * 学生选课表
 */
public class StuLesson extends BaseModel implements Serializable {
    //学生类
    private Student student;
    //课程类
    private Lesson lesson;
    // 出席次数
    private int presense;
    // 请假次数
    private int leaves;
    // 迟到次数
    private int late;
    // 缺席次数
    private int absense;
    // 回答加分
    private int award;
    //平时分总分
    private int score;

    //计算平时分的公式
    private void calculateScore() {
        if (this.leaves <= 3) this.score = Const.SCORE - 3*this.late - 5*this.absense + this.award;
        else this.score = Const.SCORE - 5*(this.leaves - 3) - 3*this.late - 5*this.absense + this.award;
    }

    public void saveState(short state){
        switch (state) {
            case Const.ROLLCALL_PRESENT : this.setPresense(this.getPresense() + 1);break;
            case Const.ROLLCALL_LEAVE : this.setLeaves(this.getLeaves() + 1);break;
            case Const.ROLLCALL_LATE : this.setLate(this.getLate() + 1);break;
            case Const.ROLLCALL_ABSENT : this.setAbsense(this.getAbsense() + 1);break;
        }
        this.calculateScore();
    }

    public void changeState(short fromState, short toState){
        switch (fromState) {
            case Const.ROLLCALL_PRESENT : this.setPresense(this.getPresense() - 1);break;
            case Const.ROLLCALL_LEAVE : this.setLeaves(this.getLeaves() - 1);break;
            case Const.ROLLCALL_LATE : this.setLate(this.getLate() - 1);break;
            case Const.ROLLCALL_ABSENT : this.setAbsense(this.getAbsense() - 1);break;
        }
        switch (toState) {
            case Const.ROLLCALL_PRESENT : this.setPresense(this.getPresense() + 1);break;
            case Const.ROLLCALL_LEAVE : this.setLeaves(this.getLeaves() + 1);break;
            case Const.ROLLCALL_LATE : this.setLate(this.getLate() + 1);break;
            case Const.ROLLCALL_ABSENT : this.setAbsense(this.getAbsense() + 1);break;
        }
        this.calculateScore();
    }

    public void changeAward(int fromAward, int toAward) {
        this.award = this.award - fromAward + toAward;
        this.calculateScore();
    }

    public StuLesson() {
        super();
        this.score = Const.SCORE;
    }

    public StuLesson(Student student, Lesson lesson) {
        this.student = student;
        this.lesson = lesson;
        this.score = Const.SCORE;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public int getPresense() {
        return presense;
    }

    public void setPresense(int presense) {
        this.presense = presense;
        this.calculateScore();
    }

    public int getLeaves() {
        return leaves;
    }

    public void setLeaves(int leaves) {
        this.leaves = leaves;
        this.calculateScore();
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
        this.calculateScore();
    }

    public int getAbsense() {
        return absense;
    }

    public void setAbsense(int absense) {
        this.absense = absense;
        this.calculateScore();
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
        this.calculateScore();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}

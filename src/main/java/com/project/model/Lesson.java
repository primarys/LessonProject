package com.project.model;

import com.project.base.BaseModel;
import com.project.utils.Const;
import com.project.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程
 * @author Dobility
 */
public class Lesson extends BaseModel implements Serializable {
    //教师类
    private Teacher teacher;
    // 课程名称
    private String lessonName;
    //课程简介
    private String introduction;
    // 上课时间,地点
    private String classinfo;
    // 开始时间
    private Date beginTime;
    // 结束时间
    private Date endTime;
    // 封面
    private PicStore cover;
    // 这节课开始上课时间的时间戳
    private long timeStamp;
    // 这节课未点名的处理方式，0代表默认出席，1代表默认缺席
    private short notType;

    public Lesson() {
        super();
    }

    public Lesson(Teacher teacher, String lessonName, String introduction, String classinfo, Date beginTime, Date endTime) {
        this.teacher = teacher;
        this.lessonName = lessonName;
        this.introduction = introduction;
        this.classinfo = classinfo;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public PicStore getCover() {
        return cover;
    }

    public void setCover(PicStore cover) {
        this.cover = cover;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getClassinfo() {
        return classinfo;
    }

    public void setClassinfo(String classinfo) {
        this.classinfo = classinfo;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public short getNotType() {
        return notType;
    }

    public void setNotType(short notType) {
        this.notType = notType;
    }
}

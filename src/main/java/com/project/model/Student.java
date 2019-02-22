package com.project.model;

import com.project.base.BaseModel;
import com.project.utils.Const;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程学生
 * @author Dobility
 */
public class Student extends BaseModel implements Serializable {
    // 密码
    private String password;
    // 微信openId
    private String weichat_openid;
    // 微信昵称
    private String nickname;
    //头像
    private PicStore headPortrait;
    // 学号
    private String studentNum;
    // 姓名
    private String name;
    // 性别
    private short sex;
    //手机号
    private String phoneNumber;

    public Student() { super(); }

    public Student(String studentNum, String name, short sex) {
        this.studentNum = studentNum;
        this.name = name;
        this.sex = sex;
    }

    public Student(String studentNum, String name) {
        this.studentNum = studentNum;
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWeichat_openid() {
        return weichat_openid;
    }

    public void setWeichat_openid(String weichat_openid) {
        this.weichat_openid = weichat_openid;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public PicStore getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(PicStore headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

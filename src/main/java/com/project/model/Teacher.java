package com.project.model;

import com.project.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 教师，可以管理课程，学生
 * @author Dobility
 */
public class Teacher extends BaseModel implements Serializable {
    // 头像
    private PicStore headPortrait;
    // 显示的名字
    private String teacherName;
    //性别
    private short sex;
    //手机号码
    private String phoneNum;
    //登录名
    private String userName;
    //密码
    private String password;
    //邮箱
    private String email;
    //微信openId
    private String weixinOpenId;

    public Teacher() { super(); }

    public Teacher(String teacherName,String userName, String phoneNum, String password, String email) {
        this.teacherName = teacherName;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.password = password;
        this.email = email;
    }
    public Teacher(String teacherName, String phoneNum, String password, String email) {
        this.teacherName = teacherName;
        this.phoneNum = phoneNum;
        this.password = password;
        this.email = email;
    }
    public Teacher(String phoneNum,String password) {
        this.phoneNum = phoneNum;
        this.password = password;
    }

    public Teacher(String teacherName, short sex, String phoneNum, String email, String weixinOpenId) {
        this.teacherName = teacherName;
        this.sex = sex;
        this.phoneNum = phoneNum;
        this.email = email;
        this.weixinOpenId = weixinOpenId;
    }

    public void teacherTrim(){
        this.password = "";
        this.weixinOpenId = "";
    }

    public String getWeixinOpenId() {
        return weixinOpenId;
    }

    public void setWeixinOpenId(String weixinOpenId) {
        this.weixinOpenId = weixinOpenId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PicStore getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(PicStore headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

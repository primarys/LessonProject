package com.project.service;

import com.project.base.BaseService;
import com.project.model.Lesson;
import com.project.model.Student;

import java.util.List;

public interface StudentService extends BaseService<Student>{
    // 根据 微信openId
    public Student getByWechatId(String openId);
    // 根据 学生姓名和学号查找学生
    public Student getByNameAndNumber(String name, String studentNum);
    // 判断手机账户
    public Student checkLoginFromPhoneNum(String phoneNum, String password);
}

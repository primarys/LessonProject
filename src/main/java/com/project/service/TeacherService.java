package com.project.service;

import com.project.base.BaseService;
import com.project.model.Teacher;

import java.util.List;

public interface TeacherService extends BaseService<Teacher> {
    // 判断登录账户
    public Teacher checkLoginFromUsername(String userName, String password);
    // 判断手机账户
    public Teacher checkLoginFromPhoneNum(String phoneNum, String password);
    // 获取所有信息
    public Teacher getById(int id);
    // 获取所有teacher
    public List<Teacher> getAllTeacher();
    // 根据用户名获得teacher
    public Teacher getByUsername(String userName);
    // 获取所有用户名
    public List<String> getAllUserName();
    // 根据微信openId获取老师
    public Teacher getByWeixinOpenId(String weixinOpenId);
    // 根据手机号码查找老师
    public Teacher getByPhoneNumber(String phoneNumber);
}

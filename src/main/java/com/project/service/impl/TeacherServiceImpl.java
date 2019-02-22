package com.project.service.impl;

import com.project.base.BaseDaoImpl;
import com.project.base.BaseServiceImpl;
import com.project.model.Teacher;
import com.project.service.TeacherService;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class TeacherServiceImpl extends BaseServiceImpl<Teacher> implements TeacherService {

    @Resource(name = "teacherDaoImpl")
    public void setBaseDao(BaseDaoImpl<Teacher> baseDao) {
        super.setBaseDao(baseDao);
    }

    // 判断登录账户
    public Teacher checkLoginFromUsername(String userName, String password) {
        String hql = "FROM Teacher m LEFT JOIN FETCH m.headPortrait " +
                "WHERE m.userName=:userName AND m.password=:password AND m.delFlag=1";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", userName);
        map.put("password", password);
        return findUniqueByHql(hql, map);
    }

    public Teacher checkLoginFromPhoneNum(String phoneNum, String password) {
        String hql = "FROM Teacher m LEFT JOIN FETCH m.headPortrait " +
                "WHERE m.phoneNum=:phoneNum AND m.password=:password AND m.delFlag=1";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phoneNum", phoneNum);
        map.put("password", password);
        return findUniqueByHql(hql, map);
    }

    // 获取所有信息
    public Teacher getById(int id) {
        String hql = "FROM Teacher m LEFT JOIN FETCH m.headPortrait WHERE m.id=:id AND m.delFlag=1";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        return findUniqueByHql(hql, map);
    }

    // 获取所有teacher
    public List<Teacher> getAllTeacher() {
        String hql = "FROM Teacher m LEFT JOIN FETCH m.headPortrait WHERE m.delFlag=1";
        return findByHql(hql, new HashMap<String, Object>(), null, "m.id ASC");
    }

    // 根据用户名获得teacher
    public Teacher getByUsername(String userName) {
        String hql = "FROM Teacher WHERE userName=:userName";
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        return findUniqueByHql(hql, map);
    }

    @Override
    public List<String> getAllUserName() {
        String hql = "FROM Teacher t WHERE t.delFlag=1";
        List<Teacher> teacherList = findByHql(hql);
        List<String> teacherNameList = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            teacherNameList.add(teacher.getUserName());
        }
        return teacherNameList;
    }

    // 根据微信openId获取老师
    public Teacher getByWeixinOpenId(String weixinOpenId) {
        String hql = "FROM Teacher t WHERE t.weixinOpenId=:weixinOpenId AND t.delFlag=1";
        Map<String,Object> map = new HashMap<>();
        map.put("weixinOpenId", weixinOpenId);
        return findUniqueByHql(hql, map);
    }

    // 根据手机号码查找老师
    public Teacher getByPhoneNumber(String phoneNumber) {
        String hql = "FROM Teacher t WHERE t.phoneNum=:phoneNumber AND t.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNumber", phoneNumber);
        return findUniqueByHql(hql, map);
    }

}
package com.project.service.impl;

import com.project.base.BaseDaoImpl;
import com.project.base.BaseServiceImpl;
import com.project.model.*;
import com.project.service.LessonService;
import com.project.service.StudentService;
import com.project.utils.Const;
import com.project.utils.DataUtils;
import com.project.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {

    @Resource(name = "studentDaoImpl")
    public void setBaseDao(BaseDaoImpl<Student> baseDao) {
        super.setBaseDao(baseDao);
    }

    // 根据 微信openId
    public Student getByWechatId(String openId) {
        String hql = "FROM Student WHERE weichat_openid=:openId AND delFlag=1";
        return findUniqueByHql(hql, DataUtils.getMap("openId", openId));
    }

    // 根据 学生姓名和学号查找学生
    @Override
    public Student getByNameAndNumber(String name, String studentNum) {
        String hql = "FROM Student WHERE name=:name AND studentNum=:studentNum AND delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("studentNum",studentNum);
        return findUniqueByHql(hql,map);
    }

    public Student checkLoginFromPhoneNum(String phoneNum, String password) {
        String hql = "FROM Student s LEFT JOIN FETCH s.headPortrait" +
                "WHERE s.phoneNumber=:phoneNum AND m.password=:password AND m.delFlag=1";
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("phoneNum",phoneNum);
        map.put("password",password);
        return findUniqueByHql(hql,map);
    }
}
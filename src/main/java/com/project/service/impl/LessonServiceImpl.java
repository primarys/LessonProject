package com.project.service.impl;

import com.project.base.BaseDaoImpl;
import com.project.base.BaseServiceImpl;
import com.project.model.Lesson;
import com.project.model.StuLesson;
import com.project.model.Teacher;
import com.project.service.LessonService;
import com.project.utils.Const;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class LessonServiceImpl extends BaseServiceImpl<Lesson> implements LessonService {

    @Resource(name = "lessonDaoImpl")
    public void setBaseDao(BaseDaoImpl<Lesson> baseDao) {
        super.setBaseDao(baseDao);
    }

    // 根据teacher获得课程
    public List<Lesson> getListByTeacher(Teacher teacher) {
        String hql = "FROM Lesson v LEFT JOIN FETCH v.teacher WHERE v.teacher=:teacher AND v.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("teacher", teacher);
        return findByHql(hql, map, null, "v.beginTime asc");
    }

    // lesson id获得课程
    public Lesson getByVid(int id) {
        String hql = "FROM Lesson v LEFT JOIN FETCH v.teacher WHERE v.id=:id AND v.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return findUniqueByHql(hql, map);
    }


    //根据课程名称获得课程
   public List<Lesson> getByLessonName(String lessonName) {
        String hql = "FROM Lesson v LEFT JOIN FETCH v.teacher WHERE v.lessonName=:lessonName AND v.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("lessonName", lessonName);
        return findByHql(hql, map, null, "v.beginTime DESC");
   }

}
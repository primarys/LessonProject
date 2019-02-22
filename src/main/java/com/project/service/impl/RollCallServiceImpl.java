package com.project.service.impl;

import com.project.base.BaseDaoImpl;
import com.project.base.BaseServiceImpl;
import com.project.model.Lesson;
import com.project.model.RollCall;
import com.project.model.Student;
import com.project.model.Teacher;
import com.project.service.RollCallService;
import com.project.utils.DataUtils;
import freemarker.ext.beans.HashAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RollCallServiceImpl extends BaseServiceImpl<RollCall> implements RollCallService {

    @Resource(name = "rollCallDaoImpl")
    public void setBaseDao(BaseDaoImpl<RollCall> baseDao) {
        super.setBaseDao(baseDao);
    }

    // 根据Id获取点名列表
    public RollCall getById(int id) {
        String hql = "FROM RollCall r LEFT JOIN FETCH r.student LEFT JOIN FETCH r.lesson WHERE r.id=:id AND r.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return findUniqueByHql(hql, map);
    }

    // 根据学生获取点名情况列表
    public List<RollCall> getListByStudent(Student student) {
        String hql = "FROM RollCall r LEFT JOIN FETCH r.student LEFT JOIN FETCH r.lesson WHERE r.student=:student AND r.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("student",student);
        return findByHql(hql, map, null, "r.createTime DESC");
    }

    @Override
    public List<RollCall> getListByState(short state) {
        String hql = "FROM RollCall r LEFT JOIN FETCH r.student LEFT JOIN FETCH r.lesson WHERE r.state=:state AND r.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("state",state);
        return findByHql(hql, map, null, "r.createTime DESC");
    }

    @Override
    public List<RollCall> getListByLesson(Lesson lesson) {
        String hql = "FROM RollCall r LEFT JOIN FETCH r.student LEFT JOIN FETCH r.lesson WHERE r.lesson=:lesson AND r.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("lesson",lesson);
        return findByHql(hql, map, null, "r.createTime DESC");
    }

    @Override
    public List<RollCall> getListByLessonAndStudent(Lesson lesson, Student student) {
        String hql = "FROM RollCall r LEFT JOIN FETCH r.student LEFT JOIN FETCH r.lesson WHERE r.lesson=:lesson AND r.student=:student AND r.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("lesson",lesson);
        map.put("student",student);
        return findByHql(hql, map, null, "r.createTime DESC");
    }

    //根据课程和学生获取最近的一条点名
    public RollCall getLastRollCallByLessonAndStudent(Lesson lesson, Student student) {
        List<RollCall> rollCallList = this.getListByLessonAndStudent(lesson, student);
        if (rollCallList.size() == 0) return null;
        else return rollCallList.get(0);
    }

    //根据student和lesson获取未点名RollCallList
    public List<RollCall> getNotRollCallListByLessonAndStudent(Lesson lesson, Student student) {
        String hql = "FROM RollCall r LEFT JOIN FETCH r.student LEFT JOIN FETCH r.lesson WHERE r.lesson=:lesson AND r.student=:student AND r.state=5 AND r.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("lesson", lesson);
        map.put("student", student);
        return findByHql(hql, map, null, "r.createTime DESC");
    }

}
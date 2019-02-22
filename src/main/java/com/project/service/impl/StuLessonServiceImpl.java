package com.project.service.impl;

import com.project.base.BaseDaoImpl;
import com.project.base.BaseServiceImpl;
import com.project.model.Lesson;
import com.project.model.StuLesson;
import com.project.model.Student;
import com.project.service.StuLessonService;
import com.project.service.StudentService;
import com.project.utils.DataUtils;
import com.project.utils.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StuLessonServiceImpl extends BaseServiceImpl<StuLesson> implements StuLessonService {

    @Resource(name = "stuLessonDaoImpl")
    public void setBaseDao(BaseDaoImpl<StuLesson> baseDao) {
        super.setBaseDao(baseDao);
    }

    // 根据 lesson 获取stuLesson
    public List<StuLesson> getListByLesson(Lesson lesson) {
        String hql = "FROM StuLesson s LEFT JOIN FETCH s.student LEFT JOIN FETCH s.lesson WHERE s.lesson=:lesson AND s.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("lesson", lesson);
        return findByHql(hql, map, null, null);
    }

    //根据lesson获取学生列表
    public List<Student> getStudentListByLesson(Lesson lesson) {
        List<StuLesson> stuLessonList = this.getListByLesson(lesson);
        List<Student> studentList = new ArrayList<>();
        for (StuLesson stuLesson : stuLessonList) {
            studentList.add(stuLesson.getStudent());
        }
        return studentList;
    }

    // 按照概率获取随机点名名单
    public List<Student> getRandomListByLessonAndAbsense(Lesson lesson) {
        List<StuLesson> stuLessonList = getListByLesson(lesson);
        List<Student> randList = new ArrayList<>();
        List<Integer> indexs = new ArrayList<>();
        int latePlus = 3;        // 迟到次数概率的放大比率
        int absensePlus = 5;     // 缺席次数概率的放大比率
        for (int i = 0; i < stuLessonList.size(); i++) {
            for (int j = 0; j < (stuLessonList.get(i).getAbsense() * absensePlus + stuLessonList.get(i).getLate() * latePlus) + 1; j++) {
                indexs.add(i);
            }
        }
        int[] randArr = RandomUtils.getRandomArray(0, indexs.size() - 1);
        for (int i : randArr) {
            int index = indexs.get(i);
            if (stuLessonList.get(index) != null) {
                randList.add(stuLessonList.get(index).getStudent());
                stuLessonList.set(index, null);
            }
        }
        return randList;
    }

    // 根据 lesson 和 student 来获取stuLesson
    public StuLesson getByLessonAndStudent(Lesson lesson, Student student) {
        String hql = "FROM StuLesson s LEFT JOIN FETCH s.student LEFT JOIN FETCH s.lesson WHERE s.lesson=:lesson AND s.student=:student AND s.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("lesson", lesson);
        map.put("student", student);
        return findUniqueByHql(hql, map);
    }

    //根据student获取stulesson列表
    public List<StuLesson> getStuLessonListByStudent(Student student) {
        String hql = "FROM StuLesson s LEFT JOIN FETCH s.student LEFT JOIN FETCH s.lesson WHERE s.student=:student AND s.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("student", student);

        return findByHql(hql, map, null, null);
    }

    //根据student获取lesson列表
    public List<Lesson> getLessonListByStudent(Student student) {
        List<StuLesson> stuLessonList = this.getStuLessonListByStudent(student);
        List<Lesson> lessonList = new ArrayList<>();
        for(StuLesson stuLesson : stuLessonList) {
            lessonList.add(stuLesson.getLesson());
        }
        return lessonList;
    }

    //模糊查找
    public List<StuLesson> getFuzzyStuLessonListByNumOrName(String studentNum, String studentName, Lesson lesson) {
        if (studentName == null && studentNum == null) {
            return null;
        }
        String hql;
        Map<String, Object> map = new HashMap<>();
        map.put("lesson", lesson);
        if (studentName == null) {
            hql = "FROM StuLesson sl LEFT JOIN FETCH sl.student LEFT JOIN FETCH sl.lesson WHERE sl.student.studentNum like '%" + studentNum + "%' AND sl.lesson=:lesson AND sl.delFlag=1";
            return findByHql(hql, map);
        }
        hql = "FROM StuLesson sl LEFT JOIN FETCH sl.student LEFT JOIN FETCH sl.lesson WHERE sl.student.name like '%" + studentName + "%' AND sl.lesson=:lesson AND sl.delFlag=1";
        return findByHql(hql, map);
    }

    // 根据student和lesson查找
    public StuLesson getByStudentAndLesson(Student student, Lesson lesson) {
        String hql = "FROM StuLesson s LEFT JOIN FETCH s.student LEFT JOIN FETCH s.lesson WHERE s.student=:student AND s.lesson=:lesson AND s.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("student", student);
        map.put("lesson", lesson);
        return findUniqueByHql(hql, map);
    }

}
package com.project.service.impl;

import com.project.base.BaseDaoImpl;
import com.project.base.BaseServiceImpl;
import com.project.model.Lesson;
import com.project.model.Quiz;
import com.project.model.RollCall;
import com.project.model.Student;
import com.project.service.QuizService;
import com.project.utils.DataUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class QuizServiceImpl extends BaseServiceImpl<Quiz> implements QuizService {

    @Resource(name = "quizDaoImpl")
    public void setBaseDao(BaseDaoImpl<Quiz> baseDao) {
        super.setBaseDao(baseDao);
    }

    // 根据学生获取课堂提问情况列表
    public List<Quiz> getListByStudent(Student student) {
        String hql = "FROM Quiz q LEFT JOIN FETCH q.student LEFT JOIN FETCH q.lesson WHERE q.student=:student AND q.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("student", student);
        return findByHql(hql, map, null, "q.createTime DESC");
    }

    @Override
    public List<Quiz> getListByLesson(Lesson lesson) {
        String hql = "FROM Quzi q LEFT FETCH q.student LEFT JOIN FETCH q.lesson WHERE q.lesson=:lesson AND q.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("lesson", lesson);
        return findByHql(hql, map, null, "q.createTime DESC");
    }

    public List<Quiz> getListByLessonAndStudent(Lesson lesson, Student student) {
        String hql = "FROM Quiz q LEFT JOIN FETCH q.student LEFT JOIN FETCH q.lesson WHERE q.lesson=:lesson AND q.student=:student AND q.delFlag=1";
        Map<String, Object> map = new HashMap<>();
        map.put("lesson",lesson);
        map.put("student",student);
        return findByHql(hql, map, null, "q.createTime DESC");
    }

    //根据学生和课程获取最近的一条Quzi
    public Quiz getLastQuizByStudentAndLesson(Lesson lesson, Student student) {
        List<Quiz> quizList = this.getListByLessonAndStudent(lesson, student);
        if (quizList.size() == 0) return null;
        else  return quizList.get(0);
    }
}
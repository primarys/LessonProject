package com.project.dao.impl;

import com.project.base.BaseDaoImpl;
import com.project.dao.LessonDao;
import com.project.model.Lesson;
import org.springframework.stereotype.Repository;

@Repository("lessonDaoImpl")
public class LessonDaoImpl extends BaseDaoImpl<Lesson> implements LessonDao{

}

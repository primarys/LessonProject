package com.project.dao.impl;

import com.project.base.BaseDaoImpl;
import com.project.dao.StuLessonDao;
import com.project.dao.StudentDao;
import com.project.model.StuLesson;
import com.project.model.Student;
import org.springframework.stereotype.Repository;

@Repository("stuLessonDaoImpl")
public class StuLessonDaoImpl extends BaseDaoImpl<StuLesson> implements StuLessonDao {

}

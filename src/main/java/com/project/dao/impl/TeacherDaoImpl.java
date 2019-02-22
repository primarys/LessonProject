package com.project.dao.impl;

import com.project.base.BaseDaoImpl;
import com.project.dao.TeacherDao;
import com.project.model.Teacher;
import org.springframework.stereotype.Repository;

@Repository("teacherDaoImpl")
public class TeacherDaoImpl extends BaseDaoImpl<Teacher> implements TeacherDao{

}

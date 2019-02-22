package com.project.dao.impl;

import com.project.base.BaseDaoImpl;
import com.project.dao.StudentDao;
import com.project.model.Student;
import org.springframework.stereotype.Repository;

@Repository("studentDaoImpl")
public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao {

}

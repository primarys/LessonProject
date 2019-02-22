package com.project.dao.impl;

import com.project.base.BaseDaoImpl;
import com.project.dao.QuizDao;
import com.project.model.Quiz;
import org.springframework.stereotype.Repository;

@Repository("quizDaoImpl")
public class QuizDaoImpl extends BaseDaoImpl<Quiz> implements QuizDao{

}

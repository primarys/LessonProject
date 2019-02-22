package com.project.dao.impl;

import com.project.base.BaseDaoImpl;
import com.project.dao.AdminDao;
import com.project.model.Admin;
import org.springframework.stereotype.Repository;

@Repository("adminDaoImpl")
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao{

}

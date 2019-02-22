package com.project.service.impl;

import com.project.base.BaseDaoImpl;
import com.project.base.BaseServiceImpl;
import com.project.model.Admin;
import com.project.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Resource(name = "adminDaoImpl")
    public void setBaseDao(BaseDaoImpl<Admin> baseDao) {
        super.setBaseDao(baseDao);
    }

    // 判断登录账户
    public Admin checkLogin(String password) {
        String hql = "FROM Admin WHERE password=:password";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("password", password);
        return findUniqueByHql(hql, map);
    }

}
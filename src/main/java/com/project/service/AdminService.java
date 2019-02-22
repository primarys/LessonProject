package com.project.service;

import com.project.base.BaseService;
import com.project.model.Admin;

public interface AdminService extends BaseService<Admin> {
    // 判断登录账户
    public Admin checkLogin(String password);
}

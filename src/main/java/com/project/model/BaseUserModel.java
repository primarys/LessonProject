package com.project.model;

import com.project.base.BaseModel;
import com.project.common.WebApplication;

import java.io.Serializable;
import java.util.Date;

/**
 * 注册管理员用户基本内容
 * @author Dobility
 */
public class BaseUserModel extends BaseModel implements Comparable,Serializable {
    // 用户登录名
    protected String username;
    // 用来显示的名称
    protected String name;
    // 密码
    protected String password;
    // 手机
    protected String phone;
    // 邮箱
    protected String email;
    // 上次登录时间
    protected Date lastLoginTime;
    // 上次登录ip
    protected String lastLoginIP;
    // 本次登录时间
    protected Date loginTime;
    // 本次登录ip
    protected String loginIP;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) { this.lastLoginIP = lastLoginIP; }

    public Date getLoginTime() { return loginTime; }

    public void setLoginTime(Date loginTime) { this.loginTime = loginTime; }

    public String getLoginIP() { return loginIP; }

    public void setLoginIP(String loginIP) { this.loginIP = loginIP; }

    public void setLoginRecord() {
        this.lastLoginTime = this.loginTime;
        this.lastLoginIP = this.loginIP;
        this.loginTime = new Date();
        this.loginIP = WebApplication.getIP();
    }
}

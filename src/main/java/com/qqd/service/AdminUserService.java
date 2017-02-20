package com.qqd.service;

import com.github.pagehelper.PageInfo;
import com.qqd.model.AdminUser;

/**
 * Created by liujianyang on 2017/1/7.
 */
public interface AdminUserService {

    public AdminUser findAdminUserByName(String loginName);

    public PageInfo<AdminUser> findSuperAdmin(String page);

    public PageInfo<AdminUser> findAdmin(String page);

    public AdminUser save(AdminUser user);
}

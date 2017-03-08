package com.qqd.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qqd.Const;
import com.qqd.dao.AdminUserDao;
import com.qqd.dao.CarDao;
import com.qqd.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liujianyang on 2017/1/7.
 */
@Service
public class AdminUserServiceImp implements AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private CarDao carDao;

    @Override
    public AdminUser findAdminUserByName(String loginName) {

        return adminUserDao.findAdminUserByUserName(loginName);
    }

    @Override
    public PageInfo<AdminUser> findSuperAdmin(String page){

        com.github.pagehelper.Page<AdminUser> pageHelper =  PageHelper.startPage(new Integer(page), Const.PAGE_SIZE);

        return new PageInfo<AdminUser>(adminUserDao.findSuperAdmin());

    }

    @Override
    public PageInfo<AdminUser> findAdmin(String page){

        com.github.pagehelper.Page<AdminUser> pageHelper =  PageHelper.startPage(new Integer(page), Const.PAGE_SIZE);

        return new PageInfo<AdminUser>(adminUserDao.findAdmin());

    }

    @Override
    public AdminUser save(AdminUser user){

        adminUserDao.save(user);

        return user;
    }

    @Override
    @Transactional
    public Boolean deleteAdmin(String loginname){

        carDao.deleteAdminCarsByAdminName(loginname);

        return adminUserDao.deleteAdmin(loginname)>0;
    }
}

/******************************************************************
 *
 *    Java Lib For Android, Powered By Shenzhen Jiuzhou.
 *
 *    Copyright (c) 2001-2014 Digital Telemedia Co.,Ltd
 *    http://www.d-telemedia.com/
 *
 *    Package:     com.qqd.service
 *
 *    Filename:    UserServiceIm.java
 *
 *    Description: TODO(用一句话描述该文件做什么)
 *
 *    Copyright:   Copyright (c) 2001-2014
 *
 *    Company:     Digital Telemedia Co.,Ltd
 *
 *    @author:     liujianyang
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年11月1日 上午8:45:53
 *
 *    Revision:
 *
 *    2016年11月1日 上午8:45:53
 *        - first revision
 *
 *****************************************************************/
package com.qqd.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qqd.Const;
import com.qqd.dao.UserDao;
import com.qqd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 作者 E-mail:
* @version 创建时间：2016年11月1日 上午8:45:53
* 类说明
*/
/**
 * @ClassName UserServiceIm
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liujianyang
 * @Date 2016年11月1日 上午8:45:53
 * @version 1.0.0
 */
@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public User findUserByName(String loginName) {
		 
		User a = null;
		try {
			System.out.println("===============" + loginName);
			a = userDao.findUserByUserName(loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
	@Override
	public User findUserByTelephone(String telephone){
		User a = null;
		try {
			System.out.println("===============" + telephone);
			a = userDao.findUserByTelephone(telephone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public PageInfo<User> findAllUsers(String page){

		com.github.pagehelper.Page<User> pageHelper =  PageHelper.startPage(new Integer(page), Const.PAGE_SIZE);

		return new PageInfo<User>(userDao.findAllUsers());
	}

    @Override
    public List<User> findUsers() {
        return userDao.findAllUsers();
    }

    @Override
	public Boolean deleteUser(String userid){
		return userDao.delete(userid)>0;
	}


	@Override
	public User addUser(User user){
		userDao.save(user);

		return user;
	}

	@Override
	public boolean updateUserInfo(User user) {
		Integer a = userDao.updateUserInfo(user);
		return a>0;
	}

	@Override
	public boolean updateUserAvatar(User user) {
		Integer a = userDao.updateUserAvatar(user);
		return a>0;
	}
}

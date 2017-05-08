/******************************************************************
 *
 *    Java Lib For Android, Powered By Shenzhen Jiuzhou.
 *
 *    Copyright (c) 2001-2014 Digital Telemedia Co.,Ltd
 *    http://www.d-telemedia.com/
 *
 *    Package:     com.qqd.mybatis
 *
 *    Filename:    UserDao.java
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
 *    Create at:   2016年11月1日 上午8:50:20
 *
 *    Revision:
 *
 *    2016年11月1日 上午8:50:20
 *        - first revision
 *
 *****************************************************************/
package com.qqd.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qqd.model.User;

import java.util.List;


/**
 * @ClassName UserDao
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liujianyang
 * @Date 2016年11月1日 上午8:50:20
 * @version 1.0.0
 */
@Mapper
public interface UserDao  {
	public User findUserByUserName(@Param("username") String name);

    public Integer updateUserInfo(User user);

    public Integer updateUserAvatar(User user);

    public List<User> findAllUsers();

    public Integer delete(@Param("userid") String userid);

    public void save(User user);

    public User findUserByTelephone(@Param("telephone")  String telephone);
}

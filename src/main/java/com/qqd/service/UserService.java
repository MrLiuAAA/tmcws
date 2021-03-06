/******************************************************************
 *
 *    Java Lib For Android, Powered By Shenzhen Jiuzhou.
 *
 *    Copyright (c) 2001-2014 Digital Telemedia Co.,Ltd
 *    http://www.d-telemedia.com/
 *
 *    Package:     com.qqd.service
 *
 *    Filename:    UserService.java
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
 *    Create at:   2016年11月1日 上午8:41:45
 *
 *    Revision:
 *
 *    2016年11月1日 上午8:41:45
 *        - first revision
 *
 *****************************************************************/
package com.qqd.service;

import com.github.pagehelper.PageInfo;
import com.qqd.model.User;

import java.util.List;

/**
* @author 作者 E-mail:
* @version 创建时间：2016年11月1日 上午8:41:45
* 类说明
*/
/**
 * @ClassName UserService
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liujianyang
 * @Date 2016年11月1日 上午8:41:45
 * @version 1.0.0
 */
public interface UserService{

	/**
     * 根据登录帐号查找
     *
     */
    public User findUserByName(String loginName);

    boolean updateUserInfo(User user);

    public boolean updateUserAvatar(User user);

    public PageInfo<User> findAllUsers(String page);

    public List<User> findUsers();

    public Boolean deleteUser(String userid);
    public User addUser(User user);

    public User findUserByTelephone(String telephone);
}

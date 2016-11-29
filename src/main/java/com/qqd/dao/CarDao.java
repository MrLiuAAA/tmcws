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

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qqd.model.Car;


/**
 * @ClassName UserDao
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liujianyang
 * @Date 2016年11月1日 上午8:50:20
 * @version 1.0.0
 */
@Mapper
public interface CarDao  {
	public List<Car> findCarsByUserName(@Param("username")String username);
	
	public void changeCarStatus(@Param("username")String username,@Param("alertstatus")String alertstatus,@Param("sn")String sn);
}

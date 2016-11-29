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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qqd.dao.CarDao;
import com.qqd.model.Car;

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
public class CarServiceImp implements CarService {

	@Autowired
	CarDao carDao;

	/*
	 * (非 Javadoc) Description:
	 * 
	 * @see com.qqd.service.CarService#findCarsByUserName(java.lang.String)
	 */
	@Override
	public List<Car> findCarsByUserName(String userName) {

		return carDao.findCarsByUserName(userName);
	}

	@Override
	public String changeCarStatus(String userName, String alertstatus, String sn) {
		// TODO Auto-generated method stub
		if (alertstatus.equals("true")) {
			alertstatus = "Y";
		} else {
			alertstatus = "N";
		}
		carDao.changeCarStatus(userName, alertstatus, sn);
		return "success";
	}

}

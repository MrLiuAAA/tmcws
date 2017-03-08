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
import com.qqd.model.Car;

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
public interface CarService {

	public Car findCarBySn(String sn);
	/**
	 * 根据登录帐号查找
	 * 
	 * @return
	 */
	public List<Car> findCarsByUserName(String userName);

	public PageInfo<Car> findCarsByUserNameByPage(String userName,String keyword,String pageNum);



	public Boolean changeCarStatus(String status, String sn, String fieldName);

	public Boolean deleteCar(String userName, String sn);

	public Boolean deleteCarBySn(String sn);

	public Boolean addCar(String username, String sn, String name);

    public List<Car> findAdminCarsByAdminName(String loginname);

	public List<Car> findAllCars();

	public PageInfo<Car> findAllCars(String keyword,String pageNum);

	public PageInfo<Car> findAdminCarsByAdminNameByPage(String loginname,String keyword,String page);

	public PageInfo<Car> findAdminAllCarsByAdminNameByPage(String loginname,String keyword,String page);



    public Boolean addCarToAdmin(String loginname, String sn);
	public Boolean addCarsToAdmin(String loginname, List<String> sns);
}

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

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qqd.Const;
import com.qqd.dao.CarDao;
import com.qqd.dao.MessageQueueDao;
import com.qqd.model.Car;
import com.qqd.model.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class CarServiceImp implements CarService {

	@Autowired
	CarDao carDao;

	@Autowired
	MessageQueueDao messageQueueDao;

	/*
	 * (非 Javadoc) Description:
	 * 
	 * @see com.qqd.service.CarService#findCarsByUserName(java.lang.String)
	 */
	@Override
	public List<Car> findCarsByUserName(String userName) {
		return carDao.findCarsByUserName(userName,"");
	}

	@Override
	public PageInfo<Car> findCarsByUserNameByPage(String userName,String keyword,String pageNum){
		com.github.pagehelper.Page<Car> pageHelper =  PageHelper.startPage(new Integer(pageNum), Const.PAGE_SIZE);
//		carDao.findCarsByUserName(userName);

		return new PageInfo<Car>(carDao.findCarsByUserName(userName,keyword));
	}
	
	@Override
	public Car findCarBySn(String sn) {
		return carDao.findCarBySn(sn);
	}

	@Override
	@Transactional
	public Boolean changeCarStatus(String status, String sn, String fieldName) {

		Car car = carDao.findCarBySn(sn);
		String code = car.getCode();
		Integer r = carDao.changeCarStatus(status, sn, fieldName);

		/**
		 * 在这里 插入 消息列队
		 */
		MessageQueue messageQueue = new MessageQueue();

		if (fieldName != null && fieldName.equalsIgnoreCase("alertstatus")) {
			/**
			 * 设防：*HQ,8696010765,SCF,113032,0,0#
			 * 撤防：*HQ,8696010765,SCF,113032,1,0#
			 */
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("HHmmss");
			String time = format.format(date);
			String cmd = "SCF";
			String message = "*" + code + "," + sn + "," + cmd + "," + time + ","
					+ ("Y".equalsIgnoreCase(status) ? "0" : "1") + ",0#";

			messageQueue.setSn(sn);
			messageQueue.setCode(code);
			messageQueue.setCmd(cmd);
			messageQueue.setMessage(message);

			/**
			 * 先从表中 删除 对同一个设备发送的同一种类型的 数据
			 *
			 * 防止用户设置某一个设置后，还未生效的期间 又进行同样的设置
			 */
			messageQueueDao.deletetMessageQueueBySnAndCodeAndCmd(sn, code, cmd);
			messageQueueDao.insertMessageQueue(messageQueue);
		}else if ("restartflag".equalsIgnoreCase(fieldName)) {
			/**
			 * 将 重启消息 放入消息列队  g_message_queue 表
			 */

			String cmd = "Reboot";
			String message = "Reboot";


			messageQueue.setSn(sn);
			messageQueue.setCode(code);
			messageQueue.setCmd(cmd);
			messageQueue.setMessage(message);

			messageQueueDao.deletetMessageQueueBySnAndCodeAndCmd(sn, code, cmd);
			messageQueueDao.insertMessageQueue(messageQueue);

		}else if ("power".equalsIgnoreCase(fieldName)) {

			/** 断油电(继电器)
			 * 断开：*HQ,8690056946,S20,095540,1,1#
			 * 恢复：*HQ,8690056946,S20,095540,1,0#
			 */
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("HHmmss");
			String time = format.format(date);
			String cmd = "S20";
			String message = "*"+code+"," + sn + ","+cmd+"," + time+",1,"+
					("Y".equalsIgnoreCase(status)?"0":"1")+"#";


			messageQueue.setSn(sn);
			messageQueue.setCode(code);
			messageQueue.setCmd(cmd);
			messageQueue.setMessage(message);

			messageQueueDao.deletetMessageQueueBySnAndCodeAndCmd(sn, code, cmd);
			messageQueueDao.insertMessageQueue(messageQueue);

		}else if ("shockalert".equalsIgnoreCase(fieldName)) {
			/// 震动报警
			/**
			 *  来电报警开启：VAphoneon
			 *	来电报警关闭：VAphoneoff
			 *	短信报警开启：125#
			 *	短信报警关闭：126#
			 */

			String cmd = "shockalert";
			String message = ("Y".equalsIgnoreCase(status)?"VAphoneon":"VAphoneoff");


			messageQueue.setSn(sn);
			messageQueue.setCode(code);
			messageQueue.setCmd(cmd);
			messageQueue.setMessage(message);

			messageQueueDao.deletetMessageQueueBySnAndCodeAndCmd(sn, code, cmd);
			messageQueueDao.insertMessageQueue(messageQueue);


		}else if ("cuttinglinealert".equalsIgnoreCase(fieldName)) {
			/// 断电报警
			/**
			 *来电报警开启：PCAphoneon
			 *来电报警关闭：PCAoff
			 *短信报警开启：pwrsms123456,1
			 *短信报警关闭：pwrsms123456,0
			 */


			String cmd = "cuttinglinealert";
			String message = ("Y".equalsIgnoreCase(status)?"PCAphoneon":"PCAoff");


			messageQueue.setSn(sn);
			messageQueue.setCode(code);
			messageQueue.setCmd(cmd);
			messageQueue.setMessage(message);

			messageQueueDao.deletetMessageQueueBySnAndCodeAndCmd(sn, code, cmd);
			messageQueueDao.insertMessageQueue(messageQueue);


		}



		return r > 0;
	}

	@Override
	public Boolean deleteCar(String userName, String sn) {
		int r = carDao.deleteCar(userName, sn);
		return r > 0;
	}


	@Override
	public Boolean deleteCarBySn(String sn){
		int r = carDao.deleteCarBySn(sn);
		return r > 0;
	}

	@Override
	public Boolean addCar(String username, String sn, String name) {

		Car car = new Car();
		car.setSn(sn);
		car.setName(name);
		car.setUsername(username);
		try {
			carDao.saveCar(car);
		}catch (Exception e){
			return false;
		}


		return car.getId()!=null;
	}


	@Override
	public List<Car> findAdminCarsByAdminName(String loginname) {

		return carDao.findAdminCarsByAdminName(loginname,"");
	}

	@Override
	public List<Car> findAllCars() {
		return carDao.findAllCars("");
	}


	@Override
	public PageInfo<Car> findAllCars(String keyword,String pageNum) {

		com.github.pagehelper.Page<Car> pageHelper =  PageHelper.startPage(new Integer(pageNum), Const.PAGE_SIZE);

		return new PageInfo<Car>(carDao.findAllCars(keyword));
	}



	public PageInfo<Car> findAdminCarsByAdminNameByPage(String loginname,String keyword,String pageNum){

		com.github.pagehelper.Page<Car> pageHelper =  PageHelper.startPage(new Integer(pageNum), Const.PAGE_SIZE);

		return new PageInfo<Car>(carDao.findAdminCarsByAdminName(loginname,keyword));
	}

	public PageInfo<Car> findAdminAllCarsByAdminNameByPage(String loginname,String keyword,String pageNum){

		com.github.pagehelper.Page<Car> pageHelper =  PageHelper.startPage(new Integer(pageNum), Const.PAGE_SIZE);

		return new PageInfo<Car>(carDao.findAdminAllCarsByAdminName(loginname,keyword));
	}



	public Boolean addCarToAdmin(String loginname, String sn){

		carDao.addCarToAdmin(loginname,sn);

		return true;
	}

	@Transactional
	public Boolean addCarsToAdmin(String loginname, List<String> sns){

		List<Car> cars = carDao.findAdminAllCarsByAdminName(loginname,"");
		List<String> carSNs = new ArrayList<>();

		System.out.println(JSON.toJSONString(cars,true));

		for (Car car:cars) {
			carSNs.add(car.getSn());
		}

		Boolean y = true;
		for (String sn:sns) {
			///  将前两位替换为 20  然后再去掉后三位
			sn = "20" + sn.substring(2, 10);
			if (carSNs.contains(sn)){
				System.out.println("已经存在："+sn);
			}else{
				System.out.println("保存："+sn);
				Integer r = carDao.addCarToAdmin(loginname,sn);
				y = (r==1);
			}

		}

		return y;
	}
}

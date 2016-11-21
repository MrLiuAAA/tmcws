/******************************************************************
 *
 *    Package:     com.qqd.model
 *
 *    Filename:    User.java
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
 *    Create at:   2016年11月1日 上午8:37:40
 *
 *    Revision:
 *
 *    2016年11月1日 上午8:37:40
 *        - first revision
 *
 *****************************************************************/
package com.qqd.model;

import java.util.Date;

/**
* @version 创建时间：2016年11月1日 上午8:37:40
* 类说明
*/
/**
 * @ClassName Car
 * @Description TODO 车辆实体类
 * @author liujianyang
 * @Date 2016年11月1日 上午8:37:40
 * @version 1.0.0
 */
public class Car {

	private String id;
	private String sn;
	private String name;
	private String username;
	private Date updatetime;
	private String power;
	private String status;
	
	private String alertstatus;
	private String autoalert;
	private String latitude;
	private String longitude;
	
	private String alertphone;
	private String shockalert;
	private String cuttinglinealert;
	
	private String callnotification;
	
	
	private String restartflag;
	private String location;
	private String alertlocation;
	private String totlemileage;
	private String mileage;
	private String speed;
	private String startdate;
	private String starttime;
	
	private String enddate;
	private String endtime;
	private String ip;
	private String vehiclestatus1;
	private String vehiclestatus2;
	private String vehiclestatus3;
	private String vehiclestatus4;
	private String longdirection;
	private String latdirection;
	private String direction;
	private String code;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAlertstatus() {
		return alertstatus;
	}
	public void setAlertstatus(String alertstatus) {
		this.alertstatus = alertstatus;
	}
	public String getAutoalert() {
		return autoalert;
	}
	public void setAutoalert(String autoalert) {
		this.autoalert = autoalert;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getAlertphone() {
		return alertphone;
	}
	public void setAlertphone(String alertphone) {
		this.alertphone = alertphone;
	}
	public String getShockalert() {
		return shockalert;
	}
	public void setShockalert(String shockalert) {
		this.shockalert = shockalert;
	}
	public String getCuttinglinealert() {
		return cuttinglinealert;
	}
	public void setCuttinglinealert(String cuttinglinealert) {
		this.cuttinglinealert = cuttinglinealert;
	}
	public String getCallnotification() {
		return callnotification;
	}
	public void setCallnotification(String callnotification) {
		this.callnotification = callnotification;
	}
	public String getRestartflag() {
		return restartflag;
	}
	public void setRestartflag(String restartflag) {
		this.restartflag = restartflag;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAlertlocation() {
		return alertlocation;
	}
	public void setAlertlocation(String alertlocation) {
		this.alertlocation = alertlocation;
	}
	public String getTotlemileage() {
		return totlemileage;
	}
	public void setTotlemileage(String totlemileage) {
		this.totlemileage = totlemileage;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getVehiclestatus1() {
		return vehiclestatus1;
	}
	public void setVehiclestatus1(String vehiclestatus1) {
		this.vehiclestatus1 = vehiclestatus1;
	}
	public String getVehiclestatus2() {
		return vehiclestatus2;
	}
	public void setVehiclestatus2(String vehiclestatus2) {
		this.vehiclestatus2 = vehiclestatus2;
	}
	public String getVehiclestatus3() {
		return vehiclestatus3;
	}
	public void setVehiclestatus3(String vehiclestatus3) {
		this.vehiclestatus3 = vehiclestatus3;
	}
	public String getVehiclestatus4() {
		return vehiclestatus4;
	}
	public void setVehiclestatus4(String vehiclestatus4) {
		this.vehiclestatus4 = vehiclestatus4;
	}
	public String getLongdirection() {
		return longdirection;
	}
	public void setLongdirection(String longdirection) {
		this.longdirection = longdirection;
	}
	public String getLatdirection() {
		return latdirection;
	}
	public void setLatdirection(String latdirection) {
		this.latdirection = latdirection;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
}

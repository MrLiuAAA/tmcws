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
 * @ClassName GpsData
 * @Description TODO GPS数据类
 * @author liujianyang
 * @Date 2016年11月19日 上午8:37:40
 * @version 1.0.0
 */
public class GpsData {

	private Date timestamp;
	private String sn;
	private String latitude;
	private String longitude;
	private String speed;
	private String direction;
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
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
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
	
	
	
	
	
}

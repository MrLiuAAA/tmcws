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

import java.util.List;

/**
* @version 创建时间：2016年11月1日 上午8:37:40
* 类说明
*/
/**
 * @ClassName User
 * @Description TODO 用户实体类
 * @author liujianyang
 * @Date 2016年11月1日 上午8:37:40
 * @version 1.0.0
 */
public class User {
	
	
	private String userid;
	private String token;
	private String pushtoken;
	private String realname;
	private String username;
	private String password;
	private String email;
	private String telephone;
	private String nickname;
	private String house;
	private String company;
	private String address;
	private String sex;
	private String mypic;
	private String os;
	private String updatetime;
	
	
	private List<Car> cars;
	
	
	
	
	public List<Car> getCars() {
		return cars;
	}
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPushtoken() {
		return pushtoken;
	}
	public void setPushtoken(String pushtoken) {
		this.pushtoken = pushtoken;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMypic() {
		return mypic;
	}
	public void setMypic(String mypic) {
		this.mypic = mypic;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
}

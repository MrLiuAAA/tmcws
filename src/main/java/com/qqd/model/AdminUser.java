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
public class AdminUser {
	
	
	private String id;
	private String loginname;
	private String name;
	private String password;
	private String avatar;
	private String role;


	public Boolean isSuperAdmin(){
		return "0".equals(this.role);
	}


	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}

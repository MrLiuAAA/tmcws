package com.qqd.utils.security;

import com.qqd.model.AdminUser;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.qqd.Const;
import com.qqd.model.User;

/**
 * 封装shiro用对象获取
 * 
 */
public class AccountShiroUtil {


	/**
	 * 判断当前登录者 是不是管理员
	 * @return
	 */
	public static Boolean isAdminUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		if (null != session) {
			Object obj = session.getAttribute(Const.SESSION_ADMIN_USER);
			if (null != obj && obj instanceof AdminUser) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	/**
	 * 获取当前对象的拷贝
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		User customer = null;
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		if (null != session) {
			Object obj = session.getAttribute(Const.SESSION_USER);
			if (null != obj && obj instanceof User) {
				try {
					/**
					 * 复制一份对象，防止被错误操作
					 */
					customer = (User) BeanUtils.cloneBean((User) obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return customer;
	}

	/**
	 * 获取当前真实的对象，可以进行操作实体
	 * 
	 * @return
	 */
	public static User getRealCurrentUser() {
		User customer = null;
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		if (null != session) {
			Object obj = session.getAttribute(Const.SESSION_USER);
			if (null != obj && obj instanceof User) {
				try {
					/**
					 * 不复制一份对象，防止被错误操作
					 */
					customer = (User) obj;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return customer;
	}

	/**
	 * 获取当前对象的拷贝
	 *
	 * @return
	 */
	public static AdminUser getCurrentAdminUser() {
		AdminUser customer = null;
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		if (null != session) {
			Object obj = session.getAttribute(Const.SESSION_ADMIN_USER);
			if (null != obj && obj instanceof AdminUser) {
				try {
					/**
					 * 复制一份对象，防止被错误操作
					 */
					customer = (AdminUser) BeanUtils.cloneBean((AdminUser) obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return customer;
	}

	/**
	 * 获取当前真实的对象，可以进行操作实体
	 *
	 * @return
	 */
	public static AdminUser getRealCurrentAdminUser() {
		AdminUser customer = null;
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		if (null != session) {
			Object obj = session.getAttribute(Const.SESSION_ADMIN_USER);
			if (null != obj && obj instanceof AdminUser) {
				try {
					/**
					 * 不复制一份对象，防止被错误操作
					 */
					customer = (AdminUser) obj;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return customer;
	}
}

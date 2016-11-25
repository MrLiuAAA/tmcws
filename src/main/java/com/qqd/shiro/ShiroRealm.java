package com.qqd.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.qqd.Const;
import com.qqd.model.User;
import com.qqd.service.UserService;
import com.qqd.utils.MD5Util;



/**
 * 
 */
public class ShiroRealm extends AuthorizingRealm {

	 /**
     * 账户类服务层注入
     */
    @Autowired
    private UserService userService;
	
	/*
	 * 登录信息和用户验证信息验证(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)  {
		 UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		 AuthenticationInfo authenticationInfo = null;
		 String username=new String(token.getUsername());//用户名
		 String password=new String(token.getPassword());//密码
		System.out.println("登录用户:"+username + "  " +password);
		 User a = userService.findUserByName(username);//通过登录名 寻找用户
		System.out.println("登录用户:"+ JSON.toJSONString(a,true));
		if (a != null) {
	    	 if(a.getPassword().equals(MD5Util.md5Encode(password))){
	    		 authenticationInfo = new SimpleAuthenticationInfo(a.getUsername(), password, getName());
	    		 this.setSession(Const.SESSION_USER, a);
	    		 return authenticationInfo;
	    	 }else{
	    		 throw new IncorrectCredentialsException(); /*错误认证异常*/
	    	 }    	
	     } else {
	    	 throw new UnknownAccountException(); /*找不到帐号异常*/
	     }
	     
	}
	
	/*
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {	
		  // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(pc);
            SecurityUtils.getSubject().logout();
            return null;
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
	}

	  /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *
     * @see
     */
    private void setSession(Object key, Object value) {
    	System.out.println("将一些数据放到ShiroSession中,以便于其它地方使用 ");
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
            	
                session.setAttribute(key, value);
            }
        }
    }
	
}

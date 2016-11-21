/******************************************************************
 *
 *    Package:     com.qqd.controller
 *
 *    Filename:    LoginController.java
 *
 *    Description: TODO(用一句话描述该文件做什么)
 *
 *    @author:     liujianyang
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年11月1日 上午11:25:53
 *
 *    Revision:
 *
 *    2016年11月1日 上午11:25:53
 *        - first revision
 *
 *****************************************************************/
package com.qqd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.qqd.Const;
import com.qqd.model.User;
import com.qqd.service.UserService;
import com.qqd.utils.PageData;
import com.qqd.utils.security.AccountShiroUtil;

/**
 * @ClassName LoginController
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liujianyang
 * @Date 2016年11月1日 上午11:25:53
 * @version 1.0.0
 */

@Controller
public class LoginController extends BaseController<User>{

	@Autowired
	UserService userService;
	
	@RequestMapping("/")
	public String index(){
		
		return "redirect:/backstage/index";
	}
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping("/loginIndex")
	public ModelAndView toLogin(){
		ModelAndView mv =  new ModelAndView();
		mv.setViewName("login");
		
		return mv;
	}
	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="/system_login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login()throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = this.getPageData();
		String errInfo = "";
		
		String username = pd.getString("username");
		String password = pd.getString("password");
		String code = pd.getString("verifyCode");
		
		System.out.println(username + "  " +password);
		if(null != username && null != password &&null != code){
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码		
			
			if(null == code || "".equals(code)){
				errInfo = "nullcode"; //验证码为空
			}else if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
				errInfo = "nullup";	//缺少用户名或密码
			}else{
				if(StringUtils.isNotEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){										
					// shiro加入身份验证
					UsernamePasswordToken token = new UsernamePasswordToken(username, password.toUpperCase());
					token.setRememberMe(true);
					try {
						if (!currentUser.isAuthenticated()) {
							currentUser.login(token);
						}	
						//记录登录日志
//						String accountId=AccountShiroUtil.getCurrentUser().getAccountId();
//						String loginIP=IPUtil.getIpAddr(getRequest());//获取用户登录IP
//						LoginLog loginLog=new LoginLog(accountId,loginIP);
//						loginLogService.saveLoginLog(loginLog);
					} catch (UnknownAccountException uae) {
						errInfo = "用户名或密码有误";// 用户名或密码有误
					} catch (IncorrectCredentialsException ice) {
						errInfo = "密码错误"; // 密码错误
					} catch (LockedAccountException lae) {
						errInfo = "inactive";// 未激活
					} catch (ExcessiveAttemptsException eae) {
						errInfo = "错误次数过多";// 错误次数过多
					} catch (AuthenticationException ae) {
						errInfo = "验证未通过";// 验证未通过
					}
					// 验证是否登录成功
					if (!currentUser.isAuthenticated()) {
						token.clear();
					}
				}else{
					errInfo = "验证码输入有误";				 	//验证码输入有误
				}
				if(StringUtils.isEmpty(errInfo)){
					errInfo = "success";					//验证成功
					session.removeAttribute(Const.SESSION_SECURITY_CODE);//移除SESSION的验证
				}
			}
		}else{
			errInfo = "缺少参数";	//缺少参数
		}	
		map.put("result", errInfo);
		
		System.out.println(map);
		return map;
	}		
	  /**
     * 帐号注销
     * @return
     */
    @RequestMapping("/system_logout")
    public String logout(HttpServletRequest request,HttpSession session) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        session = request.getSession(true);
        session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(Const.SESSION_MENULIST);
        return "redirect:loginIndex";
    }
	
}

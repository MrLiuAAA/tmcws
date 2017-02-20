package com.qqd.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 *  Shiro 配置
 *	Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 *	既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 *
 */
@Configuration
public class ShiroConfig {
	
	
    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();  
    
    @Bean(name = "ShiroRealm")  
    public ShiroRealm getShiroRealm() {  
        return new ShiroRealm();  
    }  
  
    @Bean(name = "shiroEhcacheManager")  
    public EhCacheManager getEhCacheManager() {  
        EhCacheManager em = new EhCacheManager();  
        em.setCacheManagerConfigFile("classpath:ehcache.xml");  
        return em;  
    }  
  
    @Bean(name = "lifecycleBeanPostProcessor")  
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {  
        return new LifecycleBeanPostProcessor();  
    }  
  
    @Bean  
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {  
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();  
        daap.setProxyTargetClass(true);  
        return daap;  
    }  
  
    @Bean(name = "securityManager")  
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {  
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();  
        dwsm.setRealm(getShiroRealm());  
        dwsm.setCacheManager(getEhCacheManager());  
        return dwsm;  
    }  
  
    @Bean  
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {  
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();  
        aasa.setSecurityManager(getDefaultWebSecurityManager());  
        return new AuthorizationAttributeSourceAdvisor();  
    }  
  

	 /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
       Filter Chain定义说明
       1、一个URL可以配置多个Filter，使用逗号分隔
       2、当设置多个过滤器时，全部验证通过，才视为通过
       3、部分过滤器可指定参数，如perms，roles
       
        anon:所有url都都可以匿名访问;
	    authc: 需要认证才能进行访问;
	    user:配置记住我或认证通过可以访问；
     *
     */
    @Bean(name = "shiroFilter")  
    public ShiroFilterFactoryBean shirFilter(){
       System.out.println("ShiroConfiguration.shirFilter()");
      
       ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
      
        // 必须设置 SecurityManager 
       shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
      
       //拦截器.
       Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
       //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
       filterChainDefinitionMap.put("/logout", "logout");
      
//       //配置记住我或认证通过可以访问的地址
        filterChainDefinitionMap.put("/second/**", "anon");
        filterChainDefinitionMap.put("/admin/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
      	filterChainDefinitionMap.put("/js/**", "anon");
      	filterChainDefinitionMap.put("/bootstrap/**", "anon");
      	filterChainDefinitionMap.put("/images/**", "anon");
      	filterChainDefinitionMap.put("/favicon.ico", "anon");
      	filterChainDefinitionMap.put("/verifyCode/**", "anon");
      	filterChainDefinitionMap.put("/system_login", "anon");
//       filterChainDefinitionMap.put("/index", "user");
       filterChainDefinitionMap.put("/adminLoginIndex", "anon");
        filterChainDefinitionMap.put("/admin_login", "anon");
       
       //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
       filterChainDefinitionMap.put("/**", "authc");
      
       // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/loginIndex");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/backstage/index");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/loginIndex");
      
       shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
       return shiroFilterFactoryBean;
    }
   

}

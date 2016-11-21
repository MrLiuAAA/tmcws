package com.qqd.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.qqd.AjaxRes;
import com.qqd.mybatis.Page;
import com.qqd.utils.PageData;



public class BaseController<T> {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	public AjaxRes getAjaxRes(){
		return new AjaxRes();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();	
		return request;
	}

	/**
	 * 得到分页列表的信息 
	 * @param <T>
	 */
	@SuppressWarnings("hiding")
	public <T> Page<T> getPage(){	
		return new Page<T>();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}

	
}

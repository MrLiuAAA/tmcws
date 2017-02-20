/******************************************************************
 *
 *    Java Lib For Android, Powered By Shenzhen Jiuzhou.
 *
 *    Copyright (c) 2001-2014 Digital Telemedia Co.,Ltd
 *    http://www.d-telemedia.com/
 *
 *    Package:     com.qqd.mybatis
 *
 *    Filename:    UserDao.java
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
 *    Create at:   2016年11月1日 上午8:50:20
 *
 *    Revision:
 *
 *    2016年11月1日 上午8:50:20
 *        - first revision
 *
 *****************************************************************/
package com.qqd.dao;

import com.qqd.model.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface NoticeDao {
	public List<Notice> findNoticesByUserName(@Param("username") String username);



	public Integer deleteById(@Param("noticeid") String noticeid);

	public Integer deleteByUserName(@Param("username") String username);
}

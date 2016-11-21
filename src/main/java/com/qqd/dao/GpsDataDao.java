/******************************************************************
 *
 *    Matrix Technology Co.,Ltd
 *
 *    Package:     com.qqd.dao
 *
 *    Filename:    GpsDataDao.java
 *
 *    Description: TODO
 *
 *    @author:     liujianyang
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年11月19日 下午3:11:54
 *
 *    Revision:
 *
 *    2016年11月19日 下午3:11:54
 *        - first revision
 *
 *****************************************************************/
package com.qqd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qqd.model.GpsData;
import com.qqd.model.TrajectoryInfo;

/**
 * @ClassName GpsDataDao
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liujianyang
 * @Date 2016年11月19日 下午3:11:54
 * @version 1.0.0
 */
@Mapper
public interface GpsDataDao {

	/**
	 * @Description (TODO这里用一句话描述这个方法的作用)
	 * @param sn
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<GpsData> getTrajectory(@Param("sn") String sn, @Param("startTime")String startTime, @Param("endTime")String endTime);
	TrajectoryInfo getTrajectoryTotalInfo(@Param("sn") String sn, @Param("startTime")String startTime, @Param("endTime")String endTime);
	
	
	
}

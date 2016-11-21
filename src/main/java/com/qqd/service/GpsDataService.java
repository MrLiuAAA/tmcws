/******************************************************************
 *
 *    Matrix Technology Co.,Ltd
 *
 *    Package:     com.qqd.service
 *
 *    Filename:    GpsDataService.java
 *
 *    Description: TODO
 *
 *    @author:     liujianyang
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年11月19日 下午3:13:52
 *
 *    Revision:
 *
 *    2016年11月19日 下午3:13:52
 *        - first revision
 *
 *****************************************************************/
package com.qqd.service;

import java.util.List;

import com.qqd.model.GpsData;
import com.qqd.model.TrajectoryInfo;

/**
 * @ClassName GpsDataService
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liujianyang
 * @Date 2016年11月19日 下午3:13:52
 * @version 1.0.0
 */
public interface GpsDataService {

	/**
	 * @Description (TODO这里用一句话描述这个方法的作用)
	 * @param sn
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<GpsData> getTrajectory(String sn, String startTime, String endTime);

	/**
	 * @Description (TODO这里用一句话描述这个方法的作用)
	 * @param sn
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public TrajectoryInfo getTrajectoryTotalInfo(String sn, String startTime, String endTime);
}

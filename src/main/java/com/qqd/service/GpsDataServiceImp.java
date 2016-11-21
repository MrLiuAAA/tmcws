/******************************************************************
 *
 *    Matrix Technology Co.,Ltd
 *
 *    Package:     com.qqd.service
 *
 *    Filename:    GpsDataServiceImp.java
 *
 *    Description: TODO
 *
 *    @author:     liujianyang
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年11月19日 下午3:14:22
 *
 *    Revision:
 *
 *    2016年11月19日 下午3:14:22
 *        - first revision
 *
 *****************************************************************/
package com.qqd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qqd.dao.GpsDataDao;
import com.qqd.model.GpsData;
import com.qqd.model.TrajectoryInfo;

/**
 * @ClassName GpsDataServiceImp
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liujianyang
 * @Date 2016年11月19日 下午3:14:22
 * @version 1.0.0
 */
@Service
public class GpsDataServiceImp implements GpsDataService {

	@Autowired
	GpsDataDao gpsDataDao;
	
	@Override
	public List<GpsData> getTrajectory(String sn, String startTime, String endTime) {
		
		return gpsDataDao.getTrajectory(sn,startTime,endTime);
	}

	@Override
	public TrajectoryInfo getTrajectoryTotalInfo(String sn, String startTime, String endTime) {
		
		return gpsDataDao.getTrajectoryTotalInfo(sn, startTime, endTime);
	}
}

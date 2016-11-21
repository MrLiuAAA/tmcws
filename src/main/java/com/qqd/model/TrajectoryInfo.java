/******************************************************************
 *
 *    Matrix Technology Co.,Ltd
 *
 *    Package:     com.qqd.model
 *
 *    Filename:    TrajectoryInfo.java
 *
 *    Description: TODO
 *
 *    @author:     liujianyang
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年11月19日 下午3:38:33
 *
 *    Revision:
 *
 *    2016年11月19日 下午3:38:33
 *        - first revision
 *
 *****************************************************************/
package com.qqd.model;

/**
 * @ClassName TrajectoryInfo
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liujianyang
 * @Date 2016年11月19日 下午3:38:33
 * @version 1.0.0
 */
public class TrajectoryInfo {

	

	private String sumSpeed="0"; /// 总里程
	private String maxSpeed="0"; /// 最大速度
	private String minSpeed="0"; ///  最小速度
	private String recordNum="0"; //  总记录数目

	
	
	
	
	
	public String getSumSpeed() {
		return sumSpeed;
	}

	public void setSumSpeed(String sumSpeed) {
		this.sumSpeed = sumSpeed;
	}

	public String getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(String maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getMinSpeed() {
		return minSpeed;
	}

	public void setMinSpeed(String minSpeed) {
		this.minSpeed = minSpeed;
	}

	public String getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}
	
	
	
	
}

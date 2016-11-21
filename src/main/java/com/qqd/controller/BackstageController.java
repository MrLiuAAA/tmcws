/******************************************************************
 *
 *    Matrix Technology Co.,Ltd
 *
 *    Package:     com.qqd.controller
 *
 *    Filename:    BackstageController.java
 *
 *    Description: TODO
 *
 *    @author:     liujianyang
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年11月17日 下午11:21:35
 *
 *    Revision:
 *
 *    2016年11月17日 下午11:21:35
 *        - first revision
 *
 *****************************************************************/
package com.qqd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qqd.AjaxRes;
import com.qqd.model.Car;
import com.qqd.model.GpsData;
import com.qqd.model.TrajectoryInfo;
import com.qqd.model.User;
import com.qqd.service.CarService;
import com.qqd.service.GpsDataService;
import com.qqd.service.UserService;
import com.qqd.utils.PageData;
import com.qqd.utils.security.AccountShiroUtil;

/**
 * @ClassName BackstageController
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liujianyang
 * @Date 2016年11月17日 下午11:21:35
 * @version 1.0.0
 */
@Controller
@RequestMapping("/backstage/")
public class BackstageController extends BaseController<User>{

	@Autowired
	public CarService carService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public GpsDataService gpsDataServcie;
	/**
	 * 访问系统首页
	 */
	@RequestMapping("index")
	public String index(Model model){	
		//shiro获取用户信息
		User currentUser = AccountShiroUtil.getCurrentUser();
		model.addAttribute("currentUser", currentUser);
		
		System.out.println("当前用户："+JSON.toJSONString(currentUser,true));
		
		
		List<Car> cars = carService.findCarsByUserName(currentUser.getUsername());
		
		///  查询 绑定车辆数目
		int bingCar = cars==null?0:cars.size();
		model.addAttribute("bingNum", bingCar);
		
		int alert = 0; /// 设防车辆
		int lost = 0; ///失效车辆
		
		if (bingCar==0) {
			
		}else{
			for (Car car : cars) {
				if ("已失效".equals(car.getStatus())) {
					lost++;
				}else{
					if ("Y".equals(car.getAlertstatus())) {
						alert++;
					}
				}
			}
			
		}
		model.addAttribute("alertNum", alert);
		model.addAttribute("lostNum", lost);
		
	
		
		
		
		
		
		return "index";
	}
	
	/**
	 * 定位页面
	 * @Description (TODO这里用一句话描述这个方法的作用)
	 * @param model
	 * @return
	 */
	@RequestMapping("position")
	public String position(Model model){	
		return "position";
	}
	
	/**
	 * 获取 车辆定位信息
	 * @return
	 */
	@RequestMapping(value = "getLocation",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getLocation(){	
		AjaxRes ar=getAjaxRes();
		User currentUser = AccountShiroUtil.getCurrentUser();
		///  查询出当前车辆的位置
		List<Car> cars = carService.findCarsByUserName(currentUser.getUsername());
		
		List<Map<String, String>> rList = new ArrayList<>();
		if (cars!=null) {
			for (Car car : cars) {
				HashMap<String, String> map = new HashMap<>();
				map.put("name", car.getName()); /// 名字
				map.put("sn", car.getSn()); /// 唯一标识
				map.put("latitude", car.getLatitude());///维度
				map.put("longitude", car.getLongitude());/// 经度
				map.put("status","已失效".equals(car.getStatus())?"已失效":("Y".equals(car.getAlertstatus())?"设防状态":"未设防状态")); 
				
				rList.add(map);
			}
		}
		/// 标记出当前  车辆的位置
		ar.setSucceed(rList);
		return ar;
	}
	
	/**
	 * 播放轨迹 页面
	 * @return
	 */
	@RequestMapping("positionHistory")
	public String positionHistory(Model model){	
		return "positionhistory";
	}
	
	/**
	 * 获取我的车辆
	 * @return
	 */
	@RequestMapping(value = "getmycars",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getmycars(Model model){	
		AjaxRes ar=getAjaxRes();
		User currentUser = AccountShiroUtil.getCurrentUser();
		///  查询出当前车辆的位置
		List<Car> cars = carService.findCarsByUserName(currentUser.getUsername());
		
		List<Map<String, String>> rList = new ArrayList<>();
		if (cars!=null) {
			for (Car car : cars) {
				HashMap<String, String> map = new HashMap<>();
				map.put("name", car.getName()); /// 名字
				map.put("sn", car.getSn()); /// 标识
				
				rList.add(map);
			}
		}
		/// 我拥有的车辆
		ar.setSucceed(rList);
		return ar;
	}

	/**
	 * 获取 车辆轨迹
	 * @return
	 */
	@RequestMapping(value = "getTrajectory",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getTrajectory(Model model){	
		AjaxRes ar=getAjaxRes();
		
		PageData data = getPageData();
		
		String sn = data.getString("sn");
		String startTime = data.getString("startTime");
		String endTime = data.getString("endTime");
		Map<String,Object> rMap = new HashMap<>();
		///  查询出当前车辆的位置
		List<GpsData> gpsDatas = gpsDataServcie.getTrajectory(sn,startTime,endTime);
		
		rMap.put("gps", gpsDatas);
		
		
		TrajectoryInfo trajectoryInfo = gpsDataServcie.getTrajectoryTotalInfo(sn,startTime,endTime);
		
		double mileage = new Double(trajectoryInfo.getSumSpeed()) * 1.852 / 3600 * 20;// 总里程 sum(speed)，修正为20秒发送一次数据，所以乘以20
		
		double maxSpeed = new Double(trajectoryInfo.getMaxSpeed()) * 1.852;
		double minSpeed = new Double(trajectoryInfo.getMinSpeed()) * 1.852;
		
		Long record = new Long(trajectoryInfo.getRecordNum());
		// 总时间 c
		String runningTime = Math
				.round(record * 20 / 3600)
				+ "小时"
				+ Math.round((record * 20) % 3600 / 60)
				+ "分钟";// 总时间 count(speed)  %3600 取余数，即舍去小时部分数字
		
		double runningTimeMM = record *20;
		double averageSpeed = mileage / runningTimeMM * 3600;// 平均速度
		
		rMap.put("mileage", Math.round(mileage)+"");
		rMap.put("maxspeed", Math.round(maxSpeed)+"");
		rMap.put("minspeed", Math.round(minSpeed)+"");
		
		rMap.put("averagespeed", Math.round(averageSpeed) + "");
		rMap.put("runningtime", runningTime);

		
		
		/// 我拥有的车辆
		ar.setSucceed(rMap);
		return ar;
	}
	
	
}

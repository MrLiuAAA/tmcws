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

import com.alibaba.fastjson.JSON;
import com.qqd.AjaxRes;
import com.qqd.model.*;
import com.qqd.service.*;
import com.qqd.utils.PageData;
import com.qqd.utils.security.AccountShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BackstageController
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liujianyang
 * @Date 2016年11月17日 下午11:21:35
 * @version 1.0.0
 */
@Controller
@RequestMapping("/backstage/")
public class BackstageController extends BaseController<User> {

	@Autowired
	public CarService carService;

	@Autowired
	public UserService userService;

	@Autowired
	public GpsDataService gpsDataServcie;

	@Autowired
	public SerialsNumberService serialsNumberService;


	@Autowired
	public NoticeService noticeService;

	/**
	 * 访问系统首页
	 */
	@RequestMapping("index")
	public String index(Model model) {
		// shiro获取用户信息
		User currentUser = AccountShiroUtil.getCurrentUser();
		model.addAttribute("currentUser", currentUser);

		System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));

		List<Car> cars = carService.findCarsByUserName(currentUser.getUsername());

		/// 查询 绑定车辆数目
		int bingCar = cars == null ? 0 : cars.size();
		model.addAttribute("bingNum", bingCar);

		int alert = 0; /// 设防车辆
		int lost = 0; /// 失效车辆

		if (bingCar == 0) {

		} else {
			for (Car car : cars) {
				if ("已失效".equals(car.getStatus())) {
					lost++;
				} else {
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
	 * 
	 * @Description (TODO这里用一句话描述这个方法的作用)
	 * @param model
	 * @return
	 */
	@RequestMapping("position")
	public String position(Model model) {
		return "position";
	}

	/**
	 * 获取 车辆定位信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "getLocation", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getLocation() {
		AjaxRes ar = getAjaxRes();
		User currentUser = AccountShiroUtil.getCurrentUser();
		/// 查询出当前车辆的位置
		List<Car> cars = carService.findCarsByUserName(currentUser.getUsername());

		List<Map<String, String>> rList = new ArrayList<>();
		if (cars != null) {
			for (Car car : cars) {
				HashMap<String, String> map = new HashMap<>();
				map.put("name", car.getName()); /// 名字
				map.put("sn", car.getSn()); /// 唯一标识
				map.put("latitude", car.getLatitude());/// 维度
				map.put("longitude", car.getLongitude());/// 经度
				map.put("status",
						"已失效".equals(car.getStatus()) ? "已失效" : ("Y".equals(car.getAlertstatus()) ? "设防状态" : "未设防状态"));

				rList.add(map);
			}
		}
		/// 标记出当前 车辆的位置
		ar.setSucceed(rList);
		return ar;
	}

	/**
	 * 播放轨迹 页面
	 * 
	 * @return
	 */
	@RequestMapping("positionHistory")
	public String positionHistory(Model model) {
		return "positionhistory";
	}

	/**
	 * 获取我的车辆
	 * 
	 * @return
	 */
	@RequestMapping(value = "getmycars", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getmycars(Model model) {
		AjaxRes ar = getAjaxRes();
		User currentUser = AccountShiroUtil.getCurrentUser();
		/// 查询出当前车辆的位置
		List<Car> cars = carService.findCarsByUserName(currentUser.getUsername());

		List<Map<String, String>> rList = new ArrayList<>();
		if (cars != null) {
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
	 * 
	 * @return
	 */
	@RequestMapping(value = "getTrajectory", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getTrajectory(Model model) {
		AjaxRes ar = getAjaxRes();

		PageData data = getPageData();

		String sn = data.getString("sn");
		String startTime = data.getString("startTime");
		String endTime = data.getString("endTime");
		Map<String, Object> rMap = new HashMap<>();
		/// 查询出当前车辆的位置
		List<GpsData> gpsDatas = gpsDataServcie.getTrajectory(sn, startTime, endTime);

		rMap.put("gps", gpsDatas);

		TrajectoryInfo trajectoryInfo = gpsDataServcie.getTrajectoryTotalInfo(sn, startTime, endTime);

		double mileage = new Double(trajectoryInfo.getSumSpeed()) * 1.852 / 3600 * 20;// 总里程
																						// sum(speed)，修正为20秒发送一次数据，所以乘以20

		double maxSpeed = new Double(trajectoryInfo.getMaxSpeed()) * 1.852;
		double minSpeed = new Double(trajectoryInfo.getMinSpeed()) * 1.852;

		Long record = new Long(trajectoryInfo.getRecordNum());
		// 总时间 c
		String runningTime = Math.round(record * 20 / 3600) + "小时" + Math.round((record * 20) % 3600 / 60) + "分钟";// 总时间
																													// count(speed)
																													// %3600
																													// 取余数，即舍去小时部分数字

		double runningTimeMM = record * 20;
		double averageSpeed = mileage / runningTimeMM * 3600;// 平均速度

		rMap.put("mileage", Math.round(mileage) + "");
		rMap.put("maxspeed", Math.round(maxSpeed) + "");
		rMap.put("minspeed", Math.round(minSpeed) + "");

		rMap.put("averagespeed", Math.round(averageSpeed) + "");
		rMap.put("runningtime", runningTime);

		/// 我拥有的车辆
		ar.setSucceed(rMap);
		return ar;
	}

	/**
	 * 投防
	 * 
	 * @return
	 */
	@RequestMapping("setGuard")
	public String setGuard(Model model) {
		return "setGuard";
	}

	/**
	 * 编辑 设防
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "editGuard", method = RequestMethod.GET)
	public String editGuard(Model model, String alertphone, String shockalert, String cuttinglinealert,
			String autoalert, String sn) {
		model.addAttribute("sn", sn);
		model.addAttribute("alertphone", alertphone);
		model.addAttribute("shockalert", shockalert);
		model.addAttribute("cuttinglinealert", cuttinglinealert);
		model.addAttribute("autoalert", autoalert);
		return "editGuard";
	}

	/**
	 * 修改 设防状态
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeStatus(String status, String sn, String fieldName) {
		Map<String, Object> map = new HashMap<String, Object>();
		User currentUser = AccountShiroUtil.getCurrentUser();
		/// 查询出当前车辆的位置
		Boolean ret = carService.changeCarStatus(currentUser.getUsername(), status, sn, fieldName);
		map.put("result", ret ? "success" : "failure");
		System.out.println(map);
		return map;
	}

	/**
	 * 获取我的车辆
	 * 
	 * @return
	 */
	@RequestMapping(value = "getmycarstatus", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getmycarstatus(Model model) {
		AjaxRes ar = getAjaxRes();
		User currentUser = AccountShiroUtil.getCurrentUser();
		/// 查询出当前车辆的位置
		List<Car> cars = carService.findCarsByUserName(currentUser.getUsername());

		List<Map<String, String>> rList = new ArrayList<>();
		if (cars != null) {
			for (Car car : cars) {
				HashMap<String, String> map = new HashMap<>();
				map.put("name", car.getName()); /// 名字
				map.put("sn", car.getSn()); /// 标识
				map.put("status",
						"已失效".equals(car.getStatus()) ? "已失效" : ("Y".equals(car.getAlertstatus()) ? "设防状态" : "未设防状态"));
				map.put("alertphone", car.getAlertphone());
				map.put("shockalert", car.getShockalert());
				map.put("cuttinglinealert", car.getCuttinglinealert());
				map.put("autoalert", car.getAutoalert());
				rList.add(map);
			}
		}
		/// 我拥有的车辆
		ar.setSucceed(rList);
		return ar;
	}

	/**
	 * 车辆管理
	 * 
	 * @return
	 */
	@RequestMapping("carManage")
	public String carManage(Model model) {

		User currentUser = AccountShiroUtil.getCurrentUser();
		/// 查询出当前车辆的位置
		List<Car> cars = carService.findCarsByUserName(currentUser.getUsername());

		List<Map<String, String>> rList = new ArrayList<>();
		if (cars != null) {
			for (Car car : cars) {
				HashMap<String, String> map = new HashMap<>();
				map.put("name", car.getName()); /// 名字
				map.put("sn", car.getSn()); /// 标识
				map.put("power", "Y".equalsIgnoreCase(car.getPower()) ? "通电中" : "已断电"); /// 断电
				map.put("powerflag", "Y".equalsIgnoreCase(car.getPower()) ? "Y" : "N"); /// 断电
				map.put("speed", car.getSpeed() == null ? "0" : car.getSpeed());/// 速度
				map.put("todaymileage", car.getMileage() == null ? "0" : car.getMileage());/// 当日里程
				map.put("totalmileage", car.getTotlemileage() == null ? "0" : car.getTotlemileage());/// 总里程

				map.put("latitude", car.getLatitude());/// 维度
				map.put("longitude", car.getLongitude());/// 经度
				map.put("status",
						"已失效".equals(car.getStatus()) ? "已失效" : ("Y".equals(car.getAlertstatus()) ? "设防状态" : "未设防状态"));
				rList.add(map);
			}
		}

		model.addAttribute("cars", rList);

		return "carManage";
	}

	/**
	 * 添加 车辆
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addcar")
	public String addcar(Model model) {
		return "addcar";
	}
	
	@RequestMapping(value = "editCar", method = RequestMethod.GET)
	public String editCar(Model model,String sn) {
		
		Car car = carService.findCarBySn(sn);
		
		model.addAttribute("car", car);
		return "editCar";
	}
	
	
	@RequestMapping(value = "saveCar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCar(String sn,String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		User currentUser = AccountShiroUtil.getCurrentUser();

		if (serialsNumberService.validateSN(sn)) {

			/// 验证成功后  解码
			///  将前两位替换为 20  然后再去掉后三位
			sn = "20"+sn.substring(2, 10);

			Boolean result = carService.addCar(currentUser.getUsername(), sn, name);

			System.out.println("result = " + result);

			map.put("result", result ? "success" : "failure");
			if (result){
				map.put("msg", "添加失败");
			}
		} else {
			map.put("result", "failure");
			map.put("msg", "序列号错误");
		}

		
		return map;
	}
	
	

	/**
	 * deleteCar
	 */
	@RequestMapping(value = "deleteCar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCar(String sn) {
		Map<String, Object> map = new HashMap<String, Object>();
		User currentUser = AccountShiroUtil.getCurrentUser();
		/// 查询出当前车辆的位置
		Boolean ret = carService.deleteCar(currentUser.getUsername(), sn);
		map.put("result", ret ? "success" : "failure");
		System.out.println(map);
		return map;
	}

	/**
	 * 获取我的车辆详细信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "getmycarManage", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getmycarManage(Model model) {
		AjaxRes ar = getAjaxRes();
		User currentUser = AccountShiroUtil.getCurrentUser();
		/// 查询出当前车辆的位置
		List<Car> cars = carService.findCarsByUserName(currentUser.getUsername());

		List<Map<String, String>> rList = new ArrayList<>();
		if (cars != null) {
			for (Car car : cars) {
				HashMap<String, String> map = new HashMap<>();
				map.put("name", car.getName()); /// 名字
				map.put("sn", car.getSn()); /// 标识
				map.put("latitude", car.getLatitude());/// 维度
				map.put("longitude", car.getLongitude());/// 经度

				map.put("status",
						"已失效".equals(car.getStatus()) ? "已失效" : ("Y".equals(car.getAlertstatus()) ? "设防状态" : "未设防状态"));
				rList.add(map);
			}
		}
		/// 我拥有的车辆
		ar.setSucceed(rList);
		return ar;
	}



	@RequestMapping("personalInfo")
	public String personalInfo(Model model) {
		User currentUser = AccountShiroUtil.getCurrentUser();
		model.addAttribute("user",currentUser);
		return "personalInfo";
	}


	@RequestMapping("messageInfo")
	public String messageInfo(Model model) {
		///  获取当前用户的  消息列表
		User currentUser = AccountShiroUtil.getCurrentUser();

		List<Notice> notices = noticeService.findNoticesByUserName(currentUser.getUsername());

		model.addAttribute("notices", notices);

		return "messageInfo";
	}


	@RequestMapping("aboutUs")
	public String aboutUs(Model model) {
		return "aboutUs";
	}

	@RequestMapping("editPersonalInfo")
	public String editPersonalInfo(Model model) {

		User currentUser = AccountShiroUtil.getCurrentUser();
		model.addAttribute("user",currentUser);
		return "editPersonalInfo";
	}



	@RequestMapping(value = "saveUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveUserInfo(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		User currentUser = AccountShiroUtil.getCurrentUser();

		user.setUserid(currentUser.getUserid());
		//
		if(userService.updateUserInfo(user)){
			map.put("result", "success" );
			User realCurrentUser = AccountShiroUtil.getRealCurrentUser();
			realCurrentUser.setNickname(user.getNickname());
			realCurrentUser.setTelephone(user.getTelephone());

		}else{
			map.put("result", "failure");
			map.put("msg", "保存失败");
		}


		return map;
	}

}

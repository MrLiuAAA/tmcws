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
 * 生成验证码
 * @version
 */
@Controller
public class AdminController extends BaseController<Object>{

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
	@RequestMapping(value="/admin/index")
	public String index(Model model){
		// shiro获取用户信息
		AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
		model.addAttribute("currentUser", currentUser);

		System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));

		List<Car> cars = carService.findAdminCarsByAdminName(currentUser.getLoginname());

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



		if (currentUser.isSuperAdmin()){
			return "/admin/index";
		}else{
			return "/second/index";
		}
	}





	/**
	 * 获取 车辆定位信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/getLocation", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getLocation() {
		AjaxRes ar = getAjaxRes();
		AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
		/// 查询出当前车辆的位置
		List<Car> cars = carService.findAdminCarsByAdminName(currentUser.getLoginname());

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

	@RequestMapping(value="/admin/history")
	public String history(Model model) {
		AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
		model.addAttribute("currentUser", currentUser);

		System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));

		List<Car> cars = carService.findAdminCarsByAdminName(currentUser.getLoginname());

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



		return "/admin/history";
	}


	@RequestMapping(value="/admin/guard")
	public String guard(Model model) {
		AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
		model.addAttribute("currentUser", currentUser);

		System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));

		List<Car> cars = carService.findAdminCarsByAdminName(currentUser.getLoginname());

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



		return "/admin/guard";
	}




	/**
	 * 获取我的车辆
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/getmycars", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getmycars(Model model) {
		AjaxRes ar = getAjaxRes();
		AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
		/// 查询出当前车辆的位置
		List<Car> cars = carService.findAdminCarsByAdminName(currentUser.getLoginname());

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
	@RequestMapping(value = "/admin/getTrajectory", method = RequestMethod.POST)
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
	 * 获取我的车辆
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/getmycarstatus", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getmycarstatus(Model model) {
		AjaxRes ar = getAjaxRes();
		AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
		/// 查询出当前车辆的位置
		List<Car> cars = carService.findAdminCarsByAdminName(currentUser.getLoginname());

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

	@RequestMapping(value = "/admin/editGuard", method = RequestMethod.GET)
	public String editGuard(Model model, String alertphone, String shockalert, String cuttinglinealert,
							String autoalert, String sn) {

		AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
		model.addAttribute("currentUser", currentUser);

		System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));

		List<Car> cars = carService.findAdminCarsByAdminName(currentUser.getLoginname());

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


		model.addAttribute("sn", sn);
		model.addAttribute("alertphone", alertphone);
		model.addAttribute("shockalert", shockalert);
		model.addAttribute("cuttinglinealert", cuttinglinealert);
		model.addAttribute("autoalert", autoalert);
		return "/admin/editguard";
	}
}

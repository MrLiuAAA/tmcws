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
import com.github.pagehelper.PageInfo;
import com.qqd.AjaxRes;
import com.qqd.model.*;
import com.qqd.push.App;
import com.qqd.service.*;
import com.qqd.utils.ExcelUtils;
import com.qqd.utils.MD5Util;
import com.qqd.utils.PageData;
import com.qqd.utils.security.AccountShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

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
	public AdminUserService adminUserService;

	@Autowired
	public GpsDataService gpsDataServcie;

	@Autowired
	public SerialsNumberService serialsNumberService;


	@Autowired
	public NoticeService noticeService;

	@Autowired
	public PushNewsService pushNewsService;

	/**
	 * 访问系统首页
	 */
	@RequestMapping("index")
	public String index(HttpSession session) {
		List<Car> cars = null;
		// shiro获取用户信息

		if (AccountShiroUtil.isAdminUser()){
			AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
			session.setAttribute("userName", currentUser.getName());
			session.setAttribute("nickName", currentUser.getName());
			session.setAttribute("role", currentUser.isSuperAdmin()?"superAdmin":"admin");
			session.setAttribute("avatar",currentUser.getAvatar());

			if (currentUser.isSuperAdmin()){
				cars = carService.findAllCars();
			}else{
				cars = carService.findAdminCarsByAdminName(currentUser.getLoginname());
			}

		}else{
			User currentUser = AccountShiroUtil.getCurrentUser();
			session.setAttribute("userName", currentUser.getUsername());
			session.setAttribute("nickName", currentUser.getNickname());
			session.setAttribute("role","user");
			session.setAttribute("avatar",currentUser.getMypic());

			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));
			cars = carService.findCarsByUserName(currentUser.getUsername());
		}




		/// 查询 绑定车辆数目
		int bingCar = cars == null ? 0 : cars.size();
		session.setAttribute("bingNum", bingCar);

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
		session.setAttribute("alertNum", alert);
		session.setAttribute("lostNum", lost);




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
	 * 定位车辆页面
	 *
	 */
	@RequestMapping("openmap")
	public String openmap(Model model,String sn) {

		model.addAttribute("sn", sn);
		return "openmap";
	}

	/**
	 * 选择车辆页面
	 *
	 */
	@RequestMapping("selectcar")
	public String selectcar(Model model) {
		return "select_car";
	}




	@RequestMapping(value = "getcarlocation", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getcarlocation(String sn) {

		System.out.println("-----sn-----:\n"+sn);
		AjaxRes ar = getAjaxRes();
		Car car = carService.findCarBySn(sn);

		ar.setSucceed(car);
		return ar;
	}




	/**
	 * 账户管理
	 *
	 * @Description (TODO这里用一句话描述这个方法的作用)
	 * @param model
	 * @return
	 */
	@RequestMapping("account")
	public String accountManager(Model model) {
		return "usermanage";
	}

	@RequestMapping("member")
	public String memberManager(Model model) {
		return "membermanage";
	}

	@RequestMapping("superadminmanage")
	public String superadminmanager(Model model) {
		return "superadminmanage";
	}

	@RequestMapping("adminmanage")
	public String adminmanager(Model model) {
		return "adminmanage";
	}

	@RequestMapping("push")
	public String pushMessage(Model model) {
		return "messagemanager";
	}
	@RequestMapping("addpush")
	public String addPushMessage(Model model) {
		return "pushmessage";
	}




	@RequestMapping(value = "getmembers", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getmembers(String page) {
		AjaxRes ar = getAjaxRes();
		PageInfo<User> pageInfo;

		pageInfo = userService.findAllUsers(page);


		HashMap<String, Object> map = new HashMap<>();
		map.put("list", pageInfo.getList()); /// 名字
		map.put("pageInfo", pageInfo); /// 标识

		ar.setSucceed(map);

		return ar;
	}

	@RequestMapping(value = "getsuperadminmembers", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getsuperadminmembers(String page) {
		AjaxRes ar = getAjaxRes();
		PageInfo<AdminUser> pageInfo;

		pageInfo = adminUserService.findSuperAdmin(page);


		HashMap<String, Object> map = new HashMap<>();
		map.put("list", pageInfo.getList()); /// 名字
		map.put("pageInfo", pageInfo); /// 标识

		ar.setSucceed(map);

		return ar;
	}

	@RequestMapping(value = "getadminmembers", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getadminmembers(String page) {
		AjaxRes ar = getAjaxRes();
		PageInfo<AdminUser> pageInfo;

		pageInfo = adminUserService.findAdmin(page);


		HashMap<String, Object> map = new HashMap<>();
		map.put("list", pageInfo.getList()); /// 名字
		map.put("pageInfo", pageInfo); /// 标识

		ar.setSucceed(map);

		return ar;
	}



	@RequestMapping(value = "deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUser(String userid) {
		Map<String, Object> map = new HashMap<String, Object>();

		/// 查询出当前车辆的位置
		Boolean ret = userService.deleteUser(userid);
		map.put("result", ret ? "success" : "failure");
		System.out.println(map);

		return map;
	}

	@RequestMapping(value = "saveAdmin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAdmin(AdminUser user) {
		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println("----------:\n"+JSON.toJSONString(user,true));

		user.setPassword(MD5Util.md5Encode(user.getPassword()));

        AdminUser u = adminUserService.save(user);

		map.put("result", u.getId()!=null ? "success" : "failure");
		System.out.println(map);

		return map;
	}

	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addUser(User user) {
		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println("----------:\n"+JSON.toJSONString(user,true));

		/**
		 *  检查 用户名和电话是否已经存在
		 */
		User u1 = userService.findUserByName(user.getUsername());
		if (u1!=null&&u1.getUserid()!=null){
			map.put("result","failure");
			map.put("msg","用户名已经存在！");

			System.out.println(map);
			return map;
		}
		User u2 = userService.findUserByTelephone(user.getTelephone());
		if (u2!=null&&u2.getUserid()!=null){
			map.put("result","failure");
			map.put("msg","电话号码已经存在！");

			System.out.println(map);
			return map;
		}


		user.setPassword(MD5Util.md5Encode(user.getPassword()));

        User u = userService.addUser(user);

		map.put("result", u.getUserid()!=null ? "success" : "failure");
		System.out.println(map);

		return map;
	}





	@RequestMapping("showcarlist")
	public String showcarlist(Model model,String loginname) {

		model.addAttribute("loginname",loginname);
		return "carlist";
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
		List<Car> cars = null;
		// shiro获取用户信息

		if (AccountShiroUtil.isAdminUser()){
			AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));

			if (currentUser.isSuperAdmin()){
				cars = carService.findAllCars();
			}else{
				cars = carService.findAdminCarsByAdminName(currentUser.getLoginname());
			}

		}else{
			User currentUser = AccountShiroUtil.getCurrentUser();
			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));
			cars = carService.findCarsByUserName(currentUser.getUsername());
		}
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
		return "history";
	}

	/**
	 * 获取我的车辆
	 * 
	 * @return
	 */
	@RequestMapping(value = "getmycars", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getmycars() {
		AjaxRes ar = getAjaxRes();
		List<Car> cars = null;
		// shiro获取用户信息

		if (AccountShiroUtil.isAdminUser()){
			AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));
			if (currentUser.isSuperAdmin()){
				cars = carService.findAllCars();
			}else {
				cars = carService.findAdminCarsByAdminName(currentUser.getLoginname());
			}

		}else{
			User currentUser = AccountShiroUtil.getCurrentUser();
			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));
			cars = carService.findCarsByUserName(currentUser.getUsername());
		}
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
	public AjaxRes getTrajectory() {
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
		return "guard";
	}

	/**
	 * 编辑 设防
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "editGuard", method = RequestMethod.GET)
	public String editGuard(Model model,String sn) {


		Car car = carService.findCarBySn(sn);

		model.addAttribute("sn", sn);
		model.addAttribute("alertphone", car.getAlertphone());
		model.addAttribute("shockalert", car.getShockalert());
		model.addAttribute("cuttinglinealert", car.getCuttinglinealert());
		model.addAttribute("autoalert", car.getAutoalert());
		return "fortifyedite";
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

		/// 查询出当前车辆的位置
		Boolean ret = carService.changeCarStatus(status, sn, fieldName);
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
	public AjaxRes getmycarstatus(String page,String keyword) {
		AjaxRes ar = getAjaxRes();
		List<Car> cars = null;
		PageInfo<Car> pageInfo;
		// shiro获取用户信息

		if (AccountShiroUtil.isAdminUser()){
			AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));

			if (currentUser.isSuperAdmin()){
				pageInfo = carService.findAllCars(keyword,page);
			}else{
				pageInfo = carService.findAdminCarsByAdminNameByPage(currentUser.getLoginname(),keyword,page);
			}



		}else{
			User currentUser = AccountShiroUtil.getCurrentUser();
			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));
			pageInfo = carService.findCarsByUserNameByPage(currentUser.getUsername(),keyword,page);
		}
		cars = pageInfo.getList();

		System.out.println("====\n\n\npageInfo信息：" + JSON.toJSONString(pageInfo, true));

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

		HashMap<String, Object> map = new HashMap<>();
		map.put("list", rList); /// 名字
		map.put("pageInfo", pageInfo); /// 标识

		ar.setSucceed(map);
		return ar;
	}


	@RequestMapping("carManage")
	public String carManage(Model model) {
		return "carmanage";
	}



	/**
	 * 车辆管理
	 * 
	 * @return
	 */
	@RequestMapping("getmycarsinfo")
	@ResponseBody
	public AjaxRes getmycarsinfo(String page,String keyword) {
		AjaxRes ar = getAjaxRes();
		List<Car> cars = null;
		HashMap<String, Object> rmap = new HashMap<>();
		PageInfo<Car> pageInfo;
		// shiro获取用户信息

		if (AccountShiroUtil.isAdminUser()){
			AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));


			if (currentUser.isSuperAdmin()){
					///  超级管理员 可以看到全部车辆
				pageInfo = carService.findAllCars(keyword,page);
			}else{
				pageInfo = carService.findAdminCarsByAdminNameByPage(currentUser.getLoginname(),keyword,page);
				rmap.put("isDelete", "N"); /// 二级管理员不能删除车辆
			}


		}else{
			User currentUser = AccountShiroUtil.getCurrentUser();
			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));
			pageInfo = carService.findCarsByUserNameByPage(currentUser.getUsername(),keyword,page);
		}
		cars = pageInfo.getList();

		System.out.println("====\n\n\npageInfo信息：" + JSON.toJSONString(pageInfo, true));

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


		rmap.put("list", rList); /// 数据
		rmap.put("pageInfo", pageInfo); /// 分页

		ar.setSucceed(rmap);

		return ar;
	}

	@RequestMapping("getmycarlistinfo")
	@ResponseBody
	public AjaxRes getmycarlistinfo(String page,String keyword,String loginname) {
		AjaxRes ar = getAjaxRes();
		List<Car> cars = null;
		PageInfo<Car> pageInfo = carService.findAdminAllCarsByAdminNameByPage(loginname,keyword,page);
		cars = pageInfo.getList();

		System.out.println("====\n\n\npageInfo信息：" + JSON.toJSONString(pageInfo, true));

		List<Map<String, String>> rList = new ArrayList<>();



		if (cars != null) {
			for (Car car : cars) {
				HashMap<String, String> map = new HashMap<>();
				map.put("name", car.getName()==null?"未激活":car.getName()); /// 名字
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

		HashMap<String, Object> map = new HashMap<>();
		map.put("list", rList); /// 名字
		map.put("pageInfo", pageInfo); /// 标识

		ar.setSucceed(map);

		return ar;
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
	@RequestMapping(value = "addCarToAdmin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCarToAdmin(String sn,String loginname) {
		Map<String, Object> map = new HashMap<String, Object>();
		User currentUser = AccountShiroUtil.getCurrentUser();

		if (serialsNumberService.validateSN(sn)) {

			/// 验证成功后  解码
			///  将前两位替换为 20  然后再去掉后三位
			sn = "20"+sn.substring(2, 10);

			Boolean result = carService.addCarToAdmin(loginname, sn);

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

		if (AccountShiroUtil.isAdminUser()){
			AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();

			if (currentUser.isSuperAdmin()){
				Boolean ret = carService.deleteCarBySn(sn);
				map.put("result", ret ? "success" : "failure");
			}else{
				map.put("result", "没有权限");
			}

		}else{
			User currentUser = AccountShiroUtil.getCurrentUser();
			/// 查询出当前车辆的位置
			Boolean ret = carService.deleteCar(currentUser.getUsername(), sn);
			map.put("result", ret ? "success" : "failure");
		}


		System.out.println(map);
		return map;
	}

	/**
	 * deleteAdmin
	 */
	@RequestMapping(value = "deleteAdmin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAdmin(String loginname) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (AccountShiroUtil.isAdminUser()){
			AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();

			if (currentUser.isSuperAdmin()){
				map.put("result",adminUserService.deleteAdmin(loginname)?"success":"failure");
			}else{
				map.put("result","failure");
				map.put("msg","没有权限");
			}

		}else{

			map.put("result","failure");
			map.put("msg","没有权限");
		}


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
		List<Car> cars = null;
		// shiro获取用户信息

		if (AccountShiroUtil.isAdminUser()){
			AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));

			if (currentUser.isSuperAdmin()){
				cars = carService.findAllCars();
			}else{
				cars = carService.findAdminCarsByAdminName(currentUser.getLoginname());
			}


		}else{
			User currentUser = AccountShiroUtil.getCurrentUser();
			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));
			cars = carService.findCarsByUserName(currentUser.getUsername());
		}
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



	@RequestMapping("person")
	public String personalInfo(Model model) {
		User currentUser = AccountShiroUtil.getCurrentUser();
		model.addAttribute("user",currentUser);
		return "person";
	}
	@RequestMapping("adminperson")
	public String adminpersonInfo(Model model) {

		AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
		model.addAttribute("user",currentUser);
		return "adminperson";
	}




	// 添加
	@RequestMapping(method = RequestMethod.POST, value = "importExcel")
	@ResponseBody
	public Map<String, Object> importExcel(@RequestParam(value="carExcel",required=true) MultipartFile file , HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String loginname = request.getParameter("loginname");
			String name = file.getOriginalFilename();
			InputStream inputStream = null;

			inputStream = file.getInputStream();

			ExcelUtils utils = new ExcelUtils(inputStream,name);
			List<String> sns = null;
			try {
				sns = utils.getSNs();

				Boolean y = true;
				String wrongSN = "";
				for (String sn:sns) {

					if (serialsNumberService.validateSN(sn)) {
					} else {
						y=false;
						wrongSN = sn;
						break;
					}
				}


				if (y){
					Boolean result = carService.addCarsToAdmin(loginname, sns);
					System.out.println("result = " + result);
					map.put("result", result ? "success" : "failure");
					if (result) {
						map.put("msg", "添加失败");
					}
				}else{
					map.put("result", "failure");
					map.put("msg", "序列号："+wrongSN+"验证失败");
				}


			} catch (Exception e) {
				e.printStackTrace();
				map.put("result", "failure");
				map.put("msg", "解析文件出错！");
			}





			////  在这里读取出 要导入的车辆信息
		} catch (IOException e) {
			e.printStackTrace();
			map.put("result", "failure");
			map.put("msg", "保存失败");
		}

		return map;

	}

	// 添加
	@RequestMapping(method = RequestMethod.POST, value = "uploadImg")
	@ResponseBody
	public AjaxRes uploadImg(HttpSession session,@RequestParam(value="imgStr",required=true) String imgStr) {
		AjaxRes ar = new AjaxRes();

		if(!"#nochange*".equals(imgStr)){
			BASE64Decoder decoder = new BASE64Decoder();
			String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();

//			String basePath = "C:\\web";
//			String basePath = "/Users/liujianyang/Desktop/";
			String basePath = "/usr/local/gps_pc_server";
			String urlPath = "/avatarImages/" + uuid + ".jpg";
			String filePath = basePath+urlPath;


			// Base64解码
			byte[] b;
			try {
				b = decoder.decodeBuffer(imgStr.split(",")[1]);

				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {// 调整异常数据
						b[i] += 256;
					}
				}
				// 生成jpeg图片
				OutputStream out = new FileOutputStream(filePath);
				out.write(b);
				out.flush();
				out.close();


				///  保存到数据库


				if (AccountShiroUtil.isAdminUser()){
					AdminUser currentUser = AccountShiroUtil.getCurrentAdminUser();
					System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));

					AdminUser user = new AdminUser();
					user.setId(currentUser.getId());
					user.setLoginname(currentUser.getLoginname());
					user.setAvatar(urlPath);
					adminUserService.updateAdminAvatar(user);

					AdminUser realCurrentUser = AccountShiroUtil.getRealCurrentAdminUser();
					realCurrentUser.setAvatar(user.getAvatar());

					session.setAttribute("avatar",currentUser.getAvatar());
				}else{
					User currentUser = AccountShiroUtil.getCurrentUser();
					System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));

					User user = new User();
					user.setUserid(currentUser.getUserid());
					user.setMypic(urlPath);
					userService.updateUserAvatar(user);

					User realCurrentUser = AccountShiroUtil.getRealCurrentUser();
					realCurrentUser.setMypic(user.getMypic());

					session.setAttribute("avatar",currentUser.getMypic());
				}




				ar.setSucceedMsg("上传文件成功");
			} catch (Exception e) {
				ar.setFailMsg("上传文件失败");
				e.printStackTrace();
			}
		}
		else{
			ar.setFailMsg("上传文件失败");
		}

		return ar;

	}


	@RequestMapping("news")
	public String messageInfo(Model model) {
		///  获取当前用户的  消息列表
//		User currentUser = AccountShiroUtil.getCurrentUser();
//
//		List<Notice> notices = noticeService.findNoticesByUserName(currentUser.getUsername());
//
//		model.addAttribute("notices", notices);

		return "news";
	}




	@RequestMapping(method = RequestMethod.POST, value = "getmycarnews")
	@ResponseBody
	public AjaxRes getmycarnews(String page) {

		AjaxRes ar = getAjaxRes();
		List<Notice> notices = null;
		PageInfo<Notice> pageInfo;
		// shiro获取用户信息


		///  获取当前用户的  消息列表
		User currentUser = AccountShiroUtil.getCurrentUser();

		pageInfo = noticeService.findNoticesByUserNameByPage(currentUser.getUsername(),page);

//		model.addAttribute("notices", notices);

		HashMap<String, Object> map = new HashMap<>();
		map.put("list", pageInfo.getList()); /// 名字
		map.put("pageInfo", pageInfo); /// 标识

		ar.setSucceed(map);

		return ar;
	}

	@RequestMapping(method = RequestMethod.POST, value = "deletenews")
	@ResponseBody
	public AjaxRes deletenews(String noticeid) {

		AjaxRes ar = getAjaxRes();

		boolean r = noticeService.deleteNoitce(noticeid);

		if (r){
			ar.setSucceedMsg("删除成功");
		}else{
			ar.setFailMsg("删除失败");
		}
		return ar;
	}
	@RequestMapping(method = RequestMethod.POST, value = "deleteallnews")
	@ResponseBody
	public AjaxRes deleteallnews() {

		AjaxRes ar = getAjaxRes();

		User currentUser = AccountShiroUtil.getCurrentUser();
		boolean r = noticeService.deleteAllNoitceByUserName(currentUser.getUsername());
		if (r){
			ar.setSucceedMsg("删除成功");
		}else{
			ar.setFailMsg("删除失败");
		}
		return ar;
	}





	@RequestMapping("aboutUs")
	public String aboutUs(Model model) {
		return "about";
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


	@RequestMapping(value = "savePushNews", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savePushNews(PushNews news) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			news = pushNewsService.save(news);
			//
			if(news!=null&&news.getId()!=null){
				map.put("result", "success" );
			}else{
				map.put("result", "failure");
				map.put("msg", "保存失败");
			}

			App app = new App();

			List<User> list = userService.findUsers();


			String web_url = "http://user.tiamu.cn/news?id="+news.getId();

			for (User u:
				 list) {
				if (u.getPushtoken()!=null
						&&u.getPushtoken().length()>0){
					if ("ios".equalsIgnoreCase(u.getOs()))
					{
						System.out.println("推送苹果token："+u.getPushtoken());
						app.sendIOSUnicast(u.getPushtoken(), 0, news.getTitle(),web_url,"default");
					}else {
						System.out.println("推送Android 的 token："+u.getPushtoken());
						app.sendAndroidUnicast(u.getPushtoken(), news.getTitle(),web_url,"default");
					}
				}

			}
		}catch (Exception e){
			e.printStackTrace();
			map.put("result", "failure");
			map.put("msg", "保存失败");
		}


		return map;
	}





	@RequestMapping("getPushNews")
	@ResponseBody
	public AjaxRes getPushNews(String page,String keyword) {
		AjaxRes ar = getAjaxRes();
		List<PushNews> news = null;
		HashMap<String, Object> rmap = new HashMap<>();
		PageInfo<PushNews> pageInfo;
		// shiro获取用户信息


			User currentUser = AccountShiroUtil.getCurrentUser();
			System.out.println("当前用户：" + JSON.toJSONString(currentUser, true));
			pageInfo = pushNewsService.findPushNewsByPage(page);

		news = pageInfo.getList();

		System.out.println("====\n\n\npageInfo信息：" + JSON.toJSONString(pageInfo, true));

		List<Map<String, String>> rList = new ArrayList<>();





		rmap.put("list", news); /// 数据
		rmap.put("pageInfo", pageInfo); /// 分页

		ar.setSucceed(rmap);

		return ar;
	}


	/**
	 * deleteCar
	 */
	@RequestMapping(value = "deletePushNews", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePushNews(String id) {
		Map<String, Object> map = new HashMap<String, Object>();

		Boolean ret = pushNewsService.deletePushNews(id);
		map.put("result", ret ? "success" : "failure");
		System.out.println(map);
		return map;
	}

}

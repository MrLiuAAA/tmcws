package com.qqd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.qqd.service.CarService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;


public class AlertJob implements Job {

	private String sn;
	private String alertstatus;
	private String username;

	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAlertstatus() {
		return alertstatus;
	}
	public void setAlertstatus(String alertstatus) {
		this.alertstatus = alertstatus;
	}


	@Autowired
	CarService carService;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		// 如果在Job类中定义与JobDataMap中键值一致的set和get方法，那么Quartz会自动将这些属性注入
		
//		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
//        String name = jobDataMap.getString("name");
        
		////  时间到了 将设防开启或者关闭


		carService.changeCarStatus(username, alertstatus, sn, "alertstatus");

		/**
		 * 设防：*HQ,8696010765,SCF,113032,0,0#
		 * 撤防：*HQ,8696010765,SCF,113032,1,0#
		 */
//		Car car = Car.me.findFirst("select * from g_car where sn = ? ", sn);
//		Date date=new Date();
//		DateFormat format=new SimpleDateFormat("HHmmss");
//		String time = format.format(date);
//		String code = car.getStr("code");
//		String cmdString = "*"+code+"," + sn + ",SCF," + time+","+
//				("Y".equalsIgnoreCase(alertstatus)?"0":"1")+",0#";
//
//		boolean ok = MessageQueue.me.insertMessageQueue(sn,code, "SCF", cmdString);
//
//		if(ok&&CarService.editCar(sn, "alertstatus", alertstatus)){
//			// 更新设备的设置
//			System.out.println("成功插入设防指令："+cmdString);
//		}else{
//
//		}
		
	}

}

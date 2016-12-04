package com.qqd;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;  
  
/** *//** 
 * @Title:Quartz管理类 
 *  
 * @Description: 
 *  
 quartz 时间配置规则
 格式: [秒] [分] [小时] [日] [月] [周] [年]

 序号	说明	 是否必填	 允许填写的值	允许的通配符
 1	 秒	 是	 0-59 	  , - * /
 2	 分	 是	 0-59	  , - * /
 3	小时	 是	 0-23	  , - * /
 4	 日	 是	 1-31	  , - * ? / L W
 5	 月	 是	 1-12 or JAN-DEC	  , - * /
 6	 周	 是	 1-7 or SUN-SAT	  , - * ? / L #
 7	 年	 否	 empty 或 1970-2099	 , - * /
通配符说明:
* 表示所有值. 例如:在分的字段上设置 "*",表示每一分钟都会触发。
? 表示不指定值。使用的场景为不需要关心当前设置这个字段的值。例如:要在每月的10号触发一个操作，但不关心是周几，所以需要周位置的那个字段设置为"?" 具体设置为 0 0 0 10 * ?
- 表示区间。例如 在小时上设置 "10-12",表示 10,11,12点都会触发。
, 表示指定多个值，例如在周字段上设置 "MON,WED,FRI" 表示周一，周三和周五触发
/ 用于递增触发。如在秒上面设置"5/15" 表示从5秒开始，每增15秒触发(5,20,35,50)。在月字段上设置'1/3'所示每月1号开始，每隔三天触发一次。
L 表示最后的意思。在日字段设置上，表示当月的最后一天(依据当前月份，如果是二月还会依据是否是润年[leap]), 在周字段上表示星期六，相当于"7"或"SAT"。如果在"L"前加上数字，则表示该数据的最后一个。例如在周字段上设置"6L"这样的格式,则表示“本月最后一个星期五"
W 表示离指定日期的最近那个工作日(周一至周五). 例如在日字段上设置"15W"，表示离每月15号最近的那个工作日触发。如果15号正好是周六，则找最近的周五(14号)触发, 如果15号是周未，则找最近的下周一(16号)触发.如果15号正好在工作日(周一至周五)，则就在该天触发。如果指定格式为 "1W",它则表示每月1号往后最近的工作日触发。如果1号正是周六，则将在3号下周一触发。(注，"W"前只能设置具体的数字,不允许区间"-").


'L'和 'W'可以一组合使用。如果在日字段上设置"LW",则表示在本月的最后一个工作日触发(一般指发工资 ) 

# 序号(表示每月的第几个周几)，例如在周字段上设置"6#3"表示在每月的第三个周六.注意如果指定"#5",正好第五周没有周六，则不会触发该配置(用在母亲节和父亲节再合适不过了)

周字段的设置，若使用英文字母是不区分大小写的 MON 与mon相同.

 * 
 */  
public class QuartzManager {  
   private static SchedulerFactory sf = new StdSchedulerFactory();  
   private static String JOB_GROUP_NAME = "tmcws_job_group";  
   private static String TRIGGER_GROUP_NAME = "tmcws_trigger_group";  
    
     
   /** *//** 
    *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
    * @param jobName 任务名 
    * @param class1     任务 
    * @param time    时间设置，参考quartz说明文档 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void addJob(String jobName,Class<AlertJob> job,JobDataMap map,String time)   
                               throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler();  
       
       JobBuilder jobBuilder = newJob(job).withIdentity(jobName, JOB_GROUP_NAME);
       jobBuilder.usingJobData(map);
       JobDetail jobDetail  = jobBuilder.build();

       // /触发器名,触发器组    触发器时间设定 
       CronTrigger trigger = newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME).withSchedule(cronSchedule(time))
    	        .build();       
       
       sched.scheduleJob(jobDetail,trigger);  
       //启动  
       if(!sched.isShutdown())  
          sched.start();  
   }  
     
   /** *//** 
    * 添加一个定时任务 
    * @param jobName 任务名 
    * @param jobGroupName 任务组名 
    * @param triggerName  触发器名 
    * @param triggerGroupName 触发器组名 
    * @param job     任务 
    * @param time    时间设置，参考quartz说明文档 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void addJob(String jobName,String jobGroupName,  
                             String triggerName,String triggerGroupName,  
                             Class<AlertJob> job,JobDataMap map,String time)   
                               throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler();  
       JobBuilder jobBuilder = newJob(job).withIdentity(jobName, jobGroupName);
       jobBuilder.usingJobData(map);
       JobDetail jobDetail  = jobBuilder.build();
       
       // /触发器名,触发器组    触发器时间设定 
       CronTrigger trigger = newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(cronSchedule(time))
    	        .build();
       
       sched.scheduleJob(jobDetail,trigger);  
       if(!sched.isShutdown())  
          sched.start();  
   }  
     
   /** *//** 
    * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
    * @param jobName 
    * @param time 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void modifyJobTime(String jobName,String time)   
                                  throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler();  
       CronTrigger trigger = (CronTrigger)sched.getTrigger(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME));
       
       if(trigger != null){  
           CronTrigger newTrigger = newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME).withSchedule(cronSchedule(time))
       	        .build();
           sched.rescheduleJob(trigger.getKey(), newTrigger);
           
       }  
   }  
     
   /** *//** 
    * 修改一个任务的触发时间 
    * @param triggerName 
    * @param triggerGroupName 
    * @param time 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void modifyJobTime(String triggerName,String triggerGroupName,  
                                    String time)   
                                  throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler();  
       
       CronTrigger trigger = (CronTrigger)sched.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
       
       if(trigger != null){  
           CronTrigger newTrigger = newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(cronSchedule(time))
       	        .build();
           sched.rescheduleJob(trigger.getKey(), newTrigger);
           
       }
   }  
     
   /** *//** 
    * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
    * @param jobName 
    * @throws SchedulerException 
    */  
   public static void removeJob(String jobName)   
                               throws SchedulerException{  
       Scheduler sched = sf.getScheduler();  
       sched.pauseTrigger(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME));//停止触发器  
       sched.unscheduleJob(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME));
       sched.deleteJob(JobKey.jobKey(jobName, JOB_GROUP_NAME));
   }  
     
   /** *//** 
    * 移除一个任务 
    * @param jobName 
    * @param jobGroupName 
    * @param triggerName 
    * @param triggerGroupName 
    * @throws SchedulerException 
    */  
   public static void removeJob(String jobName,String jobGroupName,  
                                String triggerName,String triggerGroupName)   
                               throws SchedulerException{  
       Scheduler sched = sf.getScheduler();  
       sched.pauseTrigger(TriggerKey.triggerKey(triggerName,triggerGroupName));//停止触发器  
       sched.unscheduleJob(TriggerKey.triggerKey(triggerName,triggerGroupName));//移除触发器  
       sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));//删除任务 
       
   }  
}  
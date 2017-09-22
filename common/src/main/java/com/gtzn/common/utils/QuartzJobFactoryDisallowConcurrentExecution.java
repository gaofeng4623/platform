package com.gtzn.common.utils;

import com.gtzn.modules.sys.entity.ScheduleJob;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 类说明：若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作 
 * @author nieg
 * @创建时间 2017年2月16日
 */
@DisallowConcurrentExecution  
public class QuartzJobFactoryDisallowConcurrentExecution implements Job{
	 public final Logger log = Logger.getLogger(this.getClass());  
	  
	    public void execute(JobExecutionContext context) throws JobExecutionException {  
	        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
	        TaskUtils.invokMethod(scheduleJob);  
	  
	    } 
}

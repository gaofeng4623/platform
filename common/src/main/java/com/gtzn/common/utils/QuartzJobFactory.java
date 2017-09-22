package com.gtzn.common.utils;


import com.gtzn.modules.sys.entity.ScheduleJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 类说明：计划任务执行处 无状态 
 * @author nieg
 * @创建时间 2017年2月16日
 */
public class QuartzJobFactory implements Job{
	//  public final Logger log = Logger.getLogger(this.getClass());  
	  
	    public void execute(JobExecutionContext context) throws JobExecutionException {  
	        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
	        TaskUtils.invokMethod(scheduleJob);  
	    }
}

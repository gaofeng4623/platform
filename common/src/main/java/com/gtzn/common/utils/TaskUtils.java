package com.gtzn.common.utils;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.modules.sys.entity.ScheduleJob;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TaskUtils {
	public final static Logger log = Logger.getLogger(TaskUtils.class);  
	//private static RecImportService recImportService = SpringContextHolder.getBean(RecImportService.class);
    /** 
     * 通过反射调用scheduleJob中定义的方法 
     *  
     * @param scheduleJob 
     */  
    public static void invokMethod(ScheduleJob scheduleJob) {
        Object object = null;
        Class clazz = null;
                //springId不为空先按springId查找bean  
        if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
            object = SpringContextHolder.getBean(scheduleJob.getSpringId());
        } else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
            try {  
                clazz = Class.forName(scheduleJob.getBeanClass());
                object = clazz.newInstance();  
            } catch (Exception e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
  
        }  
        if (object == null) {  
            log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查是否配置正确！！！");  
            return;  
        }  
        clazz = object.getClass();  
        Method method = null;
        try {  
            method = clazz.getDeclaredMethod(scheduleJob.getMethodName());  
        } catch (NoSuchMethodException e) {
            log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！");  
        } catch (SecurityException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        if (method != null) {  
            try {  
                method.invoke(object);  
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
          
    }  
    
    public static Ajax addJob(ScheduleJob job) throws SchedulerException{
    	if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			return new Ajax(false,"任务不存在或者任务未启用");
		}
		//SchedulerFactoryBean schedulerFactoryBean=SpringContextHolder.getBean("schedulerFactoryBean");
		//Scheduler scheduler = schedulerFactoryBean.getScheduler();
		Scheduler scheduler = SpringContextHolder.getBean("schedulerFactoryBean");
		System.out.println("jobAdd****************************************");
		log.debug(scheduler + ".......................................................................................add");
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			//有状态、无状态任务，是否需要等待上一个任务结束才能开启下一个任务
			Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;

			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();

			jobDetail.getJobDataMap().put("scheduleJob", job);

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
		return new Ajax(true,"任务添加成功！");
    }
}

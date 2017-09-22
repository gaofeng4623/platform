/**
 *
 */
package com.gtzn.modules.sys.service.impl;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.common.utils.ScheduleJobUtils;
import com.gtzn.modules.sys.dao.ScheduleJobDao;
import com.gtzn.modules.sys.entity.ScheduleJob;
import com.gtzn.modules.sys.service.ScheduleJobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务调度管理Service
 * @author GF
 * @version 2017-02-15
 */
@Service("scheduleJob")
@Transactional(readOnly = true)
public class ScheduleJobServiceImpl extends CrudService<ScheduleJobDao, ScheduleJob> implements ScheduleJobService {

	public ScheduleJob get(String id) {
		return super.get(id);
	}
	
	public List<ScheduleJob> findList(ScheduleJob scheduleJob) {
		return super.findList(scheduleJob);
	}
	
	public Pager<ScheduleJob> findPage(Pager<ScheduleJob> pager, ScheduleJob scheduleJob) {
		Pager<ScheduleJob> p = super.findPage(pager, scheduleJob);
		List<ScheduleJob> list = p.getList();
		//所有计划任务
		List<ScheduleJob> l1 = ScheduleJobUtils.getAllJob();
		for(ScheduleJob s:list){
			for(ScheduleJob s1:l1){
				if(s.getJobName().equals(s1.getJobName())){
					s.setJobRunStatus(s1.getJobStatus());
					/*NORMAL 正常
					COMPLETE 结束
					ERROR 错误
					BLOCKED 锁定
					PAUSED 暂停*/
					//已加入计划任务
				}
			}
		}
		//正在运行的计划任务
		/*List<ScheduleJob> l2 = ScheduleJobUtils.getRunningJob();
		for(ScheduleJob s:list){
			for(ScheduleJob s1:l2){
				if(s.getJobName().equals(s1.getJobName())){
					s.setJobRunStatus("2");//正在运行的计划任务
				}
			}
		}
		ScheduleJobUtils.getPauseJob();*/
		return p;
	}
	
	@Transactional(readOnly = false)
	public void save(ScheduleJob scheduleJob) {
		//时间表达式组合
		//	*/5 * * * * ? 每五秒钟执行一次
		// * */2 * * * ? 每两分钟执行一次
		// 0 15 10 * * ?  每天十点十五分钟执行一次
		//0 15 10 15 * ? 每月十五号十点十五分执行一次
		String cronStr="";
		if(scheduleJob.getJobType().equals("1")){//每x分钟执行一次
			cronStr="0 */"+scheduleJob.getFenz()+" * * * ?";
			scheduleJob.setCronDesc("每"+scheduleJob.getFenz()+"分钟执行一次");
		}else if(scheduleJob.getJobType().equals("2")){//每天x时x分执行一次
			cronStr="0 "+scheduleJob.getFenz()+" "+scheduleJob.getXiaos()+" * * ?";
			scheduleJob.setCronDesc("每天"+scheduleJob.getXiaos()+"点"+scheduleJob.getFenz()+"分执行一次");
		}else if(scheduleJob.getJobType().equals("3")){//每月x号x时x分执行
			cronStr="0 "+scheduleJob.getFenz()+" "+scheduleJob.getXiaos()+" "+scheduleJob.getTians()+" * ?";
			scheduleJob.setCronDesc("每月"+scheduleJob.getTians()+"号"+scheduleJob.getXiaos()+"点"+scheduleJob.getFenz()+"分执行一次");
		}
		//不是新添加的任务，并且任务是启用的
	/*	if(scheduleJob.getJobStatus().equals("1") && cronStr.equals(scheduleJob.getCronExpression())){
			ScheduleJobUtils.updateJobCron(scheduleJob);
		}*/
		scheduleJob.setCronExpression(cronStr);
		super.save(scheduleJob);
	}
	
	public List<ScheduleJob> existJobName(String name){
		return dao.existJobName(name);
	}
	
	@Transactional(readOnly = false)
	public int delete(ScheduleJob scheduleJob) {
		return super.delete(scheduleJob);
	}
	
	public List<ScheduleJob> findAllList(ScheduleJob scheduleJob){
		return super.findAllList(scheduleJob);
	}
}
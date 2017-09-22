/**
 * 
 */
package com.gtzn.modules.sys.web;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.SpringContextHolder;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.utils.TaskUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.sys.entity.ScheduleJob;
import com.gtzn.modules.sys.service.ScheduleJobService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 任务调度管理Controller
 * @author GF
 * @version 2017-02-15
 */
@Controller
@RequestMapping(value = "/sys/scheduleJob")
public class ScheduleJobController extends BaseController {
	 public final Logger log = Logger.getLogger(this.getClass());  

	@Autowired
	private ScheduleJobService scheduleJobService;
	@ModelAttribute
	public ScheduleJob get(@RequestParam(required=false) String id) {
		ScheduleJob entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scheduleJobService.get(id);
		}
		if (entity == null){
			entity = new ScheduleJob();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:scheduleJob:view")
	@RequestMapping(value = {"list"})
	public String list(ScheduleJob scheduleJob, Model model) {
		return "modules/sys/scheduleJob/scheduleJobList";
	}
	
	@RequiresPermissions("sys:scheduleJob:view")
	@RequestMapping(value = {"load"})
	@ResponseBody
	public Pager<ScheduleJob> load(Pager<ScheduleJob> pager, ScheduleJob scheduleJob, HttpServletRequest request, HttpServletResponse response) {
		Pager<ScheduleJob> page = scheduleJobService.findPage(pager, scheduleJob);
		return page;
	}
	@RequiresPermissions("sys:scheduleJob:view")
	@RequestMapping(value = "form")
	public String form(ScheduleJob scheduleJob, Model model) {
		model.addAttribute("scheduleJob", scheduleJob);
		return "modules/sys/scheduleJob/scheduleJobForm";
	}

	@RequiresPermissions("sys:scheduleJob:edit")
	@RequestMapping(value = "save")
	public String save(ScheduleJob scheduleJob, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scheduleJob)){
			return form(scheduleJob, model);
		}
		scheduleJobService.save(scheduleJob);
		addMessage(redirectAttributes, "保存任务成功");
		return "redirect:"+ "/sys/scheduleJob/?repage";
	}
	
	/**
	 * 添加任务
	 * 
	 * @param job
	 * @throws SchedulerException
	 */
	@RequiresPermissions("sys:scheduleJob:edit")
	@RequestMapping(value = "addJob")
	@ResponseBody
	public Ajax addJob(ScheduleJob job) throws SchedulerException {
		
		return TaskUtils.addJob(job);
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@RequiresPermissions("sys:scheduleJob:edit")
	@RequestMapping(value = "pauseJob")
	@ResponseBody
	public Ajax pauseJob(ScheduleJob scheduleJob){
		Scheduler scheduler = SpringContextHolder.getBean("schedulerFactoryBean");
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		try {
			scheduler.pauseJob(jobKey);
			return new Ajax(true,"暂停《"+scheduleJob.getJobName()+"》任务成功!");
		} catch (SchedulerException e) {
			System.out.println("暂停任务调度失败***********"+e.toString());
			return new Ajax(true,"暂停《"+scheduleJob.getJobName()+"》任务失败!");
		}
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@RequiresPermissions("sys:scheduleJob:edit")
	@RequestMapping(value = "resumeJob")
	@ResponseBody
	public Ajax resumeJob(ScheduleJob scheduleJob){
		Scheduler scheduler = SpringContextHolder.getBean("schedulerFactoryBean");
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		try {
			scheduler.resumeJob(jobKey);
			return new Ajax(true,"恢复《"+scheduleJob.getJobName()+"》任务成功!");
		} catch (SchedulerException e) {
			System.out.println("恢复任务调度失败***********"+e.toString());
			return new Ajax(true,"恢复《"+scheduleJob.getJobName()+"》任务失败!");
		}
	}

	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@RequiresPermissions("sys:scheduleJob:edit")
	@RequestMapping(value = "deleteJob")
	@ResponseBody
	public Ajax deleteJob(ScheduleJob scheduleJob){
		Scheduler scheduler = SpringContextHolder.getBean("schedulerFactoryBean");
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		try {
			scheduler.deleteJob(jobKey);
			scheduleJob.setJobStatus("0");
			scheduleJobService.save(scheduleJob);
			return new Ajax(true,"停止《"+scheduleJob.getJobName()+"》任务成功!");
		} catch (SchedulerException e) {
			System.out.println("停止任务调度失败***********"+e.toString());
			return new Ajax(true,"停止《"+scheduleJob.getJobName()+"》任务失败!");
		}
	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@RequiresPermissions("sys:scheduleJob:edit")
	@RequestMapping(value = "runAJobNow")
	@ResponseBody
	public Ajax runAJobNow(ScheduleJob scheduleJob){
		/*SchedulerFactoryBean schedulerFactoryBean=SpringContextHolder.getBean("schedulerFactoryBean");
		Scheduler scheduler = schedulerFactoryBean.getScheduler();*/
		Scheduler scheduler = SpringContextHolder.getBean("schedulerFactoryBean");
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		try {
			scheduler.triggerJob(jobKey);
			return new Ajax(true,"手工执行任务成功!");
		} catch (SchedulerException e) {
			System.out.println("手工执行任务失败***************"+e.toString());
			return new Ajax(false,"手工执行任务失败!");
		}
		
	}
	@RequiresPermissions("sys:scheduleJob:edit")
	@RequestMapping(value = "existJobName")
	@ResponseBody
	public Ajax existJobName(String name, String id){
		List<ScheduleJob> list = scheduleJobService.existJobName(name);
		boolean b=false;
		if(StringUtils.isNotBlank(id)){//修改
			if(null !=list && list.size()>0){
				for(ScheduleJob sj:list){
					if(!sj.getId().equals(id)){
						b=true;
					}
				}
			}
		}else{
			if(null !=list && list.size()>0){
				b=true;
			}
		}
		
		if(b){
			return new Ajax(false,"任务名称重复!");
		}else{
			return new Ajax(true,"名称不重复!");
		}
	}

	
	@RequiresPermissions("sys:scheduleJob:edit")
	@RequestMapping(value = "save2")
	@ResponseBody
	public Ajax save2(ScheduleJob scheduleJob, Model model) {
		scheduleJobService.save(scheduleJob);
		return new Ajax(true,  "保存任务成功");
	}
	
	@RequiresPermissions("sys:scheduleJob:edit")
	@RequestMapping(value = "delete")
	public String delete(ScheduleJob scheduleJob, RedirectAttributes redirectAttributes) {
		scheduleJobService.delete(scheduleJob);
		addMessage(redirectAttributes, "删除任务成功");
		return "redirect:"+ "/sys/scheduleJob/list";
	}

}
/**
 * 
 */
package com.gtzn.modules.sys.entity;

import com.gtzn.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 任务调度管理Entity
 * @author GF
 * @version 2017-02-15
 */
public class ScheduleJob extends DataEntity<ScheduleJob> {
	
	private static final long serialVersionUID = 1L;
	public static final String STATUS_RUNNING = "1";   //启用状态
    public static final String STATUS_NOT_RUNNING = "0";
    public static final String CONCURRENT_IS = "1";
    public static final String CONCURRENT_NOT = "0";
	private String jobName;		// 任务名称
	private String jobGroup;		// 任务分组
	private String jobStatus;		// 任务启用状态
	private String cronExpression;		// 时间表达式
	private String cronDesc;//时间表达式描述
	private String jobType;//任务类型 1每分钟，2每小时，3每天，4每周，5每月
	private Integer fenz;
	private Integer xiaos;
	private Integer tians;
	private String zhous;
	private String yues;
	private String description;		// 描述
	private String beanClass;		// 任务类
	private String isConcurrent;		// 当前任务状态
	private String springId;		// SpringId
	private String methodName;		// 任务调用的方法名
	private String jobRunStatus;//1 已经加入任务，2正在运行的任务
	
	public ScheduleJob() {
		super();
	}

	public ScheduleJob(String id){
		super(id);
	}

	@Length(min=0, max=60, message="任务名称长度必须介于 0 和 60 之间")
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@Length(min=0, max=60, message="任务分组长度必须介于 0 和 60 之间")
	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	
	@Length(min=0, max=1, message="任务状态长度必须介于 0 和 1 之间")
	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	@Length(min=0, max=60, message="时间表达式长度必须介于 0 和 60 之间")
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	@Length(min=0, max=200, message="描述长度必须介于 0 和 200 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=100, message="任务类长度必须介于 0 和 100 之间")
	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	
	@Length(min=0, max=1, message="当前任务状态长度必须介于 0 和 1 之间")
	public String getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}
	
	@Length(min=0, max=60, message="SpringId长度必须介于 0 和 60 之间")
	public String getSpringId() {
		return springId;
	}

	public void setSpringId(String springId) {
		this.springId = springId;
	}
	
	@Length(min=0, max=30, message="任务调用的方法名长度必须介于 0 和 30 之间")
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Integer getFenz() {
		return fenz;
	}

	public void setFenz(Integer fenz) {
		this.fenz = fenz;
	}

	public Integer getXiaos() {
		return xiaos;
	}

	public void setXiaos(Integer xiaos) {
		this.xiaos = xiaos;
	}

	public Integer getTians() {
		return tians;
	}

	public void setTians(Integer tians) {
		this.tians = tians;
	}

	public String getZhous() {
		return zhous;
	}

	public void setZhous(String zhous) {
		this.zhous = zhous;
	}

	public String getYues() {
		return yues;
	}

	public void setYues(String yues) {
		this.yues = yues;
	}

	public String getCronDesc() {
		return cronDesc;
	}

	public void setCronDesc(String cronDesc) {
		this.cronDesc = cronDesc;
	}

	public String getJobRunStatus() {
		return jobRunStatus;
	}

	public void setJobRunStatus(String jobRunStatus) {
		this.jobRunStatus = jobRunStatus;
	}
	
}
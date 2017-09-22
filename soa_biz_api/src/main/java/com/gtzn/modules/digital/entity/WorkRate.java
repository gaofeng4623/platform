/**
 * 
 */
package com.gtzn.modules.digital.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.gtzn.common.persistence.DataEntity;
import com.gtzn.modules.sys.entity.User;

/**
 * 工作进度跟踪Entity
 * @author li_gm
 */
public class WorkRate extends DataEntity<WorkRate> {
	
	private static final long serialVersionUID = 1L;
	private String deptName;		// 部门名称
	private User user;
	private String workType; //工作任务类型 借阅类别：利用；鉴定类别：濒危,划控,开放,密级,销毁,延期,遗失；征集类别：捐赠,寄存,交换,征购
	private String workTypeName;
	private int workNum;		// 数量
	private String workUnit;//单位
	private Date date;		// 时间
	public WorkRate() {
		super();
	}

	public WorkRate(String id){
		super(id);
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getWorkNum() {
		return workNum;
	}

	public void setWorkNum(int workNum) {
		this.workNum = workNum;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getWorkTypeName() {
		return workTypeName;
	}

	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}

	
	
}
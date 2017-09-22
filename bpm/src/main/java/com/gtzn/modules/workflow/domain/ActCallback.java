/**
 * 
 */
package com.gtzn.modules.workflow.domain;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.gtzn.common.persistence.DataEntity;

/**
 * 撤回Entity
 *
 */
public class ActCallback extends DataEntity<ActCallback> {
	
	private static final long serialVersionUID = 1L;
	private String taskid;	//任务id
	private Date callbacktime;		// 撤回时间
	private String callbackreason;		// 撤回原因
	private String busno;//业务id
	
	public ActCallback() {
		super();
	}

	public ActCallback(String id){
		super(id);
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCallbacktime() {
		return callbacktime;
	}

	public void setCallbacktime(Date callbacktime) {
		this.callbacktime = callbacktime;
	}

	public String getCallbackreason() {
		return callbackreason;
	}

	public void setCallbackreason(String callbackreason) {
		this.callbackreason = callbackreason;
	}

	public String getBusno() {
		return busno;
	}

	public void setBusno(String busno) {
		this.busno = busno;
	}
	
	
	
}
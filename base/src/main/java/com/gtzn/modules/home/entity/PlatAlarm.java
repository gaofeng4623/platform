/**
 * 
 */
package com.gtzn.modules.home.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.gtzn.common.persistence.DataEntity;

/**
 * 告警Entity
 * @author wzx
 * @version 2017-05-18
 */
public class PlatAlarm extends DataEntity<PlatAlarm> {
	
	private static final long serialVersionUID = 1L;
	private Date createTime;		// create_time
	private String alarmDeviceid;		// alarm_deviceid
	private String alarmDevicename;		// alarm_devicename
	private String alarmType;		// alarm_type
	private String alarmRoom;		// alarm_room
	private String linkAction;		// link_action
	private String status;		// status
	private String isdeal;		// isdeal
	private Date dealTime;		// deal_time
	private String alarmGrades;		// alarm_grades
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	private String content;		// content
	
	private Date startDate ;			//表单搜索条件---报警时间起
	private Date endDate ;			//表单搜索条件---报警时间止
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public PlatAlarm() {
		super();
	}

	public PlatAlarm(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="create_time不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getAlarmDeviceid() {
		return alarmDeviceid;
	}

	public void setAlarmDeviceid(String alarmDeviceid) {
		this.alarmDeviceid = alarmDeviceid;
	}
	
	public String getAlarmDevicename() {
		return alarmDevicename;
	}

	public void setAlarmDevicename(String alarmDevicename) {
		this.alarmDevicename = alarmDevicename;
	}
	
	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	
	public String getAlarmRoom() {
		return alarmRoom;
	}

	public void setAlarmRoom(String alarmRoom) {
		this.alarmRoom = alarmRoom;
	}
	
	public String getLinkAction() {
		return linkAction;
	}

	public void setLinkAction(String linkAction) {
		this.linkAction = linkAction;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getIsdeal() {
		return isdeal;
	}

	public void setIsdeal(String isdeal) {
		this.isdeal = isdeal;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	
	public String getAlarmGrades() {
		return alarmGrades;
	}

	public void setAlarmGrades(String alarmGrades) {
		this.alarmGrades = alarmGrades;
	}
	
}
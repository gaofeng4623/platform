/**
 * 
 */
package com.gtzn.modules.base.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gtzn.common.persistence.DataEntity;

/**
 * 门禁报警记录Entity
 * @author gtzn_Yang
 * @version 2016-12-19
 */
public class DoorWarning extends DataEntity<DoorWarning> {
	
	private static final long serialVersionUID = 1L;
	private Door door;		// 门禁
	private String rfid;		// 电子标签号
	private Date warnDate;		// 报警时间
	private String warnReason;		// 报警原因
	private String handler;		// 处理人
	private String handlerName; //处理人名称
	private String handleResult;		// 处理结果
	private Date handleDate;		// 处理时间
	
	private String warningTimeStart; //报警时间开始
	private String warningTimeEnd; //报警时间结束
	
	public DoorWarning() {
		super();
	}

	public DoorWarning(String id){
		super(id);
	}
	
	public Door getDoor() {
		return door;
	}

	public void setDoor(Door door) {
		this.door = door;
	}

	@Length(min=0, max=50, message="电子标签号长度必须介于 0 和 50 之间")
	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWarnDate() {
		return warnDate;
	}

	public void setWarnDate(Date warnDate) {
		this.warnDate = warnDate;
	}
	
	@Length(min=0, max=100, message="报警原因长度必须介于 0 和 100 之间")
	public String getWarnReason() {
		return warnReason;
	}

	public void setWarnReason(String warnReason) {
		this.warnReason = warnReason;
	}
	
	@Length(min=0, max=50, message="处理人长度必须介于 0 和 50 之间")
	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}
	
	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	@Length(min=0, max=100, message="处理结果长度必须介于 0 和 100 之间")
	public String getHandleResult() {
		return handleResult;
	}

	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

	public String getWarningTimeStart() {
		return warningTimeStart;
	}

	public void setWarningTimeStart(String warningTimeStart) {
		this.warningTimeStart = warningTimeStart;
	}

	public String getWarningTimeEnd() {
		return warningTimeEnd;
	}

	public void setWarningTimeEnd(String warningTimeEnd) {
		this.warningTimeEnd = warningTimeEnd;
	}
}
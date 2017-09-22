/**
 * 
 */
package com.gtzn.modules.rfid.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gtzn.common.persistence.DataEntity;

/**
 * 标签写入Entity
 * @author gtzn
 * @version 2017-04-06
 */
public class RfidItemstatus extends DataEntity<RfidItemstatus> {
	
	private static final long serialVersionUID = 1L;
	private String itemid;		// 分卷ID
	private String itemno;		// 卷号
	private String infoid;		// 档案主键
	private String status;		// 在架状态,0:未上架、1：在库、2：下架、3：销毁、4：待上架
	private String rfid;		// 标签号
	private String location;		// 存放位置
	private Date uptime;		// 上架时间
	
	public RfidItemstatus() {
		super();
	}

	public RfidItemstatus(String id){
		super(id);
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	
	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}
	
	public String getInfoid() {
		return infoid;
	}

	public void setInfoid(String infoid) {
		this.infoid = infoid;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	
}
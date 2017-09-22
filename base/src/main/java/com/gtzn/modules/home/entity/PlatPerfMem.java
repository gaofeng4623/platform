/**
 * 
 */
package com.gtzn.modules.home.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.gtzn.common.persistence.DataEntity;

/**
 * 内存性能Entity
 * @author wzx
 * @version 2017-05-10
 */
public class PlatPerfMem extends DataEntity<PlatPerfMem> {
	
	private static final long serialVersionUID = 1L;
	private String ip;		// ip
	private Long total;		// total
	private Long free;		// free
	private String percent;		// percent
	private Date collectdate;		// collectdate
	private Date beginCollectdate;		// 开始 collectdate
	private Date endCollectdate;		// 结束 collectdate
	
	public PlatPerfMem() {
		super();
	}

	public PlatPerfMem(String id){
		super(id);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
	public Long getFree() {
		return free;
	}

	public void setFree(Long free) {
		this.free = free;
	}
	
	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="collectdate不能为空")
	public Date getCollectdate() {
		return collectdate;
	}

	public void setCollectdate(Date collectdate) {
		this.collectdate = collectdate;
	}
	
	public Date getBeginCollectdate() {
		return beginCollectdate;
	}

	public void setBeginCollectdate(Date beginCollectdate) {
		this.beginCollectdate = beginCollectdate;
	}
	
	public Date getEndCollectdate() {
		return endCollectdate;
	}

	public void setEndCollectdate(Date endCollectdate) {
		this.endCollectdate = endCollectdate;
	}
		
}
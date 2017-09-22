/**
 * 
 */
package com.gtzn.modules.home.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gtzn.common.persistence.DataEntity;

/**
 * 网站统计Entity
 * @author chenyp
 * @version 2017-05-22
 */
public class WebVisitStatistics extends DataEntity<WebVisitStatistics> {
	
	private static final long serialVersionUID = 1L;
	private String visittype;		// 访问类型：ip/pv
	private String visittemplate;		// 访问模板
	private Date visittime;		// 访问时间
	private String visitcount;		// 访问人次
	
	public WebVisitStatistics() {
		super();
	}

	public WebVisitStatistics(String id){
		super(id);
	}

	public String getVisittype() {
		return visittype;
	}

	public void setVisittype(String visittype) {
		this.visittype = visittype;
	}
	
	public String getVisittemplate() {
		return visittemplate;
	}

	public void setVisittemplate(String visittemplate) {
		this.visittemplate = visittemplate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getVisittime() {
		return visittime;
	}

	public void setVisittime(Date visittime) {
		this.visittime = visittime;
	}
	
	public String getVisitcount() {
		return visitcount;
	}

	public void setVisitcount(String visitcount) {
		this.visitcount = visitcount;
	}

	
}
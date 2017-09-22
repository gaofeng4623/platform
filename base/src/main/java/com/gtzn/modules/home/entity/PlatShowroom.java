/**
 * 
 */
package com.gtzn.modules.home.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.gtzn.common.persistence.DataEntity;

/**
 * 展厅管理Entity
 * @author wzx
 * @version 2017-05-11
 */
public class PlatShowroom extends DataEntity<PlatShowroom> {
	
	private static final long serialVersionUID = 1L;
	private String visitor;		// visitor
	private String plat;		// 展台
	private String position;		// x:y:z
	private Date collecttime;		// collecttime
	private String ext1;		// ext1
	private String ext2;		// ext2
	private String ext3;		// ext3
	private Date beginCollecttime;		// 开始 collecttime
	private Date endCollecttime;		// 结束 collecttime
	
	public PlatShowroom() {
		super();
	}

	public PlatShowroom(String id){
		super(id);
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}
	
	public String getPlat() {
		return plat;
	}

	public void setPlat(String plat) {
		this.plat = plat;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="collecttime不能为空")
	public Date getCollecttime() {
		return collecttime;
	}

	public void setCollecttime(Date collecttime) {
		this.collecttime = collecttime;
	}
	
	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	
	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	
	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	
	public Date getBeginCollecttime() {
		return beginCollecttime;
	}

	public void setBeginCollecttime(Date beginCollecttime) {
		this.beginCollecttime = beginCollecttime;
	}
	
	public Date getEndCollecttime() {
		return endCollecttime;
	}

	public void setEndCollecttime(Date endCollecttime) {
		this.endCollecttime = endCollecttime;
	}
		
}
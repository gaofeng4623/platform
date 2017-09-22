/**
 * 
 */
package com.gtzn.modules.home.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gtzn.common.persistence.DataEntity;

/**
 * 服务器管理Entity
 * @author wzx
 * @version 2017-05-08
 */
public class PlatComputer extends DataEntity<PlatComputer> {
	
	private static final long serialVersionUID = 1L;
	private String ip;		// ip
	private String name;		// name
	private String password;		// password
	private String bmcname;		// bmcname
	private String bmcpw;		// bmcpw
	private String ostype;		// 0:widnwos;1:linux
	private String osversion;		// osversion
	private String descripe;		// descripe
	private String onlineflag;		// 0不在线1在线
	private String conflag;		// 0:可连接；1不可连接
	private Date createdate;		// createdate
	private Date updatedate;		// updatedate
	private String flag;		// 0:不收集；1收集
	private String ext1;		// ext1
	private String ext2;		// ext2
	private String ext3;		// ext3
	
	public PlatComputer() {
		super();
	}

	public PlatComputer(String id){
		super(id);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getBmcname() {
		return bmcname;
	}

	public void setBmcname(String bmcname) {
		this.bmcname = bmcname;
	}
	
	public String getBmcpw() {
		return bmcpw;
	}

	public void setBmcpw(String bmcpw) {
		this.bmcpw = bmcpw;
	}
	
	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}
	
	public String getOsversion() {
		return osversion;
	}

	public void setOsversion(String osversion) {
		this.osversion = osversion;
	}
	
	public String getDescripe() {
		return descripe;
	}

	public void setDescripe(String descripe) {
		this.descripe = descripe;
	}
	
	public String getOnlineflag() {
		return onlineflag;
	}

	public void setOnlineflag(String onlineflag) {
		this.onlineflag = onlineflag;
	}
	
	public String getConflag() {
		return conflag;
	}

	public void setConflag(String conflag) {
		this.conflag = conflag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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
	
}
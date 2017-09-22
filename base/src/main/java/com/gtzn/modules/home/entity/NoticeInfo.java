/**
 * 
 */
package com.gtzn.modules.home.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gtzn.common.persistence.DataEntity;
import com.gtzn.modules.sys.entity.User;

import java.util.Date;

/**
 * 公告信息Entity
 * @author wangzx
 * @version 2017-04-07
 */
public class NoticeInfo extends DataEntity<NoticeInfo> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String type;		// 公告所属类别
	private String content;		// 公告内容
	private Date date;		// 公告时间
	private String ext;		// 备注
	private String office;   //可见部门;
	private String officeName;   //可见部门名称;
	private User longInUser ;//当前系统用户
	private String gkflag;
	private String tname;
    private String stime;
    private String etime;
	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getGkflag() {
		return gkflag;
	}

	public void setGkflag(String gkflag) {
		this.gkflag = gkflag;
	}

	private String releaseDate;
	public User getLongInUser() {
		return longInUser;
	}

	public void setLongInUser(User longInUser) {
		this.longInUser = longInUser;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public NoticeInfo() {
		super();
	}

	public NoticeInfo(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}
	
	
}
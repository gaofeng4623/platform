package com.gtzn.modules.home.entity;

import com.gtzn.common.persistence.DataEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PlatInformation extends DataEntity<PlatInformation> {

	private static final long serialVersionUID = -5974504829412630219L;

	private String title;
	private String url;
	private String time;
	private String releaseDate;
	private String content;
	private String stime;
	private String etime;
	private String tname;
	private String type;//检索方式 1：行业检索  2： 公告检索   3：知识库检索

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time == null ? null : time.trim();
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("title", title)
				.append("url", url)
				.append("time", time)
				.append("releaseDate", releaseDate)
				.append("content", content)
				.append("stime", stime)
				.append("etime", etime)
				.append("tname", tname)
				.append("type", type)
				.toString();
	}
}
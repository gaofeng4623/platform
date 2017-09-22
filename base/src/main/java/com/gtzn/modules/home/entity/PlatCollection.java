/**
 * 
 */
package com.gtzn.modules.home.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.gtzn.common.persistence.DataEntity;

/**
 * 最新采集Entity
 * @author wzx
 * @version 2017-04-25
 */
public class PlatCollection extends DataEntity<PlatCollection> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类别
	private String theme;		// 专题
	private String title;		// 标题
	private String content;		// 内容
	private String sourcefrom;		// 来源
	private String author;		// 作者
	private String collector;		// 收集人
	private Date autordate;		// 写作时间
	private Date collectdate;		// 收集时间
	private String efile;		// 电子文件
	private String ext1;		// ext1
	private String ext2;		// ext2
	private String ext3;		// ext3
	
	public PlatCollection() {
		super();
	}

	public PlatCollection(String id){
		super(id);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getSourcefrom() {
		return sourcefrom;
	}

	public void setSourcefrom(String from) {
		this.sourcefrom = from;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAutordate() {
		return autordate;
	}

	public void setAutordate(Date autordate) {
		this.autordate = autordate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCollectdate() {
		return collectdate;
	}

	public void setCollectdate(Date collectdate) {
		this.collectdate = collectdate;
	}
	
	public String getEfile() {
		return efile;
	}

	public void setEfile(String efile) {
		this.efile = efile;
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
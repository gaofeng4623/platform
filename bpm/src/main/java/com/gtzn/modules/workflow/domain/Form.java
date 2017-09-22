package com.gtzn.modules.workflow.domain;

import com.gtzn.common.persistence.DataEntity;

public class Form  extends DataEntity<Form> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;//表单名称
	private String fields;//表单字段，JSON字符
	private String remark;//说明
	private String minlength = "0";//字段最小
	private String maxlength = "50";//字段最长
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMinlength() {
		return minlength;
	}
	public void setMinlength(String minlength) {
		this.minlength = minlength;
	}
	public String getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}
}

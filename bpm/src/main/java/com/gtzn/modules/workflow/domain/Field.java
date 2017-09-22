package com.gtzn.modules.workflow.domain;

import java.util.List;

import com.google.common.collect.Lists;

public class Field {
	/*{"label":"姓名","field_type":"text","required":true,
	 * "field_options":{"size":"small","description":"请输入名"},"cid":"c18"},{"label":"年龄","field_type":"number","required":true,"field_options":{"description"
	:"请输入年龄"},"cid":"c26"}*/
	private String label;
	private String field_type;
	private boolean required;
	private String cid;
	private Option field_options;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getField_type() {
		return field_type;
	}
	public void setField_type(String field_type) {
		this.field_type = field_type;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public Option getField_options() {
		return field_options;
	}
	public void setField_options(Option field_options) {
		this.field_options = field_options;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
}

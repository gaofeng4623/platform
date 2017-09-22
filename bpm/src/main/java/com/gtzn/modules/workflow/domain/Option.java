package com.gtzn.modules.workflow.domain;

import java.util.List;

import com.google.common.collect.Lists;

public class Option {
	
	
	private String size;//文本框长度 small
	
	private String description;//描述
	
	private String label;//标签名
	
	private boolean checked;//单选活复选的选中状态
	
	private boolean include_blank_option;//包含空
	private List<Option> options = Lists.newArrayList();


	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public boolean isInclude_blank_option() {
		return include_blank_option;
	}
	public void setInclude_blank_option(boolean include_blank_option) {
		this.include_blank_option = include_blank_option;
	}
	
}

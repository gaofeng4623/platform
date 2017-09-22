package com.gtzn.modules.workflow.domain;

public class FormGroup {

	private String form_id;// 表单id
	private String group_id;// 流程任务组id
	private String cid;// 拥有的表单字段cid
	private String option;// read，write，none不可见

	public String getForm_id() {
		return form_id;
	}

	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

}

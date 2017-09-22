package com.gtzn.modules.digital.entity;

import com.gtzn.common.persistence.DataEntity;

public class Connoisseur  extends DataEntity<Connoisseur>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String name;
	String createtime;
	String phone;
	String email;
	String workflowtype;
	String processname;
	
	public String getProcessname() {
		return processname;
	}
	public void setProcessname(String processname) {
		this.processname = processname;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getWorkflowtype() {
		return workflowtype;
	}
	public void setWorkflowtype(String workflowtype) {
		this.workflowtype = workflowtype;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}

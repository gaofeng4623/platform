package com.gtzn.modules.workflow.domain;

import com.gtzn.common.persistence.DataEntity;

public class ActModelDeploy extends DataEntity<ActModelDeploy>{

	private static final long serialVersionUID = 4073065788538993014L;
	private String modelkey;
	private String modelname;
	private String deployid;
	private String modelid;
	private Integer modelversion;
	
	public String getModelkey() {
		return modelkey;
	}
	public void setModelkey(String modelkey) {
		this.modelkey = modelkey;
	}
	public String getDeployid() {
		return deployid;
	}
	public void setDeployid(String deployid) {
		this.deployid = deployid;
	}
	public String getModelid() {
		return modelid;
	}
	public void setModelid(String modelid) {
		this.modelid = modelid;
	}
	public Integer getModelversion() {
		return modelversion;
	}
	public void setModelversion(Integer modelversion) {
		this.modelversion = modelversion;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	
}

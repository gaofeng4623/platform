package com.gtzn.modules.workflow.domain;

import org.activiti.engine.identity.User;

public class WFUser implements User {
	
	private String id;
	

	/** 
	* @Fields serialVersionUID : TODO
	*/
	private static final long serialVersionUID = 1L;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getFirstName() {
		return null;
	}

	@Override
	public void setFirstName(String firstName) {
	}

	@Override
	public void setLastName(String lastName) {
	}

	@Override
	public String getLastName() {
		return null;
	}

	@Override
	public void setEmail(String email) {
	}

	@Override
	public String getEmail() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public void setPassword(String string) {

	}

	@Override
	public boolean isPictureSet() {
		return false;
	}

}

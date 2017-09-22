/**
 * 
 */
package com.gtzn.modules.home.entity;

import com.gtzn.common.persistence.DataEntity;

/**
 * 便笺Entity
 */
public class Memo extends DataEntity<Memo> {
	private static final long serialVersionUID = 1L;
	String id;
	String mleft;
	String mtop;
	String content;
	String createUser;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getMleft() {
		return mleft;
	}
	public void setMleft(String mleft) {
		this.mleft = mleft;
	}
	public String getMtop() {
		return mtop;
	}
	public void setMtop(String mtop) {
		this.mtop = mtop;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
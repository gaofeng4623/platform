package com.gtzn.modules.digital.entity;

import com.gtzn.common.persistence.DataEntity;

public class DStatus extends DataEntity<DStatus>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6443300098043476802L;

	private String unitName;

    private Integer inLibrary;

    private Integer issue;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getInLibrary() {
		return inLibrary;
	}

	public void setInLibrary(Integer inLibrary) {
		this.inLibrary = inLibrary;
	}

	public Integer getIssue() {
		return issue;
	}

	public void setIssue(Integer issue) {
		this.issue = issue;
	}

    
}
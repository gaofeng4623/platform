package com.gtzn.modules.digital.entity;

import com.gtzn.common.persistence.DataEntity;

public class PlatUnit  extends DataEntity<PlatUnit>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String unitNode;

    private String unitName;

    private Integer electronicsNo;

    private Integer entityNo;

    private String year;

    private String unitCode;
    private Integer count;
	public String getUnitNode() {
		return unitNode;
	}

	public void setUnitNode(String unitNode) {
		this.unitNode = unitNode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getElectronicsNo() {
		return electronicsNo;
	}

	public void setElectronicsNo(Integer electronicsNo) {
		this.electronicsNo = electronicsNo;
	}

	public Integer getEntityNo() {
		return entityNo;
	}

	public void setEntityNo(Integer entityNo) {
		this.entityNo = entityNo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
  
   
}
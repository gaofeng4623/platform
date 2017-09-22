package com.gtzn.modules.digital.entity;

import com.gtzn.common.persistence.DataEntity;

public class PlatStatistics extends DataEntity<PlatStatistics>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String startName;

    private Integer electronicsNo;

    private Integer entityNo;

    private String year;
    
    private Integer electronicsNoCount;

    private Integer entityNoCount;

	public String getStartName() {
		return startName;
	}

	public void setStartName(String startName) {
		this.startName = startName;
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

	public Integer getElectronicsNoCount() {
		return electronicsNoCount;
	}

	public void setElectronicsNoCount(Integer electronicsNoCount) {
		this.electronicsNoCount = electronicsNoCount;
	}

	public Integer getEntityNoCount() {
		return entityNoCount;
	}

	public void setEntityNoCount(Integer entityNoCount) {
		this.entityNoCount = entityNoCount;
	}

   

    
}
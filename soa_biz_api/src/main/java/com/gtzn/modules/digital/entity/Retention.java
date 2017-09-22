package com.gtzn.modules.digital.entity;

import com.gtzn.common.persistence.DataEntity;

public class Retention extends DataEntity<Retention>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String retention;

    private String concentrated;

    private Integer entitiesNo;

    private Integer electronicsNo;

    private Integer type;
    private Integer count;
	public String getRetention() {
		return retention;
	}

	public void setRetention(String retention) {
		this.retention = retention;
	}

	public String getConcentrated() {
		return concentrated;
	}

	public void setConcentrated(String concentrated) {
		this.concentrated = concentrated;
	}

	public Integer getEntitiesNo() {
		return entitiesNo;
	}

	public void setEntitiesNo(Integer entitiesNo) {
		this.entitiesNo = entitiesNo;
	}

	public Integer getElectronicsNo() {
		return electronicsNo;
	}

	public void setElectronicsNo(Integer electronicsNo) {
		this.electronicsNo = electronicsNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
    


}
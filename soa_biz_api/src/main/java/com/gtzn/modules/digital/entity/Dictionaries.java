package com.gtzn.modules.digital.entity;

import com.gtzn.common.persistence.DataEntity;

public class Dictionaries extends DataEntity<Dictionaries>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
    
	private Integer entitiesNo;
	private Integer electronicsNo;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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
    
}
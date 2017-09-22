package com.gtzn.modules.home.entity;

import com.gtzn.common.persistence.DataEntity;

public class PlatColor  extends DataEntity<PlatColor>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

    private String typeId;

    private Integer type;

   


    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
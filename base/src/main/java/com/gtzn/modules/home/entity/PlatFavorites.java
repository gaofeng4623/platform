/**
 * 
 */
package com.gtzn.modules.home.entity;


import com.gtzn.common.persistence.DataEntity;

/**
 * 收藏夹Entity
 * @author wzx
 * @version 2017-04-20
 */
public class PlatFavorites extends DataEntity<PlatFavorites> {
	
	private static final long serialVersionUID = 1L;
	private String favName;		// fav_name
	
	public PlatFavorites() {
		super();
	}

	public PlatFavorites(String id){
		super(id);
	}

	public String getFavName() {
		return favName;
	}

	public void setFavName(String favName) {
		this.favName = favName;
	}
	
}
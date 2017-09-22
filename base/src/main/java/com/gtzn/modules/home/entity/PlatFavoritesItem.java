/**
 * 
 */
package com.gtzn.modules.home.entity;


import com.gtzn.common.persistence.DataEntity;

/**
 * 我的收藏Entity
 * @author wzx
 * @version 2017-04-20
 */
public class PlatFavoritesItem extends DataEntity<PlatFavoritesItem> {
	
	private static final long serialVersionUID = 1L;
	private String favoritesid;		// 收藏夹ID
	private String type;		// type
	private String linkid;		// linkid
	private String title;		// title
	
	public PlatFavoritesItem() {
		super();
	}

	public PlatFavoritesItem(String id){
		super(id);
	}

	public String getFavoritesid() {
		return favoritesid;
	}

	public void setFavoritesid(String favoritesid) {
		this.favoritesid = favoritesid;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getLinkid() {
		return linkid;
	}

	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
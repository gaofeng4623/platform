package com.gtzn.modules.home.entity;

import com.gtzn.common.persistence.DataEntity;

/**
 * 统计模块配置实体类
* @ClassName: Stati 
* @Description: TODO
* @author guant
* @date 2016年12月19日 下午3:32:57 
*
 */
public class Stati extends DataEntity<Stati>{
	
	private static final long serialVersionUID = 2841905054270768164L;
	private String statiKey;
	private String statiName;
	private int count;
	private String icon;
	private String unit;
	private String groupFlag;
	private String color;
	
	private String pId;
	private boolean nocheck = false;
	
	public String getStatiKey() {
		return statiKey;
	}
	public void setStatiKey(String statiKey) {
		this.statiKey = statiKey;
	}
	public String getStatiName() {
		return statiName;
	}
	public void setStatiName(String statiName) {
		this.statiName = statiName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getGroupFlag() {
		return groupFlag;
	}
	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "Stati [statiKey=" + statiKey + ", statiName=" + statiName
				+ ", count=" + count + ", icon=" + icon + ", unit=" + unit
				+ ", groupFlag=" + groupFlag + ", color=" + color + "]";
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public boolean isNocheck() {
		return nocheck;
	}
	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
	
}

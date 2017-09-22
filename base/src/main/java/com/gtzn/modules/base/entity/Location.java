package com.gtzn.modules.base.entity;

import com.gtzn.common.persistence.DataEntity;
import com.gtzn.common.utils.Reflections;
import com.gtzn.common.utils.StringUtils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

/**
 * 位置管理Entity
 * @author gtzn
 * @version 2016-12-19
 */
public class Location extends DataEntity<Location> {
	
	private static final long serialVersionUID = 1L;
	private String locationName;		// 位置名称
	private Location parent;		// 父节点
	private String rfid;		// 电子标签
	private String locationPath;		// 全路径（中文）
	private String locationPathGrid;		// locationpathgrid
	private int gridNo;		// gridno
	private String locationType;		// 位置类型
	private int serialNo;		// 序号
	private String serialNoPath;		// 序号全路径
	private String fileType;		// 标识该架存放要件还是权证，1：要件，0：权证
	
	private String locationTypeName; // 位置类型
	private boolean IsParent;
	
	public Location() {
		super();
	}

	public Location(String id){
		super(id);
	}
		
	@Length(min=0, max=100, message="位置名称长度必须介于 0 和 100 之间")
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
		
	@Length(min=0, max=50, message="电子标签长度必须介于 0 和 50 之间")
	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	
	@Length(min=0, max=500, message="全路径（中文）长度必须介于 0 和 500 之间")
	public String getLocationPath() {
		return locationPath;
	}

	public void setLocationPath(String locationPath) {
		this.locationPath = locationPath;
	}
	
	@Length(min=0, max=500, message="locationpathgrid长度必须介于 0 和 500 之间")
	public String getLocationPathGrid() {
		return locationPathGrid;
	}

	public void setLocationPathGrid(String locationPathGrid) {
		this.locationPathGrid = locationPathGrid;
	}
		
	public int getGridNo() {
		return gridNo;
	}

	public void setGridNo(int gridNo) {
		this.gridNo = gridNo;
	}

	@Length(min=0, max=2, message="位置类型长度必须介于 0 和 2 之间")
	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
		
	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	
	@Length(min=0, max=50, message="序号全路径长度必须介于 0 和 50 之间")
	public String getSerialNoPath() {
		return serialNoPath;
	}

	public void setSerialNoPath(String serialNoPath) {
		this.serialNoPath = serialNoPath;
	}
	
	@Length(min=0, max=1, message="标识该架存放要件还是权证，1：要件，0：权证长度必须介于 0 和 1 之间")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Location getParent() {
		return parent;
	}

	public void setParent(Location parent) {
		this.parent = parent;
	}
	
	public String getParentid() {
		String id = null;
		if (parent != null){
			id = (String)Reflections.getFieldValue(parent, "id");
		}
		return StringUtils.isNotBlank(id) ? id : "0";
	}

	public String getLocationTypeName() {
		return locationTypeName;
	}

	public void setLocationTypeName(String locationTypeName) {
		this.locationTypeName = locationTypeName;
	}

	public boolean isIsParent() {
		return IsParent;
	}

	public void setIsParent(boolean isParent) {
		IsParent = isParent;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("locationName", locationName)
				.append("parent", parent)
				.append("rfid", rfid)
				.append("locationPath", locationPath)
				.append("locationPathGrid", locationPathGrid)
				.append("gridNo", gridNo)
				.append("locationType", locationType)
				.append("serialNo", serialNo)
				.append("serialNoPath", serialNoPath)
				.append("fileType", fileType)
				.append("locationTypeName", locationTypeName)
				.append("IsParent", IsParent)
				.toString();
	}
}
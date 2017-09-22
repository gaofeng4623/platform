package com.gtzn.modules.base.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.gtzn.common.persistence.DataEntity;

/**
 * 门禁类entity
 * @ClassName: Door
 * @Description: TODO
 * @author gtzn_lee
 * @date 2016年11月23日 下午4:38:41
 *
 */
public class Door extends DataEntity<Door> {

	private static final long serialVersionUID = 1L;
	private String doorId;		// 门禁号
	private String note; 		// 名称
	private String ip; 			// 门禁IP地址(即网络地址)
	private String doorType; 	// 是否为大门（可带出的），1-是，0-否
	
	private String oldDoorId;	//
	
	public Door() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Door(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	@NotBlank(message="门禁编号不能为空")  
	public String getDoorId() {
		return doorId;
	}
	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}
	@NotBlank(message="门禁名称不能为空")  
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@NotBlank(message="门禁地址不能为空")  
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@NotBlank(message="门禁类型不能为空")  
	public String getDoorType() {
		return doorType;
	}
	public void setDoorType(String doorType) {
		this.doorType = doorType;
	}
	
	public String getOldDoorId() {
		return oldDoorId;
	}
	public void setOldDoorId(String oldDoorId) {
		this.oldDoorId = oldDoorId;
	}
	@Override
	public String toString() {
		return "BaseDoor [doorId=" + doorId + ", note=" + note + ", ip=" + ip + ", doorType=" + doorType + "]";
	}
	
	
}

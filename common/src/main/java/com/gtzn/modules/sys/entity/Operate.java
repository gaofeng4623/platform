package com.gtzn.modules.sys.entity;

import com.gtzn.common.persistence.DataEntity;

public class Operate extends DataEntity<Operate>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String userNo;//员工号
	private String userName;//员工姓名
	private String operationRecord;//操作记录
	private String ip;//ip地址
	private String module;//模块
	private String operationType;//操作类型
	private String eqType;
	private String branchId;//分行编码
	
	
//	private String beginDate;
//	private String endDate;
	
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOperationRecord() {
		return operationRecord;
	}
	public void setOperationRecord(String operationRecord) {
		this.operationRecord = operationRecord;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
//	public String getBeginDate() {
//		return beginDate;
//	}
//	public void setBeginDate(String beginDate) {
//		this.beginDate = beginDate;
//	}
//	public String getEndDate() {
//		return endDate;
//	}
//	public void setEndDate(String endDate) {
//		this.endDate = endDate;
//	}
	public String getEqType() {
		return eqType;
	}
	public void setEqType(String eqType) {
		this.eqType = eqType;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
}

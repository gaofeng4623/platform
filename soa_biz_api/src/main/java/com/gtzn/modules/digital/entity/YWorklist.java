package com.gtzn.modules.digital.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gtzn.common.persistence.DataEntity;
import com.gtzn.common.utils.excel.annotation.ExcelField;

public class YWorklist extends DataEntity<YWorklist>{

	private static final long serialVersionUID = 1L;
	@ExcelField(title="主申请ID",align=2)
	private String applyId;		// 主申请ID
	@ExcelField(title="流程ID",align=2)
	private String flowId;		// 流程ID
	@ExcelField(title="步骤ID",align=2)
	private String stepId;		// 步骤名称
	@ExcelField(title="状态0：未审批 1：审批同意 2：审批拒绝",align=2)
	private String state;		// 状态0：未审批 1：审批同意 2：审批拒绝  3:转审批
	@ExcelField(title="审批人ID",align=2)
	private String user;		// 审批人ID
	@ExcelField(title="审批时间",align=2)
	private Date approveDate;		// 审批时间
	@ExcelField(title="意见",align=2)
	private String memo;		// 意见
	private String applyType;		// 申请类型:archivesuselook:查看申请 archivesuseprint:打印申请 archivesusecall:调档申请
	private int ixh;		// 序号
	
	private int currentFlag ;//是否当前审批节点 1：是 0：否
	private String parentId;//父ID
	
	private List<String> stateList ;
	private List<String> applyTypeList ;
	private String approveName;//审批人姓名
	private String applyName;//流程发起人姓名
	
	public YWorklist() {
		super();
	}

	public YWorklist(String id){
		super(id);
	}

	@Length(min=0, max=32, message="主申请ID长度必须介于 0 和 32 之间")
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	@Length(min=0, max=10, message="流程ID长度必须介于 0 和 10 之间")
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	@Length(min=0, max=10, message="步骤ID长度必须介于 0 和 10 之间")
	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=32, message="审批人ID长度必须介于 0 和 32 之间")
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	
	@Length(min=0, max=128, message="意见长度必须介于 0 和 128 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
		if("1".equals(applyType)){
			
		}else if("2".equals(applyType)){
			
		}else if("3".equals(applyType)){
			
		}
	}

	public int getIxh() {
		return ixh;
	}

	public void setIxh(int ixh) {
		this.ixh = ixh;
	}

	public int getCurrentFlag() {
		return currentFlag;
	}

	public void setCurrentFlag(int currentFlag) {
		this.currentFlag = currentFlag;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<String> getStateList() {
		return stateList;
	}

	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

	public List<String> getApplyTypeList() {
		return applyTypeList;
	}

	public void setApplyTypeList(List<String> applyTypeList) {
		this.applyTypeList = applyTypeList;
	}

	public String getApproveName() {
		return approveName;
	}

	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	
	
	
}

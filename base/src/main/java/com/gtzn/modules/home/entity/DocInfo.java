/**
 * 
 */
package com.gtzn.modules.home.entity;

import com.gtzn.common.persistence.DataEntity;

/**
 * 文档审批Entity
 * @author wangzx
 * @version 2017-04-07
 */
public class DocInfo extends DataEntity<DocInfo> {
	
	private static final long serialVersionUID = 1L;
	private String docid;		//文档编号
	private String title;		//文档标题

	private String applyName;		//申请人
	private String applyTime;		//申请时间
	private String checkName;		//审批人
	private String checkType;		//审批状态
	private String checkTest;		//审批意见
	private String dataTime;		//审批时间
	
	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getCheckTest() {
		return checkTest;
	}

	public void setCheckTest(String checkTest) {
		this.checkTest = checkTest;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	public DocInfo() {
		super();
	}

	public DocInfo(String id){
		super(id);
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

}
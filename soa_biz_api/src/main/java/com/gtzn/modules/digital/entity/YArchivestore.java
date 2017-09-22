/**
 * 
 */
package com.gtzn.modules.digital.entity;

import com.gtzn.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 档案库Entity
 * @author zhanggk
 * @version 2016-12-15
 */
public class YArchivestore extends DataEntity<YArchivestore> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 档案库名称
	private String tblname;		// 表名称，表名以d_开始
	private String templateId;		// 模板id
	private String classId;		// 类别ID
	private String pid;		// 父ID
	private String typecode;		// 类别编码,关联数据字典，文书档案，合同档案
	private String typename;		// 类别名称,关联数据字典，文书档案，合同档案
	private String datype;		// 档案库类型：一文一件;传统立卷;:卷内文件
	private Integer ixh;		// 序号
	private String cindex;		// 是否索引N:不索引 Y：索引
	
	private String templateName;//模板名称,数据库Y_Archivestore中不存在 方便页面展示使用
	private String archiveclassName ;//类别名称,数据库Y_Archivestore中不存在 方便页面展示使用
	
	private Integer mxcount;//利用登记选择条目的数目
	public static String SEQ_NAME = "ARCHIVESTORE";
	public YArchivestore() {
		super();
	}

	public YArchivestore(String id){
		super(id);
	}

	@Length(min=1, max=64, message="档案库名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=32, message="表名称，表名以d_开始长度必须介于 1 和 32 之间")
	public String getTblname() {
		return tblname;
	}

	public void setTblname(String tblname) {
		this.tblname = tblname;
	}
	
	@Length(min=0, max=64, message="模板id长度必须介于 0 和 64 之间")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	@Length(min=0, max=64, message="类别ID长度必须介于 0 和 64 之间")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	@Length(min=0, max=64, message="父ID长度必须介于 0 和 64 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Length(min=0, max=32, message="类别编码,关联数据字典，文书档案，合同档案长度必须介于 0 和 32 之间")
	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	
	@Length(min=0, max=64, message="类别名称,关联数据字典，文书档案，合同档案长度必须介于 0 和 64 之间")
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	
 
	@Length(min=0, max=64, message="档案类型，长度必须介于 0 和 64 之间")
	public String getDatype() {
		return datype;
	}

	public void setDatype(String datype) {
		this.datype = datype;
	}

	public Integer getIxh() {
		return ixh;
	}

	public void setIxh(Integer ixh) {
		this.ixh = ixh;
	}
	
	@Length(min=0, max=1, message="是否索引N:不索引 Y：索引长度必须介于 0 和 1 之间")
	public String getCindex() {
		return cindex;
	}

	public void setCindex(String cindex) {
		this.cindex = cindex;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getArchiveclassName() {
		return archiveclassName;
	}

	public void setArchiveclassName(String archiveclassName) {
		this.archiveclassName = archiveclassName;
	}

	public Integer getMxcount() {
		return mxcount;
	}

	public void setMxcount(Integer mxcount) {
		this.mxcount = mxcount;
	}
	
	
}
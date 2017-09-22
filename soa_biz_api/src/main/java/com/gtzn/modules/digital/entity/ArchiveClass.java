package com.gtzn.modules.digital.entity;

import com.gtzn.common.persistence.DataEntity;

public class ArchiveClass extends DataEntity<ArchiveClass>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String toreName;//y_archivestore 表
	private String toreTblname;//y_archivestore 表
	private String fieldname;// y_templatefield 表
	private String Dcount;//计算d表合
	private String nd;
	private Integer countEntityNo;//按年度得到一个实体档案总数
	private Integer countElectronicsNo;//按年度得到一个电子档案总数
	private String unitCode;//y_unit 表
	private String unitName;//y_unit 表
	private String fieldpro;// y_templatefield 表
	private String bgqx;//sys_dict 保管期限
	private String MJ;//sys_dict 密集
	private String lable;//
	private String classId;
	private Integer outCount;
	private String outunit;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToreName() {
		return toreName;
	}
	public void setToreName(String toreName) {
		this.toreName = toreName;
	}
	public String getToreTblname() {
		return toreTblname;
	}
	public void setToreTblname(String toreTblname) {
		this.toreTblname = toreTblname;
	}
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	public String getDcount() {
		return Dcount;
	}
	public void setDcount(String dcount) {
		Dcount = dcount;
	}
	public String getNd() {
		return nd;
	}
	public void setNd(String nd) {
		this.nd = nd;
	}
	public Integer getCountEntityNo() {
		return countEntityNo;
	}
	public void setCountEntityNo(Integer countEntityNo) {
		this.countEntityNo = countEntityNo;
	}
	public Integer getCountElectronicsNo() {
		return countElectronicsNo;
	}
	public void setCountElectronicsNo(Integer countElectronicsNo) {
		this.countElectronicsNo = countElectronicsNo;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getFieldpro() {
		return fieldpro;
	}
	public void setFieldpro(String fieldpro) {
		this.fieldpro = fieldpro;
	}
	public String getBgqx() {
		return bgqx;
	}
	public void setBgqx(String bgqx) {
		this.bgqx = bgqx;
	}
	public String getMJ() {
		return MJ;
	}
	public void setMJ(String mJ) {
		MJ = mJ;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public Integer getOutCount() {
		return outCount;
	}
	public void setOutCount(Integer outCount) {
		this.outCount = outCount;
	}
	public String getOutunit() {
		return outunit;
	}
	public void setOutunit(String outunit) {
		this.outunit = outunit;
	}
	
   
   
   
}
package com.gtzn.modules.digital.entity;

import java.util.Date;

import com.gtzn.common.persistence.DataEntity;

public class YDataCollection extends DataEntity<YDataCollection>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String subject;  	//主题词
	private String genName;		//全宗名称
	private String autograph;	//案卷题名
	private String retainDate;	//保管期限
	private String enthesis;	//起止时间
	private String fileNum;		//文件件数
	private String docNum;		//档号
	private String catalog;		//目录号
	private String filesID;		//案卷号
	private String yearN;		//年度
	private String recordNum;	//全宗号
	private Date updateDate; 	//更新时间
	
	
	private String subject1;  	//主题词
	private String genName1;		//全宗名称
	private String retainDate1;	//保管期限
	private String enthesis1;	//起止时间
	private String fileNum1;		//文件件数
	private String docNum1;		//档号
	private String catalog1;		//目录号
	private String filesID1;		//案卷号
	private String yearN1;		//年度
	private String recordNum1;	//全宗号
	private String booksTitle;	//题名
	private int aid;			//卷内文件ID
	
	private String fid;			//文件ID
	private String fileName;	//文件名    _FILE表里的附件
	private String filePath;	//文件路径
	private String fileTitle;	//文件主题
	private String fileSize;	//文件大小
	private String fileType;	//文件类型
	public YDataCollection() {
		super();
	}

	public YDataCollection(String id){
		super(id);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGenName() {
		return genName;
	}

	public void setGenName(String genName) {
		this.genName = genName;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	
	public String getRetainDate() {
		return retainDate;
	}

	public void setRetainDate(String retainDate) {
		this.retainDate = retainDate;
	}

	public String getEnthesis() {
		return enthesis;
	}

	public void setEnthesis(String enthesis) {
		this.enthesis = enthesis;
	}

	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	
	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getFilesID() {
		return filesID;
	}

	public void setFilesID(String filesID) {
		this.filesID = filesID;
	}

	public String getYearN() {
		return yearN;
	}

	public void setYearN(String yearN) {
		this.yearN = yearN;
	}

	public String getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getBooksTitle() {
		return booksTitle;
	}

	public void setBooksTitle(String booksTitle) {
		this.booksTitle = booksTitle;
	}

	public String getSubject1() {
		return subject1;
	}

	public void setSubject1(String subject1) {
		this.subject1 = subject1;
	}

	public String getGenName1() {
		return genName1;
	}

	public void setGenName1(String genName1) {
		this.genName1 = genName1;
	}

	public String getRetainDate1() {
		return retainDate1;
	}

	public void setRetainDate1(String retainDate1) {
		this.retainDate1 = retainDate1;
	}

	public String getEnthesis1() {
		return enthesis1;
	}

	public void setEnthesis1(String enthesis1) {
		this.enthesis1 = enthesis1;
	}

	public String getFileNum1() {
		return fileNum1;
	}

	public void setFileNum1(String fileNum1) {
		this.fileNum1 = fileNum1;
	}

	public String getDocNum1() {
		return docNum1;
	}

	public void setDocNum1(String docNum1) {
		this.docNum1 = docNum1;
	}

	public String getCatalog1() {
		return catalog1;
	}

	public void setCatalog1(String catalog1) {
		this.catalog1 = catalog1;
	}

	public String getFilesID1() {
		return filesID1;
	}

	public void setFilesID1(String filesID1) {
		this.filesID1 = filesID1;
	}

	public String getYearN1() {
		return yearN1;
	}

	public void setYearN1(String yearN1) {
		this.yearN1 = yearN1;
	}

	public String getRecordNum1() {
		return recordNum1;
	}

	public void setRecordNum1(String recordNum1) {
		this.recordNum1 = recordNum1;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
}

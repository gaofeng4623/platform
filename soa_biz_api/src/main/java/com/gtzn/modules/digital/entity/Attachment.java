package com.gtzn.modules.digital.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gtzn.common.persistence.DataEntity;
import com.gtzn.modules.sys.entity.User;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * describe TODO 知识库管理模块,上传的文件附件
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/10 10:55
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attachment extends DataEntity<Attachment> {

	private String groupId;//分组Id 对应一次上传多个标签时分组id相同
	private String fileName;//文件名称
	private String filePath;//文件路径
	private String fileSize;//文件大小： kb,mb
	private User user;//创建人
	private File file;//附件所属的文件
	private String url;//附件下载地址

	public Attachment() {
		super();
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("groupId", groupId)
				.append("fileName", fileName)
				.append("filePath", filePath)
				.append("fileSize", fileSize)
				.append("user", user)
				.append("file", file)
				.append("url", url)
				.toString();
	}
}

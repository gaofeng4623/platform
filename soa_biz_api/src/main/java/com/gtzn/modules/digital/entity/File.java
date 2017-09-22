package com.gtzn.modules.digital.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gtzn.common.persistence.DataEntity;
import com.gtzn.modules.sys.entity.User;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * describe TODO 知识库管理模块,上传的文件或编辑的文章
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/10 10:47
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class File extends DataEntity<File> {

	private Folder folder;//文件所在的文件夹,即文件的档案类型
	private String title;//标题（文件名）
	private String keyWords;//关键字，多个关键字以逗号分隔
	private String fileName;//文件名（对应附件）
	private String content;//内容
	private String flag;//1-数据库文本 2-文件附件
	private User user;//创建人（用户id）
	private String attGroupId;//附件组id
	private List<Attachment> files;//附件
	private String url;//附件下载地址
	private String fileSize;//附件大小

	public File() {
		super();
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAttGroupId() {
		return attGroupId;
	}

	public void setAttGroupId(String attGroupId) {
		this.attGroupId = attGroupId;
	}

	public List<Attachment> getFiles() {
		return files;
	}

	public void setFiles(List<Attachment> files) {
		this.files = files;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("folder", folder)
				.append("title", title)
				.append("keyWords", keyWords)
				.append("fileName", fileName)
				.append("content", content)
				.append("flag", flag)
				.append("user", user)
				.append("attGroupId", attGroupId)
				.append("files", files)
				.append("url", url)
				.append("fileSize", fileSize)
				.toString();
	}
}

package com.gtzn.modules.digital.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gtzn.common.persistence.TreeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * describe TODO 知识库管理模块,文档存储文件夹entity
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/8 15:38
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Folder extends TreeEntity<Folder> {

	public Folder() {
		super();
	}

	private String parentId;
	private List<Folder> childList;

	//是否是打开状态 默认不打开
	private boolean open = false;

	//判断是否选中
	private boolean checked = false;
	//判断是否是可选的 如果为 true 则此节点不可选
	private boolean nocheck = false;

	//控制是否显示 增删改按钮  默认都是不显示
	private boolean isShowDeleteBut = false;
	private boolean isShowUpdateBut = false;
	private boolean isShowInsertBut = false;
	/**
	 * 父对象，只能通过子类实现，父类实现mybatis无法读取
	 *
	 * @return
	 */
	@Override
	public Folder getParent() {
		return null;
	}

	/**
	 * 父对象，只能通过子类实现，父类实现mybatis无法读取
	 *
	 * @param parent
	 * @return
	 */
	@Override
	public void setParent(Folder parent) {

	}

	@Override
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Folder> getChildList() {
		return childList;
	}

	public void setChildList(List<Folder> childList) {
		this.childList = childList;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public boolean isShowDeleteBut() {
		return isShowDeleteBut;
	}

	public void setShowDeleteBut(boolean showDeleteBut) {
		isShowDeleteBut = showDeleteBut;
	}

	public boolean isShowUpdateBut() {
		return isShowUpdateBut;
	}

	public void setShowUpdateBut(boolean showUpdateBut) {
		isShowUpdateBut = showUpdateBut;
	}

	public boolean isShowInsertBut() {
		return isShowInsertBut;
	}

	public void setShowInsertBut(boolean showInsertBut) {
		isShowInsertBut = showInsertBut;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("parentId", parentId)
				.append("childList", childList)
				.append("open", open)
				.append("checked", checked)
				.append("nocheck", nocheck)
				.append("isShowDeleteBut", isShowDeleteBut)
				.append("isShowUpdateBut", isShowUpdateBut)
				.append("isShowInsertBut", isShowInsertBut)
				.toString();
	}
}

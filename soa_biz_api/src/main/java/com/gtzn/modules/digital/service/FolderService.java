package com.gtzn.modules.digital.service;

import com.gtzn.modules.digital.entity.Folder;

import java.util.List;

/**
 * describe TODO 知识库管理模块,档案存储的文件夹操作业务
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/8 15:52
 **/
public interface FolderService {

	/**
	 * 根据条件获取子文件夹列表
	 *
	 * @param folder 文件夹查询条件
	 * @return 文件夹列表
	 */
	List<Folder> findChildList(Folder folder);

	/**
	 * 新增或修改一个文件夹信息
	 *
	 * @param folder 要增加或要修改的文件夹
	 * @return 新增or修改的Folder
	 */
	Folder saveOrUpdateFolder(Folder folder);

	/**
	 * 根据条件删除文件夹
	 *
	 * @param folder 删除条件
	 */
	void deleteFolder(Folder folder);

	/**
	 * 递归删除资源树上的节点
	 *
	 * @param id 要删除的节点id
	 */
	void recursiveDelete(String id);

	/**
	 * 判断此节点是否存在兄弟节点，若不存在，则将其父节点更新成子节点
	 *
	 * @param id 节点id
	 */
	void updateParentNode(String id);

}

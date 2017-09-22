package com.gtzn.modules.digital.service.impl;

import com.gtzn.common.config.Global;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.modules.digital.dao.AttachmentDao;
import com.gtzn.modules.digital.dao.FileDao;
import com.gtzn.modules.digital.dao.FolderDao;
import com.gtzn.modules.digital.entity.Attachment;
import com.gtzn.modules.digital.entity.File;
import com.gtzn.modules.digital.entity.Folder;
import com.gtzn.modules.digital.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * describe TODO
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/8 16:13
 **/
@Service
public class FolderServiceImpl implements FolderService {

	@Autowired
	private FolderDao folderDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private AttachmentDao attachmentDao;

	/**
	 * 根据条件获取子文件夹列表,默认是获取根文件夹的子文件夹
	 *
	 * @param folder 文件夹查询条件
	 * @return 文件夹列表
	 */
	@Override
	public List<Folder> findChildList(Folder folder) {
		return folderDao.getChildListById(folder.getId());
	}

	/**
	 * 新增或修改一个文件夹信息
	 *
	 * @param folder 要增加或要修改的文件夹
	 * @return 文件夹的id
	 */
	@Override
	@Transactional
	public Folder saveOrUpdateFolder(Folder folder) {
		String id = folder.getId();
		if (StringUtils.isBlank(id)) {
			folderDao.insert(folder);
			Folder folder1 = folderDao.get(folder.getParentId());
			folder1.setIsParent("true");
			folderDao.update(folder1);
		} else {
			folderDao.update(folder);
		}
		return folder;
	}

	/**
	 * 根据条件删除文件夹
	 *
	 * @param folder 删除条件
	 */
	@Override
	@Transactional
	public void deleteFolder(Folder folder) {
		folder = folderDao.get(folder);
		recursiveDelete(folder.getId());
		updateParentNode(folder.getParentId());
	}

	/**
	 * 递归删除资源树上的节点
	 *
	 * @param id 要删除的节点id
	 */
	@Override
	@Transactional
	public void recursiveDelete(String id) {
		List<Folder> list = folderDao.getChildListById(id);
		if (list.isEmpty()) {
			Folder folder = folderDao.get(id);
			String parentId = folder.getParentId();
			folderDao.delete(id);
			updateParentNode(parentId);
			File file = new File();
			Folder folder1 = new Folder();
			folder1.setId(id);
			file.setFolder(folder1);
			List<File> fileList = fileDao.findList(file);
			if (!fileList.isEmpty()) {
				for (File file1 : fileList) {
					fileDao.delete(file1.getId());
					Attachment attachment = new Attachment();
					attachment.setGroupId(file1.getAttGroupId());
					attachmentDao.delete(attachment);
					java.io.File file2 = new java.io.File(Global.getConfig("knowledgeUpLoadPath") + "/" +file1.getUrl());
					if (file2.exists() && file2.isFile())
						file2.delete();
				}
			}
		} else {
			folderDao.delete(id);
			for (Folder folder: list) {
				if ("true".equals(folder.getIsParent())) {
					recursiveDelete(folder.getId());
				} else {
					folderDao.delete(folder.getId());
					File file = new File();
					Folder folder1 = new Folder();
					folder1.setId(folder.getId());
					file.setFolder(folder1);
					List<File> fileList = fileDao.findList(file);
					if (!fileList.isEmpty()) {
						for (File file1 : fileList) {
							fileDao.delete(file1.getId());
							Attachment attachment = new Attachment();
							attachment.setGroupId(file1.getAttGroupId());
							attachmentDao.delete(attachment);
							java.io.File file2 = new java.io.File(Global.getConfig("knowledgeUpLoadPath") + "/" +file1.getUrl());
							if (file2.exists() && file2.isFile())
								file2.delete();
						}
					}
				}
			}
		}
	}

	/**
	 * 判断此节点是否存在兄弟节点，若不存在，则将其父节点更新成子节点
	 *
	 * @param parentId 节点父id
	 */
	@Override
	@Transactional
	public void updateParentNode(String parentId) {
		List<Folder> list = folderDao.getListByParentId(parentId);
		if (list.isEmpty()) {
			Folder folder = folderDao.get(parentId);
			folder.setIsParent("false");
			folderDao.update(folder);
		}
	}
}

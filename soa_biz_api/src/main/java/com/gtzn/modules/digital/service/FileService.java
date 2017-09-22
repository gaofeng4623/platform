package com.gtzn.modules.digital.service;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.digital.entity.Attachment;
import com.gtzn.modules.digital.entity.File;

import java.util.List;

/**
 * describe TODO 知识库福安里模块，文件的操作Service
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/10 11:16
 **/
public interface FileService {

	/**
	 * 通过文件夹id获取该文件夹（即档案类型）下的所有文件、文章
	 * 分页显示
	 *
	 * @param pager 分页
	 * @param file  查询条件，file内部包含所属的文件夹信息
	 * @return 文件、文章列表
	 */
	Pager<File> findList(Pager<File> pager, File file);

	/**
	 * 保存上传文件信息
	 *
	 * @param file 上传文件信息
	 * @return 返回保存后的信息
	 */
	File save(File file);

	/**
	 * 根据文件id删除文件
	 *
	 * @param fileList 要删的文件
	 */
	void delete(List<File> fileList);

	/**
	 * 通过文件id获取文件
	 *
	 * @param file 文件信息
	 * @return 获取到的文件
	 */
	File findFile(File file);

	/**
	 * 通过文件的groupId获取此文件所拥有的附件
	 *
	 * @param attGroupId 文件的groupId
	 * @return 此文件所拥有的附件
	 */
	List<Attachment> getAttachmentByGroupId(String attGroupId);
}

package com.gtzn.modules.digital.service.impl;

import com.gtzn.common.config.Global;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.IdGen;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.modules.digital.dao.AttachmentDao;
import com.gtzn.modules.digital.dao.FileDao;
import com.gtzn.modules.digital.entity.Attachment;
import com.gtzn.modules.digital.entity.File;
import com.gtzn.modules.digital.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * describe TODO 知识库福安里模块，文件的操作Service
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/10 11:17
 **/
@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileDao fileDao;
	@Autowired
	private AttachmentDao attachmentDao;

	/**
	 * 获取文件夹（即档案类型）下的所有文件、文章
	 * 分页显示
	 *
	 * @param pager 分页
	 * @param file  查询条件，一般通过文件夹id查询
	 * @return 文件、文章列表
	 */
	@Override
	public Pager<File> findList(Pager<File> pager, File file) {
		file.setPager(pager);
		List<File> list = fileDao.findList(file);
		pager.setList(list);
		pager.setRecords(fileDao.findCount(file));
		return pager;
	}

	/**
	 * 保存上传文件信息
	 *
	 * @param file 上传文件信息
	 * @return 返回保存后的信息
	 */
	@Override
	@Transactional
	public File save(File file) {
		if (StringUtils.isBlank(file.getId())) {
			file.setAttGroupId(IdGen.uuid());
			fileDao.insert(file);
		} else {
			fileDao.update(file);
		}
		return file;
	}

	/**
	 * 根据文件id删除文件
	 *
	 * @param fileList 要删的文件
	 */
	@Override
	@Transactional
	public void delete(List<File> fileList) {
		for (File file : fileList) {
			fileDao.delete(file);
			Attachment attachment = new Attachment();
			attachment.setGroupId(file.getAttGroupId());
			attachmentDao.delete(attachment);
			String url = file.getUrl();
			java.io.File file1 = new java.io.File(Global.getConfig("knowledgeUpLoadPath") + "/" + url);
			if (file1.exists() && file1.isFile())
				file1.delete();
		}
	}

	/**
	 * 通过文件id获取文件
	 *
	 * @param file 文件信息
	 * @return 获取到的文件
	 */
	@Override
	public File findFile(File file) {
		return fileDao.get(file);
	}

	/**
	 * 通过文件的groupId获取此文件所拥有的附件
	 *
	 * @param attGroupId 文件的groupId
	 * @return 此文件所拥有的附件
	 */
	@Override
	public List<Attachment> getAttachmentByGroupId(String attGroupId) {
		return fileDao.getAttachmentByGroupId(attGroupId);
	}
}

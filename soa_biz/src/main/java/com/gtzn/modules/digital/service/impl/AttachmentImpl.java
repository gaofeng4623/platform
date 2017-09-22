package com.gtzn.modules.digital.service.impl;

import com.gtzn.modules.digital.dao.AttachmentDao;
import com.gtzn.modules.digital.entity.Attachment;
import com.gtzn.modules.digital.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * describe TODO
 * @author kgm
 * @version 1.0
 * create_date 2017/5/11 10:32
 **/
@Service
public class AttachmentImpl implements AttachmentService{

	@Autowired
	private AttachmentDao attachmentDao;

	/**
	 * 保存上传文件的附加信息
	 *
	 * @param attachment 附加信息
	 * @return 已保存的附加信息
	 */
	@Override
	@Transactional
	public Attachment save(Attachment attachment) {
		attachmentDao.insert(attachment);
		return attachment;
	}

	/**
	 * 根据条件删除文件附件
	 *
	 * @param attachment 要删除的附加
	 */
	@Override
	@Transactional
	public int delete(Attachment attachment) {
		int count = attachmentDao.delete(attachment);
		String url = attachment.getFilePath();
		java.io.File file1 = new java.io.File("D://uploadFiles//" + url);
		if (file1.exists() && file1.isFile()) {
			file1.delete();
		}
		return count;
	}
}

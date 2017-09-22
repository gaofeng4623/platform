package com.gtzn.modules.digital.service;

import com.gtzn.modules.digital.entity.Attachment;

/**
 * describe TODO
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/11 10:30
 **/
public interface AttachmentService {

	/**
	 * 保存上传文件的附加信息
	 *
	 * @param attachment 附加信息
	 * @return 已保存的附加信息
	 */
	Attachment save(Attachment attachment);

	/**
	 * 根据条件删除文件附件
	 *
	 * @param attachment 要删除的附加
	 */
	int delete(Attachment attachment);
}

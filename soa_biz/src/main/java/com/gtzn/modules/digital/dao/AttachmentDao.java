package com.gtzn.modules.digital.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.Attachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * describe TODO
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/11 9:48
 **/
@MyBatisDao
public interface AttachmentDao extends CrudDao<Attachment> {

	/**
	 * 根据文件夹id获取该文件夹下的附件
	 *
	 * @param folderId 文件夹id
	 * @return
	 */
	List<Attachment> getAttachmentByFolderId(@Param("folderId") String folderId);
}

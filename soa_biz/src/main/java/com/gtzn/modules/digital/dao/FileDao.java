package com.gtzn.modules.digital.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.Attachment;
import com.gtzn.modules.digital.entity.File;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * describe TODO 知识库管理模块，上传的文件entity
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/10 11:14
 **/
@MyBatisDao
public interface FileDao extends CrudDao<File> {

	/**
	 * 通过提交获取完整的文件信息
	 *
	 * @param file 条件
	 * @return 文件
	 */
	File findFile(File file);

	/**
	 * 通过文件的groupId获取此文件所拥有的附件
	 *
	 * @param attGroupId 文件的groupId
	 * @return 此文件所拥有的附件
	 */
	List<Attachment> getAttachmentByGroupId(@Param("attGroupId") String attGroupId);

}

package com.gtzn.modules.digital.dao;

import com.gtzn.common.persistence.TreeDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.Folder;

/**
 * describe TODO 知识库管理模块,文档存储的文件夹操作
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/8 15:49
 **/
@MyBatisDao
public interface FolderDao extends TreeDao<Folder> {

}

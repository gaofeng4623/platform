/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.gtzn.modules.gen.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.gen.entity.GenTable;

/**
 * 业务表DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface IbatisSupportDao extends CrudDao<GenTable> {
	
}

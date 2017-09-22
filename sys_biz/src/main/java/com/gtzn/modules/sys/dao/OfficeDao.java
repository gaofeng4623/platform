/**
 * 
 */
package com.gtzn.modules.sys.dao;

import java.util.List;

import com.gtzn.common.persistence.TreeDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author gtzn
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	public List<Office> findByParentId(Office entity);
}

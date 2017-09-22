/**
 * 
 */
package com.gtzn.modules.home.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.Memo;
/**
 * 便笺DAO接口 
 */
@MyBatisDao
public interface MemoDao extends CrudDao<Memo> {
	
}
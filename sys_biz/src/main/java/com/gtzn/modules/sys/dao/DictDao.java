/**
 * 
 */
package com.gtzn.modules.sys.dao;

import java.util.List;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author gtzn
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}

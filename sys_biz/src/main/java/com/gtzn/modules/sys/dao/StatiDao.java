package com.gtzn.modules.sys.dao;

import java.util.List;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.Stati;
@MyBatisDao
public interface StatiDao extends CrudDao<Stati> {

	List<Stati> findGroup();
	
	
	public String findStatiIds(String roleId);
}

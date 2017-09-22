package com.gtzn.modules.sys.dao;

import java.util.List;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.sys.entity.Example;
@MyBatisDao
public interface ExampleDao extends CrudDao<Example> {

	//public void find(@Param("map1") Map e1, @Param("e1") Example e2);
	
	public List<String> findQ1();
	public List<String> findQ5();
	
}

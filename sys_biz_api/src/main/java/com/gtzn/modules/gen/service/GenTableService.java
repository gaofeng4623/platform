package com.gtzn.modules.gen.service;

import java.util.List;

import com.gtzn.common.persistence.Page;
import com.gtzn.modules.gen.entity.GenConfig;
import com.gtzn.modules.gen.entity.GenTable;

public interface GenTableService {

	GenTable get(String id);

	Page<GenTable> find(Page<GenTable> page, GenTable genTable);

	List<GenTable> findAll();

	/**
	 * 获取物理数据表列表
	 * @param genTable
	 * @return
	 */
	List<GenTable> findTableListFormDb(GenTable genTable);

	/**
	 * 验证表名是否可用，如果已存在，则返回false
	 * @param genTable
	 * @return
	 */
	boolean checkTableName(String tableName);

	/**
	 * 获取物理数据表列表
	 * @param genTable
	 * @return
	 */
	GenTable getTableFormDb(GenTable genTable);

	void save(GenTable genTable);

	void delete(GenTable genTable);
	
	
	public GenConfig getConfig();

}
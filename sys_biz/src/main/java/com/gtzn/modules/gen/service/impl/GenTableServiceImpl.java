/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.gtzn.modules.gen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Page;
import com.gtzn.common.service.BaseService;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.modules.gen.dao.GenDataBaseDictDao;
import com.gtzn.modules.gen.dao.BaseDao;
import com.gtzn.modules.gen.dao.IbatisSupportDao;
import com.gtzn.modules.gen.entity.GenConfig;
import com.gtzn.modules.gen.entity.GenTable;
import com.gtzn.modules.gen.entity.GenTableColumn;
import com.gtzn.modules.gen.service.GenTableService;
import com.gtzn.modules.gen.util.GenUtils;

/**
 * 业务表Service
 * @author gtzn
 * @version 2013-10-15
 */
@Service("genTableService")
@Transactional(readOnly = true)
public class GenTableServiceImpl extends BaseService implements GenTableService {

	@Autowired
	private IbatisSupportDao genTableDao;
	@Autowired
	private BaseDao genTableColumnDao;
	@Autowired
	private GenDataBaseDictDao genDataBaseDictDao;
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTableService#get(java.lang.String)
	 */
	@Override
	public GenTable get(String id) {
		GenTable genTable = genTableDao.get(id);
		GenTableColumn genTableColumn = new GenTableColumn();
		genTableColumn.setGenTable(new GenTable(genTable.getId()));
		genTable.setColumnList(genTableColumnDao.findList(genTableColumn));
		return genTable;
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTableService#find(com.gtzn.common.persistence.Page, com.gtzn.modules.gen.entity.GenTable)
	 */
	@Override
	public Page<GenTable> find(Page<GenTable> page, GenTable genTable) {
		//genTable.setPage(page);
		page.setList(genTableDao.findList(genTable));
		return page;
	}

	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTableService#findAll()
	 */
	@Override
	public List<GenTable> findAll() {
		return genTableDao.findAllList(new GenTable());
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTableService#findTableListFormDb(com.gtzn.modules.gen.entity.GenTable)
	 */
	@Override
	public List<GenTable> findTableListFormDb(GenTable genTable){
		return genDataBaseDictDao.findTableList(genTable);
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTableService#checkTableName(java.lang.String)
	 */
	@Override
	public boolean checkTableName(String tableName){
		if (StringUtils.isBlank(tableName)){
			return true;
		}
		GenTable genTable = new GenTable();
		genTable.setName(tableName);
		List<GenTable> list = genTableDao.findList(genTable);
		return list.size() == 0;
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTableService#getTableFormDb(com.gtzn.modules.gen.entity.GenTable)
	 */
	@Override
	public GenTable getTableFormDb(GenTable genTable){
		// 如果有表名，则获取物理表
		if (StringUtils.isNotBlank(genTable.getName())){
			
			List<GenTable> list = genDataBaseDictDao.findTableList(genTable);
			if (list.size() > 0){
				
				// 如果是新增，初始化表属性
				if (StringUtils.isBlank(genTable.getId())){
					genTable = list.get(0);
					// 设置字段说明
					if (StringUtils.isBlank(genTable.getComments())){
						genTable.setComments(genTable.getName());
					}
					genTable.setClassName(StringUtils.toCapitalizeCamelCase(genTable.getName()));
				}
				
				// 添加新列
				List<GenTableColumn> columnList = genDataBaseDictDao.findTableColumnList(genTable);
				for (GenTableColumn column : columnList){
					boolean b = false;
					for (GenTableColumn e : genTable.getColumnList()){
						if (e.getName().equals(column.getName())){
							b = true;
						}
					}
					if (!b){
						genTable.getColumnList().add(column);
					}
				}
				
				// 删除已删除的列
				for (GenTableColumn e : genTable.getColumnList()){
					boolean b = false;
					for (GenTableColumn column : columnList){
						if (column.getName().equals(e.getName())){
							b = true;
						}
					}
					if (!b){
						e.setDelFlag(GenTableColumn.DEL_FLAG_DELETE);
					}
				}
				
				// 获取主键
				genTable.setPkList(genDataBaseDictDao.findTablePK(genTable));
				
				// 初始化列属性字段
				GenUtils.initColumnField(genTable);
				
			}
		}
		return genTable;
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTableService#save(com.gtzn.modules.gen.entity.GenTable)
	 */
	@Override
	@Transactional(readOnly = false)
	public void save(GenTable genTable) {
		if (StringUtils.isBlank(genTable.getId())){
			genTable.preInsert();
			genTableDao.insert(genTable);
		}else{
			genTable.preUpdate();
			genTableDao.update(genTable);
		}
		// 保存列
		for (GenTableColumn column : genTable.getColumnList()){
			column.setGenTable(genTable);
			if (StringUtils.isBlank(column.getId())){
				column.preInsert();
				genTableColumnDao.insert(column);
			}else{
				column.preUpdate();
				genTableColumnDao.update(column);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTableService#delete(com.gtzn.modules.gen.entity.GenTable)
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(GenTable genTable) {
		genTableDao.delete(genTable);
		genTableColumnDao.deleteByGenTableId(genTable.getId());
	}
	
	
	public GenConfig getConfig() {
		return GenUtils.getConfig();
	}
}

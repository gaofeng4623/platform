package com.gtzn.modules.sys.service;

import java.util.List;

import com.gtzn.common.persistence.Page;
import com.gtzn.modules.sys.entity.Dict;

public interface DictService {

	/**
	 * 查询字段类型列表
	 * @return
	 */
	public abstract List<String> findTypeList();

	public abstract void save(Dict dict);

	public abstract int delete(Dict dict);

	public Dict get(String id);
	
	public Page<Dict> findPage(Page<Dict> page, Dict entity);
	
	public List<Dict> findList(Dict entity);
	
	public List<Dict> findAllList(Dict entity);
}
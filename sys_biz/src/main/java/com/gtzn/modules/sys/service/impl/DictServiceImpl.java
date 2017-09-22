/**
 * 
 */
package com.gtzn.modules.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.service.CrudService;
import com.gtzn.modules.sys.dao.DictDao;
import com.gtzn.modules.sys.entity.Dict;
import com.gtzn.modules.sys.service.DictService;

/**
 * 字典Service
 * @author gtzn
 * @version 2014-05-16
 */
@Service("dictService")
@Transactional(readOnly = true)
public class DictServiceImpl extends CrudService<DictDao, Dict> implements DictService {
	
	public static final String CACHE_DICT_MAP = "dictMap";
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.sys.service.impl.DictService#findTypeList()
	 */
	@Override
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	/* (non-Javadoc)
	 * @see com.gtzn.modules.sys.service.impl.DictService#save(com.gtzn.modules.sys.entity.Dict)
	 */
	@Override
	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
	}

	/* (non-Javadoc)
	 * @see com.gtzn.modules.sys.service.impl.DictService#delete(com.gtzn.modules.sys.entity.Dict)
	 */
	@Override
	@Transactional(readOnly = false)
	public int delete(Dict dict) {
		return super.delete(dict);
	}

	public List<Dict> findAllList(Dict entity) {
		return dao.findAllList(entity);
	}
	
}

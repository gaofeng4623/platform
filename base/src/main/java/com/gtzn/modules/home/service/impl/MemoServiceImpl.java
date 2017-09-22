/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.dao.MemoDao;
import com.gtzn.modules.home.entity.Memo;
import com.gtzn.modules.home.service.MemoService;

/**
 * 便笺Service 
 */
@Service("memo")
@Transactional(readOnly = true)
public class MemoServiceImpl extends CrudService<MemoDao, Memo> implements MemoService{

	public List<Memo> findList(Memo entity) {
		return super.findList(entity);
	}
	
	@Transactional(readOnly = false)
	public void save(Memo entity) {
		super.save(entity);
	}

	@Transactional(readOnly = false)
	public void insertMem(Memo entity) {
		super.insertSelective(entity);
	}

	@Transactional(readOnly = false)
	public void deleteMem(String id) {
		super.delete(id);
	}

	public List<Memo> findAllList(Memo entity){
		return super.findAllList(entity);
	}
}
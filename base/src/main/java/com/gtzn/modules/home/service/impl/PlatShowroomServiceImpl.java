/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.entity.PlatShowroom;
import com.gtzn.modules.home.dao.PlatShowroomDao;
import com.gtzn.modules.home.service.PlatShowroomService; 

/**
 * 展厅管理Service
 * @author wzx
 * @version 2017-05-11
 */
@Service("platShowroom")
@Transactional(readOnly = true)
public class PlatShowroomServiceImpl extends CrudService<PlatShowroomDao, PlatShowroom> implements PlatShowroomService{

	public List<PlatShowroom> findList(PlatShowroom platShowroom) {
		return super.findList(platShowroom);
	}
	
	public Pager<PlatShowroom> findPage(Pager<PlatShowroom> pager, PlatShowroom platShowroom) {
		return super.findPage(pager, platShowroom);
	}

	public PlatShowroom selectPlatShowroomInfo(String id) {
		return super.get(id);
	}

	public PlatShowroom selectPlatShowroomInfo(PlatShowroom platShowroom) {
		return super.get(platShowroom);
	}

	@Transactional(readOnly = false)
	public void save(PlatShowroom platShowroom) {
		super.save(platShowroom);
	}

	@Transactional(readOnly = false)
	public void insertPlatShowroomSelective(PlatShowroom platShowroom) {
		super.insertSelective(platShowroom);
	}

	@Transactional(readOnly = false)
	public void updatePlatShowroomSelective(PlatShowroom platShowroom) {
		super.updateSelective(platShowroom);
	}

	@Transactional(readOnly = false)
	public void deletePlatShowroomInfo(PlatShowroom platShowroom) {
		super.delete(platShowroom);
	}

	@Transactional(readOnly = false)
	public void deletePlatShowroomInfo(String id) {
		super.delete(id);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String[] idList) {
		return super.batchDelete(idList);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<PlatShowroom> list) {
		return super.batchInsert(list);
	}

	@Transactional(readOnly = false)
	public int executeUpdate(String sql) {
		return super.executeUpdate(sql);
	}

	@Transactional(readOnly = false)
	public void executeBatchUpdate(List<String> arraySql) {
		for (String sql : arraySql) {
			super.executeUpdate(sql);
		}
	}

	public List<PlatShowroom> findAllList(PlatShowroom platShowroom){
		return super.findAllList(platShowroom);
	}
}
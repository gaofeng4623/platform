/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.entity.PlatPerfMem;
import com.gtzn.modules.home.dao.PlatPerfMemDao;
import com.gtzn.modules.home.service.PlatPerfMemService; 

/**
 * 内存性能Service
 * @author wzx
 * @version 2017-05-10
 */
@Service("platPerfMem")
@Transactional(readOnly = true)
public class PlatPerfMemServiceImpl extends CrudService<PlatPerfMemDao, PlatPerfMem> implements PlatPerfMemService{

	public List<PlatPerfMem> findList(PlatPerfMem platPerfMem) {
		return super.findList(platPerfMem);
	}
	
	public Pager<PlatPerfMem> findPage(Pager<PlatPerfMem> pager, PlatPerfMem platPerfMem) {
		return super.findPage(pager, platPerfMem);
	}

	public PlatPerfMem selectPlatPerfMemInfo(String id) {
		return super.get(id);
	}

	public PlatPerfMem selectPlatPerfMemInfo(PlatPerfMem platPerfMem) {
		return super.get(platPerfMem);
	}

	@Transactional(readOnly = false)
	public void save(PlatPerfMem platPerfMem) {
		super.save(platPerfMem);
	}

	@Transactional(readOnly = false)
	public void insertPlatPerfMemSelective(PlatPerfMem platPerfMem) {
		super.insertSelective(platPerfMem);
	}

	@Transactional(readOnly = false)
	public void updatePlatPerfMemSelective(PlatPerfMem platPerfMem) {
		super.updateSelective(platPerfMem);
	}

	@Transactional(readOnly = false)
	public void deletePlatPerfMemInfo(PlatPerfMem platPerfMem) {
		super.delete(platPerfMem);
	}

	@Transactional(readOnly = false)
	public void deletePlatPerfMemInfo(String id) {
		super.delete(id);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String[] idList) {
		return super.batchDelete(idList);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<PlatPerfMem> list) {
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

	public List<PlatPerfMem> findAllList(PlatPerfMem platPerfMem){
		return super.findAllList(platPerfMem);
	}

	@Override
	public List<PlatPerfMem> findAllDayList(PlatPerfMem platPerfMem) {
		return dao.findAllDayList(platPerfMem);
	}

	@Override
	public List<PlatPerfMem> findAllMonthList(PlatPerfMem platPerfMem) {
		return dao.findAllMonthList(platPerfMem);
	}

	@Override
	public List<PlatPerfMem> findAllYearList(PlatPerfMem platPerfMem) {
		return dao.findAllYearList(platPerfMem);
	}
}
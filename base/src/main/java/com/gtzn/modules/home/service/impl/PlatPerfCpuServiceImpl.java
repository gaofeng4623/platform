/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.entity.PlatPerfCpu;
import com.gtzn.modules.home.dao.PlatPerfCpuDao;
import com.gtzn.modules.home.service.PlatPerfCpuService; 

/**
 * CPU性能Service
 * @author wzx
 * @version 2017-05-10
 */
@Service("platPerfCpu")
@Transactional(readOnly = true)
public class PlatPerfCpuServiceImpl extends CrudService<PlatPerfCpuDao, PlatPerfCpu> implements PlatPerfCpuService{

	public List<PlatPerfCpu> findList(PlatPerfCpu platPerfCpu) {
		return super.findList(platPerfCpu);
	}
	
	public Pager<PlatPerfCpu> findPage(Pager<PlatPerfCpu> pager, PlatPerfCpu platPerfCpu) {
		return super.findPage(pager, platPerfCpu);
	}

	public PlatPerfCpu selectPlatPerfCpuInfo(String id) {
		return super.get(id);
	}

	public PlatPerfCpu selectPlatPerfCpuInfo(PlatPerfCpu platPerfCpu) {
		return super.get(platPerfCpu);
	}

	@Transactional(readOnly = false)
	public void save(PlatPerfCpu platPerfCpu) {
		super.save(platPerfCpu);
	}

	@Transactional(readOnly = false)
	public void insertPlatPerfCpuSelective(PlatPerfCpu platPerfCpu) {
		super.insertSelective(platPerfCpu);
	}

	@Transactional(readOnly = false)
	public void updatePlatPerfCpuSelective(PlatPerfCpu platPerfCpu) {
		super.updateSelective(platPerfCpu);
	}

	@Transactional(readOnly = false)
	public void deletePlatPerfCpuInfo(PlatPerfCpu platPerfCpu) {
		super.delete(platPerfCpu);
	}

	@Transactional(readOnly = false)
	public void deletePlatPerfCpuInfo(String id) {
		super.delete(id);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String[] idList) {
		return super.batchDelete(idList);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<PlatPerfCpu> list) {
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

	public List<PlatPerfCpu> findAllList(PlatPerfCpu platPerfCpu){
		return super.findAllList(platPerfCpu);
	}

	@Override
	public List<PlatPerfCpu> findAllDayList(PlatPerfCpu platPerfCpu) {
		return dao.findAllDayList(platPerfCpu);
	}
	@Override
	public List<PlatPerfCpu> findAllMonthList(PlatPerfCpu platPerfCpu) {
		return dao.findAllMonthList(platPerfCpu);
	}
	@Override
	public List<PlatPerfCpu> findAllYearList(PlatPerfCpu platPerfCpu) {
		return dao.findAllYearList(platPerfCpu);
	}
}
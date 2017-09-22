/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.entity.PlatPerfCpu;

/**
 * CPU性能Service
 * @author wzx
 * @version 2017-05-10
 */

public interface PlatPerfCpuService {

	public List<PlatPerfCpu> findList(PlatPerfCpu platPerfCpu);
	
	public Pager<PlatPerfCpu> findPage(Pager<PlatPerfCpu> pager, PlatPerfCpu platPerfCpu);

	public PlatPerfCpu selectPlatPerfCpuInfo(String key);

	public PlatPerfCpu selectPlatPerfCpuInfo(PlatPerfCpu platPerfCpu);
	
	public void save(PlatPerfCpu platPerfCpu);

	public void insertPlatPerfCpuSelective(PlatPerfCpu platPerfCpu);

	public void updatePlatPerfCpuSelective(PlatPerfCpu platPerfCpu);
	
	public void deletePlatPerfCpuInfo(PlatPerfCpu platPerfCpu);

	public void deletePlatPerfCpuInfo(String id);

	public int batchDelete(String[] idList);

	public int batchInsert(List<PlatPerfCpu> list);

	public int executeUpdate(String sql);

	public void executeBatchUpdate(List<String> arraySql);
	
	public List<PlatPerfCpu> findAllList(PlatPerfCpu platPerfCpu);
	public List<PlatPerfCpu> findAllDayList(PlatPerfCpu platPerfCpu);
	public List<PlatPerfCpu> findAllMonthList(PlatPerfCpu platPerfCpu);
	public List<PlatPerfCpu> findAllYearList(PlatPerfCpu platPerfCpu);
	
}
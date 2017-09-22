/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.entity.PlatPerfMem;

/**
 * 内存性能Service
 * @author wzx
 * @version 2017-05-10
 */

public interface PlatPerfMemService {

	public List<PlatPerfMem> findList(PlatPerfMem platPerfMem);
	
	public Pager<PlatPerfMem> findPage(Pager<PlatPerfMem> pager, PlatPerfMem platPerfMem);

	public PlatPerfMem selectPlatPerfMemInfo(String key);

	public PlatPerfMem selectPlatPerfMemInfo(PlatPerfMem platPerfMem);
	
	public void save(PlatPerfMem platPerfMem);

	public void insertPlatPerfMemSelective(PlatPerfMem platPerfMem);

	public void updatePlatPerfMemSelective(PlatPerfMem platPerfMem);
	
	public void deletePlatPerfMemInfo(PlatPerfMem platPerfMem);

	public void deletePlatPerfMemInfo(String id);

	public int batchDelete(String[] idList);

	public int batchInsert(List<PlatPerfMem> list);

	public int executeUpdate(String sql);

	public void executeBatchUpdate(List<String> arraySql);
	
	public List<PlatPerfMem> findAllList(PlatPerfMem platPerfMem);
	public List<PlatPerfMem> findAllDayList(PlatPerfMem platPerfMem);
	public List<PlatPerfMem> findAllMonthList(PlatPerfMem platPerfMem);
	public List<PlatPerfMem> findAllYearList(PlatPerfMem platPerfMem);
	
}
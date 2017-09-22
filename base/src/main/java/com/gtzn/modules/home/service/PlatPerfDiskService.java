/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.entity.PlatPerfDisk;

/**
 * 磁盘信息Service
 * @author wzx
 * @version 2017-05-10
 */

public interface PlatPerfDiskService {

	public List<PlatPerfDisk> findList(PlatPerfDisk platPerfDisk);
	
	public Pager<PlatPerfDisk> findPage(Pager<PlatPerfDisk> pager, PlatPerfDisk platPerfDisk);

	public PlatPerfDisk selectPlatPerfDiskInfo(String key);

	public PlatPerfDisk selectPlatPerfDiskInfo(PlatPerfDisk platPerfDisk);
	
	public void save(PlatPerfDisk platPerfDisk);

	public void insertPlatPerfDiskSelective(PlatPerfDisk platPerfDisk);

	public void updatePlatPerfDiskSelective(PlatPerfDisk platPerfDisk);
	
	public void deletePlatPerfDiskInfo(PlatPerfDisk platPerfDisk);

	public void deletePlatPerfDiskInfo(String id);

	public int batchDelete(String[] idList);

	public int batchInsert(List<PlatPerfDisk> list);

	public int executeUpdate(String sql);

	public void executeBatchUpdate(List<String> arraySql);
	
	public List<PlatPerfDisk> findAllList(PlatPerfDisk platPerfDisk);
	public List<PlatPerfDisk> findAllDayList(PlatPerfDisk platPerfDisk);
	public List<PlatPerfDisk> findAllMonthList(PlatPerfDisk platPerfDisk);
	public List<PlatPerfDisk> findAllYearList(PlatPerfDisk platPerfDisk);
	
}
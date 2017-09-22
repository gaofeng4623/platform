/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.entity.PlatPerfDisk;
import com.gtzn.modules.home.dao.PlatPerfDiskDao;
import com.gtzn.modules.home.service.PlatPerfDiskService; 

/**
 * 磁盘信息Service
 * @author wzx
 * @version 2017-05-10
 */
@Service("platPerfDisk")
@Transactional(readOnly = true)
public class PlatPerfDiskServiceImpl extends CrudService<PlatPerfDiskDao, PlatPerfDisk> implements PlatPerfDiskService{

	public List<PlatPerfDisk> findList(PlatPerfDisk platPerfDisk) {
		return super.findList(platPerfDisk);
	}
	
	public Pager<PlatPerfDisk> findPage(Pager<PlatPerfDisk> pager, PlatPerfDisk platPerfDisk) {
		return super.findPage(pager, platPerfDisk);
	}

	public PlatPerfDisk selectPlatPerfDiskInfo(String id) {
		return super.get(id);
	}

	public PlatPerfDisk selectPlatPerfDiskInfo(PlatPerfDisk platPerfDisk) {
		return super.get(platPerfDisk);
	}

	@Transactional(readOnly = false)
	public void save(PlatPerfDisk platPerfDisk) {
		super.save(platPerfDisk);
	}

	@Transactional(readOnly = false)
	public void insertPlatPerfDiskSelective(PlatPerfDisk platPerfDisk) {
		super.insertSelective(platPerfDisk);
	}

	@Transactional(readOnly = false)
	public void updatePlatPerfDiskSelective(PlatPerfDisk platPerfDisk) {
		super.updateSelective(platPerfDisk);
	}

	@Transactional(readOnly = false)
	public void deletePlatPerfDiskInfo(PlatPerfDisk platPerfDisk) {
		super.delete(platPerfDisk);
	}

	@Transactional(readOnly = false)
	public void deletePlatPerfDiskInfo(String id) {
		super.delete(id);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String[] idList) {
		return super.batchDelete(idList);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<PlatPerfDisk> list) {
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

	public List<PlatPerfDisk> findAllList(PlatPerfDisk platPerfDisk){
		return super.findAllList(platPerfDisk);
	}

	@Override
	public List<PlatPerfDisk> findAllDayList(PlatPerfDisk platPerfDisk) {
		// TODO Auto-generated method stub
		return dao.findAllDayList(platPerfDisk);
	}

	@Override
	public List<PlatPerfDisk> findAllMonthList(PlatPerfDisk platPerfDisk) {
		// TODO Auto-generated method stub
		return dao.findAllMonthList(platPerfDisk);
	}

	@Override
	public List<PlatPerfDisk> findAllYearList(PlatPerfDisk platPerfDisk) {
		// TODO Auto-generated method stub
		return dao.findAllYearList(platPerfDisk);
	}
}
/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.dao.PlatAlarmDao;
import com.gtzn.modules.home.entity.PlatAlarm;
import com.gtzn.modules.home.service.PlatAlarmService;


/**
 * 告警Service
 * @author wzx
 * @version 2017-05-18
 */
@Service("platAlarm")
@Transactional(readOnly = true)
public class PlatAlarmServiceImpl extends CrudService<PlatAlarmDao, PlatAlarm> implements PlatAlarmService{

	public List<PlatAlarm> findList(PlatAlarm platAlarm) {
		return super.findList(platAlarm);
	}
	
	public Pager<PlatAlarm> findPage(Pager<PlatAlarm> pager, PlatAlarm platAlarm) {
		return super.findPage(pager, platAlarm);
	}

	public PlatAlarm selectPlatAlarmInfo(String id) {
		return super.get(id);
	}

	public PlatAlarm selectPlatAlarmInfo(PlatAlarm platAlarm) {
		return super.get(platAlarm);
	}

	@Transactional(readOnly = false)
	public void save(PlatAlarm platAlarm) {
		super.save(platAlarm);
	}

	@Transactional(readOnly = false)
	public void insertPlatAlarmSelective(PlatAlarm platAlarm) {
		super.insertSelective(platAlarm);
	}

	@Transactional(readOnly = false)
	public void updatePlatAlarmSelective(PlatAlarm platAlarm) {
		super.updateSelective(platAlarm);
	}

	@Transactional(readOnly = false)
	public void deletePlatAlarmInfo(PlatAlarm platAlarm) {
		super.delete(platAlarm);
	}

	@Transactional(readOnly = false)
	public void deletePlatAlarmInfo(String id) {
		super.delete(id);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String[] idList) {
		return super.batchDelete(idList);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<PlatAlarm> list) {
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

	public List<PlatAlarm> findAllList(PlatAlarm platAlarm){
		return super.findAllList(platAlarm);
	}
}
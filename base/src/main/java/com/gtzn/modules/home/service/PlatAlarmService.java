/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.List;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.home.entity.PlatAlarm;

/**
 * 告警Service
 * @author wzx
 * @version 2017-05-18
 */

public interface PlatAlarmService {

	public List<PlatAlarm> findList(PlatAlarm platAlarm);
	
	public Pager<PlatAlarm> findPage(Pager<PlatAlarm> pager, PlatAlarm platAlarm);

	public PlatAlarm selectPlatAlarmInfo(String key);

	public PlatAlarm selectPlatAlarmInfo(PlatAlarm platAlarm);
	
	public void save(PlatAlarm platAlarm);

	public void insertPlatAlarmSelective(PlatAlarm platAlarm);

	public void updatePlatAlarmSelective(PlatAlarm platAlarm);
	
	public void deletePlatAlarmInfo(PlatAlarm platAlarm);

	public void deletePlatAlarmInfo(String id);

	public int batchDelete(String[] idList);

	public int batchInsert(List<PlatAlarm> list);

	public int executeUpdate(String sql);

	public void executeBatchUpdate(List<String> arraySql);
	
	public List<PlatAlarm> findAllList(PlatAlarm platAlarm);
	
}
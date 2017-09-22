/**
 *
 */
package com.gtzn.modules.sys.service;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.sys.entity.ScheduleJob;

import java.util.List;

/**
 * 任务调度管理Service
 * @author GF
 * @version 2017-02-15
 */

public interface ScheduleJobService {

	public ScheduleJob get(String id);
	
	public List<ScheduleJob> findList(ScheduleJob scheduleJob);
	
	public Pager<ScheduleJob> findPage(Pager<ScheduleJob> pager, ScheduleJob scheduleJob);
	
	public void save(ScheduleJob scheduleJob);
	
	public int delete(ScheduleJob scheduleJob);
	
	public List<ScheduleJob> findAllList(ScheduleJob scheduleJob);
	
	public List<ScheduleJob> existJobName(String name);
	
}
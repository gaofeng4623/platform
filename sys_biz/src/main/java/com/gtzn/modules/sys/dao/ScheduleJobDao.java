/**
 * 
 */
package com.gtzn.modules.sys.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.sys.entity.ScheduleJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务调度管理DAO接口
 * @author 聂刚
 * @version 2017-02-15
 */
@MyBatisDao
public interface ScheduleJobDao extends CrudDao<ScheduleJob> {
	public List<ScheduleJob> existJobName(@Param("name") String name);
}
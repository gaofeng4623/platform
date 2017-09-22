/**
 * 
 */
package com.gtzn.modules.home.dao;

import java.util.List;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.PlatPerfCpu;

/**
 * CPU性能DAO接口
 * @author wzx
 * @version 2017-05-10
 */
@MyBatisDao
public interface PlatPerfCpuDao extends CrudDao<PlatPerfCpu> {
	 public List<PlatPerfCpu> findAllDayList(PlatPerfCpu platPerfCpu);
	 public List<PlatPerfCpu> findAllMonthList(PlatPerfCpu platPerfCpu);
	 public List<PlatPerfCpu> findAllYearList(PlatPerfCpu platPerfCpu);
	
}
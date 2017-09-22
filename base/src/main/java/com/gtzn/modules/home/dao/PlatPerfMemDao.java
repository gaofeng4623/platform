/**
 * 
 */
package com.gtzn.modules.home.dao;

import java.util.List;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.PlatPerfMem;

/**
 * 内存性能DAO接口
 * @author wzx
 * @version 2017-05-10
 */
@MyBatisDao
public interface PlatPerfMemDao extends CrudDao<PlatPerfMem> {
	public List<PlatPerfMem> findAllDayList(PlatPerfMem platPerfMem); 
	public List<PlatPerfMem> findAllMonthList(PlatPerfMem platPerfMem); 
	public List<PlatPerfMem> findAllYearList(PlatPerfMem platPerfMem); 
	
}
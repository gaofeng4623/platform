/**
 * 
 */
package com.gtzn.modules.home.dao;

import java.util.List;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.PlatPerfDisk;

/**
 * 磁盘信息DAO接口
 * @author wzx
 * @version 2017-05-10
 */
@MyBatisDao
public interface PlatPerfDiskDao extends CrudDao<PlatPerfDisk> {
	public List<PlatPerfDisk> findAllDayList(PlatPerfDisk platPerfDisk);
	public List<PlatPerfDisk> findAllMonthList(PlatPerfDisk platPerfDisk);
	public List<PlatPerfDisk> findAllYearList(PlatPerfDisk platPerfDisk);
	
}
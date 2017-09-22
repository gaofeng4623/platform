/**
 * 
 */
package com.gtzn.modules.base.dao;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.base.entity.DoorWarning;

/**
 * 门禁报警记录DAO接口
 * @author gtzn_Yang
 * @version 2016-12-19
 */
@MyBatisDao
public interface DoorWarningDao extends CrudDao<DoorWarning> {

	/** 
	* @Title: handleWarning 
	* @Description: 处理门禁报警
	* @param @param ids
	* @param @param handleResutl
	* @return void
	* @throws 
	*/
	public void handleWarning(@Param("ids")String[] ids, @Param("doorWarning")DoorWarning doorWarning);
	
}
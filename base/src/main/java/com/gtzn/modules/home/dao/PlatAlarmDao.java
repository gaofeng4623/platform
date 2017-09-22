/**
 * 
 */
package com.gtzn.modules.home.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.PlatAlarm;

/**
 * 告警DAO接口
 * @author wzx
 * @version 2017-05-18
 */
@MyBatisDao
public interface PlatAlarmDao extends CrudDao<PlatAlarm> {

	
}
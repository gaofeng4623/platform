/**
 * 
 */
package com.gtzn.modules.rfid.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.rfid.entity.RfidItemstatus;

/**
 * 标签写入DAO接口
 * @author gtzn
 * @version 2017-04-06
 */
@MyBatisDao
public interface RfidItemstatusDao extends CrudDao<RfidItemstatus> {

	
}
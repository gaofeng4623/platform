/**
 * 
 */
package com.gtzn.modules.sys.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * @author gtzn
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}

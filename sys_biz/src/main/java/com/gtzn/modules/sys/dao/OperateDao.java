package com.gtzn.modules.sys.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.sys.entity.Operate;
/**
 * 操作日志DAO接口
 * @author 姜帅
 * @version 2016-11-29
 */
@MyBatisDao
public interface OperateDao extends CrudDao<Operate>{

}

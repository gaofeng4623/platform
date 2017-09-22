/**
 * 
 */
package com.gtzn.modules.home.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.PlatComputer;

/**
 * 服务器管理DAO接口
 * @author wzx
 * @version 2017-05-08
 */
@MyBatisDao
public interface PlatComputerDao extends CrudDao<PlatComputer> {

	
}
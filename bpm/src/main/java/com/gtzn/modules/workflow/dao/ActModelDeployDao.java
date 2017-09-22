/**
 * 
 */
package com.gtzn.modules.workflow.dao;


import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.workflow.domain.ActModelDeploy;

/**
 * 会签DAO接口
 * @author gc
 * @version 2017-01-12
 */
@MyBatisDao
public interface ActModelDeployDao extends CrudDao<ActModelDeploy> {

}
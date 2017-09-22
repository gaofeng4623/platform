/**
 * 
 */
package com.gtzn.modules.home.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.PlatShowroom;

/**
 * 展厅管理DAO接口
 * @author wzx
 * @version 2017-05-11
 */
@MyBatisDao
public interface PlatShowroomDao extends CrudDao<PlatShowroom> {

	
}
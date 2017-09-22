package com.gtzn.modules.digital.dao;

import java.util.List;

import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.YWorklist;

/**
 * 服务大厅流程工作进度Dao
 * @author li_gm
 *
 */
@MyBatisDao
public interface WorkProcessDao {

	/**
	 * 获取服务大厅list
	 * @param yWorklist
	 * @return
	 */
	List<YWorklist> findAllList(YWorklist yWorklist);
	
	/**
	 * 获取服务大厅已办事项
	 * @param yWorklist
	 * @return
	 */
	List<YWorklist> findDoneList(YWorklist yWorklist);
	
	
}

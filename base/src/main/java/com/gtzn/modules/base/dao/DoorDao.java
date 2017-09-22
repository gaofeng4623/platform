package com.gtzn.modules.base.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.base.entity.Door;

/**
 * 门禁DAO接口
 * @ClassName: DoorDao
 * @Description: TODO
 * @author gtzn_lee
 * @date 2016年11月23日 下午5:18:50
 *
 */
@MyBatisDao
public interface DoorDao extends CrudDao<Door> {
	
	/**
	 * 根据guid删除门禁信息（多个）
	 * @Title: delDoors
	 * @Description: TODO
	 * @param guids
	 * @return void
	 * @throws
	 */
	public void delDoors(String[] guids);
	
	/**
	 * 根据门禁编号获取门禁
	 * @Title: getDoorByDoorId
	 * @Description: TODO
	 * @param 
	 * @return Door
	 * @throws
	 */
	public Door getDoorByDoorId(Door door);
	
	
	
}

package com.gtzn.modules.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.base.dao.DoorDao;
import com.gtzn.modules.base.entity.Door;
import com.gtzn.modules.base.service.DoorService;
import com.gtzn.modules.sys.entity.User;

/**
 * 
 * @ClassName: DoorServiceImpl
 * @Description: TODO
 * @author gtzn_lee
 * @date 2016年11月24日 上午9:10:44
 *
 */
@Service("doorService")
@Transactional(readOnly = true)
public class DoorServiceImpl extends CrudService<DoorDao, Door> implements DoorService {

	@Autowired
	private DoorDao doorDao;

	@Override
	public Pager<Door> findPage(Pager<Door> pager, Door door, User user) {
		// TODO Auto-generated method stub
		return super.findPage(pager, door, user, "o", "");
	}
	
	/**
	 * 删除多个门禁
	 * @Title: delDoors
	 * @Description: TODO
	 * @param 
	 * @return void
	 * @throws
	 */
	@Override
	@Transactional(readOnly = false)
	public void delDoors(String[] guids) {
		doorDao.delDoors(guids);		
	}

	/**
	 * 根据门禁编号获取门禁
	 * @Title: getDoorByDoorId
	 * @Description: TODO
	 * @param 
	 * @return Door
	 * @throws
	 */
	@Override
	public Door getDoorByDoorId(String doorId, User user) {
		Door door = new Door();
		door.setDoorId(doorId);
		door.getSqlMap().put("dsf", dataScopeFilter(user, "o", ""));
		return doorDao.getDoorByDoorId(door);	
	}
}

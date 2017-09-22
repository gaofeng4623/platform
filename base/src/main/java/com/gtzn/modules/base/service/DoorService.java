package com.gtzn.modules.base.service;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.base.entity.Door;
import com.gtzn.modules.sys.entity.User;

/**
 * 门禁service接口
 * @ClassName: DoorService
 * @Description: TODO
 * @author gtzn_lee
 * @date 2016年11月23日 下午5:31:01
 *
 */
public interface DoorService {
	
	/**
	 * 根据主键id获取门禁对象
	 * @Title: get
	 * @Description: TODO
	 * @param id
	 * @return Door
	 * @throws
	 */
	public abstract Door get(String id);

	/**
	 * 加载门禁管理列表页数据
	 * @Title: findPage
	 * @Description: TODO
	 * @param 
	 * @return Pager<Door>
	 * @throws
	 */
	public abstract Pager<Door> findPage(Pager<Door> page, Door door, User user);

	/**
	 * 保存门禁信息
	 * @Title: save
	 * @Description: TODO
	 * @param 
	 * @return void
	 * @throws
	 */
	public abstract void save(Door door);

	/**
	 * 删除单个门禁信息
	 * @Title: delete
	 * @Description: TODO
	 * @param 
	 * @return void
	 * @throws
	 */
	public abstract int delete(Door door);
	
	/**
	 * 删除多个门禁
	 * @Title: delDoors
	 * @Description: TODO
	 * @param 
	 * @return void
	 * @throws
	 */
	public abstract void delDoors(String[] guids);
	
	/**
	 * 根据门禁编号获取门禁
	 * @Title: getDoorByDoorId
	 * @Description: TODO
	 * @param 
	 * @return Door
	 * @throws
	 */
	public abstract Door getDoorByDoorId(String doorId, User user);

}

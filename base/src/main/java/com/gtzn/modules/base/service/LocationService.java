package com.gtzn.modules.base.service;

import java.util.List;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.base.entity.Location;
import com.gtzn.modules.sys.entity.User;

/**
 * 位置管理Service
* @ClassName: LocationService 
* @Description: TODO
* @author guant
* @date 2016年12月19日 上午11:43:19 
*
 */
public interface LocationService {
	
	/**
	 * 根据id获取location位置对象信息
	* @Title: get 
	* @Description: TODO
	* @param @param id
	* @param @return
	* @return Location
	* @throws
	 */
	public Location get(String id);
	
	/**
	 * 获取位置列表
	* @Title: findList 
	* @Description: TODO
	* @param @param location
	* @param @return
	* @return List<Location>
	* @throws
	 */
	public List<Location> findList(Location location);
	
	/**
	 * 获取位置信息列表分页
	* @Title: findPage 
	* @Description: TODO
	* @param @param pager
	* @param @param location
	* @param @return
	* @return Pager<Location>
	* @throws
	 */
	public Pager<Location> findPage(Pager<Location> pager, Location location, User user);
	
	/**
	 * 保存位置对象信息
	* @Title: save 
	* @Description: TODO
	* @param @param location
	* @return void
	* @throws
	 */
	public void save(Location location);
	
	/**
	 * 删除位置信息
	* @Title: delete 
	* @Description: TODO
	* @param @param location
	* @return void
	* @throws
	 */
	public int delete(Location location);
	
	/**
	 * 获取所有的位置信息
	* @Title: findAllList 
	* @Description: TODO
	* @param @param location
	* @param @param user
	* @param @return
	* @return List<Location>
	* @throws
	 */
	public List<Location> findAllList(Location location, User user);
	
	/**
	 * 保存档案中心对象信息
	* @Title: saveDept 
	* @Description: TODO
	* @param @param location
	* @return void
	* @throws
	 */
	public void saveDept(Location location);
	
	/**
	 * 获取位置上的档案数量
	* @Title: getArchiveCountByLocaton 
	* @Description: TODO
	* @param @param location
	* @param @return
	* @return long
	* @throws
	 */
	public long getArchiveCountByLocaton(Location location);
	
	/**
	 * 批量插入保存位置信息
	* @Title: saveLocationList 
	* @Description: TODO
	* @param @param parentLocation 父节点
	* @param @param types
	* @param @param typeNames
	* @param @param nums
	* @return void
	* @throws
	 */
	public void saveLocationList(Location parentLocation, List<String> types, List<String> nums, List<String> typeNames);
	
	/**
	 * 查询当前节点的孩子节点数据
	* @Title: findChildDataList 
	* @Description: TODO
	* @param @param location
	* @param @param user
	* @param @return
	* @return List<Location>
	* @throws
	 */
	public List<Location> findChildDataList(Location location, User user);
	
	/**
	 * 位置标签写入
	* @Title: doLocationRfidWrite 
	* @Description: TODO
	* @param @param id
	* @param @param type
	* @param @return
	* @return String
	* @throws
	 */
	public String doLocationRfidWrite(String id, String type);
	
}
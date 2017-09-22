package com.gtzn.modules.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.base.entity.Location;

/**
 * 位置管理dao
* @ClassName: LocationDao 
* @Description: TODO
* @author guant
* @date 2016年12月19日 上午11:45:48 
*
 */
@MyBatisDao
public interface LocationDao extends CrudDao<Location> {
	
	/**
	 * 获取当前位置的最大序号
	* @Title: getSerialNoByLocation 
	* @Description: TODO
	* @param @param location
	* @param @return
	* @return int
	* @throws
	 */
	public int getSerialNoByLocation(Location location);
	
	/**
	 * 获取位置上的个贷要件档案数量
	* @Title: getPArcCountByLocaton 
	* @Description: TODO
	* @param @param location
	* @param @return
	* @return long
	* @throws
	 */
	public long getPArcCountByLocaton(Location location);
	
	/**
	 * 获取位置上的个贷权证档案数量
	* @Title: getPWarCountByLocaton 
	* @Description: TODO
	* @param @param location
	* @param @return
	* @return long
	* @throws
	 */
	public long getPWarCountByLocaton(Location location);
	
	/**
	 * 获取位置上的法贷要件档案数量
	* @Title: getEArcCountByLocaton 
	* @Description: TODO
	* @param @param location
	* @param @return
	* @return long
	* @throws
	 */
	public long getEArcCountByLocaton(Location location);
	
	/**
	 * 获取位置上的法贷权证档案数量
	* @Title: getEWarCountByLocaton 
	* @Description: TODO
	* @param @param location
	* @param @return
	* @return long
	* @throws
	 */
	public long getEWarCountByLocaton(Location location);
	
	/**
	 * 查询当前节点的孩子节点数据
	* @Title: findChildDataList 
	* @Description: TODO
	* @param @param location
	* @param @return
	* @return List<Location>
	* @throws
	 */
	public List<Location> findChildDataList(Location location);
	
	/**
	 * 根据id更新位置rfid
	* @Title: updateLocationRfidById 
	* @Description: TODO
	* @param @param id
	* @param @param rfid
	* @return void
	* @throws
	 */
	public void updateLocationRfidById(@Param("id")String id,@Param("rfid")String rfid);
	
	
}
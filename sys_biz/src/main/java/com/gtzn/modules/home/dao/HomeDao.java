package com.gtzn.modules.home.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.Stati;

/**
 * 桌面dao接口
* @ClassName: HomeDao 
* @Description: TODO
* @author guant
* @date 2016年12月19日 下午2:42:06 
*
 */
@MyBatisDao
public interface HomeDao {
	
	/**
	 * 获取登录用户首页统计模块id数组列表
	* @Title: findUserStatiIds 
	* @Description: TODO
	* @param @param roleIds
	* @param @return
	* @return List<String>
	* @throws
	 */
	public List<String> findUserStatiIds(@Param("list")List<String> roleList);
	
	/**
	 * 获取登录用户首页统计模块列表
	* @Title: findUserStatiList 
	* @Description: TODO
	* @param @param user
	* @param @return
	* @return List<Stati>
	* @throws
	 */
	public List<Stati> findUserStatiList(@Param("list")List<String> configList);
	
	/**
	 * 获取库存档案数量
	* @Title: findLibArchiveCount 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findLibArchiveCount(@Param("dsf")String dsf);
	
	/**
	 * 获取借阅档案数量
	* @Title: findBorrowArchiveCount 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findBorrowArchiveCount(@Param("dsf")String dsf);
	
	/**
	 * 获取已移交档案成功数量
	* @Title: findTransArchiveCount 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findTransArchiveCount(@Param("dsf")String dsf);
	
	/**
	 * 获取销毁档案数量
	* @Title: findDestroyCount 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findDestroyCount(@Param("dsf")String dsf);
	
	/**
	 * 获取库管待上架数量
	* @Title: findReadyShelfCount 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findReadyShelfCount(@Param("dsf")String dsf);
	
	/**
	 * 获取库管待接收数量
	* @Title: findStoreReadyReceiveCount 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findStoreReadyReceiveCount(@Param("dsf")String dsf);
	
	/**
	 * 获取借阅待下架档案数量
	* @Title: findBorrowWaitOutCount 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findWaitOutCount(@Param("dsf")String dsf);
	
	/**
	 * 获取借阅待交付档案数量
	* @Title: findWaitDeliveryCount 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findWaitDeliveryCount(@Param("dsf")String dsf);
	
	/**
	 * 获取接收档案数量
	* @Title: findReceiveArcNum 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findReceiveArcNum(@Param("dsf")String dsf);
	
	/**
	 * 获取接收退回档案数量
	* @Title: findReceiveBackNum 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findReceiveBackNum(@Param("dsf")String dsf);
	
	/**
	 * 获取审核通过档案数量
	* @Title: getVerifyPassArcNum 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findVerifyPassArcNum(@Param("dsf")String dsf,@Param("userId")String userId);
	
	/**
	 * 获取审核退回档案数量
	* @Title: findVerifyBackArcNum 
	* @Description: TODO
	* @param @return
	* @return int
	* @throws
	 */
	public int findVerifyBackArcNum(@Param("dsf")String dsf,@Param("userId")String userId);
}

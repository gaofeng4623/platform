package com.gtzn.modules.home.service;

import java.util.List;

import com.gtzn.modules.home.entity.Stati;
import com.gtzn.modules.sys.entity.User;

/**
 * 桌面接口类
* @ClassName: HomeService 
* @Description: TODO
* @author guant
* @date 2016年12月19日 上午9:58:12 
*
 */
public interface HomeService {
	
	/**
	 * 获取用户首页统计模块数量
	* @Title: getStaticList 
	* @Description: TODO
	* @param @param user
	* @param @return
	* @return List<Stati>
	* @throws
	 */
	public List<Stati> findStaticCount(User user);
	
	/**
	 * 获取用户首页统计模块列表
	* @Title: getStaticList 
	* @Description: TODO
	* @param @param user
	* @param @return
	* @return List<Stati>
	* @throws
	 */
	public List<Stati> findStaticList(User user);
}

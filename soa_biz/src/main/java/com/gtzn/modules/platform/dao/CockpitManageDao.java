package com.gtzn.modules.platform.dao;

import java.util.List;
import java.util.Map;

import com.gtzn.common.persistence.annotation.MyBatisDao;

/**
 *  驾驶舱中平台本身的统计Dao
 * @author li_gm
 *
 */
@MyBatisDao
public interface CockpitManageDao {

	
	/**
	 * 根据x轴统计行业资讯月发布量
	 * @return
	 */
	public List<Map<String,String>> getInformStaticsByMonth();
	
	
	
}

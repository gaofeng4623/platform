package com.gtzn.modules.platform.service;

/**
 * 驾驶舱中平台本身的统计service
 * @author li_gm
 *
 */
public interface CockpitManageService{
	
	/**
	 * 根据x轴统计行业资讯月发布量
	 * @param categories
	 * @return
	 */
	public Integer[] getInformStaticsByMonth(String[] categories);
	
	
	
	
}

package com.gtzn.modules.security.service;

import java.util.Map;

/**
 * 管理驾驶舱安全中心统计
 * @author li_gm
 *
 */
public interface SecurityStaticsService {

	/**
	 * 安全中心---库房温湿度统计
	 * @return
	 */
	public Map<String, Object> getRoomTHStatics();
	
	/**
	 * 安全中心---按照报警类型统计报警次数
	 * @return
	 */
	public Map<String, Object> getAlarmStatics();
	
	/**
	 * 温湿度每天平均值变化曲线
	 * @param category
	 * @return
	 */
	public Map<String, Object> getRoomTHStaticsByDay(String category);
	
	/**
	 * 最近12个月温湿度变化范围
	 * @param category
	 * @return
	 */
	public Map<String, Object> getRoomTHStaticsByMonth(String category);
	
	/**
	 * 根据报警类型统计最近12个月的报警次数
	 * @param category
	 * @return
	 */
	public Map<String, Object> getAlarmStaticsByMonth(String category);
	
	/**
	 * 该类型的报警在各个库房的分布
	 * @param category
	 * @return
	 */
	public Map<String, Object> getAlarmStaticsByRoom(String category);
	
	/**
	 * 该类报警在各个设备上的分布
	 * @param category
	 * @return
	 */
	public Map<String, Object> getAlarmStaticsByDevice(String category);
	
	/**
	 * 统计安全报警次数
	 * @return
	 */
	public int getAlarmCount();
	
	
}

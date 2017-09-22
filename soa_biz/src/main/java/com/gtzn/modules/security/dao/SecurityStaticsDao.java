package com.gtzn.modules.security.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.annotation.MyBatisDao;

/**
 *  驾驶舱安全中心统计Dao
 * @author li_gm
 *
 */
@MyBatisDao
public interface SecurityStaticsDao {

	/**
	 * 获取可用统计的库房id列表
	 * @return
	 */
	public List<String> getRoomList();
	
	/**
	 * 根据roomid统计库房最新的温湿度信息
	 * @param roomId
	 * @return
	 */
	public Map<String,Object> getRoomTHStaticsById(@Param("roomId")String roomId);
	
	/**
	 * 按照报警类型统计报警次数
	 * @return
	 */
	public List<Map<String,Object>> getAlarmStatics();
	
	/**
	 * 温湿度每天平均值变化曲线
	 * @param category
	 * @return
	 */
	public List<Map<String,String>> getRoomTHStaticsByDay(@Param("roomName")String category);

	/**
	 * 温湿度每天平均值变化曲线
	 * @param category
	 * @return
	 */
	public List<Map<String,String>> getRoomTHStaticsByMonth(@Param("roomName")String category);
	
	/**
	 * 根据报警类型统计最近12个月的报警次数
	 * @param category
	 * @return
	 */
	public List<Map<String,String>> getAlarmStaticsByMonth(@Param("alarmTypeName")String category);
	
	/**
	 * 该类型的报警在各个库房的分布
	 * @param category
	 * @return
	 */
	public List<Map<String,Object>> getAlarmStaticsByRoom(@Param("alarmTypeName")String category);
	
	/**
	 * 该类报警在各个设备上的分布
	 * @param category
	 * @return
	 */
	public List<Map<String,Object>> getAlarmStaticsByDevice(@Param("alarmTypeName")String category);
	
	/**
	 * 统计安全报警次数
	 * @return
	 */
	public int getAlarmCount();
	
	
	
}

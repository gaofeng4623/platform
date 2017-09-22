package com.gtzn.modules.digital.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.modules.monitor.entity.Alarm;

/**
 * 安全报警service
 */
public interface AlarmService {
	
	/**
	 * 获取库房列表
	 */
	public List<Map<String,Object>> getRoomInfo();
	
	/**
	 * 根据库房id获取当天温度信息
	 */
	public List<Map<String,Object>> getWdByRoomId(@Param("roomId")int roomId);
	
	/**
	 * 根据库房id获取当天湿度信息
	 */
	public List<Map<String,Object>> getSdByRoomId(@Param("roomId")int roomId);
	
	/**
	 * 获取报警信息
	 */
	public List<Alarm> getAlarmListByAlarm(Alarm alarm);

	/**
	 * 根据房间号和设备操作类型获取设备编号
	 */
	public List<Integer> getDeviceIdListByRoomIdAndOperation(int roomId, String operation, String startDate);

	/**
	 * 根据设备编号获取设备操作时间和类型
	 */
	public List<Map<String,Object>> getOperationTimeByDeviceId(int deviceId, String operation, String startDate);
	
	/**
	 * 从当前时间往前推beforeMonth个月，这个月份发生的安全报警统计
	 * @param beforeMonth
	 */
	public List<Map<String,Object>> getGradesCountByNow(int beforeMonth);
	
}

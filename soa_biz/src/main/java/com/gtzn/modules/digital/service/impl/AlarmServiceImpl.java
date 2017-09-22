package com.gtzn.modules.digital.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.annotation.Source;
import com.gtzn.modules.digital.dao.AlarmDao;
import com.gtzn.modules.digital.service.AlarmService;
import com.gtzn.modules.monitor.entity.Alarm;

/**
 * 安全报警
 *
 */

@Source("security")
@Service("alarmService")
@Transactional(readOnly = true)
public class AlarmServiceImpl implements AlarmService{

	@Autowired
	private AlarmDao alarmDao;

	/**
	 * 获取库房列表
	 */
	public List<Map<String, Object>> getRoomInfo() {
		return alarmDao.getRoomInfo();
	}
	
	/**
	 * 根据库房id获取当天温度信息
	 */
	public List<Map<String,Object>> getWdByRoomId(@Param("roomId")int roomId){
		return alarmDao.getWdByRoomId(roomId);
	}
	
	/**
	 * 根据库房id获取当天湿度信息
	 */
	public List<Map<String,Object>> getSdByRoomId(@Param("roomId")int roomId){
		return alarmDao.getSdByRoomId(roomId);
	}
	
	/**
	 * 获取报警信息
	 */
	public List<Alarm> getAlarmListByAlarm(Alarm alarm){
		return alarmDao.getAlarmListByAlarm(alarm);
	}
	
	/**
	 * 根据房间号和设备操作类型获取设备编号
	 */
	public List<Integer> getDeviceIdListByRoomIdAndOperation(@Param("roomId")int roomId, @Param("operation")String operation, @Param("startDate")String startDate){
		Map<String, String> map = new HashMap<>();
		map.put("roomId", roomId + "");
		map.put("operation", operation);
		map.put("startDate", startDate);
		return alarmDao.getDeviceIdListByRoomIdAndOperation(map);
	}

	/**
	 * 根据设备编号获取设备操作时间和类型
	 */
	public List<Map<String,Object>> getOperationTimeByDeviceId(@Param("deviceId")int deviceId, @Param("operation")String operation, @Param("startDate")String startDate){
		Map<String, String> map = new HashMap<>();
		map.put("deviceId", deviceId + "");
		map.put("operation", operation);
		map.put("startDate", startDate);
		return alarmDao.getOperationTimeByDeviceId(map);
	}
	
	/**
	 * 从当前时间往前推beforeMonth个月，这个月份发生的安全报警统计
	 * @param beforeMonth
	 */
	public List<Map<String,Object>> getGradesCountByNow(int beforeMonth){
		return alarmDao.getGradesCountByNow(beforeMonth);
	}

}

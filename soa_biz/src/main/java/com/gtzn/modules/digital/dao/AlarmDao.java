package com.gtzn.modules.digital.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.ArchiveClass;
import com.gtzn.modules.monitor.entity.Alarm;

@MyBatisDao
public interface AlarmDao extends CrudDao<ArchiveClass>{
	public List<Map<String,Object>> getRoomInfo();
	public List<Map<String,Object>> getWdByRoomId(@Param("roomId")int roomId);
	public List<Map<String,Object>> getSdByRoomId(@Param("roomId")int roomId);
	public List<Alarm> getAlarmListByAlarm(Alarm alarm);
	public List<Integer> getDeviceIdListByRoomIdAndOperation(@Param("map")Map<String, String> map);
	public List<Map<String,Object>> getOperationTimeByDeviceId(@Param("map")Map<String, String> map);
	public List<Map<String,Object>> getGradesCountByNow(@Param("beforeMonth")int beforeMonth);
}
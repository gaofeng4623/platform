package com.gtzn.modules.digital.service;

import java.util.List;
import java.util.Map;

import com.gtzn.modules.digital.entity.DStatus;
import com.gtzn.modules.digital.entity.Dictionaries;
import com.gtzn.modules.digital.entity.PlatStatistics;
import com.gtzn.modules.digital.entity.Retention;

public interface RetentionService  {
	public void updateAll(Integer type);//清空全部
	public Retention queryName(Map m);//清空全部
	public void updateName(Retention record);//根据名字和类型更新
	public void save(Retention record);
	List<Map<String, Object>> getStoragePeriod(String type);//保管期限密集统计
}

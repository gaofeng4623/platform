package com.gtzn.modules.digital.service;

import java.util.List;
import java.util.Map;

import com.gtzn.modules.digital.entity.DStatus;
import com.gtzn.modules.digital.entity.Dictionaries;
import com.gtzn.modules.digital.entity.PlatStatistics;

public interface DStatusService  {
	public void save(DStatus DStatus);
	public DStatus selectByName(String name);//根据大类查询
	public void updateDstatus(DStatus dStatus);//根据大类查询
	public void updateAll();//清空全部
	public List<DStatus> listDs();
	Map<String,Object> getCapacityData();
}

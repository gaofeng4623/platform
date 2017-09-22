package com.gtzn.modules.digital.service;

import java.util.List;
import java.util.Map;

import com.gtzn.modules.digital.entity.Dictionaries;
import com.gtzn.modules.digital.entity.PlatStatistics;

public interface PlatStatisticsService  {
	Map<String, Object> queryStatisticsList();
	public void save(PlatStatistics statistics);//往mysql 导入大类
	public PlatStatistics selectByName(String name);//根据大类查询
	int updateSt(PlatStatistics dic);
	int updateList();//更新全部
	public PlatStatistics queryNd(Map m);//根据年度和大类查询
	public List<PlatStatistics> queryNdList(Map m);//根据年度查询一个集合
	public Map<String, Object> getChartsIncrementData();
}

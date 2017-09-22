package com.gtzn.modules.digital.service;


import java.util.List;
import java.util.Map;

import com.gtzn.modules.digital.entity.PlatStatistics;
import com.gtzn.modules.digital.entity.PlatUnit;

public interface PlatUnitService  {
	int updateList();//更新全部
	public void save(PlatUnit PlatUnit);//往mysql 导入
	public PlatUnit selectByName(Map m);//根据立档单位和年度查询有就更新没有添加
	int updateSt(PlatUnit plat);
	public List<PlatUnit> platList();
	public List<PlatUnit> platListCount(String nd);//柱状图统计
	public Map<String,Object> getIncrementData();
}

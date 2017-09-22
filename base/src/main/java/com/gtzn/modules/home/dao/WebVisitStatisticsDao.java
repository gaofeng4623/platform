/**
 * 
 */
package com.gtzn.modules.home.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.WebVisitStatistics;

/**
 * 网站统计DAO接口
 * @author chenyp
 * @version 2017-05-22
 */
@MyBatisDao
public interface WebVisitStatisticsDao extends CrudDao<WebVisitStatistics> {

	// 获取模块名称
	public List<WebVisitStatistics> getTemp();
	
	public List<Integer> getYear();
	//年模块浏览量
	public Integer getTempYesrCount(@Param("template") String template,@Param("year")String year);
	//获取最近12月
	public List<String> getYearMonth();
	//该月的数据
	public Integer getMonthCount(@Param("yearMonth") String yearMonth,@Param("type")String type);
	//获取最近30天
	public List<String> getMonthDay();
	//该日的统计量
	public Integer getDayCount(@Param("monthDay") String monthDay,@Param("type")String type);
	//获取该日的统计类型
	public List<String> getDayVisittype(@Param("monthDay") String monthDay);
}
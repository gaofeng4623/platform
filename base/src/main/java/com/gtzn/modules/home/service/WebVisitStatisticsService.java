/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.List;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.home.entity.WebVisitStatistics;

/**
 * 网站统计Service
 * @author chenyp
 * @version 2017-05-22
 */

public interface WebVisitStatisticsService {

	/**
	 * 获取模块名称
	 * @return
	 */
	public List<WebVisitStatistics> getTemp();
	
	public List<Integer> getYear();
	/**
	 * @param template 模块
	 * @param year  年
	 * @return 浏览量
	 */
	public Integer getTempYesrCount(String template,String year);
	/**
	 * 获取最近12月
	 * @return
	 */
	public List<String> getYearMonth();
	/**
	 * 月统计量
	 * @param yearMonth
	 * @param type
	 * @return
	 */
	public Integer getMonthCount(String yearMonth,String type);
	
	/**
	 * 获取最近30天
	 * @return
	 */
	public List<String> getMonthDay();
	/**
	 * 该日的统计量
	 * @param monthDay
	 * @param type
	 * @return
	 */
	public Integer getDayCount(String monthDay,String type);
	
	
	public List<WebVisitStatistics> findList(WebVisitStatistics webVisitStatistics);
	
	public Pager<WebVisitStatistics> findPage(Pager<WebVisitStatistics> pager, WebVisitStatistics webVisitStatistics);

	public WebVisitStatistics selectWebVisitStatisticsInfo(String key);

	public WebVisitStatistics selectWebVisitStatisticsInfo(WebVisitStatistics webVisitStatistics);
	
	public void save(WebVisitStatistics webVisitStatistics);

	//添加
	public void insertWebVisitStatisticsSelective(WebVisitStatistics webVisitStatistics);

	public void updateWebVisitStatisticsSelective(WebVisitStatistics webVisitStatistics);
	
	public void deleteWebVisitStatisticsInfo(WebVisitStatistics webVisitStatistics);

	public void deleteWebVisitStatisticsInfo(String id);

	public int batchDelete(String[] idList);

	public int batchInsert(List<WebVisitStatistics> list);

	public int executeUpdate(String sql);

	public void executeBatchUpdate(List<String> arraySql);
	
	public List<WebVisitStatistics> findAllList(WebVisitStatistics webVisitStatistics);
	
}
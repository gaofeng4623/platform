/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.entity.WebVisitStatistics;
import com.gtzn.modules.home.dao.WebVisitStatisticsDao;
import com.gtzn.modules.home.service.WebVisitStatisticsService; 

/**
 * 网站统计Service
 * @author chenyp
 * @version 2017-05-22
 */
@Service("webVisitStatistics")
@Transactional(readOnly = true)
public class WebVisitStatisticsServiceImpl extends CrudService<WebVisitStatisticsDao, WebVisitStatistics> implements WebVisitStatisticsService{

	@Autowired
	private WebVisitStatisticsDao webVisitStatisticsDao;
	
	public List<WebVisitStatistics> findList(WebVisitStatistics webVisitStatistics) {
		return super.findList(webVisitStatistics);
	}
	
	public Pager<WebVisitStatistics> findPage(Pager<WebVisitStatistics> pager, WebVisitStatistics webVisitStatistics) {
		return super.findPage(pager, webVisitStatistics);
	}

	public WebVisitStatistics selectWebVisitStatisticsInfo(String id) {
		return super.get(id);
	}

	public WebVisitStatistics selectWebVisitStatisticsInfo(WebVisitStatistics webVisitStatistics) {
		return super.get(webVisitStatistics);
	}

	@Transactional(readOnly = false)
	public void save(WebVisitStatistics webVisitStatistics) {
		super.save(webVisitStatistics);
	}

	//添加
	@Transactional(readOnly = false)
	public void insertWebVisitStatisticsSelective(WebVisitStatistics webVisitStatistics) {
		super.insertSelective(webVisitStatistics);
	}

	@Transactional(readOnly = false)
	public void updateWebVisitStatisticsSelective(WebVisitStatistics webVisitStatistics) {
		super.updateSelective(webVisitStatistics);
	}

	@Transactional(readOnly = false)
	public void deleteWebVisitStatisticsInfo(WebVisitStatistics webVisitStatistics) {
		super.delete(webVisitStatistics);
	}

	@Transactional(readOnly = false)
	public void deleteWebVisitStatisticsInfo(String id) {
		super.delete(id);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String[] idList) {
		return super.batchDelete(idList);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<WebVisitStatistics> list) {
		return super.batchInsert(list);
	}

	@Transactional(readOnly = false)
	public int executeUpdate(String sql) {
		return super.executeUpdate(sql);
	}

	@Transactional(readOnly = false)
	public void executeBatchUpdate(List<String> arraySql) {
		for (String sql : arraySql) {
			super.executeUpdate(sql);
		}
	}

	public List<WebVisitStatistics> findAllList(WebVisitStatistics webVisitStatistics){
		return super.findAllList(webVisitStatistics);
	}

	@Override
	public List<WebVisitStatistics> getTemp() {
		// TODO Auto-generated method stub
		return webVisitStatisticsDao.getTemp();
	}

	@Override
	public Integer getTempYesrCount(String template, String year) {
		// TODO Auto-generated method stub
		return webVisitStatisticsDao.getTempYesrCount(template, year);
	}

	@Override
	public List<Integer> getYear() {
		// TODO Auto-generated method stub
		return webVisitStatisticsDao.getYear();
	}

	@Override
	public List<String> getYearMonth() {
		// TODO Auto-generated method stub
		return webVisitStatisticsDao.getYearMonth();
	}

	@Override
	public Integer getMonthCount(String yearMonth,String type) {
		// TODO Auto-generated method stub
		Integer aa = webVisitStatisticsDao.getMonthCount(yearMonth, type);
		if (aa==null || (aa+"")=="") {
			return 0;
		}
		return aa ;
	}

	@Override
	public List<String> getMonthDay() {
		// TODO Auto-generated method stub
		return webVisitStatisticsDao.getMonthDay();
	}

	@Override
	public Integer getDayCount(String monthDay, String type) {
		// TODO Auto-generated method stub
		List<String> types = webVisitStatisticsDao.getDayVisittype(monthDay);
		if (types.size()==1 && types.get(0).equals(type)) {
			return webVisitStatisticsDao.getDayCount(monthDay, type);
		}
		if (types.size()==1 && !types.get(0).equals(type)) {
			return 0;
		}
		if (types.size()==2) {
			return webVisitStatisticsDao.getDayCount(monthDay, type);
		}
		return 0;
	}
}
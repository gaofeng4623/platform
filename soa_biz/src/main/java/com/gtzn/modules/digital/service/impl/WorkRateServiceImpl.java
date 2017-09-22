package com.gtzn.modules.digital.service.impl;

import java.util.List;
import java.util.Map;

import com.gtzn.common.annotation.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gtzn.modules.digital.dao.WorkRateDao;
import com.gtzn.modules.digital.entity.WorkRate;
import com.gtzn.modules.digital.service.WorkRateService;
import com.gtzn.modules.sys.entity.User;

/**
 * 工作监督
 * @author li_gm
 *
 */

@Source("digital")
@Service("workRateService")
@Transactional(readOnly = true)
public class WorkRateServiceImpl implements WorkRateService{
	
	@Autowired
	private WorkRateDao workRateDao;
	
	/**
	 * 获取工作监督进度
	 * @param workRate
	 * @return
	 */
	public WorkRate get(WorkRate workRate){
		
		return null;
	}
	
	public List<WorkRate> findUseList(WorkRate workRate,User user){
		workRate.setUser(user);
		return workRateDao.findUseList(workRate);
	}
	
	/**
	 * 获取档案征集接收工作进度统计
	 * @param workRate
	 * @param user
	 * @return
	 */
	public List<WorkRate> findCollectList(WorkRate workRate, User user){
		workRate.setUser(user);
		return workRateDao.findCollectList(workRate);
	}
	
	/**
	 * 获取档案鉴定工作进度统计
	 * @param workRate
	 * @param user
	 * @return
	 */
	public List<WorkRate> findAuthList(WorkRate workRate, User user){
		workRate.setUser(user);
		return workRateDao.findAuthList(workRate);
	}
	
	/**
	 * 获取档案移交交接文据工作进度统计
	 * @param workRate
	 * @param user
	 * @return
	 */
	public List<WorkRate> findTransferList(WorkRate workRate, User user){
		workRate.setUser(user);
		return workRateDao.findTransferList(workRate);
	}
	
	/**
	 * 获取档案借阅工作进度统计
	 * @param workRate
	 * @param user
	 * @return
	 */
	@Override
	public List<WorkRate> findborrowList(WorkRate workRate, User user) {
		workRate.setUser(user);
		return workRateDao.findborrowList(workRate);
	}
	
	/**
	 * 获取征集处统计数据
	 * @return {"dataNum":xx, "yearMonth":"2017-04"}
	 */
	public List<Map<String,Object>> findZhengJiDataByMonth(){
		return workRateDao.findZhengJiDataByMonth();
	}
	
	/**
	 * 获取利用处统计数据
	 * @return {"dataNum":xx, "yearMonth":"2017-04"}
	 */
	public List<Map<String,Object>> findLiYongDataByMonth(){
		return workRateDao.findLiYongDataByMonth();
	}
	
	/**
	 * 获取鉴定处统计数据
	 * @return {"dataNum":xx, "yearMonth":"2017"}
	 */
	public List<Map<String,Object>> findJianDingDataByMonth(){
		return workRateDao.findJianDingDataByMonth();
	}
	
	/**
	 * 获取移交统计数据
	 * @return {"dataNum":xx, "yearMonth":"2017"}
	 */
	public List<Map<String,Object>> findYiJiaoDataByMonth(){
		return workRateDao.findYiJiaoDataByMonth();
	}
	
	/**
	 * 获取借阅统计数据
	 * @return {"dataNum":xx, "yearMonth":"2017"}
	 */
	public List<Map<String,Object>> findJieYueDataByYear(){
		return workRateDao.findJieYueDataByYear();
	}

	
	/**
	 * 获取时间段内数据的单位
	 */
	public List<Map<String,Object>> getWorkRateUnit(WorkRate workRate){
		return workRateDao.getWorkRateUnit(workRate);
	}

	/**
	 * 根据单位和类型获取征集数据
	 */
	public List<Integer> getWorkRate1(WorkRate workRate){
		return workRateDao.getWorkRate1(workRate);
	}
	
	/**
	 * 根据时间获取前12个月
	 */
	public List<String> getWorkRate1Date(WorkRate workRate){
		return workRateDao.getWorkRate1Date(workRate);
	}
	
	/**
	 * 根据年份获取利用数据
	 */
	public List<Integer> getWorkRate2(WorkRate workRate){
		return workRateDao.getWorkRate2(workRate);
	}

	/**
	 * 根据时间获取前5年
	 */
	public List<String> getWorkRate3Date(WorkRate workRate){
		return workRateDao.getWorkRate3Date(workRate);
	}
	
	/**
	 * 根据类型获取鉴定数据
	 */
	public List<Integer> getWorkRate3(WorkRate workRate){
		return workRateDao.getWorkRate3(workRate);
	}
	
	/**
	 * 根据时间获取移交数据
	 */
	public List<Integer> getWorkRate4(WorkRate workRate){
		return workRateDao.getWorkRate4(workRate);
	}
	
	/**
	 * 根据时间获取借阅数据
	 */
	public List<Integer> getWorkRate5(WorkRate workRate){
		return workRateDao.getWorkRate5(workRate);
	}

}

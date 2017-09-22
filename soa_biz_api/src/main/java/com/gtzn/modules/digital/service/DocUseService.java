package com.gtzn.modules.digital.service;

import java.util.List;
import java.util.Map;

import com.gtzn.modules.sys.entity.Dict;

/**
 * 档案利用统计数据接口
 * 
 * @author jiang
 *
 */
public interface DocUseService {
	
	List<Dict> findDict(String type);
	
	/**
	 * 利用,借阅 总数统计
	 * @return
	 */
	Map<String, Object> findUseAllCountByMonth();

	/**
	 * 档案利用
	 * @return
	 */
	Map<String, Object> findUseCountByMonthUserType();

	/**
	 * 档案借阅
	 * @return
	 */
	Map<String, Object> findBorrowCountByMonthApplyType();

	/**
	 * 利用目的
	 * @return
	 */
	List<Map<String, Object>> findUseCountByUsepurpose();

	/**
	 * 借阅目的
	 * @return
	 */
	List<Map<String, Object>> findBorrowCountByUsepurpose();

	/**
	 * 全宗利用借阅数量统计
	 * @return
	 */
	Map<String, Object> findUnitCount();
	
	/**
	 * 按年龄段统计人数
	 * @return
	 */
	Map<String, Object> findUseAgeRange();
	
	/**
	 * 按年统计借阅利用数量
	 * @return
	 */
	public Map<String, Object> findUseAllCountByYear();
	
	
	/**
	 * 利用，点击按月、类型，统计档案类型
	 * @param month
	 * @param usertype
	 * @return
	 */
	public Map<String, Object> findUseCountByMonthUserTypeEq(String month, String usertype);
	/**
	 * 利用，点击按月，统计档案类型
	 * @param month
	 * @param usertype
	 * @return
	 */
	public Map<String, Object> findUseCountByClassEq(String month, String usertype);
	
	/**
	 * 借阅，点击按月，申请类型，统计档案类型
	 * @param month
	 * @param applytype
	 * @return
	 */
	public Map<String, Object> findBorrowCountByMonthApplyTypeEq(String month, String applytype);
	/**
	 * 利用，点击按月,申请类型，统计档案类型
	 * @param month
	 * @param applytype
	 * @return
	 */
	public Map<String, Object> findBorrowCountByClassEq(String month, String applytype);
}

package com.gtzn.modules.digital.service;

import java.util.List;
import java.util.Map;

import com.gtzn.modules.sys.entity.Dict;

/**
 * 驾驶舱统计service
 * @author li_gm
 *
 */
public interface DriveManageService{
	
	/**
	 * 电子借阅
	 * @return
	 */
	public int getUseCount();
	
	/**
	 * 实物借阅
	 * @return
	 */
	public int getBorrowCount();
	
	/**
	 * 档案接收
	 * @return
	 */
	public int getReceiveCount();
	
	/**
	 * 档案征集
	 * @return
	 */
	public int getCollectCount();
	
	/**
	 * 档案鉴定
	 * @return
	 */
	public int getAuthCount();
	
	/**
	 * 根据x轴统计征集各类型（捐赠、寄存、交换、征购）的档案数量
	 * @param categories
	 * @return
	 */
	public List<Map<String,Object>> getCollectStaticsByMonth(String[] categories);
	
	/**
	 * 根据x轴统计鉴定各类型（"濒危","划控","开放","密级","销毁","延期","遗失"）的档案数量
	 * @param categories
	 * @return
	 */
	public List<Map<String,Object>> getAuthStaticsByYear(String[] categories);
	
	/**
	 * 根据x轴统计年移交档案量
	 * @param categories
	 * @return
	 */
	public Integer[] getTransferStaticsByYear(String[] categories);
	
	/**
	 * 根据type和x轴统计每天征集量
	 * @param categories
	 * @param type
	 * @return
	 */
	public Integer[] getCollectStaticsByDays(String[] categories,String type);
	
	/**
	 * 统计type类型当月征集的价值分布
	 * @param category
	 * @param type
	 * @return
	 */
	public List<List<Object>> getCollectCostStatics(String category,String type);
	
	/**
	 * 月征集档案征集类型分布
	 * @param collectType
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String,Object>> getCollectTypeStatics(List<Dict> collectType,String category,String type);
	
	/**
	 * 月征集档案征集来源分布---饼图
	 * @param sourceType
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String,Object>> getCollectSourceStatics(String[] sourceType,String category,String type);
	
	/**
	 * 月征集档案征集来源机构性质分布---饼图
	 * @param institutionType
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String,Object>> getCollectSourceOfDeptStatics(List<Dict> institutionType,String category,String type);
	
	/**
	 * 按全宗显示年鉴定档案量---柱状图
	 * @param category
	 * @param type
	 * @return
	 */
	public Map<String,Object> getAuthStaticsByUnit(String category,String type);
	
	/**
	 * 显示档案类型鉴定量---折线图
	 * @param category
	 * @param type
	 * @return
	 */
	public Map<String,Object> getAuthStaticsByStoreId(String category,String type);
	
	/**
	 * 显示移交量全宗分布
	 * @param category
	 * @return
	 */
	public Map<String,Object> getTransferStaticsByUnit(String category);
	
	/**
	 * 显示按档案类型统计移交量
	 * @param category
	 * @return
	 */
	public Map<String,Object> getTransferStaticsByArchiveType(String category);
	
	/**
	 * 显示移交保管期限分布
	 * @param archivePeriod
	 * @param category
	 * String ySum
	 * @return
	 */
	public List<Map<String,Object>> getTransferStaticsByPeriod(Map<String,Object> archivePeriod,String category,String ySum);
	
	/**
	 * 显示移交交接性质分布---饼图
	 * @param transferType
	 * @param category
	 * @return
	 */
	public List<Map<String,Object>> getTransferStaticsByType(List<Dict> transferType,String category);
	
	/**
	 * 档案利用方式统计
	 * @param useMode
	 * @return
	 */
	public List<Map<String,Object>> getUseStatics(List<Dict> useMode);
	
	/**
	 * 根据利用方式label获取dict
	 * @param dict
	 * @return
	 */
	public Dict getDictByLabel(Dict dict);
	
	/**
	 * 显示最近12个月档案利用数量
	 * @param useMode
	 * @return
	 */
	public Map<String,Object> getUseModeStaticsByMonths(String useMode);
	
	/**
	 * 显示档案利用全宗分布
	 * @param useMode
	 * @return
	 */
	public Map<String,Object> getUseModeStaticsByUnit(String useMode);
	
	/**
	 * 显示按档案类型统计移交量
	 * @param useMode
	 * @return
	 */
	public Map<String,Object> getUseModeStaticsByArchiveType(String useMode);
	
	/**
	 * 根据借阅人性质统计利用主体利用量分布----饼图
	 * @param useMode
	 * @return
	 */
	public List<Map<String,Object>> getUseModeStaticsByUser(List<Dict> userType,String useMode);
	
	/**
	 * 显示最近12个月档案利用数量
	 * @param usePurpose
	 * @return
	 */
	public Map<String,Object> getUsePurposeStaticsByMonths(String usePurpose);
	
	/**
	 * 显示档案利用全宗分布
	 * @param usePurpose
	 * @return
	 */
	public Map<String,Object> getUsePurposeStaticsByUnit(String usePurpose);
	
	/**
	 * 显示按档案类型统计移交量
	 * @param usePurpose
	 * @return
	 */
	public Map<String,Object> getUsePurposeStaticsByArchiveType(String usePurpose);
	
	/**
	 * 根据借阅人性质统计利用主体利用量分布----饼图
	 * @param usePurpose
	 * @return
	 */
	public List<Map<String,Object>> getUsePurposeStaticsByUser(List<Dict> userType,String usePurpose);
	
	
	
	
}

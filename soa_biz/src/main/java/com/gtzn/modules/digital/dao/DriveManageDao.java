package com.gtzn.modules.digital.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.sys.entity.Dict;

/**
 *  驾驶舱统计Dao
 * @author li_gm
 *
 */
@MyBatisDao
public interface DriveManageDao {

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
	 * 根据类型（捐赠、寄存、交换、征购）和时间月统计征集档案数量
	 * @param type
	 * @return
	 */
	public List<Map<String,String>> getCollectStaticsByType(@Param("type")String type);
	
	/**
	 * 根据类型（"濒危","划控","开放","密级","销毁","延期","遗失"）和时间月统计鉴定档案数量
	 * @param type
	 * @return
	 */
	public List<Map<String,String>> getAuthStaticsByType(@Param("type")String type);
	
	/**
	 * 根据x轴统计年移交档案量
	 * @return
	 */
	public List<Map<String,String>> getTransferStaticsByYear();
	
	/**
	 * 根据type和x轴统计每天征集量
	 * @return
	 */
	public List<Map<String,String>> getCollectStaticsByDays(@Param("type")String type);
	
	/**
	 * 统计type类型当月征集的价值分布
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> getCollectCostStatics(@Param("category")String category,
			@Param("type")String type);
	
	/**
	 * 月征集档案征集类型分布
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String, String>> getCollectTypeStatics(@Param("category")String category,@Param("type")String type);
	
	/**
	 * 月征集档案征集来源分布
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String, String>> getCollectSourceStatics(@Param("category")String category,@Param("type")String type);
	
	/**
	 * 月征集档案征集来源机构性质分布
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String, String>> getCollectSourceOfDeptStatics(@Param("category")String category,@Param("type")String type);
	
	/**
	 * 按全宗显示年鉴定档案量---柱状图
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> getAuthStaticsByUnit(@Param("category")String category,@Param("type")String type);
	
	/**
	 * 显示档案类型鉴定量
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> getAuthStaticsByStoreId(@Param("category")String category,@Param("type")String type);
	
	/**
	 * 显示移交量全宗分布
	 * @param category
	 * @return
	 */
	public List<Map<String, Object>> getTransferStaticsByUnit(@Param("category")String category);
	
	/**
	 * 显示按档案类型统计移交量
	 * @param category
	 * @return
	 */
	public List<Map<String, Object>> getTransferStaticsByArchiveType(@Param("category")String category);
	
	/**
	 * 显示移交保管期限分布
	 * @param category
	 * @return
	 */
	public Map<String, Object> getTransferStaticsByPeriod(@Param("category")String category);
	
	/**
	 * 显示移交交接性质分布---饼图
	 * @param category
	 * @return
	 */
	public List<Map<String, String>> getTransferStaticsByType(@Param("category")String category);
	
	/**
	 * 档案利用方式统计
	 * @return
	 */
	public List<Map<String, String>> getUseStatics();
	
	/**
	 * 根据利用方式label获取dict
	 * @param dict
	 * @return
	 */
	public Dict getDictByLabel(Dict dict);
	
	/**
	 * 根据利用方式统计每月统计量
	 * @param useMode
	 * @return
	 */
	public List<Map<String,String>> getUseModeStaticsByMonths(@Param("useMode")String useMode);
	
	/**
	 * 根据利用方式统计各个全宗的统计量
	 * @param useMode
	 * @return
	 */
	public List<Map<String,Object>> getUseModeStaticsByUnit(@Param("useMode")String useMode);
	
	/**
	 * 显示按档案类型统计利用量
	 * @param useMode
	 * @return
	 */
	public List<Map<String,Object>> getUseModeStaticsByArchiveType(@Param("useMode")String useMode);
	
	/**
	 * 根据借阅人性质统计利用主体利用量分布---饼图
	 * @param useMode
	 * @return
	 */
	public List<Map<String, String>> getUseModeStaticsByUser(@Param("useMode")String useMode);
	
	/**
	 * 根据利用方式统计每月统计量
	 * @param usePurpose
	 * @return
	 */
	public List<Map<String,String>> getUsePurposeStaticsByMonths(@Param("usePurpose")String usePurpose);
	
	/**
	 * 根据利用方式统计各个全宗的统计量
	 * @param usePurpose
	 * @return
	 */
	public List<Map<String,Object>> getUsePurposeStaticsByUnit(@Param("usePurpose")String usePurpose);
	
	/**
	 * 显示按档案类型统计利用量
	 * @param usePurpose
	 * @return
	 */
	public List<Map<String,Object>> getUsePurposeStaticsByArchiveType(@Param("usePurpose")String usePurpose);
	
	/**
	 * 根据借阅人性质统计利用主体利用量分布---饼图
	 * @param usePurpose
	 * @return
	 */
	public List<Map<String, String>> getUsePurposeStaticsByUser(@Param("usePurpose")String usePurpose);
	
	
}

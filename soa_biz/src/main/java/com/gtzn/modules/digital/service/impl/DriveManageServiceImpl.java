package com.gtzn.modules.digital.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gtzn.common.annotation.Source;
import com.gtzn.common.utils.DateUtils;
import com.gtzn.modules.base.entity.Constant;
import com.gtzn.modules.digital.dao.DriveManageDao;
import com.gtzn.modules.digital.service.DriveManageService;
import com.gtzn.modules.sys.entity.Dict;

/**
 * 驾驶舱统计service
 * @author li_gm
 *
 */
@Source("digital")
@Service("driveManageService")
@Transactional(readOnly = true)
public class DriveManageServiceImpl implements DriveManageService{
	
	@Autowired
	private DriveManageDao driveManageDao;
	
	/**
	 * 电子借阅
	 * @return
	 */
	public int getUseCount(){
		return driveManageDao.getUseCount();
	}
	
	/**
	 * 实物借阅
	 * @return
	 */
	public int getBorrowCount(){
		return driveManageDao.getBorrowCount();
	}
	
	/**
	 * 档案接收
	 * @return
	 */
	public int getReceiveCount(){
		return driveManageDao.getReceiveCount();
	}
	
	/**
	 * 档案征集
	 * @return
	 */
	public int getCollectCount(){
		return driveManageDao.getCollectCount();
	}
	
	/**
	 * 档案鉴定
	 * @return
	 */
	public int getAuthCount(){
		return driveManageDao.getAuthCount();
	}
	
	/**
	 * 根据x轴获取征集各类型（捐赠、寄存、交换、征购）的档案数量
	 * @param categories
	 * @return
	 */
	public List<Map<String,Object>> getCollectStaticsByMonth(String[] categories){
		String[] types = Constant.collect_type;
		List<Map<String,Object>> list = Lists.newArrayList();
		for(String type : types){
			List<Map<String,String>> dataList = driveManageDao.getCollectStaticsByType(type);
			Integer[] numList = this.dealList(dataList, categories);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", type);
			map.put("data", numList);
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 根据x轴获取鉴定各类型（"濒危","划控","开放","密级","销毁","延期","遗失"）的档案数量
	 * @param categories
	 * @return
	 */
	public List<Map<String,Object>> getAuthStaticsByYear(String[] categories){
		String[] types = Constant.auth_type;
		List<Map<String,Object>> list = Lists.newArrayList();
		for(String type : types){
			List<Map<String,String>> dataList = driveManageDao.getAuthStaticsByType(type);
			Integer[] numList = this.dealList(dataList, categories);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", type);
			map.put("data", numList);
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 根据x轴统计年移交档案量
	 * @param categories
	 * @return
	 */
	public Integer[] getTransferStaticsByYear(String[] categories){
		List<Map<String,String>> dataList = driveManageDao.getTransferStaticsByYear();
		Integer[] numList = this.dealList(dataList, categories);
		return numList;
	}
	
	/**
	 * 根据type和x轴统计每天征集量
	 * @param categories
	 * @param type
	 * @return
	 */
	public Integer[] getCollectStaticsByDays(String[] categories,String type){
		List<Map<String,String>> dataList = driveManageDao.getCollectStaticsByDays(type);
		Integer[] numList = this.dealList(dataList, categories);
		return numList;
	}
	
	/**
	 * 统计type类型当月征集的价值分布
	 * @param category
	 * @param type
	 * @return
	 */
	public List<List<Object>> getCollectCostStatics(String category,String type){
		List<Map<String, Object>> slist = driveManageDao.getCollectCostStatics(category, type);
		List<List<Object>> list = Lists.newArrayList();
		for(Map<String, Object> map : slist ){
			List<Object> temp = Lists.newArrayList();
			/*for (Map.Entry entry : map.entrySet()) {
				temp.add(entry.getValue());
			}*/
			temp.add(map.get("futurecost"));
			temp.add(map.get("lastcost"));
			temp.add(map.get("incount"));
			list.add(temp);
		}
		return list;
	}
	
	/**
	 * 月征集档案征集类型分布
	 * @param collectType
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String,Object>> getCollectTypeStatics(List<Dict> collectType,String category,String type){
		List<Map<String,Object>> list = Lists.newArrayList();
		List<Map<String,String>> numList = driveManageDao.getCollectTypeStatics(category,type);
		Map<String,Double> map = this.dealPieData(numList,collectType);
		for(Dict dict : collectType){
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("name", dict.getLabel());
			temp.put("y", map.get(dict.getValue()));
			list.add(temp);
		}
		return list;
	}
	
	/**
	 * 月征集档案征集来源分布---饼图
	 * @param sourceType
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String,Object>> getCollectSourceStatics(String[] sourceType,String category,String type){
		List<Map<String,Object>> list = Lists.newArrayList();
		List<Map<String,String>> numList = driveManageDao.getCollectSourceStatics(category, type);
		List<Dict> types = Lists.newArrayList();
		for(String source : sourceType){
			Dict dict = new Dict();
			dict.setValue(source);
			dict.setLabel(source);
			types.add(dict);
		}
		Map<String,Double> map = this.dealPieData(numList,types);
		for(String source : sourceType){
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("name", source);
			temp.put("y", map.get(source));
			list.add(temp);
		}
		return list;
	}
	
	/**
	 * 月征集档案征集来源机构性质分布---饼图
	 * @param institutionType
	 * @param category
	 * @param type
	 * @return
	 */
	public List<Map<String,Object>> getCollectSourceOfDeptStatics(List<Dict> institutionType,
			String category,String type){
		List<Map<String,Object>> list = Lists.newArrayList();
		List<Map<String,String>> numList = driveManageDao.getCollectSourceOfDeptStatics(category,type);
		Map<String,Double> map = this.dealPieData(numList,institutionType);
		for(Dict dict : institutionType){
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("name", dict.getLabel());
			temp.put("y", map.get(dict.getValue()));
			list.add(temp);
		}
		return list;
	}
	
	/**
	 * 按全宗显示年鉴定档案量---柱状图
	 * @param category
	 * @param type
	 * @return
	 */
	public Map<String,Object> getAuthStaticsByUnit(String category,String type){
		List<Map<String,Object>> list = driveManageDao.getAuthStaticsByUnit(category,type);
		Map<String,Object> map = Maps.newHashMap();
		List<String> categories = Lists.newArrayList();
		List<Integer> data = Lists.newArrayList();
		for(Map<String,Object> temp : list){
			categories.add(temp.get("name").toString());
			data.add(Integer.parseInt(temp.get("num").toString()));
		}
		map.put("categories", categories);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 显示档案类型鉴定量---折线图
	 * @param category
	 * @param type
	 * @return
	 */
	public Map<String,Object> getAuthStaticsByStoreId(String category,String type){
		List<Map<String,Object>> list = driveManageDao.getAuthStaticsByStoreId(category,type);
		Map<String,Object> map = Maps.newHashMap();
		List<String> categories = Lists.newArrayList();
		List<Integer> data = Lists.newArrayList();
		for(Map<String,Object> temp : list){
			categories.add(temp.get("name").toString());
			data.add(Integer.parseInt(temp.get("num").toString()));
		}
		map.put("categories", categories);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 显示移交量全宗分布
	 * @param category
	 * @return
	 */
	public Map<String,Object> getTransferStaticsByUnit(String category){
		List<Map<String,Object>> list = driveManageDao.getTransferStaticsByUnit(category);
		Map<String,Object> map = Maps.newHashMap();
		List<String> categories = Lists.newArrayList();
		List<Integer> data = Lists.newArrayList();
		for(Map<String,Object> temp : list){
			categories.add(temp.get("name").toString());
			data.add(Integer.parseInt(temp.get("num").toString()));
		}
		map.put("categories", categories);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 显示按档案类型统计移交量
	 * @param category
	 * @return
	 */
	public Map<String,Object> getTransferStaticsByArchiveType(String category){
		List<Map<String,Object>> list = driveManageDao.getTransferStaticsByArchiveType(category);
		Map<String,Object> map = Maps.newHashMap();
		List<String> categories = Lists.newArrayList();
		List<Integer> data = Lists.newArrayList();
		for(Map<String,Object> temp : list){
			categories.add(temp.get("name").toString());
			data.add(Integer.parseInt(temp.get("num").toString()));
		}
		map.put("categories", categories);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 显示移交保管期限分布
	 * @param archivePeriod
	 * @param category
	 * @param ysum 总和 
	 * @return
	 */
	public List<Map<String,Object>> getTransferStaticsByPeriod(Map<String,Object> archivePeriod,String category,String ySum){
		List<Map<String,Object>> list = Lists.newArrayList();
		int ysum = Integer.parseInt(ySum);
		for(Map.Entry entry : archivePeriod.entrySet()){
			Map<String,Object> map = new HashMap<>();
			map.put("type", entry.getKey().toString());
			map.put("name",entry.getValue().toString());
			map.put("y", 0.00);
			list.add(map);
		}
		if(ysum != 0){
			Map<String,Object> numMap = driveManageDao.getTransferStaticsByPeriod(category);
			for(Map<String,Object> map : list){
				Double d = (Double.parseDouble(numMap.get(map.get("type").toString()).toString())/ysum)*100;
				map.put("y", d);
			}
		}
		return list;
		
	}
	
	/**
	 * 显示移交交接性质分布---饼图
	 * @param transferType
	 * @param category
	 * @return
	 */
	public List<Map<String,Object>> getTransferStaticsByType(List<Dict> transferType,String category){
		List<Map<String,Object>> list = Lists.newArrayList();
		List<Map<String,String>> numList = driveManageDao.getTransferStaticsByType(category);
		Map<String,Double> map = this.dealPieData(numList,transferType);
		for(Dict dict : transferType){
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("name", dict.getLabel());
			temp.put("y", map.get(dict.getValue()));
			list.add(temp);
		}
		return list;
	}
	
	/**
	 * 档案利用方式统计
	 * @param useMode
	 * @return
	 */
	public List<Map<String,Object>> getUseStatics(List<Dict> useMode){
		List<Map<String,Object>> list = Lists.newArrayList();
		List<Map<String,String>> numList = driveManageDao.getUseStatics();
		Map<String,Double> map = this.dealPieData(numList,useMode);
		for(Dict dict : useMode){
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("name", dict.getLabel());
			temp.put("y", map.get(dict.getValue()));
			list.add(temp);
		}
		return list;
	}
	
	/**
	 * 根据利用方式label获取dict
	 * @param dict
	 * @return
	 */
	public Dict getDictByLabel(Dict dict){
		return driveManageDao.getDictByLabel(dict);
	}
	
	/**
	 * 显示最近12个月档案利用数量
	 * @param useMode
	 * @return
	 */
	public Map<String,Object> getUseModeStaticsByMonths(String useMode){
		Map<String, Object> map = new HashMap<>();
		String[] categories = DateUtils.getLastMonths(12);
		map.put("categories", categories);
		List<Map<String,String>> dataList = driveManageDao.getUseModeStaticsByMonths(useMode);
		Integer[] data = this.dealList(dataList,categories);
		map.put("columnData", data);
		map.put("splineData", data);
		return map;
	}
	
	/**
	 * 显示档案利用全宗分布
	 * @param useMode
	 * @return
	 */
	public Map<String,Object> getUseModeStaticsByUnit(String useMode){
		List<Map<String,Object>> list = driveManageDao.getUseModeStaticsByUnit(useMode);
		Map<String,Object> map = Maps.newHashMap();
		List<String> categories = Lists.newArrayList();
		List<Integer> data = Lists.newArrayList();
		for(Map<String,Object> temp : list){
			categories.add(temp.get("name").toString());
			data.add(Integer.parseInt(temp.get("num").toString()));
		}
		map.put("categories", categories);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 显示按档案类型统计利用量
	 * @param useMode
	 * @return
	 */
	public Map<String,Object> getUseModeStaticsByArchiveType(String useMode){
		List<Map<String,Object>> list = driveManageDao.getUseModeStaticsByArchiveType(useMode);
		Map<String,Object> map = Maps.newHashMap();
		List<String> categories = Lists.newArrayList();
		List<Integer> data = Lists.newArrayList();
		for(Map<String,Object> temp : list){
			categories.add(temp.get("name").toString());
			data.add(Integer.parseInt(temp.get("num").toString()));
		}
		map.put("categories", categories);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 根据借阅人性质统计利用主体利用量分布----饼图
	 * @param useMode
	 * @return
	 */
	public List<Map<String,Object>> getUseModeStaticsByUser(List<Dict> userType,String useMode){
		List<Map<String,Object>> list = Lists.newArrayList();
		List<Map<String,String>> numList = driveManageDao.getUseModeStaticsByUser(useMode);
		Map<String,Double> map = this.dealPieData(numList,userType);
		for(Dict dict : userType){
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("name", dict.getLabel());
			temp.put("y", map.get(dict.getValue()));
			list.add(temp);
		}
		return list;
	}
	
	/**
	 * 显示最近12个月档案利用数量
	 * @param usePurpose
	 * @return
	 */
	public Map<String,Object> getUsePurposeStaticsByMonths(String usePurpose){
		Map<String, Object> map = new HashMap<>();
		String[] categories = DateUtils.getLastMonths(12);
		map.put("categories", categories);
		List<Map<String,String>> dataList = driveManageDao.getUsePurposeStaticsByMonths(usePurpose);
		Integer[] data = this.dealList(dataList,categories);
		map.put("columnData", data);
		map.put("splineData", data);
		return map;
	}
	
	/**
	 * 显示档案利用全宗分布
	 * @param usePurpose
	 * @return
	 */
	public Map<String,Object> getUsePurposeStaticsByUnit(String usePurpose){
		List<Map<String,Object>> list = driveManageDao.getUsePurposeStaticsByUnit(usePurpose);
		Map<String,Object> map = Maps.newHashMap();
		List<String> categories = Lists.newArrayList();
		List<Integer> data = Lists.newArrayList();
		for(Map<String,Object> temp : list){
			categories.add(temp.get("name").toString());
			data.add(Integer.parseInt(temp.get("num").toString()));
		}
		map.put("categories", categories);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 显示按档案类型统计移交量
	 * @param usePurpose
	 * @return
	 */
	public Map<String,Object> getUsePurposeStaticsByArchiveType(String usePurpose){
		List<Map<String,Object>> list = driveManageDao.getUsePurposeStaticsByArchiveType(usePurpose);
		Map<String,Object> map = Maps.newHashMap();
		List<String> categories = Lists.newArrayList();
		List<Integer> data = Lists.newArrayList();
		for(Map<String,Object> temp : list){
			categories.add(temp.get("name").toString());
			data.add(Integer.parseInt(temp.get("num").toString()));
		}
		map.put("categories", categories);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 根据借阅人性质统计利用主体利用量分布----饼图
	 * @param usePurpose
	 * @return
	 */
	public List<Map<String,Object>> getUsePurposeStaticsByUser(List<Dict> userType,String usePurpose){
		List<Map<String,Object>> list = Lists.newArrayList();
		List<Map<String,String>> numList = driveManageDao.getUsePurposeStaticsByUser(usePurpose);
		Map<String,Double> map = this.dealPieData(numList,userType);
		for(Dict dict : userType){
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("name", dict.getLabel());
			temp.put("y", map.get(dict.getValue()));
			list.add(temp);
		}
		return list;
	}
	
	
	
	
	
	/**
	 * 按x坐标种类数据整合图表数据for驾驶舱管理
	 * @param dataList
	 * @param model
	 * @return
	 */
	private Integer[] dealList(List<Map<String,String>> dataList,String[] model){
		int size = model.length;
		Integer[] data = new Integer[size];
		LinkedHashMap<String,Integer> dMap = new LinkedHashMap<String,Integer>();
		for (String string : model) {
			dMap.put(string, 0);
		}
		for (Map map : dataList) {
			String key = map.get("date").toString();
			String value = map.get("num").toString();
			if(dMap.containsKey(key)){
				dMap.put(key, Integer.parseInt(value));
			}
		}
		int i = 0;
		for (Map.Entry<String, Integer> entry : dMap.entrySet()) {
		     data[i++] = entry.getValue();
		}
		return data;
	}
	
	private Map<String,Double> dealPieData(List<Map<String,String>> dataList,List<Dict> model){
		LinkedHashMap<String,Double> dMap = new LinkedHashMap<String,Double>();
		for (Dict dict : model) {
			dMap.put(dict.getValue(), 0.00);
		}
		int sumNum = 0;
		for (Map map : dataList) {
			sumNum = sumNum + Integer.parseInt(map.get("num").toString());
		}
		if(sumNum != 0){
			for (Map map : dataList) {
				String key = map.get("type").toString();
				String value = map.get("num").toString();
				if(dMap.containsKey(key)){
					Double d = (Double.parseDouble(value)/sumNum)*100; 
					dMap.put(key, d);
				}
			}
		}
		return dMap;
	}
	
	
	
}

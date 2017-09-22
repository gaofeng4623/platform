package com.gtzn.modules.security.service.impl;

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
import com.gtzn.modules.security.dao.SecurityStaticsDao;
import com.gtzn.modules.security.service.SecurityStaticsService;

/**
 * 驾驶舱安全中心统计service
 * @author li_gm
 *
 */
@Source("security")
@Service("securityStaticsService")
@Transactional(readOnly = true)
public class SecurityStaticsServiceImpl implements SecurityStaticsService{
	
	@Autowired
	private SecurityStaticsDao securityStaticsDao;
	
	/**
	 *  安全中心---库房温湿度统计
	 */
	@Override
	public Map<String, Object> getRoomTHStatics() {
		List<String> roomIds = securityStaticsDao.getRoomList();
		List<Map<String,Object>> list = Lists.newArrayList();
		Map<String,Object> data = Maps.newHashMap();
		for(String roomId : roomIds){
			Map<String,Object> rlt = securityStaticsDao.getRoomTHStaticsById(roomId);
			if (rlt != null && !"".equals(rlt)) {
				list.add(rlt);
			}
		}
		List<String> categories = Lists.newArrayList();
		List<Double> temperature = Lists.newArrayList();
		List<Double> humidity = Lists.newArrayList();
		for(Map<String,Object> temp : list){
			categories.add(temp.get("roomName").toString());
			temperature.add(Double.valueOf(temp.get("temperature").toString()));
			humidity.add(Double.valueOf(temp.get("humidity").toString()));
		}
		data.put("categories", categories);
		data.put("temperature", temperature);
		data.put("humidity", humidity);
		return data;
	}
	
	/**
	 * 安全中心---按照报警类型统计报警次数
	 * @return
	 */
	public Map<String, Object> getAlarmStatics(){
		List<Map<String,Object>> list = securityStaticsDao.getAlarmStatics();
		Map<String,Object> data = Maps.newHashMap();
		List<String> categories = Lists.newArrayList();
		List<Integer> numList = Lists.newArrayList();
		for(Map<String,Object> temp : list){
			categories.add(temp.get("category").toString());
			numList.add(Integer.parseInt(temp.get("num").toString()));
		}
		data.put("categories", categories);
		data.put("data", numList);
		return data;
	}
	
	/**
	 * 温湿度每天平均值变化曲线
	 * @param category
	 * @return
	 */
	public Map<String, Object> getRoomTHStaticsByDay(String category){
		Map<String,Object> data = Maps.newHashMap();
		String[] categories = DateUtils.getLastDays(30);
		List<Map<String,String>> list = securityStaticsDao.getRoomTHStaticsByDay(category);
		Map<String,Object> temp = this.dealDoubleList(list, categories);
		data.put("categories", categories);
		data.put("temperature", temp.get("temperature"));
		data.put("humidity", temp.get("humidity"));
		return data;
	}
	
	/**
	 * 最近12个月温湿度变化范围
	 * @param category
	 * @return
	 */
	public Map<String, Object> getRoomTHStaticsByMonth(String category){
		Map<String,Object> data = Maps.newHashMap();
		String[] categories = DateUtils.getLastMonths(12);
		List<Map<String,String>> list = securityStaticsDao.getRoomTHStaticsByMonth(category);
		Map<String,Object> temp = this.dealListData(list, categories);
		data.put("categories", categories);
		data.put("temperature", temp.get("temperature"));
		data.put("humidity", temp.get("humidity"));
		return data;
	}
	
	/**
	 * 根据报警类型统计最近12个月的报警次数
	 * @param category
	 * @return
	 */
	public Map<String, Object> getAlarmStaticsByMonth(String category){
		Map<String,Object> map = Maps.newHashMap();
		String[] categories = DateUtils.getLastMonths(12);
		List<Map<String,String>> list = securityStaticsDao.getAlarmStaticsByMonth(category);
		Integer[] data = this.dealList(list, categories);
		map.put("categories", categories);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 该类型的报警在各个库房的分布
	 * @param category
	 * @return
	 */
	public Map<String, Object> getAlarmStaticsByRoom(String category){
		Map<String,Object> map = Maps.newHashMap();
		List<Map<String,Object>> list = securityStaticsDao.getAlarmStaticsByRoom(category);
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
	 * 该类报警在各个设备上的分布
	 * @param category
	 * @return
	 */
	public Map<String, Object> getAlarmStaticsByDevice(String category){
		Map<String,Object> map = Maps.newHashMap();
		List<String> categories = Lists.newArrayList();
		List<Integer> data = Lists.newArrayList();
		List<Integer> duration = Lists.newArrayList();
		List<Map<String,Object>> list = securityStaticsDao.getAlarmStaticsByDevice(category);
		for(Map<String,Object> temp : list){
			categories.add(temp.get("name").toString());
			data.add(Integer.parseInt(temp.get("num").toString()));
			duration.add(Integer.parseInt(temp.get("duration").toString()));
		}
		map.put("categories", categories);
		map.put("data", data);
		map.put("duration", duration);
		return map;
	}
	
	/**
	 * 统计安全报警次数
	 * @return
	 */
	public int getAlarmCount(){
		return securityStaticsDao.getAlarmCount();
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
	
	/**
	 * 按x坐标种类数据整合图表数据for驾驶舱管理
	 * @param dataList
	 * @param model
	 * @return
	 */
	private Map<String,Object> dealDoubleList(List<Map<String,String>> dataList,String[] model){
		Map<String,Object> data = Maps.newHashMap();
		int size = model.length;
		Double[] temperature = new Double[size];
		Double[] humidity = new Double[size];
		LinkedHashMap<String,Double> tMap = new LinkedHashMap<String,Double>();
		LinkedHashMap<String,Double> hMap = new LinkedHashMap<String,Double>();
		for (String string : model) {
			tMap.put(string, 0.00);
			hMap.put(string, 0.00);
		}
		for (Map map : dataList) {
			String key = map.get("date").toString();
			String value = map.get("temperature").toString();
			if(tMap.containsKey(key)){
				tMap.put(key, Double.parseDouble(value));
			}
			String humid = map.get("humidity").toString();
			if(hMap.containsKey(key)){
				hMap.put(key, Double.parseDouble(humid));
			}
		}
		int i = 0;
		for (Map.Entry<String, Double> entry : tMap.entrySet()) {
			temperature[i++] = entry.getValue();
		}
		int j = 0;
		for (Map.Entry<String, Double> entry : hMap.entrySet()) {
			humidity[j++] = entry.getValue();
		}
		data.put("temperature", temperature);
		data.put("humidity", humidity);
		return data;
	}
	
	/**
	 * 按x坐标种类数据整合图表数据for驾驶舱管理
	 * @param dataList
	 * @param model
	 * @return
	 */
	private Map<String,Object> dealListData(List<Map<String,String>> dataList,String[] model){
		Map<String,Object> data = Maps.newHashMap();
		int size = model.length;
		LinkedHashMap<String,List> tMap = new LinkedHashMap<String,List>();
		LinkedHashMap<String,List> hMap = new LinkedHashMap<String,List>();
		for (String string : model) {
			tMap.put(string, Lists.newArrayList());
			hMap.put(string, Lists.newArrayList());
		}
		for (Map map : dataList) {
			List<Double> tList = Lists.newArrayList();
			List<Double> hList = Lists.newArrayList();
			String key = map.get("date").toString();
			String minT = map.get("minT").toString();
			String maxT = map.get("maxT").toString();
			tList.add(Double.parseDouble(minT));
			tList.add(Double.parseDouble(maxT));
			if(tMap.containsKey(key)){
				tMap.put(key, tList);
			}
			String minH = map.get("minH").toString();
			String maxH = map.get("maxH").toString();
			hList.add(Double.parseDouble(minH));
			hList.add(Double.parseDouble(maxH));
			if(hMap.containsKey(key)){
				hMap.put(key, hList);
			}
		}
		List<Object> temperature = Lists.newArrayList();
		for (Map.Entry<String, List> entry : tMap.entrySet()) {
			temperature.add(entry.getValue());
		}
		List<Object> humidity = Lists.newArrayList();
		for (Map.Entry<String, List> entry : hMap.entrySet()) {
			humidity.add(entry.getValue());
		}
		data.put("temperature", temperature);
		data.put("humidity", humidity);
		return data;
	}
	
		
	
}

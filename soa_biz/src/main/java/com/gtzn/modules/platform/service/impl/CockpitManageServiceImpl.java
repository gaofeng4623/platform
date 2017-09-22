package com.gtzn.modules.platform.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.modules.platform.dao.CockpitManageDao;
import com.gtzn.modules.platform.service.CockpitManageService;

/**
 * 驾驶舱中平台本身的统计service
 * @author li_gm
 *
 */
@Service("cockpitManageService")
@Transactional(readOnly = true)
public class CockpitManageServiceImpl implements CockpitManageService{
	
	@Autowired
	private CockpitManageDao cockpitManageDao;
	
	/**
	 * 根据x轴统计行业资讯月发布量
	 * @param categories
	 * @return
	 */
	public Integer[] getInformStaticsByMonth(String[] categories){
		List<Map<String,String>> dataList = cockpitManageDao.getInformStaticsByMonth();
		Integer[] numList = this.dealList(dataList, categories);
		return numList;
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
	
	
	
}

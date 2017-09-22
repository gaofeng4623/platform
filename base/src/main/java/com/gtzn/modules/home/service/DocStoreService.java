package com.gtzn.modules.home.service;

import java.util.Map;

/**
 * 档案馆藏数量Service
 * @author chenyp
 *
 */
public interface DocStoreService {

	public Map<String,Object> getChartsGrossData();
	
	public Map<String,Object> getChartsNumData();
	
	public Map<String, Object> getChartsCapacityData();
	
	public Map<String, Object> getChartsIncrementData();
	
	public Map<String, Object> getChartsStorageData();
}

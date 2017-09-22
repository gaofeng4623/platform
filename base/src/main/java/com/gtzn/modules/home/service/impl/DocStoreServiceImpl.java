package com.gtzn.modules.home.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.modules.home.service.DocStoreService;

/**
 * 档案馆藏数量Service
 * @author chenyp
 *
 */
@Service("docStore")
@Transactional(readOnly = true)
public class DocStoreServiceImpl implements DocStoreService {

	@Override
	public Map<String, Object> getChartsGrossData() {
		Map<String, Object> map = new HashMap<>();
		
		String[] categories={"诉讼档案","验资档案","照片档案","公证档案","婚姻档案","文书档案","工商档案","工程项目档案",
				"知青档案","死亡档案","音像档案","查抄档案","荣誉实物档案","设备档案","数据采集","婴儿出生证明","会计档案","图书资料"};
		map.put("categories", categories);
		
		List<Map<String,Object>> list = new ArrayList<>();
		
		Map<String,Object> listMap1= new HashMap<>();
		Object[] listMap1Date = {47200, 64600, 63100, 71500, 32100, 75100, 63200, 74500,47200, 64600, 63100, 71500, 32100, 75100, 63200, 74500,63200, 74500};
		listMap1.put("name", "电子");
		listMap1.put("data", listMap1Date);
		list.add(listMap1);
		
		Map<String,Object> listMap2= new HashMap<>();
		Object[] listMap2Date = {62000, 72200, 74500, 76100, 80010, 84600, 79800, 83500,0, 0, 0, 71500, 32100, 75100, 63200, 74500,0, 0};
		listMap2.put("name", "实物");
		listMap2.put("data", listMap2Date);
		list.add(listMap2);
		
		map.put("data", list);
		return map;
	}

	@Override
	public Map<String, Object> getChartsNumData() {
		Map<String, Object> map = new HashMap<>();
		
		String[] categories={"2010","2011", "2012", "2013","2014", "2015", "2016"};
		map.put("categories", categories);
		
		List<Map<String,Object>> list = new ArrayList<>();
		
		Map<String,Object> listMap1= new HashMap<>();
		Object[] listMap1Date = {0, 72000,  115000,  159000,  200000,  243000,  273000};
		listMap1.put("name", "档案馆藏数量");
		listMap1.put("data", listMap1Date);
		list.add(listMap1);
		
		map.put("data", list);
		return map;
	}

	@Override
	public Map<String, Object> getChartsCapacityData() {
		Map<String, Object> map = new HashMap<>();
		
		String[] categories={"2010","2011", "2012", "2013","2014", "2015", "2016"};
		map.put("categories", categories);
		
		List<Map<String,Object>> list = new ArrayList<>();
		
		Map<String,Object> listMap1= new HashMap<>();
		Object[] listMap1Date = {0,72000, 115000,  159000, 200000, 243000, 273000};
		listMap1.put("name", "档案馆藏数量");
		listMap1.put("data", listMap1Date);
		list.add(listMap1);
		
		Map<String,Object> listMap2= new HashMap<>();
		Object[] listMap2Date = {150000,  150000, 150000, 300000, 300000, 300000, 300000};
		listMap2.put("name", "档案馆藏容量");
		listMap2.put("data", listMap2Date);
		list.add(listMap2);
		
		map.put("data", list);
		return map;
	}

	@Override
	public Map<String, Object> getChartsIncrementData() {
		Map<String, Object> map = new HashMap<>();
		
		String[] categories={"2010","2011", "2012", "2013","2014", "2015", "2016"};
		map.put("categories", categories);
		
		List<Map<String,Object>> list = new ArrayList<>();
		
		Map<String,Object> listMap1= new HashMap<>();
		Object[] listMap1Date = {0, 72000, 43000, 44000, 41000, 43000, 3000};
		listMap1.put("name", "新增档案数量");
		listMap1.put("data", listMap1Date);
		list.add(listMap1);
		
		map.put("data", list);
		return map;
	}

	@Override
	public Map<String, Object> getChartsStorageData() {
		Map<String, Object> map = new HashMap<>();
		
		String[] categories={"2011", "2012", "2013","2014", "2015", "2016"};
		map.put("categories", categories);
		
		List<Map<String,Object>> list = new ArrayList<>();
		
		Map<String,Object> listMap1= new HashMap<>();
		Object[] listMap1Date = {8100, 7892, 6292, 5142, 6182, 5212};
		listMap1.put("name", "组织部");
		listMap1.put("data", listMap1Date);
		list.add(listMap1);
		
		Map<String,Object> listMap2= new HashMap<>();
		Object[] listMap2Date = {9122, 7928, 8252, 7112, 6417, 5221};
		listMap2.put("name", "人社厅");
		listMap2.put("data", listMap2Date);
		list.add(listMap2);
		
		Map<String,Object> listMap3= new HashMap<>();
		Object[] listMap3Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap3.put("name", "人社厅");
		listMap3.put("data", listMap3Date);
		list.add(listMap3);
		Map<String,Object> listMap4= new HashMap<>();
		Object[] listMap4Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap3.put("name", "人社厅");
		listMap3.put("data", listMap3Date);
		list.add(listMap3);
		Map<String,Object> listMap5= new HashMap<>();
		Object[] listMap5Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap5.put("name", "人社厅");
		listMap5.put("data", listMap5Date);
		list.add(listMap5);
		Map<String,Object> listMap6= new HashMap<>();
		Object[] listMap6Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap6.put("name", "人社厅");
		listMap6.put("data", listMap6Date);
		list.add(listMap6);
		Map<String,Object> listMap7= new HashMap<>();
		Object[] listMap7Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap7.put("name", "人社厅");
		listMap7.put("data", listMap7Date);
		list.add(listMap6);
		Map<String,Object> listMap8= new HashMap<>();
		Object[] listMap8Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap8.put("name", "人社厅");
		listMap8.put("data", listMap8Date);
		list.add(listMap8);
		Map<String,Object> listMap9= new HashMap<>();
		Object[] listMap9Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap9.put("name", "人社厅");
		listMap9.put("data", listMap9Date);
		list.add(listMap5);
		Map<String,Object> listMap10= new HashMap<>();
		Object[] listMap10Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap10.put("name", "人社厅");
		listMap10.put("data", listMap10Date);
		list.add(listMap10);
		
		
		Map<String,Object> listMap11= new HashMap<>();
		Object[] listMap11Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap11.put("name", "人社厅");
		listMap11.put("data", listMap11Date);
		list.add(listMap11);
		Map<String,Object> listMap12= new HashMap<>();
		Object[] listMap12Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap12.put("name", "人社厅");
		listMap12.put("data", listMap12Date);
		list.add(listMap12);
		Map<String,Object> listMap13= new HashMap<>();
		Object[] listMap13Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap13.put("name", "人社厅");
		listMap13.put("data", listMap13Date);
		list.add(listMap13);
		Map<String,Object> listMap14= new HashMap<>();
		Object[] listMap14Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap14.put("name", "人社厅");
		listMap14.put("data", listMap14Date);
		list.add(listMap14);
		Map<String,Object> listMap15= new HashMap<>();
		Object[] listMap15Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap15.put("name", "人社厅");
		listMap15.put("data", listMap15Date);
		list.add(listMap10);
		Map<String,Object> listMap16= new HashMap<>();
		Object[] listMap16Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap16.put("name", "人社厅");
		listMap16.put("data", listMap16Date);
		list.add(listMap16);
		Map<String,Object> listMap17= new HashMap<>();
		Object[] listMap17Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap17.put("name", "人社厅");
		listMap17.put("data", listMap17Date);
		list.add(listMap17);
		Map<String,Object> listMap19= new HashMap<>();
		Object[] listMap19Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap19.put("name", "人社厅");
		listMap19.put("data", listMap19Date);
		list.add(listMap19);
		Map<String,Object> listMap20= new HashMap<>();
		Object[] listMap20Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap20.put("name", "人社厅");
		listMap20.put("data", listMap5Date);
		list.add(listMap20);
		Map<String,Object> listMap21= new HashMap<>();
		Object[] listMap21Date = {7410, 6311, 5623, 5738, 6163, 6277};
		listMap21.put("name", "人社厅");
		listMap21.put("data", listMap21Date);
		list.add(listMap21);
		map.put("data", list);
		return map;
	}
}

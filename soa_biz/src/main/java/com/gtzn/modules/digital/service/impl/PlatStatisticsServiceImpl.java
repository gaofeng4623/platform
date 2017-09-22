package com.gtzn.modules.digital.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.service.CrudService;
import com.gtzn.modules.digital.dao.PlatStatisticsDao;
import com.gtzn.modules.digital.entity.Dictionaries;
import com.gtzn.modules.digital.entity.PlatStatistics;
import com.gtzn.modules.digital.service.PlatStatisticsService;
import com.gtzn.modules.sys.utils.DictUtils;


@Service("platStatistics")
@Transactional(readOnly = false)
public class PlatStatisticsServiceImpl extends CrudService<PlatStatisticsDao, PlatStatistics>  implements PlatStatisticsService{
	 @Resource
	  private PlatStatisticsDao statisticsDao;

	@Override
	public Map<String, Object> queryStatisticsList() {
		// TODO Auto-generated method stub
		List<PlatStatistics>plat= statisticsDao.queryNdGroup();
		Map<String, Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		List list3 = new ArrayList();
		List list4 = new ArrayList();
		Map<String,Object> listMap1= new HashMap<>();
		Map<String,Object> listMap2= new HashMap<>();
		Map<String,Object> listMap3= new HashMap<>();
		int zlCount=0;
		int stCount=0;
		int dzCount=0;
		int zhyn=0;
		if(plat!=null){
			if(plat.size()>5){
				for(int i=plat.size()-5;i<plat.size();i++){
					List<PlatStatistics> nd= statisticsDao.queryNdCount(Integer.parseInt(plat.get(i).getYear()));
					list4.add(plat.get(i).getYear());
					for(PlatStatistics p:nd){
						list2.add(p.getEntityNoCount());
						list3.add(p.getElectronicsNoCount());
						list1.add(p.getEntityNoCount()+p.getElectronicsNoCount());
						zlCount+=p.getEntityNoCount()+p.getElectronicsNoCount();
						stCount+=p.getEntityNoCount();
						dzCount+=p.getElectronicsNoCount();
						if(i==plat.size()-1){
							 zhyn=p.getEntityNoCount()+p.getElectronicsNoCount();
						}
					}
				
				}	
			}else{
				for(int i=0;i<plat.size();i++){
					List<PlatStatistics> nd= statisticsDao.queryNdCount(Integer.parseInt(plat.get(i).getYear()));
					list4.add(plat.get(i).getYear());
					for(PlatStatistics p:nd){
						list2.add(p.getEntityNoCount());
						list3.add(p.getElectronicsNoCount());
						list1.add(p.getEntityNoCount()+p.getElectronicsNoCount());
						zlCount+=p.getEntityNoCount()+p.getElectronicsNoCount();
						stCount+=p.getEntityNoCount();
						dzCount+=p.getElectronicsNoCount();
					}
				}	
			}
			
		}
		for(int y=1;y<=6;y++){
			int nd=5*y;
			int year=2017;
			list4.add(2017+nd);
		}
		
		listMap3.put("name", "档案馆藏数量总数");
		listMap3.put("data", list1);
		list.add(listMap3);
		/*listMap1.put("name", "档案馆藏实体数量");
		listMap1.put("data", list2);
		list.add(listMap1);
		listMap2.put("name", "档案馆电子数量");
		listMap2.put("data", list3);
		list.add(listMap2);*/
		map.put("categories", list4);
		map.put("zl", list1);
		map.put("zlCount", zlCount);
		map.put("st", list2);
		map.put("stCount", stCount);
		map.put("dz", list3);
		map.put("dzCount", dzCount);
		map.put("zhyn", zhyn);
		String libraryCount=DictUtils.getDictValue("库房总容量","libraryCount","");
		/*map.put("zlyj", list1);
		map.put("dz", list1);*/
		map.put("libraryCount", libraryCount);
		return map;
	}

	@Override
	public PlatStatistics selectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateSt(PlatStatistics dic) {
		// TODO Auto-generated method stub
		return statisticsDao.updateSt(dic);
	}

	@Override
	public int updateList() {
		// TODO Auto-generated method stub
		 return statisticsDao.updateList();
	}

	@Override
	public PlatStatistics queryNd(Map m) {
		// TODO Auto-generated method stub
		return statisticsDao.queryNd(m);
	}

	@Override
	public List<PlatStatistics> queryNdList(Map m) {
		// TODO Auto-generated method stub
		return statisticsDao.queryNdList(m);
	}

	@Override
	public Map<String, Object> getChartsIncrementData() {
		List<PlatStatistics>plat= statisticsDao.queryNdGroup();
		Map<String, Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		List list2 = new ArrayList();
		List list3 = new ArrayList();
		List list4 = new ArrayList();
		Map<String,Object> listMap1= new HashMap<>();
		Map<String,Object> listMap2= new HashMap<>();
		if(plat!=null){
			if(plat.size()>5){
				for(int i=plat.size()-5;i<plat.size();i++){
					list4.add(plat.get(i).getYear());
					list2.add(plat.get(i).getEntityNoCount());
					list3.add(plat.get(i).getElectronicsNoCount());
				}	
			}else{
				for(int i=0;i<plat.size()-1;i++){
					list4.add(plat.get(i).getYear());
					list2.add(plat.get(i).getEntityNoCount());
					list3.add(plat.get(i).getElectronicsNoCount());
					
					
				}	
			}
			
		}
		
		listMap1.put("name", "档案馆藏实体数量");
		listMap1.put("data", list2);
		list.add(listMap1);
		listMap2.put("name", "档案馆电子数量");
		listMap2.put("data", list3);
		list.add(listMap2);
		map.put("categories", list4);
		map.put("data", list);
		map.put("list2", list2);
		map.put("list3", list3);
		return map;
	}

	
}
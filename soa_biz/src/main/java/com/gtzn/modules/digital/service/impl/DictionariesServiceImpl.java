package com.gtzn.modules.digital.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.service.CrudService;
import com.gtzn.modules.digital.dao.DictionariesDao;
import com.gtzn.modules.digital.entity.Dictionaries;
import com.gtzn.modules.digital.service.DictionariesService;


@Service("dictionaries")
@Transactional(readOnly = false)
public class DictionariesServiceImpl extends CrudService<DictionariesDao, Dictionaries>  implements DictionariesService{
	  @Resource
	   private DictionariesDao dictionariesDao;
	@Override
	public Map<String, Object> queryDictionariesList() {
		List<Dictionaries>dic= dictionariesDao.queryDictionariesList();
		Map<String, Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		List list2 = new ArrayList();
		List list3 = new ArrayList();
		List list4 = new ArrayList();
		Map<String,Object> listMap1= new HashMap<>();
		Map<String,Object> listMap2= new HashMap<>();
		int count=0;
		if(dic!=null){
			for(int i=0;i<dic.size();i++){
				if(dic.get(i).getEntitiesNo()!=0||dic.get(i).getElectronicsNo()!=0){
					list4.add(dic.get(i).getTitle());
					list2.add(dic.get(i).getEntitiesNo());
					list3.add(dic.get(i).getElectronicsNo());
					count+=dic.get(i).getEntitiesNo()+dic.get(i).getElectronicsNo();
				}
				
			}
		}
		listMap1.put("name", "电子");
		listMap1.put("data", list3);
		list.add(listMap1);
		listMap2.put("name", "实物");
		listMap2.put("data", list2);
		list.add(listMap2);
		map.put("categories", list4);
		map.put("data", list);
		map.put("list3", list3);
		map.put("list2", list2);
		map.put("count", count);
		return map;
	}

	@Override
	public void save(Dictionaries platInformation) {
		// TODO Auto-generated method stub
		super.save(platInformation);
	}

	@Override
	public Dictionaries selectByName(String name) {
		// TODO Auto-generated method stub
		return dictionariesDao.selectByName(name);
	}

	@Override
	public int updateDic(Dictionaries dic) {
		// TODO Auto-generated method stub
		return dictionariesDao.updateDic(dic);
	}

	@Override
	public int updateList() {
		// TODO Auto-generated method stub
		return dictionariesDao.updateList();
	}

}
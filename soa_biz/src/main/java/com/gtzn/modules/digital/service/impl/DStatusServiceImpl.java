package com.gtzn.modules.digital.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.service.CrudService;
import com.gtzn.modules.digital.dao.DStatusDao;
import com.gtzn.modules.digital.dao.DictionariesDao;
import com.gtzn.modules.digital.dao.YArchivestoreDao;
import com.gtzn.modules.digital.entity.DStatus;
import com.gtzn.modules.digital.entity.Dictionaries;
import com.gtzn.modules.digital.service.DStatusService;
import com.gtzn.modules.digital.service.DictionariesService;


@Service("dstatus")
@Transactional(readOnly = false)
public class DStatusServiceImpl extends CrudService<DStatusDao, DStatus>  implements DStatusService{
	@Autowired
	private DStatusDao dStatusDao;
	@Override
	public void save( DStatus  dStatus) {
		// TODO Auto-generated method stub
		super.save(dStatus);
	}

	@Override
	public DStatus selectByName(String name) {
		// TODO Auto-generated method stub
		return dStatusDao.selectByName(name);
	}

	@Override
	public void updateDstatus(DStatus dStatus) {
		// TODO Auto-generated method stub
		dStatusDao.updateDstatus(dStatus);
	}
	@Override
	public void updateAll() {
		// TODO Auto-generated method stub
		dStatusDao.updateAll();
	}

	@Override
	public List<DStatus> listDs() {
		// TODO Auto-generated method stub
		return dStatusDao.listDs();
	}

	@Override
	public Map<String, Object> getCapacityData() {
		// TODO Auto-generated method stub
		List<DStatus>dic= dStatusDao.listDs();
		Map<String, Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		List list2 = new ArrayList();
		List list3 = new ArrayList();
		List list4 = new ArrayList();
		Map<String,Object> listMap1= new HashMap<>();
		Map<String,Object> listMap2= new HashMap<>();
		if(dic!=null){
			for(int i=0;i<dic.size();i++){
				list4.add(dic.get(i).getUnitName());
				list2.add(dic.get(i).getInLibrary());
				list3.add(dic.get(i).getIssue());
			}
		}
		
		map.put("categories", list4);
		map.put("inLibrary", list2);
		map.put("issue", list3);
		return map;
	}
}
package com.gtzn.modules.digital.service.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.digital.dao.PlatUnitDao;
import com.gtzn.modules.digital.entity.PlatUnit;
import com.gtzn.modules.digital.service.PlatUnitService;


@Service("platUnit")
@Transactional(readOnly = false)
public class PlatUnitServiceImpl extends CrudService<PlatUnitDao, PlatUnit>  implements PlatUnitService{
	 @Resource
	  private PlatUnitDao platUnitDao;
	@Override
	public int updateList() {
		// TODO Auto-generated method stub
		return platUnitDao.updateList();
	}
	@Override
	public void save(PlatUnit platUnit) {
		// TODO Auto-generated method stub
		super.save(platUnit);
	}
	@Override
	public PlatUnit selectByName(Map m) {
		// TODO Auto-generated method stub
		return platUnitDao.selectByName(m);
	}
	@Override
	public int updateSt(PlatUnit plat) {
		// TODO Auto-generated method stub
		return platUnitDao.updateSt(plat);
	}
	@Override
	public List<PlatUnit> platList() {
		// TODO Auto-generated method stub
		return platUnitDao.platList();
	}
	@Override
	public List<PlatUnit> platListCount(String nd) {
		// TODO Auto-generated method stub
		return platUnitDao.platListCount(nd);
	}
	@Override
	public Map<String, Object> getIncrementData() {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		
		 List<PlatUnit>listunit=platUnitDao.platListCount(df.format(new Date()));
			List<Map<String,Object>> list = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			List list2 = new ArrayList();
			//List list3 = new ArrayList();
			List list4 = new ArrayList();
			Map<String,Object> listMap1= new HashMap<>();
			Map<String,Object> listMap2= new HashMap<>();
			Map<String,Object> listMap= new HashMap<>();
			if(listunit!=null){
				for(int i=0;i<listunit.size();i++){
					if(listunit.get(i).getCount()!=0){
						list2.add(listunit.get(i).getUnitName());
						list4.add(listunit.get(i).getCount());	
					}
				
					
					
				}
			}
			listMap2.put("name", "今年入库量");
			listMap2.put("data", list4);
			list.add(listMap2);
			map.put("categories", list2);
			map.put("data", list);
			map.put("list4", list4);
			return map;
	}
	
	
}
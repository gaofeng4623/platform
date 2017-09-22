package com.gtzn.modules.digital.service.impl;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.digital.dao.DStatusDao;
import com.gtzn.modules.digital.dao.DictionariesDao;
import com.gtzn.modules.digital.dao.RetentionDao;
import com.gtzn.modules.digital.dao.YArchivestoreDao;
import com.gtzn.modules.digital.entity.DStatus;
import com.gtzn.modules.digital.entity.Dictionaries;
import com.gtzn.modules.digital.entity.Retention;
import com.gtzn.modules.digital.service.DStatusService;
import com.gtzn.modules.digital.service.DictionariesService;
import com.gtzn.modules.digital.service.RetentionService;
import com.gtzn.modules.sys.entity.Dict;


@Service("retention")
@Transactional(readOnly = false)
public class RetentionServiceImpl extends CrudService<RetentionDao, Retention>  implements RetentionService{
	@Autowired
	private RetentionDao retentionDao;
	@Override
	public void updateAll(Integer type) {
		// TODO Auto-generated method stub
		retentionDao.updateAll(type);
	}
	@Override
	public Retention queryName(Map m) {
		// TODO Auto-generated method stub
		return retentionDao.queryName(m);
	}
	@Override
	public void updateName(Retention record) {
		// TODO Auto-generated method stub
		retentionDao.updateName(record);
	}
	@Override
	public void save(Retention record) {
		// TODO Auto-generated method stub
		super.save(record);
	}
	@Override
	public List<Map<String, Object>> getStoragePeriod(String type) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("type",type);
		Map<String, String> useMap = Maps.newHashMap();
		double total = 0;
		 List<Retention> retebList = retentionDao.getStoragePeriod(map);
		 List<Map<String, Object>> lists = Lists.newArrayList();
		for (Retention list: retebList) {
			total +=list.getCount();
		}
		// 创建一个数值格式化对象  
        NumberFormat numberFormat = NumberFormat.getInstance();  
        // 设置精确到小数点后2位  
        numberFormat.setMaximumFractionDigits(2); 
		for (Retention list2: retebList) {
			Map<String, Object> m = Maps.newHashMap();
			m.put("label", list2.getRetention());
			m.put("value",  Double.parseDouble(numberFormat.format(list2.getCount()/total)));
			lists.add(m);
		}
		return lists;
	}
}
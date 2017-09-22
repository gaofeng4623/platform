/**
 * 
 */
package com.gtzn.modules.digital.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.annotation.Source;
import com.gtzn.common.persistence.Page;
import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.base.entity.Constant;
import com.gtzn.modules.digital.dao.ConnoisseurDao;
import com.gtzn.modules.digital.entity.Connoisseur;
import com.gtzn.modules.digital.service.ConnoisseurService;
import com.gtzn.modules.sys.entity.Office;
import com.gtzn.modules.sys.entity.User;
/**
 * costom Service
 * @author gt
 * @version 
 */
@Source("digital")
@Service("ConnoisseurService")
@Transactional(readOnly = false)
public class  ConnoisseurServiceImpl  implements ConnoisseurService {
	
	public static final String CACHE_DICT_MAP = "dictMap";
	@Autowired
	private  ConnoisseurDao  connoisseurDao;
	
	@Override
	public Pager< Connoisseur> findPage(Pager< Connoisseur> page,  Connoisseur entity) {
		// TODO Auto-generated method stub
		// 设置分页参数
		entity.setPager(page);
		// 执行分页查询
		List<Connoisseur> csList1 = new ArrayList<Connoisseur> ();
		
		List<Connoisseur> csList = connoisseurDao.getPage(page.getCurrentRows(),page.getCurrentNum());
		for (int i = 0;i<csList.size();i++) {
			Connoisseur cs = csList.get(i);
			cs.setProcessname(Constant.workflow_type.get(cs.getWorkflowtype()));
			csList1.add(cs);
		}
		
		page.setList(csList1);
		page.setRecords(connoisseurDao.findCount(entity));
		return page;
	}
	
	@Override
	public List< Connoisseur> findAllList( Connoisseur entity) {
		// TODO Auto-generated method stub
		return connoisseurDao.findList(entity);
	}
}

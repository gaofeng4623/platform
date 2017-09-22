/**
 *
 */
package com.gtzn.modules.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.base.dao.DoorWarningDao;
import com.gtzn.modules.base.entity.DoorWarning;
import com.gtzn.modules.base.service.DoorWarningService;
import com.gtzn.modules.sys.entity.User;

/**
 * 门禁报警记录Service
 * @author gtzn_Yang
 * @version 2016-12-19
 */
@Service("doorWarning")
@Transactional(readOnly = true)
public class DoorWarningServiceImpl extends CrudService<DoorWarningDao, DoorWarning> implements DoorWarningService{

	@Autowired
	private DoorWarningDao doorWarningDao;
	
	public DoorWarning get(String id) {
		return super.get(id);
	}
	
	public List<DoorWarning> findList(DoorWarning doorWarning) {
		return super.findList(doorWarning);
	}
	
	public Pager<DoorWarning> findPage(Pager<DoorWarning> pager, DoorWarning doorWarning, User user) {
//		return super.findPage(pager, doorWarning);
		return super.findPage(pager, doorWarning, user, "o", "");
	}
	
	@Transactional(readOnly = false)
	@Override
	public void handleWarning(String[] ids, DoorWarning doorWarning) {
		doorWarningDao.handleWarning(ids, doorWarning);
	}
}
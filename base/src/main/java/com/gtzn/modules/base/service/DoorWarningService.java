/**
 *
 */
package com.gtzn.modules.base.service;

import java.util.List;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.base.entity.DoorWarning;
import com.gtzn.modules.sys.entity.User;

/**
 * 门禁报警记录Service
 * @author gtzn_Yang
 * @version 2016-12-19
 */

public interface DoorWarningService {

	public DoorWarning get(String id);
	
	public List<DoorWarning> findList(DoorWarning doorWarning);
	
	public Pager<DoorWarning> findPage(Pager<DoorWarning> pager, DoorWarning doorWarning, User user);
	
	public void handleWarning(String[] ids, DoorWarning doorWarning);
	
}
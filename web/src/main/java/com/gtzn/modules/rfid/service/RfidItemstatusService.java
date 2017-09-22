/**
 *
 */
package com.gtzn.modules.rfid.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.rfid.entity.RfidItemstatus;

/**
 * 标签写入Service
 * @author gtzn
 * @version 2017-04-06
 */

public interface RfidItemstatusService {

	public List<RfidItemstatus> findList(RfidItemstatus rfidItemstatus);
	
	public Pager<RfidItemstatus> findPage(Pager<RfidItemstatus> pager, RfidItemstatus rfidItemstatus);

	public RfidItemstatus selectRfidItemstatusInfo(String key);

	public RfidItemstatus selectRfidItemstatusInfo(RfidItemstatus rfidItemstatus);
	
	public void save(RfidItemstatus rfidItemstatus);

	public void insertRfidItemstatusSelective(RfidItemstatus rfidItemstatus);

	public void updateRfidItemstatusSelective(RfidItemstatus rfidItemstatus);
	
	public void deleteRfidItemstatusInfo(RfidItemstatus rfidItemstatus);

	public void deleteRfidItemstatusInfo(String id);

	public int batchDelete(String[] idList);

	public int batchInsert(List<RfidItemstatus> list);

	public int executeUpdate(String sql);

	public void executeBatchUpdate(List<String> arraySql);
	
	public List<RfidItemstatus> findAllList(RfidItemstatus rfidItemstatus);
	
}
/**
 *
 */
package com.gtzn.modules.rfid.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.rfid.entity.RfidItemstatus;
import com.gtzn.modules.rfid.dao.RfidItemstatusDao;
import com.gtzn.modules.rfid.service.RfidItemstatusService; 

/**
 * 标签写入Service
 * @author gtzn
 * @version 2017-04-06
 */
@Service("rfidItemstatus")
@Transactional(readOnly = true)
public class RfidItemstatusServiceImpl extends CrudService<RfidItemstatusDao, RfidItemstatus> implements RfidItemstatusService{

	public List<RfidItemstatus> findList(RfidItemstatus rfidItemstatus) {
		return super.findList(rfidItemstatus);
	}
	
	public Pager<RfidItemstatus> findPage(Pager<RfidItemstatus> pager, RfidItemstatus rfidItemstatus) {
		return super.findPage(pager, rfidItemstatus);
	}

	public RfidItemstatus selectRfidItemstatusInfo(String id) {
		return super.get(id);
	}

	public RfidItemstatus selectRfidItemstatusInfo(RfidItemstatus rfidItemstatus) {
		return super.get(rfidItemstatus);
	}

	@Transactional(readOnly = false)
	public void save(RfidItemstatus rfidItemstatus) {
		super.save(rfidItemstatus);
	}

	@Transactional(readOnly = false)
	public void insertRfidItemstatusSelective(RfidItemstatus rfidItemstatus) {
		super.insertSelective(rfidItemstatus);
	}

	@Transactional(readOnly = false)
	public void updateRfidItemstatusSelective(RfidItemstatus rfidItemstatus) {
		super.updateSelective(rfidItemstatus);
	}

	@Transactional(readOnly = false)
	public void deleteRfidItemstatusInfo(RfidItemstatus rfidItemstatus) {
		super.delete(rfidItemstatus);
	}

	@Transactional(readOnly = false)
	public void deleteRfidItemstatusInfo(String id) {
		super.delete(id);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String[] idList) {
		return super.batchDelete(idList);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<RfidItemstatus> list) {
		return super.batchInsert(list);
	}

	@Transactional(readOnly = false)
	public int executeUpdate(String sql) {
		return super.executeUpdate(sql);
	}

	@Transactional(readOnly = false)
	public void executeBatchUpdate(List<String> arraySql) {
		for (String sql : arraySql) {
			super.executeUpdate(sql);
		}
	}

	public List<RfidItemstatus> findAllList(RfidItemstatus rfidItemstatus){
		return super.findAllList(rfidItemstatus);
	}
}
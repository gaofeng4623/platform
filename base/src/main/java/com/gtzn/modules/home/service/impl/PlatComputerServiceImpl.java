/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.security.Cryptos;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.dao.PlatComputerDao;
import com.gtzn.modules.home.entity.PlatComputer;
import com.gtzn.modules.home.service.PlatComputerService; 

/**
 * 服务器管理Service
 * @author wzx
 * @version 2017-05-08
 */
@Service("platComputer")
@Transactional(readOnly = true)
public class PlatComputerServiceImpl extends CrudService<PlatComputerDao, PlatComputer> implements PlatComputerService{

	public List<PlatComputer> findList(PlatComputer platComputer) {
		return super.findList(platComputer);
	}
	
	public Pager<PlatComputer> findPage(Pager<PlatComputer> pager, PlatComputer platComputer) {
		return super.findPage(pager, platComputer);
	}

	public PlatComputer selectPlatComputerInfo(String id) {
		return super.get(id);
	}

	public PlatComputer selectPlatComputerInfo(PlatComputer platComputer) {
		return super.get(platComputer);
	}

	@Transactional(readOnly = false)
	public void save(PlatComputer platComputer) {
		platComputer.setPassword(Cryptos.aesEncrypt(platComputer.getPassword()));
		super.save(platComputer);
	}

	@Transactional(readOnly = false)
	public void insertPlatComputerSelective(PlatComputer platComputer) {
		super.insertSelective(platComputer);
	}

	@Transactional(readOnly = false)
	public void updatePlatComputerSelective(PlatComputer platComputer) {
		super.updateSelective(platComputer);
	}

	@Transactional(readOnly = false)
	public void deletePlatComputerInfo(PlatComputer platComputer) {
		super.delete(platComputer);
	}

	@Transactional(readOnly = false)
	public void deletePlatComputerInfo(String id) {
		super.delete(id);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String[] idList) {
		return super.batchDelete(idList);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<PlatComputer> list) {
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

	public List<PlatComputer> findAllList(PlatComputer platComputer){
		return super.findAllList(platComputer);
	}
}
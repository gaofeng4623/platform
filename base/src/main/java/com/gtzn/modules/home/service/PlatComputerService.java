/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.List;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.home.entity.PlatComputer;

/**
 * 服务器管理Service
 * @author wzx
 * @version 2017-05-08
 */

public interface PlatComputerService {

	public List<PlatComputer> findList(PlatComputer platComputer);
	
	public Pager<PlatComputer> findPage(Pager<PlatComputer> pager, PlatComputer platComputer);

	public PlatComputer selectPlatComputerInfo(String key);

	public PlatComputer selectPlatComputerInfo(PlatComputer platComputer);
	
	public void save(PlatComputer platComputer);

	public void insertPlatComputerSelective(PlatComputer platComputer);

	public void updatePlatComputerSelective(PlatComputer platComputer);
	
	public void deletePlatComputerInfo(PlatComputer platComputer);

	public void deletePlatComputerInfo(String id);

	public int batchDelete(String[] idList);

	public int batchInsert(List<PlatComputer> list);

	public int executeUpdate(String sql);

	public void executeBatchUpdate(List<String> arraySql);
	
	public List<PlatComputer> findAllList(PlatComputer platComputer);
	
}
/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.entity.PlatShowroom;

/**
 * 展厅管理Service
 * @author wzx
 * @version 2017-05-11
 */

public interface PlatShowroomService {

	public List<PlatShowroom> findList(PlatShowroom platShowroom);
	
	public Pager<PlatShowroom> findPage(Pager<PlatShowroom> pager, PlatShowroom platShowroom);

	public PlatShowroom selectPlatShowroomInfo(String key);

	public PlatShowroom selectPlatShowroomInfo(PlatShowroom platShowroom);
	
	public void save(PlatShowroom platShowroom);

	public void insertPlatShowroomSelective(PlatShowroom platShowroom);

	public void updatePlatShowroomSelective(PlatShowroom platShowroom);
	
	public void deletePlatShowroomInfo(PlatShowroom platShowroom);

	public void deletePlatShowroomInfo(String id);

	public int batchDelete(String[] idList);

	public int batchInsert(List<PlatShowroom> list);

	public int executeUpdate(String sql);

	public void executeBatchUpdate(List<String> arraySql);
	
	public List<PlatShowroom> findAllList(PlatShowroom platShowroom);
	
}
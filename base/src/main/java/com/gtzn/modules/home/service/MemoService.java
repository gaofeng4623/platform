/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.List;

import com.gtzn.modules.home.entity.Memo;

/**
 * 便笺Service 
 */

public interface MemoService {

	public List<Memo> findList(Memo entity);
	
	public void save(Memo entity);
	
	public void insertMem(Memo entity);
	
	public void deleteMem(String id);
	
	public List<Memo> findAllList(Memo entity);
}
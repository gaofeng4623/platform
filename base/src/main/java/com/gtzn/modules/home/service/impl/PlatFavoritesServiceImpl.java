/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.entity.PlatFavorites;
import com.gtzn.modules.home.dao.PlatFavoritesDao;
import com.gtzn.modules.home.service.PlatFavoritesService; 

/**
 * 收藏夹Service
 * @author wzx
 * @version 2017-04-20
 */
@Service("platFavorites")
@Transactional(readOnly = true)
public class PlatFavoritesServiceImpl extends CrudService<PlatFavoritesDao, PlatFavorites> implements PlatFavoritesService{

	public List<PlatFavorites> findList(PlatFavorites platFavorites) {
		return super.findList(platFavorites);
	}
	
	public Pager<PlatFavorites> findPage(Pager<PlatFavorites> pager, PlatFavorites platFavorites) {
		return super.findPage(pager, platFavorites);
	}

	public PlatFavorites selectPlatFavoritesInfo(String id) {
		return super.get(id);
	}

	public PlatFavorites selectPlatFavoritesInfo(PlatFavorites platFavorites) {
		return super.get(platFavorites);
	}

	@Transactional(readOnly = false)
	public void save(PlatFavorites platFavorites) {
		super.save(platFavorites);
	}

	@Transactional(readOnly = false)
	public void insertPlatFavoritesSelective(PlatFavorites platFavorites) {
		super.insertSelective(platFavorites);
	}

	@Transactional(readOnly = false)
	public void updatePlatFavoritesSelective(PlatFavorites platFavorites) {
		super.updateSelective(platFavorites);
	}

	@Transactional(readOnly = false)
	public void deletePlatFavoritesInfo(PlatFavorites platFavorites) {
		super.delete(platFavorites);
	}

	@Transactional(readOnly = false)
	public void deletePlatFavoritesInfo(String id) {
		super.delete(id);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String[] idList) {
		return super.batchDelete(idList);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<PlatFavorites> list) {
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

	public List<PlatFavorites> findAllList(PlatFavorites platFavorites){
		return super.findAllList(platFavorites);
	}
}
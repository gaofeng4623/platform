/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.entity.PlatFavorites;

/**
 * 收藏夹Service
 * @author wzx
 * @version 2017-04-20
 */

public interface PlatFavoritesService {

	public List<PlatFavorites> findList(PlatFavorites platFavorites);
	
	public Pager<PlatFavorites> findPage(Pager<PlatFavorites> pager, PlatFavorites platFavorites);

	public PlatFavorites selectPlatFavoritesInfo(String key);

	public PlatFavorites selectPlatFavoritesInfo(PlatFavorites platFavorites);
	
	public void save(PlatFavorites platFavorites);

	public void insertPlatFavoritesSelective(PlatFavorites platFavorites);

	public void updatePlatFavoritesSelective(PlatFavorites platFavorites);
	
	public void deletePlatFavoritesInfo(PlatFavorites platFavorites);

	public void deletePlatFavoritesInfo(String id);

	public int batchDelete(String[] idList);

	public int batchInsert(List<PlatFavorites> list);

	public int executeUpdate(String sql);

	public void executeBatchUpdate(List<String> arraySql);
	
	public List<PlatFavorites> findAllList(PlatFavorites platFavorites);
	
}
/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.List;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.home.entity.PlatFavoritesItem;

/**
 * 我的收藏Service
 * @author wzx
 * @version 2017-04-20
 */

public interface PlatFavoritesItemService {

	public List<PlatFavoritesItem> findList(PlatFavoritesItem platFavoritesItem);
	
	public Pager<PlatFavoritesItem> findPage(Pager<PlatFavoritesItem> pager, PlatFavoritesItem platFavoritesItem);

	public PlatFavoritesItem selectPlatFavoritesItemInfo(String key);

	public PlatFavoritesItem selectPlatFavoritesItemInfo(PlatFavoritesItem platFavoritesItem);
	
	public void save(PlatFavoritesItem platFavoritesItem);

	public void insertPlatFavoritesItemSelective(PlatFavoritesItem platFavoritesItem);

	public void updatePlatFavoritesItemSelective(PlatFavoritesItem platFavoritesItem);
	
	public void deletePlatFavoritesItemInfo(PlatFavoritesItem platFavoritesItem);

	public void deletePlatFavoritesItemInfo(String id);

	public int batchDelete(String[] idList);

	public int batchInsert(List<PlatFavoritesItem> list);

	public int executeUpdate(String sql);

	public void executeBatchUpdate(List<String> arraySql);
	
	public List<PlatFavoritesItem> findAllList(PlatFavoritesItem platFavoritesItem);
	
	public List<PlatFavoritesItem> findNoticeFromFavorites(String linkId,String userid);
	public List<PlatFavoritesItem> findInforFromFavorites(String linkId,String userid);
	public List<PlatFavoritesItem>  selectFavoritesByLindIds(String[] idList);

}
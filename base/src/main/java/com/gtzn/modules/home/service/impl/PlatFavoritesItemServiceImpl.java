/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.dao.PlatFavoritesItemDao;
import com.gtzn.modules.home.entity.PlatFavoritesItem;
import com.gtzn.modules.home.service.PlatFavoritesItemService; 

/**
 * 我的收藏Service
 * @author wzx
 * @version 2017-04-20
 */
@Service("platFavoritesItem")
@Transactional(readOnly = true)
public class PlatFavoritesItemServiceImpl extends CrudService<PlatFavoritesItemDao, PlatFavoritesItem> implements PlatFavoritesItemService{

	public List<PlatFavoritesItem> findList(PlatFavoritesItem platFavoritesItem) {
		return super.findList(platFavoritesItem);
	}
	
	public Pager<PlatFavoritesItem> findPage(Pager<PlatFavoritesItem> pager, PlatFavoritesItem platFavoritesItem) {
		return super.findPage(pager, platFavoritesItem);
	}

	public PlatFavoritesItem selectPlatFavoritesItemInfo(String id) {
		return super.get(id);
	}

	public PlatFavoritesItem selectPlatFavoritesItemInfo(PlatFavoritesItem platFavoritesItem) {
		return super.get(platFavoritesItem);
	}

	@Transactional(readOnly = false)
	public void save(PlatFavoritesItem platFavoritesItem) {
		super.save(platFavoritesItem);
	}

	@Transactional(readOnly = false)
	public void insertPlatFavoritesItemSelective(PlatFavoritesItem platFavoritesItem) {
		super.insertSelective(platFavoritesItem);
	}

	@Transactional(readOnly = false)
	public void updatePlatFavoritesItemSelective(PlatFavoritesItem platFavoritesItem) {
		super.updateSelective(platFavoritesItem);
	}

	@Transactional(readOnly = false)
	public void deletePlatFavoritesItemInfo(PlatFavoritesItem platFavoritesItem) {
		super.delete(platFavoritesItem);
	}

	@Transactional(readOnly = false)
	public void deletePlatFavoritesItemInfo(String id) {
		super.delete(id);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String[] idList) {
		return super.batchDelete(idList);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<PlatFavoritesItem> list) {
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

	public List<PlatFavoritesItem> findAllList(PlatFavoritesItem platFavoritesItem){
		return super.findAllList(platFavoritesItem);
	}

	@Override
	public List<PlatFavoritesItem> findNoticeFromFavorites(String linkId,String userid) {
		
		return dao.findNoticeFromFavorites(linkId, userid);
	}

	@Override
	public List<PlatFavoritesItem> findInforFromFavorites(String linkId,String userid) {
		
		return dao.findInforFromFavorites(linkId, userid);
	}

	@Override
	public List<PlatFavoritesItem> selectFavoritesByLindIds(String[] idList) {
		return dao.selectFavoritesByLindIds(idList);
	}
}
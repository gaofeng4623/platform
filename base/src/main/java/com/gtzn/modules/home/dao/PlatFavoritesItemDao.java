/**
 * 
 */
package com.gtzn.modules.home.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.PlatFavoritesItem;

/**
 * 我的收藏DAO接口
 * @author wzx
 * @version 2017-04-20
 */
@MyBatisDao
public interface PlatFavoritesItemDao extends CrudDao<PlatFavoritesItem> {

	public List<PlatFavoritesItem> findNoticeFromFavorites(@Param("linkId") String linkId,@Param("userid") String userid);
	public List<PlatFavoritesItem> findInforFromFavorites(@Param("linkId") String linkId,@Param("userid") String userid);
	public List<PlatFavoritesItem> selectFavoritesByLindIds(@Param("idList") String[] idList);
}
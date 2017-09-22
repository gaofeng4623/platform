/**
 * 
 */
package com.gtzn.modules.home.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.PlatFavorites;

/**
 * 收藏夹DAO接口
 * @author wzx
 * @version 2017-04-20
 */
@MyBatisDao
public interface PlatFavoritesDao extends CrudDao<PlatFavorites> {

	
}
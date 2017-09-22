/**
 * 
 */
package com.gtzn.modules.home.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.PlatCollection;

/**
 * 最新采集DAO接口
 * @author wzx
 * @version 2017-04-25
 */
@MyBatisDao
public interface PlatCollectionDao extends CrudDao<PlatCollection> {

	List<PlatCollection> findTopN( @Param(value = "topN") int topN);

	
}
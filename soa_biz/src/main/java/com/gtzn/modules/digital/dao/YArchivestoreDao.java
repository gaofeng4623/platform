/**
 * 
 */
package com.gtzn.modules.digital.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.YArchivestore;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @info 提供数字化档案馆的查询服务
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-05-03
 * @version 1.0.0
 */
@MyBatisDao
public interface YArchivestoreDao extends CrudDao<YArchivestore> {

	public YArchivestore getById(@Param("id") String id);

	public YArchivestore getByTableName(@Param("tbName") String tbName);

	public String getColumnByTableName(@Param("tbName") String tbName, @Param("clName") String clName);
	
	public List<YArchivestore> findAllListByType(@Param("storeTypes") String[] storeTypes);

	public List<YArchivestore> getStoreByIds(@Param("storelist") List<String> storelist);
}
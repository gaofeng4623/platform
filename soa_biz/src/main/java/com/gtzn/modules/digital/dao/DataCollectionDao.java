/**
 * 
 */
package com.gtzn.modules.digital.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.YDataCollection;
import java.util.List;

import org.apache.ibatis.annotations.Param;


@MyBatisDao
public interface DataCollectionDao extends CrudDao<YDataCollection> {

	public int findCount(@Param("cTableName") String cTableName);
	
	public List<YDataCollection> findTopN( @Param("topN") int topN,@Param("cTableName") String cTableName,
			@Param("aTableName") String aTableName,@Param("fTableName") String fTableName);
	
	public List<YDataCollection> get(@Param("aid") String aid,@Param("cTableName") String cTableName,@Param("aTableName") String aTableName);
	
	public List<YDataCollection> getFile(@Param("aid") String aid,@Param("fTableName") String fTableName);
	
	public List<YDataCollection> getPage(@Param("cTableName") String cTableName,@Param("aTableName") String aTableName,
			@Param("currentRows")int currentRows,@Param("currentNum")int currentNum,
			@Param("subject") String subject,@Param("genName") String genName);
	
	
}
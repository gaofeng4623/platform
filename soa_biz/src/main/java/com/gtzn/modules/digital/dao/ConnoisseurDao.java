/**
 * 
 */
package com.gtzn.modules.digital.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.Connoisseur;
import com.gtzn.modules.digital.entity.YDataCollection;

/**
 * 客户DAO接口
 * @author gt
 * @version 
 */
@MyBatisDao
public interface ConnoisseurDao extends CrudDao<Connoisseur> {
	
	public List<String> findCustomList();
	
	public List<Connoisseur> getPage(
			@Param("currentRows")int currentRows,@Param("currentNum")int currentNum);
	
}

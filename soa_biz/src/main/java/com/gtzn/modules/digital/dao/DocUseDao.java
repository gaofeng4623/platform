package com.gtzn.modules.digital.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.sys.entity.Dict;
@MyBatisDao
public interface DocUseDao {

	
	List<Dict> findDict(String type);
	
	
	int findUseCount();
	
	int findBorrowCount();
	
	List<Map<String, String>> findUseCountByMonth();
	
	
	List<Map<String, String>> findBorrowCountByMonth();
	
	List<Map<String, String>> findUseCountByMonthUserType();
	
	
	List<Map<String, String>> findBorrowCountByMonthApplyType();
	
	
	List<Map<String, String>> findUseCountByUsepurpose();
	
	List<Map<String, String>> findBorrowCountByUsepurpose();
	
	
	List<Map<String, String>> findUseUnitCount();
	
	List<Map<String, String>> findBorrowUnitCount();
	
	Map<String, Object> findUseAgeRange();
	
	
	List<Map<String, String>> findUseCountByYear();
	
	List<Map<String, String>> findBorrowCountByYear();
	
	
	
	List<Map<String, String>> findUseCountByMonthUserTypeEq(@Param("month")String month, @Param("usertype")String usertype);
	
	
	
	List<Map<String, String>> findUseCountByClassEq(@Param("month")String month, @Param("usertype")String usertype);
	
	
	List<Map<String, String>> findBorrowCountByMonthApplyTypeEq(@Param("month")String month, @Param("applytype")String applytype);
	
	List<Map<String, String>> findBorrowCountByClassEq(@Param("month")String month, @Param("applytype")String applytype);
}

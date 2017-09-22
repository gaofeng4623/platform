package com.gtzn.modules.home.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.PlatInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface PlatInformationDao extends CrudDao<PlatInformation> {
	int queryPlatInformation(@Param("title") String title);

	List<PlatInformation> queryPlatInformationList();

	PlatInformation queryPlatInFrom(@Param("id") String id);

	List<PlatInformation> loadData(@Param("m") Map m);

	int loaCount(@Param("m") Map m);

	int updatePlat(PlatInformation plat);

	void deletePlatInformation(PlatInformation platInformation);

	List<PlatInformation> whole(@Param("m") Map m);

	int wholeCount(@Param("m") Map m);

	PlatInformation wholeId(@Param("id") String id);
}
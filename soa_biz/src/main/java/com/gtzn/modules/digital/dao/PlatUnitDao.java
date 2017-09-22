package com.gtzn.modules.digital.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.PlatUnit;
@MyBatisDao
public interface PlatUnitDao extends CrudDao<PlatUnit>{
    int insert(PlatUnit record);

    int insertSelective(PlatUnit record);
    int updateList();//更新全部
	public PlatUnit selectByName(@Param("m") Map m);//根据立档单位和年度查询有就更新没有添加
	 int updateSt(PlatUnit dic);
	 public List<PlatUnit> platList();
	 public List<PlatUnit> platListCount(String nd);
}
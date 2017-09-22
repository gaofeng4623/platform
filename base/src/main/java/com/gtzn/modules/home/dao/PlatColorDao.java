package com.gtzn.modules.home.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.PlatColor;
@MyBatisDao
public interface PlatColorDao  extends CrudDao<PlatColor>{
    int deleteByPrimaryKey(Integer id);

    int insert(PlatColor record);

    int insertSelective(PlatColor record);

    PlatColor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlatColor record);

    int updateByPrimaryKey(PlatColor record);
    PlatColor selectTypeId(@Param("m") Map m);
}
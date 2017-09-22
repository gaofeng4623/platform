package com.gtzn.modules.digital.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.Retention;

@MyBatisDao
public interface RetentionDao extends CrudDao<Retention>{
    int deleteByPrimaryKey(Integer id);

    int insert(Retention record);

    int insertSelective(Retention record);

    Retention selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Retention record);

    int updateByPrimaryKey(Retention record);
    public void updateAll(Integer type);
    public Retention queryName(@Param("m") Map m);
    public void updateName(Retention record);//根据名字和类型更新
    public  List<Retention>  getStoragePeriod(@Param("m") Map m);
}
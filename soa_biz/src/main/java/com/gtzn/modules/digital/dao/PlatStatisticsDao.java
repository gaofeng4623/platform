package com.gtzn.modules.digital.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.PlatStatistics;
@MyBatisDao
public interface PlatStatisticsDao extends CrudDao<PlatStatistics>{
    int deleteByPrimaryKey(Integer id);
    int insert(PlatStatistics record);

    int insertSelective(PlatStatistics record);

    PlatStatistics selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(PlatStatistics record);
    int updateByPrimaryKey(PlatStatistics record);
    public PlatStatistics queryNd(@Param("m") Map m);//根据年度和大类查询
    int updateSt(PlatStatistics dic);
    int updateList();//更新全部
    public List<PlatStatistics> queryNdList(@Param("m") Map m);//根据年度查询一个集合
    public List<PlatStatistics> queryNdGroup();//根据年度查询一个集合
    public List<PlatStatistics> queryNdCount(Integer year);//根据年度查询一个集合
}
package com.gtzn.modules.digital.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.ArchiveClass;
import com.gtzn.modules.digital.entity.Dictionaries;
@MyBatisDao
public interface ArchiveClassDao extends CrudDao<ArchiveClass>{
    int deleteByPrimaryKey(Integer id);

    int insert(Dictionaries record);

    int insertSelective(Dictionaries record);

    Dictionaries selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dictionaries record);

    int updateByPrimaryKey(Dictionaries record);
    public List<ArchiveClass> queryArchiveClassList();
    public List<ArchiveClass> toreList(@Param("m") Map m);//得到d表
    public List<ArchiveClass> toreIdeaList(@Param("m") Map m);//查询出电子档案
    String countD(@Param("m") Map m);
    int countIdea(@Param("m") Map m);
    int countIsNull(@Param("m") Map m);
    public List<ArchiveClass> ListNd(@Param("m") Map m);//根据年度得到一个集合
     int counNd(@Param("m") Map m);//根据年度得到总数
     public List<ArchiveClass> queryUnitList();//得到立档单位
     public List<ArchiveClass> toreUnitList(@Param("m") Map m);//得到d表
     public List<ArchiveClass> toreNdList(@Param("m") Map m);//得到d表
     public List<ArchiveClass> countUnitList(@Param("m") Map m);
     int Librarycoun(@Param("m") Map m);//统计在库未在库   
     public List<ArchiveClass> dict(@Param("m") Map m);//查询字典表用在保管期限密集   
     public String bgqxCount(@Param("m") Map m);
     public String bgqxDzCount(@Param("m") Map m);
     public List<ArchiveClass> transfer();
}
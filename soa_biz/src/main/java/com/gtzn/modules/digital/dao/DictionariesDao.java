package com.gtzn.modules.digital.dao;
import java.util.List;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.Dictionaries;
@MyBatisDao
public interface DictionariesDao extends CrudDao<Dictionaries>{
    int deleteByPrimaryKey(Integer id);

    int insert(Dictionaries record);

    int insertSelective(Dictionaries record);

    Dictionaries selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dictionaries record);

    int updateByPrimaryKey(Dictionaries record);
    Dictionaries selectByName(String name);
    int updateDic(Dictionaries dic);
    List<Dictionaries> queryDictionariesList();
    /**
     * 初始化数据
     * @return
     */
    int updateList();//更新全部
}
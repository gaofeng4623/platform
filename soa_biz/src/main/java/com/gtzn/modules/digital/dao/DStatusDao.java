package com.gtzn.modules.digital.dao;

import java.util.List;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.digital.entity.DStatus;
@MyBatisDao
public interface DStatusDao extends CrudDao<DStatus>{
    int deleteByPrimaryKey(Integer id);
    
    int insert(DStatus record);

    int insertSelective(DStatus record);

    DStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DStatus record);

    int updateByPrimaryKey(DStatus record);
	public DStatus selectByName(String name);//根据大类查询
	public void updateDstatus(DStatus DStatus);
	public void updateAll();
	public List<DStatus> listDs();

}
package com.gtzn.modules.digital.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.modules.digital.entity.ArchiveClass;


public interface ArchiveClassService  {
	/**
	 * 查询所有档案大类，如出生证明，设备档案 等等
	 * @return
	 */
	List<ArchiveClass> queryArchiveClassList();
	public List<ArchiveClass> toreList(Map m);//查询出实体档案
	public List<ArchiveClass> toreIdeaList(Map m);//查询出电子档案
	int countIsNull(Map m);//判断有没有数据
	String countD(Map m);
	int countIdea(Map m);
	public List<ArchiveClass> ListNd(Map m);//根据年度得到一个集合
	 int counNd(Map m);
	 List<ArchiveClass> queryUnitList();
	 public List<ArchiveClass> toreUnitList(Map m);//根据立档单位查询出实体档案
	 public List<ArchiveClass> toreNdList(Map m);//根据年度，实体分数查询
	 public List<ArchiveClass> countUnitList(Map m);
	 int Librarycoun(Map m);
	 public List<ArchiveClass> dict(Map m);//查询字典表
	 public String bgqxCount(Map m);
	 public String bgqxDzCount(Map m);
	 public List<ArchiveClass> transfer();
}

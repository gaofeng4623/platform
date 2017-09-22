package com.gtzn.modules.digital.service.impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.gtzn.common.annotation.Source;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.digital.dao.ArchiveClassDao;
import com.gtzn.modules.digital.entity.ArchiveClass;
import com.gtzn.modules.digital.service.ArchiveClassService;

@Service("archiveClass")
@Source("digital")
public class ArchiveClassServiceImpl extends CrudService<ArchiveClassDao, ArchiveClass> implements ArchiveClassService{
	  @Resource
	   private ArchiveClassDao archiveClassDao;
	@Override
	public List<ArchiveClass> queryArchiveClassList() {
		// TODO Auto-generated method stub
		return archiveClassDao.queryArchiveClassList();
	}
	@Override
	public List<ArchiveClass> toreList(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.toreList(m);
	}
	@Override
	public String countD(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.countD(m);
	}
	@Override
	public List<ArchiveClass> toreIdeaList(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.toreIdeaList(m);
	}
	@Override
	public int countIdea(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.countIdea(m);
	}
	@Override
	public int countIsNull(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.countIsNull(m);
	}
	@Override
	public List<ArchiveClass> ListNd(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.ListNd(m);
	}
	@Override
	public int counNd(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.counNd(m);
	}
	@Override
	public List<ArchiveClass> queryUnitList() {
		// TODO Auto-generated method stub
		return archiveClassDao.queryUnitList();
	}
	@Override
	public List<ArchiveClass> toreUnitList(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.toreUnitList(m);
	}
	@Override
	public List<ArchiveClass> toreNdList(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.toreNdList(m);
	}
	@Override
	public List<ArchiveClass> countUnitList(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.countUnitList(m);
	}
	@Override
	public int Librarycoun(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.Librarycoun(m);
	}
	@Override
	public List<ArchiveClass> dict(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.dict(m);
	}
	@Override
	public String bgqxCount(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.bgqxCount(m);
	}
	@Override
	public String bgqxDzCount(Map m) {
		// TODO Auto-generated method stub
		return archiveClassDao.bgqxDzCount(m);
	}
	@Override
	public List<ArchiveClass> transfer() {
		// TODO Auto-generated method stub
		return archiveClassDao.transfer();
	}

}
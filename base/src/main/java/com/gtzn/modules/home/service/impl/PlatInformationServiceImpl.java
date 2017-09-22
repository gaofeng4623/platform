/**
 *
 */
package com.gtzn.modules.home.service.impl;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.dao.PlatInformationDao;
import com.gtzn.modules.home.entity.PlatInformation;
import com.gtzn.modules.home.service.PlatInformationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("platInformation")
@Transactional(readOnly = false)
public class PlatInformationServiceImpl extends CrudService<PlatInformationDao, PlatInformation> implements
		PlatInformationService {
	@Resource
	private PlatInformationDao platInformationDao;

	@Transactional(readOnly = false)
	public void save(PlatInformation platInformation) {
		super.save(platInformation);
	}

	@Override
	public int queryPlatInformation(String title) {
		// TODO Auto-generated method stub
		int count = platInformationDao.queryPlatInformation(title);
		return count;
	}

	@Override
	public List<PlatInformation> queryPlatInformationList() {
		// TODO Auto-generated method stub
		return platInformationDao.queryPlatInformationList();
	}

	@Override
	public Pager<PlatInformation> findPage(Pager<PlatInformation> pager, PlatInformation platInformation) {
		// TODO Auto-generated method stub
		return super.findPage(pager, platInformation);
	}

	@Override
	public PlatInformation queryPlatInFrom(String id) {
		// TODO Auto-generated method stub
		return platInformationDao.queryPlatInFrom(id);
	}

	@Override
	public List<PlatInformation> loadData(Integer page, Integer rows, String titleCont) {
		// TODO Auto-generated method stub
		int start = (page - 1) * rows;
		Map m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("titleCont", titleCont);
		return platInformationDao.loadData(m);
	}

	@Override
	public int loadCount(String titleCont) {
		// TODO Auto-generated method stub
		Map m = new HashMap();
		m.put("titleCont", titleCont);
		return platInformationDao.loaCount(m);
	}

	@Override
	public int updatePlat(PlatInformation plat) {
		// TODO Auto-generated method stub
		return platInformationDao.updatePlat(plat);
	}

	@Override
	public void deletePlatInformation(PlatInformation platInformation) {
		// TODO Auto-generated method stub
		platInformationDao.deletePlatInformation(platInformation);
	}

	/**
	 * 全文搜索,目前是like匹配
	 *
	 * @param page      第几页
	 * @param rows      每页条数
	 * @param type      搜索类型 0:行业 1:公告 2:知识库 3:行业&公告 4: 行业&知识库 5:公告&知识库 6:行业&公告&知识库
	 * @param titleCont 搜索关键词
	 * @return 搜索结果
	 */
	@Override
	public List<PlatInformation> whole(Integer page, Integer rows, String type, String titleCont) {
		int start = (page - 1) * rows;
		Map<String, Object> m = new HashMap<>();
		m.put("start", start);
		m.put("rows", rows);
		m.put("type", type);
		m.put("titleCont", titleCont);
		return platInformationDao.whole(m);
	}

	/**
	 * 全文搜索的总条目
	 *
	 * @param type      搜索类型 0:行业 1:公告 2:知识库 3:行业&公告 4: 行业&知识库 5:公告&知识库 6:行业&公告&知识库
	 * @param titleCont 搜索关键词
	 * @return 搜索结果总条目
	 */
	@Override
	public int wholeCount(String type, String titleCont) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", type);
		m.put("titleCont", titleCont);
		return platInformationDao.wholeCount(m);
	}

	@Override
	public PlatInformation wholeId(String id) {
		// TODO Auto-generated method stub
		return platInformationDao.wholeId(id);
	}

}
/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.dao.PlatCollectionDao;
import com.gtzn.modules.home.entity.PlatCollection;
import com.gtzn.modules.home.service.PlatCollectionService; 

/**
 * 最新采集Service
 * @author wzx
 * @version 2017-04-25
 */
@Service("platCollection")
@Transactional(readOnly = true)
public class PlatCollectionServiceImpl extends CrudService<PlatCollectionDao, PlatCollection> implements PlatCollectionService{

	public List<PlatCollection> findList(PlatCollection platCollection) {
		return super.findList(platCollection);
	}
	
	public Pager<PlatCollection> findPage(Pager<PlatCollection> pager, PlatCollection platCollection) {
		return super.findPage(pager, platCollection);
	}

	public PlatCollection selectPlatCollectionInfo(String id) {
		return super.get(id);
	}

	public PlatCollection selectPlatCollectionInfo(PlatCollection platCollection) {
		return super.get(platCollection);
	}

	public List<PlatCollection> findAllList(PlatCollection platCollection){
		return super.findAllList(platCollection);
	}

	@Override
	public List<PlatCollection> findTopN(int topN) {
		return dao.findTopN(topN);
	}
}
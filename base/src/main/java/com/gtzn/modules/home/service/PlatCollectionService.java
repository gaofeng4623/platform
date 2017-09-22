/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.List;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.home.entity.PlatCollection;

/**
 * 最新采集Service
 * @author wzx
 * @version 2017-04-25
 */

public interface PlatCollectionService {

	public List<PlatCollection> findList(PlatCollection platCollection);
	
	public Pager<PlatCollection> findPage(Pager<PlatCollection> pager, PlatCollection platCollection);

	public PlatCollection selectPlatCollectionInfo(String key);

	public PlatCollection selectPlatCollectionInfo(PlatCollection platCollection);
	
	public List<PlatCollection> findAllList(PlatCollection platCollection);
	
	public List<PlatCollection> findTopN(int topN);
	
}
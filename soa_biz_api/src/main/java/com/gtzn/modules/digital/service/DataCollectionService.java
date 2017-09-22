package com.gtzn.modules.digital.service;

import java.util.List;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.digital.entity.YDataCollection;

/**
 * 最新采集service
 * @author chenyp
 *
 */
public interface DataCollectionService {
	
	public Pager<YDataCollection> findPage(Pager<YDataCollection> pager, YDataCollection yDataCollection);
	
	public List<YDataCollection> findTopN(int topN);
	
	public List<YDataCollection> selectYDataCollection(YDataCollection yDataCollection);
	
	public List<YDataCollection> selectFileCollection(String aid);
}

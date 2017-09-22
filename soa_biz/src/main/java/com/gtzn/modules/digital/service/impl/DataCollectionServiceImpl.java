package com.gtzn.modules.digital.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.annotation.Source;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.digital.dao.DataCollectionDao;
import com.gtzn.modules.digital.dao.YArchivestoreDao;
import com.gtzn.modules.digital.entity.YArchivestore;
import com.gtzn.modules.digital.entity.YDataCollection;
import com.gtzn.modules.digital.service.DataCollectionService;

@Source("digital")
@Service("dataCollectionService")
@Transactional(readOnly = true)
public class DataCollectionServiceImpl extends CrudService<DataCollectionDao, YDataCollection> implements DataCollectionService {

	@Autowired
	private YArchivestoreDao yArchivestoreDao;
	@Autowired
	private DataCollectionDao dataCollectionDao;
	
	/**
	 * 获取主表名称(D_0039)
	 * @return 表明
	 */
	public String getCTableName(){
		YArchivestore ya = new YArchivestore();
		ya.setName("数据采集");
		ya.setDatype("传统立卷");
		List<YArchivestore> list = yArchivestoreDao.findAllList(ya);
		String tname = list.get(0).getTblname();
		return tname;
	}
	/**
	 * 获取从表名称(D_0040)
	 * @return
	 */
	public String getATableName(){
		YArchivestore ya = new YArchivestore();
		ya.setName("数据采集");
		ya.setDatype("卷内文件");
		List<YArchivestore> list = yArchivestoreDao.findAllList(ya);
		String tname=null;
		if (list!=null&&list.get(0)!=null) {
			tname = list.get(0).getTblname();
		}
		return tname;
	}
	/**
	 * 获取文件表名称(D_0040_FILE)
	 * @return
	 */
	public String getFTableName(){
		String FTN = getATableName()+"_FILE";
		return FTN;
	}
	/**
	 * 分页查询
	 * @param pager
	 * @param yDataCollection
	 * @return
	 */
	public Pager<YDataCollection> findPage(Pager<YDataCollection> pager, YDataCollection yDataCollection) {
		int count = dataCollectionDao.findCount(getCTableName());
		pager.setRecords(count);
		List<YDataCollection> c = dataCollectionDao.getPage(getCTableName(),getATableName(), pager.getCurrentRows(), 
				pager.getCurrentNum(), yDataCollection.getSubject(),yDataCollection.getGenName());
		pager.setList(c);
		return pager;
	}
	/**
	 * 首页显示
	 * @param topN
	 * @return
	 */
	public List<YDataCollection> findTopN(int topN) {
		return dataCollectionDao.findTopN(topN, getCTableName(), getATableName(), getFTableName());
	}
	/**
	 * 查询一条信息卷内文件
	 * @param yDataCollection
	 * @return
	 */
	public List<YDataCollection> selectYDataCollection(YDataCollection yDataCollection){
		return dataCollectionDao.get(yDataCollection.getId(), getCTableName(),getATableName());
	}
	/**
	 * 查询一条文件信息
	 * @param yDataCollection
	 * @return
	 */
	public List<YDataCollection> selectFileCollection(String aid){
		return dataCollectionDao.getFile(aid, getFTableName());
	}
}

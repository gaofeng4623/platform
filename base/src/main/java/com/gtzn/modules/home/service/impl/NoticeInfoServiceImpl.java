/**
 *
 */
package com.gtzn.modules.home.service.impl;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.dao.NoticeInfoDao;
import com.gtzn.modules.home.entity.NoticeInfo;
import com.gtzn.modules.home.service.NoticeInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公告信息Service
 * @author wangzx
 * @version 2017-04-07
 */
@Service("noticeInfo")
@Transactional(readOnly = true)
public class NoticeInfoServiceImpl extends CrudService<NoticeInfoDao, NoticeInfo> implements NoticeInfoService{

	public List<NoticeInfo> findList(NoticeInfo noticeInfo) {
		return super.findList(noticeInfo);
	}
	
	public Pager<NoticeInfo> findPage(Pager<NoticeInfo> pager, NoticeInfo noticeInfo) {
		return super.findPage(pager, noticeInfo);
	}

	public NoticeInfo selectNoticeInfoInfo(String id) {
		return super.get(id);
	}

	public NoticeInfo selectNoticeInfoInfo(NoticeInfo noticeInfo) {
		return super.get(noticeInfo);
	}

	@Transactional(readOnly = false)
	public void save(NoticeInfo noticeInfo) {
		super.save(noticeInfo);
	}

	@Transactional(readOnly = false)
	public void insertNoticeInfoSelective(NoticeInfo noticeInfo) {
		super.insertSelective(noticeInfo);
	}

	@Transactional(readOnly = false)
	public void updateNoticeInfoSelective(NoticeInfo noticeInfo) {
		super.updateSelective(noticeInfo);
	}

	@Transactional(readOnly = false)
	public void deleteNoticeInfoInfo(NoticeInfo noticeInfo) {
		super.delete(noticeInfo);
	}

	@Transactional(readOnly = false)
	public void deleteNoticeInfoInfo(String id) {
		super.delete(id);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String[] idList) {
		return super.batchDelete(idList);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<NoticeInfo> list) {
		return super.batchInsert(list);
	}

	@Transactional(readOnly = false)
	public int executeUpdate(String sql) {
		return super.executeUpdate(sql);
	}

	@Transactional(readOnly = false)
	public void executeBatchUpdate(List<String> arraySql) {
		for (String sql : arraySql) {
			super.executeUpdate(sql);
		}
	}

	public List<NoticeInfo> findAllList(NoticeInfo noticeInfo){
		return super.findAllList(noticeInfo);
	}

	@Override
	public List<NoticeInfo> findTopN(NoticeInfo noticeInfo, int topN) {
		// TODO Auto-generated method stub
		return dao.findTopN(noticeInfo, topN);
	}

	@Override
	public List<NoticeInfo> notePage(Integer page, Integer rows, String titleCont) {
		// TODO Auto-generated method stub
		int start = (page-1)*rows;
		Map m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("titleCont", titleCont);
		return dao.notePage(m);
	}

	@Override
	public int noteCount(String titleCont) {
		// TODO Auto-generated method stub
        Map m = new HashMap();
		m.put("titleCont", titleCont);  
		return dao.noteCount(m);
	}
}
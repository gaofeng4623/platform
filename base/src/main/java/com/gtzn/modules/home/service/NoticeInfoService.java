/**
 *
 */
package com.gtzn.modules.home.service;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.home.entity.NoticeInfo;
import com.gtzn.modules.home.entity.PlatInformation;

import java.util.List;

/**
 * 公告信息Service
 * @author wangzx
 * @version 2017-04-07
 */

public interface NoticeInfoService {

	public List<NoticeInfo> findList(NoticeInfo noticeInfo);
	
	public Pager<NoticeInfo> findPage(Pager<NoticeInfo> pager, NoticeInfo noticeInfo);

	public NoticeInfo selectNoticeInfoInfo(String key);

	public NoticeInfo selectNoticeInfoInfo(NoticeInfo noticeInfo);
	
	public void save(NoticeInfo noticeInfo);

	public void insertNoticeInfoSelective(NoticeInfo noticeInfo);

	public void updateNoticeInfoSelective(NoticeInfo noticeInfo);
	
	public void deleteNoticeInfoInfo(NoticeInfo noticeInfo);

	public void deleteNoticeInfoInfo(String id);

	public int batchDelete(String[] idList);

	public int batchInsert(List<NoticeInfo> list);

	public int executeUpdate(String sql);

	public void executeBatchUpdate(List<String> arraySql);
	
	public List<NoticeInfo> findAllList(NoticeInfo noticeInfo);
	
	public List<NoticeInfo> findTopN(NoticeInfo noticeInfo, int topN);
	List<NoticeInfo> notePage(Integer page, Integer rows,String titleCont);
	int noteCount(String titleCont);

}
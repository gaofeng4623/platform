/**
 * 
 */
package com.gtzn.modules.home.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.home.entity.NoticeInfo;
import com.gtzn.modules.home.entity.PlatInformation;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 公告信息DAO接口
 * @author wangzx
 * @version 2017-04-07
 */
@MyBatisDao
public interface NoticeInfoDao extends CrudDao<NoticeInfo> {
	
	public List<NoticeInfo> findTopN( @Param("noticeinfo")NoticeInfo noticeinfo, @Param(value = "topN") int topN);
	List<NoticeInfo> notePage(@Param("m") Map m);
	int noteCount(@Param("m") Map m);
}
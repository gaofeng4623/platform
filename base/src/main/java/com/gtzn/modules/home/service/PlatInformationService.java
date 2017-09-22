/**
 *
 */
package com.gtzn.modules.home.service;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.home.entity.PlatInformation;

import java.util.List;

public interface PlatInformationService {
	public void save(PlatInformation platInformation);

	int queryPlatInformation(String title);

	List<PlatInformation> queryPlatInformationList();

	Pager<PlatInformation> findPage(Pager<PlatInformation> pager, PlatInformation platInformation);

	PlatInformation queryPlatInFrom(String id);

	List<PlatInformation> loadData(Integer page, Integer rows, String titleCont);

	int loadCount(String titleCont);

	int updatePlat(PlatInformation plat);

	void deletePlatInformation(PlatInformation platInformation);

	/**
	 * 全文搜索,目前是like匹配
	 *
	 * @param page      第几页
	 * @param rows      每页条数
	 * @param type      搜索类型 0:行业 1:公告 2:知识库 3:行业&公告 4: 行业&知识库 5:公告&知识库 6:行业&公告&知识库
	 * @param titleCont 搜索关键词
	 * @return 搜索结果
	 */
	List<PlatInformation> whole(Integer page, Integer rows, String type, String titleCont);//全部

	/**
	 * 全文搜索的总条目
	 *
	 * @param type      搜索类型 0:行业 1:公告 2:知识库 3:行业&公告 4: 行业&知识库 5:公告&知识库 6:行业&公告&知识库
	 * @param titleCont 搜索关键词
	 * @return 搜索结果总条目
	 */
	int wholeCount(String type, String titleCont);//全部

	PlatInformation wholeId(String id);

}
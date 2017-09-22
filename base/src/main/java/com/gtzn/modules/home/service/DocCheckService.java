package com.gtzn.modules.home.service;


import java.util.List;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.home.entity.DocInfo;

/**
 * 档案审核Service
 * @author wangzhao
 * @version 2017-04-11
 */

public interface DocCheckService {
	public Pager<DocInfo> getCheckListByPara(Pager<DocInfo> pager, DocInfo docInfo);
	public DocInfo getCheckInfoById(String id);
	public List<DocInfo> findDocCheck(DocInfo docInfo, int topN);
}
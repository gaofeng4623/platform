package com.gtzn.modules.home.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.home.entity.DocInfo;
import com.gtzn.modules.home.service.DocCheckService;

/**
 * 档案审批Service
 * @author wangzhao
 * @version 2017-04-07
 */
@Service("check")
public class DocCheckServiceImpl implements DocCheckService{
	
	private List<DocInfo> getDataList(){
		//------假数据
		List<DocInfo> list1 = new ArrayList<>();
		DocInfo docInfo1 = new DocInfo();
		docInfo1.setDocid("1");
		docInfo1.setTitle("2016年度山东省档案局(馆)部门预算");
		docInfo1.setApplyName("申请人1");
		docInfo1.setApplyTime("2016-10-12");
		docInfo1.setCheckName("审批人1");
		docInfo1.setCheckType("审批通过");
		docInfo1.setCheckTest("审批意见");;
		docInfo1.setDataTime("2016-10-13");
		list1.add(docInfo1);
		
		DocInfo docInfo2 = new DocInfo();
		docInfo2.setDocid("2");
		docInfo2.setTitle("事业单位法人变更信息公开");
		docInfo2.setApplyName("申请人2");
		docInfo2.setApplyTime("2016-10-09");
		docInfo2.setCheckName("");
		docInfo2.setCheckType("待审批");
		docInfo2.setCheckTest("");;
		docInfo2.setDataTime("2016-10-11");
		list1.add(docInfo2);
		
		DocInfo docInfo3 = new DocInfo();
		docInfo3.setDocid("3");
		docInfo3.setTitle("2015年度山东省档案局部门预算");
		docInfo3.setApplyName("申请人3");
		docInfo3.setApplyTime("2015-10-09");
		docInfo3.setCheckName("审批人2");
		docInfo3.setCheckType("驳回");
		docInfo3.setCheckTest("格式不正确");;
		docInfo3.setDataTime("2015-10-13");
		list1.add(docInfo3);
		
		DocInfo docInfo4 = new DocInfo();
		docInfo4.setDocid("4");
		docInfo4.setTitle("2014年度山东省档案局部门预算");
		docInfo4.setApplyName("申请人4");
		docInfo4.setApplyTime("2014-10-09");
		docInfo4.setCheckName("审批人1");
		docInfo4.setCheckType("审批通过");
		docInfo4.setCheckTest("审批意见222");;
		docInfo4.setDataTime("2014-10-13");
		list1.add(docInfo4);
		
		for(int i=5; i<50; i++){
			DocInfo docInfo = new DocInfo();
			docInfo.setDocid(i+"");
			docInfo.setTitle(2018-i + "年度山东省档案局部门预算");
			docInfo.setApplyName("申请人"+i);
			docInfo.setApplyTime(2018-i + "-10-09");
			docInfo.setCheckName("审批人"+i);
			docInfo.setCheckType("待审批");
			docInfo.setCheckTest("审批意见"+i);;
			docInfo.setDataTime(2018-i + "-10-13");
			list1.add(docInfo);
		}
		
		return list1;
	}
	
	/**
	 * 查询文档审批列表
	 */
	@Override
	public Pager<DocInfo> getCheckListByPara(Pager<DocInfo> pager, DocInfo docInfo) {
		
		//todo 调用借口获取数据
		List<DocInfo> list = getDataList();
		
		//todo 假数据加工
		List<DocInfo> list1 = new ArrayList<>();
		String title = docInfo.getTitle();
		String applyName = docInfo.getApplyName();
		String checkType = docInfo.getCheckType();
		int page = pager.getPage();
		int rows = pager.getRows();
		int beforePageNum = 0;
		int startPageNum = (page-1)*rows+1;
		int endPageNum = page*rows;
		for(DocInfo docItem : list){
			 if(null == title || "".equals(title) || docItem.getTitle().contains(title)){
				 if(null == applyName || "".equals(applyName) || docItem.getApplyName().contains(applyName)){
					 if(null == checkType || "".equals(checkType) || docItem.getCheckType().contains(checkType)){
						 beforePageNum++;
						 if(beforePageNum >= startPageNum && beforePageNum <= endPageNum){
							 list1.add(docItem);
						 }
					 }
				 }
			 }
		}
		pager.setList(list1);
        pager.setRecords(beforePageNum);
		return pager;
	}

	/**
	 * 获取文档审批详情
	 */
	@Override
	public DocInfo getCheckInfoById(String docid) {
		DocInfo docInfo = getDataList().get(Integer.parseInt(docid)-1);
		return docInfo;
	}
	
	/**
	 * home页面模块展示用
	 */
	@Override
	public List<DocInfo> findDocCheck(DocInfo docInfo, int topN) {
		Pager<DocInfo> pager = new Pager<DocInfo>();
		pager.setPage(1);
		pager.setRows(topN);
		docInfo.setCheckType("待审批");
		pager = getCheckListByPara(pager,docInfo);
		return pager.getList();
	}

}
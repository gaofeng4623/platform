package com.gtzn.modules.digital.service.impl;

import java.util.Arrays;
import java.util.List;

import com.gtzn.common.annotation.Source;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.base.entity.Constant;
import com.gtzn.modules.digital.dao.WorkProcessDao;
import com.gtzn.modules.digital.entity.YWorklist;
import com.gtzn.modules.digital.service.WorkProcessService;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.workflow.domain.WFTask;

@Source("digital")
@Service("workProcessService")
@Transactional(readOnly = true)
public class WorkProcessServiceImpl implements WorkProcessService{
	
	@Autowired
	private WorkProcessDao workProcessDao;
	
	/**
	 * 获取流程实例信息
	 * @param wftask
	 * @return
	 */
	public WFTask get(WFTask wftask){
		
		return null;
	}
	
	/**
	 * 获取工作进度已办和待办分页列表
	 * @param pager
	 * @param wftask
	 * @param user
	 * @return
	 */
	@Override
	public Pager<WFTask> loadTaskListPage(Pager<WFTask> pager, WFTask wftask, User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取工作进度前几条记录列表
	 * @param wftask
	 * @param user
	 * @return
	 */
	@Override
	public List<WFTask> loadTaskTopList(WFTask wftask, User user, int top) {
		// TODO Auto-generated method stub
		return null;
	}
		
	/**
	 * 获取服务大厅待办list根据流程类型
	 * @param applyType
	 * @return
	 */
	public List<WFTask> findToDoWroklist(String applyType,User user){
		String state = "0";
		List<YWorklist> worklist = Lists.newArrayList();
		YWorklist yWorklist = new YWorklist();
		yWorklist.setUser(user.getId());
		yWorklist.setState(state);
		yWorklist.setApplyTypeList(Arrays.asList(applyType.split(",")));
		worklist = workProcessDao.findAllList(yWorklist);
		List<WFTask> list = Lists.newArrayList();
		
		for(YWorklist work : worklist){
			list.add(parseWorkList(work));
		}
		
		return list;
	}
	
	/**
	 * 获取服务大厅已办list
	 * @param applyType
	 * @param user
	 * @return
	 */
	public List<WFTask> findDoneWroklist(String applyType, String state, User user){
		String doneState = "1,2,3";//已办的状态   1同意 2拒绝 3转审批
		YWorklist yWorklist = new YWorklist();
		yWorklist.setUser(user.getId());
		yWorklist.setState(state);
		yWorklist.setApplyTypeList(Arrays.asList(applyType.split(",")));
		yWorklist.setStateList(Arrays.asList(doneState.split(",")));
		List<YWorklist> worklist = workProcessDao.findDoneList(yWorklist);
		List<WFTask> list = Lists.newArrayList();
		for(YWorklist work : worklist){
			list.add(parseWorkList(work));
		}
		return list;
	}
	
	
	private WFTask parseWorkList(YWorklist worklist){
		WFTask task = new WFTask();
		task.setTaskId(worklist.getId());
		task.setTaskName(worklist.getStepId());
		task.setStatus(worklist.getState());
		task.setProcessDefinitionKey(worklist.getApplyType());
		task.setProcessDefinitionName(Constant.workflow_type.get(worklist.getApplyType()));//流程名称
		task.setAssignee(worklist.getUser());
		task.setProcessInstanceId(worklist.getApplyId());
		task.setAuthor(worklist.getApplyName());//发起人
		task.setCreateTime(DateFormatUtils.format(worklist.getCreateDate(), "yyyy-MM-dd HH:mm"));//任务创建时间
		if(worklist.getApproveDate()!=null){
			task.setEndTime(DateFormatUtils.format(worklist.getApproveDate(), "yyyy-MM-dd HH:mm"));
		}
		return task;
	}

}

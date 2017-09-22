package com.gtzn.modules.digital.service;

import java.util.List;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.workflow.domain.WFTask;

/**
 * 工作进度service
 * @author li_gm
 *
 */
public interface WorkProcessService {
	
	/**
	 * 获取流程实例信息
	 * @param wftask
	 * @return
	 */
	public WFTask get(WFTask wftask);
	
	/**
	 * 获取工作进度已办和待办分页列表
	 * @param pager
	 * @param wftask
	 * @param user
	 * @return
	 */
	public Pager<WFTask> loadTaskListPage(Pager<WFTask> pager, WFTask wftask, User user);
	
	/**
	 * 获取工作进度前几条记录列表
	 * @param wftask
	 * @param user
	 * @return
	 */
	public List<WFTask> loadTaskTopList(WFTask wftask, User user, int top);
	
	/**
	 * 获取服务大厅待办list根据流程类型
	 * @param applyType
	 * @param user
	 * @return
	 */
	public List<WFTask> findToDoWroklist(String applyType,User user);
	
	/**
	 * 获取服务大厅已办list
	 * @param applyType
	 * @param user
	 * @return
	 */
	public List<WFTask> findDoneWroklist(String applyType, String state,User user);
	
	
}

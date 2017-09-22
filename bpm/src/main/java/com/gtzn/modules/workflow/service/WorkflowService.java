package com.gtzn.modules.workflow.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.gtzn.modules.workflow.domain.WFComment;
import com.gtzn.modules.workflow.domain.WFNextTask;

public interface WorkflowService {

	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessId	业务表编号
	 * @param variables			流程变量
	 * @return 流程实例ID
	 */
	String startProcess(String tenantId, String procDefKey, String businessId, String userNo,
                        Map<String, Object> variables, Map<String, Object> urlParam);

	/**
	 * 提交操作
	 */
	void commitProcess(String taskId, String processInstanceId, String userId, String comment,
                       Map<String, Object> variables);

	/**
	 * 提交操作
	 */
	void commitProcess(String taskId, String userId, String comment, Map<String, Object> variables);

	void addComment(String taskId, String processInstanceId, String userId, String comment);

	void claim(String taskId, String userId);
	
	void delegation(String taskId, String userId);
	
	List<WFComment> findProcessComments(String processInstanceId);

	HistoricTaskInstance findHistoricTaskInstance(String processInstanceId, String taskDefinitionKey);

	/**
	 * 查询指定流程信息
	 * @Title: findTaskById  
	 * @Description: TODO 
	 * @param       
	 * @return Task     
	 * @throws
	 */
	Task findTaskById(String taskId);

	List<WFNextTask> findNextTask(String taskId, Map<String, Object> variables);

	/**
	 * 获取当前节点信息
	 * @param processInstance
	 * @return
	 */
	List<Task> findCurrentTask(String processInstanceId);

	ProcessInstance findProcessInstance(String processInstanceId);

	/**
	 * 撤回流程
	 * @Title: callBackProcess  
	 * @Description: TODO 
	 * @param       
	 * @return void     
	 * @throws
	 */
	void callBackProcess(String processInstanceId, String activityId, Map<String, Object> variables);

	String deleteProcessInstance(String processInstanceId);

	String findProcessDefinitionId(String processInstanceId);

}
package com.gtzn.modules.workflow.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.modules.workflow.cmd.AddCommentCmd;
import com.gtzn.modules.workflow.cmd.NextTaskCmd;
import com.gtzn.modules.workflow.domain.SortWFCommentByTime;
import com.gtzn.modules.workflow.domain.WFComment;
import com.gtzn.modules.workflow.domain.WFNextTask;
import com.gtzn.modules.workflow.service.WorkflowService;

@Service
@Transactional(readOnly = true)
public class WorkflowServiceImpl implements WorkflowService {
	
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected FormService formService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	IdentityService identityService;
	
	@Autowired
	ManagementService managementService;
	
	
	
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#startProcess(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.util.Map)
	 */
	@Override
	@Transactional(readOnly = false)
	public String startProcess(String tenantId, String procDefKey, String businessId, String userNo, Map<String, Object> variables, Map<String, Object> urlParam) {
		try {
			//截取前5位 分行代码
			//tenantId = StringUtils.substring(tenantId, 0, 5);
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(userNo);
			// 设置流程变量
			if (variables == null) {
				variables = new HashMap<>();
			}
			// 启动流程
			ProcessInstance procIns = runtimeService.startProcessInstanceByKeyAndTenantId(procDefKey, businessId, variables, tenantId);
			List<String> param = new ArrayList<>();
			if (null != urlParam) {
				for (Entry<String, Object> enumEntry : urlParam.entrySet()) {
					String key = enumEntry.getKey();
					Object value = enumEntry.getValue();
					if (null != value) {
						param.add(key + "=" + value.toString());
					}
				}
			}
			runtimeService.setVariable(procIns.getId(), "urlParam", StringUtils.join(param, "&"));
			return procIns.getId();
		} finally {
			  identityService.setAuthenticatedUserId(null);
		}
	}

	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#commitProcess(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	@Transactional(readOnly = false)
	public void commitProcess(String taskId, String processInstanceId, String userId, String comment, Map<String, Object> variables) {
		try {
			if (variables == null) {
				variables = new HashMap<String, Object>();
			}
			identityService.setAuthenticatedUserId(userId);
			// 添加意见
			if (StringUtils.isBlank(comment)) {
				comment = "[未填写意见]";
			}
			taskService.addComment(taskId, processInstanceId, comment);
			taskService.complete(taskId, variables);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#commitProcess(java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	@Transactional(readOnly = false)
	public void commitProcess(String taskId, String userId, String comment, Map<String, Object> variables) {
		try {
			if (variables == null) {
				variables = new HashMap<String, Object>();
			}
			identityService.setAuthenticatedUserId(userId);
			Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
			// 添加意见
			if (StringUtils.isBlank(comment)) {
				comment = "[未填写意见]";
			}
			taskService.addComment(taskId, task.getProcessInstanceId(), comment);
			taskService.complete(taskId, variables);
		} finally {
			  identityService.setAuthenticatedUserId(null);
		}
	}
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#addComment(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public void addComment(String taskId, String processInstanceId, String userId, String comment) {
		try {
			identityService.setAuthenticatedUserId(userId);
			managementService.executeCommand(new AddCommentCmd(taskId, processInstanceId, comment));
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#claim(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public void claim(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delegation(String taskId, String userId) {
		//1 自己直接设置任务派遣人
	/*	Task task = this.findTaskById(taskId);
		//已被签收
		String assignee = task.getAssignee();
		if (StringUtils.isNotEmpty(assignee)) {
			taskService.setOwner(taskId, assignee);
		}
		taskService.setAssignee(taskId, userId);*/
		
		
		//2 使用 工作流api，被委托人完成任务事使用taskService.resolveTask(taskId);返回给授权人
		
		taskService.delegateTask(taskId, userId);
	}
	
	/*public void setRuntimeVariable(String processInstanceId, String variableName, Object value) {
		runtimeService.setVariable(processInstanceId, variableName, value);
	}*/
	
	
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#findProcessComments(java.lang.String)
	 */
	@Override
	public List<WFComment> findProcessComments(String processInstanceId) {
		List<Comment> historyCommnets = new ArrayList<>();
		//通过流程实例查询所有的(用户任务类型)历史活动
		List<HistoricActivityInstance> hais = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId).activityType("userTask").orderByHistoricActivityInstanceEndTime().desc().list();
		//查询每个历史任务的批注
		for (HistoricActivityInstance hai : hais) {
			String historytaskId = hai.getTaskId();
			List<Comment> comments = taskService.getTaskComments(historytaskId);
			if (comments != null && comments.size() > 0) {
				historyCommnets.addAll(comments);
			}
		}
		List<WFComment> list = new ArrayList<WFComment>();
		for (Comment ct : historyCommnets) {
			CommentEntity c = (CommentEntity) ct;
			WFComment comment = new WFComment();
			comment.setId(c.getId());
			comment.setTaskId(c.getTaskId());
			comment.setProcessInstanceId(c.getProcessInstanceId());
			comment.setTime(c.getTime());
			comment.setType(c.getType());
			comment.setUserId(c.getUserId());
			comment.setFullMessage(c.getFullMessage());
			comment.setMessage(c.getMessage());
			HistoricTaskInstance t = historyService.createHistoricTaskInstanceQuery().taskId(c.getTaskId()).singleResult();
			comment.setTaskDefinitionKey(t.getTaskDefinitionKey());
			comment.setTaskName(t.getName());//任务名称
			comment.setUserName(identityService.createUserQuery().userId(c.getUserId()).singleResult().getFirstName());//用户名称
			
			list.add(comment);
		}
		Collections.sort(list, new SortWFCommentByTime());
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#findHistoricTaskInstance(java.lang.String, java.lang.String)
	 */
	@Override
	public HistoricTaskInstance findHistoricTaskInstance(String processInstanceId, String taskDefinitionKey) {
		HistoricTaskInstance hi = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).taskDefinitionKey(taskDefinitionKey)
				.finished().orderByTaskDueDate().desc().listPage(0, 1).get(0);
		return hi;
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#findTaskById(java.lang.String)
	 */
	@Override
	public Task findTaskById(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		return task;
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#findNextTask(java.lang.String, java.util.Map)
	 */
	@Override
	public List<WFNextTask> findNextTask(String taskId, Map<String, Object> variables) {
		return managementService.executeCommand(new NextTaskCmd<List<WFNextTask>>(taskId, variables));
	}
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#findCurrentTask(java.lang.String)
	 */
	@Override
	public List<Task> findCurrentTask(String processInstanceId) {
		List<Task> currentTask = new ArrayList<>();
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		//流程未结束
		if (null != processInstance) {
			currentTask  = taskService.createTaskQuery()
					.processInstanceId(processInstance.getId()).list();
		} 
		//流程已结束
		else {
			TaskEntity current= new TaskEntity();
			current.setTaskDefinitionKey("end");
			current.setProcessInstanceId(processInstanceId);
			current.setName("已结束");
			currentTask.add(current);
		}
		return currentTask;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#findProcessInstance(java.lang.String)
	 */
	@Override
	public ProcessInstance findProcessInstance(String processInstanceId) {
		return runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#callBackProcess(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	@Transactional(readOnly = false)
	public void callBackProcess(String processInstanceId, String activityId, Map<String, Object> variables) {
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}
		// 查找所有 未完成任务节点，同时取回
		List<Task> taskList = findTaskList(processInstanceId);
		for (Task task : taskList) {
			turnTransition(task.getId(), activityId, variables);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#deleteProcessInstance(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public String deleteProcessInstance(String processInstanceId) {
		String result = "01";
		ProcessInstance p = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (null == p) {
			result = "流程已结束不能删除!";
		} else {
			 runtimeService.deleteProcessInstance(processInstanceId, ""); 
			 historyService.deleteHistoricProcessInstance(processInstanceId);
		}
		return result;
	}
	
	
	/* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessCoreService#findProcessDefinitionId(java.lang.String)
	 */
	@Override
	public String findProcessDefinitionId (String processInstanceId) {
		ProcessInstance p = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (null == p) {
			// 已结束流程
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if(historicProcessInstance == null){
				return null;
			}else{
				return historicProcessInstance.getProcessDefinitionId();
			}
		} else {
			return p.getProcessDefinitionId();
		}
	}
	
	/**
	 * 根据任务ID获取流程定义
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId) {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(findTaskById(taskId).getProcessDefinitionId());
		return processDefinition;
	}

	/**
	 * 根据流程实例ID和任务key值查询所有同级任务集合
	 * 
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	private List<Task> findTaskList(String processInstanceId) {
		return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
	}
	
	@Transactional(readOnly = false)
	private void turnTransition(String taskId, String activityId, Map<String, Object> variables) {
		// 当前节点
		ActivityImpl currActivity = findActivitiImpl(taskId, null);
		// 清空当前流向
		List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

		// 创建新流向
		TransitionImpl newTransition = currActivity.createOutgoingTransition();
		// 目标节点
		ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
		// 设置新流向的目标节点
		newTransition.setDestination(pointActivity);

		// 执行转向任务
		taskService.complete(taskId, variables);
		// 删除目标节点新流入
		pointActivity.getIncomingTransitions().remove(newTransition);

		// 还原以前流向
		restoreTransition(currActivity, oriPvmTransitionList);
	}
	
	/**
	 * 清空指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @return 节点流向集合
	 */
	@Transactional(readOnly = false)
	private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
		// 存储当前节点所有流向临时变量
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		// 获取当前节点所有流向，存储到临时变量，然后清空
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		return oriPvmTransitionList;
	}
	/**
	 * 还原指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @param oriPvmTransitionList
	 *            原有节点流向集合
	 */
	@Transactional(readOnly = false)
	private void restoreTransition(ActivityImpl activityImpl, List<PvmTransition> oriPvmTransitionList) {
		// 清空现有流向
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		pvmTransitionList.clear();
		// 还原以前流向
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}
	/**
	 * 根据任务ID和节点ID获取活动节点 <br>
	 * 
	 * @param taskId
	 *            任务ID
	 * @param activityId
	 *            活动节点ID <br>
	 *            如果为null或""，则默认查询当前活动节点 <br>
	 *            如果为"end"，则查询结束节点 <br>
	 * 
	 * @return
	 * @throws Exception
	 */
	private ActivityImpl findActivitiImpl(String taskId, String activityId) {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 获取当前活动节点ID
		if (StringUtils.isEmpty(activityId)) {
			activityId = findTaskById(taskId).getTaskDefinitionKey();
		}

		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}

		// 根据节点ID，获取对应的活动节点
		ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition).findActivity(activityId);

		return activityImpl;
	}
	
}

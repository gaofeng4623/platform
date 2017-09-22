package com.gtzn.modules.workflow.cmd;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractRuntimeCmd<T> implements Command<T> {
	private static final Logger logger = LoggerFactory.getLogger(AbstractRuntimeCmd.class);
	protected String taskId;
	protected TaskEntity taskEntity;
	protected String processDefId;
	protected ExecutionEntity executionEntity;
	protected ProcessDefinitionEntity processDefinitionEntity;
	protected ProcessInstance processInstance;
	protected TaskService taskService = null;
	protected RuntimeService runtimeService = null;
	protected RepositoryService repositoryService = null;
	protected HistoryService historyService = null;

	AbstractRuntimeCmd(String taskId) {
		this.taskId = taskId;
	}

	void initServices(CommandContext commandContext) {
		logger.info("开始初始化Service{}", AbstractRuntimeCmd.class);
		taskService = commandContext.getProcessEngineConfiguration().getTaskService();
		runtimeService = commandContext.getProcessEngineConfiguration().getRuntimeService();
		repositoryService = commandContext.getProcessEngineConfiguration().getRepositoryService();
		historyService = commandContext.getProcessEngineConfiguration().getHistoryService();
	};

	@Override
	public T execute(CommandContext commandContext) {
		initServices(commandContext);
		this.taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		initResources();
		return this.executeCmd(commandContext);
	}

	private void initResources() {
		logger.info("开始初始化相关资源{}", AbstractRuntimeCmd.class);
		this.executionEntity = (ExecutionEntity) runtimeService
				.createExecutionQuery()
				.executionId(taskEntity.getExecutionId()).singleResult();
		this.processDefId = taskEntity.getProcessDefinitionId();
		RepositoryServiceImpl resImpl = (RepositoryServiceImpl) repositoryService;
		processDefinitionEntity = (ProcessDefinitionEntity) resImpl
				.getDeployedProcessDefinition(taskEntity.getProcessDefinitionId());
		processInstance = runtimeService.createProcessInstanceQuery()
				.includeProcessVariables()
				.processInstanceId(taskEntity.getProcessInstanceId())
				.singleResult();

	}

	public abstract T executeCmd(CommandContext commandContext);

}

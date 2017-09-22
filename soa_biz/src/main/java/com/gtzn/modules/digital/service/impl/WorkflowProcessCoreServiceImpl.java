package com.gtzn.modules.digital.service.impl;

import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtzn.common.annotation.Source;
import com.gtzn.modules.digital.service.WorkflowProcessCoreService;
import com.gtzn.modules.workflow.dao.ActCallbackDao;
import com.gtzn.modules.workflow.dao.ActModelDeployDao;
import com.gtzn.modules.workflow.domain.ActCallback;
import com.gtzn.modules.workflow.domain.ActModelDeploy;

@Source("digital")
@Service
public class WorkflowProcessCoreServiceImpl implements WorkflowProcessCoreService {
	
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
	@Autowired
	ActCallbackDao calldao;
	@Autowired
	ActModelDeployDao mddao;

	@Override
	public List<ActCallback> getCallback(String taskid) {
		ActCallback back = new ActCallback();
		back.setTaskid(taskid);
		return calldao.findAllList(back);
	}

	@Override
	public ActModelDeploy getModelkey(String deployid) {
		ActModelDeploy entity = new ActModelDeploy();
		entity.setDeployid(deployid);
		List<ActModelDeploy> mdlist = mddao.findAllList(entity);
		if(mdlist!=null && mdlist.size()>0){
			return mdlist.get(0);
		}
		return null;
	}

	
}

package com.gtzn.modules.digital.service;

import java.util.List;

import com.gtzn.modules.workflow.domain.ActCallback;
import com.gtzn.modules.workflow.domain.ActModelDeploy;

public interface WorkflowProcessCoreService {

	List<ActCallback> getCallback(String taskid);

	ActModelDeploy getModelkey(String deployid);

}
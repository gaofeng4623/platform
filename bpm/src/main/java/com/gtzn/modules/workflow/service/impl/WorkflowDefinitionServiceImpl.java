package com.gtzn.modules.workflow.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.gtzn.modules.workflow.domain.Node;
import com.gtzn.modules.workflow.service.WorkflowDefinitionService;
import com.gtzn.modules.workflow.util.WorkflowUtils;

/**
 * 工作流中流程以及流程实例相关Service
 *
 */
@Service
public class WorkflowDefinitionServiceImpl implements WorkflowDefinitionService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected HistoryService historyService;
    
    @Autowired
	private IdentityService identityService;

    /* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessDefinitionService#findProcessDefinitionByPid(java.lang.String)
	 */
    @Override
	public ProcessDefinition findProcessDefinitionByPid(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        ProcessDefinition processDefinition = findProcessDefinition(processDefinitionId);
        return processDefinition;
    }

    /* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessDefinitionService#findProcessDefinition(java.lang.String)
	 */
    @Override
	public ProcessDefinition findProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }

    /* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessDefinitionService#deployFromClasspath(java.lang.String, java.lang.String)
	 */
    @Override
	public void deployFromClasspath(String exportDir, String... processKey) throws Exception {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        String[] processKeys = {"leave", "leave-dynamic-from", "leave-formkey", "dispatch"};
        for (String loopProcessKey : processKeys) {

      /*
       * 需要过滤指定流程
       */
            if (ArrayUtils.isNotEmpty(processKey)) {
                if (ArrayUtils.contains(processKey, loopProcessKey)) {
                    logger.debug("hit module of {}", (Object[]) processKey);
                    deploySingleProcess(resourceLoader, loopProcessKey, exportDir);
                } else {
                    logger.debug("module: {} not equals process key: {}, ignore and continue find next.", loopProcessKey, processKey);
                }
            } else {
        /*
         * 所有流程
         */
                deploySingleProcess(resourceLoader, loopProcessKey, exportDir);
            }
        }
    }

    /**
     * 部署单个流程定义
     *
     * @param resourceLoader {@link ResourceLoader}
     * @param processKey     模块名称
     * @throws IOException 找不到zip文件时
     */
    private void deploySingleProcess(ResourceLoader resourceLoader, String processKey, String exportDir) throws IOException {
        String classpathResourceUrl = "classpath:/deployments/" + processKey + ".bar";
        logger.debug("read workflow from: {}", classpathResourceUrl);
        Resource resource = resourceLoader.getResource(classpathResourceUrl);
        InputStream inputStream = resource.getInputStream();
        if (inputStream == null) {
            logger.warn("ignore deploy workflow module: {}", classpathResourceUrl);
        } else {
            logger.debug("finded workflow module: {}, deploy it!", classpathResourceUrl);
            ZipInputStream zis = new ZipInputStream(inputStream);
            Deployment deployment = repositoryService.createDeployment().addZipInputStream(zis).deploy();

            // export diagram
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
            for (ProcessDefinition processDefinition : list) {
                WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
            }
        }
    }

    /* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessDefinitionService#redeploy(java.lang.String, java.lang.String)
	 */
    @Override
	public void redeploy(String exportDir, String... processKey) throws Exception {
        this.deployFromClasspath(exportDir, processKey);
    }

    /* (non-Javadoc)
	 * @see com.gtzn.workflow.service.impl.WorkflowProcessDefinitionService#deployAllFromClasspath(java.lang.String)
	 */
    @Override
	public void deployAllFromClasspath(String exportDir) throws Exception {
        this.deployFromClasspath(exportDir);
    }

	@Override
	public List<Node> assignee() {
		List<Node> nodes = new ArrayList<>();
		UserQuery userQuery = identityService.createUserQuery();
		List<Group> groups = identityService.createGroupQuery().list();
		List<User> users;
		for (Group g : groups) {
			Node node = new Node();
			node.setId(g.getId());
			node.setName(g.getName());
			node.setNocheck(true);
			nodes.add(node);
			users = userQuery.memberOfGroup(g.getId()).list();
			for (User u : users) {
				Node node2 = new Node();
				node2.setId(u.getId());
				node2.setName(u.getFirstName());
				node2.setpId(g.getId());
				nodes.add(node2);
			}
		}
		return nodes;
	}

	@Override
	public List<Node> candidateUsers() {
		return assignee();
	}

	@Override
	public List<Node> candidateGroups() {
		List<Node> nodes = new ArrayList<>();
		UserQuery userQuery = identityService.createUserQuery();
		List<Group> groups = identityService.createGroupQuery().list();
		List<User> users;
		for (Group g : groups) {
			Node node = new Node();
			node.setId(g.getId());
			node.setName(g.getName());
			nodes.add(node);
			users = userQuery.memberOfGroup(g.getId()).list();
			for (User u : users) {
				Node node2 = new Node();
				node2.setId(u.getId());
				node2.setName(u.getFirstName());
				node2.setpId(g.getId());
				node2.setNocheck(true);
				nodes.add(node2);
			}
		}
		return nodes;
	}

}

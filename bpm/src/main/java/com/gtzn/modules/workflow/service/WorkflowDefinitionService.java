package com.gtzn.modules.workflow.service;

import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

import com.gtzn.modules.workflow.domain.Node;

public interface WorkflowDefinitionService {

	/**
	 * 根据流程实例ID查询流程定义对象{@link ProcessDefinition}
	 *
	 * @param processInstanceId 流程实例ID
	 * @return 流程定义对象{@link ProcessDefinition}
	 */
	ProcessDefinition findProcessDefinitionByPid(String processInstanceId);

	/**
	 * 根据流程定义ID查询流程定义对象{@link ProcessDefinition}
	 *
	 * @param processDefinitionId 流程定义对象ID
	 * @return 流程定义对象{@link ProcessDefinition}
	 */
	ProcessDefinition findProcessDefinition(String processDefinitionId);

	/**
	 * 部署classpath下面的流程定义
	 * <p>
	 * 从属性配置文件中获取属性<b>workflow.modules</b>扫描**deployments**
	 * </p>
	 * <p>
	 * 然后从每个**deployments/${module}**查找在属性配置文件中的属性**workflow.module.keys.${
	 * submodule}**
	 * <p>
	 * 配置实例：
	 * <p/>
	 * <pre>
	 * #workflow for deploy
	 * workflow.modules=budget,erp,oa
	 * workflow.module.keys.budget=budget
	 * workflow.module.keys.erp=acceptInsurance,billing,effectInsurance,endorsement,payment
	 * workflow.module.keys.oa=caruse,leave,officalstamp,officesupply,out,overtime
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param processKey 流程定义KEY
	 * @throws Exception
	 */
	void deployFromClasspath(String exportDir, String... processKey) throws Exception;

	/**
	 * 重新部署单个流程定义
	 *
	 * @param processKey 流程定义KEY
	 * @throws Exception
	 * @see #deployFromClasspath
	 */
	void redeploy(String exportDir, String... processKey) throws Exception;

	/**
	 * 重新部署所有流程定义，调用：{@link #deployFromClasspath()}完成功能
	 *
	 * @throws Exception
	 * @see #deployFromClasspath
	 */
	void deployAllFromClasspath(String exportDir) throws Exception;

	/**
	 * 授权到人，人员单选
	* @Title: assignee 
	* @Description: TODO
	* @param @return
	* @return List<Node>
	* @throws
	 */
	public List<Node> assignee();
	
	/**
	 * 候选人人，人员多选
	* @Title: assignee 
	* @Description: TODO
	* @param @return
	* @return List<Node>
	* @throws
	 */
	public List<Node> candidateUsers();
	
	/**
	 * 用户组，多选
	* @Title: candidateGroups 
	* @Description: TODO
	* @param @return
	* @return List<Node>
	* @throws
	 */
	public List<Node> candidateGroups();
	
}
package com.gtzn.modules.home;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.home.entity.PlatInformation;
import com.gtzn.modules.home.entity.Stati;
import com.gtzn.modules.home.service.HomeService;
import com.gtzn.modules.home.service.NoticeInfoService;
import com.gtzn.modules.home.service.PlatInformationService;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.workflow.domain.WFTask;
import com.gtzn.modules.workflow.service.WorkflowDefinitionService;
import com.gtzn.modules.workflow.service.WorkflowService;
import com.gtzn.web.util.WebUtil;

/**
 * Created by jiang on 2016/12/13.
 */

/**
 * 首页信息，代办任务，统计等信息 Controller
 * 
 * @author jiang
 * @version 20161213
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	WorkflowDefinitionService workflowDefinitionService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	HistoryService historyService;
	@Autowired
	WorkflowService workflowService;
	@Autowired
	FormService formService;
	@Autowired
	ManagementService managementService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private HomeService homeService;
	@Autowired
	private NoticeInfoService noticeInfoService;
	@Autowired
    private PlatInformationService platInformationService;
	protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();

	@RequestMapping(value = "/home")
	public ModelAndView homeIndex() {
		ModelAndView mav = new ModelAndView("/modules/home/home");
		List<Stati> dataList = homeService.findStaticCount(WebUtil.getUser());
		List<PlatInformation> formationList=platInformationService.queryPlatInformationList();
		mav.addObject("statiList", dataList);
		mav.addObject("statiFlag", dataList.size() > 0 ? 1 : 0);
		mav.addObject("formationList",formationList);
		return mav;
	}

	@RequestMapping(value = "/right")
	public ModelAndView homeIndex1() {
		ModelAndView mav = new ModelAndView("modules/home/right");
		List<Stati> dataList = homeService.findStaticCount(WebUtil.getUser());
		List<PlatInformation> formationList=platInformationService.queryPlatInformationList();
		mav.addObject("statiList", dataList);
		mav.addObject("statiFlag", dataList.size() > 0 ? 1 : 0);
		mav.addObject("formationList",formationList);
		return mav;
	}

	/**
	 * 获取用户首页统计模块列表
	 * 
	 * @Title: findStatiList
	 * @Description: TODO
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/findStatiList")
	@ResponseBody
	public Ajax findStatiList() {
		Ajax ajax = new Ajax();
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Stati> dataList = homeService
					.findStaticList(WebUtil.getUser());
			resultMap.put("statiList", dataList);
			ajax.setSuccess(true);
			ajax.setO(resultMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ajax.setSuccess(false);
			ajax.setMsg("获取统计数据模块异常，请联系管理员！");
		}
		return ajax;
	}

	/**
	 * 个人待办任务
	 */
	@RequestMapping(value = "/todo/task/list")
	public ModelAndView todoTaskList() {
		ModelAndView mav = new ModelAndView("/modules/home/todo-task-list");
		mav.addObject("tasks", this.todo(5));
		return mav;
	}

	private List<WFTask> todo(int max) {
		User user = WebUtil.getUser();
		List<WFTask> result = new ArrayList<WFTask>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 已经签收的任务
		List<Task> todoList = taskService.createTaskQuery()
				.taskAssignee(user.getNo()).active().orderByTaskCreateTime()
				.desc().listPage(0, max);
		for (Task task : todoList) {
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
			result.add(packageTaskInfo(sdf, task, processDefinition, "todo"));
		}
		// 等待签收的任务
		List<Task> toClaimList = taskService.createTaskQuery()
				.taskCandidateUser(user.getNo()).active()
				.orderByTaskCreateTime().desc().listPage(0, max);
		for (Task task : toClaimList) {
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
			result.add(packageTaskInfo(sdf, task, processDefinition, "claim"));
		}

		return result;
	}

	private ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE
				.get(processDefinitionId);
		if (processDefinition == null) {
			processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionId(processDefinitionId).singleResult();
			PROCESS_DEFINITION_CACHE
					.put(processDefinitionId, processDefinition);
		}
		return processDefinition;
	}

	private WFTask packageTaskInfo(SimpleDateFormat sdf, TaskInfo task,
			ProcessDefinition processDefinition, String status) {
		WFTask singleTask = new WFTask();
		singleTask.setTaskId(task.getId());
		singleTask.setTaskName(task.getName());
		singleTask.setCreateTime(sdf.format(task.getCreateTime()));
		singleTask.setProcessInstanceId(task.getProcessInstanceId());
		singleTask.setAssignee(task.getAssignee());
		singleTask.setProcessDefinitionName(processDefinition.getName());
		singleTask.setProcessDefinitionId(processDefinition.getId());
		process(singleTask, task);
		return singleTask;
	}

	private WFTask packageHistoricTaskInfo(SimpleDateFormat sdf,
			HistoricTaskInstance task, ProcessDefinition processDefinition,
			String status) {
		WFTask singleTask = new WFTask();
		singleTask.setTaskId(task.getId());
		singleTask.setTaskName(task.getName());
		singleTask.setCreateTime(sdf.format(task.getCreateTime()));
		singleTask.setProcessInstanceId(task.getProcessInstanceId());
		singleTask.setAssignee(task.getAssignee());
		singleTask.setProcessDefinitionName(processDefinition.getName());
		singleTask.setProcessDefinitionId(processDefinition.getId());
		singleTask.setEndTime(sdf.format(task.getEndTime()));
		// 业务表key
		process(singleTask, task);
		return singleTask;
	}

	private void process(WFTask singleTask, TaskInfo task) {
		// 业务表key
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		// 已结束流程
		if (null == processInstance) {
			HistoricProcessInstance historicProcessInstance = historyService
					.createHistoricProcessInstanceQuery()
					.processInstanceId(task.getProcessInstanceId())
					.singleResult();
			singleTask.setBusinessKey(historicProcessInstance.getBusinessKey());
			Map<String, Object> variables = historicProcessInstance
					.getProcessVariables();
			Object urlParam = variables.get("urlParam");
			if (null != urlParam) {
				if (StringUtils.contains(task.getFormKey(), "?")) {
					singleTask.setFormKey(task.getFormKey() + "&taskId="
							+ task.getId() + "&" + urlParam.toString());
				} else {
					singleTask.setFormKey(task.getFormKey() + "?taskId="
							+ task.getId() + "&" + urlParam.toString());
				}
			} else {
				singleTask.setFormKey(task.getFormKey());
			}
			// 启动流程时，附加的信息，便于区分任务之间的差别，如单号，立卷单位等
			Object taskDesp = variables.get("processDesp");
			if (null != taskDesp) {
				singleTask.setDescription(taskDesp.toString());
			}
		} else {
			singleTask.setBusinessKey(processInstance.getBusinessKey());
			Object o = runtimeService.getVariable(processInstance.getId(),
					"urlParam");
			if (null != o) {
				if (StringUtils.contains(task.getFormKey(), "?")) {
					singleTask.setFormKey(task.getFormKey() + "&taskId="
							+ task.getId() + "&" + o.toString());
				} else {
					singleTask.setFormKey(task.getFormKey() + "?taskId="
							+ task.getId() + "&" + o.toString());
				}
			} else {
				singleTask.setFormKey(task.getFormKey());
			}
			// 启动流程时，附加的信息，便于区分任务之间的差别，如单号，立卷单位等
			Object taskDesp = runtimeService.getVariable(
					processInstance.getId(), "processDesp");
			if (null != taskDesp) {
				singleTask.setDescription(taskDesp.toString());
			}
		}
	}

}

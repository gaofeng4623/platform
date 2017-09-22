package com.gtzn.modules.workflow.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.modules.workflow.service.WorkflowService;
import com.gtzn.web.util.WebUtil;


@Controller
@RequestMapping(value = "/wftest")
public class WorkFlowTest {
	
	@Autowired
	WorkflowService workflowService;

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
	
	
	@RequestMapping(value = "/start")
	@ResponseBody
    public Ajax start(HttpSession session) {
		Ajax ajax = new Ajax(true, "成功");
		User user = WebUtil.getUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		String tenantId = "gtzn";
		String procDefKey = "qingjia";
		String businessId = "业务表id";
		String userId = "ed3d4e6a58324202b5228dbdf9e1b4b4";//jiang 账户
		variables.put("userId", userId);
		
		Map<String, Object> urlParam = new HashMap<String, Object>();
		
		
		workflowService.startProcess(tenantId, procDefKey, businessId, userId, variables, urlParam);
		
		
        return ajax;
    }
	
	@RequestMapping(value = "/submit")
    public Ajax submit(String pass, String comment, String taskId, String processInstanceId, HttpSession session) {
		Ajax ajax = new Ajax(true, "成功");
		User user = WebUtil.getUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		
		if ("1".equals(pass)) {
			variables.put("pass", 1);
			variables.put("days", 6);
			//Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
			//Object o = runtimeService.getVariable(task.getProcessInstanceId(), "back");
			//variables.put("back", 0);
			workflowService.commitProcess(taskId, user.getId(), comment, variables);
			//ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			//Object o = runtimeService.getVariable(task.getExecutionId(), "back");
			//Map<String, Object> v = task.getProcessVariables();
		} else {
			variables.put("pass", 0);
			//variables.put("back", 0);
			workflowService.commitProcess(taskId, user.getId(), comment, variables);
		}
		
		
        return ajax;
    }
	@RequestMapping(value = "mypage")
    public ModelAndView test(String taskId, String processInstanceId) {
        ModelAndView mav = new ModelAndView("worktest/mypage");
        mav.addObject("taskId", taskId);
        
        /*processInstanceId = "210040";
        mav.addObject("processInstanceId", processInstanceId);
        ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		//流程未结束
		if (null != processInstance) {
			String activitiId = processInstance.getActivityId();
			//会签？？？
			List<Task> currentTask  = taskService.createTaskQuery()
					.processInstanceId(processInstance.getId()).list();
			
			int a = 0;
		} 
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", 1);
		List<WFNextTask> t1 = workflowService.findNextTask(taskId, variables);
		variables.put("pass", 0);
		List<WFNextTask> t2 = workflowService.findNextTask(taskId, variables);
		List<Task> t = workflowService.findCurrentTask(processInstanceId);
		int a = 0;*/
        return mav;
    }
	@RequestMapping(value = "/back")
    public Ajax back(String processInstanceId, HttpSession session) throws Exception {
		Ajax ajax = new Ajax(true, "成功");
		User user = WebUtil.getUser();
		workflowService.callBackProcess(processInstanceId, "edit", null);
        return ajax;
    }
	@RequestMapping(value = "/delete")
    public Ajax delete(String processInstanceId, HttpSession session) throws Exception {
		Ajax ajax = new Ajax(true, "成功");
		/*User user = UserUtil.getUserFromSession();
		processInstanceId = "207520";
		workflowService.deleteProcessInstance(processInstanceId);
		*/
        return ajax;
    }
	
	
	@RequestMapping(value = "/bizForm")
    public ModelAndView bizForm(String taskId, String processInstanceId) {
		ModelAndView mv = new ModelAndView("worktest/bizForm");
		
		
		
		return mv;
	}
}

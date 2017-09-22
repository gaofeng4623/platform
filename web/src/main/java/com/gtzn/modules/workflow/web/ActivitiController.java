package com.gtzn.modules.workflow.web;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.workflow.domain.Node;
import com.gtzn.modules.workflow.domain.WFComment;
import com.gtzn.modules.workflow.domain.WFProcessDefinition;
import com.gtzn.modules.workflow.domain.WFProcessInstance;
import com.gtzn.modules.workflow.domain.WFTask;
import com.gtzn.modules.workflow.service.WorkflowDefinitionService;
import com.gtzn.modules.workflow.service.WorkflowService;
import com.gtzn.web.util.WebUtil;


/**
 * 流程管理控制器
 *
 */
@Controller
@RequestMapping(value = "/workflow")
public class ActivitiController {	

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    WorkflowDefinitionService workflowProcessDefinitionService;
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
   // @Autowired
   // ISysOrgService sysOrgService;
    protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();

    @Autowired
    ProcessEngineFactoryBean processEngine;

    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;

    
    
    /**
     * 待办
     */
    @RequestMapping(value = "/toPersonalWorkTodoList")
    public ModelAndView toPersonalWorkTodoList(HttpSession session) throws Exception {
    	ModelAndView mav = new ModelAndView("/modules/home/todo-task-list");
        return mav;
    }
    @RequestMapping(value = "/loadPersonalWorkTodoList")
    @ResponseBody
    public List<WFTask> loadPersonalWorkTodoList(HttpSession session) throws Exception {
        return this.todo(session, 15);
    }
    
    /**
     * 已办
     */
    
    @RequestMapping(value = "/toPersonalHistoryList")
    public ModelAndView toPersonalHistoricList(HttpSession session) throws Exception {
    	ModelAndView mav = new ModelAndView("/modules/home/history-task-list");
    	List<Model> list = repositoryService.createModelQuery().list();
    	mav.addObject("modelList", list);
        return mav;
    }
    @RequestMapping(value = "/loadPersonalHistoryList")
    @ResponseBody
    public Pager loadPersonalHistoryList(int page, int rows, String key, String date1, String date2, HttpSession session) throws Exception {
    	User user = WebUtil.getUser();
    	List<WFTask> result = new ArrayList<WFTask>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	int start = (page -1) * rows;
    	HistoricTaskInstanceQuery query = historyService
				.createHistoricTaskInstanceQuery().finished()
				.taskAssignee(user.getNo());
    	if (StringUtils.isNotEmpty(key)) {
    		query.processDefinitionKey(key);
    	}
    //	query.processDefinitionName(processDefinitionName)
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    	if (StringUtils.isNotEmpty(date1) && StringUtils.isNotEmpty(date2)) {
    		query.taskCompletedAfter(sf.parse(date1));
    		Date date = sf.parse(date2);
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
    		cal.add(Calendar.DATE, 1);
    		query.taskCompletedBefore(cal.getTime());
    	} else if (StringUtils.isNotEmpty(date1)) {
    		query.taskCompletedAfter(sf.parse(date1));
    	} else if (StringUtils.isNotEmpty(date2)) {
    		Date date = sf.parse(date2);
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
    		cal.add(Calendar.DATE, 1);
    		query.taskCompletedBefore(cal.getTime());
    	}
		List<HistoricTaskInstance> historyTask = query.orderByHistoricTaskInstanceEndTime().desc().
				listPage(start, rows);
		long total = query.count();
		
    	for (HistoricTaskInstance task : historyTask) {
            String processDefinitionId = task.getProcessDefinitionId();
            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
            result.add(packageHistoricTaskInfo(sdf, task, processDefinition, "history"));
        }
    	 Pager<WFTask> pager = new Pager<>();
         pager.setRecords((int)total);
         pager.setList(result);
    	return pager;
    }
    /**
     * 待办任务--Portlet
     */
    @RequestMapping(value = "/todo/task/list")
    public ModelAndView todoList(HttpSession session) throws Exception {
    	ModelAndView mav = new ModelAndView("modules/workflow/todo-task-list");
        mav.addObject("tasks", this.todo(session, 5));
        return mav;
    }
    
    private List<WFTask> todo(HttpSession session, int max) {
    	 User user = WebUtil.getUser();
         List<WFTask> result = new ArrayList<WFTask>();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         // 等待签收的任务
         List<Task> toClaimList = taskService.createTaskQuery().taskCandidateUser(user.getNo()).active().orderByTaskCreateTime().desc().listPage(0, max);
         for (Task task : toClaimList) {
        	 String processDefinitionId = task.getProcessDefinitionId();
        	 ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
        	 result.add(packageTaskInfo(sdf, task, processDefinition, "claim"));
         }
         // 已经签收的任务
         List<Task> todoList = taskService.createTaskQuery().taskAssignee(user.getNo()).active().orderByTaskCreateTime().desc().listPage(0, max);
         for (Task task : todoList) {
             String processDefinitionId = task.getProcessDefinitionId();
             ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
             result.add(packageTaskInfo(sdf, task, processDefinition, "todo"));
         }

         return result;
    } 
    
    //已办任务
    @RequestMapping(value = "/history/task/list")
    public ModelAndView historyList(HttpSession session) throws Exception {
    	User user = WebUtil.getUser();
    	List<WFTask> result = new ArrayList<WFTask>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ModelAndView mav = new ModelAndView("/modules/workflow/history-task-list");
		List<HistoricTaskInstance> historyTask = historyService
				.createHistoricTaskInstanceQuery().finished()
				.taskAssignee(user.getNo())
				.orderByHistoricTaskInstanceEndTime().desc().listPage(0, 10);
    	for (HistoricTaskInstance task : historyTask) {
            String processDefinitionId = task.getProcessDefinitionId();
            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
            result.add(packageHistoricTaskInfo(sdf, task, processDefinition, "history"));
        }
    	mav.addObject("tasks", result);
        return mav;
    }
    
    
    private WFTask packageTaskInfo(SimpleDateFormat sdf, TaskInfo task, ProcessDefinition processDefinition ,String status) {
    	WFTask singleTask = new WFTask();
        singleTask.setTaskId(task.getId());
        singleTask.setTaskName(task.getName());
        singleTask.setCreateTime(sdf.format(task.getCreateTime()));
        singleTask.setProcessInstanceId(task.getProcessInstanceId());
        singleTask.setDescription(task.getDescription());
        singleTask.setAssignee(task.getAssignee());
        singleTask.setProcessDefinitionName(processDefinition.getName());
        singleTask.setProcessDefinitionId(processDefinition.getId());
        formkey(singleTask, task);
        return singleTask;
    }
  	private WFTask packageHistoricTaskInfo(SimpleDateFormat sdf, HistoricTaskInstance task, ProcessDefinition processDefinition ,String status) {
    	WFTask singleTask = new WFTask();
    	singleTask.setTaskId(task.getId());
        singleTask.setTaskName(task.getName());
        singleTask.setCreateTime(sdf.format(task.getCreateTime()));
        singleTask.setProcessInstanceId(task.getProcessInstanceId());
        singleTask.setDescription(task.getDescription());
        singleTask.setAssignee(task.getAssignee());
        singleTask.setProcessDefinitionName(processDefinition.getName());
        singleTask.setProcessDefinitionId(processDefinition.getId());
        singleTask.setEndTime(sdf.format(task.getEndTime()));
        
        
        //业务表key
        formkey(singleTask, task);
        return singleTask;
    }
  	private void formkey(WFTask singleTask, TaskInfo task) {
  	//业务表key
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        //已结束流程
        if (null == processInstance) {
        	HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        	singleTask.setBusinessKey(historicProcessInstance.getBusinessKey());
        	Object o = historicProcessInstance.getProcessVariables().get("urlParam");
        	if (null != o) {
        		if (StringUtils.contains(task.getFormKey(), "?")) {
        			singleTask.setFormKey(task.getFormKey() + "&taskId=" + task.getId() +"&" + o.toString());
        		} else {
        			singleTask.setFormKey(task.getFormKey() + "?taskId=" + task.getId() +"&" + o.toString());
        		}
        	} else {
        		singleTask.setFormKey(task.getFormKey());
        	}
        } else {
        	singleTask.setBusinessKey(processInstance.getBusinessKey());
        	Object o = runtimeService.getVariable(processInstance.getId(), "urlParam");
        	if (null != o) {
        		if (StringUtils.contains(task.getFormKey(), "?")) {
        			singleTask.setFormKey(task.getFormKey() + "&taskId=" + task.getId() +"&"+ o.toString());
        		} else {
        			singleTask.setFormKey(task.getFormKey() + "?taskId=" + task.getId() +"&"+ o.toString());
        		}
        	} else {
        		singleTask.setFormKey(task.getFormKey());
        	}
        }
  	}
  	
    /**
     * 流程审批意见
     */
    @RequestMapping(value = "/process-comments-list/{processInstanceId}")
    public ModelAndView processComments(@PathVariable("processInstanceId") String processInstanceId) {
    	ModelAndView mv = new ModelAndView("/modules/workflow/process-comments");
    	List<WFComment> comments = workflowService.findProcessComments(processInstanceId);
    	mv.addObject("comments", comments);
    	return mv;
    }
    @RequestMapping(value = "/process-comments/{processInstanceId}")
    @ResponseBody
    public List<WFComment> processCommentsJson(@PathVariable("processInstanceId") String processInstanceId) {
    	List<WFComment> comments = workflowService.findProcessComments(processInstanceId);
    	return comments;
    }
    
    /**
     * 签收任务
     */
    @RequestMapping(value = "/task/claim")
    @ResponseBody
    public Object claim(String taskId, HttpSession session,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {
        User user = WebUtil.getUser();
        Task task = workflowService.findTaskById(taskId);
        Map<String, Object> ajax = new HashMap<>();
        if (StringUtils.isNotEmpty(task.getAssignee())) {
        	ajax.put("success", false);
        	ajax.put("msg", "任务已被签收！");
        	return ajax;
        }
    	taskService.claim(taskId, user.getNo());
    	ajax.put("success", true);
    	ajax.put("msg", "任务已签收,请办理！");
        return ajax;
    }
    @RequestMapping(value = "/task/delegation")
    @ResponseBody
    public Ajax delegation(String taskId, String userId) {
    	Ajax ajax = new Ajax(true, "转授权成功！");
    	workflowService.delegation(taskId, userId);
    	return ajax;
    }
    @RequestMapping(value = "/task/finsh")
    @ResponseBody
    public Ajax finsh(String taskId) {
    	Ajax ajax = new Ajax(true, "转授权成功！");
    	//taskService.resolveTask(taskId);
    	taskService.complete(taskId);
    	return ajax;
    }
    
   /* @RequestMapping(value = "/task/claim/{id}")
    public String claim(@PathVariable("id") String taskId, HttpSession session,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {
        String userId = UserUtil.getUserFromSession(session).getId();
        taskService.claim(taskId, userId);
        redirectAttributes.addFlashAttribute("message", "任务已签收");
        return "redirect:/sysDesk/toDeskPage.do";
    }*/
    
    /**
     * 流程定义列表
     *
     * @return
     */
    @RequestMapping(value = "/process-list")
    public ModelAndView processList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("modules/workflow/process-list");
       // List<SysOrg> orgs = sysOrgService.findBranchOrg("0");
       // mav.addObject("orgs", orgs);
        return mav;
    }
    @RequestMapping(value = "/loadProcessList")
    @ResponseBody
    public Pager loadProcessList(int page, int rows, HttpServletRequest request) {

	    /*
	     *  ProcessDefinition（流程定义）， Deployment（流程部署）
	     */
    	List<WFProcessDefinition> result = new ArrayList<>();
    	ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionKey().asc().orderByProcessDefinitionVersion().desc();
       	List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage((page-1)*rows, rows);
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            WFProcessDefinition p = new WFProcessDefinition();
            p.setId(processDefinition.getId());
            p.setKey(processDefinition.getKey());
            p.setName(deployment.getName());
            p.setDeploymentId(deploymentId);
            p.setDeploymentTime(deployment.getDeploymentTime());
            p.setDiagramResourceName(processDefinition.getDiagramResourceName());
            p.setResourceName(processDefinition.getResourceName());
            p.setTenantId(processDefinition.getTenantId());
           // SysOrg org = sysOrgService.findSysOrgByCode(p.getTenantId());
			//p.setTenantName(org.getOrgName());
            p.setVersion(processDefinition.getVersion());
            result.add(p);
        }
        Long total = processDefinitionQuery.count();
        
        Pager<WFProcessDefinition> pager = new Pager<>();
        pager.setRecords(total.intValue());
        pager.setList(result);
        return pager;
    }
    /**
     * 提交启动流程
     */
   /* @RequestMapping(value = "start-process/{processDefinitionId}")
    public String submitStartFormAndStartProcessInstance(@PathVariable("processDefinitionId") String processDefinitionId,
                                                         @RequestParam(value = "processType", required = false) String processType,
                                                         RedirectAttributes redirectAttributes,
                                                         HttpServletRequest request) {
        Map<String, String> formProperties = new HashMap<String, String>();

        // 从request中读取参数然后转换
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            String key = entry.getKey();

            // fp_的意思是form paremeter
            if (StringUtils.defaultString(key).startsWith("fp_")) {
                formProperties.put(key.split("_")[1], entry.getValue()[0]);
            }
        }

        logger.debug("start form parameters: {}", formProperties);

        User user = UserUtil.getUserFromSession(request.getSession());
        // 用户未登录不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
        if (user == null || StringUtils.isBlank(user.getId())) {
            return "redirect:/login?timeout=true";
        }
        ProcessInstance processInstance = null;
        try {
            identityService.setAuthenticatedUserId(user.getId());
            processInstance = formService.submitStartFormData(processDefinitionId, formProperties);
            
            Map<String, Object> urlParam = new HashMap<>();
            List<String> param = new ArrayList<>();
    		urlParam.put("_", Math.random());
    		for (Entry<String, Object> enumEntry : urlParam.entrySet()) {
    			String key = enumEntry.getKey();
    			Object value = enumEntry.getValue();
    			if (null != value) {
    				param.add(key + "=" + value.toString());
    			}
    		}
    		runtimeService.setVariable(processInstance.getId(), "urlParam", StringUtils.join(param, "&"));
            
            logger.debug("start a processinstance: {}", processInstance);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        redirectAttributes.addFlashAttribute("message", "启动成功，流程ID：" + processInstance.getId());

        return "redirect:/modules/workflow/process-list.do?processType=" + processType;
    }*/
    
    @RequestMapping(value = "running")
    public ModelAndView running(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/modules/workflow/running-manage");
        return mav;
    }
    @RequestMapping(value = "loadRunning")
    @ResponseBody
    public Pager<WFProcessInstance> loadRunning(int page, int rows, HttpServletRequest request) {
    	
    	List<WFProcessInstance> result = new ArrayList<>();
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
		List<ProcessInstance> list = processInstanceQuery.listPage((page - 1) * rows, rows);
        for (ProcessInstance p : list) {
        	List<String> names = new ArrayList<>();
        	//获取当前流程的活动节点
        	List<Task> currentTask  = taskService.createTaskQuery()
					.processInstanceId(p.getProcessInstanceId()).list();
			for (Task t : currentTask) {
				names.add(t.getName());
			}
			
			WFProcessInstance pt = new WFProcessInstance();
			pt.setTenantId(p.getTenantId());
			//SysOrg org = sysOrgService.findSysOrgByCode(p.getTenantId());
			//pt.setTenantName(org.getOrgName());
			pt.setProcessDefinitionKey(p.getProcessDefinitionKey());
			pt.setProcessDefinitionVersion(p.getProcessDefinitionVersion());
			
			pt.setProcessDefinitionId(p.getProcessDefinitionId());
			pt.setProcessDefinitionName(p.getProcessDefinitionName());
			pt.setProcessInstanceId(p.getProcessInstanceId());
			pt.setCurrentTask(StringUtils.join(names, ","));
			
			result.add(pt);
        }
        Long total = processInstanceQuery.count();
        Pager<WFProcessInstance> pager = new Pager<>();
        pager.setRecords(total.intValue());
        pager.setList(result);
        return pager;
    }
    /**
     * 读取资源，通过部署ID
     *
     * @param processDefinitionId 流程定义
     * @param resourceType        资源类型(xml|image)
     * @throws Exception
     */
    @RequestMapping(value = "/resource/read")
    public void loadByDeployment(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceType") String resourceType,
                                 HttpServletResponse response) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 读取资源，通过流程ID
     *
     * @param resourceType      资源类型(xml|image)
     * @param processInstanceId 流程实例ID
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/resource/process-instance")
    public void loadByProcessInstance(@RequestParam("type") String resourceType, @RequestParam("pid") String processInstanceId, HttpServletResponse response)
            throws Exception {
        InputStream resourceAsStream = null;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId())
                .singleResult();

        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentId 流程部署ID
     */
    @RequestMapping(value = "/process/delete")
    @ResponseBody
    public Ajax delete(String deploymentId) {
    	Ajax ajax = null;
        try {
        	 repositoryService.deleteDeployment(deploymentId, true);
        	 ajax = new Ajax(true, "删除成功!");
        } catch (Exception e) {
       	 	ajax = new Ajax(false, "出现异常:" + e.getMessage());
       	 	logger.error("根据模型部署流程失败：modelId={}", deploymentId, e);
       }
       return ajax;
    }


    @RequestMapping(value = "/deploy")
    @ResponseBody
    public Ajax deploy(@RequestParam(value = "file", required = false) MultipartFile file, String tenantId, String name) {
    	Ajax ajax = null;
    	if (null == file) {
    		ajax = new Ajax(false, "文件不能为空！");
    		return ajax;
    	}
        String fileName = file.getOriginalFilename();

        try {
            InputStream fileInputStream = file.getInputStream();
            //Deployment deployment = null;

            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                repositoryService.createDeployment().tenantId(tenantId).name(name).addZipInputStream(zip).deploy();
            } else {
            	repositoryService.createDeployment().tenantId(tenantId).name(name).addInputStream(fileName, fileInputStream).deploy();
            }
            ajax = new Ajax(true, "部署成功："+fileName);
        } catch (Exception e) {
        	ajax = new Ajax(false, "出现异常:" + e.getMessage());
            logger.error("error on deploy process, because of file input stream", e);
        }

        return ajax;
    }

    @RequestMapping(value = "/process/convert-to-model/{processDefinitionId}")
    public String convertToModel(@PathVariable("processDefinitionId") String processDefinitionId)
            throws UnsupportedEncodingException, XMLStreamException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getName());
        modelData.setCategory(processDefinition.getDeploymentId());
        modelData.setTenantId(processDefinition.getTenantId());
        
        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);

        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

        return "redirect:/modules/workflow/model/list.do";
    }

    

    private ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
        if (processDefinition == null) {
            processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
        }
        return processDefinition;
    }


  
    @RequestMapping("/findUserRadio")
	@ResponseBody
	public List<Node> findUserRadio() {
		List<Node> nodes = new ArrayList<>();
		UserQuery userQuery = identityService.createUserQuery();
		List<Group> groups = identityService.createGroupQuery().list();
		List<org.activiti.engine.identity.User> users;
		for (Group g : groups) {
			Node node = new Node();
			node.setId(g.getId());
			node.setName(g.getName());
			node.setNocheck(true);
			nodes.add(node);
			users = userQuery.memberOfGroup(g.getId()).list();
			for (org.activiti.engine.identity.User u : users) {
				Node node2 = new Node();
				node2.setId(u.getId());
				node2.setName(u.getFirstName());
				node2.setpId(g.getId());
				nodes.add(node2);
			}
		}
		return nodes;
	}
    @RequestMapping("/findGrupRadioTree")
  	@ResponseBody
  	public List<Node> findGrupCheckTree() {
  		List<Node> nodes = new ArrayList<>();
  		UserQuery userQuery = identityService.createUserQuery();
  		List<Group> groups = identityService.createGroupQuery().list();
  		List<org.activiti.engine.identity.User> users;
  		for (Group g : groups) {
  			Node node = new Node();
  			node.setId(g.getId());
  			node.setName(g.getName());
  			node.setOpen(false);
  			nodes.add(node);
  			users = userQuery.memberOfGroup(g.getId()).list();
  			for (org.activiti.engine.identity.User u : users) {
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
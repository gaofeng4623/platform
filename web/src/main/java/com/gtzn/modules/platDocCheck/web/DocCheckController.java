package com.gtzn.modules.platDocCheck.web;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.base.entity.Constant;
import com.gtzn.modules.digital.service.WorkProcessService;
import com.gtzn.modules.digital.service.WorkflowProcessCoreService;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.sys.service.SystemService;
import com.gtzn.modules.workflow.domain.ActCallback;
import com.gtzn.modules.workflow.domain.ActModelDeploy;
import com.gtzn.modules.workflow.domain.WFTask;

/**
 * 工作进度Controller
 * @author wangzhao
 * @version 2017-04-11
 */
@Controller
@RequestMapping(value = "/platDocCheck")
public class DocCheckController extends BaseController {

	protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();
	
	@Autowired
	private WorkProcessService workProcessService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	protected WorkflowProcessCoreService workflowProcessCoreService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected HistoryService historyService;
	private Properties workflowdata;
	
	public DocCheckController() {
		try {
		    InputStream in = this.getClass().getClassLoader().getResourceAsStream("workflow.properties");
		    Properties p = new Properties();
		    p.load(in);
		    in.close();
		    setWorkflowdata(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Properties getWorkflowdata() {
		return workflowdata;
	}
	public void setWorkflowdata(Properties workflowdata) {
		this.workflowdata = workflowdata;
	}
	
	/**
	 * 页面跳转:工作进度主页
	 */
	@RequestMapping(value = "toDocCheckIndex")
	public String toDocCheckIndex(Model model) {
		return "modules/platDocCheck/docCheckIndex";
	}
	
	/**
	 * 页面跳转:工作进度列表页
	 */
	@RequestMapping(value = "toDocCheckListPage")
	public String toDocCheckListPage(Model model) {
		model.addAttribute("processDefinitionKeys", Constant.workflow_type);
		model.addAttribute("workState", Constant.work_state);
		
		String docCheckToDo = workflowdata.getProperty("docCheckToDo")+"liux/";
		String docCheckShowHistory = workflowdata.getProperty("docCheckShowHistory")+"liux/";
		String docCheckCancel = workflowdata.getProperty("docCheckCancel")+"liux/";
		model.addAttribute("docCheckToDo", docCheckToDo);
		model.addAttribute("docCheckShowHistory", docCheckShowHistory);
		model.addAttribute("docCheckCancel", docCheckCancel);
		
		return "modules/platDocCheck/docCheckList";
	}
	
	/**
	 * 页面跳转:工作进度信息页
	 */
	@RequestMapping(value = "toDocCheckInfoPage")
	public String toDocCheckInfoPage(WFTask wftask, Model model,@RequestParam("hrefURL") String hrefURL) {
		model.addAttribute("wftask", wftask);
		model.addAttribute("hrefURL", hrefURL);
		return "modules/platDocCheck/docCheckInfo";
	}
	
	
	/**
	 * 获取工作进度前几条记录列表
	 * @param wftask
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findDocCheckTopN")
	public Map<String, Object> findDocCheckTopN(WFTask wftask,@RequestParam("topN") int topN) {
		Map<String, Object> map = new HashMap<>();
		Pager<WFTask> pager = new Pager<WFTask>();
		pager.setPage(1);
		pager.setRows(topN);
		pager = getCheckListByPara(pager,wftask,null);
		List<WFTask> list = pager.getList();
		for(WFTask wfTask : list){
			wfTask.setCreateTime(wfTask.getCreateTime().substring(0, 10));
		}
		map.put("docCheckList", list);
		
		String docCheckToDo = workflowdata.getProperty("docCheckToDo")+"liux/";
		String docCheckShowHistory = workflowdata.getProperty("docCheckShowHistory")+"liux/";

		map.put("docCheckToDo", docCheckToDo);
		map.put("docCheckShowHistory", docCheckShowHistory);
		
		return map;
	}
	
	/**
	 * 工作进度待办已办列表
	 * @param pager
	 * @param WFTask
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getCheckListByPara")
	public Pager<WFTask> getCheckListByPara(Pager<WFTask> pager, WFTask wftask, Model model) {
		int pageNo = pager.getPage();
		int rows = pager.getRows();
		int beforePageNum = 0;
		int startPageNum = (pageNo-1)*rows+1;
		int endPageNum = pageNo*rows;
		User user = new User("0593456df1c74fa9bb27f368c87f08a9");
		List<WFTask> list = new ArrayList<>();
		if(null != wftask && null != wftask.getStatus() && (!"".equals(wftask.getStatus()))){
			String status = wftask.getStatus();
			if("0".equals(status)){
				list.addAll(getToDoList(wftask,user));
				list.addAll(findToDoWroklist(wftask,user));
			}else if("1".equals(status) || "2".equals(status) || "3".equals(status)){
				list.addAll(findDoneWorkList(wftask,user));
			}else if("4".equals(status)){
				list.addAll(getDoneList(wftask,user));
			}
		}else{
			list.addAll(getToDoList(wftask,user));
			list.addAll(findToDoWroklist(wftask,user));
			list.addAll(getDoneList(wftask,user));
			list.addAll(findDoneWorkList(wftask,user));
		}
		List<WFTask> showList = new ArrayList<WFTask>();
		for(WFTask task : list){
			 beforePageNum++;
			if(beforePageNum >= startPageNum && beforePageNum <= endPageNum){
				showList.add(task);
			}
		}
		pager.setList(showList);
        pager.setRecords(beforePageNum);
		return pager;
	}
	
	private List<WFTask> getToDoList(WFTask wftask, User user){
		List<WFTask> tasklist = new ArrayList<WFTask>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*TaskQuery taskquery = taskService.createTaskQuery().taskCandidateOrAssigned(user.getId()).active().orderByTaskCreateTime().desc();
		if(wftask.getProcessDefinitionKey()!=null && !"".equals(wftask.getProcessDefinitionKey())){
			taskquery.processDefinitionKey(wftask.getProcessDefinitionKey());
		}*/
		List<Task> todotasklist = Lists.newArrayList();
		if(wftask.getProcessDefinitionKey()!=null && !"".equals(wftask.getProcessDefinitionKey())){
			todotasklist = taskService.createNativeTaskQuery().
					sql("select task.* from act_ru_task task,act_re_procdef procdef,act_model_deploy modeldeploy"
							+ " where task.PROC_DEF_ID_ = procdef.ID_ and procdef.DEPLOYMENT_ID_ = modeldeploy.deployid "
							+ " and modeldeploy.modelkey = '"+wftask.getProcessDefinitionKey()+"' and task.ASSIGNEE_ = '"+user.getId()
							+"' order by task.CREATE_TIME_ DESC").list();
		}else{
			todotasklist = taskService.createNativeTaskQuery().
					sql("select task.* from act_ru_task task,act_re_procdef procdef,act_model_deploy modeldeploy"
							+ " where task.PROC_DEF_ID_ = procdef.ID_ and procdef.DEPLOYMENT_ID_ = modeldeploy.deployid "
							+ " and task.ASSIGNEE_ = '"+user.getId()+"' order by task.CREATE_TIME_ DESC" ).list();
		}
		
		//List<Task> todotasklist = taskquery.list();
		//List<Task> todotasklist = taskquery.listPage(pager.getStart(), pager.getRows());
		for (Task task : todotasklist) {
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
			tasklist.add(packageTaskInfo(sdf, task, processDefinition));
		}
		return tasklist;
	}
	
	private List<WFTask> getDoneList(WFTask wftask, User user){
		List<WFTask> tasklist = new ArrayList<WFTask>();			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*HistoricTaskInstanceQuery histaskquery = historyService.createHistoricTaskInstanceQuery().taskDeleteReason("completed").taskAssignee(user.getId()).finished();
    	if(wftask.getProcessDefinitionKey()!=null && !"".equals(wftask.getProcessDefinitionKey())){
    		histaskquery.processDefinitionKey(wftask.getProcessDefinitionKey());
		}
    	List<HistoricTaskInstance> todotasklist = histaskquery.list();*/
    	List<HistoricTaskInstance> todotasklist = Lists.newArrayList();
		if(wftask.getProcessDefinitionKey()!=null && !"".equals(wftask.getProcessDefinitionKey())){
			todotasklist = historyService.createNativeHistoricTaskInstanceQuery().
					sql("select task.* from act_hi_taskinst task,act_re_procdef procdef,act_model_deploy modeldeploy"
							+ " where task.PROC_DEF_ID_ = procdef.ID_ and procdef.DEPLOYMENT_ID_ = modeldeploy.deployid "
							+ " and modeldeploy.modelkey = '"+wftask.getProcessDefinitionKey()+"' and task.ASSIGNEE_ = '"+user.getId()
							+"' and task.DELETE_REASON_='completed' order by task.START_TIME_ DESC").list();
		}else{
			todotasklist = historyService.createNativeHistoricTaskInstanceQuery().
					sql("select task.* from act_hi_taskinst task,act_re_procdef procdef,act_model_deploy modeldeploy"
							+ " where task.PROC_DEF_ID_ = procdef.ID_ and procdef.DEPLOYMENT_ID_ = modeldeploy.deployid "
							+ " and task.ASSIGNEE_ = '"+user.getId()+"' and task.DELETE_REASON_='completed' order by task.START_TIME_ DESC" ).list();
		}
		//List<HistoricTaskInstance> todotasklist = histaskquery.listPage(pager.getStart(), pager.getRows());
		for (HistoricTaskInstance task : todotasklist) {
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
			tasklist.add(packageDoneTaskInfo(sdf, task, processDefinition));
		}
		return tasklist;
	}
	
	private List<WFTask> findToDoWroklist(WFTask wftask, User user){
		String applyType = wftask.getProcessDefinitionKey();
		List<WFTask> tasklist = new ArrayList<WFTask>();	
		if(StringUtils.isEmpty(wftask.getProcessDefinitionKey()) || Constant.workflow_type.containsKey(applyType)){
			if(StringUtils.isEmpty(wftask.getProcessDefinitionKey())){
				applyType = StringUtils.map2String(Constant.workflow_type);
			}
			List<WFTask> archiveUseTask = workProcessService.findToDoWroklist(applyType,user);//this.getWFTaskList(applyType, "0");
			tasklist.addAll(archiveUseTask);
		}
		return tasklist;
	}
	
	private List<WFTask> findDoneWorkList(WFTask wftask, User user){
		String applyType = wftask.getProcessDefinitionKey();
		List<WFTask> tasklist = new ArrayList<WFTask>();	
		if(StringUtils.isEmpty(wftask.getProcessDefinitionKey()) || Constant.workflow_type.containsKey(applyType)){
			if(StringUtils.isEmpty(wftask.getProcessDefinitionKey())){
				applyType = com.gtzn.common.utils.StringUtils.map2String(Constant.workflow_type);
			}
			String state = null == wftask.getStatus() ? "" : wftask.getStatus()+"";
			List<WFTask> archiveUseTask = workProcessService.findDoneWroklist(applyType,state,user);//this.getWFTaskList(applyType, "1,2,3");
			tasklist.addAll(archiveUseTask);
		}
		return tasklist;
	}
	
	/**
     * 流程定义信息放入缓存
     * @param processDefinitionId
     * @return
     */
	private ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
        if (processDefinition == null) {
            processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
        }
        return processDefinition;
    }
	
	private WFTask packageTaskInfo(SimpleDateFormat sdf, TaskInfo task, ProcessDefinition processDefinition) {
    	WFTask singleTask = new WFTask();
        singleTask.setTaskId(task.getId());
        singleTask.setTaskKey(task.getTaskDefinitionKey());
        singleTask.setTaskName(task.getName());
        singleTask.setStatus("5");
        singleTask.setCreateTime(sdf.format(task.getCreateTime()));
        singleTask.setProcessInstanceId(task.getProcessInstanceId());
        singleTask.setDescription(task.getDescription());
        singleTask.setAssignee(task.getAssignee());
        String deployid = processDefinition.getDeploymentId();
        ActModelDeploy modeldeploy = workflowProcessCoreService.getModelkey(deployid);
        singleTask.setProcessDefinitionName(modeldeploy.getModelname());
        singleTask.setProcessDefinitionKey(modeldeploy.getModelkey());
        //singleTask.setProcessDefinitionName(processDefinition.getName());
        singleTask.setProcessDefinitionId(processDefinition.getId());
        //singleTask.setProcessDefinitionKey(processDefinition.getKey());
        HistoricProcessInstance hisProIns = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if(hisProIns!=null){
        	String userid = hisProIns.getStartUserId();
        	String username = "";
        	if(userid!=null && !"".equals(userid)){
				User user = systemService.getUser(userid);
				if (null != user) {
					username = user.getName();
				}
        	}
        	singleTask.setAuthor(username);
        }
        taskformkey(singleTask, task,processDefinition);
        String formKey = singleTask.getFormKey();
        formKey = formKey.replaceAll( "/","ad132");
        formKey = formKey.replaceAll("[?]","w9a3");
        formKey = formKey.replaceAll("&","a0Y1");
        singleTask.setFormKey(formKey);
        return singleTask;
    }
	private WFTask packageDoneTaskInfo(SimpleDateFormat sdf, HistoricTaskInstance task, ProcessDefinition processDefinition) {
		WFTask singleTask = new WFTask();
		singleTask.setTaskId(task.getId());
		singleTask.setTaskKey(task.getTaskDefinitionKey());
		singleTask.setTaskName(task.getName());
		singleTask.setStatus("4");
		singleTask.setCreateTime(sdf.format(task.getCreateTime()));
		singleTask.setEndTime(sdf.format(task.getEndTime()));
		singleTask.setProcessInstanceId(task.getProcessInstanceId());
		singleTask.setDescription(task.getDescription());
		singleTask.setAssignee(task.getAssignee());
		String deployid = processDefinition.getDeploymentId();
        ActModelDeploy modeldeploy = workflowProcessCoreService.getModelkey(deployid);
        singleTask.setProcessDefinitionName(modeldeploy.getModelname());
        singleTask.setProcessDefinitionKey(modeldeploy.getModelkey());
		//singleTask.setProcessDefinitionName(processDefinition.getName());
		singleTask.setProcessDefinitionId(processDefinition.getId());
		//singleTask.setProcessDefinitionKey(processDefinition.getKey());
		HistoricProcessInstance hisProIns = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		if(hisProIns!=null){
			String userid = hisProIns.getStartUserId();
			String username = "";
			if(userid!=null && !"".equals(userid)){
				User user = systemService.getUser(userid);
				if (null != user) {
					username = user.getName();
				}
			}
			singleTask.setAuthor(username);
		}
		List<ActCallback> backlist = workflowProcessCoreService.getCallback(task.getId());
		if(backlist!=null && backlist.size()>0){
			singleTask.setCallback("1");
		}else{
			singleTask.setCallback("0");
		}
		taskformkey(singleTask, task,processDefinition);
		return singleTask;
	}
	/**
	 * 获取任务formkey
	 * @param singleTask
	 * @param task
	 */
	private void taskformkey(WFTask singleTask, TaskInfo task,ProcessDefinition processDefinition) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        //已结束流程
        if (null == processInstance) {
        	HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        	singleTask.setBusinessKey(historicProcessInstance.getBusinessKey());
        	if(!StringUtils.isEmpty(task.getFormKey())){
        		String formkey = task.getFormKey();
        		if(!formkey.startsWith("/")){
        			formkey = "/"+formkey;
        		}
        		if(formkey.endsWith("/")){
        			formkey = formkey.substring(0, formkey.length()-1);
        		}
        		if (StringUtils.contains(formkey, "?")) {
        			String[] forms = formkey.split("?");
	        		singleTask.setFormKey(forms[0]+"/toaudit?"+forms[1] + "&taskId=" + task.getId());
	        	} else {
	        		singleTask.setFormKey(formkey + "/toaudit?taskId=" + task.getId());
	        	}
        	}
        } else {
        	singleTask.setBusinessKey(processInstance.getBusinessKey());
        	if(!StringUtils.isEmpty(task.getFormKey())){
        		String formkey = task.getFormKey();
        		if(!formkey.startsWith("/")){
        			formkey = "/"+formkey;
        		}
        		if(formkey.endsWith("/")){
        			formkey = formkey.substring(0, formkey.length()-1);
        		}
        		if (StringUtils.contains(formkey, "?")) {
        			String[] forms = formkey.split("?");
	        		singleTask.setFormKey(forms[0]+"/toaudit?"+forms[1] + "&taskId=" + task.getId()+"&processInstanceId="+processInstance.getId()+"&processkey="+processDefinition.getKey());
	        	} else {
	        		singleTask.setFormKey(formkey + "/toaudit?taskId=" + task.getId()+"&processInstanceId="+processInstance.getId()+"&processkey="+processDefinition.getKey());
	        	}
        	}else{
        		String deployid = processDefinition.getDeploymentId();
        		ActModelDeploy modeldeploy = workflowProcessCoreService.getModelkey(deployid);
        		String modelkey = modeldeploy.getModelkey();
        		String formkey = workflowdata.getProperty(modelkey+"formkey");//业务表名;
        		if(!formkey.startsWith("/")){
        			formkey = "/"+formkey;
        		}
        		if(formkey.endsWith("/")){
        			formkey = formkey.substring(0, formkey.length()-1);
        		}
        		if (StringUtils.contains(formkey, "?")) {
        			String[] forms = formkey.split("?");
	        		singleTask.setFormKey(forms[0]+"/toaudit?"+forms[1] + "&taskId=" + task.getId()+"&processInstanceId="+processInstance.getId()+"&processkey="+modelkey);
	        	} else {
	        		singleTask.setFormKey(formkey + "/toaudit?taskId=" + task.getId()+"&processInstanceId="+processInstance.getId()+"&processkey="+modelkey);
	        	}
        	
        	}
        }
  	}

}
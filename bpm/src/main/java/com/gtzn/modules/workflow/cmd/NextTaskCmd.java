package com.gtzn.modules.workflow.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gtzn.modules.workflow.cmd.AbstractRuntimeCmd;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gtzn.modules.workflow.common.Constants;
import com.gtzn.modules.workflow.domain.WFNextTask;
/**
 * 根据流程变量及任务id获取下一环节信息暂时不包括下环节的属性信息。后续补充
 * @author llb
 *
 * 2014年12月18日
 */
public class NextTaskCmd<T> extends AbstractRuntimeCmd<T> {
	private static final Logger logger = LoggerFactory
			.getLogger(NextTaskCmd.class);
	private static final String ACTIVITI_EXCLUSIVE = "exclusiveGateway";
	private String USERTASK = "userTask";
	private String SUBPROCESS = "subProcess";
	private String STARTACT = "startEvent";
	private String ENDACT = "endEvent";
	private String startUserId = "${applyUserId}";
	private Map<String,Object> processVariables=null;
	private Map<String, Object> param = null;
	
	public NextTaskCmd(String taskId, Map<String, Object> param) {
		super(taskId);
		if (null == param) {
			param = new HashMap<>();
		}
		this.param = param;
	}
	private void initParam() {
		//参数中 不包含 pass参数，则默认为通过
		if (!this.param.containsKey(Constants.PASS)) {
			param.put(Constants.PASS, 1);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public T executeCmd(CommandContext commandContext) {
		initParam();
		List<WFNextTask> resultList = new ArrayList<WFNextTask>();
		logger.debug("返回下一流程环节列表：taskId={}", taskId );
		String activitiId =executionEntity.getActivityId();
		
		ActivityImpl activity = processDefinitionEntity.findActivity(activitiId);
		List<PvmTransition> outTransitions = activity.getOutgoingTransitions();
		for (PvmTransition pt : outTransitions) {
			PvmActivity pa = pt.getDestination();
			String actype = String.valueOf(pa.getProperty("type"));
			if (USERTASK.equals(actype)) {
				WFNextTask n= this.genNextTask(pa);
				resultList.add(n);
			} else {
				List<WFNextTask> list = findNextTask(pa);
				resultList.addAll(list);
			}
		}
		if (resultList == null ||resultList.size() <= 0) {
			WFNextTask n = new WFNextTask();
			n.setEnd(true);
			n.setTaskName("结束");
			resultList.add(n);
		}
		ConvertUserNameByLanguage(resultList);
		return (T) resultList;
	}
	private void ConvertUserNameByLanguage(List<WFNextTask> resultList) {
		HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(taskEntity.getProcessInstanceId()).singleResult();
		String applyUser = hi.getStartUserId();
		for (WFNextTask n : resultList) {
			String dealPerson = n.getTaskUser();
			if (startUserId.equals(String.valueOf(dealPerson))) {
				n.setTaskUser(applyUser);
			}
		}
		
	}
	private List<WFNextTask> findNextTask(PvmActivity pa) {
		List<WFNextTask> list = new ArrayList<WFNextTask>();
		String actype = String.valueOf(pa.getProperty("type"));
		if (!USERTASK.equals(actype)) {
			if (SUBPROCESS.equals(actype)) {
					//项目中暂没有子流程，暂时不实现
			} else if (ENDACT.equals(actype)) {
				// 结束环节
				if (pa.getParent() != null) {
					if (null != pa.getParent().getProperty("type")) {
						String parentType = String.valueOf(pa.getParent()
								.getProperty("type"));
						if (SUBPROCESS.equals(parentType)) {
							List<PvmTransition> outTransitions = ((ActivityImpl) pa
									.getParent()).getOutgoingTransitions();
							if (outTransitions.size() > 0) {
								for (PvmTransition tr : outTransitions) {

									PvmActivity ac = tr.getDestination();
									List<WFNextTask> temp = findNextTask(ac);
									list.addAll(temp);
								}
							}
						}
					} else {

					}
				}
			} else if (ACTIVITI_EXCLUSIVE.equals(pa.getProperty("type"))) {
				List<PvmTransition> outTransitions = pa.getOutgoingTransitions();
				//只有一条执行路径
				if (outTransitions.size() == 1) {
					PvmActivity ac = outTransitions.get(0).getDestination();
					List<WFNextTask> temp = findNextTask(ac);
					list.addAll(temp);
				}
				//多条执行路径，需要处理表达式
				else if (outTransitions.size() > 1) {
					for (PvmTransition tr : outTransitions) {
						String expression = String.valueOf(tr.getProperty("conditionText"));
						if (getResult(this.param, expression)) {
							List<WFNextTask> temp = findNextTask(tr.getDestination());
							list.addAll(temp);
						}
					}
				}
			} else {
				List<PvmTransition> outTransitions = pa
						.getOutgoingTransitions();
				if (outTransitions.size() > 0) {
					for (PvmTransition tr : outTransitions) {
						PvmActivity ac = tr.getDestination();
						List<WFNextTask> temp = findNextTask(ac);
						list.addAll(temp);
					}
				}
			}

		} else {
			list.add(this.genNextTask(pa));
		}
		return list;

	}
	private WFNextTask genNextTask(PvmActivity pa){
		String name = (String) pa.getProperty("name");
		WFNextTask n = new WFNextTask();
		n.setTaskName(name);
		TaskDefinition taskDefinition = (TaskDefinition) pa.getProperty("taskDefinition");
		n.setTaskDefinitionKey(taskDefinition.getKey());
		/*DefaultTaskFormHandler hanld = (DefaultTaskFormHandler) taskDefinition.getTaskFormHandler();
		List<FormPropertyHandler> kk = hanld.getFormPropertyHandlers();
		map.put("assignStatus", "1");
		for (FormPropertyHandler s : kk) {
			if("assignStatus".equals(s.getId())){
				map.put("assignStatus", s.getName());
				break;
			}
		}*/
		//解析表达式
		String nextUser = taskDefinition.getAssigneeExpression() == null ? ""
				: String.valueOf(taskDefinition.getAssigneeExpression());
		
		if (startUserId.equals(String.valueOf(nextUser))) {
			String procId = taskEntity.getProcessInstanceId();
			HistoricProcessInstance hi = historyService
					.createHistoricProcessInstanceQuery()
					.processInstanceId(procId).singleResult();
			if (hi != null) {
				nextUser = hi.getStartUserId();
			} else {
				nextUser = "";
			}
		}

		n.setTaskUser(nextUser);
		n.setTaskCandidateGroups(taskDefinition.getCandidateGroupIdExpressions());
		n.setTaskCandidateUsers(taskDefinition.getCandidateUserIdExpressions());
		return n;
	}
	
	//参数 + 表达式  返回执行结果 false true
	private boolean getResult(Map<String, Object> param, String expression) {
		ExpressionFactory factory = new ExpressionFactoryImpl();  
	    SimpleContext context = new SimpleContext();  
	    for (Iterator iterator = param.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (param.get(key) == null) {
				continue;
			}
			context.setVariable(key, factory.createValueExpression(param.get(key), param.get(key).getClass()));  
		}
	    ValueExpression e = factory.createValueExpression(context, expression, Boolean.class);  
	    return (Boolean) e.getValue(context);  
		
	}
}

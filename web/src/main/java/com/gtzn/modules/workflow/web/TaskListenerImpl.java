package com.gtzn.modules.workflow.web;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerImpl implements TaskListener {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	public Expression fieldName1;
	public Expression fieldName2;
	//public String fieldName2;
	//public String fieldName3;
	//public String fieldName4;
	//private Expression fieldName1;
	private static final long serialVersionUID = 1L;
	public void notify(DelegateTask delegateTask) {
	//	SysParaService sysParaService =  SpringContextUtil.getBean(SysParaService.class);
		// TODO Auto-generated method stub
		String a = delegateTask.getTaskDefinitionKey();
		delegateTask.setAssignee("asas");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>"+a);
		Map<String, Object>  n = delegateTask.getVariables();
		//System.out.println("任务监听器:" + fieldName1.getValue(delegateTask));
		//sysParaService.deleteSysPara("8dcd3717-b65e-46f6-ab46-b74c01d5c841");
		
		//SysParaService sysParaService2 =  SpringContextUtil.getBean(SysParaService.class);
		// TODO Auto-generated method stub
		//System.out.println("任务监听器:" + fieldName1.getValue(delegateTask));
		//sysParaService2.deleteSysPara("ed84f01b-d42b-4dd6-8bf5-6a506ce5bafd");
	}
	public Expression getFieldName1() {
		return fieldName1;
	}
	public void setFieldName1(Expression fieldName1) {
		this.fieldName1 = fieldName1;
	}
	public Expression getFieldName2() {
		return fieldName2;
	}
	public void setFieldName2(Expression fieldName2) {
		this.fieldName2 = fieldName2;
	}
	
	/*public String getFieldName2() {
		return fieldName2;
	}
	public void setFieldName2(String fieldName2) {
		this.fieldName2 = fieldName2;
	}*/
	
	/*public Expression getFieldName1() {
		return fieldName1;
	}
	public void setFieldName1(Expression fieldName1) {
		this.fieldName1 = fieldName1;
	}*/
	
}

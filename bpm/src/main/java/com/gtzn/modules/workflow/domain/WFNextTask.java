package com.gtzn.modules.workflow.domain;

import java.util.Set;

import org.activiti.engine.delegate.Expression;

public class WFNextTask {

	private String taskName;
	private String taskUser;
	private  Set<Expression> taskCandidateGroups;
	private  Set<Expression> taskCandidateUsers;
	private String taskDefinitionKey;
	
	
	private boolean end = false;
	
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskUser() {
		return taskUser;
	}
	public void setTaskUser(String taskUser) {
		this.taskUser = taskUser;
	}
	public Set<Expression> getTaskCandidateGroups() {
		return taskCandidateGroups;
	}
	public void setTaskCandidateGroups(Set<Expression> taskCandidateGroups) {
		this.taskCandidateGroups = taskCandidateGroups;
	}
	public Set<Expression> getTaskCandidateUsers() {
		return taskCandidateUsers;
	}
	public void setTaskCandidateUsers(Set<Expression> taskCandidateUsers) {
		this.taskCandidateUsers = taskCandidateUsers;
	}
	public boolean isEnd() {
		return end;
	}
	public void setEnd(boolean end) {
		this.end = end;
	}
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}
	
}

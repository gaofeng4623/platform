package com.gtzn.modules.workflow.domain;



public class WFAuthUser {

	private String instanceId;  //流程实例id
	private String authId;  //权限用户id(用户id或角色id)
	private String nodeId;  //节点id
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	@Override
	public String toString() {
		return "WFAuthUser [instanceId=" + instanceId + ", authId=" + authId
				+ ", nodeId=" + nodeId + "]";
	}
	
}

package com.gtzn.modules.workflow.dao;

import com.gtzn.modules.workflow.domain.WFAuthUser;

import java.util.List;

public interface IWFDao {

	/**
	 * 为指定流程节点指定权限用户
	 * @param authUser
	 */
	public void insertAuthUserInfo(WFAuthUser authUser);
	
	/**
	 * 根据流程实例和流程节点定义id获取流程节点配置权限用户信息
	 * @param businessId
	 * @param definitionKey
	 * @return
	 */
	public List<WFAuthUser> getAuthUserInfo(String businessId, String definitionKey);
}

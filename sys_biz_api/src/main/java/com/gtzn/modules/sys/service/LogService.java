package com.gtzn.modules.sys.service;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.sys.entity.Log;

public interface LogService {

	public abstract Pager<Log> findPage(Pager<Log> page, Log log);

	/**
	 * 保存操作日志
	* @Title: log 
	* @Description: TODO  
	* @param @param userId 操作记录
	* @param @param operationRecord 操作记录
	* @param @param ip ip地址
	* @param @param module 模块
	* @param @param operationType 操作类型     
	* @return void   
	* @throws
	 */
	public void log(String userId,String userName,String operationRecord,String ip,String module,String operationType);
	
	/**
	 * 保存操作日志
	* @Title: log 
	* @Description: TODO  
	* @param @param userId 操作记录
	* @param @param operationRecord 操作记录
	* @param @param ip ip地址
	* @param @param module 模块
	* @param @param operationType 操作类型     
	* @return void   
	* @throws
	 */
	public void log(String operationRecord,String module,String operationType);
	
}
/**
 * 
 */
package com.gtzn.modules.sys.service.impl;

import java.lang.reflect.Method;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.common.utils.DateUtils;
import com.gtzn.common.utils.Exceptions;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.modules.sys.dao.LogDao;
import com.gtzn.modules.sys.entity.Log;
import com.gtzn.modules.sys.service.LogService;

/**
 * 日志Service
 * @author gtzn
 * @version 2014-05-16
 */
@Service("logService")
@Transactional(readOnly = true)
public class LogServiceImpl extends CrudService<LogDao, Log> implements LogService {

	/* (non-Javadoc)
	 * @see com.gtzn.modules.sys.service.LogService#findPage(com.gtzn.common.persistence.Page, com.gtzn.modules.sys.entity.Log)
	 */
	@Override
	public Pager<Log> findPage(Pager<Log> page, Log log) {
		return super.findPage(page, log);
		
	}

	@Override
	public void log(String userId, String userName, String operationRecord, String ip, String module,
			String operationType) {
		
	}

	@Override
	public void log(String operationRecord, String module, String operationType) {
		
	}
	
	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{
		
		private Log log;
		private Object handler;
		private Exception ex;
		
		public SaveLogThread(Log log, Object handler, Exception ex){
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.ex = ex;
		}
		
		@Override
		public void run() {
			// 获取日志标题
			if (StringUtils.isBlank(log.getTitle())){
				String permission = "";
				if (handler instanceof HandlerMethod){
					Method m = ((HandlerMethod)handler).getMethod();
					/*RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
					permission = (rp != null ? StringUtils.join(rp.value(), ",") : "");*/
				}
				//log.setTitle(getMenuNamePath(log.getRequestUri(), permission));
			}
			// 如果有异常，设置异常信息
			log.setException(Exceptions.getStackTraceAsString(ex));
			// 如果无标题并无异常日志，则不保存信息
			if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getException())){
				return;
			}
			// 保存日志信息
			log.preInsert();
			//logDao.insert(log);
		}
	}
	
}

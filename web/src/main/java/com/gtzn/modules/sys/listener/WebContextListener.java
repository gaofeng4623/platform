package com.gtzn.modules.sys.listener;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import com.gtzn.web.util.WebUtil;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		WebApplicationContext context  = super.initWebApplicationContext(servletContext);
		//加载数据到缓存
		logger.info("正在加载数据到缓存...");
		WebUtil.loadRoleMenuToCache();
		logger.info("加载成功...");
		
		return context;
	}
}

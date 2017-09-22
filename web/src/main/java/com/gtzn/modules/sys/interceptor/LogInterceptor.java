/**
 * 
 */
package com.gtzn.modules.sys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gtzn.common.utils.JsonUtil;
import com.gtzn.common.web.Servlets;

/**
 * 日志拦截器
 * 
 * @author gtzn
 * @version 2014-8-19
 */
public class LogInterceptor implements HandlerInterceptor {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		if (!Servlets.isStaticFile(uri)) {
			logger.info("请求的URI: {}", request.getRequestURI());
			logger.info("请求的参数Map " + JsonUtil.toJson(request.getParameterMap()));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			logger.info("视图ViewName: " + modelAndView.getViewName());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		// 保存日志
		// LogUtils.saveLog(request, handler, ex, null);
		// 打印JVM信息。
		/*if (logger.isDebugEnabled()) {
			logger.debug("最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
					 Runtime.getRuntime().maxMemory() / 1024 / 1024,
					Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024,
					(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()
							+ Runtime.getRuntime().freeMemory()) / 1024 / 1024);
		}*/
		
	}

}

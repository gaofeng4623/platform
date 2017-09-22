package com.gtzn.common.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.gtzn.modules.base.entity.Constant;
import com.gtzn.web.util.LogUtils;
import com.gtzn.web.util.WebUtil;

public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

	 @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		 //点击退出时，存在登录已超时的情况，这时user为空，不在记录日志
		 if (null != WebUtil.getUser()) {
			 LogUtils.addLog("退出系统", Constant.ModuleType.LOGIN_OUT, Constant.OperationType.LOGOUT, Constant.DeviceType.PC);
		 }
		 return super.preHandle(request, response);
	 }
	
}

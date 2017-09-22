/**
 * 
 */
package com.gtzn.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.sys.entity.Log;
import com.gtzn.modules.sys.service.LogService;

/**
 * 日志Controller
 * @author gtzn
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "/sys/log")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	@RequiresPermissions("sys:log:view")
	@RequestMapping("list")
	public String list() {
		return "modules/sys/logList";
	}
	@ResponseBody
	@RequiresPermissions("sys:log:view")
	@RequestMapping("load")
	public Pager<Log> load(Log log, Pager<Log> page, HttpServletRequest request, HttpServletResponse response, Model model) {
        Pager<Log> pager = logService.findPage(page, log); 
		return pager;
	}
}

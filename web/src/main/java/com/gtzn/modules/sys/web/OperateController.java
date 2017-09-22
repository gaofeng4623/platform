/**
 * 
 */
package com.gtzn.modules.sys.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.sys.entity.Operate;
import com.gtzn.modules.sys.service.OperateService;

/**
 * 日志Controller
 * @author gtzn
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "/sys/operate")
public class OperateController extends BaseController {

	@Autowired
	private OperateService operateService;
	
	@RequiresPermissions("sys:operate:view")
	@RequestMapping("list")
	public String list() {
		return "modules/sys/operateList";
	}
	@ResponseBody
	@RequiresPermissions("sys:operate:view")
	@RequestMapping("load")
	public Pager<Operate> load(Operate log, Pager<Operate> page) {
        Pager<Operate> pager = operateService.findPage(page, log); 
		return pager;
	}
}

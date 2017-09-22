/**
 * 
 */
package com.gtzn.modules.sys.web;

import com.gtzn.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @info 系统权限控制
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-04-28
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/sys/limit")
public class LimitController extends BaseController {

	@RequiresPermissions("sys:limit:view")
	@RequestMapping(value = { "index" })
	public String limitIndex() {
		return "modules/sys/limitIndex";
	}


}

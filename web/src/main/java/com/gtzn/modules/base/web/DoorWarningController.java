/**
 * 
 */
package com.gtzn.modules.base.web;

import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.base.entity.DoorWarning;
import com.gtzn.modules.base.entity.Constant.DeviceType;
import com.gtzn.modules.base.entity.Constant.ModuleType;
import com.gtzn.modules.base.entity.Constant.OperationType;
import com.gtzn.modules.base.service.DoorWarningService;
import com.gtzn.web.util.LogUtils;
import com.gtzn.web.util.WebUtil;

/**
 * 门禁报警记录Controller
 * @author gtzn_Yang
 * @version 2016-12-19
 */
@Controller
@RequestMapping(value = "/base/doorWarning")
public class DoorWarningController extends BaseController {

	@Autowired
	private DoorWarningService doorWarningService;
	
	@RequiresPermissions("base:doorWarning:view")
	@RequestMapping(value = {"list"})
	public String list(DoorWarning doorWarning, Model model) {
		return "modules/base/doorWarning/doorWarningList";
	}
	
	/** 
	* @Title: load 
	* @Description: 分页查询
	* @param @param pager
	* @param @param doorWarning
	* @param @return
	* @return Pager<DoorWarning>
	* @throws 
	*/
	@RequiresPermissions("base:doorWarning:view")
	@RequestMapping(value = {"load"})
	@ResponseBody
	public Pager<DoorWarning> load(Pager<DoorWarning> pager, DoorWarning doorWarning) {
		Pager<DoorWarning> page = doorWarningService.findPage(pager, doorWarning, WebUtil.getUser()); 
		return page;
	}
	
	/** 
	* @Title: handleWarning 
	* @Description: 处理门禁报警记录
	* @param @param ids
	* @param @param doorWarning
	* @param @return
	* @return Ajax
	* @throws 
	*/
	@ResponseBody
	@RequiresPermissions("base:doorWarning:handle")
	@RequestMapping(value = "handleWarning")
	public Ajax handleWarning(@RequestParam("ids[]") String[] ids, String doorWarnings, DoorWarning doorWarning){
		boolean successFlag = true;
		String msg = "";
		try {
			doorWarning.setUpdateBy(WebUtil.getUser());
	    	doorWarning.setUpdateDate(new Date());
	    	doorWarningService.handleWarning(ids, doorWarning);
	    	LogUtils.addLog("处理门禁报警["+doorWarnings+"]", ModuleType.DOOR_MANAGE, OperationType.UPDATE, DeviceType.PC);
	    	msg = "处理成功！";
		} catch (Exception e) {
			e.printStackTrace();
			successFlag = false;
			msg = "处理失败！";
		}
		return new Ajax(successFlag, msg);
	}
	
}
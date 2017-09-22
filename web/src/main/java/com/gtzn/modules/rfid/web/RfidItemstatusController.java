/**
 * 
 */
package com.gtzn.modules.rfid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.web.BaseController;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.modules.rfid.entity.RfidItemstatus;
import com.gtzn.modules.rfid.service.RfidItemstatusService;

/**
 * 标签写入Controller
 * @author gtzn
 * @version 2017-04-06
 */
@Controller
@RequestMapping(value = "/rfid/rfidItemstatus")
public class RfidItemstatusController extends BaseController {

	@Autowired
	private RfidItemstatusService rfidItemstatusService;


	@RequestMapping(value = {"list"})
	public String list(RfidItemstatus rfidItemstatus, Model model) {
		return "modules/rfid/rfidItemstatus/rfidItemstatusList";
	}
	
	@RequestMapping(value = {"load"})
	@ResponseBody
	public Pager<RfidItemstatus> load(Pager<RfidItemstatus> pager, RfidItemstatus rfidItemstatus,HttpServletRequest request,HttpServletResponse response) {
		Pager<RfidItemstatus> page = rfidItemstatusService.findPage(pager, rfidItemstatus); 
		return page;
	}
	@RequestMapping(value = "addForm")
	public String addForm(RfidItemstatus rfidItemstatus, Model model) {
		model.addAttribute("rfidItemstatus", rfidItemstatus);
		return "modules/rfid/rfidItemstatus/rfidItemstatusAddForm";
	}

	@RequestMapping(value = "editForm")
	public String editForm(RfidItemstatus rfidItemstatus, Model model) {
		rfidItemstatus = rfidItemstatusService.selectRfidItemstatusInfo(rfidItemstatus);
		model.addAttribute("rfidItemstatus", rfidItemstatus);
		return "modules/rfid/rfidItemstatus/rfidItemstatusEditForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Ajax save(RfidItemstatus rfidItemstatus, Model model) {
		rfidItemstatusService.save(rfidItemstatus);
		return new Ajax(true,  "保存标签成功");
	}

	@RequestMapping(value = "save2")
	public String save2(RfidItemstatus rfidItemstatus, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rfidItemstatus)){
			return editForm(rfidItemstatus, model);
		}
		rfidItemstatusService.save(rfidItemstatus);
		addMessage(redirectAttributes, "保存标签成功");
		return "redirect:"+ "/rfid/rfidItemstatus/?repage";
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public Ajax delete(RfidItemstatus rfidItemstatus) {
		try {
			rfidItemstatusService.deleteRfidItemstatusInfo(rfidItemstatus);
			return new Ajax(true, "删除标签成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除标签异常");
		}
	}

	@RequestMapping(value = "multiDel")
	@ResponseBody
	public Ajax multiDel(String[] idList) {
		try {
			rfidItemstatusService.batchDelete(idList);
			return new Ajax(true, "删除标签成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除标签异常");
		}
	}

}
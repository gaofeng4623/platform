package com.gtzn.modules.base.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.base.entity.Door;
import com.gtzn.modules.base.entity.Constant.DeviceType;
import com.gtzn.modules.base.entity.Constant.ModuleType;
import com.gtzn.modules.base.entity.Constant.OperationType;
import com.gtzn.modules.base.service.DoorService;
import com.gtzn.web.util.LogUtils;
import com.gtzn.web.util.WebUtil;

/**
 * 门禁controller
 * @ClassName: DoorController
 * @Description: TODO
 * @author gtzn_lee
 * @date 2016年11月23日 下午4:51:30
 *
 */
@Controller
@RequestMapping(value = "/base/door")
public class DoorController extends BaseController {

	@Autowired
	private DoorService doorService;
	
	/**
	 * 跳转到门禁管理首页list
	 * @Title: list
	 * @Description: TODO
	 * @param 
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("base:door:view")
	@RequestMapping(value = {"list", ""})
	public String list(Door door, Model model) {
		return "modules/base/door/doorList";
	}
	
	/**
	 * 加载门禁管理列表数据
	 * @Title: load
	 * @Description: TODO
	 * @param 
	 * @return Pager<Log>
	 * @throws
	 */
	@ResponseBody
	@RequiresPermissions("base:door:view")
	@RequestMapping("load")
	public Pager<Door> load(Door door, Model model, Pager<Door> page, HttpServletRequest request, HttpServletResponse response) {
        Pager<Door> pager = doorService.findPage(page, door, WebUtil.getUser()); 
		return pager;
	}

	/**
	 * 跳转到门禁编辑页面
	 * @Title: form
	 * @Description: TODO
	 * @param 
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("base:door:view")
	@RequestMapping(value = "form")
	public String form(Door door, Model model) {
		if (StringUtils.isNotBlank(door.getDoorId())){
			door = doorService.get(door.getId());
		}
		Map<String, String> doorTypeMap = new HashMap<String, String>();  
		doorTypeMap.put("", "全部");  
		doorTypeMap.put("1", "大门");  
		doorTypeMap.put("0", "非大门"); 
		model.addAttribute("doorTypeMap", doorTypeMap);
		model.addAttribute("door", door);
		return "modules/base/door/doorForm";
	}
	
	/**
	 * 保存门禁信息
	 * @Title: save
	 * @Description: TODO
	 * @param 
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("base:door:edit")
	@RequestMapping(value = "save")
	public String save(Door door, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, door)){
			return form(door, model);
		}
		door.setBranchId(WebUtil.getUser().getBranchId());
		door.setCreateBy(WebUtil.getUser());
		door.setUpdateBy(WebUtil.getUser());
		doorService.save(door);
		if (StringUtils.isBlank(door.getId())) {
			LogUtils.addLog("新增门禁["+door.getDoorId()+"]", ModuleType.DOOR_MANAGE, OperationType.UPDATE, DeviceType.PC);
		} else {
			LogUtils.addLog("修改门禁["+door.getDoorId()+"]", ModuleType.DOOR_MANAGE, OperationType.SAVE, DeviceType.PC);
		}
		addMessage(redirectAttributes, "保存门禁'" + door.getNote() + "'成功");
		return "redirect:" +  "/base/door/list";
	}
	
	/**
	 * 删除单个门禁信息
	 * @Title: delete
	 * @Description: TODO
	 * @param 
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("base:door:edit")
	@RequestMapping(value = "delete")
	public String delete(Door door, RedirectAttributes redirectAttributes) {
		doorService.delete(door);
		LogUtils.addLog("删除门禁["+door.getDoorId()+"]", ModuleType.DOOR_MANAGE, OperationType.DELETE, DeviceType.PC);
		addMessage(redirectAttributes, "删除门禁成功");
		return "redirect:" +  "/base/door/list";
	}

	/**
	 * 删除多个门禁
	 * @Title: delDoors
	 * @Description: TODO
	 * @param 
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequiresPermissions("base:door:edit")
	@RequestMapping(value = "delDoors")
	public Ajax delDoors(String ids, String doorIds){
		String[] arr_id = ids.split(","); 
        if(arr_id.length > 0){
        	doorService.delDoors(arr_id);
        	LogUtils.addLog("删除门禁["+doorIds+"]", ModuleType.DOOR_MANAGE, OperationType.DELETE, DeviceType.PC);
        	return new Ajax(true,  "删除门禁成功！");
        }else{
        	return new Ajax(false,  "删除门禁失败！");
        }
	}
	
	/**
	 * 验证门禁编号是否有效
	 * @Title: checkDoorId
	 * @Description: TODO
	 * @param 
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequiresPermissions("base:door:edit")
	@RequestMapping(value = "checkDoorId")
	public String checkDoorId(String oldDoorId, String doorId) {
		if (doorId !=null && doorId.equals(oldDoorId)) {
			return "true";
		} else if (doorId !=null && doorService.getDoorByDoorId(doorId, WebUtil.getUser()) == null) {
			return "true";
		}
		return "false";
	}
	
	
}

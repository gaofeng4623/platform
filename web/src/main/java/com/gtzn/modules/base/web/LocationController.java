package com.gtzn.modules.base.web;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gtzn.modules.sys.utils.DictUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.base.entity.Constant.DeviceType;
import com.gtzn.modules.base.entity.Constant.ModuleType;
import com.gtzn.modules.base.entity.Constant.OperationType;
import com.gtzn.modules.base.entity.Location;
import com.gtzn.modules.base.service.LocationService;
import com.gtzn.web.util.LogUtils;
import com.gtzn.web.util.WebUtil;

/**
 * 位置管理controller
* @ClassName: LocationController 
* @Description: TODO
* @author guant
* @date 2016年12月19日 上午11:45:11 
*
 */
@Controller
@RequestMapping(value = "/base/location")
public class LocationController extends BaseController {

	@Autowired
	private LocationService locationService;
	
	/*@ModelAttribute
	public Location get(@RequestParam(required=false) String id) {
		Location entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = locationService.get(id);
		}
		if (entity == null){
			entity = new Location();
		}
		return entity;
	}*/
	
	/**
	 * 跳转到位置管理页面
	 * @Title: index
	 * @Description: TODO
	 * @param 
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("base:location:view")
	@RequestMapping(value = {"index"})
	public String index(Location location, Model model) {
		return "modules/base/location/locationIndex";
	}
	
	/**
	 * 位置查询列表页显示
	* @Title: list 
	* @Description: TODO
	* @param @param location
	* @param @param model
	* @param @return
	* @return String
	* @throws
	 */
	@RequiresPermissions("base:location:view")
	@RequestMapping(value = {"list"})
	public String list(Location location, Model model) {
		if(StringUtils.isBlank(location.getId())){
			location.setId("0");
		}
		location.setBranchId(WebUtil.getUser().getBranchId());
		LogUtils.addLog("加载位置信息列表", ModuleType.LOCATION, OperationType.FIND, DeviceType.PC);
		return "modules/base/location/locationList";
	}
	
	/**
	 * 查询位置列表分页信息
	* @Title: load 
	* @Description: TODO
	* @param @param pager
	* @param @param location
	* @param @param request
	* @param @param response
	* @param @return
	* @return Pager<Location>
	* @throws
	 */
	@RequiresPermissions("base:location:view")
	@RequestMapping(value = {"load"})
	@ResponseBody
	public Pager<Location> load(Pager<Location> pager, Location location,HttpServletRequest request,HttpServletResponse response) {
		location.setBranchId(WebUtil.getUser().getBranchId());
		Pager<Location> page = locationService.findPage(pager, location, WebUtil.getUser()); 
		LogUtils.addLog("位置信息列表分页查询", ModuleType.LOCATION, OperationType.FIND, DeviceType.PC);
		return page;
	}
	
	/**
	 * 跳转到位置信息编辑页
	* @Title: form 
	* @Description: TODO
	* @param @param location
	* @param @param model
	* @param @return
	* @return String
	* @throws
	 */
	@RequiresPermissions("base:location:view")
	@RequestMapping(value = "form")
	public String form(Location location, Model model) {
		if (StringUtils.isNotBlank(location.getId())){
			location = locationService.get(location.getId());
		}
		if (StringUtils.isNotBlank(location.getParentid())){
			Location parent = locationService.get(location.getParentid());
			model.addAttribute("parent", parent);
		}
		location.setBranchId(WebUtil.getUser().getBranchId());
		model.addAttribute("location", location);
		LogUtils.addLog("加载位置信息", ModuleType.LOCATION, OperationType.FIND, DeviceType.PC);
		return "modules/base/location/locationForm";
	}
	
	/**
	 * 
	* @Title: form 
	* @Description: TODO
	* @param @param location
	* @param @param model
	* @param @return
	* @return String
	* @throws
	 */
	@RequiresPermissions("base:location:view")
	@RequestMapping(value = "deptForm")
	public String deptForm(Location location, Model model) {
		location.setBranchId(WebUtil.getUser().getBranchId());
		if (StringUtils.isNotBlank(location.getId())){
			location = locationService.get(location.getId());
		}
		model.addAttribute("location", location);
		LogUtils.addLog("加载档案中心位置信息", ModuleType.LOCATION, OperationType.FIND, DeviceType.PC);
		return "modules/base/location/deptForm";
	}

	/**
	 * 保存位置信息
	* @Title: save 
	* @Description: TODO
	* @param @param location
	* @param @param model
	* @param @param redirectAttributes
	* @param @return
	* @return Object
	* @throws
	 */
	@RequiresPermissions("base:location:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(Location location, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, location)){
			return form(location, model);
		}
		location.setBranchId(WebUtil.getUser().getBranchId());
		locationService.save(location);
		LogUtils.addLog("保存位置信息"+location.getLocationName(), ModuleType.LOCATION, OperationType.SAVE, DeviceType.PC);
		addMessage(redirectAttributes, "保存位置成功");
		//return "redirect:"+ "/base/location/list?id="+location.getParentid();
		return new Ajax(true,"保存位置"+location.getLocationName()+"成功");
	}
	
	/**
	 * 保存档案中心根节点
	* @Title: saveDept 
	* @Description: TODO
	* @param @param location
	* @param @param model
	* @param @return
	* @return Object
	* @throws
	 */
	@RequiresPermissions("base:location:edit")
	@RequestMapping(value = "saveDept")
	@ResponseBody
	public Object saveDept(Location location, Model model) {
		location.setBranchId(WebUtil.getUser().getBranchId());
		locationService.saveDept(location);
		LogUtils.addLog("保存档案中心位置信息"+location.getLocationName(), ModuleType.LOCATION, OperationType.SAVE, DeviceType.PC);
		return new Ajax(true,  "保存档案中心位置"+location.getLocationName()+"成功");
	}
	
	/**
	 * 删除位置
	* @Title: delete 
	* @Description: TODO
	* @param @param location
	* @param @param redirectAttributes
	* @param @return
	* @return String
	* @throws
	 */
	@RequiresPermissions("base:location:edit")
	@RequestMapping(value = "delete")
	public String delete(Location location, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(location.getId())){
			List<Location> list = locationService.findChildDataList(location,WebUtil.getUser());
			long count = locationService.getArchiveCountByLocaton(location);
			if(list != null && list.size() > 0){
				addMessage(redirectAttributes, "该位置下有孩子位置，请先删除子位置后再删除该位置！");
			}else if(count > 0){
				addMessage(redirectAttributes, "该位置已被档案占用，不可删除该位置！");
			}else{
				locationService.delete(location);
				addMessage(redirectAttributes, "删除位置成功");
				LogUtils.addLog("删除位置信息"+location.getLocationName(), ModuleType.LOCATION, OperationType.DELETE, DeviceType.PC);
			}
		}else{
			addMessage(redirectAttributes, "请选择位置再进行删除操作！");
		}
		return "redirect:"+ "/base/location/list?id="+location.getParentid();
	}
	
	/**
	 * 获取机构JSON数据。
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String id,HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Location location = new Location();
		location.setBranchId(WebUtil.getUser().getBranchId());
		List<Location> list = locationService.findAllList(location, WebUtil.getUser());
		for (int i=0; i<list.size(); i++){
			Location e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentid());
			map.put("name", e.getLocationName());
			map.put("locationType", e.getLocationType());
			map.put("serialNoPath", e.getSerialNoPath());
			map.put("locationPath", e.getLocationPath());
			mapList.add(map);
		}
		return mapList;
	}
	
	/**
	 * 获取机构JSON数据。
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "getChildData")
	public List<Map<String, Object>> getChildData(@RequestParam(required=false) String id,HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Location location = new Location(id);
		location.setBranchId(WebUtil.getUser().getBranchId());
		List<Location> list = locationService.findChildDataList(location,WebUtil.getUser());
		for (int i=0; i<list.size(); i++){
			Location e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentid());
			map.put("name", e.getLocationName());
			map.put("locationType", e.getLocationType());
			map.put("isParent", e.isIsParent());
			map.put("serialNoPath", e.getSerialNoPath());
			map.put("locationPath", e.getLocationPath());
			mapList.add(map);
		}
		return mapList;
	}
	
	/**
	 * 打开批量插入对话框
	* @Title: openBatchAddForm 
	* @Description: TODO
	* @param @param location
	* @param @param model
	* @param @return
	* @return String
	* @throws
	 */
	@RequiresPermissions("base:location:edit")
	@RequestMapping(value = "openBatchAddForm")
	public String openBatchAddForm(Location location, Model model){
		if (StringUtils.isNotBlank(location.getId())){
			location = locationService.get(location.getId());
		}
		location.setBranchId(WebUtil.getUser().getBranchId());
		model.addAttribute("location", location);
		return "modules/base/location/batchAddForm";
	}
	
	/**
	 * 保存批量插入位置信息
	* @Title: saveBatchForm 
	* @Description: TODO
	* @param @param 
	* @param @param model
	* @param @return
	* @return Ajax
	* @throws
	 */
	@RequiresPermissions("base:location:edit")
	@RequestMapping(value = "saveBatchForm")
	@ResponseBody
	public Ajax saveBatchForm(String id, String locationType, String num ) {
		Location parentLocation = new Location(id);
		parentLocation.setBranchId(WebUtil.getUser().getBranchId());
		List<String> types = Arrays.asList(locationType.split(","));
		List<String> typeNames = Lists.newArrayList();
		for(String type : types){
			typeNames.add(DictUtils.getDictLabel(type, "location_type", ""));
		}
		List<String> nums = Arrays.asList(num.split(","));
		locationService.saveLocationList(parentLocation,types,nums,typeNames);
		LogUtils.addLog("保存批量位置信息成功", ModuleType.LOCATION, OperationType.SAVE, DeviceType.PC);
		return new Ajax(true,  "保存批量插入位置信息成功");
	}
	

	@RequestMapping(value = {"doLocationRfidWrite"})
	@ResponseBody
	public Ajax doLocationRfidWrite(String id, String type){
		Ajax ajax = new Ajax();
		try {
			String rfid = locationService.doLocationRfidWrite(id, type);
			Map<String,String> resultMap = new HashMap<String,String>();
			resultMap.put("rfid", rfid);
			ajax.setO(resultMap);
			ajax.setSuccess(true);
		} catch (Exception e) {
			ajax.setSuccess(false);
			ajax.setMsg("数据库标签信息写入异常，请联系管理员！");
		}
        return ajax;
	}
}
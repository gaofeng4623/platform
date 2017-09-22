/**
 * 
 */
package com.gtzn.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gtzn.common.config.Global;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.Collections3;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.sys.entity.Office;
import com.gtzn.modules.sys.entity.Role;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.sys.service.OfficeService;
import com.gtzn.modules.sys.service.SystemService;
import com.gtzn.web.util.WebUtil;

/**
 * 角色Controller
 * 
 * @author gtzn
 * @version 2013-12-05
 */
@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private OfficeService officeService;


	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = { "index" })
	public String roleIndex(Role role, Model model) {
		return "modules/sys/roleIndex";
	}

	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = { "list" })
	public String roleList(Role role, Model model) {
		model.addAttribute("role", role);
		return "modules/sys/roleList";
	}

	@ResponseBody
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = { "load" })
	public Pager<Role> load(Pager<Role> page, Role role) {
		return systemService.findRole(page, role, WebUtil.getUser());
	}

	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "form")
	public String form(Role role, Model model) {
		if (StringUtils.isNotBlank(role.getId())) {
			role = systemService.getRole(role.getId());
		}
		if (role.getOffice() == null) {
			role.setOffice(WebUtil.getUser().getOffice());
		}
		
		model.addAttribute("role", role);
		model.addAttribute("menuList", systemService.findMenu(WebUtil.getUser()));
		model.addAttribute("officeList", officeService.findList(WebUtil.getUser()));
		model.addAttribute("statiList", systemService.findStati());
		model.addAttribute("statiIds", systemService.findStatiIds(role.getId()));
		return "modules/sys/roleForm";
	}

	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "save")
	public String save(Role role, Model model, RedirectAttributes redirectAttributes) {
		if (!WebUtil.getUser().isAdmin() && role.getSysData().equals(Global.YES)) {
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + "/sys/role/list";
		}
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + "/sys/role/list";
		}
		if (!beanValidator(model, role)) {
			return form(role, model);
		}
		if (StringUtils.isEmpty(role.getOffice().getId())) {
			addMessage(model, "保存角色'" + role.getName() + "'失败, 必须分配归属机构");
			return form(role, model);
		}
		if (!"true".equals(checkName(role))) {
			addMessage(model, "保存角色'" + role.getName() + "'失败, 同级组织机构下角色名已存在");
			return form(role, model);
		}
		if (!"true".equals(checkEnname(role))) {
			addMessage(model, "保存角色'" + role.getName() + "'失败, 英文名已存在");
			return form(role, model);
		}
		role.setCreateBy(WebUtil.getUser());
		role.setUpdateBy(WebUtil.getUser());
		systemService.saveRole(role);
		redirectAttributes.addFlashAttribute("role", role);
		addMessage(redirectAttributes, "保存角色'" + role.getName() + "'成功");
		return "redirect:" + "/sys/role/list";
	}

	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "delete")
	public String delete(Role role, RedirectAttributes redirectAttributes) {
		if (!WebUtil.getUser().isAdmin() && role.getSysData().equals(Global.YES)) {
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + "/sys/role/list?repage";
		}
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + "/sys/role/list";
		}
		role = systemService.getRole(role.getId());
		if (null != role) {
			systemService.deleteRole(role);
			redirectAttributes.addFlashAttribute("role", role);
		}
		addMessage(redirectAttributes, "删除角色成功");
		return "redirect:" + "/sys/role/list";
	}

	/**
	 * 角色分配页面
	 * 
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assign")
	public String assign(Role role, Model model) {
		if (StringUtils.isNotBlank(role.getId())) {
			role = systemService.getRole(role.getId());
		}
		List<User> userList = systemService.findUser(WebUtil.getUser(), new User(new Role(role.getId())));
		model.addAttribute("userList", userList);
		model.addAttribute("role", role);
		return "modules/sys/roleAssign";
	}

	/**
	 * 角色分配 -- 打开角色分配对话框
	 * 
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "usertorole")
	public String selectUserToRole(Role role, Model model) {
		role = systemService.getRole(role.getId());
		List<User> userList = systemService.findUser(WebUtil.getUser(), new User(new Role(role.getId())));
		model.addAttribute("role", role);
		model.addAttribute("userList", userList);
		model.addAttribute("selectIds", Collections3.extractToString(userList, "id", ","));
		model.addAttribute("officeList", officeService.findList(WebUtil.getUser()));
		return "modules/sys/selectUserToRole";
	}

	/**
	 * 角色分配 -- 根据部门编号获取用户列表
	 * 
	 * @param officeId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@ResponseBody
	@RequestMapping(value = "users")
	public List<Map<String, Object>> users(String officeId, String type, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		User user = new User();
		if ("1".equals(type) && StringUtils.isNotBlank(type)) {// 公司
			user.setCompany(new Office(officeId));
		} else if ("2".equals(type) && StringUtils.isNotBlank(type)) {// 部门
			user.setOffice(new Office(officeId));
		} else {
			user.setCompany(new Office());
			user.setOffice(new Office());
		}
		// 有修改，注意测试
		Pager<User> page = systemService.findUser(new Pager<User>(), WebUtil.getUser(), user);
		for (User e : page.getList()) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", 0);
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 角色分配 -- 从角色中移除用户
	 * 
	 * @param userId
	 * @param roleId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "outrole")
	public String outrole(String userId, String roleId, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + "/sys/role/assign?id=" + roleId;
		}
		Role role = systemService.getRole(roleId);
		User user = systemService.getUser(userId);
		if (WebUtil.getUser().getId().equals(userId)) {
			addMessage(redirectAttributes, "无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！");
		} else {
			if (user.getRoleList().size() <= 1) {
				addMessage(redirectAttributes,
						"用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！这已经是该用户的唯一角色，不能移除。");
			} else {
				Boolean flag = systemService.outUserInRole(role, user);
				if (!flag) {
					addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！");
				} else {
					addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！");
				}
			}
		}
		return "redirect:" + "/sys/role/assign?id=" + role.getId();
	}

	/**
	 * 角色分配
	 * 
	 * @param role
	 * @param idsArr
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assignrole")
	public String assignRole(Role role, String[] idsArr, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + "/sys/role/assign?id=" + role.getId();
		}
		role = systemService.getRole(role.getId());
		StringBuilder msg = new StringBuilder();
		int newNum = 0;
		for (int i = 0; i < idsArr.length; i++) {
			User user = systemService.assignUserToRole(role, systemService.getUser(idsArr[i]));
			if (null != user) {
				msg.append("<br/>新增用户【" + user.getName() + "】到角色【" + role.getName() + "】！");
				newNum++;
			}
		}
		addMessage(redirectAttributes, "已成功分配 " + newNum + " 个用户" + msg);
		return "redirect:" + "/sys/role/assign?id=" + role.getId();
	}

	/**
	 * 验证角色名是否有效
	 * 
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(Role role) {
		if (role.getName() != null && systemService.getRoleByName(role) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 验证角色英文名是否有效
	 * 
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkEnname")
	public String checkEnname(Role role) {
		if (role.getEnname() != null && systemService.getRoleByEnname(role) == null) {
			return "true";
		}
		return "false";
	}

}

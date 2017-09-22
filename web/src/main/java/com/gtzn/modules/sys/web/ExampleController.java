package com.gtzn.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.sys.entity.Example;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.sys.service.ExampleService;
import com.gtzn.web.util.WebUtil;
/**
 * 例子
 * @author Administrator
 *
 */

@Controller
@RequestMapping(value = "/sys/example")
public class ExampleController extends BaseController {

	@Autowired
	ExampleService exampleService;
	
	@RequestMapping("list")
	@RequiresPermissions("sys:example:view")
	public String list() {
		return "modules/sys/exampleList";
	}
	
	
	
	@RequiresPermissions("sys:example:view")
	@RequestMapping("load")
	@ResponseBody
	public Pager<Example> load(Example example, Pager<Example> pager, HttpServletRequest request, HttpServletResponse response) {
		Pager<Example> page = exampleService.findPage(pager, example);
		return page;
	}
	
	@ModelAttribute
	public Example get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return exampleService.get(id);
		}else{
			return new Example();
		}
	}
	
	@RequiresPermissions("sys:example:view")
	@RequestMapping(value = "form")
	public String form(Example example, Model model) {
		User user = WebUtil.getUser();
		
		model.addAttribute("lists", user.getRoleList());
		model.addAttribute("example", example);
		return "modules/sys/exampleForm";
	}
	@RequestMapping(value = "formWin")
	public String formWin(Example example, Model model) {
		return "modules/sys/exampleFormWin";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:example:edit")
	@RequestMapping(value = "save")//@Valid 
	public Ajax save(Example example, Model model, RedirectAttributes redirectAttributes) {
		
		example.setCreateBy(WebUtil.getUser());
		example.setUpdateBy(WebUtil.getUser());
		exampleService.save(example);
		return new Ajax(true,  "保存例子'" + example.getName() + "'成功");
		//addMessage(redirectAttributes, "保存例子'" + example.getName() + "'成功");
		//return "redirect:" +  "/sys/example/list";
	}
	
	@RequiresPermissions("sys:example:delete")
	@RequestMapping(value = "delete")
	public String delete(Example example, RedirectAttributes redirectAttributes) {
		exampleService.delete(example);
		addMessage(redirectAttributes, "删除例子成功");
		return "redirect:" +  "/sys/dict/list";
	}
}

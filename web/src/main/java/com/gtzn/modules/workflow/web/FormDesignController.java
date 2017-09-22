package com.gtzn.modules.workflow.web;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.utils.JsonUtil;
import com.gtzn.modules.workflow.domain.Field;
import com.gtzn.modules.workflow.domain.Form;
import com.gtzn.modules.workflow.domain.Node;
import com.gtzn.modules.workflow.service.FormDesignService;

@Controller
@RequestMapping(value = "/workflow/formDesign/")
public class FormDesignController {

	@Autowired
	FormDesignService formDesignService;
	
	
	@RequestMapping(value = "formList")
	public ModelAndView formList() {
		ModelAndView mv = new ModelAndView("modules/workflow/formList");
		return mv;
	}
	@RequestMapping(value = "formDesign")
	public ModelAndView formDesign(String id) {
		Form form = formDesignService.getForm(id);
		ModelAndView mv = new ModelAndView("modules/workflow/formDesign");
		mv.addObject("form", form);
		
		return mv;
	}
	@RequestMapping(value = "loadFormList")
	@ResponseBody
	public List<Form> loadFormList(Form form) {
		List<Form> list = formDesignService.findForm(form);
		return list;
	}
	
	@RequestMapping(value = "saveForm")
	@ResponseBody
	public Ajax saveForm(Form form) {
		formDesignService.saveForm(form);
		return new Ajax(true, "保存成功");
	}
	
	@RequestMapping(value = "bindRole")
	public ModelAndView bindRole(String id) {
		Form form = formDesignService.getForm(id);
		ModelAndView mv = new ModelAndView("modules/workflow/bindRole");
		mv.addObject("form", form);
		return mv;
	}
	@RequestMapping(value = "formTree")
	@ResponseBody
	public List<Node> formTree(String id) {
		List<Node> nodes = new ArrayList<>();
		
		Form form = formDesignService.getForm(id);
		
		List<Field> list = JsonUtil.fromJson(form.getFields(), new TypeReference<List<Field>>() {});
		for (Field f : list) {
			Node node = new Node();
			node.setName(f.getLabel());
			node.setId(f.getCid());
			nodes.add(node);
		}
		
		return nodes;
	}
	
	
}

package com.gtzn.modules.platRegulation.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 行政监管Controller
 * @author chenyp
 *
 */
@Controller
@RequestMapping(value = "/regulation")
public class RegulationController {

	
	@ResponseBody
	@RequestMapping(value = "DeptData")
	public Map<String, Object> dataTmp(){
		Map<String, Object> map = new HashMap<>();
		
		String[] categories = {"企业职责", "工作领导", "人员配备", "管理方式", "各部门职责", "档案部门职责",
				"工作制度", "考核与评价", "决策与协调", "业务制度规范", "网络化服务系统", "档案管理系统", "数字资源建设", "数字档案馆"
				};
		map.put("categories", categories);
		
		List<Map<String,Object>> list = new ArrayList<>();
		
		Map<String, Object> listM = new HashMap<>();
		Object[] listData = {7, 9, 9, 4, 8, 3, 5, 6, 3, 8, 3, 9, 9, 4};
		listM.put("name", "得分");
		listM.put("data", listData);
		listM.put("pointPlacement", "on");
		list.add(listM);
		
		map.put("data", list);
		return map;
	}
	@ResponseBody
	@RequestMapping(value = "list")
	public String findList(){
		return "modules/platRegulation/regulationlist";
	}
}

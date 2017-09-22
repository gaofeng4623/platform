/**
 * 
 */
package com.gtzn.modules.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.DateUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.home.entity.PlatShowroom;
import com.gtzn.modules.home.service.PlatShowroomService;

/**
 * 展厅管理Controller
 * 
 * @author wzx
 * @version 2017-05-11
 */
@Controller
@RequestMapping(value = "/home/platShowroom")
public class PlatShowroomController extends BaseController {

	@Autowired
	private PlatShowroomService platShowroomService;

	@RequestMapping(value = { "list" })
	public String list(PlatShowroom platShowroom, Model model) {
		return "modules/home/platShowroom/platShowroomList";
	}

	@RequestMapping(value = { "showRoom" })
	public String showRoom(PlatShowroom platShowroom, Model model) {
		return "modules/platShowRoom/showRoom";
	}

	/**
	 * 按月统计展厅访问量----折线图
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getShowRoomByMonth")
	public Map<String, Object> getShowRoomByMonth(String category, Model model) {

//		Map<String, Object> map = new HashMap<>();
//
//		String[] categories = DateUtils.getLastMonths(12);
//		map.put("categories", categories);
//		Integer[] data ={96, 114, 189, 210, 153,96, 114, 189, 210, 153,100,150};
//		// int[] data = {96, 114, 189, 210, 153};
//		map.put("data", data);
		Map<String, Object> map = new HashMap<>();
		
		List<Map<String,Object>> dataList = new ArrayList<>();
		List<Map<String,Object>> dataTotal = new ArrayList<>();
		Map<String,Object> dataListItem1 = new HashMap<>();
		dataListItem1.put("name", "1月");
		dataListItem1.put("y", 1233);
		dataListItem1.put("drilldown", "1月");
		dataList.add(dataListItem1);
		
		Map<String,Object> datatotalItem1 = new HashMap<>();
		datatotalItem1.put("name", "1月");
		datatotalItem1.put("y", 11233);
		dataTotal.add(datatotalItem1);
		
		Map<String,Object> dataListItem2 = new HashMap<>();
		dataListItem2.put("name", "2月");
		dataListItem2.put("y", 1403);
		dataListItem2.put("drilldown", "2月");
		dataList.add(dataListItem2);
		
		Map<String,Object> datatotalItem2 = new HashMap<>();
		datatotalItem2.put("name", "2月");
		datatotalItem2.put("y", 12636);
		dataTotal.add(datatotalItem2);
		
		
		Map<String,Object> dataListItem3 = new HashMap<>();
		dataListItem3.put("name", "3月");
		dataListItem3.put("y", 1438);
		dataListItem3.put("drilldown", "3月");
		dataList.add(dataListItem3);
		
		Map<String,Object> datatotalItem3 = new HashMap<>();
		datatotalItem3.put("name", "3月");
		datatotalItem3.put("y", 14074);
		dataTotal.add(datatotalItem3);
		
		
		
		Map<String,Object> dataListItem4 = new HashMap<>();
		dataListItem4.put("name", "4月");
		dataListItem4.put("y", 1477);
		dataListItem4.put("drilldown", "4月");
		dataList.add(dataListItem4);
		
		Map<String,Object> datatotalItem4 = new HashMap<>();
		datatotalItem4.put("name", "4月");
		datatotalItem4.put("y", 15551);
		dataTotal.add(datatotalItem4);
		
		Map<String,Object> dataListItem5 = new HashMap<>();
		dataListItem5.put("name", "5月");
		dataListItem5.put("y", 1038);
		dataListItem5.put("drilldown", "5月");
		dataList.add(dataListItem5);
		
		Map<String,Object> datatotalItem5 = new HashMap<>();
		datatotalItem5.put("name", "5月");
		datatotalItem5.put("y", 16589);
		dataTotal.add(datatotalItem5);
		
		Map<String,Object> dataListItem6 = new HashMap<>();
		dataListItem6.put("name", "6月");
		dataListItem6.put("y", 1503);
		dataListItem6.put("drilldown", "6月");
		dataList.add(dataListItem6);
		
		Map<String,Object> datatotalItem6 = new HashMap<>();
		datatotalItem6.put("name", "6月");
		datatotalItem6.put("y", 18092);
		dataTotal.add(datatotalItem6);
		
		Map<String,Object> dataListItem7 = new HashMap<>();
		dataListItem7.put("name", "7月");
		dataListItem7.put("y", 1638);
		dataListItem7.put("drilldown", "7月");
		dataList.add(dataListItem7);
		
		Map<String,Object> datatotalItem7 = new HashMap<>();
		datatotalItem7.put("name", "7月");
		datatotalItem7.put("y", 19730);
		dataTotal.add(datatotalItem7);
		
		Map<String,Object> dataListItem8 = new HashMap<>();
		dataListItem8.put("name", "8月");
		dataListItem8.put("y", 1503);
		dataListItem8.put("drilldown", "8月");
		dataList.add(dataListItem8);
		
		Map<String,Object> datatotalItem8 = new HashMap<>();
		datatotalItem8.put("name", "8月");
		datatotalItem8.put("y", 21233);
		dataTotal.add(datatotalItem8);
		
		Map<String,Object> dataListItem9 = new HashMap<>();
		dataListItem9.put("name", "9月");
		dataListItem9.put("y", 1601);
		dataListItem9.put("drilldown", "9月");
		dataList.add(dataListItem9);
		
		Map<String,Object> datatotalItem9 = new HashMap<>();
		datatotalItem9.put("name", "9月");
		datatotalItem9.put("y", 22834);
		dataTotal.add(datatotalItem9);
		
		Map<String,Object> dataListItem10 = new HashMap<>();
		dataListItem10.put("name", "10月");
		dataListItem10.put("y", 1903);
		dataListItem10.put("drilldown", "10月");
		dataList.add(dataListItem10);
		
		Map<String,Object> datatotalItem10 = new HashMap<>();
		datatotalItem10.put("name", "10月");
		datatotalItem10.put("y", 24737);
		dataTotal.add(datatotalItem10);
		
		Map<String,Object> dataListItem11 = new HashMap<>();
		dataListItem11.put("name", "11月");
		dataListItem11.put("y", 1038);
		dataListItem11.put("drilldown", "11月");
		dataList.add(dataListItem11);
		
		Map<String,Object> datatotalItem11 = new HashMap<>();
		datatotalItem11.put("name", "11月");
		datatotalItem11.put("y", 25775);
		dataTotal.add(datatotalItem11);
		
		
		Map<String,Object> dataListItem12 = new HashMap<>();
		dataListItem12.put("name", "12月");
		dataListItem12.put("y", 1803);
		dataListItem12.put("drilldown", "12月");
		dataList.add(dataListItem12);
		
		Map<String,Object> datatotalItem12 = new HashMap<>();
		datatotalItem12.put("name", "12月");
		datatotalItem12.put("y", 27578);
		dataTotal.add(datatotalItem12);
		
		
		map.put("data", dataList);
		map.put("datatotal", dataTotal);

		List<Map<String,Object>> drilldownList = new ArrayList<>();
		
		Map<String,Object> drilldownListItem1 = new HashMap<>();
		Object[][] Item1Data = {{"1", 66}, {"2", 20}, {"3", 25}, {"4", 8}, {"5", 11}, {"6", 6}, {"7", 12}, {"8", 15}, {"9", 10}, {"10",12}, 
                {"11", 15}, {"12", 10}, {"13",8}, {"14", 35}, {"15",42}, {"16", 23}, {"17", 30}, {"18", 50}, {"19", 30}, {"20",45}, 
                {"21",42},  {"22",23}, {"23", 41}, {"24", 43}, {"25", 45}, {"26", 15}, {"27",46}, {"28",35}, {"29", 8}, {"30", 22}, {"31",42}};
		drilldownListItem1.put("name", "1月");
		drilldownListItem1.put("id", "1月");
		drilldownListItem1.put("data", Item1Data);
		drilldownList.add(drilldownListItem1);
		
		Map<String,Object> drilldownListItem2 = new HashMap<>();
		Object[][] Item2Data = {{"1", 46}, {"2", 40}, {"3", 45}, {"4",48}, {"5", 41}, {"6",66}, {"7", 42}, {"8", 55}, {"9", 40}, {"10", 42}, 
                {"11", 45}, {"12", 30}, {"13", 48}, {"14", 35}, {"15", 42}, {"16",53}, {"17", 34}, {"18",56}, {"19", 37}, {"20", 45}, 
                {"21", 42},  {"22", 53}, {"23", 51}, {"24", 53}, {"25", 50}, {"26", 25}, {"27", 46}, {"28", 35}};
		drilldownListItem2.put("name", "2月");
		drilldownListItem2.put("id", "2月");
		drilldownListItem2.put("data", Item2Data);
		drilldownList.add(drilldownListItem2);
		
		Map<String,Object> drilldownListItem3 = new HashMap<>();
		Object[][] Item3Data = {{"1", 46}, {"2", 40}, {"3", 45}, {"4", 48}, {"5", 41}, {"6", 66}, {"7", 42}, {"8", 55}, {"9",40}, {"10", 42}, 
                {"11", 45}, {"12", 30}, {"13",48}, {"14", 35}, {"15", 42}, {"16", 53}, {"17", 34}, {"18", 56}, {"19", 37}, {"20", 45}, 
                {"21", 42},  {"22", 53}, {"23", 51}, {"24", 53}, {"25", 50}, {"26", 25}, {"27", 46}, {"28", 35}, {"29", 56}, {"30", 66}, {"31", 58}};
		drilldownListItem3.put("name", "3月");
		drilldownListItem3.put("id", "3月");
		drilldownListItem3.put("data", Item3Data);
		drilldownList.add(drilldownListItem3);
		
		Map<String,Object> drilldownListItem4 = new HashMap<>();
		Object[][] Item4Data = {{"1", 46}, {"2", 40}, {"3", 45}, {"4", 48}, {"5", 41}, {"6", 66}, {"7", 42}, {"8", 55}, {"9", 40}, {"10", 42}, 
                {"11", 45}, {"12", 30}, {"13", 48}, {"14", 35}, {"15", 42}, {"16", 53}, {"17", 34}, {"18", 56}, {"19", 37}, {"20", 45}, 
                {"21", 42},  {"22", 53}, {"23", 51}, {"24", 53}, {"25", 50}, {"26", 25}, {"27", 46}, {"28", 35}, {"29", 56}, {"30", 66}};
		drilldownListItem4.put("name", "4月");
		drilldownListItem4.put("id", "4月");
		drilldownListItem4.put("data", Item4Data);
		drilldownList.add(drilldownListItem4);
		
		Map<String,Object> drilldownListItem5 = new HashMap<>();
		Object[][] Item5Data = {{"1", 46}, {"2", 40}, {"3", 45}, {"4", 48}, {"5", 41}, {"6", 66}, {"7", 42}, {"8", 55}, {"9", 40}, {"10", 42}, 
                {"11", 45}, {"12", 30}, {"13", 48}, {"14", 35}, {"15", 42}, {"16", 53}, {"17", 34}, {"18", 56}, {"19", 37}, {"20", 45}, 
                {"21", 42},  {"22", 53}, {"23", 51}, {"24", 53}, {"25", 50}, {"26", 25}, {"27", 46}, {"28", 35}, {"29", 56}, {"30", 66}, {"31", 80}};
		drilldownListItem5.put("name", "5月");
		drilldownListItem5.put("id", "5月");
		drilldownListItem5.put("data", Item5Data);
		drilldownList.add(drilldownListItem5);
		
		Map<String,Object> drilldownListItem6 = new HashMap<>();
		Object[][] Item6Data = {{"1", 46}, {"2", 40}, {"3", 45}, {"4", 48}, {"5", 41}, {"6", 66}, {"7", 42}, {"8", 55}, {"9", 40}, {"10", 42}, 
                {"11", 45}, {"12", 30}, {"13", 48}, {"14", 35}, {"15", 42}, {"16", 53}, {"17", 34}, {"18", 56}, {"19", 37}, {"20", 45}, 
                {"21", 42},  {"22", 53}, {"23", 51}, {"24", 53}, {"25", 50}, {"26", 25}, {"27", 46}, {"28", 35}, {"29", 56}, {"30", 66}};
		drilldownListItem6.put("name", "6月");
		drilldownListItem6.put("id", "6月");
		drilldownListItem6.put("data", Item6Data);
		drilldownList.add(drilldownListItem6);
		
		Map<String,Object> drilldownListItem7 = new HashMap<>();
		Object[][] Item7Data = {{"1", 46}, {"2", 40}, {"3", 45}, {"4", 48}, {"5", 41}, {"6", 66}, {"7", 42}, {"8", 55}, {"9", 40}, {"10", 42}, 
                {"11", 45}, {"12", 30}, {"13", 48}, {"14", 35}, {"15", 42}, {"16", 53}, {"17", 34}, {"18", 56}, {"19", 37}, {"20", 45}, 
                {"21", 42},  {"22", 53}, {"23", 51}, {"24", 53}, {"25", 50}, {"26", 25}, {"27", 46}, {"28", 35}, {"29", 56}, {"30", 66}, {"31", 80}};
		drilldownListItem7.put("name", "7月");
		drilldownListItem7.put("id", "7月");
		drilldownListItem7.put("data", Item7Data);
		drilldownList.add(drilldownListItem7);
		
		Map<String,Object> drilldownListItem8 = new HashMap<>();
		Object[][] Item8Data = {{"1", 46}, {"2", 40}, {"3", 45}, {"4", 48}, {"5", 41}, {"6", 66}, {"7", 42}, {"8", 55}, {"9", 40}, {"10", 42}, 
                {"11", 45}, {"12", 30}, {"13", 48}, {"14", 35}, {"15", 42}, {"16", 53}, {"17", 34}, {"18", 56}, {"19", 37}, {"20", 45}, 
                {"21", 42},  {"22", 53}, {"23", 51}, {"24", 53}, {"25", 50}, {"26", 25}, {"27", 46}, {"28", 35}, {"29", 56}, {"30", 66}, {"31", 80}};
		drilldownListItem8.put("name", "8月");
		drilldownListItem8.put("id", "8月");
		drilldownListItem8.put("data", Item8Data);
		drilldownList.add(drilldownListItem8);
		
		Map<String,Object> drilldownListItem9 = new HashMap<>();
		Object[][] Item9Data = {{"1", 46}, {"2", 40}, {"3", 45}, {"4", 48}, {"5", 41}, {"6", 66}, {"7", 42}, {"8", 55}, {"9", 40}, {"10", 42}, 
                {"11", 45}, {"12", 30}, {"13", 48}, {"14", 35}, {"15", 42}, {"16", 53}, {"17", 34}, {"18", 56}, {"19", 37}, {"20", 45}, 
                {"21", 42},  {"22", 53}, {"23", 51}, {"24", 53}, {"25", 50}, {"26", 25}, {"27", 46}, {"28", 35}, {"29", 56}, {"30", 66}};
		drilldownListItem9.put("name", "9月");
		drilldownListItem9.put("id", "9月");
		drilldownListItem9.put("data", Item9Data);
		drilldownList.add(drilldownListItem9);
		
		Map<String,Object> drilldownListItem10 = new HashMap<>();
		Object[][] Item10Data = {{"1", 46}, {"2", 40}, {"3", 45}, {"4", 48}, {"5", 41}, {"6", 66}, {"7", 42}, {"8", 55}, {"9", 40}, {"10", 42}, 
                {"11", 45}, {"12", 30}, {"13", 48}, {"14", 35}, {"15", 42}, {"16", 53}, {"17", 34}, {"18", 56}, {"19", 37}, {"20", 45}, 
                {"21", 42},  {"22", 53}, {"23", 51}, {"24", 53}, {"25", 50}, {"26", 25}, {"27", 46}, {"28", 35}, {"29", 56}, {"30", 66}, {"31", 80}};
		drilldownListItem10.put("name", "10月");
		drilldownListItem10.put("id", "10月");
		drilldownListItem10.put("data", Item10Data);
		drilldownList.add(drilldownListItem10);
		
		Map<String,Object> drilldownListItem11 = new HashMap<>();
		Object[][] Item11Data = {{"1", 46}, {"2", 40}, {"3", 45}, {"4", 48}, {"5", 41}, {"6", 66}, {"7", 42}, {"8", 55}, {"9", 40}, {"10", 42}, 
                {"11", 45}, {"12", 30}, {"13", 48}, {"14", 35}, {"15", 42}, {"16", 53}, {"17", 34}, {"18", 56}, {"19", 37}, {"20", 45}, 
                {"21", 42},  {"22", 53}, {"23", 51}, {"24", 53}, {"25", 50}, {"26", 25}, {"27", 46}, {"28", 35}, {"29", 56}, {"30", 66}};
		drilldownListItem11.put("name", "11月");
		drilldownListItem11.put("id", "11月");
		drilldownListItem11.put("data", Item11Data);
		drilldownList.add(drilldownListItem11);
		
		Map<String,Object> drilldownListItem12 = new HashMap<>();
		Object[][] Item12Data = {{"1", 46}, {"2", 40}, {"3", 45}, {"4", 48}, {"5", 41}, {"6", 66}, {"7", 42}, {"8", 55}, {"9", 40}, {"10", 42}, 
                {"11", 45}, {"12", 30}, {"13", 48}, {"14", 35}, {"15", 42}, {"16", 53}, {"17", 34}, {"18", 56}, {"19", 37}, {"20", 45}, 
                {"21", 42},  {"22", 53}, {"23", 51}, {"24", 53}, {"25", 50}, {"26", 25}, {"27", 46}, {"28", 35}, {"29", 56}, {"30", 66}, {"31", 80}};
		drilldownListItem12.put("name", "12月");
		drilldownListItem12.put("id", "12月");
		drilldownListItem12.put("data", Item12Data);
		drilldownList.add(drilldownListItem12);
		
		map.put("drilldown", drilldownList);
		return map;

	}
	
	/**
	 * 按月统计展台访问量----柱状图
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getShowRoomByPlat")
	public Map<String, Object> getShowRoomByPlat(String category, Model model) {

		Map<String, Object> map = new HashMap<>();
		List<String> categories = new ArrayList<String>();
		List<Integer> dataList = new ArrayList<Integer>();
		for(int i = 1 ;i < 12;i++){
			categories.add(i+"号展台");
			dataList.add((int)(Math.random()*1000)+1000);
		}
		//String[] categories = {"一号展台","二号展台","三号号展台","四号展台","五号展台","六号展台"};
		map.put("categories", categories);
		map.put("data", dataList);
		return map;
	}

	@RequestMapping(value = { "load" })
	@ResponseBody
	public Pager<PlatShowroom> load(Pager<PlatShowroom> pager, PlatShowroom platShowroom, HttpServletRequest request,
			HttpServletResponse response) {
		Pager<PlatShowroom> page = platShowroomService.findPage(pager, platShowroom);
		return page;
	}

	@RequestMapping(value = "addForm")
	public String addForm(PlatShowroom platShowroom, Model model) {
		model.addAttribute("platShowroom", platShowroom);
		return "modules/home/platShowroom/platShowroomAddForm";
	}

	@RequestMapping(value = "editForm")
	public String editForm(PlatShowroom platShowroom, Model model) {
		platShowroom = platShowroomService.selectPlatShowroomInfo(platShowroom);
		model.addAttribute("platShowroom", platShowroom);
		return "modules/home/platShowroom/platShowroomEditForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Ajax save(PlatShowroom platShowroom, Model model) {
		platShowroomService.save(platShowroom);
		return new Ajax(true, "保存人员分布成功");
	}

	@RequestMapping(value = "save2")
	public String save2(PlatShowroom platShowroom, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, platShowroom)) {
			return editForm(platShowroom, model);
		}
		platShowroomService.save(platShowroom);
		addMessage(redirectAttributes, "保存人员分布成功");
		return "redirect:" + "/home/platShowroom/?repage";
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public Ajax delete(PlatShowroom platShowroom) {
		try {
			platShowroomService.deletePlatShowroomInfo(platShowroom);
			return new Ajax(true, "删除人员分布成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除人员分布异常");
		}
	}

	@RequestMapping(value = "multiDel")
	@ResponseBody
	public Ajax multiDel(String[] idList) {
		try {
			platShowroomService.batchDelete(idList);
			return new Ajax(true, "删除人员分布成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除人员分布异常");
		}
	}

}
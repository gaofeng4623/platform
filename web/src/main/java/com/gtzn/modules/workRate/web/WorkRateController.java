package com.gtzn.modules.workRate.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.base.entity.Constant;
import com.gtzn.modules.digital.entity.WorkRate;
import com.gtzn.modules.digital.service.WorkRateService;
import com.gtzn.web.util.WebUtil;


/**
 * 工作监督Controller
 * @author chenyp
 * @version 2017-04-17
 */
@Controller
@RequestMapping(value = "/workRate")
public class WorkRateController extends BaseController {

	@Autowired
	private WorkRateService workRateService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	@RequestMapping(value = {"list"})
	public String list(WorkRate workRate,Model model) {
		model.addAttribute("work_type", Constant.work_type);
		model.addAttribute("workRate", workRate);
		return "modules/workRate/workRateList";
	}
	
	/**
	 * 获取前几条记录列表
	 */
	@ResponseBody
	@RequestMapping(value = "findWorkRateTopN")
	public List<WorkRate> findDocCheckTopN(WorkRate workRate,@RequestParam("topN") int topN) {
		Pager<WorkRate> pager = new Pager<WorkRate>();
		pager.setPage(1);
		pager.setRows(topN);
		pager = load(pager,workRate);
		return pager.getList();
	}
	
	@RequestMapping(value = {"load"})
	@ResponseBody
	public Pager<WorkRate> load(Pager<WorkRate> pager, WorkRate workRate) {
		int pageNo = pager.getPage();
		int rows = pager.getRows();
		int beforePageNum = 0;
		int startPageNum = (pageNo-1)*rows+1;
		int endPageNum = pageNo*rows;
		List<WorkRate> list = workRateService.findCollectList(workRate, WebUtil.getUser());
		if(StringUtils.isBlank(workRate.getWorkType()) || workRate.getWorkType().equals("利用")){
			list.addAll(workRateService.findUseList(workRate, WebUtil.getUser()));
		}
		list.addAll(workRateService.findAuthList(workRate, WebUtil.getUser()));
		if(StringUtils.isBlank(workRate.getWorkType()) || workRate.getWorkType().equals("移交")){
			list.addAll(workRateService.findTransferList(workRate, WebUtil.getUser()));
		}
		if(StringUtils.isBlank(workRate.getWorkType()) || workRate.getWorkType().equals("借阅")){
			list.addAll(workRateService.findborrowList(workRate, WebUtil.getUser()));
		}
		List<WorkRate> showList = new ArrayList<WorkRate>();
		for(WorkRate work : list){
			 beforePageNum++;
			if(beforePageNum >= startPageNum && beforePageNum <= endPageNum){
				work.setWorkTypeName(Constant.work_type.get(work.getWorkType()).toString());
				showList.add(work);
			}
		}
		pager.setList(showList);
        pager.setRecords(beforePageNum);
		return pager;
	}
	/**
	 * 单个信息显示页面1
	 */
	@RequestMapping(value = "infoForm")
	public String infoForm1(WorkRate workRate, Model model) {
		String returnUrl = "";
		if(null == workRate || null == workRate.getDate()){
			return "modules/workRate/workRateInfoForm";
		}
		workRate.setDeptName(sdf.format(workRate.getDate()));
		model.addAttribute("workRate", workRate);
		
		String workType = workRate.getWorkType();
		if("征购".equals(workType) || "捐赠".equals(workType) || "交换".equals(workType) || "寄存".equals(workType)){
			returnUrl = "modules/workRate/workRateInfoForm1";
		}else if("利用".equals(workType)){
			returnUrl = "modules/workRate/workRateInfoForm2";
		}else if("开放".equals(workType) || "销毁".equals(workType) || "濒危".equals(workType) || "划控".equals(workType) 
				|| "密级".equals(workType) || "延期".equals(workType) || "遗失".equals(workType)){
			returnUrl = "modules/workRate/workRateInfoForm3";
		}else if("移交".equals(workType) || "借阅".equals(workType)){
			returnUrl = "modules/workRate/workRateInfoForm4";
		}else if("借阅".equals(workType)){
			returnUrl = "modules/workRate/workRateInfoForm5";
		}
		return returnUrl;
	}

	/**
	 * 征集：征购、捐赠、交换、寄存
	 */
	@ResponseBody
	@RequestMapping(value = "getWorkRateInfoForm1")
	public Map<String,Object> getWorkRateInfoForm1(WorkRate workRate){
		Map<String,Object> map = new HashMap<>();
		map.put("workRate", workRate);
		try {
			Date date = sdf.parse(workRate.getDeptName());
			workRate.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> categories = workRateService.getWorkRate1Date(workRate);
		map.put("categories", categories);
		
		List<Map<String,Object>> returnList = new ArrayList<>();
		
		List<Map<String,Object>> unitList = workRateService.getWorkRateUnit(workRate);
		for(Map<String,Object> unitItem : unitList){

			workRate.setWorkUnit(unitItem.get("unit").toString());
			
			Map<String, Object> returnItem = new HashMap<>();
			List<Integer> returnItemList = workRateService.getWorkRate1(workRate);
			for(Integer i : returnItemList){
				if(i != 0){
					returnItem.put("data", returnItemList);
					returnItem.put("name", unitItem.get("workUnit").toString());
					returnList.add(returnItem);
					break;
				}
			}
		}
		map.put("returnList", returnList);
		return map;
	}

	/**
	 * 利用：利用
	 */
	@ResponseBody
	@RequestMapping(value = "getWorkRateInfoForm2")
	public Map<String,Object> getWorkRateInfoForm2(WorkRate workRate){
		Map<String,Object> map = new HashMap<>();
		map.put("workRate", workRate);
		try {
			Date date = sdf.parse(workRate.getDeptName());
			workRate.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Map<String,Object>> returnList = new ArrayList<>();

		Calendar c = Calendar.getInstance();
		c.setTime(workRate.getDate());
		c.add(Calendar.YEAR, -3);
		for(int i=0; i<3; i++){
			c.add(Calendar.YEAR, +1);
			workRate.setDate(c.getTime());
			Map<String, Object> returnItem = new HashMap<>();
			returnItem.put("name", c.get(Calendar.YEAR));
			List<Integer> returnItemList = workRateService.getWorkRate2(workRate);
			returnItem.put("data", returnItemList);
			returnList.add(returnItem);
		}
		map.put("returnList", returnList);
		return map;
	}

	/**
	 * 鉴定：开放、销毁、濒危、划控、密级、延期、遗失
	 */
	@ResponseBody
	@RequestMapping(value = "getWorkRateInfoForm3")
	public Map<String,Object> getWorkRateInfoForm3(WorkRate workRate){
		Map<String,Object> map = new HashMap<>();
		map.put("workRate", workRate);
		try {
			Date date = sdf.parse(workRate.getDeptName());
			workRate.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> categories = workRateService.getWorkRate3Date(workRate);
		map.put("categories", categories);
		List<Integer> returnList = workRateService.getWorkRate3(workRate);
		map.put("returnList", returnList);
		return map;
	}
	
	/**
	 * 移交：移交
	 */
	@ResponseBody
	@RequestMapping(value = "getWorkRateInfoForm4")
	public Map<String,Object> getWorkRateInfoForm4(WorkRate workRate){
		Map<String,Object> map = new HashMap<>();
		map.put("workRate", workRate);
		try {
			Date date = sdf.parse(workRate.getDeptName());
			workRate.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> categories = workRateService.getWorkRate3Date(workRate);
		map.put("categories", categories);
		List<Integer> returnList = workRateService.getWorkRate4(workRate);
		map.put("returnList", returnList);
		return map;
	}
	
	/**
	 * 借阅：借阅
	 */
	@ResponseBody
	@RequestMapping(value = "getWorkRateInfoForm5")
	public Map<String,Object> getWorkRateInfoForm5(WorkRate workRate){
		Map<String,Object> map = new HashMap<>();
		map.put("workRate", workRate);
		try {
			Date date = sdf.parse(workRate.getDeptName());
			workRate.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> categories = workRateService.getWorkRate3Date(workRate);
		map.put("categories", categories);
		List<Integer> returnList = workRateService.getWorkRate5(workRate);
		map.put("returnList", returnList);
		return map;
	}

	/**
	 * 图表页面
	 * @param workRate
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "chartsIndex")
	public String chartsIndex(WorkRate workRate, Model model) {
		return "modules/workRate/workRateChartsIndex";
	}
	
	/**
	 * 图表页面
	 * @param workRate
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "charts")
	public String editForm(WorkRate workRate, Model model) {
		return "modules/workRate/workRateCharts";
	}

	/**
	 * 各处统计
	 */
	@RequestMapping(value = "getWorkRateDataByMonth")
	@ResponseBody
	public Map<String, Object> getWorkRateDataByMonth(@RequestParam("workRateTypePara") String workRateTypePara){
		List<Object> categoriesList = new ArrayList<>();
		List<Object> dataList = new ArrayList<>();
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		
		if(null != workRateTypePara && "zhengJi".equals(workRateTypePara)){
			list = workRateService.findZhengJiDataByMonth();
		}else if(null != workRateTypePara && "liYong".equals(workRateTypePara)){
			list = workRateService.findLiYongDataByMonth();
		}else if(null != workRateTypePara && "jianDing".equals(workRateTypePara)){
			list = workRateService.findJianDingDataByMonth();
		}else if(null != workRateTypePara && "yiJiao".equals(workRateTypePara)){
			list = workRateService.findYiJiaoDataByMonth();
		}else if(null != workRateTypePara && "jieYue".equals(workRateTypePara)){
			list = workRateService.findJieYueDataByYear();
		}
		for(Map<String, Object> mapItem :list){
			categoriesList.add(mapItem.get("yearMonth"));
			dataList.add(mapItem.get("dataNum"));
		}
		map.put("categories", categoriesList);
		map.put("data", dataList);
		return map;
	}
	
}
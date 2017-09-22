package com.gtzn.modules.platDriveManage.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.gtzn.common.utils.DateUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.base.entity.Constant;
import com.gtzn.modules.digital.service.DocUseService;
import com.gtzn.modules.digital.service.DriveManageService;
import com.gtzn.modules.platform.service.CockpitManageService;
import com.gtzn.modules.security.service.SecurityStaticsService;
import com.gtzn.modules.sys.entity.Dict;

/**
 * 驾驶舱管理Controller
 * @author wangzhao
 * @version 2017-04-25
 */
@Controller
@RequestMapping(value = "/platDriveManage")
public class DriveManageController extends BaseController {

	@Autowired
	private DriveManageService driveManageService;
	@Autowired
	private CockpitManageService cockpitManageService;
	@Autowired
	private DocUseService docUseService;
	@Autowired
	private SecurityStaticsService securityStaticsService;
	
	/**
	 * 页面跳转-home页驾驶舱管理
	 */
	@RequestMapping(value = "toDriveManage")
	public String toDriveManage() {
		return "modules/platDriveManage/driveManage";
	}
	
	/**
	 * 驾驶舱管理---气泡图---借阅逾期
	 */
	@ResponseBody
	@RequestMapping(value = "getReadOverdue")
	public Map<String, Object> getReadOverdue(){
		Map<String, Object> map = new HashMap<>();
		
		Object[] category = {0,1,2,3,5};
		map.put("category", category);
		Object[] data = {0,0,2,238,4280};
		map.put("data", data);
		
		return map;
	}
	
	
	/**
	 * 驾驶舱管理---行业资讯---折线图
	 */
	@ResponseBody
	@RequestMapping(value = "getInformationStatics")
	public Map<String, Object> getInformationStatics(){
		Map<String, Object> map = new HashMap<>();
		
		String[] categories = DateUtils.getLastMonths(5);
		map.put("categories", categories);
		Integer[] data = cockpitManageService.getInformStaticsByMonth(categories);
		//int[] data = {96, 114, 189, 210, 153};
		map.put("data", data);
		return map;
	}

	/**
	 * 驾驶舱管理---年移交档案量---混合图
	 */
	@ResponseBody
	@RequestMapping(value = "getTransferInByYear")
	public Map<String, Object> getTransferInByYear(){
		Map<String, Object> map = new HashMap<>();
		
		String[] categories = DateUtils.getLastYears(5);
		map.put("categories", categories);
		Integer[] data = driveManageService.getTransferStaticsByYear(categories);
		//int[] data1 = {9696, 21014, 11189, 37610, 11153};
		map.put("columnData", data);
		map.put("splineData", data);
		return map;
	}
	
	/**
	 * 部门公告分类统计--饼图
	 */
	@ResponseBody
	@RequestMapping(value = "getDeptNotice")
	public Map<String, Object> getDeptNotice(){
		Map<String, Object> map = new HashMap<>();
		
		List<Map<String,Object>> dataList = new ArrayList<>();
		
		Map<String,Object> dataListItem1 = new HashMap<>();
		dataListItem1.put("name", "办公室");
		dataListItem1.put("y", 56.33);
		dataListItem1.put("drilldown", "办公室");
		dataList.add(dataListItem1);
		
		Map<String,Object> dataListItem2 = new HashMap<>();
		dataListItem2.put("name", "业务督导科");
		dataListItem2.put("y", 24.03);
		dataListItem2.put("drilldown", "业务督导科");
		dataList.add(dataListItem2);
		
		Map<String,Object> dataListItem3 = new HashMap<>();
		dataListItem3.put("name", "档案管理科");
		dataListItem3.put("y", 10.38);
		dataListItem3.put("drilldown", "档案管理科");
		dataList.add(dataListItem3);
		
		Map<String,Object> dataListItem4 = new HashMap<>();
		dataListItem4.put("name", "信息科");
		dataListItem4.put("y", 4.77);
		dataListItem4.put("drilldown", "信息科");
		dataList.add(dataListItem4);
		
		map.put("data", dataList);
		return map;
	}

	/**
	 * 驾驶舱管理---年鉴定档案量---柱状图
	 */
	@ResponseBody
	@RequestMapping(value = "getAuthStatics")
	public Map<String, Object> getAuthStatics(){
		Map<String, Object> map = new HashMap<>();
		String[] categories = DateUtils.getLastYears(5);
		map.put("categories", categories);
		List<Map<String,Object>> list = driveManageService.getAuthStaticsByYear(categories);
				
		/*List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> listMap1= new HashMap<>();
		Object[] listMap1Date = {472, 646, 631, 715, 321};
		listMap1.put("name", "濒危");
		listMap1.put("data", listMap1Date);
		list.add(listMap1);
		
		Map<String,Object> listMap2= new HashMap<>();
		Object[] listMap2Date = {620, 722, 745, 761, 800};
		listMap2.put("name", "划控");
		listMap2.put("data", listMap2Date);
		list.add(listMap2);
		
		Map<String,Object> listMap3= new HashMap<>();
		Object[] listMap3Date = {520, 782, 705, 721, 500};
		listMap3.put("name", "开放");
		listMap3.put("data", listMap3Date);
		list.add(listMap3);
		
		Map<String,Object> listMap4= new HashMap<>();
		Object[] listMap4Date = {520, 622, 545, 561, 400};
		listMap4.put("name", "密级");
		listMap4.put("data", listMap4Date);
		list.add(listMap4);
		
		Map<String,Object> listMap5= new HashMap<>();
		Object[] listMap5Date = {120, 122, 145, 161, 100};
		listMap5.put("name", "销毁");
		listMap5.put("data", listMap5Date);
		list.add(listMap5);
		
		Map<String,Object> listMap6= new HashMap<>();
		Object[] listMap6Date = {320, 522, 445, 461, 300};
		listMap6.put("name", "延期");
		listMap6.put("data", listMap6Date);
		list.add(listMap6);
		
		Map<String,Object> listMap7= new HashMap<>();
		Object[] listMap7Date = {20, 22, 45, 61, 40};
		listMap7.put("name", "遗失");
		listMap7.put("data", listMap7Date);
		list.add(listMap7);*/
		
		map.put("data", list);
		return map;
	}

	/**
	 * 驾驶舱管理---月征集档案量---面积图
	 */
	@ResponseBody
	@RequestMapping(value = "getDocInByMonth")
	public Map<String, Object> getDocInByMonth(){
		Map<String, Object> map = new HashMap<>();
		String[] categories = DateUtils.getLast12Months();
		map.put("categories", categories);
		List<Map<String,Object>> numListOfType = driveManageService.getCollectStaticsByMonth(categories);
		
		/*List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> listMap1= new HashMap<>();
		int[] data1 = {1004, 1287, 1447, 1376, 1255, 2144, 1009, 1950, 3871, 1524,1824,1824};
		listMap1.put("name", "捐赠");
		listMap1.put("data", data1);
		list.add(listMap1);
		Map<String,Object> listMap2= new HashMap<>();
		int[] data2 = {1643, 3092, 3478, 2915, 5385, 4055, 4205, 5044, 4393, 4935,3824,4824};
		listMap2.put("name", "寄存");
		listMap2.put("data", data2);
		list.add(listMap2);
		Map<String,Object> listMap3= new HashMap<>();
		int[] data3 = {1143, 3092, 2478, 2915, 3385, 1955, 2205, 2344, 2593, 2735,2824,2824};
		listMap3.put("name", "交换");
		listMap3.put("data", data3);
		list.add(listMap3);
		Map<String,Object> listMap4= new HashMap<>();
		int[] data4 = {2643, 3092, 4478, 5915, 7385, 7055, 6205, 5044, 5393, 6935,4824,5824};
		listMap4.put("name", "征购");
		listMap4.put("data", data4);
		list.add(listMap4);*/
		map.put("data", numListOfType);
		return map;
	}
	
	/**
	 * 驾驶舱管理---档案利用---折线图
	 */
	@ResponseBody
	@RequestMapping(value = "getDocUseByMonth")
	public Map<String, Object> getDocUseByMonth(){
		Map<String, Object> map = new HashMap<>();
		
		Object[] categories = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
		map.put("categories", categories);
		
		Object[] data1 = {48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2};
		map.put("data1", data1);
		Object[] data2 = {53.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 76.6, 62.3};
		map.put("data2", data2);
		
		return map;
	}
	

	/**
	 * 安全报警次数
	 */
	@ResponseBody
	@RequestMapping(value = "getDrive6")
	public Map<String, Object> getDrive6(){
		Map<String, Object> map = new HashMap<>();
		int count = securityStaticsService.getAlarmCount();
		map.put("data", count);
		return map;
	}
	
	/**
	 * 档案鉴定
	 */
	@ResponseBody
	@RequestMapping(value = "getDrive5")
	public Map<String, Object> getDrive5(){
		Map<String, Object> map = new HashMap<>();
		int count = driveManageService.getAuthCount();
		map.put("data", count);
		return map;
	}
	
	/**
	 * 档案征集
	 */
	@ResponseBody
	@RequestMapping(value = "getDrive4")
	public Map<String, Object> getDrive4(){
		Map<String, Object> map = new HashMap<>();
		int count = driveManageService.getCollectCount();
		map.put("data", count);
		return map;
	}
	
	/**
	 * 档案接收
	 */
	@ResponseBody
	@RequestMapping(value = "getDrive3")
	public Map<String, Object> getDrive3(){
		Map<String, Object> map = new HashMap<>();
		int count = driveManageService.getReceiveCount();
		map.put("data", count);
		return map;
	}
	
	/**
	 * 实物借阅
	 */
	@ResponseBody
	@RequestMapping(value = "getDrive2")
	public Map<String, Object> getDrive2(){
		Map<String, Object> map = new HashMap<>();
		int count = driveManageService.getBorrowCount();
		map.put("data", count);
		return map;
	}
	
	/**
	 * 电子借阅
	 */
	@ResponseBody
	@RequestMapping(value = "getDrive1")
	public Map<String, Object> getDrive1(){
		Map<String, Object> map = new HashMap<>();
		int count = driveManageService.getUseCount();
		map.put("data", count);
		return map;
	}
	
	private String getMax(int count) {
		int len = String.valueOf(count).length();
		StringBuilder b = new StringBuilder("1");
		for (int i = 0; i < len; i++) {
			b.append("0");
		}
		return b.toString();
	} 
	
	/**
	 * 月征集量明细box
	 * @return
	 */
	@RequestMapping(value = "toCollectStaticsDetail")
	public String toCollectStaticsDetail(String category, String type, Model model) {
		model.addAttribute("category", category);
		model.addAttribute("type", type);
		return "modules/platDriveManage/collectStaticsDetail";
	}
	
	/**
	 * 统计type类型每天征集档案量---折线图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getCollectStaticsByDays")
	public Map<String, Object> getCollectStaticsByDays(String category, String type, Model model) {
		Map<String, Object> map = new HashMap<>();
		String[] categories = DateUtils.getDayByMonth(DateUtils.formatDate(category+"-01"));
		map.put("categories", categories);
		Integer[] data = driveManageService.getCollectStaticsByDays(categories,type);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 统计type类型当月征集的价值分布---气泡图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getCollectCostStatics")
	public Map<String, Object> getCollectCostStatics(String category, String type, Model model) {
		Map<String, Object> map = new HashMap<>();
		List<List<Object>> data = driveManageService.getCollectCostStatics(category,type);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 月征集档案征集类型分布---饼图
	 */
	@ResponseBody
	@RequestMapping(value = "getCollectTypeStatics")
	public Map<String, Object> getCollectTypeStatics(String category, String type, Model model){
		Map<String, Object> map = new HashMap<>();
		//获取档案征集类型字典列表
		List<Dict> collectType = docUseService.findDict("ZJLX");
		List<Map<String,Object>> list = driveManageService.getCollectTypeStatics(collectType,category,type);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 月征集档案征集来源分布---饼图
	 */
	@ResponseBody
	@RequestMapping(value = "getCollectSourceStatics")
	public Map<String, Object> getCollectSourceStatics(String category, String type, Model model){
		Map<String, Object> map = new HashMap<>();
		//个人或机构
		String[] sourceType = Constant.collect_source_type;
		List<Map<String,Object>> list = driveManageService.getCollectSourceStatics(sourceType,category,type);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 月征集档案征集来源机构性质分布---饼图
	 */
	@ResponseBody
	@RequestMapping(value = "getCollectSourceOfDeptStatics")
	public Map<String, Object> getCollectSourceOfDeptStatics(String category, String type, Model model){
		Map<String, Object> map = new HashMap<>();
		//获取档案征集来源机构性质字典列表
		List<Dict> institutionType = docUseService.findDict("DWXZ");
		List<Map<String,Object>> list = driveManageService.getCollectSourceOfDeptStatics(institutionType,category,type);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 年鉴定量明细box
	 * @return
	 */
	@RequestMapping(value = "toAuthStaticsDetail")
	public String toAuthStaticsDetail(String category, String type, Model model) {
		model.addAttribute("category", category);
		model.addAttribute("type", type);
		return "modules/platDriveManage/authStaticsDetail";
	}
	
	/**
	 * 鉴定明细---按全宗显示年鉴定档案量---柱状图
	 */
	@ResponseBody
	@RequestMapping(value = "getAuthStaticsByUnit")
	public Map<String, Object> getAuthStaticsByUnit(String category, String type, Model model){
		return driveManageService.getAuthStaticsByUnit(category,type);
	}
	
	/**
	 * 鉴定明细---显示档案类型鉴定量---折线图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAuthStaticsByStoreId")
	public Map<String, Object> getAuthStaticsByStoreId(String category, String type, Model model) {
		return driveManageService.getAuthStaticsByStoreId(category,type);
	}
	
	/**
	 * 年移交量明细box
	 * @return
	 */
	@RequestMapping(value = "toTransferStaticsDetail")
	public String toTransferStaticsDetail(String category,String y, Model model) {
		model.addAttribute("category", category);
		model.addAttribute("ySum", y);
		return "modules/platDriveManage/transferStaticsDetail";
	}
	
	/**
	 * 移交明细----显示移交量全宗分布----柱状图
	 */
	@ResponseBody
	@RequestMapping(value = "getTransferStaticsByUnit")
	public Map<String, Object> getTransferStaticsByUnit(String category, Model model){
		return driveManageService.getTransferStaticsByUnit(category);
	}
	
	/**
	 * 移交明细----显示按档案类型统计移交量----折线图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTransferStaticsByArchiveType")
	public Map<String, Object> getTransferStaticsByArchiveType(String category, Model model) {
		return driveManageService.getTransferStaticsByArchiveType(category);
	}
	
	/**
	 * 移交明细----显示移交保管期限分布----饼图
	 */
	@ResponseBody
	@RequestMapping(value = "getTransferStaticsByPeriod")
	public Map<String, Object> getTransferStaticsByPeriod(String category,String ySum, Model model){
		Map<String, Object> map = new HashMap<>();
		//档案保管期限
		Map<String,Object> archivePeriod = Constant.archive_period;
		List<Map<String,Object>> list = driveManageService.getTransferStaticsByPeriod(archivePeriod,category,ySum);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 移交明细----显示移交交接性质分布----饼图
	 */
	@ResponseBody
	@RequestMapping(value = "getTransferStaticsByType")
	public Map<String, Object> getTransferStaticsByType(String category, Model model){
		Map<String, Object> map = new HashMap<>();
		//获取档案移交交接性质字典列表
		List<Dict> transferType = docUseService.findDict("JJXZ");
		List<Map<String,Object>> list = driveManageService.getTransferStaticsByType(transferType,category);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 档案借阅利用明细box
	 * @return
	 */
	@RequestMapping(value = "toBorrowUseStaticsDetail")
	public String toBorrowUseStaticsDetail(Model model) {
		return "modules/platDriveManage/docUseChartsList";
	}
	
	/**
	 * 档案利用方式统计----饼图
	 */
	@ResponseBody
	@RequestMapping(value = "getUseStatics")
	public Map<String, Object> getUseStatics(){
		Map<String, Object> map = new HashMap<>();
		//获取档案利用方式字典列表
		List<Dict> useMode = docUseService.findDict("LYFS");
		List<Map<String,Object>> list = driveManageService.getUseStatics(useMode);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 档案利用目的统计----饼图
	 */
	@ResponseBody
	@RequestMapping(value = "findUseCountByUsepurpose")
	public Map<String, Object> findUseCountByUsepurpose(){
		Map<String, Object> map = new HashMap<>();
		List<Map<String,Object>> list = docUseService.findUseCountByUsepurpose();
		List<Map<String,Object>> data = Lists.newArrayList();
		for(Map<String,Object> temp : list){
			Map<String,Object> t = new HashMap<>();
			t.put("name", temp.get("label").toString());
			t.put("y", Double.parseDouble(temp.get("value").toString()));
			data.add(t);
		}
		map.put("data", data);
		return map;
	}
	
	/**
	 * 档案利用方式明细box
	 * @return
	 */
	@RequestMapping(value = "toUseModeStaticsDetail")
	public String toUseModeStaticsDetail(Dict dict,Model model) {
		dict.setType("LYFS");
		//根据利用方式label获取dict
		dict = driveManageService.getDictByLabel(dict);
		model.addAttribute("dict", dict);
		return "modules/platDriveManage/useModeStaticsDetail";
	}
	
	/**
	 * 利用方式明细----显示最近12个月档案利用数量----折线图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getUseModeStaticsByMonths")
	public Map<String, Object> getUseModeStaticsByMonths(String useMode, Model model) {
		return driveManageService.getUseModeStaticsByMonths(useMode);
	}
	
	/**
	 * 利用方式明细----显示档案利用全宗分布----柱状图
	 */
	@ResponseBody
	@RequestMapping(value = "getUseModeStaticsByUnit")
	public Map<String, Object> getUseModeStaticsByUnit(String useMode, Model model){
		return driveManageService.getUseModeStaticsByUnit(useMode);
	}
	
	/**
	 * 利用方式明细----显示按档案类型统计利用量----折线图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getUseModeStaticsByArchiveType")
	public Map<String, Object> getUseModeStaticsByArchiveType(String useMode, Model model) {
		return driveManageService.getUseModeStaticsByArchiveType(useMode);
	}
	
	/**
	 * 利用方式明细----显示利用主体分布----饼图
	 */
	@ResponseBody
	@RequestMapping(value = "getUseModeStaticsByUser")
	public Map<String, Object> getUseModeStaticsByUser(String useMode, Model model){
		Map<String, Object> map = new HashMap<>();
		//获取档案利用借阅人性质字典列表
		List<Dict> userType = docUseService.findDict("JXRXZ");
		List<Map<String,Object>> list = driveManageService.getUseModeStaticsByUser(userType,useMode);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 档案利用目的明细box
	 * @return
	 */
	@RequestMapping(value = "toUsePurposeStaticsDetail")
	public String toUsePurposeStaticsDetail(Dict dict,Model model) {
		dict.setType("LYMD");
		//根据利用目的label获取dict
		dict = driveManageService.getDictByLabel(dict);
		model.addAttribute("dict", dict);
		return "modules/platDriveManage/usePurposeStaticsDetail";
	}
	
	/**
	 * 利用目的明细----显示最近12个月档案利用数量----折线图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getUsePurposeStaticsByMonths")
	public Map<String, Object> getUsePurposeStaticsByMonths(String usePurpose, Model model) {
		return driveManageService.getUsePurposeStaticsByMonths(usePurpose);
	}
	
	/**
	 * 利用目的明细----显示档案利用全宗分布----柱状图
	 */
	@ResponseBody
	@RequestMapping(value = "getUsePurposeStaticsByUnit")
	public Map<String, Object> getUsePurposeStaticsByUnit(String usePurpose, Model model){
		return driveManageService.getUsePurposeStaticsByUnit(usePurpose);
	}
	
	/**
	 * 利用目的明细----显示按档案类型统计利用量----折线图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getUsePurposeStaticsByArchiveType")
	public Map<String, Object> getUsePurposeStaticsByArchiveType(String usePurpose, Model model) {
		return driveManageService.getUsePurposeStaticsByArchiveType(usePurpose);
	}
	
	/**
	 * 利用目的明细----显示利用主体分布----饼图
	 */
	@ResponseBody
	@RequestMapping(value = "getUsePurposeStaticsByUser")
	public Map<String, Object> getUsePurposeStaticsByUser(String usePurpose, Model model){
		Map<String, Object> map = new HashMap<>();
		//获取档案利用借阅人性质字典列表
		List<Dict> userType = docUseService.findDict("JXRXZ");
		List<Map<String,Object>> list = driveManageService.getUsePurposeStaticsByUser(userType,usePurpose);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 档案馆藏统计明细box
	 * @return
	 */
	@RequestMapping(value = "toDocStoreStaticsDetail")
	public String toDocStoreStaticsDetail(Model model) {
		return "modules/platDocStore/docStoreCharts";
	}
	
	/**
	 * 安全中心---库房温湿度统计
	 */
	@ResponseBody
	@RequestMapping(value = "getRoomTHStatics")
	public Map<String, Object> getRoomTHStatics(Model model){
		return securityStaticsService.getRoomTHStatics();
	}
	
	/**
	 * 库房温湿度明细box
	 * @return
	 */
	@RequestMapping(value = "toRoomTHStaticsDetail")
	public String toRoomTHStaticsDetail(String category,Model model) {
		model.addAttribute("category", category);
		return "modules/platDriveManage/roomTHStaticsDetail";
	}
	
	/**
	 * 库房温湿度----根据库房名称温湿度每天平均值变化曲线
	 */
	@ResponseBody
	@RequestMapping(value = "getRoomTHStaticsByDay")
	public Map<String, Object> getRoomTHStaticsByDay(String category, Model model){
		return securityStaticsService.getRoomTHStaticsByDay(category);
	}
	
	/**
	 * 库房温湿度----根据库房名称统计最近12个月温湿度变化范围
	 */
	@ResponseBody
	@RequestMapping(value = "getRoomTHStaticsByMonth")
	public Map<String, Object> getRoomTHStaticsByMonth(String category, Model model){
		return securityStaticsService.getRoomTHStaticsByMonth(category);
	}
	
	/**
	 * 安全中心---按照报警类型统计报警次数
	 */
	@ResponseBody
	@RequestMapping(value = "getAlarmStatics")
	public Map<String, Object> getAlarmStatics(Model model){
		return securityStaticsService.getAlarmStatics();
	}
	
	/**
	 * 报警次数分布明细box
	 * @return
	 */
	@RequestMapping(value = "toAlarmStaticsDetail")
	public String toAlarmStaticsDetail(String category,Model model) {
		model.addAttribute("category", category);
		return "modules/platDriveManage/alarmStaticsDetail";
	}
	
	/**
	 * 报警统计----根据报警类型统计最近12个月的报警次数
	 */
	@ResponseBody
	@RequestMapping(value = "getAlarmStaticsByMonth")
	public Map<String, Object> getAlarmStaticsByMonth(String category, Model model){
		return securityStaticsService.getAlarmStaticsByMonth(category);
	}
	
	/**
	 * 报警统计----该类型的报警在各个库房的分布
	 */
	@ResponseBody
	@RequestMapping(value = "getAlarmStaticsByRoom")
	public Map<String, Object> getAlarmStaticsByRoom(String category, Model model){
		return securityStaticsService.getAlarmStaticsByRoom(category);
	}
	
	/**
	 * 报警统计----该类报警在各个设备上的分布
	 */
	@ResponseBody
	@RequestMapping(value = "getAlarmStaticsByDevice")
	public Map<String, Object> getAlarmStaticsByDevice(String category, Model model){
		return securityStaticsService.getAlarmStaticsByDevice(category);
	}
	
	
}
package com.gtzn.modules.platDocUse.web;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.gtzn.common.utils.DateUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.digital.service.DocUseService;

/**
 * 档案利用Controller
 * @author wangzhao
 * @version 2017-04-20
 */
@Controller
@RequestMapping(value = "/platDocUse")
public class DocUseController extends BaseController {

	@Autowired
	private DocUseService docUseService;
	
	/**
	 * 页面跳转:档案利用
	 */
	@RequestMapping(value = "toDocUseIndex")
	public String toDocUseChartsIndex() {
		return "modules/platDocUse/docUseIndex";
	}
	
	/**
	 * 页面跳转:档案利用-查阅列表页
	 */
	@RequestMapping(value = "toDocUseChartsListPage")
	public String toDocUseChartsListPage() {
		return "modules/platDocUse/docUseChartsList";
	}
	
	/**
	 * 页面跳转:档案利用-网络列表页
	 */
	@RequestMapping(value = "toDocUseNetListPage")
	public String toDocUseNetListPage() {
		return "modules/platDocUse/docUseNetList";
	}
	
	/**
	 * 利用，借阅 按月统计数量
	 */
	@ResponseBody
	@RequestMapping(value = "findUseAllCountByMonth")
	public Map<String,Object> findUseAllCountByMonth() {
		return docUseService.findUseAllCountByMonth();
	}
	
	
	/**
	 * 档案利用
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findUseCountByMonthUserType")
	public Map<String, Object> findUseCountByMonthUserType() {
		return docUseService.findUseCountByMonthUserType();
	}

	/**
	 * 档案借阅
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findBorrowCountByMonthApplyType")
	public Map<String, Object> findBorrowCountByMonthApplyType() {
		return docUseService.findBorrowCountByMonthApplyType();
	}

	/**
	 * 利用目的
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findUseCountByUsepurpose")
	public List<Map<String, Object>> findUseCountByUsepurpose() {
		return docUseService.findUseCountByUsepurpose();
	}

	/**
	 * 借阅目的
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findBorrowCountByUsepurpose")
	public List<Map<String, Object>> findBorrowCountByUsepurpose() {
		return docUseService.findBorrowCountByUsepurpose();
	}

	/**
	 * 全宗利用借阅数量统计
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findUnitCount")
	public Map<String, Object> findUnitCount() {
		return docUseService.findUnitCount();
	}
	
	/**
	 * 利用按年龄段统计
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findUseAgeRange")
	Map<String, Object> findUseAgeRange() {
		return docUseService.findUseAgeRange();
	}
	/**
	 * 按年统计利用借阅数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findUseAllCountByYear")
	Map<String, Object> findUseAllCountByYear() {
		return docUseService.findUseAllCountByYear();
	}
	
	//以下是弹出的页面
	
	/**
	 * 利用页面
	 * @param month
	 * @param usertype
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toUseCountByMonthUserType")
	public String toUseCountByMonthUserType(String month, String usertype, Model model) {
		model.addAttribute("month", month);
		model.addAttribute("usertype", usertype);
		return "modules/platDocUse/useCountByMonthUserType";
	}
	/**
	 * 按月统计利用数量
	 * @param month
	 * @param usertype
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findUseCountByMonthUserTypeEq")
	Map<String, Object> findUseCountByMonthUserTypeEq(String month, String usertype) {
		return docUseService.findUseCountByMonthUserTypeEq(month, usertype);
	}
	/**
	 * 按月统计类型数量
	 * @param month
	 * @param usertype
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findUseCountByClassEq")
	Map<String, Object> findUseCountByClassEq(String month, String usertype) {
		return docUseService.findUseCountByClassEq(month, usertype);
	}
	/**
	 * 借阅页面
	 * @param month
	 * @param applytype
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toBorrowCountByMonthApplyType")
	public String toBorrowCountByMonthApplyType(String month, String applytype, Model model) {
		model.addAttribute("month", month);
		model.addAttribute("applytype", applytype);
		return "modules/platDocUse/borrowCountByMonthApplyType";
	}
	/**
	 * 按月统计借阅数量
	 * @param month
	 * @param applytype
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findBorrowCountByMonthApplyTypeEq")
	Map<String, Object> findBorrowCountByMonthApplyTypeEq(String month, String applytype) {
		return docUseService.findBorrowCountByMonthApplyTypeEq(month, applytype);
	}
	/**
	 * 借阅按类型统计数量
	 * @param month
	 * @param applytype
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findBorrowCountByClassEq")
	Map<String, Object> findBorrowCountByClassEq(String month, String applytype) {
		return docUseService.findBorrowCountByClassEq(month, applytype);
	}
	
	/**
	 * 网络途径档案利用-按地区统计---基础饼图
	 */
	@ResponseBody
	@RequestMapping(value = "getNetDocUseArea")
	public List<Map<String,Object>> getNetDocUseArea(){
		
		List<Map<String,Object>> useArea = new ArrayList<>();
		Map<String,Object> map1 = new HashMap<>();
		map1.put("name", "本市内");
		map1.put("y", 69.81);
		useArea.add(map1);
		Map<String,Object> map2 = new HashMap<>();
		map2.put("name", "省内相邻市");
		map2.put("y", 15.03);
		useArea.add(map2);
		Map<String,Object> map3 = new HashMap<>();
		map3.put("name", "省内其他市");
		map3.put("y", 10.38);
		useArea.add(map3);
		Map<String,Object> map4 = new HashMap<>();
		map4.put("name", "外省");
		map4.put("y", 3.77);
		useArea.add(map4);
		Map<String,Object> map5 = new HashMap<>();
		map5.put("name", "其他地区");
		map5.put("y", 1.01);
		useArea.add(map5);
		
		return useArea;
		
	}
	
	/**
	 * 网络途径档案利用-按时间点统计---显示点值的折线图
	 */
	@ResponseBody
	@RequestMapping(value = "getNetDocUseTime")
	public Map<String, Object> getNetDocUseTime(){
		Map<String, Object> map = new HashMap<>();
		
		Object[] categories = {"0-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-19", "19-20","20-21","21-24"};
		map.put("categories", categories);
		
		List<Map<String,Object>> dataList = new ArrayList<>();
		
		Map<String, Object> map1 = new HashMap<>();
		Object[] map1Data = {7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6, 5.2, 1.0};
		map1.put("name", "网站");
		map1.put("data", map1Data);
		dataList.add(map1);
		
		Map<String, Object> map2 = new HashMap<>();
		Object[] map2Data = {3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8, 3.1, 0.5};
		map2.put("name", "微信");
		map2.put("data", map2Data);
		dataList.add(map2);
		
		Map<String, Object> map3 = new HashMap<>();
		Object[] map3Data = {1.9, 2.2, 1.7, 1.5, 1.9, 2.2, 2.0, 1.6, 1.2, 2.3, 1.6, 1.8, 2.1, 0.2};
		map3.put("name", "APP");
		map3.put("data", map3Data);
		dataList.add(map3);
		
		map.put("data", dataList);
		return map;
	}
	/**
	 * 档案利用列表导出
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "export", method=RequestMethod.GET)
	public String exportFile(HttpServletRequest request, HttpServletResponse response){
		int count = 0;
		String [] header = {"类型","利用数量","日期"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("档案利用列表");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		Integer[] use01 = (Integer[]) docUseService.findUseCountByMonthUserType().get("use01");
		Integer[] use02 = (Integer[]) docUseService.findUseCountByMonthUserType().get("use02");
		String[] months = (String[]) docUseService.findUseCountByMonthUserType().get("months");
		
		for (int i=0;i<use01.length;i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue("个人利用");//类型
			row1.createCell(1).setCellValue(use01[i]);//条数
			row1.createCell(2).setCellValue(months[i]);//时间
			count++;
		}
		for (int i=0;i<use02.length;i++) {
			XSSFRow row1 = sheet.createRow(i+1+count);
			row1.createCell(0).setCellValue("单位利用");//类型
			row1.createCell(1).setCellValue(use02[i]);//条数
			row1.createCell(2).setCellValue(months[i]);//时间
		}
		// 输出Excel文件
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=ArchivesUtilize"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
			response.setContentType("application/msexcel;charset=UTF-8");
			//写文件   
			wb.write(output);   
			//关闭输出流   
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 档案借阅列表导出
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "export2", method=RequestMethod.GET)
	public String exportFile2(HttpServletRequest request, HttpServletResponse response){
		int count = 0;
		String [] header = {"类型","借阅数量","日期"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("档案借阅列表");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		Integer[] apply1 = (Integer[]) docUseService.findBorrowCountByMonthApplyType().get("apply1");
		Integer[] apply2 = (Integer[]) docUseService.findBorrowCountByMonthApplyType().get("apply2");
		String[] months = (String[]) docUseService.findBorrowCountByMonthApplyType().get("months");
		
		for (int i=0;i<apply1.length;i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue("电子档案");//类型
			row1.createCell(1).setCellValue(apply1[i]);//条数
			row1.createCell(2).setCellValue(months[i]);//时间
			count++;
		}
		for (int i=0;i<apply2.length;i++) {
			XSSFRow row1 = sheet.createRow(i+1+count);
			row1.createCell(0).setCellValue("实物档案");//类型
			row1.createCell(1).setCellValue(apply2[i]);//条数
			row1.createCell(2).setCellValue(months[i]);//时间
		}
		// 输出Excel文件
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=ArchivesBorrow"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
			response.setContentType("application/msexcel;charset=UTF-8");
			//写文件   
			wb.write(output);   
			//关闭输出流   
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 利用目的列表导出
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "export3", method=RequestMethod.GET)
	public String exportFile3(HttpServletRequest request, HttpServletResponse response){
		String [] header = {"类型","所占比率"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("利用目的列表");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		List<Map<String, Object>> lists = docUseService.findUseCountByUsepurpose();
		
		for (int i=0;i<lists.size();i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			Map<String, Object> m = lists.get(i);
			row1.createCell(0).setCellValue(m.get("label").toString());//类型
			row1.createCell(1).setCellValue(m.get("value").toString());//占比
		}
		// 输出Excel文件
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=UsePurpose"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
			response.setContentType("application/msexcel;charset=UTF-8");
			//写文件   
			wb.write(output);   
			//关闭输出流   
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 借阅目的列表导出
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "export4", method=RequestMethod.GET)
	public String exportFile4(HttpServletRequest request, HttpServletResponse response){
		String [] header = {"类型","所占比率"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("借阅目的列表");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		List<Map<String, Object>> lists = docUseService.findBorrowCountByUsepurpose();
		
		for (int i=0;i<lists.size();i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			Map<String, Object> m = lists.get(i);
			row1.createCell(0).setCellValue(m.get("label").toString());//类型
			row1.createCell(1).setCellValue(m.get("value").toString());//占比
		}
		// 输出Excel文件
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=BorrowingPurpose"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
			response.setContentType("application/msexcel;charset=UTF-8");
			//写文件   
			wb.write(output);   
			//关闭输出流   
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 全宗查阅统计列表导出
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "export5", method=RequestMethod.GET)
	public String exportFile5(HttpServletRequest request, HttpServletResponse response){
		int count = 0;
		String [] header = {"类型","卷数","统计单位名称"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("全宗查阅统计列表");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		String[] units = (String[])docUseService.findUnitCount().get("units");
		Integer[] uses = (Integer[]) docUseService.findUnitCount().get("uses");
		Integer[] borrows = (Integer[]) docUseService.findUnitCount().get("borrows");
		
		for (int i=0;i<uses.length;i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue("利用");//类型
			row1.createCell(1).setCellValue(uses[i]);//卷数
			row1.createCell(2).setCellValue(units[i]);//统计单位名称
			count++;
		}
		for (int i=0;i<borrows.length;i++) {
			XSSFRow row1 = sheet.createRow(i+1+count);
			row1.createCell(0).setCellValue("借阅");//类型
			row1.createCell(1).setCellValue(borrows[i]);//卷数
			row1.createCell(2).setCellValue(units[i]);//统计单位名称
		}
		// 输出Excel文件
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=Statistics"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
			response.setContentType("application/msexcel;charset=UTF-8");
			//写文件   
			wb.write(output);   
			//关闭输出流   
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 全宗查阅统计列表导出
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "export6", method=RequestMethod.GET)
	public String exportFile6(HttpServletRequest request, HttpServletResponse response){
		int count = 0;
		String [] header = {"借阅人数","借阅人年龄"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("年龄段统计列表");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		String[] ageRange = (String[])docUseService.findUseAgeRange().get("ageRange");
		Object[] uses = (Object[])docUseService.findUseAgeRange().get("uses");
		
		for (int i=0;i<uses.length;i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue(uses[i]+"");//借阅人数
			row1.createCell(1).setCellValue(ageRange[i]);//借阅人年龄
		}
		// 输出Excel文件
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=AgeStatistics"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
			response.setContentType("application/msexcel;charset=UTF-8");
			//写文件   
			wb.write(output);   
			//关闭输出流   
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 借阅利用按年统计导出
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "export7", method=RequestMethod.GET)
	public String exportFile7(HttpServletRequest request, HttpServletResponse response){
		int count = 0;
		String [] header = {"类型","卷数","日期"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("借阅利用按年统计列表");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		String[] years = (String[])docUseService.findUseAllCountByYear().get("years");
		Integer[] uses = (Integer[]) docUseService.findUseAllCountByYear().get("uses");
		Integer[] borrows = (Integer[]) docUseService.findUseAllCountByYear().get("borrows");
		
		for (int i=0;i<uses.length;i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue("利用");//类型
			row1.createCell(1).setCellValue(uses[i]);//卷数
			row1.createCell(2).setCellValue(years[i]);//日期
			count++;
		}
		for (int i=0;i<borrows.length;i++) {
			XSSFRow row1 = sheet.createRow(i+1+count);
			row1.createCell(0).setCellValue("借阅");//类型
			row1.createCell(1).setCellValue(borrows[i]);//卷数
			row1.createCell(2).setCellValue(years[i]);//日期
		}
		// 输出Excel文件
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=BorrowUtilize"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
			response.setContentType("application/msexcel;charset=UTF-8");
			//写文件   
			wb.write(output);   
			//关闭输出流   
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 网络途径档案利用-按时间点统计---显示点值的折线图
	 */
	@ResponseBody
	@RequestMapping(value = "getNetDocUseWay")
	public Map<String, Object> getNetDocUseWay(){
		Map<String, Object> map = new HashMap<>();
		
		List<Map<String,Object>> dataList = new ArrayList<>();
		
		Map<String,Object> dataListItem1 = new HashMap<>();
		dataListItem1.put("name", "1月");
		dataListItem1.put("y", 12.33);
		dataListItem1.put("drilldown", "1月");
		dataList.add(dataListItem1);
		
		Map<String,Object> dataListItem2 = new HashMap<>();
		dataListItem2.put("name", "2月");
		dataListItem2.put("y", 14.03);
		dataListItem2.put("drilldown", "2月");
		dataList.add(dataListItem2);
		
		Map<String,Object> dataListItem3 = new HashMap<>();
		dataListItem3.put("name", "3月");
		dataListItem3.put("y", 14.38);
		dataListItem3.put("drilldown", "3月");
		dataList.add(dataListItem3);
		
		Map<String,Object> dataListItem4 = new HashMap<>();
		dataListItem4.put("name", "4月");
		dataListItem4.put("y", 14.77);
		dataListItem4.put("drilldown", "4月");
		dataList.add(dataListItem4);
		
		Map<String,Object> dataListItem5 = new HashMap<>();
		dataListItem5.put("name", "5月");
		dataListItem5.put("y", 10.38);
		dataListItem5.put("drilldown", "5月");
		dataList.add(dataListItem5);
		
		Map<String,Object> dataListItem6 = new HashMap<>();
		dataListItem6.put("name", "6月");
		dataListItem6.put("y", 15.03);
		dataListItem6.put("drilldown", "6月");
		dataList.add(dataListItem6);
		
		Map<String,Object> dataListItem7 = new HashMap<>();
		dataListItem7.put("name", "7月");
		dataListItem7.put("y", 16.38);
		dataListItem7.put("drilldown", "7月");
		dataList.add(dataListItem7);
		
		Map<String,Object> dataListItem8 = new HashMap<>();
		dataListItem8.put("name", "8月");
		dataListItem8.put("y", 15.03);
		dataListItem8.put("drilldown", "8月");
		dataList.add(dataListItem8);
		
		Map<String,Object> dataListItem9 = new HashMap<>();
		dataListItem9.put("name", "9月");
		dataListItem9.put("y", 16.01);
		dataListItem9.put("drilldown", "9月");
		dataList.add(dataListItem9);
		
		Map<String,Object> dataListItem10 = new HashMap<>();
		dataListItem10.put("name", "10月");
		dataListItem10.put("y", 19.03);
		dataListItem10.put("drilldown", "10月");
		dataList.add(dataListItem10);
		
		Map<String,Object> dataListItem11 = new HashMap<>();
		dataListItem11.put("name", "11月");
		dataListItem11.put("y", 10.38);
		dataListItem11.put("drilldown", "11月");
		dataList.add(dataListItem11);
		
		Map<String,Object> dataListItem12 = new HashMap<>();
		dataListItem12.put("name", "12月");
		dataListItem12.put("y", 18.03);
		dataListItem12.put("drilldown", "12月");
		dataList.add(dataListItem12);
		
		map.put("data", dataList);

		List<Map<String,Object>> drilldownList = new ArrayList<>();
		
		Map<String,Object> drilldownListItem1 = new HashMap<>();
		Object[][] Item1Data = {{"1", 0.06}, {"2", 0.20}, {"3", 0.25}, {"4", 0.08}, {"5", 0.11}, {"6", 0.06}, {"7", 0.12}, {"8", 0.15}, {"9", 0.10}, {"10", 0.12}, 
                {"11", 0.15}, {"12", 0.10}, {"13", 0.08}, {"14", 0.35}, {"15", 0.42}, {"16", 0.23}, {"17", 0.30}, {"18", 0.50}, {"19", 0.30}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.23}, {"23", 0.41}, {"24", 0.43}, {"25", 0.45}, {"26", 0.15}, {"27", 0.46}, {"28", 0.35}, {"29", 0.08}, {"30", 0.22}, {"31", 0.42}};
		drilldownListItem1.put("name", "1月");
		drilldownListItem1.put("id", "1月");
		drilldownListItem1.put("data", Item1Data);
		drilldownList.add(drilldownListItem1);
		
		Map<String,Object> drilldownListItem2 = new HashMap<>();
		Object[][] Item2Data = {{"1", 0.46}, {"2", 0.40}, {"3", 0.45}, {"4", 0.48}, {"5", 0.41}, {"6", 0.66}, {"7", 0.42}, {"8", 0.55}, {"9", 0.40}, {"10", 0.42}, 
                {"11", 0.45}, {"12", 0.30}, {"13", 0.48}, {"14", 0.35}, {"15", 0.42}, {"16", 0.53}, {"17", 0.34}, {"18", 0.56}, {"19", 0.37}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.53}, {"23", 0.51}, {"24", 0.53}, {"25", 0.50}, {"26", 0.25}, {"27", 0.46}, {"28", 0.35}};
		drilldownListItem2.put("name", "2月");
		drilldownListItem2.put("id", "2月");
		drilldownListItem2.put("data", Item2Data);
		drilldownList.add(drilldownListItem2);
		
		Map<String,Object> drilldownListItem3 = new HashMap<>();
		Object[][] Item3Data = {{"1", 0.46}, {"2", 0.40}, {"3", 0.45}, {"4", 0.48}, {"5", 0.41}, {"6", 0.66}, {"7", 0.42}, {"8", 0.55}, {"9", 0.40}, {"10", 0.42}, 
                {"11", 0.45}, {"12", 0.30}, {"13", 0.48}, {"14", 0.35}, {"15", 0.42}, {"16", 0.53}, {"17", 0.34}, {"18", 0.56}, {"19", 0.37}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.53}, {"23", 0.51}, {"24", 0.53}, {"25", 0.50}, {"26", 0.25}, {"27", 0.46}, {"28", 0.35}, {"29", 0.56}, {"30", 0.66}, {"31", 0.58}};
		drilldownListItem3.put("name", "3月");
		drilldownListItem3.put("id", "3月");
		drilldownListItem3.put("data", Item3Data);
		drilldownList.add(drilldownListItem3);
		
		Map<String,Object> drilldownListItem4 = new HashMap<>();
		Object[][] Item4Data = {{"1", 0.46}, {"2", 0.40}, {"3", 0.45}, {"4", 0.48}, {"5", 0.41}, {"6", 0.66}, {"7", 0.42}, {"8", 0.55}, {"9", 0.40}, {"10", 0.42}, 
                {"11", 0.45}, {"12", 0.30}, {"13", 0.48}, {"14", 0.35}, {"15", 0.42}, {"16", 0.53}, {"17", 0.34}, {"18", 0.56}, {"19", 0.37}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.53}, {"23", 0.51}, {"24", 0.53}, {"25", 0.50}, {"26", 0.25}, {"27", 0.46}, {"28", 0.35}, {"29", 0.56}, {"30", 0.66}};
		drilldownListItem4.put("name", "4月");
		drilldownListItem4.put("id", "4月");
		drilldownListItem4.put("data", Item4Data);
		drilldownList.add(drilldownListItem4);
		
		Map<String,Object> drilldownListItem5 = new HashMap<>();
		Object[][] Item5Data = {{"1", 0.46}, {"2", 0.40}, {"3", 0.45}, {"4", 0.48}, {"5", 0.41}, {"6", 0.66}, {"7", 0.42}, {"8", 0.55}, {"9", 0.40}, {"10", 0.42}, 
                {"11", 0.45}, {"12", 0.30}, {"13", 0.48}, {"14", 0.35}, {"15", 0.42}, {"16", 0.53}, {"17", 0.34}, {"18", 0.56}, {"19", 0.37}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.53}, {"23", 0.51}, {"24", 0.53}, {"25", 0.50}, {"26", 0.25}, {"27", 0.46}, {"28", 0.35}, {"29", 0.56}, {"30", 0.66}, {"31", 0.80}};
		drilldownListItem5.put("name", "5月");
		drilldownListItem5.put("id", "5月");
		drilldownListItem5.put("data", Item5Data);
		drilldownList.add(drilldownListItem5);
		
		Map<String,Object> drilldownListItem6 = new HashMap<>();
		Object[][] Item6Data = {{"1", 0.46}, {"2", 0.40}, {"3", 0.45}, {"4", 0.48}, {"5", 0.41}, {"6", 0.66}, {"7", 0.42}, {"8", 0.55}, {"9", 0.40}, {"10", 0.42}, 
                {"11", 0.45}, {"12", 0.30}, {"13", 0.48}, {"14", 0.35}, {"15", 0.42}, {"16", 0.53}, {"17", 0.34}, {"18", 0.56}, {"19", 0.37}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.53}, {"23", 0.51}, {"24", 0.53}, {"25", 0.50}, {"26", 0.25}, {"27", 0.46}, {"28", 0.35}, {"29", 0.56}, {"30", 0.66}};
		drilldownListItem6.put("name", "6月");
		drilldownListItem6.put("id", "6月");
		drilldownListItem6.put("data", Item6Data);
		drilldownList.add(drilldownListItem6);
		
		Map<String,Object> drilldownListItem7 = new HashMap<>();
		Object[][] Item7Data = {{"1", 0.46}, {"2", 0.40}, {"3", 0.45}, {"4", 0.48}, {"5", 0.41}, {"6", 0.66}, {"7", 0.42}, {"8", 0.55}, {"9", 0.40}, {"10", 0.42}, 
                {"11", 0.45}, {"12", 0.30}, {"13", 0.48}, {"14", 0.35}, {"15", 0.42}, {"16", 0.53}, {"17", 0.34}, {"18", 0.56}, {"19", 0.37}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.53}, {"23", 0.51}, {"24", 0.53}, {"25", 0.50}, {"26", 0.25}, {"27", 0.46}, {"28", 0.35}, {"29", 0.56}, {"30", 0.66}, {"31", 0.80}};
		drilldownListItem7.put("name", "7月");
		drilldownListItem7.put("id", "7月");
		drilldownListItem7.put("data", Item7Data);
		drilldownList.add(drilldownListItem7);
		
		Map<String,Object> drilldownListItem8 = new HashMap<>();
		Object[][] Item8Data = {{"1", 0.46}, {"2", 0.40}, {"3", 0.45}, {"4", 0.48}, {"5", 0.41}, {"6", 0.66}, {"7", 0.42}, {"8", 0.55}, {"9", 0.40}, {"10", 0.42}, 
                {"11", 0.45}, {"12", 0.30}, {"13", 0.48}, {"14", 0.35}, {"15", 0.42}, {"16", 0.53}, {"17", 0.34}, {"18", 0.56}, {"19", 0.37}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.53}, {"23", 0.51}, {"24", 0.53}, {"25", 0.50}, {"26", 0.25}, {"27", 0.46}, {"28", 0.35}, {"29", 0.56}, {"30", 0.66}, {"31", 0.80}};
		drilldownListItem8.put("name", "8月");
		drilldownListItem8.put("id", "8月");
		drilldownListItem8.put("data", Item8Data);
		drilldownList.add(drilldownListItem8);
		
		Map<String,Object> drilldownListItem9 = new HashMap<>();
		Object[][] Item9Data = {{"1", 0.46}, {"2", 0.40}, {"3", 0.45}, {"4", 0.48}, {"5", 0.41}, {"6", 0.66}, {"7", 0.42}, {"8", 0.55}, {"9", 0.40}, {"10", 0.42}, 
                {"11", 0.45}, {"12", 0.30}, {"13", 0.48}, {"14", 0.35}, {"15", 0.42}, {"16", 0.53}, {"17", 0.34}, {"18", 0.56}, {"19", 0.37}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.53}, {"23", 0.51}, {"24", 0.53}, {"25", 0.50}, {"26", 0.25}, {"27", 0.46}, {"28", 0.35}, {"29", 0.56}, {"30", 0.66}};
		drilldownListItem9.put("name", "9月");
		drilldownListItem9.put("id", "9月");
		drilldownListItem9.put("data", Item9Data);
		drilldownList.add(drilldownListItem9);
		
		Map<String,Object> drilldownListItem10 = new HashMap<>();
		Object[][] Item10Data = {{"1", 0.46}, {"2", 0.40}, {"3", 0.45}, {"4", 0.48}, {"5", 0.41}, {"6", 0.66}, {"7", 0.42}, {"8", 0.55}, {"9", 0.40}, {"10", 0.42}, 
                {"11", 0.45}, {"12", 0.30}, {"13", 0.48}, {"14", 0.35}, {"15", 0.42}, {"16", 0.53}, {"17", 0.34}, {"18", 0.56}, {"19", 0.37}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.53}, {"23", 0.51}, {"24", 0.53}, {"25", 0.50}, {"26", 0.25}, {"27", 0.46}, {"28", 0.35}, {"29", 0.56}, {"30", 0.66}, {"31", 0.80}};
		drilldownListItem10.put("name", "10月");
		drilldownListItem10.put("id", "10月");
		drilldownListItem10.put("data", Item10Data);
		drilldownList.add(drilldownListItem10);
		
		Map<String,Object> drilldownListItem11 = new HashMap<>();
		Object[][] Item11Data = {{"1", 0.46}, {"2", 0.40}, {"3", 0.45}, {"4", 0.48}, {"5", 0.41}, {"6", 0.66}, {"7", 0.42}, {"8", 0.55}, {"9", 0.40}, {"10", 0.42}, 
                {"11", 0.45}, {"12", 0.30}, {"13", 0.48}, {"14", 0.35}, {"15", 0.42}, {"16", 0.53}, {"17", 0.34}, {"18", 0.56}, {"19", 0.37}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.53}, {"23", 0.51}, {"24", 0.53}, {"25", 0.50}, {"26", 0.25}, {"27", 0.46}, {"28", 0.35}, {"29", 0.56}, {"30", 0.66}};
		drilldownListItem11.put("name", "11月");
		drilldownListItem11.put("id", "11月");
		drilldownListItem11.put("data", Item11Data);
		drilldownList.add(drilldownListItem11);
		
		Map<String,Object> drilldownListItem12 = new HashMap<>();
		Object[][] Item12Data = {{"1", 0.46}, {"2", 0.40}, {"3", 0.45}, {"4", 0.48}, {"5", 0.41}, {"6", 0.66}, {"7", 0.42}, {"8", 0.55}, {"9", 0.40}, {"10", 0.42}, 
                {"11", 0.45}, {"12", 0.30}, {"13", 0.48}, {"14", 0.35}, {"15", 0.42}, {"16", 0.53}, {"17", 0.34}, {"18", 0.56}, {"19", 0.37}, {"20", 0.45}, 
                {"21", 0.42},  {"22", 0.53}, {"23", 0.51}, {"24", 0.53}, {"25", 0.50}, {"26", 0.25}, {"27", 0.46}, {"28", 0.35}, {"29", 0.56}, {"30", 0.66}, {"31", 0.80}};
		drilldownListItem12.put("name", "12月");
		drilldownListItem12.put("id", "12月");
		drilldownListItem12.put("data", Item12Data);
		drilldownList.add(drilldownListItem12);
		
		map.put("drilldown", drilldownList);
		return map;
	}
	
}
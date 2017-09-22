package com.gtzn.modules.platServiceCenter.web;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gtzn.common.web.BaseController;
import com.gtzn.modules.home.entity.WebVisitStatistics;
import com.gtzn.modules.home.service.WebVisitStatisticsService;

/**
 * 服务中心Controller 网站统计
 * @author chenyp
 *
 */
@Controller
@RequestMapping(value = "/platServiceCenter")
public class PlatServiceCenterController extends BaseController{

	@Autowired
	private WebVisitStatisticsService webVisitStatisticsService;
	/**
	 * 跳转到网站统计页面
	 * @return
	 */
	@RequestMapping("platServiceCenter")
	public String toServiceCenter(){
		
		return "modules/platServiceCenter/platServiceCenter";
	}
	
	/**
	 * 跳转到信息导入页面
	 * @return
	 */
	@RequestMapping("toIntroduction")
	public String toIntroduction(){
		
		return "modules/platServiceCenter/introduction";
	}
	
	/**
	 * 热点模块访问统计图
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getVisitData")
	public Map<String, Object> getVisitData(){
		Map<String, Object> map = new HashMap<>();
		
		List<String> categories = new ArrayList<>();
		List<WebVisitStatistics> list = webVisitStatisticsService.getTemp();
		for (WebVisitStatistics wvs : list) {
			categories.add(wvs.getVisittemplate());
		}
//		categories = {"工作动态","公告栏","政策法规","视频档案","信息公开","网上展示", "珍藏经典","档案编演","公开档案","岁月留痕","档案利用","基层网络"};
		map.put("categories", categories);
		
		List<Map<String, Object>> dataList = new ArrayList<>();
		
		List<Integer> ya = webVisitStatisticsService.getYear();
//		System.err.println(ya.get(0)+"---==---==---"+ya.get(1)+"---==---==---"+ya.size());
		
		Map<String,Object> listMap1 = new HashMap<>();
		listMap1.put("name", ya.get(0)+"年");
		List<Integer> intlist1 = new ArrayList<>();
		for (String str : categories) {
			int ss = webVisitStatisticsService.getTempYesrCount(str, ya.get(0)+"");
			intlist1.add(ss);
		}
		listMap1.put("data", intlist1);
		dataList.add(listMap1);
		
		Map<String,Object> listMap2 = new HashMap<>();
		listMap2.put("name", ya.get(1)+"年");
		List<Integer> intlist2 = new ArrayList<>();
		for (String str : categories) {
			int ss = webVisitStatisticsService.getTempYesrCount(str, ya.get(1)+"");
			intlist2.add(ss);
		}
		listMap2.put("data", intlist2);
		dataList.add(listMap2);
		
		Map<String,Object> listMap3 = new HashMap<>();
		listMap3.put("name", ya.get(2)+"年");
		List<Integer> intlist3 = new ArrayList<>();
		for (String str : categories) {
			int ss = webVisitStatisticsService.getTempYesrCount(str, ya.get(2)+"");
			intlist3.add(ss);
		}
		listMap3.put("data", intlist3);
		dataList.add(listMap3);
		
		map.put("data", dataList);
		return map;
	}
	
	/**
	 * 档案馆网站访问统计
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSitesData")
	public Map<String, Object> getSitesVisitData(){
		Map<String, Object> map = new HashMap<>();
		//年
		List<String> ycategories = webVisitStatisticsService.getYearMonth();
//		String[] ycategories = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
		map.put("ycategories", ycategories);
		
		List<Map<String, Object>> ydataList = new ArrayList<>();
		
		Map<String,Object> listMap1 = new HashMap<>();
		listMap1.put("name", "独立IP");
		List<Integer> intlist1 = new ArrayList<>();
		for (String str : ycategories) {
			Integer mc = webVisitStatisticsService.getMonthCount(str, "IP");
			intlist1.add(mc);
		}
//		Object[] data1 = {129, 151, 186, 199, 204, 216, 205, 169, 166, 184, 135, 114};
		
		listMap1.put("data", intlist1);
		ydataList.add(listMap1);
		
		Map<String,Object> listMap2 = new HashMap<>();
		listMap2.put("name", "浏览量(PV)");
		List<Integer> intlist2 = new ArrayList<>();
		for (String str : ycategories) {
			Integer mc = webVisitStatisticsService.getMonthCount(str, "PV");
			intlist2.add(mc);
		}
//		Object[] data2 = {183, 228, 298, 293, 286, 284, 275, 264, 291, 283, 206, 192};
		listMap2.put("data", intlist2);
		ydataList.add(listMap2);
		map.put("ydata", ydataList);
		
		
		//月
		List<String> mcategories = webVisitStatisticsService.getMonthDay();
//		Object[] mcategories = { 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 , 11 , 12 , 13 , 14 , 15 ,
//           	 16 , 17 , 18 , 19 , 20 , 21 , 22 , 23 , 24 , 25 , 26 , 27 , 28 , 29 , 30 , 31 };
		map.put("mcategories", mcategories);
		
		List<Map<String, Object>> mdataList = new ArrayList<>();
		
		Map<String,Object> listMap3 = new HashMap<>();
		listMap3.put("name", "独立IP");
		List<Integer> intlist3 = new ArrayList<>();
		for (String str : mcategories) {
			Integer dc = webVisitStatisticsService.getDayCount(str, "IP");
			intlist3.add(dc);
		}
//		Object[] data3 = {29, 21, 18, 19, 24, 16, 13,29, 21, 18, 19, 24, 16, 17,19, 21, 18, 19, 24, 16, 13,
//            	29, 21, 18, 19, 24, 16, 14,29, 21, 18};
		listMap3.put("data", intlist3);
		mdataList.add(listMap3);
		
		Map<String,Object> listMap4 = new HashMap<>();
		listMap4.put("name", "浏览量(PV)");
		List<Integer> intlist4 = new ArrayList<>();
		for (String str : mcategories) {
			Integer dc = webVisitStatisticsService.getDayCount(str, "PV");
			intlist4.add(dc);
		}
//		Object[] data4 = {33, 28, 28, 29, 28, 24, 19, 43, 28, 28, 29, 28, 24, 26,23, 28, 28, 29, 28, 24, 19,
//            	33, 28, 28, 29, 28, 24, 21,43, 28, 28};
		listMap4.put("data", intlist4);
		mdataList.add(listMap4);
		map.put("mdata", mdataList);
		return map;
	}
	/**
	 * 信息导入
	 * @param url
	 * @return
	 */
	@RequestMapping(value="platServiceCenterSave",method=RequestMethod.POST)
//	@ResponseBody
	public String platServiceCenterSave(@RequestParam(name = "file", required = false) MultipartFile file,HttpServletRequest request,Model model){
		InputStream is =null;
		Workbook wb = null;
		try {

			is=file.getInputStream();
			if (is==null) {
				throw new Exception("is==null");
			}
			//文件后缀名
			String hm = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
			if (hm.equals("xls")) {
	            wb = new HSSFWorkbook(is);
	        } else if (hm.equals("xlsx")) {
	            wb = new XSSFWorkbook(is);
	        }else{
	        	//格式不正确返回导入页面
	        	return "modules/platServiceCenter/introduction";
//	        	throw new Exception("文件格式不正确");
	        }
			System.out.println("导入文件:"+file.getOriginalFilename());
			int sheetSize = wb.getNumberOfSheets();
			for (int i = 0; i < sheetSize; i++) {//遍历sheet页
	            Sheet sheet = wb.getSheetAt(i);
	            int rowSize = sheet.getLastRowNum() + 1;
	            for (int j = 0; j < rowSize; j++) {//遍历行
	                Row row = sheet.getRow(j);
	                if (row == null) {//略过空行
	                    continue;
	                }
	                int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
	                if (j == 0) {//第一行是标题行
	                    for (int k = 0; k < cellSize; k++) {
	                        Cell cell = row.getCell(k);
//	                        titles.add(cell.toString());
	                    }
	                } else {//其他行是数据行

	                    WebVisitStatistics wvs = new WebVisitStatistics();
	                    Cell cell = row.getCell(0);
	                    if (cell != null && !cell.toString().equals("")) {
	                    	String aa = cell.toString();
	                    	if (!aa.equals("IP") & !aa.equals("PV")) {
	                    		model.addAttribute("message", "访问类型不正确,请写入正确的访问类型");
	                    		return "modules/platServiceCenter/introduction";
							}
	                        wvs.setVisittype(aa);
	                    }    
	                    Cell cell1 = row.getCell(1);
	                    if (cell1 != null && !cell1.toString().equals("")) {
	                        wvs.setVisittemplate(cell1.toString());
	                    }else {
	                    	wvs.setVisittemplate("N");
						}
	                    Cell cell2 = row.getCell(2);
	                    if (cell2 != null && !cell2.toString().equals("")) {
	                        wvs.setVisittime(cell2.getDateCellValue());
	                    }
	                    Cell cell3 = row.getCell(3);
	                    if (cell3 != null && !cell3.toString().equals("")) {
	                        wvs.setVisitcount(cell3.toString());
	                    }   
	                    webVisitStatisticsService.insertWebVisitStatisticsSelective(wvs);
	                }
	            }
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("message", "导入成功");
		return "modules/platServiceCenter/introduction";
	}

}

package com.gtzn.modules.platDocStore.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
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

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.utils.DateUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.digital.entity.Dictionaries;
import com.gtzn.modules.digital.service.DStatusService;
import com.gtzn.modules.digital.service.DictionariesService;
import com.gtzn.modules.digital.service.PlatStatisticsService;
import com.gtzn.modules.digital.service.PlatUnitService;
import com.gtzn.modules.digital.service.RetentionService;
import com.gtzn.modules.home.service.DocStoreService;

/**
 * 档案馆藏数量
 * @author chenyp
 *
 */
@Controller
@RequestMapping(value = "/platDocStore")
public class DocStoreController extends BaseController{
	 
	@Autowired
	private DocStoreService docStoreService;
	@Autowired
	private DictionariesService dictionariesService;
	@Autowired
	private PlatStatisticsService platStatisticsService;
	@Autowired
	private PlatUnitService platUnitService;
	@Autowired
	private DStatusService dStatusService;
	@Autowired   
	private RetentionService retentionService;
	
	/**
	 * 馆藏数量web主页
	* @Title: 
	* @Description: 
	* @param @return
	* @return 
	* @throws
	 */
	@RequestMapping(value = "toDocStoreIndex")
	public String toDocStoreIndex() {
		return "modules/platDocStore/docStoreIndex";
	}
	
	@RequestMapping(value = "toDocStoreCharts")
	public String toDocStoreCharts() {
		return "modules/platDocStore/docStoreCharts";
	}
	
	/**
	 * 档案主页图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "GrossDataCharts")
	public Map<String,Object> getGrossData(){
		Map<String, Object> map = dictionariesService.queryDictionariesList();
		return map;
	}
	
	/**
	 * 馆藏数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getChartsNumData")
	public Map<String,Object> getNumData(){
		Map<String, Object> map = platStatisticsService.queryStatisticsList();
		return map;
	}
	
	/**
	 * 查看上架和未上架
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getChartsCapacityData")
	public Map<String,Object> getCapacityData(){
		Map<String, Object> map = dStatusService.getCapacityData();
		return map;
	}
	
	/**
	 * 历年新增量图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getChartsIncrementData")
	public Map<String,Object> getIncrementData(){
		Map<String, Object> map = platStatisticsService.getChartsIncrementData();
		return map;
	}
	
	/**
	 * 新入库数量图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getChartsStorageData")
	public Map<String,Object> getStorageData(){
		Map<String, Object> map = platUnitService.getIncrementData();
		return map;
	}
	/**
	 * 按照保管期限统计，饼图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getStoragePeriod")
	public List<Map<String, Object>> getStoragePeriod (String type) {
		return retentionService.getStoragePeriod(type);
	}
	
	/**
	 * 档案类型列表 导出
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value = "export", method=RequestMethod.GET)
	public String exportFile(HttpServletRequest request, HttpServletResponse response){
		int count = 0;
		String [] header = {"档案类型","实物(卷)","电子(卷)"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("档案类型列表");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		List<String> categories = (List<String>) dictionariesService.queryDictionariesList().get("categories");
		List<Integer> list2 = (List<Integer>) dictionariesService.queryDictionariesList().get("list2");
		List<Integer> list3 = (List<Integer>) dictionariesService.queryDictionariesList().get("list3");
		
		for (int i=0;i<categories.size();i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue(categories.get(i));//类型
			row1.createCell(1).setCellValue(list2.get(i));//实物条数
			row1.createCell(2).setCellValue(list3.get(i));//电子条数
			count++;
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
	 * 馆藏容量列表导出
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value = "export2", method=RequestMethod.GET)
	public String exportFile2(HttpServletRequest request, HttpServletResponse response){
		String [] header = {"档案馆","移交","未移交","总和"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("馆藏容量");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		List<String> categories = (List<String>) dStatusService.getCapacityData().get("categories");
		List<Integer> list2 = (List<Integer>) dStatusService.getCapacityData().get("inLibrary");
		List<Integer> list3 = (List<Integer>) dStatusService.getCapacityData().get("issue");
		
		for (int i=0;i<categories.size();i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue(categories.get(i));
			row1.createCell(1).setCellValue(list2.get(i));
			row1.createCell(2).setCellValue(list3.get(i));
			row1.createCell(3).setCellValue(list3.get(i)+list2.get(i));
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
	 * 历年新增量列表导出
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value = "export3", method=RequestMethod.GET)
	public String exportFile3(HttpServletRequest request, HttpServletResponse response){
		String [] header = {"年份","新增实体数量(卷)","新增电子数量(卷)"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("历年新增量");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		List<String> categories = (List<String>) platStatisticsService.getChartsIncrementData().get("categories");
		List<Integer> list2 = (List<Integer>) platStatisticsService.getChartsIncrementData().get("list2");
		List<Integer> list3 = (List<Integer>) platStatisticsService.getChartsIncrementData().get("list3");
		
		for (int i=0;i<categories.size();i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue(categories.get(i));//年份
			row1.createCell(1).setCellValue(list2.get(i));//实物
			row1.createCell(2).setCellValue(list3.get(i));//电子
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
	 * 新入库数量列表
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value = "export4", method=RequestMethod.GET)
	public String exportFile4(HttpServletRequest request, HttpServletResponse response){
		String [] header = {"档案馆名称","今年入库量(卷)"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("新入库量");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		List<String> categories = (List<String>) platUnitService.getIncrementData().get("categories");
		List<Integer> list2 = (List<Integer>) platUnitService.getIncrementData().get("list4");
		
		for (int i=0;i<categories.size();i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue(categories.get(i));
			row1.createCell(1).setCellValue(list2.get(i));
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
	 * 密级列表导出
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value = "export5", method=RequestMethod.GET)
	public String exportFile5(HttpServletRequest request, HttpServletResponse response, String type){
		String [] header = {"密级","占比"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("密级比例列表");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		List<Map<String, Object>> list = retentionService.getStoragePeriod(type);
		
		for (int i=0;i<list.size();i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			String str =  (String) list.get(i).get("label");
			double doub =  (double) list.get(i).get("value");
			//百分数显示
			NumberFormat nf = NumberFormat.getPercentInstance();  
			//保留两位有效数字
			nf.setMinimumFractionDigits(1);
			String dst = nf.format(doub);
			row1.createCell(0).setCellValue(str);
			row1.createCell(1).setCellValue(dst);
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
	 * 保管年限列表导出
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value = "export6", method=RequestMethod.GET)
	public String exportFile6(HttpServletRequest request, HttpServletResponse response, String type){
		String [] header = {"保管期限","占比"};
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("保管期限比例列表");
		//生成第一行 header
		XSSFRow row = sheet.createRow(0); 
		for (int i=0;i<header.length;i++) {
			//给这一行的第一列赋值   
			row.createCell(i).setCellValue(header[i]);    
		}
		List<Map<String, Object>> list = retentionService.getStoragePeriod(type);
		
		for (int i=0;i<list.size();i++) {
			XSSFRow row1 = sheet.createRow(i+1);
			String str =  (String) list.get(i).get("label");
			double doub =  (double) list.get(i).get("value");
			NumberFormat nf = NumberFormat.getPercentInstance();  
			nf.setMinimumFractionDigits(1);
			String dst = nf.format(doub);
			row1.createCell(0).setCellValue(str);
			row1.createCell(1).setCellValue(dst);
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
}

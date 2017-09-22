/**
 * 
 */
package com.gtzn.modules.home;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.DateUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.digital.entity.YDataCollection;
import com.gtzn.modules.digital.service.DataCollectionService;
import com.gtzn.modules.home.service.PlatCollectionService;

/**
 * 最新采集Controller
 * @author wzx
 * @version 2017-04-25
 */
@Controller
@RequestMapping(value = "/platcollection/platCollection")
public class PlatCollectionController extends BaseController {

	@Autowired
	private PlatCollectionService platCollectionService;
	@Autowired
	private DataCollectionService dataCollectionService;


	@RequiresPermissions("platcollection:platCollection:view")
	@RequestMapping(value = {"index"})
	public String index(YDataCollection yDataCollection, Model model) {
		return "modules/home/platCollection/platCollectionIndex";
	}
	
	@RequiresPermissions("platcollection:platCollection:view")
	@RequestMapping(value = {"list"})
	public String list(YDataCollection yDataCollection, Model model) {

		return "modules/home/platCollection/platCollectionList";
	}
	
	@RequiresPermissions("platcollection:platCollection:view")
	@RequestMapping(value = {"load"})
	@ResponseBody
	public Pager<YDataCollection> load(Pager<YDataCollection> pager, YDataCollection yDataCollection,HttpServletRequest request,HttpServletResponse response) {
		Pager<YDataCollection> page = dataCollectionService.findPage(pager, yDataCollection);
		return page;
	}
	
	@RequiresPermissions("platcollection:platCollection:view")
	@RequestMapping(value = "editForm")
	public String editForm(YDataCollection yDataCollection, Model model) {
		List<YDataCollection> list = dataCollectionService.selectYDataCollection(yDataCollection);

		model.addAttribute("list", list);
		return "modules/home/platCollection/platCollectionEditForm";
	}
	
	@RequiresPermissions("platcollection:platCollection:view")
	@RequestMapping(value = "save")
	public String saveForm(HttpServletRequest request, Model model) {
		String aid = request.getParameter("aid");
		List<YDataCollection> list = dataCollectionService.selectFileCollection(aid);

		model.addAttribute("list", list);
		return "modules/home/platCollection/platCollectionfile";
	}

	@RequiresPermissions("platcollection:platCollection:view")
	@RequestMapping(value = "downloadDoc")
	public String downloadDoc(HttpServletRequest request, HttpServletResponse response,Model model) {
		String fileName=request.getParameter("fileName");
		
		String filePath="D:\\download"+"\\"+fileName;
		
        System.out.println(fileName+"文件下载------------"+filePath);
        
        // 处理文件名  
       String dfileName =DateUtils.getDate("yyyyMMddHHmmss") + fileName.substring(fileName.indexOf("."));  
        try {
        	// 设置响应头，控制浏览器下载该文件  
            response.setHeader("content-disposition", "attachment;filename="  
        			+ URLEncoder.encode(dfileName, "UTF-8"));  
        	// 读取要下载的文件，保存到文件输入流  
        	FileInputStream in = new FileInputStream(filePath);  
        	// 创建输出流  
        	OutputStream out = response.getOutputStream();  
        	// 创建缓冲区  
        	byte buffer[] = new byte[1024];  
        	int len = 0;  
        	// 循环将输入流中的内容读取到缓冲区当中  
        	while ((len = in.read(buffer)) > 0) {  
        		// 输出缓冲区的内容到浏览器，实现文件下载  
        		out.write(buffer, 0, len);  
        	}  
        	// 关闭文件输入流  
        	in.close();   
        	// 关闭输出流  
			out.close();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
}
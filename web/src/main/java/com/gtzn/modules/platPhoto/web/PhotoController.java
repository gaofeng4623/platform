package com.gtzn.modules.platPhoto.web;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import com.gtzn.soa.service.WebService;
import com.gtzn.common.utils.SpringContextHolder;
import com.gtzn.common.config.Global;

import com.gtzn.common.web.BaseController;
import com.gtzn.modules.monitor.entity.CameraImage;

/**
 * 待审批Controller
 * @author wangzhao
 * @version 2017-04-11
 */
@Controller
@RequestMapping(value = "/platPhoto/")
public class PhotoController extends BaseController {
	
    private WebService platPhotoService = SpringContextHolder.getBean("platPhotoService"); //报警服务
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "toPhotoIndex")
	public String toPhotoIndex() {
		return "modules/platPhoto/photoIndex";
	}
	
	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "toPhotoListPage")
	public String toPhotoListPage() {
		return "modules/platPhoto/photoList";
	}
	
	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "toPhotoList2Page")
	public String toPhotoList2Page(Model model, @RequestParam("cameraId") String cameraId, @RequestParam("dateStr") String dateStr) {
		model.addAttribute("cameraId", cameraId);
		model.addAttribute("dateStr", dateStr);
		return "modules/platPhoto/photoList2";
	}
	
	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "toPhotoVideosPage")
	public String toPhotoVideosPage(Model model, @RequestParam("videoUrl") String videoUrl) {
		model.addAttribute("videoUrl", videoUrl);
		return "modules/platPhoto/photoVideos";
	}

	/**
	 * 获取重要部位的照片
	 */
	@ResponseBody
	@RequestMapping(value = "getCameraImages")
	public List<CameraImage> getCameraImages(){
		List<CameraImage> list = new ArrayList<>();
		try {
			list = platPhotoService.invokeResult("getCameraImages", new Object[]{
					df.format(new Date())}, CameraImage.class, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取关联照片
	 */
	@ResponseBody
	@RequestMapping(value = "getRelatedImages")
	public List<CameraImage> getRelatedImages(CameraImage cameraImage){
		List<CameraImage> list = new ArrayList<>();
		try {
			list = platPhotoService.invokeResult("getRelatedImages", new Object[]{
					cameraImage.getCameraId(),cameraImage.getDateStr()}, CameraImage.class, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	

}
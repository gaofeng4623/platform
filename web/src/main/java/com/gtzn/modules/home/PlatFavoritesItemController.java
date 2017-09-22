/**
 * 
 */
package com.gtzn.modules.home;

import com.google.common.collect.Maps;
import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.digital.entity.File;
import com.gtzn.modules.digital.service.FileService;
import com.gtzn.modules.home.entity.NoticeInfo;
import com.gtzn.modules.home.entity.PlatFavorites;
import com.gtzn.modules.home.entity.PlatFavoritesItem;
import com.gtzn.modules.home.entity.PlatInformation;
import com.gtzn.modules.home.service.NoticeInfoService;
import com.gtzn.modules.home.service.PlatFavoritesItemService;
import com.gtzn.modules.home.service.PlatFavoritesService;
import com.gtzn.modules.home.service.PlatInformationService;
import com.gtzn.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 我的收藏Controller
 * @author wzx
 * @version 2017-04-20
 */
@Controller
@RequestMapping(value = "/platfavoritesitem/platFavoritesItem")
public class PlatFavoritesItemController extends BaseController {

	@Autowired
	private PlatFavoritesItemService platFavoritesItemService;
	
	@Autowired
	private PlatFavoritesService platFavoritesService;
	
	@Autowired
	private NoticeInfoService noticeInfoService;
	
	@Autowired
	private  PlatInformationService platInformationService;
	@Autowired
	private FileService fileService;
//	@ModelAttribute
//	public PlatFavoritesItem get(@RequestParam(required=false) String favoritesid) {
//		PlatFavoritesItem entity = null;
//		if (StringUtils.isNotBlank(favoritesid)){
//			entity = platFavoritesItemService.selectPlatFavoritesItemInfo(id);
//		}
//		if (entity == null){
//			entity = new PlatFavoritesItem();
//		}
//		return entity;
//	}

	@RequestMapping(value = {"list"})
	public String list(PlatFavoritesItem platFavoritesItem,String favoritesid, Model model) {
		if(StringUtils.isNotEmpty(favoritesid)){
			model.addAttribute("favoritesid",favoritesid);
		}
		return "modules/home/platFavoritesItem/platFavoritesItemList";
	}
	
	@RequestMapping(value = {"load"})
	@ResponseBody
	public Pager<PlatFavoritesItem> load(Pager<PlatFavoritesItem> pager, String favoritesid,PlatFavoritesItem platFavoritesItem,HttpServletRequest request,HttpServletResponse response) {
		
		if(null !=platFavoritesItem.getFavoritesid() && platFavoritesItem.getFavoritesid().equals("001")){
			platFavoritesItem.setFavoritesid(null);
		}
		
		platFavoritesItem.setCreateBy(WebUtil.getUser());
		Pager<PlatFavoritesItem> page = platFavoritesItemService.findPage(pager, platFavoritesItem); 
		List<PlatFavoritesItem> list = page.getList();
		for (PlatFavoritesItem platFavoritesItem2 : list) {
			Map<String, Object> map =getscj();
			//用remaks保存收藏夹名称，用于临时展示使用
			platFavoritesItem2.setRemarks((String)map.get(platFavoritesItem2.getFavoritesid()));
		}
		
		return page;
	}
	@RequestMapping(value = "addForm")
	public String addForm(PlatFavoritesItem platFavoritesItem, Model model) {
		model.addAttribute("platFavoritesItem", platFavoritesItem);
		return "modules/home/platFavoritesItem/platFavoritesItemAddForm";
	}

	@RequestMapping(value = "editForm")
	public String editForm(PlatFavoritesItem platFavoritesItem, Model model) {
		platFavoritesItem = platFavoritesItemService.selectPlatFavoritesItemInfo(platFavoritesItem);
		model.addAttribute("platFavoritesItem", platFavoritesItem);
		List<PlatFavorites> list = platFavoritesService.findAllList(new PlatFavorites());
		model.addAttribute("list",list);
		
		return "modules/home/platFavoritesItem/platFavoritesItemEditForm";
	}
	
	@RequestMapping(value = "formSC")
	public String formSC(PlatFavoritesItem favoritesItem, Model model,String id,String type) {
		Map<String, Object> map = getscj();
		model.addAttribute("map", map);
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("favoritesItem", favoritesItem);
		return "modules/home/platFavoritesItem/addscForm";
	}
	
	@RequestMapping(value = "saveSC")
	@ResponseBody
	public Ajax saveSC(PlatFavoritesItem favoritesItem, String linkid,String type,Model model, RedirectAttributes redirectAttributes) {
		//1代表公告信息， 2代表行业资讯
		if("1".equals(type)){
			NoticeInfo info = noticeInfoService.selectNoticeInfoInfo(linkid);
			if(info==null){
				  return new Ajax(false,  "该公告已经不存在，请重新刷新");
			}
			
			Date now = new Date();
			favoritesItem.setTitle(info.getTitle());
			favoritesItem.setLinkid(info.getId());
			favoritesItem.setType("公告信息");
			favoritesItem.setCreateBy(WebUtil.getUser());
			favoritesItem.setCreateDate(now);
			favoritesItem.setUpdateBy(WebUtil.getUser());
			favoritesItem.setUpdateDate(now);
			platFavoritesItemService.save(favoritesItem);
		}else if("2".equals(type)){
			PlatInformation info =platInformationService.queryPlatInFrom(linkid);
			if(info==null){
				  return new Ajax(false,  "该资讯已经不存在，请重新刷新");
			}
			
			Date now = new Date();
			favoritesItem.setTitle(info.getTitle());
			favoritesItem.setLinkid(info.getId());
			favoritesItem.setType("行业资讯");
			favoritesItem.setCreateBy(WebUtil.getUser());
			favoritesItem.setCreateDate(now);
			favoritesItem.setUpdateBy(WebUtil.getUser());
			favoritesItem.setUpdateDate(now);
			platFavoritesItemService.save(favoritesItem);
			
		} else if ("3".equals(type)) {
			File file = new File();
			file.setId(linkid);
			File file1 = fileService.findFile(file);
			if(file1==null){
				return new Ajax(false,  "该知识已经不存在，请重新刷新");
			}

			Date now = new Date();
			String title = file1.getTitle();
			title = title.substring(0, title.lastIndexOf("."));
			favoritesItem.setTitle(title);
			favoritesItem.setLinkid(file1.getId());
			favoritesItem.setType("知识库");
			favoritesItem.setCreateBy(WebUtil.getUser());
			favoritesItem.setCreateDate(now);
			favoritesItem.setUpdateBy(WebUtil.getUser());
			favoritesItem.setUpdateDate(now);
			platFavoritesItemService.save(favoritesItem);
		} else {
			return new Ajax(false,  "该保存类型不存在");
		}
		return new Ajax(true,  "保存收藏条目成功");
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public Ajax save(PlatFavoritesItem platFavoritesItem, Model model) {
		
		PlatFavoritesItem item = platFavoritesItemService.selectPlatFavoritesItemInfo(platFavoritesItem.getId());
		item.setFavoritesid(platFavoritesItem.getFavoritesid());
		Date now = new Date();
		item.setUpdateDate(now);
		platFavoritesItemService.save(item);
		return new Ajax(true,  "保存收藏条目成功");
	}

	@RequestMapping(value = "save2")
	public String save2(PlatFavoritesItem platFavoritesItem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, platFavoritesItem)){
			return editForm(platFavoritesItem, model);
		}
		platFavoritesItemService.save(platFavoritesItem);
		addMessage(redirectAttributes, "保存收藏条目成功");
		return "redirect:"+ "/home/platFavoritesItem/?repage";
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public Ajax delete(PlatFavoritesItem platFavoritesItem) {
		try {
			platFavoritesItemService.deletePlatFavoritesItemInfo(platFavoritesItem);
			return new Ajax(true, "删除收藏条目成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除收藏条目异常");
		}
	}

	@RequestMapping(value = "multiDel")
	@ResponseBody
	public Ajax multiDel(String[] idList) {
		try {
			platFavoritesItemService.batchDelete(idList);
			return new Ajax(true, "删除收藏条目成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除收藏条目异常");
		}
	}
	
	public Map<String, Object> getscj() {
		PlatFavorites favorites=new PlatFavorites();
		favorites.setCreateBy(WebUtil.getUser());
		List<PlatFavorites> list=platFavoritesService.findAllList(favorites);
		Map<String, Object> map = Maps.newHashMap();
		for (int i=0; i<list.size(); i++){
			PlatFavorites e = list.get(i);
			map.put(e.getId(), e.getFavName());
		}
		map.put("002", "默认收藏夹");
		return map;
	}
}
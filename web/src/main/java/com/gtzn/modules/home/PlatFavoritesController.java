/**
 * 
 */
package com.gtzn.modules.home;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.web.BaseController;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.modules.home.entity.PlatFavorites;
import com.gtzn.modules.home.entity.PlatFavoritesItem;
import com.gtzn.modules.home.service.PlatFavoritesItemService;
import com.gtzn.modules.home.service.PlatFavoritesService;
import com.gtzn.web.util.WebUtil;

/**
 * 收藏夹Controller
 * @author wzx
 * @version 2017-04-20
 */
@Controller
@RequestMapping(value = "/platfavorites/platFavorites")
public class PlatFavoritesController extends BaseController {

	@Autowired
	private PlatFavoritesService platFavoritesService;
	@Autowired
	private PlatFavoritesItemService platFavoritesItemService;

	@RequestMapping(value = {"index"})
	public String index(PlatFavorites favorites, Model model) {
		return "modules/home/platFavorites/platFavoritesIndex";
	}	
	@RequestMapping(value = {"list"})
	public String list(PlatFavorites platFavorites, Model model) {
		return "modules/home/platFavorites/platFavoritesList";
	}
	
	@ModelAttribute
	public PlatFavorites get(@RequestParam(required=false) String id) {
		PlatFavorites entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = platFavoritesService.selectPlatFavoritesInfo(id);
		}
		if (entity == null){
			entity = new PlatFavorites();
		}
		return entity;
	}
	
	@RequestMapping(value = {"load"})
	@ResponseBody
	public Pager<PlatFavorites> load(Pager<PlatFavorites> pager, PlatFavorites platFavorites,HttpServletRequest request,HttpServletResponse response) {
		Pager<PlatFavorites> page = platFavoritesService.findPage(pager, platFavorites); 
		return page;
	}
	
	@RequestMapping(value = "form")
	public String form(PlatFavorites platFavorites, Model model) {
		model.addAttribute("platFavorites", platFavorites);
		return "modules/home/platFavorites/platFavoritesForm";
	}
	
	@RequestMapping(value = "addForm")
	public String addForm(PlatFavorites platFavorites, Model model) {
		model.addAttribute("platFavorites", platFavorites);
		return "modules/home/platFavorites/platFavoritesAddForm";
	}

	@RequestMapping(value = "editForm")
	public String editForm(PlatFavorites platFavorites, Model model) {
		platFavorites = platFavoritesService.selectPlatFavoritesInfo(platFavorites);
		model.addAttribute("platFavorites", platFavorites);
		return "modules/home/platFavorites/platFavoritesEditForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Ajax save(PlatFavorites platFavorites, Model model) {
		if(platFavorites.getIsNewRecord()){
			platFavorites.setCreateBy(WebUtil.getUser());
			platFavorites.setCreateDate(new Date());
		}
		
		platFavorites.setUpdateBy(WebUtil.getUser());
		platFavorites.setUpdateDate(new Date());
		platFavoritesService.save(platFavorites);
		return new Ajax(true,  "保存收藏夹成功");
	}

	@RequestMapping(value = "save2")
	public String save2(PlatFavorites platFavorites, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, platFavorites)){
			return editForm(platFavorites, model);
		}
		platFavoritesService.save(platFavorites);
		addMessage(redirectAttributes, "保存收藏夹成功");
		return "redirect:"+ "/home/platFavorites/?repage";
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public Ajax delete(PlatFavorites platFavorites) {
		try {
			platFavoritesService.deletePlatFavoritesInfo(platFavorites);
			PlatFavoritesItem item = new PlatFavoritesItem();
			item.setFavoritesid(platFavorites.getId());
			platFavoritesItemService.deletePlatFavoritesItemInfo(item);
			return new Ajax(true, "删除收藏夹成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除收藏夹异常");
		}
	}

	@RequestMapping(value = "multiDel")
	@ResponseBody
	public Ajax multiDel(String[] idList) {
		try {
			platFavoritesService.batchDelete(idList);
			return new Ajax(true, "删除收藏夹成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除收藏夹异常");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(PlatFavorites favorites,HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		favorites.setCreateBy(WebUtil.getUser());
		List<PlatFavorites> list = platFavoritesService.findAllList(favorites);
		//判断当前实体对象是否包含pId 字段
			Map<String, Object> map2 = Maps.newHashMap();
			map2.put("id", "001");
			map2.put("name","全部收藏");
			map2.put("pId","0");
			mapList.add(map2);
			Map<String, Object> map3 = Maps.newHashMap();
			map3.put("id", "002");
			map3.put("name","默认收藏夹");
			map3.put("pId","001");
			mapList.add(map3);
		for (int i=0; i<list.size(); i++){
			PlatFavorites e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", "001");
			map.put("name", e.getFavName());
			mapList.add(map);
		}
		return mapList;
	}

}
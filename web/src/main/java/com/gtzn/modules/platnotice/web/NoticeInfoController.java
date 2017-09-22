/**
 * 
 */
package com.gtzn.modules.platnotice.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gtzn.modules.sys.utils.DictUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.home.entity.NoticeInfo;
import com.gtzn.modules.home.entity.PlatFavoritesItem;
import com.gtzn.modules.home.service.NoticeInfoService;
import com.gtzn.modules.home.service.PlatFavoritesItemService;
import com.gtzn.modules.sys.entity.Office;
import com.gtzn.modules.sys.service.OfficeService;
import com.gtzn.web.util.WebUtil;

/**
 * 公告信息Controller
 * 
 * @author wangzx
 * @version 2017-04-07
 */
@Controller
@RequestMapping(value = "/notice/noticeInfo")
public class NoticeInfoController extends BaseController {

	@Autowired
	private NoticeInfoService noticeInfoService;
	
	@Autowired
	private PlatFavoritesItemService favoritesItemService;
	
	@Autowired
	private OfficeService OfficeService;


	@RequiresPermissions("notice:noticeInfo:view")
	@RequestMapping(value = { "index" })
	public String index(NoticeInfo noticeInfo, Model model) {
		return "modules/platnotice/noticeInfo/noticeInfoIndex";
	}
	
	@RequiresPermissions("notice:noticeInfo:view")
	@RequestMapping(value = { "list" })
	public String list(String type, NoticeInfo noticeInfo, Model model) {
		// return "modules/notice/noticeInfo/noticeInfoList";
		model.addAttribute("type", type);
		return "modules/platnotice/noticeInfo/noticeInfoList";
	}

	@RequiresPermissions("notice:noticeInfo:view")
	@RequestMapping(value = { "type" })
	public String typeList(String type, NoticeInfo noticeInfo, Model model) {
		// return "modules/notice/noticeInfo/noticeInfoList";
		model.addAttribute("type", type);
		
		String typeTitle="公告信息列表";
		if ((null == type) || type.equals("") || type.equals("all")) {
		} else {

			String typename = DictUtils.getDictLabel(type, "notice_type", "");
			typeTitle = typename;
		}
		
		model.addAttribute("typeTitle", typeTitle);
		
		return "modules/platnotice/noticeInfo/noticeInfoListRight";
	}

	@RequiresPermissions("notice:noticeInfo:edit")
	@RequestMapping(value = { "editList" })
	public String editList(NoticeInfo noticeInfo, Model model) {
		// return "modules/notice/noticeInfo/noticeInfoList";
		return "modules/platnotice/noticeInfo/noticeInfoEditList";
	}

	@RequiresPermissions("notice:noticeInfo:view")
	@RequestMapping(value = { "load" })
	@ResponseBody
	public Pager<NoticeInfo> load(@RequestParam(name = "type", required = false) String type, Pager<NoticeInfo> pager,
			NoticeInfo noticeInfo, HttpServletRequest request, HttpServletResponse response) {

		if ((null == type) || type.equals("") || type.equals("all")) {

			noticeInfo.setType(null);

		} else {
			noticeInfo.setType(type);
		}
		
		//编辑页面可以查询所有
		if ("edit".equals(type)){
			noticeInfo.setType(null);
		}else{
			noticeInfo.setLongInUser(WebUtil.getUser());
		}
		Pager<NoticeInfo> page = noticeInfoService.findPage(pager, noticeInfo);
		//使用ext保存公告类型仅用于页面展示
		List<NoticeInfo> list = page.getList();
		for (NoticeInfo info : list) {
			info.setExt(DictUtils.getDictLabel(info.getType(), "notice_type", ""));
			
			String officeName="";
			if(info.getGkflag().equals("0")&&StringUtils.isNotEmpty(info.getOffice())){
				String officeStr = info.getOffice();
				String[] officeArr = officeStr.split(",");
				for (int i = 0; i < officeArr.length; i++) {
					Office office = OfficeService.get(officeArr[i]); 
					if(office!=null&&StringUtils.isNotEmpty(office.getName())){
						if(i==0){
							officeName=office.getName();	
						}else{
							officeName=officeName+","+office.getName();	
						}
					}
				}
			}
			info.setOfficeName(officeName);
		}
		return page;
	}

	@RequiresPermissions("notice:noticeInfo:view")
	@RequestMapping(value = "addForm")
	public String addForm(NoticeInfo noticeInfo, Model model) {
		model.addAttribute("noticeInfo", noticeInfo);
		return "modules/platnotice/noticeInfo/noticeInfoAddForm";
	}

	@RequiresPermissions("notice:noticeInfo:view")
	@RequestMapping(value = "editForm")
	public String editForm(NoticeInfo noticeInfo, Model model) {
		noticeInfo = noticeInfoService.selectNoticeInfoInfo(noticeInfo);
		String officeName="";
		if(StringUtils.isNotEmpty(noticeInfo.getOffice())){
			String officeStr = noticeInfo.getOffice();
			String[] officeArr = officeStr.split(",");
			for (int i = 0; i < officeArr.length; i++) {
				Office office = OfficeService.get(officeArr[i]); 
				if(office!=null&&StringUtils.isNotEmpty(office.getName())){
					if(i==0){
						officeName=office.getName();	
					}else{
						officeName=officeName+","+office.getName();	
					}
				}

			}
			
		}
		noticeInfo.setOfficeName(officeName);
		model.addAttribute("noticeInfo", noticeInfo);
		return "modules/platnotice/noticeInfo/noticeInfoEditForm";
	}

	@RequiresPermissions("notice:noticeInfo:view")
	@RequestMapping(value = "DetailForm")
	public String DetailForm(HttpServletRequest request, HttpServletResponse response, Model model) {
		NoticeInfo noticeInfo = new NoticeInfo();
		String id = (String) request.getParameter("id");
		noticeInfo.setId(id);
		noticeInfo = noticeInfoService.selectNoticeInfoInfo(noticeInfo);
		if(noticeInfo!=null&&noticeInfo.getContent()!=null)
		{	
			noticeInfo.setContent(StringEscapeUtils.unescapeHtml4(noticeInfo.getContent()));
		}
		
		if(noticeInfo==null)
		{
			model.addAttribute("deletInfo",  "该公告信息已经被删除");
		}
		
		List<PlatFavoritesItem> itemlist = favoritesItemService.findNoticeFromFavorites(id,WebUtil.getUser().getId());
		boolean needsc = false;
		if(itemlist!=null&&itemlist.size()>0){
			needsc =false;
		}else{
			needsc =true;
		}
		model.addAttribute("needsc", needsc);
		model.addAttribute("noticeInfo", noticeInfo);
		return "modules/platnotice/noticeInfo/noticeInfoDetailForm";
	}

	@RequiresPermissions("notice:noticeInfo:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Ajax save(NoticeInfo noticeInfo, Model model) {
		noticeInfoService.save(noticeInfo);
		return new Ajax(true, "保存公告信息成功");
	}

	@RequiresPermissions("notice:noticeInfo:edit")
	@RequestMapping(value = "save2")
	public String save2(NoticeInfo noticeInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, noticeInfo)) {
			return editForm(noticeInfo, model);
		}
		noticeInfoService.save(noticeInfo);
		addMessage(redirectAttributes, "保存公告信息成功");
		return "redirect:" + "/platnotice/noticeInfo/?repage";
	}

	@RequiresPermissions("notice:noticeInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Ajax delete(NoticeInfo noticeInfo) {
		try {
			noticeInfoService.deleteNoticeInfoInfo(noticeInfo);
			return new Ajax(true, "删除公告信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除公告信息异常");
		}
	}

	@RequiresPermissions("notice:noticeInfo:edit")
	@RequestMapping(value = "multiDel")
	@ResponseBody
	public Ajax multiDel(String[] idList) {
		try {
			noticeInfoService.batchDelete(idList);
			return new Ajax(true, "删除公告信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除公告信息异常");
		}
	}
	
	@RequiresPermissions("notice:noticeInfo:edit")
	@ResponseBody
	@RequestMapping(value = "ifFavorites")
	public Ajax ifFavorites(String id){
		List<PlatFavoritesItem> itemlist = favoritesItemService.findNoticeFromFavorites(id,WebUtil.getUser().getId());
		boolean ifFavorites = false;
		if(itemlist!=null&&itemlist.size()>0){
			ifFavorites =true;
		}else{
			ifFavorites =false;
		}
		return new Ajax(true, "查询是否被收藏",ifFavorites);
	}
	
	@RequiresPermissions("notice:noticeInfo:edit")
	@ResponseBody
	@RequestMapping(value = "ifListFavorites")
	public Ajax ifListFavorites(String[] idList){
		List<PlatFavoritesItem> itemlist = favoritesItemService.selectFavoritesByLindIds(idList);
		boolean ifFavorites = false;
		if(itemlist!=null&&itemlist.size()>0){
			ifFavorites =true;
		}else{
			ifFavorites =false;
		}
		return new Ajax(true, "查询是否被收藏",ifFavorites);
	}
}
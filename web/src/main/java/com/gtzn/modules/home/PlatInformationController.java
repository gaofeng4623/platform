/**
 *
 */
package com.gtzn.modules.home;
import com.gtzn.common.config.Global;
import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.digital.service.FileService;
import com.gtzn.modules.home.entity.NoticeInfo;
import com.gtzn.modules.home.entity.PlatFavoritesItem;
import com.gtzn.modules.home.entity.PlatInformation;
import com.gtzn.modules.home.service.NoticeInfoService;
import com.gtzn.modules.home.service.PlatColorService;
import com.gtzn.modules.home.service.PlatFavoritesItemService;
import com.gtzn.modules.home.service.PlatInformationService;
import com.gtzn.web.util.WebUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/plat/platInformation")
public class PlatInformationController extends BaseController {

	@Autowired
	private  PlatInformationService platInformationService;

	@Autowired
	private PlatFavoritesItemService favoritesItemService;
	@Autowired
	private NoticeInfoService noticeInfoService;
	@Autowired
	private PlatColorService platColorService;
	@Autowired
	private FileService fileService;


	@RequestMapping(value = "queryList")
	@ResponseBody
	public List queryList() {
		List datalist = platInformationService.queryPlatInformationList();
		return datalist;
	}
	/**
	 * 读取txt文件向数据库插入数据
	 */
	public void platInformationSave() {
		String txtPath = Global.getConfig("txtPath");
		BufferedReader br = null;
		PlatInformation p=new PlatInformation();
		int plat=0;
		File file=null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String s = null;
			 file = new File(txtPath +"platinfo"+ df.format(new Date()) + ".txt");
			if (file.exists()) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
				while((s = br.readLine())!=null){//使用readLine方法，一次读一行
					String[] strs = s.split("=");
					for (int i = 0; i < strs.length; i++) {
						System.out.println(strs[i]);
						if(i==0){
							p.setTitle(strs[i]);
						}else if(i==1){
							p.setUrl(strs[i]);
						}else if(i==2){
							java.util.Date date_util = sdf.parse(strs[i]);
							p.setReleaseDate(sdf.format(date_util));
						}else if(i==3){
							p.setContent(strs[i]);
						}

					}
					plat=platInformationService.queryPlatInformation(p.getTitle());
					if(plat==0){
						p.setTime(df2.format(new Date()));
						p.setIsNewRecord(true);
						platInformationService.save(p);
					}

				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			// TODO: handle finally clause
			if (file.exists()) {
				try {br.close();} catch (IOException e) {e.printStackTrace();}

			}
		}
	}

	/**
	 * 跳转到更多页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"webIndex"})
	public String webIndex(PlatInformation platInformation, Model model) {
		//return "modules/notice/noticeInfo/noticeInfoList";
		return "modules/home/information/informationWebIndex";
	}
	/**
	 * 跳转到更多页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"weblist"})
	public String weblist(PlatInformation platInformation, Model model) {
		//return "modules/notice/noticeInfo/noticeInfoList";
		return "modules/home/information/informationWebList";
	}
	/**
	 * 跳转到行业资讯维护页面
	 * @param model
	 * @return
	 */
	@RequiresPermissions("plat:platInformation:view")
	@RequestMapping(value = {"list"})
	public String list(PlatInformation platInformation, Model model) {
		//return "modules/notice/noticeInfo/noticeInfoList";
		return "modules/home/information/informationList";
	}
	/**
	 * 分页查询
	 * @param pager
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("plat:platInformation:view")
	@RequestMapping(value = {"load"})
	@ResponseBody
	public Pager<PlatInformation> load(Pager<PlatInformation> pager, PlatInformation platInformation,HttpServletRequest request,HttpServletResponse response) {
		Pager<PlatInformation> page = platInformationService.findPage(pager, platInformation);
		return page;
	}
	/**
	 * 跳转到更多页面
	 * @return
	 */
	@RequiresPermissions("plat:platInformation:view")
	@RequestMapping(value = "/res")
	public ModelAndView res(String url) {
		ModelAndView mode = new ModelAndView("modules/home/information/findList");
		mode.addObject("url",url);
		//return "modules/notice/noticeInfo/noticeInfoList";
		return mode;
	}
	/**
	 * 根据id查询一条信息
	 */
	@RequiresPermissions("plat:platInformation:view")
	@RequestMapping(value = "informationForm")
	public String DetailForm(String id, Model model) {
		PlatInformation platInformation =platInformationService.queryPlatInFrom(id);
	           /* Map map = new HashMap();
	            map.put("userId",WebUtil.getUser().getId());
	            map.put("typeId",platInformation.getId());*/
	         /*   map.put("type","2");
	           PlatColor plat= platColorService.selectTypeId(map);
	             if(plat==null){
	            	 PlatColor color=new PlatColor();
	            	 color.setUserId(WebUtil.getUser().getId());
	            	 color.setTypeId(platInformation.getId());
	            	 color.setType(2);
	            	 color.setIsNewRecord(true);
	            	 platColorService.save(color);;
	             }*/
		if(platInformation==null){
			model.addAttribute("deletInfo",  "该行业资讯已经被删除");
		}
		if(platInformation!=null&&platInformation.getContent()!=null)
		{
			platInformation.setContent(StringEscapeUtils.unescapeHtml4(platInformation.getContent()));
		}
		List<PlatFavoritesItem> itemlist = favoritesItemService.findInforFromFavorites(id,WebUtil.getUser().getId());
		boolean needsc = false;
		if(itemlist!=null&&itemlist.size()>0){
			needsc =false;
		}else{
			needsc =true;
		}
		model.addAttribute("needsc", needsc);

		model.addAttribute("platInformation", platInformation);
		return "modules/home/information/informationForm";
	}
	/**
	 * 根据id查询一条信息
	 * @throws UnsupportedEncodingException
	 */
	@RequiresPermissions("plat:platInformation:view")
	@RequestMapping(value = "go")
	public String go(String id,String type,  Model model) throws UnsupportedEncodingException {
		String jsp = "modules/home/information/informationRetrue";
		if("2".equals(type)){
			PlatInformation platInformation =platInformationService.queryPlatInFrom(id);
			if(platInformation==null){
				model.addAttribute("deletInfo",  "该行业资讯已经被删除");
			}
			if(platInformation!=null&&platInformation.getContent()!=null)
			{
				platInformation.setContent(StringEscapeUtils.unescapeHtml4(platInformation.getContent()));
			}
			List<PlatFavoritesItem> itemlist = favoritesItemService.findInforFromFavorites(id,WebUtil.getUser().getId());
			boolean needsc;
			if(itemlist!=null&&itemlist.size()>0){
				needsc =false;
			}else{
				needsc =true;
			}
			model.addAttribute("needsc", needsc);
			model.addAttribute("platInformation", platInformation);
		}else if("1".equals(type)){
			NoticeInfo notice =noticeInfoService.selectNoticeInfoInfo(id);
			if(notice==null){
				model.addAttribute("deletInfo",  "该公告已经被删除");
			}
			if(notice!=null&&notice.getContent()!=null)
			{
				notice.setContent(StringEscapeUtils.unescapeHtml4(notice.getContent()));
				String date = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(notice.getDate());
				notice.setReleaseDate(date);
			}
			List<PlatFavoritesItem> itemlist = favoritesItemService.findInforFromFavorites(id,WebUtil.getUser().getId());
			boolean needsc;
			if(itemlist!=null&&itemlist.size()>0){
				needsc =false;
			}else{
				needsc =true;
			}
			model.addAttribute("needsc", needsc);
			model.addAttribute("platInformation", notice);
		} else if ("3".equals(type)){
			com.gtzn.modules.digital.entity.File file = new com.gtzn.modules.digital.entity.File();
			file.setId(id);
			com.gtzn.modules.digital.entity.File file1 = fileService.findFile(file);
			List<PlatFavoritesItem> itemlist = favoritesItemService.findInforFromFavorites(id,WebUtil.getUser().getId());
			boolean needsc;
			if(itemlist!=null&&itemlist.size()>0){
				needsc =false;
			}else{
				needsc =true;
			}
			model.addAttribute("needsc", needsc);
			model.addAttribute("file", file1);
			jsp = "modules/platKnowledge/knowledgeView";
		}else{
			PlatInformation platInformation =platInformationService.wholeId(id);
			if(platInformation==null){
				model.addAttribute("deletInfo",  "该资讯已经被删除");
			}
			if(platInformation!=null&&platInformation.getContent()!=null)
			{
				platInformation.setContent(StringEscapeUtils.unescapeHtml4(platInformation.getContent()));
			}
			List<PlatFavoritesItem> itemlist = favoritesItemService.findInforFromFavorites(id,WebUtil.getUser().getId());
			boolean needsc;
			if(itemlist!=null&&itemlist.size()>0){
				needsc =false;
			}else{
				needsc =true;
			}
			model.addAttribute("needsc", needsc);
			model.addAttribute("platInformation", platInformation);
		}

		return jsp;
	}

	/**
	 * 手动导入页面
	 * @param model
	 * @return
	 */
	@RequiresPermissions("plat:platInformation:edit")
	@RequestMapping(value = {"manual"})
	public String manual(PlatInformation platInformation, String title, Model model) {
		//return "modules/notice/noticeInfo/noticeInfoList";
		return "modules/home/information/manual";
	}
	/**
	 * 手动导入
	 */
	@RequestMapping(value = {"platInformationSave"})
	@ResponseBody
	public Ajax platInformationSave(String url) {
		//String txtPath = Global.getConfig("txtPath");
		BufferedReader br = null;
		PlatInformation p=new PlatInformation();
		int plat=0;
		File file=null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String s = null;
			 file = new File(url);
			if (file.exists()) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
				while((s = br.readLine())!=null){//使用readLine方法，一次读一行
					//System.out.println(s);
					//String name=s;
					String[] strs = s.split("=");
					//platInformationService.save(platInformation);
					for (int i = 0; i < strs.length; i++) {
						System.out.println(strs[i]);
							           /* int tmp = i/2;
							            if(i-tmp*2 == 0){
							            	p.setTitle(strs[i]);

							            }else{
							            	p.setUrl(strs[i]);
							            }*/
						if(i==0){
							p.setTitle(strs[i]);
						}else if(i==1){
							p.setUrl(strs[i]);
						}else if(i==2){
							java.util.Date date_util = sdf.parse(strs[i]);
							p.setReleaseDate(sdf.format(date_util));
						}else if(i==3){
							p.setContent(strs[i]);
						}

					}
					plat=platInformationService.queryPlatInformation(p.getTitle());
					if(plat==0){
						p.setTime(df.format(new Date()));
						p.setIsNewRecord(true);
						platInformationService.save(p);
					}

				}
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			// TODO: handle finally clause
			if (file.exists()) {
				try {br.close();} catch (IOException e) {e.printStackTrace();}
				return new Ajax(true, "导入成功");
			}else{
				return new Ajax(true, "导入失败");
			}
		}
	}
	/**
	 * 跳转到检索页面
	 * @param model
	 * @return
	 */
	@RequiresPermissions("plat:platInformation:view")
	@RequestMapping(value = {"retrieval"})
	public String retrieval(PlatInformation platInformation, Model model) {
		//return "modules/notice/noticeInfo/noticeInfoList";
		return "modules/home/information/retrieval";
	}
	/**
	 * 检索条件查询行业信息
	 */
	@RequiresPermissions("plat:platInformation:view")
	@RequestMapping(value="loadData")
	@ResponseBody
	public Ajax loadData(Integer page, Integer rows,String titleCont,Model model){
		List<PlatInformation>list=platInformationService.loadData(page,rows,titleCont);
		List<PlatInformation> plat=new ArrayList<PlatInformation>();
		int count=platInformationService.loadCount(titleCont);
		String con="";
		String conLing="";
		for(int i=0;i<list.size();i++){
			PlatInformation plagtin=new PlatInformation();
			con=list.get(i).getContent().substring(0, 100);
			conLing=con.substring(con.length()-1,con.length());
			int y=0;
			while(y++ < 100)
			{
				conLing=con.substring(con.length()-1,con.length());
				if(conLing.equals("&")||conLing.equals("n")||conLing.equals("b")||conLing.equals("s")||conLing.equals("p")
						||conLing.equals("<")||conLing.equals("/")){
					con=list.get(i).getContent().substring(0, 100+y);
					continue;
				}else{
					break;
				}
			}

			plagtin.setTitle(list.get(i).getTitle());
			plagtin.setTname(list.get(i).getTname());
			plagtin.setContent(con+"......");
			plagtin.setId(list.get(i).getId());
			plat.add(plagtin);
		}
		model.addAttribute("list", plat);
		model.addAttribute("count",count);
		return new Ajax(true,model);
	}
	/**
	 * 检索条件查询公告信息
	 * @return
	 */
	@RequiresPermissions("plat:platInformation:view")
	@RequestMapping(value="loadNotice")
	@ResponseBody
	public Ajax loadNotice(Integer page, Integer rows,String titleCont,Model model){
		List<NoticeInfo>list=noticeInfoService.notePage(page,rows,titleCont);
		List<NoticeInfo> plat=new ArrayList<NoticeInfo>();
		int count=noticeInfoService.noteCount(titleCont);
		String con="";
		String conLing="";
		for(int i=0;i<list.size();i++){
			NoticeInfo plagtin=new NoticeInfo();
			con=list.get(i).getContent();//.substring(0, 80);
			if(con!=null){
				if(con.length()>100){
					con=list.get(i).getContent().substring(0, 100);
					conLing=con.substring(con.length()-1,con.length());
					con=list.get(i).getContent().substring(0, 100);
					int y=0;
					while(y++ < 100)
					{
						conLing=con.substring(con.length()-1,con.length());
						if(isChinese(conLing)){
							con=list.get(i).getContent().substring(0, 100+y);
							continue;
						}else{
							break;
						}
					}
				}

			}
			plagtin.setTitle(list.get(i).getTitle());
			plagtin.setContent(con+"......");
			plagtin.setId(list.get(i).getId());
			plagtin.setTname(list.get(i).getTname());
			plat.add(plagtin);
		}
		model.addAttribute("list", plat);
		model.addAttribute("count",count);
		return new Ajax(true,model);
	}

	/**
	 * 检索条件查询行业信息、公告信息、知识库信息
	 *
	 * @param page      第几页
	 * @param rows      每页条数
	 * @param titleCont 查询条件
	 */
	@RequiresPermissions("plat:platInformation:view")
	@RequestMapping(value = "whole")
	@ResponseBody
	public Ajax whole(Integer page, Integer rows, String titleCont, String type, Model model) {
		List<PlatInformation> list = platInformationService.whole(page, rows, type, titleCont);
		List<PlatInformation> plat = new ArrayList<>();
		int count = platInformationService.wholeCount(type, titleCont);
		String conLing;
		for (PlatInformation aList : list) {
			PlatInformation platInfo = new PlatInformation();
			String content = aList.getContent();
			if (StringUtils.isNotBlank(content)) {
				if (content.length() > 100) {
					content = aList.getContent().substring(0, 100);
					int y = 0;
					while (y++ < 100) {
						conLing = content.substring(content.length() - 1, content.length());
						if (isChinese(conLing)) {
							content = aList.getContent().substring(0, 100 + y);
						} else {
							break;
						}
					}
					platInfo.setContent(content + "......");
				}
			} else {
				platInfo.setContent("");
			}


			platInfo.setTitle(aList.getTitle());
			platInfo.setTname(aList.getTname());
			platInfo.setUrl(aList.getUrl());
			platInfo.setId(aList.getId());
			platInfo.setType(aList.getType());
			plat.add(platInfo);
		}
		model.addAttribute("list", plat);
		model.addAttribute("count", count);
		return new Ajax(true, model);
	}

	/**
	 * @Title: list
	 * @Description: 页面跳转
	 * @param model
	 * @return String    返回类型
	 * @throws
	 */
	@RequiresPermissions("plat:platInformation:view")
	@RequestMapping(value = {"editForm"})
	public String editForm(PlatInformation platInformation, Model model) {
		PlatInformation plt =platInformationService.queryPlatInFrom(platInformation.getId());
		model.addAttribute("pit", plt);
		return "modules/home/information/editForm";
	}

	@RequiresPermissions("plat:platInformation:edit")
	@RequestMapping(value = "updatePlat")
	@ResponseBody
	public Ajax updatePlat(PlatInformation platInformation, Model model) {
			/*	PlatInformation plat=new PlatInformation();
				plat.setTitle(title);
				plat.setId(id);
				plat.setContent(conte);*/
		platInformationService.updatePlat(platInformation);
		return new Ajax(true, "行业信息修改成功");
	}
	/**
	 * @Title: delete
	 * @Description: 删除行业信息
	 * @return Ajax    返回类型
	 * @throws
	 */
	@RequiresPermissions("plat:platInformation:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Ajax delete(PlatInformation platInformation) {
		platInformationService.deletePlatInformation(platInformation);
		return new Ajax(true, "资讯信息删除成功！");
	}

	/**
	 * 检索条件查询行业信息
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "wholeQuery")
	public ModelAndView wholeQuery(String titleCont, Integer page, Integer rows, String type, Model model) throws
			UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView("modules/home/information/retrievalSy");
		if (!"".equals(titleCont) && titleCont != null) {
			titleCont = URLDecoder.decode(titleCont, "UTF-8");
//			List<PlatInformation> list = platInformationService.whole(page, rows, type, titleCont);
//			List<PlatInformation> plat = new ArrayList<PlatInformation>();
//			int count = platInformationService.wholeCount(type, titleCont);
//			String con = "";
//			String conLing = "";
//			for (int i = 0; i < list.size(); i++) {
//				PlatInformation plagtin = new PlatInformation();
//				con = list.get(i).getContent();//.substring(0, 80);
//				if (con != null) {
//					if (con.length() > 100) {
//						conLing = con.substring(con.length() - 1, con.length());
//						con = list.get(i).getContent().substring(0, 100);
//						// con=list.get(i).getContent().substring(0, 100);
//						// conLing=con.substring(con.length()-1,con.length());
//						int y = 0;
//						while (y++ < 100) {
//							conLing = con.substring(con.length() - 1, con.length());
//							if (isChinese(conLing)) {
//								con = list.get(i).getContent().substring(0, 100 + y);
//								continue;
//							} else {
//								break;
//							}
//						}
//
//					}
//				}
//
//
//				plagtin.setTitle(list.get(i).getTitle());
//				plagtin.setContent(con + "......");
//				plagtin.setId(list.get(i).getId());
//				plat.add(plagtin);
//			}

			mav.addObject("titleCont", titleCont);
		}

		return mav;
	}

	public  boolean isChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean flg = true;
		if (matcher.find())
			flg = false;

		return flg;
	}

}
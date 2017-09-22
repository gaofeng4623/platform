/**
 * 
 */
package com.gtzn.modules.home;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.utils.IdGen;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.home.entity.Memo;
import com.gtzn.modules.home.service.MemoService;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.web.util.WebUtil;

/**
 * 便笺Controller
 */
@Controller
@RequestMapping(value = "/home/memo")
public class MemoController extends BaseController {

	@Autowired
	private MemoService memoService;
	
	@ResponseBody
	@RequestMapping(value = {"allList"})
	public Ajax allList(Memo entity, Model model) {
		User user = WebUtil.getUser();
		entity.setCreateUser(user.getLoginName());
		List<Memo> list = memoService.findAllList(entity);
		
		if (list != null && list.size() > 0) {
			for (int i = 0;i<list.size();i++) {
				if  (list.get(i).getContent() == null) {
					list.get(i).setContent("");
				}
			}
			return new Ajax(true,list);
		} else {
			return new Ajax(false);
		}
		
	}
	
	@RequestMapping(value = {"index"})
	public String index(Memo entity, Model model) {
		return "modules/home/sticker/memo";
	}
	
	@ResponseBody
	@RequestMapping(value = "delete")
	public Ajax delete(Memo entity,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		String stickerid = request.getParameter("stickerid");
		memoService.deleteMem(stickerid);
		return new Ajax(true);
	}
	
	@ResponseBody
	@RequestMapping(value = "save")//@Valid 
	public Ajax save(Memo entity,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		String id = request.getParameter("stickerid");
		User user = WebUtil.getUser();
		String contentecd = request.getParameter("content");
		if (contentecd != null && !"".equals(contentecd)) {
			try {
				contentecd = URLDecoder.decode(contentecd,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		if (id != null && !"".equals(id) && !"null".equals(id)) {
			entity.setId(id);
			entity.setIsNewRecord(false);
		} else {
			entity.setId(IdGen.nextLongId()+"");
			entity.setIsNewRecord(true);
		}
		entity.setCreateUser(user.getLoginName());
		entity.setMleft(request.getParameter("left"));
		entity.setMtop(request.getParameter("top"));
		entity.setContent(contentecd);
		memoService.save(entity);
		return new Ajax(true);
	}
}
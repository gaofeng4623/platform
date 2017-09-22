/**
 * 
 */
package com.gtzn.modules.sys.web;

import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.digital.entity.Connoisseur;
import com.gtzn.modules.digital.service.ConnoisseurService;
import com.gtzn.modules.workflow.domain.WFTask;

/**
 * 客户Controller
 * @author 
 * @version
 */
@Controller
@RequestMapping(value = "/sys/connoisseur")
public class ConnoisseurController extends BaseController {

	@Autowired
	private ConnoisseurService connoisseurService;
	private Properties workflowdata;
	public ConnoisseurController() {
		try {
		    InputStream in = this.getClass().getClassLoader().getResourceAsStream("workflow.properties");
		    Properties p = new Properties();
		    p.load(in);
		    in.close();
		    setWorkflowdata(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Properties getWorkflowdata() {
		return workflowdata;
	}
	public void setWorkflowdata(Properties workflowdata) {
		this.workflowdata = workflowdata;
	}
	
	/**
	 * 页面跳转:借阅监督信息页
	 */
	@RequestMapping("/toBorrowInfo")
	public String toBorrowSupervision(Connoisseur entity, Model model,@RequestParam("hrefURL") String hrefURL) {
		model.addAttribute("hrefURL", hrefURL);
		return "modules/sys/doBorrowSupervisionInfo";
	}
	
	@RequestMapping(value = {"toBorrowSupervision"})
	public String list(Connoisseur entity, Model model) {
		String docCheckShowHistory = workflowdata.getProperty("docCheckShowHistory")+"liux/";
		model.addAttribute("docCheckShowHistory", docCheckShowHistory);
		return "modules/sys/borrowSupervisionList";
	}

	@ResponseBody
	@RequestMapping(value = {"load"})
	public Pager<Connoisseur> load(Pager<Connoisseur> page, Connoisseur entity) {
		page = connoisseurService.findPage(page, entity);
		return page;
	}
	
}

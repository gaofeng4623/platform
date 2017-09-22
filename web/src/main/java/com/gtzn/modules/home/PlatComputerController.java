/**
 * 
 */
package com.gtzn.modules.home;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.FixedLengthQueue;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.home.entity.PerfAll;
import com.gtzn.modules.home.entity.PlatComputer;
import com.gtzn.modules.home.entity.PlatPerfCpu;
import com.gtzn.modules.home.entity.PlatPerfDisk;
import com.gtzn.modules.home.entity.PlatPerfMem;
import com.gtzn.modules.home.service.PerfService;
import com.gtzn.modules.home.service.PlatComputerService;
import com.gtzn.modules.home.service.PlatPerfCpuService;
import com.gtzn.modules.home.service.PlatPerfDiskService;
import com.gtzn.modules.home.service.PlatPerfMemService;
import com.gtzn.modules.home.service.WmiService;
import com.gtzn.modules.sys.utils.DictUtils;

/**
 * 服务器管理Controller
 * @author wzx
 * @version 2017-05-08
 */
@Controller
@RequestMapping(value = "/platcomputer/platComputer")
public class PlatComputerController extends BaseController {

	@Autowired
	private PlatComputerService platComputerService;
	@Autowired
	private PlatPerfCpuService cpuService ;
	@Autowired
	private PlatPerfMemService memService ;
	@Autowired
	private PlatPerfDiskService diskService;


	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = {"list"})
	public String list(PlatComputer platComputer, Model model) {
		return "modules/home/platcomputer/platComputerList";
	}
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = {"load"})
	@ResponseBody
	public Pager<PlatComputer> load(Pager<PlatComputer> pager, PlatComputer platComputer,HttpServletRequest request,HttpServletResponse response) {
		Pager<PlatComputer> page = platComputerService.findPage(pager, platComputer); 
		List<PlatComputer> list = page.getList();
		for (PlatComputer platComputer2 : list) {
			platComputer2.setOstype(DictUtils.getDictLabel(platComputer2.getOstype(), "ostype", "未知"));
//			try {
//				boolean online = InetAddress.getByName(platComputer2.getIp()).isReachable(3000);
//				if(online){
//					platComputer2.setOnlineflag("在线");
//				}else{
//					platComputer2.setOnlineflag("离线");
//				}
//			} catch (UnknownHostException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		return page;
	}
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "addForm")
	public String addForm(PlatComputer platComputer, Model model) {
		model.addAttribute("platComputer", platComputer);
		return "modules/home/platcomputer/platComputerAddForm";
	}
	
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "view")
	public String view(String ip,PlatComputer platComputer, Model model,String id) {
		model.addAttribute("ip", ip);
		
		String result= "modules/home/platcomputer/showPerf";
		if(id!=null){
			if(id.equals("day")){
				result =  "modules/home/platcomputer/showPerfDay";
			}else if(id.equals("month")){
				result =  "modules/home/platcomputer/showPerfMonth";
			}else if(id.equals("year")){
				result =  "modules/home/platcomputer/showPerfYear";
			}
		}
		return result;
	}
	
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "index")
	public String index(String ip,PlatComputer platComputer, Model model) {
		model.addAttribute("ip", ip);
		return "modules/home/platcomputer/perfIndex";
	}

	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "editForm")
	public String editForm(PlatComputer platComputer, Model model) {
		platComputer = platComputerService.selectPlatComputerInfo(platComputer);
		model.addAttribute("platComputer", platComputer);
		return "modules/home/platcomputer/platComputerEditForm";
	}

	@RequiresPermissions("platcomputer:platComputer:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Ajax save(PlatComputer platComputer, Model model) {
		boolean isNew = StringUtils.isBlank(platComputer.getId());
		platComputerService.save(platComputer);
		if(!isNew){
			PerfService.removeComputer(platComputer.getIp());
		}
		PerfService perfService = new PerfService();
		PerfService.addComputer(platComputer);
		perfService.collectDataByComputer(platComputer);
		return new Ajax(true,  "保存计算机设备成功");
	}

	@RequiresPermissions("platcomputer:platComputer:edit")
	@RequestMapping(value = "save2")
	public String save2(PlatComputer platComputer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, platComputer)){
			return editForm(platComputer, model);
		}
		platComputerService.save(platComputer);
		addMessage(redirectAttributes, "保存计算机设备成功");
		return "redirect:"+ "modules/platcomputer/?repage";
	}

	@RequiresPermissions("platcomputer:platComputer:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Ajax delete(PlatComputer platComputer) {
		try {
			platComputer = platComputerService.selectPlatComputerInfo(platComputer.getId());
			String ip = platComputer.getIp();
			platComputerService.deletePlatComputerInfo(platComputer);
			PerfService.removeComputer(ip);
			return new Ajax(true, "删除计算机设备成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除计算机设备异常");
		}
	}

	@RequiresPermissions("platcomputer:platComputer:edit")
	@RequestMapping(value = "multiDel")
	@ResponseBody
	public Ajax multiDel(String[] idList) {
		try {
			platComputerService.batchDelete(idList);
			return new Ajax(true, "删除计算机设备成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除计算机设备异常");
		}
	}
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getCpuPerf")
	@ResponseBody
	public List getCpuPerf(String ip) {
		List<PlatPerfCpu> cpuList= new ArrayList<PlatPerfCpu>(); 
		try {
			Map<String, FixedLengthQueue<PerfAll>> map =  PerfService.getMap();
			FixedLengthQueue<PerfAll> perfQueue= map.get(ip);
			if(perfQueue != null){
				PerfAll perfAll= perfQueue.getLast();
				if(perfAll!=null){
					PlatPerfCpu cpu = perfAll.getCpu();
					cpuList.add(cpu);
					}
			}
			return cpuList;
		} catch (Exception e) {
			e.printStackTrace();
			return cpuList;
		}
	}
	
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getMemPerf")
	@ResponseBody
	public List getMemPerf(String ip) {
		List<PlatPerfMem> memList= new ArrayList<PlatPerfMem>(); 
		try {
			Map<String, FixedLengthQueue<PerfAll>> map =  PerfService.getMap();
			FixedLengthQueue<PerfAll> perfQueue= map.get(ip);
			if(perfQueue != null){
				PerfAll perfAll= perfQueue.getLast();
				if(perfAll!=null){
					PlatPerfMem Mem = perfAll.getMem();
					memList.add(Mem);
					}
			}
			return memList;
		} catch (Exception e) {
			e.printStackTrace();
			return memList;
		}
	}
	
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getDiskPerf")
	@ResponseBody
	public List getDiskPerf(String ip) {
		List<PlatPerfDisk> diskList= new ArrayList<PlatPerfDisk>(); 
		try {
			Map<String, FixedLengthQueue<PerfAll>> map =  PerfService.getMap();
			FixedLengthQueue<PerfAll> perfQueue= map.get(ip);
			if(perfQueue != null){
				PerfAll perfAll= perfQueue.getLast();
				if(perfAll!=null){
					diskList = perfAll.getDisk();
					}
			}
			return diskList;
		} catch (Exception e) {
			e.printStackTrace();
			return diskList;
		}
	}
	
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getCpuPerfAll")
	@ResponseBody
	public List getCpuPerfAll(String ip) {
		List<PerfAll> cpuList= new ArrayList<PerfAll>(); 
		try {
			Map<String, FixedLengthQueue<PerfAll>> map =  PerfService.getMap();
			FixedLengthQueue<PerfAll> perfQueue= map.get(ip);
			if(perfQueue != null){
				LinkedBlockingQueue<PerfAll> perfAllQueue= perfQueue.getQueque();
				Iterator<PerfAll> it = perfAllQueue.iterator();
				while(it.hasNext()){
					PerfAll perfAll = it.next();
					if(perfAll!=null){
//						PlatPerfCpu cpu = perfAll.getCpu();
						cpuList.add(perfAll);
					}
				}
			}
			return cpuList;
		} catch (Exception e) {
			e.printStackTrace();
			return cpuList;
		}
	}
	
	

	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getCpuPerfDay")
	@ResponseBody
	public List getCpuPerfDay(String ip) {
		List<PlatPerfCpu> cpuList= new ArrayList<PlatPerfCpu>(); 
		try {
			PlatPerfCpu platPerfCpu = new PlatPerfCpu();
			platPerfCpu.setIp(ip);
			cpuList = cpuService.findAllDayList(platPerfCpu);
			return cpuList;
		} catch (Exception e) {
			e.printStackTrace();
			return cpuList;
		}
	}
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getMemPerfDay")
	@ResponseBody
	public List getMemPerfDay(String ip) {
		List<PlatPerfMem> memList= new ArrayList<PlatPerfMem>(); 
		try {
			PlatPerfMem platPerfMem = new PlatPerfMem();
			platPerfMem.setIp(ip);
			memList = memService.findAllDayList(platPerfMem);
			return memList;
		} catch (Exception e) {
			e.printStackTrace();
			return memList;
		}
	}
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getDiskPerfDay")
	@ResponseBody
	public Map<String, List<PlatPerfDisk>> getDiskPerfDay(String ip) {
		Map<String, List<PlatPerfDisk>> map = new HashMap<String, List<PlatPerfDisk>>();
		List<PlatPerfDisk> diskList= new ArrayList<PlatPerfDisk>(); 
		try {
			PlatPerfDisk platDisk = new PlatPerfDisk();
			platDisk.setIp(ip);
			diskList = diskService.findAllDayList(platDisk);
			for(PlatPerfDisk disk :diskList){
				String name = disk.getName();
				if(map.containsKey(name)){
					map.get(name).add(disk);
				}else{
					map.put(name,new  ArrayList<PlatPerfDisk>());
					map.get(name).add(disk);
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
	}
	
	

	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getCpuPerfMonth")
	@ResponseBody
	public List getCpuPerfMonth(String ip) {
		List<PlatPerfCpu> cpuList= new ArrayList<PlatPerfCpu>(); 
		try {
			PlatPerfCpu platPerfCpu = new PlatPerfCpu();
			platPerfCpu.setIp(ip);
			cpuList = cpuService.findAllMonthList(platPerfCpu);
			return cpuList;
		} catch (Exception e) {
			e.printStackTrace();
			return cpuList;
		}
	}
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getMemPerfMonth")
	@ResponseBody
	public List getMemPerfMonth(String ip) {
		List<PlatPerfMem> memList= new ArrayList<PlatPerfMem>(); 
		try {
			PlatPerfMem platPerfMem = new PlatPerfMem();
			platPerfMem.setIp(ip);
			memList = memService.findAllMonthList(platPerfMem);
			return memList;
		} catch (Exception e) {
			e.printStackTrace();
			return memList;
		}
	}
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getDiskPerfMonth")
	@ResponseBody
	public Map<String, List<PlatPerfDisk>> getDiskPerfMonth(String ip) {
		Map<String, List<PlatPerfDisk>> map = new HashMap<String, List<PlatPerfDisk>>();
		List<PlatPerfDisk> diskList= new ArrayList<PlatPerfDisk>(); 
		try {
			PlatPerfDisk platDisk = new PlatPerfDisk();
			platDisk.setIp(ip);
			diskList = diskService.findAllMonthList(platDisk);
			for(PlatPerfDisk disk :diskList){
				String name = disk.getName();
				if(map.containsKey(name)){
					map.get(name).add(disk);
				}else{
					map.put(name,new  ArrayList<PlatPerfDisk>());
					map.get(name).add(disk);
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
	}

	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getCpuPerfYear")
	@ResponseBody
	public List getCpuPerfYear(String ip) {
		List<PlatPerfCpu> cpuList= new ArrayList<PlatPerfCpu>(); 
		try {
			PlatPerfCpu platPerfCpu = new PlatPerfCpu();
			platPerfCpu.setIp(ip);
			cpuList = cpuService.findAllYearList(platPerfCpu);
			return cpuList;
		} catch (Exception e) {
			e.printStackTrace();
			return cpuList;
		}
	}
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getMemPerfYear")
	@ResponseBody
	public List getMemPerfYear(String ip) {
		List<PlatPerfMem> memList= new ArrayList<PlatPerfMem>(); 
		try {
			PlatPerfMem platPerfMem = new PlatPerfMem();
			platPerfMem.setIp(ip);
			memList = memService.findAllYearList(platPerfMem);
			return memList;
		} catch (Exception e) {
			e.printStackTrace();
			return memList;
		}
	}
	
	@RequiresPermissions("platcomputer:platComputer:view")
	@RequestMapping(value = "getDiskPerfYear")
	@ResponseBody
	public Map<String, List<PlatPerfDisk>> getDiskPerfYear(String ip) {
		Map<String, List<PlatPerfDisk>> map = new HashMap<String, List<PlatPerfDisk>>();
		List<PlatPerfDisk> diskList= new ArrayList<PlatPerfDisk>(); 
		try {
			PlatPerfDisk platDisk = new PlatPerfDisk();
			platDisk.setIp(ip);
			diskList = diskService.findAllYearList(platDisk);
			for(PlatPerfDisk disk :diskList){
				String name = disk.getName();
				if(map.containsKey(name)){
					map.get(name).add(disk);
				}else{
					map.put(name,new  ArrayList<PlatPerfDisk>());
					map.get(name).add(disk);
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
	}

	
	
	/**
	 * 验证IP是否存在
	 * @param ip
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("platcomputer:platComputer:edit")
	@RequestMapping(value = "checkIp")
	public String checkIp(String oldIp,String ip) {
		PlatComputer pc = new PlatComputer();
		pc.setIp(ip);
		if (ip !=null && ip.equals(oldIp)) {
			return "true";
		} else if (ip !=null && platComputerService.findAllList(pc).size() == 0) {
			return "true";
		}
		return "false";
	}

	/**
	 * 验证IP是否在线
	 * @param ip
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("platcomputer:platComputer:edit")
	@RequestMapping(value = "checkIpOnline")
	public Ajax checkIpOnline(String ip,String rownum) {
		try {
			boolean online = InetAddress.getByName(ip).isReachable(3000);
			if(online){
				return new Ajax(true,"在线",rownum);
			}else{
				return new Ajax(true,"离线",rownum);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new Ajax(true,"未知",rownum);
		}
	}
	
	
	
	/**
	 * 验证IP是否在线
	 * @param ip
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("platcomputer:platComputer:edit")
	@RequestMapping(value = "checkcon")
	public Ajax checkcon(String name,String password,String ip) {
		try {
			WmiService wmi = new WmiService(ip,name,password,"");
			if(wmi.checkConnect()){
				return new Ajax(true,"可以联通主机！");
			}else{
				return new Ajax(false,"无法联通主机！");
			}
		} catch (Exception e) {
			return new Ajax(false,"无法联通主机！");
		}
	}
}
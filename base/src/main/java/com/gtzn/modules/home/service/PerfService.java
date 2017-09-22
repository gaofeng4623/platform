package com.gtzn.modules.home.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.gtzn.common.security.Cryptos;
import com.gtzn.common.utils.FixedLengthQueue;
import com.gtzn.common.utils.SpringContextHolder;
import com.gtzn.modules.home.entity.PerfAll;
import com.gtzn.modules.home.entity.PlatComputer;
import com.gtzn.modules.home.entity.PlatPerfDisk;
import com.gtzn.modules.home.service.SSHService.SSHPerfThread;
import com.gtzn.modules.home.service.WmiService.WMIPerfThread;
import com.gtzn.modules.sys.utils.DictUtils;

public class PerfService {
	private static PlatComputerService computerService = SpringContextHolder.getBean(PlatComputerService.class);
	
	private static Map<String, FixedLengthQueue<PerfAll>> map = new HashMap<String, FixedLengthQueue<PerfAll>>();
	private static List<PlatComputer> computerList ;
	public static List<PlatComputer> getComputerList() {
		return computerList;
	}

	public static Map<String, FixedLengthQueue<PerfAll>> getMap() {
		return map;
	}

	public static void setMap(Map<String, FixedLengthQueue<PerfAll>> map) {
		PerfService.map = map;
	}
	public static void removeComputer(String ip){
		for(PlatComputer computer:computerList){
			if(computer.getIp().equals(ip)){
				computerList.remove(computer);
				break;
			}
		}
	}
	
	public static void addComputer(PlatComputer computer){
		computerList.add(computer);
	}
	
	public  void collectDate() {
		computerList = computerService.findAllList(new PlatComputer());		
		
		// 实时性能数据
		if(map==null){
			map = new HashMap<String, FixedLengthQueue<PerfAll>>();
		}

		for (PlatComputer platComputer : computerList) {
			collectDataByComputer(platComputer);
		}
	}
	
	public void collectDataByComputer(PlatComputer platComputer){

		FixedLengthQueue<Float> cpuQue = new FixedLengthQueue<Float>(10);
		FixedLengthQueue<Float> memQue = new FixedLengthQueue<Float>(10);
		FixedLengthQueue<List<PlatPerfDisk>> diskQue = new FixedLengthQueue<List<PlatPerfDisk>>(10);
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		int time_interval = Integer.parseInt(DictUtils.getDictValue("时间间隔", "perf_interval", "30"));
		if (platComputer.getOstype().equals("0")) {
			WmiService wmiService = new WmiService(platComputer.getIp(), platComputer.getName(),
					Cryptos.aesDecrypt(platComputer.getPassword()), "");
			WMIPerfThread runnable = wmiService.new WMIPerfThread(wmiService, map, cpuQue, memQue, diskQue,
					platComputer.getIp());
			service.scheduleAtFixedRate(runnable, 20, time_interval, TimeUnit.SECONDS);
		} else {
			SSHService sshService = new SSHService(platComputer.getIp(), platComputer.getName(), Cryptos.aesDecrypt(platComputer.getPassword()));
			SSHPerfThread runable = sshService.new SSHPerfThread(sshService, map, cpuQue, memQue, diskQue, platComputer.getIp());
			service.scheduleAtFixedRate(runable, 20, time_interval, TimeUnit.SECONDS);
		}
	
	}


}

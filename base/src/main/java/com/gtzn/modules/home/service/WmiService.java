package com.gtzn.modules.home.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.annotation.PostConstruct;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JIProgId;
import org.jinterop.dcom.core.JISession;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.jinterop.dcom.impls.JIObjectFactory;
import org.jinterop.dcom.impls.automation.IJIDispatch;
import org.jinterop.dcom.impls.automation.IJIEnumVariant;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gtzn.common.security.Cryptos;
import com.gtzn.common.utils.FixedLengthQueue;
import com.gtzn.common.utils.JedisUtils;
import com.gtzn.common.utils.SpringContextHolder;
import com.gtzn.modules.home.entity.PerfAll;
import com.gtzn.modules.home.entity.PlatAlarm;
import com.gtzn.modules.home.entity.PlatComputer;
import com.gtzn.modules.home.entity.PlatPerfCpu;
import com.gtzn.modules.home.entity.PlatPerfDisk;
import com.gtzn.modules.home.entity.PlatPerfMem;
import com.gtzn.modules.home.service.SSHService.SSHPerfThread;
import com.gtzn.modules.sys.utils.DictUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA. User: wzx Date: 10/5/17 Time: 8:00 AM To change
 * this template use File | Settings | File Templates.
 */
public class WmiService {

	private JIComServer m_ComStub = null;
	private IJIComObject m_ComObject = null;
	private IJIDispatch m_Dispatch = null;
	private String m_Address = null;
	private JISession m_Session = null;
	private IJIDispatch m_WbemServices = null;

	private String m_UserName = "";
	private String m_Password = "";
	private String m_domin = "";
	// private WmiService wmiService = null;

	public WmiService() {
		// wmiService = new WmiService("192.168.179.132","monitor",
		// "wang123","");
		// wmiService.connect();
	}

	private static final String WMI_CLSID = "76A6415B-CB41-11d1-8B02-00600806D9B6";
	private static final String WMI_PROGID = "WbemScripting.SWbemLocator";

	// private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static PlatPerfCpuService cpuService = SpringContextHolder.getBean(PlatPerfCpuService.class);
	private static PlatPerfMemService memService = SpringContextHolder.getBean(PlatPerfMemService.class);
	private static PlatPerfDiskService diskService = SpringContextHolder.getBean(PlatPerfDiskService.class);
	
	private static PlatAlarmService alarmService = SpringContextHolder.getBean(PlatAlarmService.class);
	
	public WmiService(String address, String name, String password, String domin) {
		JISystem.setAutoRegisteration(true);
		JISystem.getLogger().setLevel(Level.WARNING);
		m_Address = address;
		m_UserName = name;
		m_Password = password;
		m_domin = domin;
	}

	public String query(String strQuery) throws Exception {

		// System.out.println("query:" + strQuery);
		String result = "";
		JIVariant results[] = new JIVariant[0];
		try {
			results = m_WbemServices.callMethodA("ExecQuery", new Object[] { new JIString(strQuery),
					JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM() });
			IJIDispatch wOSd = (IJIDispatch) JIObjectFactory.narrowObject((results[0]).getObjectAsComObject());

			int count = wOSd.get("Count").getObjectAsInt();

			IJIComObject enumComObject = wOSd.get("_NewEnum").getObjectAsComObject();
			IJIEnumVariant enumVariant = (IJIEnumVariant) JIObjectFactory
					.narrowObject(enumComObject.queryInterface(IJIEnumVariant.IID));

			IJIDispatch wbemObject_dispatch = null;

			for (int c = 0; c < count; c++) {

				Object[] values = enumVariant.next(1);
				JIArray array = (JIArray) values[0];
				Object[] arrayObj = (Object[]) array.getArrayInstance();
				for (int j = 0; j < arrayObj.length; j++) {
					wbemObject_dispatch = (IJIDispatch) JIObjectFactory
							.narrowObject(((JIVariant) arrayObj[j]).getObjectAsComObject());
				}

				String str = (wbemObject_dispatch.callMethodA("GetObjectText_", new Object[] { 1 }))[0]
						.getObjectAsString2();
				// System.out.println("(" + c + "):");
				// System.out.println(str);
				// System.out.println();
				result = result+str+"###";
			}
		} catch (JIException e) {
			
			throw new Exception(e);
		}
		return result;
	}
	
	public boolean checkConnect() throws Exception{
		try {
			m_Session = JISession.createSession(m_domin, m_UserName, m_Password);
			m_Session.useSessionSecurity(true);
			m_Session.setGlobalSocketTimeout(5000);
			if(!InetAddress.getByName(m_Address).isReachable(3000)){
				throw new Exception("主机不可达");
			}
			m_ComStub = new JIComServer(JIProgId.valueOf(WMI_PROGID), m_Address, m_Session);

			IJIComObject unknown = m_ComStub.createInstance();
			m_ComObject = unknown.queryInterface(WMI_CLSID);

			m_Dispatch = (IJIDispatch) JIObjectFactory.narrowObject(m_ComObject.queryInterface(IJIDispatch.IID));
			JIVariant results[] = m_Dispatch.callMethodA("ConnectServer",
					new Object[] { new JIString(m_Address), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(),
							JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(), 0,
							JIVariant.OPTIONAL_PARAM() });

			m_WbemServices = (IJIDispatch) JIObjectFactory.narrowObject((results[0]).getObjectAsComObject()); 
			if (m_Session != null) {
				try {
					JISession.destroySession(m_Session);
				} catch (JIException e1) {
					
				}
			}
			return true;
		}catch (Exception e) {
			if (m_Session != null) {
				try {
					JISession.destroySession(m_Session);
				} catch (JIException e1) {
					
				}
			}
			throw e;
		}
	}
	

	public void connect() {
		try {

			m_Session = JISession.createSession(m_domin, m_UserName, m_Password);
			m_Session.useSessionSecurity(true);
			m_Session.setGlobalSocketTimeout(5000);
			if(!InetAddress.getByName(m_Address).isReachable(3000)){
				PlatAlarm alarm = new PlatAlarm();
				alarm.setAlarmDevicename(m_Address);
				alarm.setAlarmGrades("严重");
				alarm.setAlarmType("网络连接");
				alarm.setContent("设备无法ping通，请检查网络！");
				alarm.setCreateTime(new Date());
				alarm.setIsdeal("否");
				alarmService.save(alarm);
				throw new Exception("主机不可达");
			}
			m_ComStub = new JIComServer(JIProgId.valueOf(WMI_PROGID), m_Address, m_Session);

			IJIComObject unknown = m_ComStub.createInstance();
			m_ComObject = unknown.queryInterface(WMI_CLSID);

			m_Dispatch = (IJIDispatch) JIObjectFactory.narrowObject(m_ComObject.queryInterface(IJIDispatch.IID));
			JIVariant results[] = m_Dispatch.callMethodA("ConnectServer",
					new Object[] { new JIString(m_Address), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(),
							JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(), 0,
							JIVariant.OPTIONAL_PARAM() });

			m_WbemServices = (IJIDispatch) JIObjectFactory.narrowObject((results[0]).getObjectAsComObject());

		} catch (JIException e) {
			
			PlatAlarm alarm = new PlatAlarm();
			alarm.setAlarmDevicename(m_Address);
			alarm.setAlarmGrades("严重");
			alarm.setAlarmType("WMI连接");
			alarm.setContent("设备无法通过WMI连通！");
			alarm.setCreateTime(new Date());
			alarm.setIsdeal("否");
			alarmService.save(alarm);
			if (m_Session != null) {
				try {
					JISession.destroySession(m_Session);
				} catch (JIException e1) {
				}
			}
		} catch (UnknownHostException e) {
			
			PlatAlarm alarm = new PlatAlarm();
			alarm.setAlarmDevicename(m_Address);
			alarm.setAlarmGrades("严重");
			alarm.setAlarmType("WMI连接");
			alarm.setContent("设备无法通过WMI连通！");
			alarm.setCreateTime(new Date());
			alarm.setIsdeal("否");
			alarmService.save(alarm);
			if (m_Session != null) {
				try {
					JISession.destroySession(m_Session);
				} catch (JIException e1) {
					// logger.error(e.getMessage(), e);
				}
			}
		}catch (Exception e) {
			PlatAlarm alarm = new PlatAlarm();
			alarm.setAlarmDevicename(m_Address);
			alarm.setAlarmGrades("严重");
			alarm.setAlarmType("WMI连接");
			alarm.setContent("设备无法通过WMI连通！");
			alarm.setCreateTime(new Date());
			alarm.setIsdeal("否");
			alarmService.save(alarm);
			if (m_Session != null) {
				try {
					JISession.destroySession(m_Session);
				} catch (JIException e1) {
					// logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public void disconnect() {
		try {
			JISession.destroySession(m_Session);
		} catch (JIException e) {
			
		}
	}

	public PlatPerfCpu saveCpuPerf() throws Exception {
		PlatPerfCpu cpu = new PlatPerfCpu();
		// CPU信息
		String PercentProcessorTime = "0";
		
		String processor = query("SELECT * FROM Win32_PerfFormattedData_PerfOS_Processor WHERE Name = '_Total'");
		PercentProcessorTime = getValueFromString(processor, "PercentProcessorTime");
		cpu.setIp(m_Address);
		cpu.setPercent(PercentProcessorTime);
		cpu.setCollecttime(new Date());
		cpuService.save(cpu);
		
		return cpu;
	}

	public PlatPerfMem saveMemPerf() throws Exception {
		PlatPerfMem Mem = new PlatPerfMem();
		
		// 系统信息
		String ComputerSystem = query("SELECT * FROM Win32_ComputerSystem");
		String totalMemory = getValueFromString(ComputerSystem, "TotalPhysicalMemory");

		// 内存信息
		String memory = query("SELECT * FROM Win32_PerfFormattedData_PerfOS_Memory");
		String freeMem = getValueFromString(memory, "AvailableBytes");
		Mem.setIp(m_Address);
		Long total = Long.valueOf(totalMemory);
		Long free = Long.valueOf(freeMem);
		Mem.setTotal(total);
		Mem.setFree(free);
		Mem.setCollectdate(new Date());
		if (total != 0) {
			Mem.setPercent((total - free) / (double) total*100 + "");
		}
		memService.save(Mem);
		
		return Mem;
	}

	public List<PlatPerfDisk> saveDiskPerf() throws Exception {
		List<PlatPerfDisk> diskList = new ArrayList<PlatPerfDisk>();
		
		String disksStr = query("SELECT * FROM Win32_LogicalDisk  where DriveType='3'");
		String []diskArr = disksStr.split("###");
		for(int i = 0 ;i<diskArr.length;i++){
			PlatPerfDisk perfdisk = new PlatPerfDisk();
			String disk = diskArr[i];
			String totalDisk = getValueFromString(disk, "Size");
			String freeDisk = getValueFromString(disk, "FreeSpace");
			String name = getValueFromString(disk, "Caption");
			perfdisk.setIp(m_Address);
			Long total = Long.valueOf(totalDisk);
			Long free = Long.valueOf(freeDisk);
			perfdisk.setTotal(total);
			perfdisk.setFree(free);
			perfdisk.setName(name);
			perfdisk.setCollectdate(new Date());
			if (total != 0) {
				perfdisk.setPercent((total - free) / (double) total*100 + "");
			}
			diskService.save(perfdisk);
			diskList.add(perfdisk);
		}	
		
		return diskList;
	}
	
	

	class WMIPerfThread implements Runnable {

		public WMIPerfThread(WmiService wmiService, Map<String, FixedLengthQueue<PerfAll>> map,
				FixedLengthQueue<Float> cpuQue, FixedLengthQueue<Float> memQue,
				FixedLengthQueue<List<PlatPerfDisk>> diskQue, String ip) {

			this.map = map;
			this.wmiService = wmiService;
			this.cpuQue = cpuQue;
			this.memQue = memQue;
			this.diskQue = diskQue;
			this.ip = ip;

		}

		FixedLengthQueue<Float> cpuQue;
		FixedLengthQueue<Float> memQue;
		FixedLengthQueue<List<PlatPerfDisk>> diskQue;
		Map<String, FixedLengthQueue<PerfAll>> map;
		WmiService wmiService;
		String ip;

		public void run() {
			try {
				
				PlatComputer com = null;
				for (PlatComputer computer: PerfService.getComputerList()) {
					if(computer.getIp().equals(ip)){
						com = computer;
						break;
					}
				}
				if(com == null){
					return;
				}
				if (map.get(ip) == null) {
					map.put(ip, new FixedLengthQueue<PerfAll>(100));
					wmiService.connect();
				}
				FixedLengthQueue<PerfAll> queue = map.get(ip);
				PlatPerfCpu cpu = wmiService.saveCpuPerf();
				PlatPerfMem mem = wmiService.saveMemPerf();
				List<PlatPerfDisk> disk = wmiService.saveDiskPerf();
				if(cpu!=null&&mem!=null&&disk!=null){
					PerfAll pa = new PerfAll();
					pa.setCpu(cpu);
					pa.setMem(mem);
					pa.setDisk(disk);
					if(queue.getQueque().size()==0){
						createDataForInit(pa,queue);
					}
					queue.add(pa);
				
					// 告警处理
					cpuQue.add(Float.valueOf(cpu.getPercent()));
					memQue.add(Float.valueOf(mem.getPercent()));
					diskQue.add(disk);
					addAlarm(cpuQue, 80, 90,ip,"CPU");
					addAlarm(memQue, 80, 90,ip,"Memory");
					addAlarmDisk(diskQue,  90,ip);
				}
			} catch (Exception e) {
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
				}
				wmiService.disconnect();
				wmiService.connect();
			}
		}
	};
	
	public void createDataForInit(PerfAll pa,FixedLengthQueue<PerfAll> queue){
		for(int i = 0;i<10;i++){
			PerfAll pall = new PerfAll();
			PlatPerfCpu cpu = new PlatPerfCpu();
			cpu.setPercent(pa.getCpu().getPercent());
			cpu.setCollecttime(new Date((pa.getCpu().getCollecttime().getTime()-30*1000*(10-i))));
			pall.setCpu(cpu);
			PlatPerfMem mem = new PlatPerfMem();
			mem.setPercent(pa.getMem().getPercent());
			mem.setCollectdate(new Date((pa.getMem().getCollectdate().getTime()-30*1000*(10-i))));
			pall.setMem(mem);
			List<PlatPerfDisk> disklist = new ArrayList<PlatPerfDisk>();
			for(int j = 0 ;j<pa.getDisk().size();j++){
				PlatPerfDisk pd = new PlatPerfDisk();
				pd.setPercent(pa.getDisk().get(j).getPercent());
				pd.setName(pa.getDisk().get(j).getName());
				pd.setCollectdate(new Date((pa.getDisk().get(j).getCollectdate().getTime()-30*1000*(10-i))));
				disklist.add(pd);
			}
			pall.setDisk(disklist);
			queue.add(pall);
		}
		
	}
	public static String getValueFromString(String source, String need) {
		if (source == null || source.equals("")) {
			return "";
		}
		int begin = source.indexOf(need);
		int beginE = source.indexOf("=", begin);
		int end = source.indexOf(";", begin);
		return source.substring(beginE + 3, end - 1);
	}

	public static String addAlarm(FixedLengthQueue<Float> queue, double linjie, double alarmValue,String ip,String type) {
		Float total = 0f;
		int i = 0;
		for (Object value : queue.getQueue()) {
			i++;
			if ((Float) value < linjie) {
				queue.clear();
				return "";
			}
			total = total + (Float)value;
		}

		if (i == 10 && (total / i > alarmValue)) {
			String ressult = type+"利用率超过："+alarmValue+"%!";
			PlatAlarm alarm = new PlatAlarm();
			alarm.setAlarmDevicename(ip);
			alarm.setAlarmGrades("一般");
			alarm.setAlarmType("设备性能");
			alarm.setContent(ressult);
			alarm.setCreateTime(new Date());
			alarm.setIsdeal("否");
			alarmService.save(alarm);
			queue.clear();
			return ressult;
		} else {
			return "";
		}
	}
	
	public static String addAlarmDisk(FixedLengthQueue<List<PlatPerfDisk>> queue, double alarmValue,String ip) {
		if( queue.getQueue().remainingCapacity() != 0){
			return "";
		}
		List<PlatPerfDisk> diskList = queue.getQueue().peek();
		if(diskList!=null){
			for(int i = 0;i<diskList.size();i++){
				PlatPerfDisk disk = diskList.get(i);
				String name = disk.getName();
				Double value = Double.valueOf(disk.getPercent());
				if(value > alarmValue){
					queue.getQueue().clear();
					String ressult = "磁盘"+name+"利用率超过:"+alarmValue+"%!";
					PlatAlarm alarm = new PlatAlarm();
					alarm.setAlarmDevicename(ip);
					alarm.setAlarmGrades("一般");
					alarm.setAlarmType("设备性能");
					alarm.setContent(ressult);
					alarm.setCreateTime(new Date());
					alarm.setIsdeal("否");
					alarmService.save(alarm);
					return ressult;
				}
			}
		}
		return 	"";
	}
	
}


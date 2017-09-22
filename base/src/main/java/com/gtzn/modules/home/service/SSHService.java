package com.gtzn.modules.home.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.gtzn.common.utils.FixedLengthQueue;
import com.gtzn.common.utils.SpringContextHolder;
import com.gtzn.modules.home.entity.PerfAll;
import com.gtzn.modules.home.entity.PlatAlarm;
import com.gtzn.modules.home.entity.PlatComputer;
import com.gtzn.modules.home.entity.PlatPerfCpu;
import com.gtzn.modules.home.entity.PlatPerfDisk;
import com.gtzn.modules.home.entity.PlatPerfMem;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSHService {
	
	private static PlatPerfCpuService cpuService = SpringContextHolder.getBean(PlatPerfCpuService.class);
	private static PlatPerfMemService memService = SpringContextHolder.getBean(PlatPerfMemService.class);
	private static PlatPerfDiskService diskService = SpringContextHolder.getBean(PlatPerfDiskService.class);
	private static PlatAlarmService alarmService = SpringContextHolder.getBean(PlatAlarmService.class);
	
	private Connection conn = null;
	private String hostname = null;
	private String username = null;
	private String password = null;

	public SSHService(String hostname, String username, String password) {
		super();
		this.hostname = hostname;
		this.username = username;
		this.password = password;
	}

	public boolean connect() {
		conn = new Connection(hostname);
		/* Now connect */
		boolean isAuthenticated = false;
		try {
			conn.connect();
			isAuthenticated = conn.authenticateWithPassword(username, password);
			if(!isAuthenticated){
				PlatAlarm alarm = new PlatAlarm();
				alarm.setAlarmDevicename(hostname);
				alarm.setAlarmGrades("严重");
				alarm.setAlarmType("网络连接");
				alarm.setContent("无法通过SSH连接主机");
				alarm.setCreateTime(new Date());
				alarm.setIsdeal("否");
				alarmService.save(alarm);
			}
		} catch (Exception e) {
			PlatAlarm alarm = new PlatAlarm();
			alarm.setAlarmDevicename(hostname);
			alarm.setAlarmGrades("严重");
			alarm.setAlarmType("网络连接");
			alarm.setContent("无法通过SSH连接主机");
			alarm.setCreateTime(new Date());
			alarm.setIsdeal("否");
			alarmService.save(alarm);
			conn.close();
			try {
				Thread.sleep(100000);
				connect();
			} catch (Exception e1) {
			}
		}
		return isAuthenticated;
	}

	public Connection getConnect() {
		return conn;
	}

//	public  void collect() {
//		boolean isAuthenticated = false;
//		SSHService ssh = new SSHService("192.168.12.217", "wangzx", "wang123");
//		isAuthenticated = ssh.connect();
//
//		if (isAuthenticated == false) {
//			System.out.println("SSH Login  Authentication failed.");
//		} else {
//			while (true) {
//				Connection conn = ssh.getConnect();
//				try {
//					/* Create a session */
//					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@当前时间：" + new Date());
//					Session sess = conn.openSession();
//					getCpuInfo(sess);
//					sess.close();
//					sess = conn.openSession();
//					getMemInfo(sess);
//					sess.close();
//					sess = conn.openSession();
//					getDiskInfo(sess);
//					sess.close();
//					Thread.sleep(1000);
//				} catch (Exception e) {
//					e.printStackTrace();
//					ssh.connect();
//				}
//			}
//		}
//
//	}

	public  PlatPerfCpu saveCpuPerf(Connection conn) throws Exception {
		PlatPerfCpu cpu = new PlatPerfCpu();
		Session sess = conn.openSession();
		InputStream is = null;
		BufferedReader brStat = null;
		StringTokenizer tokenStat = null;
		String str = "";
		int  cpuidle = 0;
		try {
			sess.execCommand("vmstat");
			is = new StreamGobbler(sess.getStdout());
			brStat = new BufferedReader(new InputStreamReader(is));
			brStat.readLine();
			brStat.readLine();

			str = brStat.readLine();
			if (str != null) {
				tokenStat = new StringTokenizer(str);
				for (int i = 0; i < 14; i++) {
					tokenStat.nextToken();
				}
				cpuidle = new Integer(tokenStat.nextToken()).intValue();
			}
			brStat.close();
			sess.close();
			cpu.setIp(hostname);
			cpu.setPercent(String.valueOf(100 - cpuidle));
			cpu.setCollecttime(new Date());
			cpuService.save(cpu);
		} catch (Exception e) {
			sess.close();
			throw e;
		}
		return cpu;
	}

	public PlatPerfMem saveMemPerf(Connection conn) throws Exception  {
		Session sess = conn.openSession();
		PlatPerfMem Mem = new PlatPerfMem();
		InputStream is = null;
		BufferedReader brStat = null;
		StringTokenizer tokenStat = null;
		String str = "";
		int  total = 1, used = 0;
		try {
			sess.execCommand("free");
			is = new StreamGobbler(sess.getStdout());
			brStat = new BufferedReader(new InputStreamReader(is));
			brStat.readLine();
			str = brStat.readLine();
			if (str != null) {
				tokenStat = new StringTokenizer(str);
				for (int i = 0; i < 3; i++) {

					String result = tokenStat.nextToken();

					if (i == 1) {
						total = new Integer(result).intValue();
					}
					if (i == 2) {
						used = new Integer(result).intValue();
					}
				}
			}
			
			brStat.close();
			sess.close();
			Mem.setIp(hostname);
			Mem.setTotal((long) total);
			Mem.setFree((long) (total-used));
			Mem.setCollectdate(new Date());
			if (total != 0) {
				Mem.setPercent(used /(double)total * 100 + "");
			}
			memService.save(Mem);
		} catch (Exception e) {
			sess.close();
			throw e;
		}
		return Mem;
	}

	public List<PlatPerfDisk> saveDiskPerf(Connection conn) throws Exception { 
		Session sess = conn.openSession();
		List<PlatPerfDisk> diskList = new ArrayList<PlatPerfDisk>();
		InputStream is = null;
		BufferedReader brStat = null;
		StringTokenizer tokenStat = null;
		String str = "";
		String total = "";
		String used = "";
		float percent = 0.0f;
		String name = "";
		try {
			sess.execCommand("df -k");
			is = new StreamGobbler(sess.getStdout());
			brStat = new BufferedReader(new InputStreamReader(is));
			brStat.readLine();
			while ((str = brStat.readLine()) != null) {
				tokenStat = new StringTokenizer(str);
				boolean needCollect = true;
				for (int i = 0; i <= 4; i++) {
					needCollect = true;
					String result = tokenStat.nextToken();
					if (i == 0) {
						name = result;
						if (!result.contains("dev") || result.contains("sr0") || result.contains("tmpfs")) {
							needCollect = false;
							break;
						}
					}
					if (i == 1) {
						total = result;
					}
					if (i == 2) {
						used = result;
					}
					if (i == 4) {
						percent = new Float(result.replace("%", "")).floatValue();
						if (needCollect) {
							PlatPerfDisk perfdisk = new PlatPerfDisk();
							perfdisk.setIp(hostname);
							Long totalDisk = Long.valueOf(total);
							Long usedDisk = Long.valueOf(used);
							Long freeDisk = totalDisk - usedDisk;
							perfdisk.setTotal(totalDisk);
							perfdisk.setFree(freeDisk);
							perfdisk.setName(name);
							perfdisk.setCollectdate(new Date());
							if (totalDisk != 0) {
								perfdisk.setPercent(usedDisk / (double) totalDisk*100 + "");
							}
							diskService.save(perfdisk);
							diskList.add(perfdisk);
						}
					}
				}

			}
			brStat.close();
			sess.close();
		} catch (IOException e) {
			sess.close();
			throw e;
		}
		return diskList;
	}
	
	
	
	class SSHPerfThread implements Runnable {

		public SSHPerfThread(SSHService sshService, Map<String, FixedLengthQueue<PerfAll>> map,
				FixedLengthQueue<Float> cpuQue, FixedLengthQueue<Float> memQue,
				FixedLengthQueue<List<PlatPerfDisk>> diskQue, String ip) {

			this.map = map;
			this.sshService = sshService;
			this.cpuQue = cpuQue;
			this.memQue = memQue;
			this.diskQue = diskQue;
			this.ip = ip;

		}

		FixedLengthQueue<Float> cpuQue;
		FixedLengthQueue<Float> memQue;
		FixedLengthQueue<List<PlatPerfDisk>> diskQue;
		Map<String, FixedLengthQueue<PerfAll>> map;
		SSHService sshService;
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
					sshService.connect();
				}
				FixedLengthQueue<PerfAll> queue = map.get(ip);
				PlatPerfCpu cpu = sshService.saveCpuPerf(sshService.getConnect());
				PlatPerfMem mem = sshService.saveMemPerf(sshService.getConnect());
				List<PlatPerfDisk> disk = sshService.saveDiskPerf(sshService.getConnect());
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
				sshService.getConnect().close();
				sshService.connect();
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

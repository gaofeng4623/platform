package com.gtzn.modules.home.entity;

import java.util.List;

public 	class PerfAll {
	public PlatPerfCpu getCpu() {
		return cpu;
	}
	public void setCpu(PlatPerfCpu cpu) {
		this.cpu = cpu;
	}
	public PlatPerfMem getMem() {
		return mem;
	}
	public void setMem(PlatPerfMem mem) {
		this.mem = mem;
	}
	public List<PlatPerfDisk> getDisk() {
		return disk;
	}
	public void setDisk(List<PlatPerfDisk> disk) {
		this.disk = disk;
	}
	PlatPerfCpu cpu;
	PlatPerfMem mem;
	List<PlatPerfDisk> disk;


}

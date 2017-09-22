package com.gtzn.modules.monitor.entity;

public class FunctionInfo
{
	private int _id = 0;
	private int _functionNo = 0;
	private String _functionName = "";
	private String _functionImageName = "";
	private FloorInfo[] _floorInfos = null;
	private Integer[] _historyTypes = null;
	private Integer[] _alarmTypes = null;
	private Integer[] _deviceTypes = null;
	private int _isShowMonitor = 1;

	public int getIsShowMonitor()
	{
		return _isShowMonitor;
	}
	public void setIsShowMonitor(int isShowMonitor)
	{
		_isShowMonitor = isShowMonitor;
	}
	public int getId()
	{
		return _id;
	}
	public void setId(int id)
	{
		_id = id;
	}
	public int getFunctionNo()
	{
		return _functionNo;
	}
	public void setFunctionNo(int functionNo)
	{
		_functionNo = functionNo;
	}
	public String getFunctionName()
	{
		return _functionName;
	}
	public void setFunctionName(String functionName)
	{
		_functionName = functionName;
	}
	public FloorInfo[] getFloorInfos()
	{
		return _floorInfos;
	}
	public void setFloorInfos(FloorInfo[] floorInfos)
	{
		_floorInfos = floorInfos;
	}
	public Integer[] getHistoryTypes()
	{
		return _historyTypes;
	}
	public void setHistoryTypes(Integer[] historyTypes)
	{
		_historyTypes = historyTypes;
	}
	public Integer[] getAlarmTypes()
	{
		return _alarmTypes;
	}
	public void setAlarmTypes(Integer[] alarmTypes)
	{
		_alarmTypes = alarmTypes;
	}
	public Integer[] getDeviceTypes()
	{
		return _deviceTypes;
	}
	public void setDeviceTypes(Integer[] deviceTypes)
	{
		_deviceTypes = deviceTypes;
	}
	public String getFunctionImageName()
	{
		return _functionImageName;
	}
	public void setFunctionImageName(String functionImageName)
	{
		_functionImageName = functionImageName;
	}	
}

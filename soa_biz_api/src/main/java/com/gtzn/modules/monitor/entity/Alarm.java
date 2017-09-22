package com.gtzn.modules.monitor.entity;

public class Alarm
{
	private int _alarmId = 0;				// 报警编号
	private String _createTime = "";		// 创建时间
	private int _alarmDeviceId = 0;			// 报警设备编号
	private String _alarmDeviceName = "";	// 报警设备名称
	private String _alarmTypeName = "";		// 报警类型名称
	private String _lastAlarmTime = "";		// 最后报警时间
	private String _roomName = "";			// 异常发生地
	private String _linkageAction = "";		// 应急预案
	private String _userName = "";			//
	private String _mention = "";			// 目前状态
	private int _isDeal = 0;				// 是否处理
	private String _dealTime = "";			// 处理时间
	private int _x = 0;						//
	private int _y = 0;						//
	private int _alarmTypeId = 0;			//报警类型id
	private EventPicture[] _eventPictures = null;
	private String startDate = "";			//表单搜索条件---报警时间起
	private String endDate = "";			//表单搜索条件---报警时间止
	private String grades = "";				//等级
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null)
		{
			return false;
		}

		if (!(obj instanceof Alarm))
		{
			return false;
		}

		Alarm rs = (Alarm) obj;
		boolean ret = _alarmId == rs._alarmId;
		if (!ret)
		{
			return false;
		}

		ret = _isDeal == rs._isDeal;
		if (!ret)
		{
			return false;
		}
		return true;
	}

	public int getAlarmId()
	{
		return _alarmId;
	}
	public void setAlarmId(int alarmId)
	{
		_alarmId = alarmId;
	}
	public String getCreateTime()
	{
		return _createTime;
	}
	public void setCreateTime(String createTime)
	{
		_createTime = createTime;
	}
	public int getAlarmDeviceId()
	{
		return _alarmDeviceId;
	}
	public void setAlarmDeviceId(int alarmDeviceId)
	{
		_alarmDeviceId = alarmDeviceId;
	}
	public String getAlarmDeviceName()
	{
		return _alarmDeviceName;
	}
	public void setAlarmDeviceName(String alarmDeviceName)
	{
		_alarmDeviceName = alarmDeviceName;
	}

	public String getAlarmTypeName()
	{
		return _alarmTypeName;
	}

	public void setAlarmTypeName(String alarmTypeName)
	{
		_alarmTypeName = alarmTypeName;
	}

	public String getLastAlarmTime()
	{
		return _lastAlarmTime;
	}
	public void setLastAlarmTime(String lastAlarmTime)
	{
		_lastAlarmTime = lastAlarmTime;
	}
	public String getRoomName()
	{
		return _roomName;
	}
	public void setRoomName(String roomName)
	{
		_roomName = roomName;
	}
	public String getLinkageAction()
	{
		return _linkageAction;
	}
	public void setLinkageAction(String linkageAction)
	{
		_linkageAction = linkageAction;
	}
	public String getUserName()
	{
		return _userName;
	}
	public void setUserName(String userName)
	{
		_userName = userName;
	}
	public String getMention()
	{
		return _mention;
	}
	public void setMention(String mention)
	{
		_mention = mention;
	}
	public int getIsDeal()
	{
		return _isDeal;
	}
	public void setIsDeal(int isDeal)
	{
		_isDeal = isDeal;
	}
	public String getDealTime()
	{
		return _dealTime;
	}
	public void setDealTime(String dealTime)
	{
		_dealTime = dealTime;
	}

	public int getX()
	{
		return _x;
	}

	public void setX(int x)
	{
		_x = x;
	}

	public int getY()
	{
		return _y;
	}

	public void setY(int y)
	{
		_y = y;
	}

	public int getAlarmTypeId()
	{
		return _alarmTypeId;
	}

	public void setAlarmTypeId(int alarmTypeId)
	{
		_alarmTypeId = alarmTypeId;
	}

	public EventPicture[] getEventPictures()
	{
		return _eventPictures;
	}

	public void setEventPictures(EventPicture[] eventPictures)
	{
		_eventPictures = eventPictures;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getGrades() {
		return grades;
	}

	public void setGrades(String grades) {
		this.grades = grades;
	}
	
}

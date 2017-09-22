package com.gtzn.modules.monitor.entity;

public class EventPicture
{
	private int _id = 0;
	private String _createTime = "";
	private int _eventId = -1;
	private int _deviceId = -1;
	private String _picture = "";
	private int _cameraId = -1;

	public int getId()
	{
		return _id;
	}

	public void setId(int id)
	{
		_id = id;
	}

	public String getCreateTime()
	{
		return _createTime;
	}

	public void setCreateTime(String createTime)
	{
		_createTime = createTime;
	}

	public int getEventId()
	{
		return _eventId;
	}

	public void setEventId(int eventId)
	{
		_eventId = eventId;
	}

	public int getDeviceId()
	{
		return _deviceId;
	}

	public void setDeviceId(int deviceId)
	{
		_deviceId = deviceId;
	}

	public String getPicture()
	{
		return _picture;
	}

	public void setPicture(String picture)
	{
		_picture = picture;
	}

	public int getCameraId()
	{
		return _cameraId;
	}

	public void setCameraId(int cameraId)
	{
		_cameraId = cameraId;
	}

}

package com.gtzn.modules.monitor.entity;

public class TWsdSensorStatus 
{
	private int _deviceId = -1;
	private String _createTime = "";
	private int _roomId = -1;
	private float _temperature = 0;
	private float _humidity = 0;
	private String _lastUpdateTime = "";

	public int getDeviceId() 
	{
		return _deviceId;
	}

	public void setDeviceId(int deviceId) 
	{
		_deviceId = deviceId;
	}

	public String getCreateTime() 
	{
		return _createTime;
	}

	public void setCreateTime(String createTime) 
	{
		_createTime = createTime;
	}

	public int getRoomId() 
	{
		return _roomId;
	}

	public void setRoomId(int roomId) 
	{
		_roomId = roomId;
	}

	public float getTemperature() 
	{
		return _temperature;
	}

	public void setTemperature(float temperature) 
	{
		_temperature = temperature;
	}

	public float getHumidity() 
	{
		return _humidity;
	}

	public void setHumidity(float humidity) 
	{
		_humidity = humidity;
	}

	public String getLastUpdateTime() 
	{
		return _lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) 
	{
		_lastUpdateTime = lastUpdateTime;
	}
	
}

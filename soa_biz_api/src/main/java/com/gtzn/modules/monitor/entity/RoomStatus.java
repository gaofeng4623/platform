package com.gtzn.modules.monitor.entity;

public class RoomStatus
{
	private int _roomId = 0;
	private String _roomName = "";
	private float _temperature = 0;
	private float _humidity = 0;
	private int _isTHAuto = 0;
	private int _isLightAuto = 0;
	private int _isDisinfectAuto = 0;
	private int _isAirAuto = 0;
	private int _isAntiTheftDefenceSet = 0;
	private TWsdSensorStatus[] _wsdSensorStatusList = null;

	public int getRoomId()
	{
		return _roomId;
	}

	public void setRoomId(int roomId)
	{
		_roomId = roomId;
	}

	public String getRoomName()
	{
		return _roomName;
	}

	public void setRoomName(String roomName)
	{
		_roomName = roomName;
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

	public int getIsTHAuto()
	{
		return _isTHAuto;
	}

	public void setIsTHAuto(int isTHAuto)
	{
		_isTHAuto = isTHAuto;
	}

	public int getIsLightAuto()
	{
		return _isLightAuto;
	}

	public void setIsLightAuto(int isLightAuto)
	{
		_isLightAuto = isLightAuto;
	}

	public int getIsDisinfectAuto()
	{
		return _isDisinfectAuto;
	}

	public void setIsDisinfectAuto(int isDisinfectAuto)
	{
		_isDisinfectAuto = isDisinfectAuto;
	}

	public int getIsAirAuto()
	{
		return _isAirAuto;
	}

	public void setIsAirAuto(int isAirAuto)
	{
		_isAirAuto = isAirAuto;
	}

	public int getIsAntiTheftDefenceSet()
	{
		return _isAntiTheftDefenceSet;
	}

	public void setIsAntiTheftDefenceSet(int isAntiTheftDefenceSet)
	{
		_isAntiTheftDefenceSet = isAntiTheftDefenceSet;
	}

	public TWsdSensorStatus[] getWsdSensorStatusList()
	{
		return _wsdSensorStatusList;
	}

	public void setWsdSensorStatusList(TWsdSensorStatus[] wsdSensorStatusList)
	{
		_wsdSensorStatusList = wsdSensorStatusList;
	}

	//此处无需判断每个传感器的值是否相同，因为没有必要
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		
		if (o == null)
		{
			return false;
		}
		
		if (!(o instanceof RoomStatus))
		{
			return false;
		}
		
		RoomStatus rs = (RoomStatus)o;
		
		boolean ret = _roomId == rs._roomId;
	    
		ret = _roomName == null ? rs._roomName == null : _roomName.equals(rs._roomName);
	    if (!ret)
	    {
	    	return false;
	    }
		
		ret = _temperature == rs._temperature;
	    if (!ret)
	    {
	    	return false;
	    }
	    
		ret = _humidity == rs._humidity;
	    if (!ret)
	    {
	    	return false;
	    }
	    
		ret = _isTHAuto == rs._isTHAuto;
	    if (!ret)
	    {
	    	return false;
	    }
	    
		ret = _isLightAuto == rs._isLightAuto;
	    if (!ret)
	    {
	    	return false;
	    }
	    
		ret = _isDisinfectAuto == rs._isDisinfectAuto;
	    if (!ret)
	    {
	    	return false;
	    }
	    
		ret = _isAirAuto == rs._isAirAuto;
	    if (!ret)
	    {
	    	return false;
	    }
	    
		ret = _isAntiTheftDefenceSet == rs._isAntiTheftDefenceSet;
	    if (!ret)
	    {
	    	return false;
	    }	    	
	    
		return ret;
	}
}

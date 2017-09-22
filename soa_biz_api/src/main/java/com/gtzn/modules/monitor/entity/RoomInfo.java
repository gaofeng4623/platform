package com.gtzn.modules.monitor.entity;

public class RoomInfo
{
	private int _roomId = 0;
	private String _roomName = "";
	private int _floorId = 0;
	private float _tMax = 0;
	private float _tMin = 0;
	private float _hMax = 0;
	private float _hMin = 0;
	private int _tGain = 0;
	private int _hGain = 0;
	private int _tStepping = 0;
	private int _hStepping = 0;

	private RoomStatus _roomStatus = null;

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

	public int getFloorId()
	{
		return _floorId;
	}

	public void setFloorId(int floorId)
	{
		_floorId = floorId;
	}

	public float getTMax()
	{
		return _tMax;
	}

	public void setTMax(float tMax)
	{
		_tMax = tMax;
	}

	public float getTMin()
	{
		return _tMin;
	}

	public void setTMin(float tMin)
	{
		_tMin = tMin;
	}

	public float getHMax()
	{
		return _hMax;
	}

	public void setHMax(float hMax)
	{
		_hMax = hMax;
	}

	public float getHMin()
	{
		return _hMin;
	}

	public void setHMin(float hMin)
	{
		_hMin = hMin;
	}

	public int getTGain()
	{
		return _tGain;
	}

	public void setTGain(int tGain)
	{
		_tGain = tGain;
	}

	public int getHGain()
	{
		return _hGain;
	}

	public void setHGain(int hGain)
	{
		_hGain = hGain;
	}

	public int getTStepping()
	{
		return _tStepping;
	}

	public void setTStepping(int tStepping)
	{
		_tStepping = tStepping;
	}

	public int getHStepping()
	{
		return _hStepping;
	}

	public void setHStepping(int hStepping)
	{
		_hStepping = hStepping;
	}

	public RoomStatus getRoomStatus()
	{
		return _roomStatus;
	}

	public void setRoomStatus(RoomStatus roomStatus)
	{
		_roomStatus = roomStatus;
	}
	
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
	    
	    if (!(o instanceof RoomInfo))
	    {
	    	return false;
	    }
	    
	    RoomInfo ri = (RoomInfo)o;
	    boolean ret = _roomId == ri._roomId;
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    ret = _roomName == null ? ri._roomName == null : _roomName.equals(ri._roomName);
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    ret = _floorId == ri._floorId;
	    if (!ret)
	    {
	    	return false;
	    }	    
	   
	    ret = _tMax == ri._tMax;
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    ret = _tMin == ri._tMin;
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    ret = _hMax == ri._hMax;
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    ret = _hMin == ri._hMin;
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    ret = _hStepping == ri._hStepping;
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    ret = _tGain == ri._tGain;
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    ret = _hGain == ri._hGain;
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    ret = _tStepping == ri._tStepping;
	    if (!ret)
	    {
	    	return false;
	    }
	   	    
	    ret = _roomStatus.equals(ri._roomStatus);
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    return ret;
	}
}

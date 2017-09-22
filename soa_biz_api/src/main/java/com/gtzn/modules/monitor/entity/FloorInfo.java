package com.gtzn.modules.monitor.entity;

public class FloorInfo
{
	private int _floorId = 0;
	private String _floorName = "";
	private int _flag = 0;
	private RoomInfo[] _rooms = null;

	public FloorInfo()
	{

	}

	public FloorInfo(String floorName, int floorId)
	{
		this.setFloorName(floorName);
		this.setFloorId(floorId);
	}

	public String getFloorName()
	{
		return _floorName;
	}

	public void setFloorName(String floorName)
	{
		_floorName = floorName;
	}

	public int getFloorId()
	{
		return _floorId;
	}

	public void setFloorId(int floorId)
	{
		_floorId = floorId;
	}

	public int getFlag()
	{
		return _flag;
	}

	public void setFlag(int flag)
	{
		_flag = flag;
	}

	public RoomInfo[] getRooms()
	{
		return _rooms;
	}

	public void setRooms(RoomInfo[] rooms)
	{
		_rooms = rooms;
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
	    
	    if (!(o instanceof FloorInfo))
	    {
	    	return false;
	    }
	    
	    FloorInfo fi = (FloorInfo)o;
	    boolean ret = _floorId == fi._floorId;
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    ret = _floorName == null ? fi._floorName == null : _floorName.equals(fi._floorName);
	    if (!ret)
	    {
	    	return false;
	    }
	    
	    ret = _flag == fi._flag;
	    if (!ret)
	    {
	    	return false;
	    }

	    if (getRooms() == null && fi.getRooms() == null)
	    {
	    	return true;
	    }
	    
	    if (getRooms().length != fi.getRooms().length)
	    {
	    	return false;
	    }
	    
	    for (int i=0; i<getRooms().length; i++)
	    {
	    	if (!(getRooms()[i].equals(fi.getRooms()[i])))
	    	{
	    		return false;
	    	}
	    }
		
	    return true;
	}

}

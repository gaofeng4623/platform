package com.gtzn.modules.monitor.entity;

public class CameraImage
{
	private int _id = -1;                        //图片ID
	private String _createTime = "";             //创建时间
	private int _cameraId = -1;                  //摄像机ID
	private String _displayName = "";            //显示名字
	private String _imageUrl = "";               //图片路径
	private String _rtspUrl = "";                //摄像机视频路径
	private String _dateStr = "";                //获取该图片时传入的参数
	private String _note = "";                   //备注信息

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
	
	public int getCameraId()
	{
		return _cameraId;
	}

	public void setCameraId(int cameraId)
	{
		_cameraId = cameraId;
	}

	public String getDisplayName()
	{
		return _displayName;
	}

	public void setDisplayName(String displayName)
	{
		_displayName = displayName;
	}

	public String getImageUrl()
	{
		return _imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		_imageUrl = imageUrl;
	}

	public String getRtspUrl()
	{
		return _rtspUrl;
	}

	public void setRtspUrl(String rtspUrl)
	{
		_rtspUrl = rtspUrl;
	}

	public String getDateStr()
	{
		return _dateStr;
	}

	public void setDateStr(String dateStr)
	{
		_dateStr = dateStr;
	}

	public String getNote()
	{
		return _note;
	}

	public void setNote(String note)
	{
		_note = note;
	}
}

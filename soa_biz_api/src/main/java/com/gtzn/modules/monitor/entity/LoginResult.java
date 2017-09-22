package com.gtzn.modules.monitor.entity;


public class LoginResult
{
	private int _result = 0;
	private String _realName = "";
    private int _isControlByRoom = 1;
	private FunctionInfo[] _functionInfos = null;

	public int getResult()
	{
		return _result;
	}
	public void setResult(int result)
	{
		_result = result;
	}
	public String getRealName()
	{
		return _realName;
	}
	public void setRealName(String realName)
	{
		_realName = realName;
	}
	public FunctionInfo[] getFunctionInfos()
	{
		return _functionInfos;
	}
	public void setFunctionInfos(FunctionInfo[] functionInfos)
	{
		_functionInfos = functionInfos;
	}

	public int getIsControlByRoom()
	{
		return _isControlByRoom;
	}
	public void setIsControlByRoom(int isControlByRoom)
	{
		_isControlByRoom = isControlByRoom;
	}
}

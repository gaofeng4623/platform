package com.gtzn.common;
/*
* axis命令行工具
* */

import java.util.ArrayList;
import java.util.List;

public class WSCommand
{
    private String _serviceName = "";
    private String _methodName = "";
    private List<KeyValuePair> _paramList = new ArrayList<KeyValuePair>();

    private boolean _isList = false;                                     //是否是List，如果是，则�?��单独处理
    private boolean _isArray = false;                                    //是否是Array，如果是，则�?��单独处理
    private Class<?> _mainReturnType = null;                             //如果返回值不是List及Array，则直接是返回类型的class，例如MapInfo。class，如果返回的是List或�?Array，它将是数组内部元素的class，如WsdHistory.class

	public WSCommand()
    {

    }

    public WSCommand(String serviceName, String methodName, Class<?> retType, boolean isList, boolean isArray)
    {
    	_serviceName = serviceName;
        _methodName = methodName;
        _mainReturnType = retType;
        _isList = isList;
        _isArray = isArray;
    }

    public void putParam(String key, Object value)
    {
        _paramList.add(new KeyValuePair(key, value));
    }

    public String getMethodName()
    {
        return _methodName;
    }

    public void setMethodName(String methodName)
    {
        _methodName = methodName;
    }

    public String getServiceName()
    {
        return _serviceName;
    }

    public void setServiceName(String serviceName)
    {
        _serviceName = serviceName;
    }

    public List<KeyValuePair> getParamList()
    {
        return _paramList;
    }

    public void setParamList(List<KeyValuePair> paramList)
    {
        _paramList = paramList;
    }

    public boolean getIsList()
	{
		return _isList;
	}

	public void setIsList(boolean isList)
	{
		_isList = isList;
	}

	public boolean getIsArray()
	{
		return _isArray;
	}

	public void setIsArray(boolean isArray)
	{
		_isArray = isArray;
	}

	public Class<?> getMainReturnType()
	{
		return _mainReturnType;
	}

	public void setMainReturnType(Class<?> mainReturnType)
	{
		_mainReturnType = mainReturnType;
	}

	@Override
	public String toString()
	{
		String params = "";
		if (_paramList != null)
		{
			for (KeyValuePair kv : _paramList)
			{
				params += String.format("paramName:%s; paramValue:%s;", kv.getKey(), kv.getValue() == null ? "null" : kv.getValue().toString());
			}
		}
		String str = String.format("service: %s;\r\nmethod:%s;\r\nparams:%s;\r\n", _serviceName, _methodName, params);
		return str;
	}
}
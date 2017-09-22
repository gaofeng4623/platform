package com.gtzn.common;

public class KeyValuePair
{
	private String _key = "";
	private Object _value = null;
	
	public KeyValuePair()
	{
	
	}	

	public KeyValuePair(String key, Object value)
	{
		setKey(key);
		setValue(value);
	}

	public String getKey()
	{
		return _key;
	}

	public void setKey(String key)
	{
		_key = key;
	}

	public Object getValue()
	{
		return _value;
	}

	public void setValue(Object value)
	{
		_value = value;
	}
	
}

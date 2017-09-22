package com.gtzn.soa.service;

/**
 * @info 声明webservice接口
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-04-21 15:11:00
 * @version $Id$
 */
public interface WebService {
    public <T> T invokeResult(String methodName, Object[] args, Class<?> returnType, boolean isList, boolean isArray) throws Exception;
}

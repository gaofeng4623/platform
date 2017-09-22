package com.gtzn.soa.service.impl;

import com.gtzn.soa.service.AbstractWebService;
import org.codehaus.xfire.client.Client;

import java.net.URL;

/**
 * @info 实现XFire的客户端请求方式
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-04-21 15:11:00
 * @version $Id$
 */
public class WebServiceXFireImpl extends AbstractWebService {

    @Override
    public <T> T invokeResult(String methodName, Object[] args, Class<?> returnType, boolean isList, boolean isArray) throws Exception{
        Client client = new Client(new URL(getServerAddress()));
        Object[] result = client.invoke(methodName, args);
        if (result != null && result.length > 0) {
            return (T)result[0];
        }
        return null;
    }
}
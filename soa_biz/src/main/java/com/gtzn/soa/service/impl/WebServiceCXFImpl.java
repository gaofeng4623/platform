package com.gtzn.soa.service.impl;

import com.gtzn.soa.service.AbstractWebService;
//import org.apache.cxf.endpoint.Client;
//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * @info 实现CXF的客户端请求方式
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-05-03
 * @version $Id$
 */
public class WebServiceCXFImpl extends AbstractWebService {
    //private JaxWsDynamicClientFactory clientFactory = null;

    @Override
    public <T> T invokeResult(String methodName, Object[] args, Class<?> returnType, boolean isList, boolean isArray) throws Exception {
        /*if (clientFactory == null) {
            clientFactory = JaxWsDynamicClientFactory.newInstance();
        }
        Client client = clientFactory.createClient(getServerAddress());
        Object[] result = client.invoke(methodName, args);
        if (result != null && result.length > 0) {
            return (T)result[0];
        }*/
        return null;
    }
}

package com.gtzn.soa.service;

/**
 * @info 发布webservice的调用方式
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-04-21 15:11:00
 * @version $Id$
 */
public abstract class AbstractWebService implements WebService {

    private String serverAddress;

    private String nameSpace;

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public abstract <T> T invokeResult(String methodName, Object[] args, Class<?> returnType, boolean isList, boolean isArray) throws Exception;
}

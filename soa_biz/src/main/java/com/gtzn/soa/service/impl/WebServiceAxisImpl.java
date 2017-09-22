package com.gtzn.soa.service.impl;

import com.gtzn.common.KeyValuePair;
import com.gtzn.common.WSCommand;
import com.gtzn.soa.service.AbstractWebService;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.engine.DefaultObjectSupplier;
import org.apache.axis2.rpc.client.RPCServiceClient;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @info 实现Axis的客户端请求方式
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-04-21 15:11:00
 * @version $Id$
 */
public class WebServiceAxisImpl extends AbstractWebService {
    private RPCServiceClient _client = null;
    private boolean debug = false;

    @Override
    public <T> T invokeResult(String methodName, Object[] args, Class<?> returnType, boolean isList, boolean isArray) throws Exception {
        WSCommand command = new WSCommand(getServerAddress(), methodName, returnType, isList, isArray);
        for (int i = 0; i < args.length; i++) {
            command.putParam("param" + i, args[i]);
        }
        return (T) invoke(command);
    }

    public Object invoke(WSCommand command) {
        if (command != null) {
            return queryFromRemote(command);
        }
        return null;
    }

    private synchronized Object queryFromRemote(WSCommand command) {
        try {
            if (_client == null) {
                _client = new RPCServiceClient();
            }

            Options options = _client.getOptions();
            String url = command.getServiceName();
            EndpointReference end = new EndpointReference(url);
            options.setTo(end);
            options.setTimeOutInMilliSeconds(2000);
            QName qname = new QName(getNameSpace(), command.getMethodName());

            Object[] args = null;
            if (command.getParamList() != null && !command.getParamList().isEmpty()) {
                args = new Object[command.getParamList().size()];
                int i = 0;
                for (KeyValuePair kv : command.getParamList()) {
                    args[i] = kv.getValue();
                    i++;
                }
            }

            log("begin Call", command);
            if (command.getIsList() || command.getIsArray()) {
                OMElement obj = _client.invokeBlocking(qname, args);
                log(obj.getText());

                ArrayList<Object> list = new ArrayList<Object>();
                Iterator<?> it = obj.getChildElements();
                while (it.hasNext()) {
                    OMElement e = (OMElement) it.next();
                    Object o = (Object) BeanUtil.deserialize(command.getMainReturnType(), e, new DefaultObjectSupplier(), null);
                    list.add(o);
                }

                if (command.getIsArray()) {
                    Object[] array = new Object[list.size()];
                    list.toArray(array);

                    log("after Call", command);
                    return array;
                }

                log("after Call", command);
                return list;
            } else {
                Class<?>[] classes = new Class<?>[]{command.getMainReturnType()};
                Object result = (Object) _client.invokeBlocking(qname, args, classes)[0];
                log("after Call", command);
                return result;
            }
        } catch (AxisFault e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            _client = null;
        }
        log("Error! after Call", command);
        return null;
    }

    private void log(String msg) {
        if (isDebug()) {
            System.out.println(msg);
        }
    }

    private void log(String prefix, WSCommand command) {
        if (isDebug()) {
            System.out.println(prefix + ": " + command.toString());
        }
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}

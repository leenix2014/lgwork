package com.lagou.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Activate(group = {CommonConstants.CONSUMER})
public class TransportIPFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        invocation.getAttachments().put("ip", getIp());
        return invoker.invoke(invocation);
    }

    private String getIp(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }
}

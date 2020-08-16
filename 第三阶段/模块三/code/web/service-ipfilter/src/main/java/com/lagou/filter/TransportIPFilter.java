package com.lagou.filter;

import com.lagou.filter.util.WebRequestHolder;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Activate(group = {CommonConstants.CONSUMER})
public class TransportIPFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        HttpServletRequest request = WebRequestHolder.getRequest();
        invocation.getAttachments().put("ip", request.getRemoteAddr());
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

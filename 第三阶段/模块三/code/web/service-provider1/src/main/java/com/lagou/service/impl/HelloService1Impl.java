package com.lagou.service.impl;

import com.lagou.service.HelloService1;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

@DubboService
public class HelloService1Impl implements HelloService1 {
    @Override
    public String sayHello1(String name) {
        String ip = RpcContext.getContext().getAttachment("ip");
        System.out.println("sayHello attachment:" + ip);
        return "hello1 " + name;
    }
}

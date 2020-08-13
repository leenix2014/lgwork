package com.lagou.service.impl;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

@DubboService
public class HelloServiceImpl implements HelloService {
    public String sayHello(String name) {
        String ip = RpcContext.getContext().getAttachment("ip");
        System.out.println("sayHello attachment:" + ip);
        return "hello " + name;
    }

    @Override
    public String sayHi() {
        String ip = RpcContext.getContext().getAttachment("ip");
        System.out.println("sayHi attachment:" + ip);
        return "hi";
    }
}

package com.lagou.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    //将来客户单要远程调用的方法
    public String sayHello(String msg) {
        System.out.println("收到客户端请求数据："+msg);
        return "success";
    }
}

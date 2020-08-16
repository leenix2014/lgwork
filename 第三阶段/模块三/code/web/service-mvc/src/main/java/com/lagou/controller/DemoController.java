package com.lagou.controller;

import com.lagou.service.HelloService;
import com.lagou.service.HelloService1;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DemoController {

    @DubboReference
    private HelloService service;

    @DubboReference
    private HelloService1 service1;

    @RequestMapping("/test")
    public String test(HttpServletRequest request){
        service.sayHello("test");
        service.sayHi();
        service1.sayHello1("test1");
        return request.getRemoteAddr();
    }
}

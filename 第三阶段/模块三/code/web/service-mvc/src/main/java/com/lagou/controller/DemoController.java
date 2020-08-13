package com.lagou.controller;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @DubboReference
    private HelloService service;

    @RequestMapping("/test")
    public String test(){
        service.sayHello("test");
        service.sayHi();
        return "success";
    }
}

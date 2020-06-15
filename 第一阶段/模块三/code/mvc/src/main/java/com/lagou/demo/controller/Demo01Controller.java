package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvcframework.annotations.LagouAutowired;
import com.lagou.edu.mvcframework.annotations.LagouController;
import com.lagou.edu.mvcframework.annotations.LagouRequestMapping;
import com.lagou.edu.mvcframework.annotations.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@LagouController
@LagouRequestMapping("/demo")
@Security(values = {"zhang3","li4"})
public class Demo01Controller {

    @LagouRequestMapping("/handle01")
    @Security(values = {"li4", "wang5"})
    public String test1(String username){
        return "Welcome " + username;
    }
}

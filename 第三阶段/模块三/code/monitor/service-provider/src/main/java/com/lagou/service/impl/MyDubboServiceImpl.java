package com.lagou.service.impl;

import com.lagou.service.MyDubboService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Random;

@DubboService
public class MyDubboServiceImpl implements MyDubboService {

    private static Random rand = new Random();

    public String methodA() {
        int time = rand.nextInt(100);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("methodA sleep for %s ms", time));
        return "methodA";
    }

    public String methodB() {
        int time = rand.nextInt(100);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("methodB sleep for %s ms", time));
        return "methodB";
    }

    public String methodC() {
        int time = rand.nextInt(100);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("methodC sleep for %s ms", time));
        return "methodC";
    }
}

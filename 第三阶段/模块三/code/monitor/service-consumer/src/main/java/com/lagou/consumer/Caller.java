package com.lagou.consumer;

import com.lagou.service.MyDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class Caller {

    @DubboReference
    private MyDubboService service;

    public void call(){
        ExecutorService executor = Executors.newFixedThreadPool(10);
        while (true) {
//            service.methodA();
//            service.methodB();
//            service.methodC();
            try {
                Thread.sleep(20);
                executor.submit(() -> {
                    service.methodA();
                });
                executor.submit(() -> {
                    service.methodB();
                });
                executor.submit(() -> {
                    service.methodC();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

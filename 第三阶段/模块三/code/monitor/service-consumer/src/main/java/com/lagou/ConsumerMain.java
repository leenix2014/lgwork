package com.lagou;

import com.lagou.config.ConsumerConfiguration;
import com.lagou.consumer.Caller;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class ConsumerMain {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        Caller caller = context.getBean(Caller.class);
        caller.call();
//        System.in.read();
        System.out.println("done!");
    }
}

package com.lagou;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@PropertySource("classpath:/dubbo-consumer.properties")
//@ComponentScan(basePackages = "com.lagou")
//@EnableDubbo
public class ConsumerMain {
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerMain.class);
//        context.start();
        SpringApplication.run(ConsumerMain.class, args);
    }
}

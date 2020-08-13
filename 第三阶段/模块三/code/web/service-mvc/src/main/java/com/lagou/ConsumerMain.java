package com.lagou;

import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/dubbo-consumer.properties")
@EnableDubboConfig
public class ConsumerMain {
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerMain.class);
//        context.start();
        SpringApplication.run(ConsumerMain.class, args);
    }
}

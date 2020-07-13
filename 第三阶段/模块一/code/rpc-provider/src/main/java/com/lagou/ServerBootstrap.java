package com.lagou;

import com.lagou.boot.ServerBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerBootstrap {
    public static void main(String[] args) throws InterruptedException {
        // 容器初始化
        SpringApplication.run(ServerBootstrap.class, args);
        // 启动Netty
        ServerBoot.startServer(8999);
    }
}

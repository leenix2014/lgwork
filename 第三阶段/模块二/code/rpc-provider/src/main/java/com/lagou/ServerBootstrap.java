package com.lagou;

import com.lagou.boot.ServerBoot;
import com.lagou.registry.RpcRegistry;
import com.lagou.service.IUserService;
import com.lagou.util.CuratorUtils;
import com.lagou.util.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class ServerBootstrap {
    public static void main(String[] args) throws InterruptedException {
        int port = 8999;
        if (args.length > 0){
            port = StringUtils.toInt(args[0], port);
        }
        // 容器初始化
        SpringApplication.run(ServerBootstrap.class, args);
        RpcRegistry.registry(IUserService.class, getHost(), port);
        // 启动Netty
        ServerBoot.startServer(port);
    }


    public static String getHost(){
        String host = "";
        try {
            InetAddress ia = InetAddress.getLocalHost();
            host = ia.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }
}

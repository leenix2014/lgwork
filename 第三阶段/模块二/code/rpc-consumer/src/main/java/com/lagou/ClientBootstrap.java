package com.lagou;

import com.lagou.client.RPCConsumer;
import com.lagou.discovery.ServerWatcher;
import com.lagou.service.IUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class ClientBootstrap {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ClientBootstrap.class, args);
        ServerWatcher.watch();
        while (true) {
            IUserService service = (IUserService) RPCConsumer.createProxy(IUserService.class);
            String result = service.sayHello("test");
//            System.out.println(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())+"服务器响应数据:"+result);
            Thread.sleep(5000);
        }
    }
}

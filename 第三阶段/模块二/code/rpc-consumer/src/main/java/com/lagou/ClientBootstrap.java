package com.lagou;

import com.lagou.client.RPCConsumer;
import com.lagou.service.IUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientBootstrap {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ClientBootstrap.class, args);

        while (true) {
            IUserService service = (IUserService) RPCConsumer.createProxy(IUserService.class);
            String result = service.sayHello("test");
            System.out.println(result);
            Thread.sleep(2000);
        }
    }
}

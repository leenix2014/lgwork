package com.lagou.boot;

import com.lagou.client.RPCConsumer;
import com.lagou.request.RpcRequest;

public class ConsumerBoot {

    //参数定义
    private static final String PROVIDER_NAME = "UserService#sayHello#";

    public static void main(String[] args) throws Exception {

//        //1.创建代理对象
//        IUserService service = (IUserService) RPCConsumer.createProxy(IUserService.class, PROVIDER_NAME);
//
//        //2.循环给服务器写数据
        while (true){
//            String result = service.sayHello("are you ok !!");
            RpcRequest request = new RpcRequest();
            request.setClassName("com.lagou.service.IUserService");
            request.setMethodName("sayHello");
            request.setParameterTypes(new Class[]{String.class});
            request.setParameters(new Object[]{"hello server"});
            Object result = RPCConsumer.callServer(request);
            System.out.println(result);
            Thread.sleep(2000);
        }
    }
}

package com.lagou.client;

import com.lagou.discovery.ServerWatcher;
import com.lagou.request.RpcRequest;

import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 消费者
 */
public class RPCConsumer {

    public static Object createProxy(Class<?> serviceClass){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, (o, method, objects) -> {
//                    NettyClient client = ServerWatcher.randomServer();
                    NettyClient client = ServerWatcher.minTimeServer();
                    RpcRequest request = new RpcRequest();
//                    request.setRequestId(UUID.randomUUID().toString());
                    request.setClassName(serviceClass.getName());
                    request.setMethodName(method.getName());
                    request.setParameterTypes(method.getParameterTypes());
                    request.setParameters(objects);
                    String result = client.send(request);
                    return result;
                });
    }
}

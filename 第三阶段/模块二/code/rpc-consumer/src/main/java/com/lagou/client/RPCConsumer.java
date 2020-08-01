package com.lagou.client;

import com.lagou.codec.JSONSerializer;
import com.lagou.codec.RpcDecoder;
import com.lagou.codec.RpcEncoder;
import com.lagou.handler.UserClientHandler;
import com.lagou.registry.RpcRegistry;
import com.lagou.request.RpcRequest;
import com.lagou.service.IUserService;
import com.lagou.util.StringUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消费者
 */
public class RPCConsumer {

    //1.创建一个线程池对象  -- 它要处理我们自定义事件
    private static ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //2.声明一个自定义事件处理器  UserClientHandler
    private static UserClientHandler userClientHandler;

    private static Bootstrap bootstrap;

    private static List<String> servers;


    //3.编写方法,初始化客户端  ( 创建连接池  bootStrap  设置bootstrap  连接服务器)
    public static void initClient() throws InterruptedException {
        //1) 初始化UserClientHandler
        userClientHandler  = new UserClientHandler();
        //2)创建连接池对象
        EventLoopGroup group = new NioEventLoopGroup();
        //3)创建客户端的引导对象
        bootstrap =  new Bootstrap();
        //4)配置启动引导对象
        bootstrap.group(group)
                //设置通道为NIO
                .channel(NioSocketChannel.class)
                //设置请求协议为TCP
                .option(ChannelOption.TCP_NODELAY,true)
                //监听channel 并初始化
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //获取ChannelPipeline
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //设置编码
//                        pipeline.addFirst(new StringEncoder());
                        pipeline.addFirst(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
//                        pipeline.addLast(new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                        pipeline.addLast(new StringDecoder());
                        //添加自定义事件处理器
                        pipeline.addLast(userClientHandler);
                    }
                });

        //5)连接服务端
        servers = RpcRegistry.discovery(IUserService.class);
        for (String server : servers) {
            bootstrap.connect(getIp(server), getPort(server)).sync();
        }
//        RpcRegistry.watch(IUserService.class, event -> {
//            if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged){
//                servers = RpcRegistry.discovery(IUserService.class);
//                System.out.println("服务器节点发生变化，更新后节点："+ servers);
//            }
//        });
        RpcRegistry.watch(IUserService.class, (curatorFramework, pathChildrenCacheEvent) -> {
            String server = pathChildrenCacheEvent.getData().getPath();
            switch (pathChildrenCacheEvent.getType()){
                case CHILD_ADDED:
                    servers.add(server);
                    System.out.println("服务器上线："+server);
                    break;
                case CHILD_REMOVED:
                    servers.remove(server);
                    System.out.println("服务器下线："+server);
                    break;
            }
        });
    }

    private static String getIp(String server){
        return server.split(":")[0];
    }
    private static int getPort(String server){
        return StringUtils.toInt(server.split(":")[1], 8999);
    }

    //4.编写一个方法,使用JDK的动态代理创建对象
    // serviceClass 接口类型,根据哪个接口生成子类代理对象;
    public static Object createProxy(Class<?> serviceClass){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, (o, method, objects) -> {
                    //1)初始化客户端cliet
                    if(userClientHandler == null){
                        initClient();
                    }

                    //2)给UserClientHandler 设置request参数
                    RpcRequest request = new RpcRequest();
                    request.setClassName(serviceClass.getName());
                    request.setMethodName(method.getName());
                    request.setParameterTypes(method.getParameterTypes());
                    request.setParameters(objects);
                    userClientHandler.setRequest(request);

                    //3).使用线程池,开启一个线程处理处理call() 写操作,并返回结果
                    Object result = executorService.submit(userClientHandler).get();

                    //4)return 结果
                    return result;
                });
    }
}

package com.lagou.client;

import com.lagou.codec.JSONSerializer;
import com.lagou.codec.RpcEncoder;
import com.lagou.request.RpcRequest;
import com.lagou.util.StringUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyClient extends ChannelInboundHandlerAdapter {

    private EventLoopGroup group;

    public NettyClient() {

    }

    public NettyClient connect(String server) {
        NettyClient self = this;
        group = new NioEventLoopGroup();
        Bootstrap bootstrap =  new Bootstrap();
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
                        pipeline.addLast(self);
                    }
                });
        try {
            bootstrap.connect(getIp(server), getPort(server)).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    private ChannelHandlerContext context; //事件处理器上下文对象 (存储handler信息,写操作)
    private String result; // 记录服务器返回的数据
    private Long requestStartTime = Long.MIN_VALUE;
    private Long lastResponseTime = null;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //初始化ChannelHandlerContext
        this.context = ctx;
    }


    //3.实现channelRead 当我们读到服务器数据,该方法自动执行
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg)  {
        //将读到的服务器的数据msg ,设置为成员变量的值
        result = msg.toString();
        lastResponseTime = System.currentTimeMillis() - requestStartTime;
        notify();
    }

    public synchronized String send(RpcRequest request) throws InterruptedException {
        //context给服务器写数据
        context.writeAndFlush(request);
        requestStartTime = System.currentTimeMillis();
        wait(500);
        return result;
    }

    public void close(){
        group.shutdownGracefully();
    }

    public Long getLastResponseTime() {
        return lastResponseTime;
    }

    private static String getIp(String server){
        return server.split(":")[0];
    }
    private static int getPort(String server){
        return StringUtils.toInt(server.split(":")[1], 8999);
    }
}

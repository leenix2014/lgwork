package com.lagou.handler;

import com.lagou.request.RpcRequest;
import com.lagou.service.IUserService;
import com.lagou.service.UserServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 自定义的业务处理器
 */
@Component
public class UserServiceHandler extends ChannelInboundHandlerAdapter implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    //当客户端读取数据时,该方法会被调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = (RpcRequest) msg;
        String className = request.getClassName();
        Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
        Object bean = applicationContext.getBean(clazz);

        String methodName = request.getMethodName();
        Method method = clazz.getMethod(methodName, request.getParameterTypes());
        Object result = method.invoke(bean, request.getParameters());
        ctx.channel().writeAndFlush(result.toString());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

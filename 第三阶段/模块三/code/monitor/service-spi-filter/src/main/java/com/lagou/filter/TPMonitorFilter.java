package com.lagou.filter;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Activate(group = {CommonConstants.CONSUMER})
public class TPMonitorFilter implements Filter, Runnable {

    public TPMonitorFilter(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this, 5, 5, TimeUnit.SECONDS);
    }

    private Statistics statistics = new Statistics();

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long start = System.currentTimeMillis();
        try {
            return invoker.invoke(invocation);
        } finally {
            long end = System.currentTimeMillis();
            long duration = end - start;
            String methodName = invocation.getMethodName();
//            System.out.println(String.format("%s耗时：%s ms", methodName, duration));
            statistics.record(methodName, duration, end);
        }
    }

    public void run() {
        try {
            statistics.printMonitorInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

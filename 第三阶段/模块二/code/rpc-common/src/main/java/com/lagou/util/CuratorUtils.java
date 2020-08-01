package com.lagou.util;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorUtils {
    static CuratorFramework client;
    static {
        RetryPolicy exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", exponentialBackoffRetry);
        client.start();
    }

    public static boolean exists(String path){
        try {
            Stat data = client.checkExists().forPath(path);
            return data != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void create(String path, CreateMode mode){
        try {
            client.create().creatingParentsIfNeeded()
                    .withMode(mode).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.lagou.util;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.watch.PersistentWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

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
            if (exists(path)){
                client.delete().guaranteed().forPath(path);
            }
            client.create().creatingParentsIfNeeded()
                    .withMode(mode).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getChildren(String path){
        List<String> children = new ArrayList<String>();
        try {
            children = client.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }

    public static List<String> getChildren(String path, CuratorWatcher watcher){
        List<String> children = new ArrayList<String>();
        try {
            children = client.getChildren().usingWatcher(watcher).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }


    public static void watch(String path, Watcher watcher) {
        PersistentWatcher persistentWatcher = new PersistentWatcher(client, path, false);
        persistentWatcher.getListenable().addListener(watcher);
        persistentWatcher.start();
    }

    public static void watch(String path, PathChildrenCacheListener listener){
        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.getListenable().addListener(listener);
        try {
            cache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setData(String path, byte[] data){
        if (!exists(path)){
            create(path, CreateMode.PERSISTENT);
        }
        try {
            client.setData().forPath(path, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] getData(String path){
        try {
            return client.getData().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    public static void watchData(String path, NodeCacheListener listener){
        NodeCache cache = new NodeCache(client, path, false);
        cache.getListenable().addListener(listener);
        try {
            cache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

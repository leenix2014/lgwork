package com.lagou.registry;

import com.lagou.util.CuratorUtils;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import java.util.List;

public class RpcRegistry {

    static final String PROVIDER_PREFIX = "/provider";

    public static void registry(Class<?> clazz, String host, int port){
        registry(providerPath(clazz), host, port);
    }

    public static List<String> discovery(Class<?> clazz){
        return CuratorUtils.getChildren(providerPath(clazz));
    }

    public static void watch(Class<?> clazz, Watcher watcher){
        CuratorUtils.watch(providerPath(clazz), watcher);
    }

    public static void watch(Class<?> clazz, PathChildrenCacheListener watcher){
        CuratorUtils.watch(providerPath(clazz), watcher);
    }

    public static List<String> discovery(Class<?> clazz, CuratorWatcher watcher){
        return CuratorUtils.getChildren(providerPath(clazz), watcher);
    }

    private static void registry(String path, String host, int port) {
        if (!CuratorUtils.exists(path)) {
            CuratorUtils.create(path, CreateMode.PERSISTENT);
        }
        String ephemeralPath = path + "/" + host + ":" + port;
        CuratorUtils.create(ephemeralPath, CreateMode.EPHEMERAL);
        System.out.println("服务器上线:"+ephemeralPath);
    }

    public static String providerPath(Class<?> clazz){
        return PROVIDER_PREFIX + "/" + clazz.getName();
    }
}

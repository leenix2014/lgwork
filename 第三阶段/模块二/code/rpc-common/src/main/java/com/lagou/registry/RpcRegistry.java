package com.lagou.registry;

import com.lagou.util.CuratorUtils;
import org.apache.zookeeper.CreateMode;

public class RpcRegistry {

    static final String PROVIDER_PREFIX = "/provider";

    public static void registry(Class<?> clazz, String host, int port){
        registry(providerPath(clazz), host, port);
    }

    private static void registry(String path, String host, int port) {
        if (!CuratorUtils.exists(path)) {
            CuratorUtils.create(path, CreateMode.PERSISTENT);
        }
        String ephemeralPath = path + "/" + host + ":" + port;
        CuratorUtils.create(ephemeralPath, CreateMode.EPHEMERAL);
    }

    static String providerPath(Class<?> clazz){
        return PROVIDER_PREFIX + "/" + clazz.getName() + "/";
    }
}

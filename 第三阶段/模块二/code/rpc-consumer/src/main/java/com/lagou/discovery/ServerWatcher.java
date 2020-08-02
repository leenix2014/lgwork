package com.lagou.discovery;

import com.lagou.client.NettyClient;
import com.lagou.registry.RpcRegistry;
import com.lagou.service.IUserService;
import com.lagou.util.CuratorUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ServerWatcher {

    private static Map<String, NettyClient> clients = new HashMap<>();

    public static void watch(){
        List<String> servers = RpcRegistry.discovery(IUserService.class);
        for (String server : servers){
            clients.put(server, new NettyClient().connect(server));
        }
        RpcRegistry.watch(IUserService.class, (curatorFramework, pathChildrenCacheEvent) -> {
            String server = pathChildrenCacheEvent.getData().getPath();
            server = server.substring(server.lastIndexOf("/")+1, server.length());
            switch (pathChildrenCacheEvent.getType()){
                case CHILD_ADDED:
                    clients.put(server, new NettyClient().connect(server));
                    System.out.println("服务器上线："+server);
                    break;
                case CHILD_REMOVED:
                    NettyClient client = clients.get(server);
                    if (client != null) {
                        client.close();
                    }
                    clients.remove(server);
                    System.out.println("服务器下线："+server);
                    break;
            }
        });
    }

    public static NettyClient minTimeServer(){
        Long minTime = Long.MAX_VALUE;
        NettyClient minClient = null;
        for (NettyClient client : clients.values()) {
            if (client.getLastResponseTime() == null){
                minClient = client;
                break;
            }
            if (client.getLastResponseTime() <= minTime){
                minClient = client;
                minTime = client.getLastResponseTime();
            }
        }
        return minClient;
    }

    public static Random rand = new Random(System.currentTimeMillis());

    public static NettyClient randomServer(){
        Object[] keys = clients.keySet().toArray();
        int index = rand.nextInt(keys.length);
        return clients.get(keys[index]);
    }
}

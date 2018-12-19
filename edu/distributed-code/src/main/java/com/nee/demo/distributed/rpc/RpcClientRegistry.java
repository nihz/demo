package com.nee.demo.distributed.rpc;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcClientRegistry extends RpcRegistry {

    private final RpcLoadBalance rpcLoadBalance;

    private final static Map<String, List<String>> urlsCache = new ConcurrentHashMap<>();
    //TODO 加个地址缓存

    public RpcClientRegistry(RpcLoadBalance rpcLoadBalance) {
        this.rpcLoadBalance = rpcLoadBalance;
    }

    public String acquire(String serviceName) throws Exception {

        List<String> urls = urlsCache.get(serviceName);
        if (urls == null) {
            String path = ROOT_PATH + "/" + serviceName;
            urls = curatorFramework.getChildren().usingWatcher(
                    (Watcher) event ->
                    {
                        System.out.println("监听器watchedEvent：" + event);
                        try {
                            urlsCache.put(serviceName, curatorFramework.getChildren().forPath(path));
                            System.out.println(urlsCache);
                        } catch (Exception e) {
                            // e.printStackTrace();
                        }
                    }
            ).forPath(path);

            urlsCache.put(serviceName, urls);
        }

        return rpcLoadBalance.loadBalance(urls);
    }

}

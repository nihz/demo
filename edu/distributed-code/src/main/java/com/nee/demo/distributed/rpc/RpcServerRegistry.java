package com.nee.demo.distributed.rpc;

import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RpcServerRegistry extends RpcRegistry {

    public void registry(String serviceName, int port) throws Exception {
        String ip = null;
        try {
            ip = getLocalIp();
        } catch (UnknownHostException e) {
            throw new RuntimeException("acquire local ip error");
        }
        String path = ROOT_PATH + "/" + serviceName + "/" + ip + ":" + port;
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "0".getBytes());
    }

    private String getLocalIp() throws UnknownHostException {

        InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress.getCanonicalHostName();

    }
}

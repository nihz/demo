package com.nee.demo.distributed.rpc;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

abstract class RpcRegistry {
    final CuratorFramework curatorFramework;
    final String ROOT_PATH = "/had-rpc";

    RpcRegistry() {
        this.curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("localhost:2182")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        curatorFramework.start();
    }
}

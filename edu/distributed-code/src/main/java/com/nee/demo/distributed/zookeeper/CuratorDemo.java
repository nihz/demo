package com.nee.demo.distributed.zookeeper;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

public class CuratorDemo {

    public static void main(String[] args) throws Exception {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2182")
                .canBeReadOnly(true)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

        curatorFramework.start();

        curatorFramework.create().creatingParentsIfNeeded().forPath("/my/path", "0".getBytes());


        System.out.println("data of /my/path is: " + new String(curatorFramework.getData().forPath("/my/path")));

        System.out.println(curatorFramework.getState());

        curatorFramework = CuratorFrameworkFactory.newClient("localhost:2182",
                new ExponentialBackoffRetry(1000, 3));

        curatorFramework.start();
        List<String> children = curatorFramework.getChildren().forPath("/my");

        curatorFramework.setData().withVersion(-1).forPath("/my/path", "1".getBytes());


        curatorFramework.delete().forPath("/my/path");

        System.out.println(children);
    }
}

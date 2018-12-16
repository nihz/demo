package com.nee.demo.distributed.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ConnectionDemo {

    public static void main(String[] args) {

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            ZooKeeper zooKeeper = new ZooKeeper("192.168.1.4:2181", 4000, watchedEvent -> {
                System.out.println(" all event: " + watchedEvent.getType());
                if (Watcher.Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();

            zooKeeper.exists("/zk-persis-nee", watchedEvent -> {
                System.out.println(watchedEvent.getType() + " -> " + watchedEvent.getPath()) ;
            });

            zooKeeper.create("/zk-persis-nee", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            Stat stat = new Stat();
            byte[] bytes = zooKeeper.getData("/zk-persis-nee", null, stat);
            System.out.println(new String(bytes));



            zooKeeper.setData("/zk-persis-nee", "1".getBytes(), stat.getVersion());

            zooKeeper.delete("/zk-persis-nee", stat.getVersion());

            System.out.println(zooKeeper.getState());
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }
}

package com.nee.demo.distributed.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class DistributedLock implements Lock, Watcher {

    private ZooKeeper zk = null;
    private String ROOT_LOCK = "/locks";
    private String WAIT_LOCK;
    private String CURRENT_LOCK;
    private CountDownLatch countDownLatch;

    public DistributedLock() {
        try {
            zk = new ZooKeeper("192.168.1.4:2181", 4000, this);
            Stat stat = zk.exists(ROOT_LOCK, false);
            if (stat == null) {
                zk.create(ROOT_LOCK, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        if (tryLock()) {
            System.out.println(Thread.currentThread().getName() + " -> " + CURRENT_LOCK);
        }
        try {
            waitForLock(WAIT_LOCK);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitForLock(String prev) throws KeeperException, InterruptedException {
        Stat stat = zk.exists(prev, true);
        if (stat != null) {
            System.out.println(Thread.currentThread().getName() + "wait for lock " + prev);
            countDownLatch = new CountDownLatch(1);
            countDownLatch.await();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            CURRENT_LOCK = zk.create(ROOT_LOCK  + "/", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "->" + CURRENT_LOCK);
            List<String> children = zk.getChildren(ROOT_LOCK, false);
            TreeSet<String> sortedSet = new TreeSet<>();
            children.forEach(c -> {
                sortedSet.add(ROOT_LOCK + "/" + c);
            });
            String firstNode = sortedSet.first();
            if (CURRENT_LOCK.equals(firstNode)) {
                return true;
            }
            SortedSet<String> lessThenMe = sortedSet.headSet(CURRENT_LOCK);
            if (!lessThenMe.isEmpty()) {
                WAIT_LOCK = lessThenMe.last();
            }

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

        try {
            zk.delete(CURRENT_LOCK, -1);
            CURRENT_LOCK = null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent event) {
        countDownLatch.countDown();
    }


    public static void main(String[] args) throws IOException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i =0 ;i < 10; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    DistributedLock lock = new DistributedLock();
                    lock.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Thread" + i).start();
            countDownLatch.countDown();
            System.in.read();
        }
    }
}

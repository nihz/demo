package com.nee.demo.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockDebugDemo {


    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        lock.lock();
        new Thread(() -> {
            lock.lock();
            System.out.println("sub thread acquire lock");
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();


        TimeUnit.SECONDS.sleep(5);
        System.out.println("main thread release lock");
        lock.unlock();
    }
}

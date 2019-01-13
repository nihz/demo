package com.nee.demo.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    static Lock lock = new ReentrantLock(true);
    private static int count = 0;

    private static void incr() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        count++;
        lock.unlock();

    }

    public static void main(String[] args) {

    }
}

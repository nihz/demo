package com.nee.demo.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockDemo {

    private static Map<String, Object> cacheMap = new HashMap<>();

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();


    public static final Object get(String key) {
        readLock.lock();
        try {
            return cacheMap.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public static final Object set(String key, Object valuse) {
        writeLock.lock();
        try {
            return cacheMap.put(key, valuse);
        } finally {
            writeLock.unlock();
        }
    }
}

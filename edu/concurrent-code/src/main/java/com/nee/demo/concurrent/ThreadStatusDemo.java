package com.nee.demo.concurrent;

import java.util.concurrent.TimeUnit;

public class ThreadStatusDemo {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Time-Waiting").start();
        new Thread(() -> {
            while (true) {
                try {
                    ThreadStatusDemo.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Waiting").start();
    }


    static class BlockDemo extends Thread {
        @Override
        public void run() {
            synchronized (BlockDemo.class) {
                while (true) {
                    try {
                        BlockDemo.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

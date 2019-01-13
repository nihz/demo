package com.nee.demo.concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(countDownLatch::countDown).start();
        new Thread(countDownLatch::countDown).start();
        new Thread(countDownLatch::countDown).start();

        countDownLatch.await();
        System.out.println("finish");
    }
}

package com.nee.demo.concurrent;

import java.util.concurrent.TimeUnit;

public class InterruptDemo {

    private static int i = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            /*while (!Thread.currentThread().isInterrupted()) {
                    i++;
            }*/
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.stop();

    }
}

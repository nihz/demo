package com.nee.demo.concurrent;

import java.util.concurrent.TimeUnit;

public class AutomicDemo {

    private static boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
           int i = 0;
           while (!stop) {
               i++;
               System.out.println(i);
           }
        }).start();

        TimeUnit.SECONDS.sleep(1);

        stop = true;
    }
}

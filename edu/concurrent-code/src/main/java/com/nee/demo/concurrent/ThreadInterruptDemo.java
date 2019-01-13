package com.nee.demo.concurrent;

import java.util.concurrent.TimeUnit;

public class ThreadInterruptDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
           while (true) {
               boolean interrupted = Thread.currentThread().isInterrupted();
               if (interrupted) {
                   System.out.println("before: " + interrupted);
                   System.out.println(Thread.interrupted());
                   System.out.println(Thread.interrupted());
                   System.out.println("after: " + Thread.currentThread().isInterrupted());
                   // Thread.currentThread().interrupt();
                   System.out.println("after: " + Thread.currentThread().isInterrupted());

               }
           }
        });

        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }
}

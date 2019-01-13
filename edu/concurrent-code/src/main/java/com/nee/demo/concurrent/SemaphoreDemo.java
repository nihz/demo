package com.nee.demo.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {

        int permits = 5;
        Semaphore semaphore = new Semaphore(permits);
        for (int i = 0; i < 10; i++) {
            new DoAnything(i, semaphore).start();
        }
    }

    static class DoAnything extends Thread {
        private int num;
        private Semaphore semaphore;


        DoAnything(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        public void run() {
            try {
                semaphore.acquire();
                System.out.println(num);
                TimeUnit.SECONDS.sleep(2);
                System.out.println("release token");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.nee.spring.cloud.hystrix;

import java.util.Random;
import java.util.concurrent.*;

public class FutureDemo {

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<String> future = service.submit(() -> {
            int value = new Random().nextInt(200);
            System.out.println("hello, word costs " + value + "ms");
            Thread.sleep(value);
            return "hello, world";
        });

        try {
            future.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}

package com.nee.demo.concurrent;

import java.util.concurrent.*;

public class CallableDemo implements Callable<String> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CallableDemo callableDemo = new CallableDemo();
        Future<String> future = executorService.submit(callableDemo);
        System.out.println(future.get());
        executorService.shutdown();
    }

    @Override
    public String call() throws Exception {

        TimeUnit.SECONDS.sleep(2);
        return "String";
    }
}

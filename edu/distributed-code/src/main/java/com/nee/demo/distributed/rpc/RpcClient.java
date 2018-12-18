package com.nee.demo.distributed.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class RpcClient implements InvocationHandler {

    private final RpcNettyClient nettyClient;

    public RpcClient() {
        this.nettyClient = new RpcNettyClient();
    }

    Object acquire(Class clazz) {

        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    public void handleResult(Object msg) {
        nettyClient.handleResult(msg);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvokeRequest invokeRequest = new InvokeRequest(new Packet(method.getDeclaringClass().getName(), method.getName(), args, new Random().nextInt(10000)));
        CountDownLatch countDownLatch = new CountDownLatch(1);
        nettyClient.sendRequest(invokeRequest);
        countDownLatch.await();
        return invokeRequest.getResult();
    }
}

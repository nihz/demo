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

        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvokeRequest invokeRequest = new InvokeRequest(
                new Packet(method.getDeclaringClass().getName(), method.getName(), method.getParameterTypes(), args, new Random().nextInt(10000)));
        invokeRequest.setCountDownLatch(new CountDownLatch(1));
        nettyClient.sendRequest(invokeRequest);
        invokeRequest.getCountDownLatch().await();
        return invokeRequest.getResult();
    }
}

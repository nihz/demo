package com.nee.demo.distributed.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class RpcClient implements InvocationHandler {

    private final RpcNettyClient nettyClient;
    private final RpcClientRegistry rpcClientRegistry;

    public RpcClient() {
        this.nettyClient = new RpcNettyClient();
        rpcClientRegistry = new RpcClientRegistry(new RpcRandomLoadBalance());
    }

    Object acquire(Class clazz) {

        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String url = rpcClientRegistry.acquire(method.getDeclaringClass().getName());
        String[] address = url.split(":");
        InvokeRequest invokeRequest = new InvokeRequest(
                new Packet(method.getDeclaringClass().getName(), method.getName(), method.getParameterTypes(), args, new Random().nextInt(10000), null));
        invokeRequest.setCountDownLatch(new CountDownLatch(1));
        invokeRequest.setHost(address[0]);
        invokeRequest.setPort(Integer.parseInt(address[1]));
        nettyClient.sendRequest(invokeRequest);
        invokeRequest.getCountDownLatch().await();
        return invokeRequest.getResult();
    }
}

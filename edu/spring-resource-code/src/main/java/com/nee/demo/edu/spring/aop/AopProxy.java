package com.nee.demo.edu.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AopProxy implements InvocationHandler {


    private AopConfig config;

    private Object target;

    public void setConfig(AopConfig aopConfig) {
        this.config = aopConfig;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (config.contains(method)) {
            AopConfig.Aspect aspect = config.get(method);
            aspect.getPoints()[0].invoke(aspect, args);
        }

        Object obj = method.invoke(this.target, args);
        if (config.contains(method)) {
            AopConfig.Aspect aspect = config.get(method);
            aspect.getPoints()[1].invoke(aspect, args);
        }
        return obj;
    }

    public Object getProxy(Object instance) {
        this.target = instance;
        Class clazz = instance.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }
}

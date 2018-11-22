package com.nee.demo.edu.pattern.proxy.jdk;

import com.nee.demo.edu.pattern.proxy.stati.Person;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {
    private Person target;

    public Object getInstance(Person person) {
        this.target = person;
        Class<?> clazz = target.getClass();

        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass());
        System.out.println("I am proxy");
        System.out.println("start proxy");
        method.invoke(this.target, args);

        System.out.println("end");
        return null;
    }
}

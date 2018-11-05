package com.nee.demo.edu.pattern.proxy.customer;

import com.nee.demo.edu.pattern.proxy.stati.Person;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NeeProxyTest implements NeeInvocationHandler {
    private Person target;

    public Object getInstance(Person person) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.target = person;
        Class<?> clazz = target.getClass();

        return NeeProxy.newProxyInstance(new NeeClassLoader(), clazz.getInterfaces(), this);
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass());
        System.out.println("I am proxy");
        System.out.println("start proxy ....");
        method.invoke(this.target, args);

        System.out.println("end");
        return null;
    }
}

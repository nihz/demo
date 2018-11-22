package com.nee.demo.edu.pattern.proxy.customer;

import java.lang.reflect.Method;

public interface NeeInvocationHandler {

    Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}

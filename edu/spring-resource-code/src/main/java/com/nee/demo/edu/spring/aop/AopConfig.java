package com.nee.demo.edu.spring.aop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AopConfig {

    private Map<Method, Aspect> points = new HashMap<>();

    public void put(Method target, Object aspect, Method[] points) {
        this.points.put(target, new Aspect(aspect, points));
    }

    public Aspect get(Method target) {
        return this.points.get(target);
    }

    public boolean contains(Method method) {
        return this.points.containsKey(method);
    }

    @Data
    @AllArgsConstructor
    public class Aspect {
        private Object aspect;
        private Method[] points;
    }
}

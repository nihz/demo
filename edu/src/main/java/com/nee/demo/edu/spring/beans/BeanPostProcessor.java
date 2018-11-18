package com.nee.demo.edu.spring.beans;

public class BeanPostProcessor {


    public Object postProcessBeforeInitialization(Object bean, String className) {

        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String className) {

        return bean;
    }
}
